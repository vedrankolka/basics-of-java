package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * The copy command expects two arguments: source file name and destination file
 * name (i.e. paths and names). If destination file exists, the user is asked if
 * is it allowed to overwrite it. The copy command works only with files (no
 * directories). If the second argument is directory, the original file is
 * copied into that directory using the original file name.
 * 
 * @author Vedran Kolka
 *
 */
public class CopyShellCommand extends AbstractShellCommand {

	public CopyShellCommand() {
		super("copy", "The copy command expects two arguments:",
				"source file name and destination file name (i.e. paths and names).",
				"If destination file exists, the user is asked if is it allowed to overwrite it.",
				"The copy command works only with files (no directories).", "If the second argument is directory, "
						+ "the original file is copied into that directory using the original file name.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (!ArgumentsParser.checkArguments(arguments)) {
			env.writeln("No arguments were given.");
			return ShellStatus.CONTINUE;
		}

		arguments = arguments.trim();
		// first read the arguments
		String source;
		String destination;
		try {
			if (arguments.contains("\"")) {
				int firstIndexOfQuotation = arguments.indexOf('"');
				int lastIndexOfQuotation = arguments.lastIndexOf('"');
				// if it is the first character, the first path is in quotation marks
				if (firstIndexOfQuotation == 0) {
					// if the last character is a quotation mark, then the second path is also in
					// quotation marks
					if (lastIndexOfQuotation == arguments.length() - 1) {
						// split where a quotation mark is followed by one or more spaces
						String[] args = arguments.split("\"\\s+", 2);
						source = args[0] + "\"";// return the quotation mark that was removed when splitting
						destination = args[1];
					} else {
						// if it was not, only the first argument is in quotation marks
						source = arguments.substring(0, lastIndexOfQuotation + 1);
						destination = arguments.substring(lastIndexOfQuotation + 1).trim();
					}
				} else {
					// else the second argument is in quotation marks
					source = arguments.substring(0, firstIndexOfQuotation).trim();
					destination = arguments.substring(firstIndexOfQuotation, lastIndexOfQuotation + 1);
				}
			} else {
				// else split by one or more spaces, because without quotation marks a path
				// cannot have spaces
				String[] args = arguments.split("\\s+");
				source = args[0];
				destination = args[1];
			}
		} catch (IndexOutOfBoundsException e) {
			env.writeln("The arguments for copy command are invalid: " + arguments);
			return ShellStatus.CONTINUE;
		}
		// now that the arguments are read, get the correct paths
		Path sourcePath;
		Path destinationPath;

		try {
			sourcePath = ArgumentsParser.parsePath(source);
			destinationPath = ArgumentsParser.parsePath(destination);
		} catch (InvalidPathException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(sourcePath)) {
			env.writeln("Cannot copy a directory.");
			return ShellStatus.CONTINUE;
		}
		// assume this will be the file name
		String destinationFileName = destinationPath.getFileName().toString();

		// if the destination is a directory, change the assumed file name to the
		// original file name
		if (Files.isDirectory(destinationPath)) {
			destinationFileName = sourcePath.getFileName().toString();
			// edit the path so that a new file will be created in the destination directory
			destinationPath = destinationPath.resolve(destinationFileName);
		} else if (Files.exists(destinationPath)) {
			// if the destination path exists but is not a directory,
			// a file is trying to be overwritten
			env.write("The destination file exists. Do You want to overwrite it? Y/N : ");
			String ans = env.readLine();
			if (!"Y".equalsIgnoreCase(ans)) {
				env.writeln("Copying not done.");
				return ShellStatus.CONTINUE;
			}
		}
		// finally, copy the file to the destination
		try {
			copy(sourcePath, destinationPath);
		} catch (IOException e) {
			env.writeln("copying from '" + sourcePath.getFileName() + "' to '" + destinationFileName + "' failed");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Copies the source file into the destination file.
	 * 
	 * @param sourcePath
	 * @param destinationPath
	 * @throws IOException if opening of the streams fails
	 */
	private void copy(Path sourcePath, Path destinationPath) throws IOException {
		try (InputStream is = Files.newInputStream(sourcePath);
				OutputStream os = Files.newOutputStream(destinationPath)) {

			byte[] buffer = new byte[1024];

			while (true) {
				int r = is.read(buffer);
				if (r < 1)
					break;
				os.write(buffer, 0, r);
				os.flush();
			}

		}

	}

}
