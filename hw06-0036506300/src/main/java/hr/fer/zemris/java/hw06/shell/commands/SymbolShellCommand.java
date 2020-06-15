package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * If given one argument, the command writes the requested symbol.
 * If given two arguments, the command sets the requested symbol (first argument)
 * to the given value (second argument).
 * @author Vedran Kolka
 *
 */
public class SymbolShellCommand extends AbstractShellCommand {
	
	public SymbolShellCommand() {
		super("symbol",
			  "If given one argument, the command writes the requested symbol.",
			  "If given two arguments, the command sets the requested symbol (first argument) " + 
			  "to the given value (second argument).");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!ArgumentsParser.checkArguments(arguments)) {
			env.writeln("No arguments were given.");
			return ShellStatus.CONTINUE;
		}
		String[] args = arguments.split("\\s+");
		String symbolName = args[0];
		
		if (args.length == 1) {
			switch(symbolName) {
			case "PROMPT":
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() +"'");
				break;
			case "MORELINES":
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
				break;
			case "MULTILINE":
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'" );
				break;
			default:
				env.writeln("Unknown symbol :" + symbolName);
			}
		} else if (args.length == 2) {
			if(args[1].length() > 1) {
				env.writeln("A symbol can be only one charcter.");
				return ShellStatus.CONTINUE;
			}
			switch(symbolName) {
			case "PROMPT":
				env.writeln(String.format("Symbol for PROMPT changed from '%c' to '%c'",
							env.getPromptSymbol(), args[1].charAt(0)));
				env.setPromptSymbol(args[1].charAt(0));
				break;
			case "MORELINES":
				env.writeln(String.format("Symbol for MORELINES changed from '%c' to '%c'",
							env.getMorelinesSymbol(), args[1].charAt(0)));
				env.setMorelinesSymbol(args[1].charAt(0));
				break;
			case "MULTILINE":
				env.writeln(String.format("Symbol for MULTILINE changed from '%c' to '%c'",
							env.getMultilineSymbol(), args[1].charAt(0)));
				env.setPromptSymbol(args[1].charAt(0));
				break;
			default:
				env.writeln("Unknown symbol :" + symbolName);
			}
		} else {
			env.writeln("Wrong number od arguments.");
		}
		return ShellStatus.CONTINUE;
	}

}
