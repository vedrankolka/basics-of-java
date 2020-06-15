package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import hr.fer.zemris.java.hw06.shell.ArgumentsParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * The tree command expects a single argument: directory name and prints a tree.
 * @author Vedran Kolka
 *
 */
public class TreeShellCommand extends AbstractShellCommand {
	
	public TreeShellCommand() {
		super("tree",
			  "The tree command expects a single argument: directory name and prints a tree.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(!ArgumentsParser.checkArguments(arguments)) {
			env.writeln("No arguments were given.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Path root = ArgumentsParser.parsePath(arguments);
			Files.walkFileTree(root, new TreeVisitor(0, env));
		} catch(IOException e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}
	/**
	 * A simpleFileVisitor that writes the tree structure on the environment output.
	 * @author Vedran Kolka
	 *
	 */
	private static class TreeVisitor extends SimpleFileVisitor<Path> {
		
		private int level;
		private Environment env;

		public TreeVisitor(int level, Environment env) {
			this.level = level;
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			Path name = level==0? dir.toAbsolutePath() : dir.getFileName();
			env.writeln("  ".repeat(level) + name);
			level++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln("  ".repeat(level) + file.getFileName());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}
		
		
	}

}
