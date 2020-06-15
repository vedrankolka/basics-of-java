package hr.fer.zemris.java.hw17.trazilica.baza;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.java.hw17.trazilica.parser.TextParser;
import hr.fer.zemris.java.hw17.trazilica.vektor.Vector;

/**
 * A singleton class that represents a database of documents.
 * <p>
 * The class offers a lot of methods used for text processing.
 * 
 * @author Vedran Kolka
 *
 */
public class Database {
	/** The single database */
	private static Database database = new Database();
	/** The path to the file with croatian stopwords */
	private static final String STOPWORDS_PATH = "src/main/resources/hrvatski_stoprijeci.txt";
	/** The vocabulary of all the texts in this database, stopwords not included */
	private List<String> vocabulary;
	/** stopwords */
	private Set<String> stopwords;
	/**
	 * vectors built for each document that represent the document by whose name the
	 * vector is mapped
	 */
	private Map<String, Vector> vectors;
	/** Last search results of a query */
	private List<SearchResult> sr;
	/** idf vector */
	private Vector idf;

	private Database() {

	}

	public static Database getDatabase() {
		return database;
	}

	public List<String> getVocabulary() {
		return vocabulary;
	}

//	public Set<String> getStopwords() {
//		return stopwords;
//	}

//	public Vector getVector(String file) {
//		return vectors.get(file);
//	}

	public List<SearchResult> getSearchResults() {
		return sr;
	}

//	public Vector getIdf() {
//		return idf;
//	}

	/**
	 * The method to be called for initializing the database and loading all the
	 * relevant data into the memory, such as the vocabulary of the database and the
	 * tf.idf vectors of all documents from the database.
	 * 
	 * @param dir root directory of all the documents of the database.
	 * @throws IOException
	 */
	public void load(Path dir) throws IOException {

		if (vectors != null) {
			return;
		}

		Set<String> vocabularySet = new TreeSet<>();
		stopwords = loadStopWords();
		Files.walkFileTree(dir, new VocabularyBuilderVisitor(vocabularySet, stopwords));
		vocabulary = new ArrayList<String>(vocabularySet);
		calculateTfIdfVectors(dir);
	}

	/**
	 * Loads the stopwords from the file with path {@link #STOPWORDS_PATH}
	 * 
	 * @return set of stopwords
	 * @throws IOException
	 */
	private Set<String> loadStopWords() throws IOException {

		List<String> lines = Files.readAllLines(Paths.get(STOPWORDS_PATH), StandardCharsets.UTF_8);
		return new HashSet<>(lines);
	}

	/**
	 * Calculates all the values for each tf vector of the documents in this
	 * database.
	 * 
	 * @param dir
	 * @throws IOException
	 */
	private void calculateTfIdfVectors(Path dir) throws IOException {
		// create a map of vectors and calculate tf vectors
		vectors = new HashMap<>();
		idf = new Vector(new double[vocabulary.size()]);
		TfVectorBuilderVisitor vbv = new TfVectorBuilderVisitor();
		Files.walkFileTree(dir, vbv);
		// now the idf vector contains only counters so we calculate the real idf values
		// for each word in the vocabulary
		for (int i = 0; i < idf.getValues().length; i++) {
			idf.getValues()[i] = Math.log(vbv.fileCounter / (double) idf.getValues()[i]);
		}
		// now it is time to calculate the tf-idf values which is done for each word in
		// each document (each vector now that the documents have been analyzed)
		for (String key : vectors.keySet()) {
			Vector tf = vectors.get(key);
			vectors.put(key, calculateTfIdfVector(tf));
		}
	}

	/**
	 * Calculates the values for a single tf vector for a document with text
	 * <code>text</code> and updates the idf vector if the flag updateIdf is
	 * <code>true</code>.
	 * 
	 * @param text      of the document whose vector is being calculated
	 * @param updateIdf flag to indicate if this document should be taken into
	 *                  consideration when calculating the idf vector. Usually
	 *                  should be <code>true</code>
	 * @return
	 */
	private Vector calculateTfVector(String text, boolean updateIdf) {
		double[] values = new double[vocabulary.size()];
		List<String> textWords = new TextParser(text).getWords();
		Set<String> textVocabulary = new HashSet<>();

		for (String textWord : textWords) {

			int indexOfWord = vocabulary.indexOf(textWord);
			if (indexOfWord == -1)
				continue;

			if (updateIdf && textVocabulary.add(textWord)) {
				idf.getValues()[indexOfWord]++;
			}

			values[indexOfWord]++;
		}

		return new Vector(values);
	}

	/**
	 * Calculates the values for a single tf vector for a document with text
	 * <code>text</code>.
	 * 
	 * @param text
	 * @return vector
	 */
	public Vector calculateTfVector(String text) {
		return calculateTfVector(text, false);
	}

	public Vector calculateTfIdfVector(Vector tfVector) {
		double[] tfValues = tfVector.getValues();
		double[] tfIdfValues = new double[tfValues.length];
		for (int i = 0; i < tfValues.length; ++i) {
			tfIdfValues[i] = tfValues[i] * idf.getValues()[i];
		}
		return new Vector(tfIdfValues);
	}

	/**
	 * Searches the vectors for the best matches, creates a list of
	 * {@link SearchResult}s and sorts them by the rate of match descending.
	 * 
	 * @param tfIdf to search for
	 */
	public void searchForMatches(Vector tfIdf) {
		sr = new ArrayList<>();

		for (String file : vectors.keySet()) {
			Vector v = vectors.get(file);
			sr.add(new SearchResult(file, v, v.calculateCosine(tfIdf)));
		}

		sr.sort(null);
	}

	public void clear() {

	}

	// --------------------------------------------------------------------------------

	private static class VocabularyBuilderVisitor extends SimpleFileVisitor<Path> {

		private Set<String> toFill;

		private Set<String> toDiscard;

		public VocabularyBuilderVisitor(Set<String> toFill, Set<String> toDiscard) {
			this.toFill = toFill;
			this.toDiscard = toDiscard;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

			String text = Files.readString(file, StandardCharsets.UTF_8).toLowerCase();
			List<String> words = new TextParser(text).getWords();
			words.stream().filter(w -> !toDiscard.contains(w)).forEach(toFill::add);

			return FileVisitResult.CONTINUE;
		}

	}

	private class TfVectorBuilderVisitor extends SimpleFileVisitor<Path> {

		private int fileCounter = 0;

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

			String text = Files.readString(file, StandardCharsets.UTF_8);
			Vector tf = calculateTfVector(text.toLowerCase(), true);
			vectors.put(file.toString(), tf);
			fileCounter++;

			return FileVisitResult.CONTINUE;
		}
	}

}
