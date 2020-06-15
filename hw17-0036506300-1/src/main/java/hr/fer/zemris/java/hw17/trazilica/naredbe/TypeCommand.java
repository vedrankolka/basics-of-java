package hr.fer.zemris.java.hw17.trazilica.naredbe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.hw17.trazilica.baza.Database;
import hr.fer.zemris.java.hw17.trazilica.baza.SearchResult;

/**
 * Command that writes the whole document to the standard output.
 * 
 * @author Vedran Kolka
 *
 */
public class TypeCommand implements Command {

	private static final String ERROR_MESSAGE = "type naredba zahtijeva jedan argument - indeks reezultata za ispisati.";

	@Override
	public boolean execute(String args) {

		List<SearchResult> sr = Database.getDatabase().getSearchResults();
		if (sr == null) {
			System.out.println("Niste zadali upit ('query' naredba).");
			return true;
		}

		if (args == null || args.isBlank()) {
			System.out.println(ERROR_MESSAGE);
			return true;
		}

		try {
			int index = Integer.parseInt(args);
			if (index < 0 || index > 59) {
				System.out.println("Nema rezultata pod indeksom " + index);
				return true;
			}
			String fileName = sr.get(index).getFile();
			String text = Files.readString(Paths.get(fileName), StandardCharsets.UTF_8);
			System.out.println("-".repeat(fileName.length() + 11));
			System.out.println("Dokument: " + fileName);
			System.out.println("-".repeat(fileName.length() + 11));
			System.out.println(text);
		} catch (IOException e) {
			System.out.println("Čitanje nije uspjelo.");
		} catch (NumberFormatException e) {
			System.out.println("Neispravan argument: " + args);
			System.out.println(ERROR_MESSAGE);
		}

		return true;
	}

}
