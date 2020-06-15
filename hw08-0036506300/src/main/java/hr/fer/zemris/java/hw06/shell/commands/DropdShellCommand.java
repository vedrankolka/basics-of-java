package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * Removes the directory on top of the shared memory cdstack.
 * @author Vedran Kolka
 *
 */
public class DropdShellCommand extends AbstractShellCommand {

	public DropdShellCommand() {
		super("dropd", "Removes the directory on top of the shared memory cdstack.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		@SuppressWarnings("unchecked")
		Stack<Path> cdStack = (Stack<Path>) env.getSharedData(CD_STACK_KEY);
		if(cdStack == null || cdStack.isEmpty()) {
			env.writeln("The cdstack is empty.");
		} else {
			cdStack.pop();
		}
		
		return ShellStatus.CONTINUE;
	}

}
