package hr.fer.zemris.java.hw17.trazilica.naredbe;

import java.util.List;

import hr.fer.zemris.java.hw17.trazilica.baza.Database;
import hr.fer.zemris.java.hw17.trazilica.vektor.Vector;

/**
 * Searches documents based on the given words as arguments of the command.
 * 
 * @author Vedran Kolka
 *
 */
public class QueryCommand implements Command {
	/**
	 * a command to execute when the results have been searched to show them to the
	 * user.
	 */
	private static Command resultsCommand = new ResultsCommand();

	@Override
	public boolean execute(String args) {

		if (args == null || args.isBlank()) {
			System.out.println("query naredba zahtijeva argumente.");
			return true;
		}
		Database db = Database.getDatabase();

		String filteredArguments = filter(db.getVocabulary(), args.toLowerCase());
		System.out.println("Query is: " + filteredArguments);
		if (filteredArguments.length() == 2) {
			System.out.println("Nema rezultata.");
			return true;
		}
		Vector tfIdf = db.calculateTfIdfVector(db.calculateTfVector(filteredArguments));

		db.searchForMatches(tfIdf);
		resultsCommand.execute(null);

		return true;
	}

	/**
	 * Filters all the words from the given <code>args</code> that are not in the
	 * vocabulary.
	 * 
	 * @param vocabulary
	 * @param args
	 * @return filtered String
	 */
	private String filter(List<String> vocabulary, String args) {

		String[] words = args.split("\\s+");
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (String w : words) {
			if (vocabulary.contains(w)) {
				sb.append(w).append(", ");
			}
		}
		if (sb.length() == 1) {
			sb.append(']');
		} else {
			sb.replace(sb.length() - 2, sb.length(), "]");
		}

		return sb.toString();
	}

}
