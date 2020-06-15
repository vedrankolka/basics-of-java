package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command cat takes one or two arguments.
 * The first argument is path to some file and is mandatory.
 * The second argument is charset name that should be used to interpret chars from bytes.
 * If not provided, a default platform charset is used.
 * @author Vedran Kolka
 *
 */
public class CatShellCommand extends AbstractShellCommand {
	
	public CatShellCommand() {
		super("cat",
			  "Command cat takes one or two arguments.",
			  "The first argument is path to some file and is mandatory.",
			  "The second argument is charset name that should be used to interpret chars from bytes.",
			  "If not provided, a default platform charset is used.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(!ArgumentsParser.checkArguments(arguments)) {
			env.writeln("No arguments were given.");
			return ShellStatus.CONTINUE;
		}
		
		Path source;
		Charset charset = Charset.defaultCharset();
		try {
			String pathString;
			if(arguments.contains("\"")) {
				//if the path is written in quotation marks, read the arguments accordingly
				String[] args = arguments.split("\"\\s+");
				if(args.length == 2) {
					charset = Charset.forName(args[2]);
				}
				//add the quotation mark if it was removed when splitting
				//it will be ignored when reading if it is extra
				pathString = args[0] + "\"";
	
			} else {
				String[] args = arguments.split("\\s+", 2);
				pathString = args[0];
				if(args.length == 2) {
					charset = Charset.forName(args[2]);
				}
			}
			source = ArgumentsParser.parsePath(pathString);
		} catch (IndexOutOfBoundsException e) {
			env.writeln("Illegal arguments for cat command.");
			return ShellStatus.CONTINUE;
		} catch (IllegalCharsetNameException | UnsupportedCharsetException | InvalidPathException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		try {
			List<String> lines = Files.readAllLines(source, charset);
			lines.forEach(env::writeln);
		} catch(IOException e) {
			env.writeln("Cannot read given file path");
		}
			
		return ShellStatus.CONTINUE;
	}
	
	

}
