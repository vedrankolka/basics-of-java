package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexer;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexerException;

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

		if (!checkArguments(arguments)) {
			env.writeln("No arguments were given.");
			return ShellStatus.CONTINUE;
		}

		Path source;
		Path destination;
		try {
			ArgumentsLexer lexer = new ArgumentsLexer(arguments);
			source = resolvePath(env, Paths.get(lexer.nextToken().getValue()));
			destination = resolvePath(env, Paths.get(lexer.nextToken().getValue()));
			
		} catch (ArgumentsLexerException | InvalidPathException | NullPointerException e) {
			env.writeln("The arguments for copy command are invalid: " + arguments);
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(source)) {
			env.writeln("Cannot copy a directory.");
			return ShellStatus.CONTINUE;
		}
		// assume this will be the file name
		String destinationFileName = destination.getFileName().toString();

		// if the destination is a directory, change the assumed file name to the
		// original file name
		if (Files.isDirectory(destination)) {
			destinationFileName = source.getFileName().toString();
			// edit the path so that a new file will be created in the destination directory
			destination = destination.resolve(destinationFileName);
		} else if (Files.exists(destination)) {
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
			copy(source, destination);
			env.writeln("File copied.");
		} catch (IOException e) {
			env.writeln("copying from '" + source.getFileName() + "' to '" + destinationFileName + "' failed");
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
