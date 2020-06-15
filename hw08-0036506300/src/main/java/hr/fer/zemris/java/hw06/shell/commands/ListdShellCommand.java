package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * Lists all the directories on the shared data cdstack.
 * @author Vedran Kolka
 *
 */
public class ListdShellCommand extends AbstractShellCommand {
	
	public ListdShellCommand() {
		super("listd", "Lists all the directories on the shared data cdstack.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		@SuppressWarnings("unchecked")
		Stack<Path> cdStack = (Stack<Path>) env.getSharedData(CD_STACK_KEY);
		if(cdStack == null || cdStack.isEmpty()) {
			env.writeln("No directories on the cdstack.");
			return ShellStatus.CONTINUE;
		}
		// create a help stack to save all the directories
		Stack<Path> helpStack = new Stack<>();
		while(!cdStack.isEmpty()) {
			Path dir = cdStack.pop();
			helpStack.push(dir);
			env.writeln(dir.toString());
		}
		// return the directories on the cdStack
		while(!helpStack.isEmpty()) {
			cdStack.push(helpStack.pop());
		}
		
		return ShellStatus.CONTINUE;
	}

}
