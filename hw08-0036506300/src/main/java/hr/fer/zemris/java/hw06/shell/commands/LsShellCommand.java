package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexer;
import hr.fer.zemris.java.hw06.shell.lexer.ArgumentsLexerException;

/**
 * Command ls takes a single argument – directory –
 * and writes a directory listing (not recursive). 
 * @author Vedran Kolka
 *
 */
public class LsShellCommand extends AbstractShellCommand {
	
	public LsShellCommand() {
		super("ls",
				"Command ls takes a single argument – directory –"
				+ "and writes a directory listing (not recursive). ");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(!checkArguments(arguments)) {
			env.writeln("No arguments were given.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			ArgumentsLexer lexer = new ArgumentsLexer(arguments);
			Path dir = resolvePath(env, Paths.get(lexer.nextToken().getValue()));
			if(!Files.isDirectory(dir)) {
				env.writeln("The given path is not a directory: " + dir.toString());
				return ShellStatus.CONTINUE;
			}
			//DirectoryStream is closable so try with resources is used to ensure closing
			try(DirectoryStream<Path> streamOfChildren = Files.newDirectoryStream(dir)) {
				for(Path p : streamOfChildren) {
					String data = formatAttributes(p);
					env.writeln(data);
				}
			}
		} catch(InvalidPathException | IOException | ArgumentsLexerException e) {
			env.writeln(e.getMessage());
		}
		return ShellStatus.CONTINUE;
	} 

	/**
	 * Formats the relevant attributes into a line.
	 * @param pAttributes
	 * @return String containing the file attributes
	 * @throws IOException if attributes cannot be read
	 */
	private String formatAttributes(Path p) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		BasicFileAttributeView pAttributesView =  Files.getFileAttributeView(
				p,
				BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS );
		BasicFileAttributes pAttributes = pAttributesView.readAttributes();
		//append the flags
		sb.append( Files.isDirectory(p) ? 'd' : '-' );
		sb.append( Files.isReadable(p) ? 'r' : '-' );
		sb.append( Files.isWritable(p) ? 'w' : '-' );
		sb.append( Files.isExecutable(p) ? 'x' : '-' );
		//to separate columns
		sb.append(' ');
		//append the size
		sb.append(String.format("%10d", pAttributes.size()));
		//to separate columns
		sb.append(' ');
		//append the date and time
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FileTime fileTime = pAttributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis())); 
		sb.append(formattedDateTime);
		//to separate columns
		sb.append(' ');
		//append the file name
		sb.append(p.getFileName());
		
		return sb.toString();
	}

}
