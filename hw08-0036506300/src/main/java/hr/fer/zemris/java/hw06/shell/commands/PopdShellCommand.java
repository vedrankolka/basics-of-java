package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.EmptyStackException;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * Pops a path from the cdstack in the shared memory and sets it as the working directory.
 * @author Vedran Kolka
 *
 */
public class PopdShellCommand extends AbstractShellCommand {
	
	public PopdShellCommand() {
		super("popd", "Pops a path from the cdstack in the shared memory and sets"
				+ "it as the working directory.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			@SuppressWarnings("unchecked")
			Stack<Path> cdStack = (Stack<Path>)env.getSharedData(CD_STACK_KEY);
			if(cdStack == null) {
				env.writeln("No directories were yet pushed on the stack.");
				return ShellStatus.CONTINUE;
			}
			Path dir = cdStack.pop();
			env.setCurrentDirectory(dir);
			
		} catch (EmptyStackException e) {
			env.writeln("No directories on the stack.");
		} catch (IllegalArgumentException e) {
			env.writeln("The directory was erased and cannot be set as a working directory.");
		}
		return ShellStatus.CONTINUE;
	}

}
