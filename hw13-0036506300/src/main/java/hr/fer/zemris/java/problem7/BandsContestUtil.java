package hr.fer.zemris.java.problem7;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * A utility class that offers static methods for loading the contest definition
 * file and for loading and changing the contest results file.
 * 
 * @author Vedran Kolka
 *
 */
public class BandsContestUtil {

	/**
	 * Creates a map from the file with given <code>path</code> where each line is
	 * expected to have at least a unique integer, a tab as a separator and a string
	 * that is the band name.
	 * 
	 * @param path to the file with the
	 * @return a map of bands mapped by their ids or <code>null</code> if the file
	 *         does not exist
	 */
	public static SortedMap<Integer, String[]> loadBands(String path) throws IOException {

		if (!Files.exists(Paths.get(path))) {
			return null;
		}

		SortedMap<Integer, String[]> bands = new TreeMap<>();
		fillMap(path, bands, Integer::parseInt, (s -> s.split("\t+")));
		return bands;
	}

	/**
	 * Creates a map from the file with given <code>path</code> where each line is
	 * expected to have at least a unique integer, a tab as a separator and an
	 * integer representing the number of votes for the band with the associated id.
	 * 
	 * @param path to the file with the
	 * @return a map of numbers of votes mapped by their band's ids
	 */
	public static SortedMap<Integer, Integer> loadVotes(String path) throws IOException {

		if (!Files.exists(Paths.get(path))) {
			return null;
		}

		SortedMap<Integer, Integer> votes = new TreeMap<>();
		fillMap(path, votes, Integer::parseInt, Integer::parseInt);
		return votes;
	}

	/**
	 * Stores data from the given map to the file with the given <code>path</code>
	 * where each entry is written in the form:"id numberOfVotes\n" where the
	 * separator between "id" and "numberOfVotes" is a tab (\t).
	 * 
	 * @param path  to the file where the data is written
	 * @param votes map of numbers of votes mapped by their respective band ids
	 * @throws IOException
	 */
	public static void storeVotes(String path, Map<Integer, Integer> votes) throws IOException {
		try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(path)))) {

			for (Map.Entry<Integer, Integer> e : votes.entrySet()) {
				byte[] line = (e.getKey() + "\t" + e.getValue() + "\n").getBytes("UTF-8");
				bos.write(line);
			}

		}
	}

	/**
	 * Creates a map of numbers of votes that are set to zeros mapped by the band
	 * ids from the contest definition file with path <code>bandsDefPath</code> and
	 * returns it.
	 * 
	 * @param bandsDefPath path to the file where the bands for the contest are
	 *                     defined
	 * @return map of zeros mapped by band ids
	 * @throws IOException
	 */
	public static SortedMap<Integer, Integer> createVotes(String bandsDefPath) throws IOException {

		SortedMap<Integer, String[]> bands = loadBands(bandsDefPath);
		System.out.println(bands == null);
		SortedMap<Integer, Integer> votes = new TreeMap<>();

		for (Integer id : bands.keySet()) {
			votes.put(id, 0);
		}

		return votes;
	}

	/**
	 * Fills the given <code>mapToFill</code> with lines from file with given
	 * <code>path</code> with the given functions for transforming the key and value
	 * for the map.
	 * 
	 * @param path        of the file from which the lines are read
	 * @param mapToFill   map to fill
	 * @param keyMapper   function used to transform a string into the appropriate
	 *                    key for the map
	 * @param valueMapper function used to transform a string into the appropriate
	 *                    value for the map
	 * @throws IOException
	 */
	private static <K, V> void fillMap(String path, Map<K, V> mapToFill, Function<String, K> keyMapper,
			Function<String, V> valueMapper) throws IOException {

		List<String> lines = Files.readAllLines(Paths.get(path));

		for (String l : lines) {
			String[] line = l.split("\t+", 2);
			K key = keyMapper.apply(line[0]);
			V value = valueMapper.apply(line[1]);
			mapToFill.put(key, value);
		}
	}

}
