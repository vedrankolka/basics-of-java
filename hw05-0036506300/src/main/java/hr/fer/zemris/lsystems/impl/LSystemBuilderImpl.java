package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.function.Consumer;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

public class LSystemBuilderImpl implements LSystemBuilder {
	/**
	 * A dictionary of registered commands.
	 */
	private Dictionary<Character, Command> commands;
	/**
	 * A dictionary of registered productions.
	 */
	private Dictionary<Character, String> productions;
	
	private double unitLength = 0.1;
	private double unitLengthDegreeScaler = 1;
	private Vector2D origin = new Vector2D(0, 0);
	/**
	 * Angle in <b>degrees</b>.
	 */
	private double angle = 0;
	private String axiom = "";
	
	public LSystemBuilderImpl() {
		commands = new Dictionary<>();
		productions = new Dictionary<>();
	}
	
	public class LSystemImpl implements LSystem {

		@Override
		public void draw(int level, Painter painter) {
			Context ctx = new Context();
			//create a direction vector and rotate it for the set angle
			Vector2D direction = new Vector2D(1, 0);
			direction.rotate(Math.toRadians(angle));
			//calculate the delta of the turtle
			double newUnitLength = unitLength*Math.pow(unitLengthDegreeScaler, level);
			//create the set state and push it onto the context stack
			TurtleState state = new TurtleState(origin.copy(), direction, Color.BLACK, newUnitLength);
			ctx.pushState(state);
			//generate the string that represents the system to draw
			String result = generate(level);
			char[] array = result.toCharArray();
			//search the string for characters that represent commands and execute them
			for(int i = 0 ; i<array.length ; ++i) {
				Command command = commands.get(array[i]);
				if(command == null) {
					continue;
				}
				command.execute(ctx, painter);
			}
			
		}

		@Override
		public String generate(int level) {
			StringBuilder sb = new StringBuilder();
			char[] characters = axiom.toCharArray();
			for( int i = 0 ; i<characters.length ; ++i) {
				sb.append(generateRecursive(characters[i], level));
			}
			return sb.toString();
		}
		
		/**
		 * Generates the production of the given character and then calls itself
		 * to generate the productions of all the generated characters until
		 * level is 0.
		 * @param c
		 * @param level
		 * @return String production of the character <code>c</code>
		 */
		private String generateRecursive(char c, int level) {
			String production = productions.get(c);
			//if the character does not have any productions or no more levels are needed, return
			if(production == null || level == 0) {
				return Character.toString(c);
			}
			char[] charactersOfProduction = production.toCharArray();
			StringBuilder sb = new StringBuilder();
			//generate all the productions of characters of this production
			for( int i = 0 ; i<charactersOfProduction.length ; ++i) {
				sb.append(generateRecursive(charactersOfProduction[i], level-1));
			}
			return sb.toString();
		}
		
	}
	
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	@Override
	public LSystemBuilder configureFromText(String[] arguments) {
		try {
			for(String s : arguments) {
				
				String[] lineArguments = s.split("\\s+"); //split by one or more spaces
				switch(lineArguments[0]) {
				case "":
					continue;
				case "origin":
					setOriginFromString(lineArguments[1], lineArguments[2]);
					continue;
				case "angle":
					parseNumber(angle -> setAngle(angle), lineArguments[1]);
					continue;
				case "unitLength":
					parseNumber(unitLength -> setUnitLength(unitLength), lineArguments[1]);
					continue;
				case "unitLengthDegreeScaler":
					setUnitLengthDegreeScalerFromString(lineArguments);
					continue;
				case "command":
					registerCommandFromLine(lineArguments);
					continue;
				case "axiom":
					setAxiom(lineArguments[1]);
					continue;
				case "production":
					registerProduction(lineArguments[1].charAt(0), lineArguments[2]);
					continue;
				default:
					throw new RuntimeException("Unexpected configuation keyword.");
				}
				
			}
		} catch(IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("The configuration is not written correctly.%n" +
												e.getMessage());
		}
		return this;
	}
	
