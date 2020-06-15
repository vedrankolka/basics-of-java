package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class that offers static methods for parsing arguments
 * for the ShellCommands.
 * @author Vedran Kolka
 *
 */
public class ArgumentsParser {

	public static final char ESCAPE_SIGN = '\\';
	
	/**
	 * A utility method for parsing the given string in quotes to a Path.
	 * @param stringPath to parse
	 * @return parsed Path
	 * @throws IllegalArgumentException if the escape sequence in the given
	 * <code>stringPath</code> is invalid
	 */
	public static Path parseStringToPath(String stringPath) {
		
		char[] data = stringPath.toCharArray();
		int currentIndex = 0;
		StringBuilder sb = new StringBuilder();
		//TODO mozda impl da moze i bez njih delat
		if(data[currentIndex++] != '"') {
			throw new IllegalArgumentException("The expected was expected in quotation marks.");
		}
		
		while(currentIndex<data.length && data[currentIndex] != '"') {
			//if it is the escape sign
			if(data[currentIndex] == ESCAPE_SIGN) {
				//check if it is a legal escape sequence
				if(currentIndex+1 < data.length && (
						data[currentIndex+1] == '\\' ||
						data[currentIndex+1] == '"') ) {
					//skip the escape sign
					currentIndex++;
				}
			}
			sb.append(data[currentIndex++]);
		}
		return Paths.get(sb.toString());
	}
	
	/**
	 * Parses a path from the given String <code>path</code>
	 * either in quotes or without quotes and returns it.
	 * @param Stirng path to parse
	 * @return parsed path
	 */
	public static Path parsePath(String path) {
		Path p;
		path = path.trim();
		if(path.startsWith("\"") && path.endsWith("\"")) {
			p = parseStringToPath(path);
		} else {
			p = Paths.get(path);
		}
		return p;
	}
	
	/**
	 * Checks if the given <code>arguments</code> are not null and not an empty string.
	 * @param arguments to check
	 * @return <code>true</code> if they are, <code>false</code> otherwise
	 */
	public static boolean checkArguments(String arguments) {
		return arguments != null && arguments.length() > 0;
	}
	
}
