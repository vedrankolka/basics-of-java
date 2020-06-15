package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.crypto.Util;
import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * The hexdump command expects a single argument: file name, and produces
 * hex-output.
 * 
 * @author Vedran Kolka
 *
 */
public class HexdumpShellCommand extends AbstractShellCommand {

	public HexdumpShellCommand() {
		super("hexdump", "The hexdump command expects a single argument: file name, and produces hex-output.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (!ArgumentsParser.checkArguments(arguments)) {
			env.writeln("No arguments were given.");
			return ShellStatus.CONTINUE;
		}

		Path path;
		try {
			path = ArgumentsParser.parsePath(arguments);
		} catch (InvalidPathException e) {
			env.writeln(arguments + " is not a valid file path.");
			return ShellStatus.CONTINUE;
		}

		try (InputStream is = Files.newInputStream(path)) {
			List<String> lines = hexRead(is);
			lines.forEach(env::writeln);
		} catch (IOException e) {
			env.writeln(e.getMessage());
		}

		return ShellStatus.CONTINUE;
	}

	private List<String> hexRead(InputStream is) throws IOException {
		List<String> lines = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		long count = 0;
		while (true) {
			byte[] buffer = is.readNBytes(16);
			if (buffer.length == 0)
				break;
			sb.append(String.format("%08X: ", count));
			sb.append(getHexFormatted(buffer, 0, 8));
			sb.append('|');
			sb.append(getHexFormatted(buffer, 8, 8));
			sb.append(" | ");
			sb.append(getCharacters(buffer));
			lines.add(sb.toString());
			sb.delete(0, sb.length());
			count += buffer.length;
		}

		return lines;
	}

	private String getCharacters(byte[] buffer) {
		StringBuilder sb = new StringBuilder();
		for (byte b : buffer) {
			char c = b < 32 || b > 127 ? '.' : (char) b;
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * Formats the bytes in given <code>buffer</code> from indexes offset to
	 * <code>offset</code> + <code>len</code> to hexadecimal characters two by two,
	 * separated with a single space character and returns the result as a string.
	 * 
	 * @param buffer
	 * @param offset
	 * @param len
	 * @return formatted hexadecimal representation of the selected bytes in buffer
	 */
	private String getHexFormatted(byte[] buffer, int offset, int len) {
		StringBuilder sb = new StringBuilder();
		// the first ILLEGAL index is the minimum of the compared lengths
		int firstIllegalIndex = buffer.length < offset + len ? buffer.length : offset + len;
		
		for (int i = offset; i < offset + len; i++) {
			if(i < firstIllegalIndex) {
				byte[] maskArray = { buffer[i] };
				sb.append(Util.bytetohex(maskArray)).append(' ');
			} else {
				sb.append("   ");
			}
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}

}
