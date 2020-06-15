package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexer;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexerException;
/**
 * Pushes the current directory on a shared data stack
 * Sets the given directory as the working directory.
 * @author Vedran Kolka
 *
 */
public class PushdShellCommand extends AbstractShellCommand {

	public PushdShellCommand() {
		super("pushd", "Pushes the current directory on a shared data stack",
				"Sets the given directory as the working directory");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		@SuppressWarnings("unchecked")
		Stack<Path> cdStack = (Stack<Path>) env.getSharedData(CD_STACK_KEY);
		if (cdStack == null) {
			cdStack = new Stack<Path>();
			env.setSharedData("cdstack", cdStack);
		}
		
		try {
			Path oldDir = env.getCurrentDirectory();
			ArgumentsLexer lexer = new ArgumentsLexer(arguments);
			Path dir = resolvePath(env, Paths.get(lexer.nextToken().getValue()));
			env.setCurrentDirectory(dir);
			cdStack.push(oldDir);
			
		} catch (ArgumentsLexerException | IllegalArgumentException e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

}
