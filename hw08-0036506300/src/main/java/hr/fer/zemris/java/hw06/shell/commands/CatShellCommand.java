package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexer;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexerException;

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
		
		if(!checkArguments(arguments)) {
			env.writeln("No arguments were given.");
			return ShellStatus.CONTINUE;
		}
		
		Path source;
		Charset charset = Charset.defaultCharset();
		try {
			ArgumentsLexer lexer = new ArgumentsLexer(arguments);
			source = resolvePath(env, Paths.get(lexer.nextToken().getValue()));
			String token = lexer.nextToken().getValue();
			if(token != null) {
				charset = Charset.forName(token);
			}
		} catch (IllegalCharsetNameException | UnsupportedCharsetException |
				InvalidPathException | ArgumentsLexerException e) {
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
