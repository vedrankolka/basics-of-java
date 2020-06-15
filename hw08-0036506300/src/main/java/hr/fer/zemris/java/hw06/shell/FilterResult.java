package hr.fer.zemris.java.hw06.shell;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that represents a single filter result of matching a file with the
 * regular expression.
 * 
 * @author Vedran Kolka
 *
 */
public class FilterResult {
	/**
	 * Name of the file.
	 */
	private String fileName;
	/**
	 * Matcher for the given pattern.
	 */
	private Matcher matcher;

	public FilterResult(String fileName, Matcher matcher) {
		this.fileName = fileName;
		this.matcher = matcher;
	}

	public String toString() {
		return fileName;
	}

	/**
	 * Returns the number of groups matched by the matcher.
	 * 
	 * @return number of groups matched by the matcher
	 */
	public int numberOfGroups() {
		return matcher.groupCount();
	}

	/**
	 * Returns the indexed group if it exists.
	 * 
	 * @param index
	 * @return indexed group
	 * @throws IndexOutOfBoundsException if index is greater than the number of
	 *                                   matched groups or less than 0
	 */
	public String group(int index) {
		return matcher.group(index);
	}

	/**
	 * Returns a list of FilterResults of the paths that match the given pattern.
	 * @param dir
	 * @param pattern
	 * @return list of FilterResults of the paths that match the given pattern
	 * @throws IOException
	 */
	public static List<FilterResult> filter(Path dir, String pattern) throws IOException {
		List<FilterResult> results = new ArrayList<>();
		int flags = Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE;
		Pattern regexPattern = Pattern.compile(pattern, flags);

		DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
		for(Path p : stream) {
			// we are not interested in anything but files
			if(Files.isDirectory(p))
				continue;
			String fileName = p.getFileName().toString();
			Matcher m = regexPattern.matcher(fileName);
			if(m.matches()) {
				results.add(new FilterResult(fileName, m));
			}
		}
		
		return results;
	}

}