	private void registerCommandFromLine(String[] line) {
		String commandArguments = null;
		if(line.length == 4) {
			commandArguments = line[2] + " " + line[3];
		} else if(line.length == 3) {
			commandArguments = line[2];
		} else {
			throw new IllegalArgumentException("Wrong number of arguments for the command : " + line[1]);
		}
		registerCommand(line[1].charAt(0), commandArguments);
	}
	
	/**
	 * A helper method that tries to parse the given argument and gives it
	 * to the consumer c.
	 * @param c consumer for the parsed number
	 * @param argument to parse
	 */
	private void parseNumber( Consumer<Double> c, String argument) {
		try {
			double number = Double.parseDouble(argument);
			c.accept(number);
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException(argument + " cannot be accepted as a number.");
		}
	}
	
	/**
	 * Tries to read the given strings as arguments to set the origin.
	 * @param sX
	 * @param sY
	 */
	private void setOriginFromString(String sX, String sY) {
		try {
			double x = Double.parseDouble(sX);
			double y = Double.parseDouble(sY);
			setOrigin(x, y);
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException("Arguemnts for origin are invalid: " +sX + " " + sY);
		}
	}
	
	/**
	 * Reads the argument for the unitLengthScaler and sets it to the read value.
	 * The argument can be given in a a/b format where a and b are numbers.
	 * @param line from which to read the arguments
	 * @throws IllegalArgumentException if the argument was not in a correct format
	 */
	private void setUnitLengthDegreeScalerFromString(String[] line) {
		String argument = "";
		//put together all the remaining arguments from the line
		for(int i = 1 ; i<line.length ; ++i) {
			argument += line[i];
		}
		String[] numbers = argument.split("/");
		try {
			double number1 = Double.parseDouble(numbers[0]);
			double number2 = numbers.length == 2 ? Double.parseDouble(numbers[1]) : 1.0 ;
			setUnitLengthDegreeScaler(number1/number2);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("The numbers are not written"
					+ " in an acceptable format: " + argument);
		}
	}

	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		String[] arguments = arg1.split("\\s+");
		Command command;
		if(arguments.length == 2) {
			command = readCommandWithArgument(arguments[0], arguments[1]);
		} else if(arguments.length == 1) {
			command = readCommand(arguments[0]);
		} else {
			throw new IllegalArgumentException("The command '" + arg1 + "' is invalid");
		}
		commands.put(arg0, command);
		return this;
	}
	
	/**
	 * Reads the command from the given strings and returns it as a {@link Command}.
	 * @param name of the command
	 * @param argument of the command
	 * @return Command
	 * @throws IllegalArgumentException if the command is unknown or the argument
	 * of the command is invalid (not a color or number)
	 */
	private Command readCommandWithArgument(String name, String argument) {
		if(name.equals("color")) {
			Color color = Color.decode("#" + argument);
			return new ColorCommand(color);
		}
		
		try {
			double number = Double.parseDouble(argument);
			switch(name) {
			case "draw":
				return new DrawCommand(number);
			case "rotate":
				return new RotateCommand(number);
			case "scale":
				return new ScaleCommand(number);
			case "skip":
				return new SkipCommand(number);
			default:
				throw new IllegalArgumentException("Unknown command: " + name);
			}
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException("Wrong argument for " + name + " command.");
		}
	}
	
	/**
	 * Reads the command from the given string and returns it as a {@link Command}.
	 * @param name of the command
	 * @return Command
	 * @throws IllegalArgumentException if the command is unknown
	 */
	private Command readCommand(String name) {
		switch(name) {
		case "pop":
			return new PopCommand();
		case "push":
			return new PushCommand();
		default:
			throw new IllegalArgumentException("Unknown command: " + name);
		}
	}

	@Override
	public LSystemBuilder registerProduction(char c, String production) {
		productions.put(c, production);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		origin = new Vector2D(x, y);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

}
