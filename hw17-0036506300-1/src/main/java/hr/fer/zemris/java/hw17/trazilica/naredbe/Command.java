package hr.fer.zemris.java.hw17.trazilica.naredbe;

/**
 * Executable command.
 * 
 * @author Vedran Kolka
 *
 */
public interface Command {
	/**
	 * Action to perform when the commands is executed.
	 * 
	 * @param args of the command
	 * @return <code>true</code> if the shell should continue with its work,
	 *         <code>false</code> otherwise
	 */
	boolean execute(String args);

}
