package hr.fer.zemris.java.hw17.trazilica.naredbe;

/**
 * Command to exit and terminate the shell.
 * 
 * @author Vedran Kolka
 *
 */
public class ExitCommand implements Command {

	@Override
	public boolean execute(String args) {
		return false;
	}

}
