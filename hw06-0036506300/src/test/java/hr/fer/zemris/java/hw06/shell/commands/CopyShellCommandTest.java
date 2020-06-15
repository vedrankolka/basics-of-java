package hr.fer.zemris.java.hw06.shell.commands;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.TestEnvironment;

public class CopyShellCommandTest {

	public static CopyShellCommand cmd;
	public static Environment env;

	@BeforeAll
	public static void initializeCommand() {
		cmd = new CopyShellCommand();
		env = new TestEnvironment();
	}

	@Test
	public void copyFile1() {
		cmd.executeCommand(env,
				"src/test/resources/read/testFile1.txt " + "src/test/resources/write/testFile1.copy.txt");

		Path p1 = Paths.get("src/test/resources/read/testFile1.txt");
		Path p2 = Paths.get("src/test/resources/write/testFile1.copy.txt");
		compareDocuments(p1, p2);
	}

	@Test
	public void copyFile2() {
		cmd.executeCommand(env,
				"src/test/resources/read/testFile2.xlsx " + "src/test/resources/write/testFile2.copy.xlsx");

		Path p1 = Paths.get("src/test/resources/read/testFile2.xlsx");
		Path p2 = Paths.get("src/test/resources/write/testFile2.copy.xlsx");
		compareDocuments(p1, p2);
	}

	@Test
	public void copyFile3() {
		cmd.executeCommand(env,
				"src/test/resources/read/testFile3.pdf " + "src/test/resources/write/testFile3.copy.pdf");

		Path p1 = Paths.get("src/test/resources/read/testFile3.pdf");
		Path p2 = Paths.get("src/test/resources/write/testFile3.copy.pdf");
		compareDocuments(p1, p2);
	}

	@Test
	public void copyFile11() {
		cmd.executeCommand(env,
				"src/test/resources/read/testFile1.txt " + "\"src/test/resources/write/testFile1.copy1.txt\"");

		Path p1 = Paths.get("src/test/resources/read/testFile1.txt");
		Path p2 = Paths.get("src/test/resources/write/testFile1.copy1.txt");
		compareDocuments(p1, p2);
	}

	@Test
	public void copyFile12() {
		cmd.executeCommand(env,
				"\"src/test/resources/read/testFile1.txt\" " + "src/test/resources/write/testFile1.copy2.txt");

		Path p1 = Paths.get("src/test/resources/read/testFile1.txt");
		Path p2 = Paths.get("src/test/resources/write/testFile1.copy2.txt");
		compareDocuments(p1, p2);
	}

	@Test
	public void copyFile13() {
		cmd.executeCommand(env,
				"\"src/test/resources/read/testFile1.txt\" " + "\"src/test/resources/write/testFile1.copy3.txt\"");

		Path p1 = Paths.get("src/test/resources/read/testFile1.txt");
		Path p2 = Paths.get("src/test/resources/write/testFile1.copy3.txt");
		compareDocuments(p1, p2);
	}
	
	@Test
	public void difficultFileNameTest() {
		cmd.executeCommand(env,
				"\"src\\test\\\\resources/read/a_folder with a Tricky name/difficult name to read my boi.txt.txt\""
				+ "  \"src/test/resources/write/difficultNameCobyPriant.txt\" ");
		Path p1 = Paths.get("src/test/resources/read/a_folder with a Tricky name/difficult name to read my boi.txt.txt");
		Path p2 = Paths.get("src/test/resources/write/difficultNameCobyPriant.txt");
		compareDocuments(p1, p2);
	}
	
	@Test
	public void copyToDirectoryTest() {
		cmd.executeCommand(env,
				"src/test/resources/read/testFile4.txt src/test/resources/write");
		Path p1 = Paths.get("src/test/resources/read/testFile4.txt");
		Path p2 = Paths.get("src/test/resources/write/testFile4.txt");
		compareDocuments(p1, p2);
	}

	@Test
	public void invalidPathsTest() {
		try {
			cmd.executeCommand(env, "");
			cmd.executeCommand(env, "invalid Path");
			cmd.executeCommand(env, "       \"invalid path\" ");
			cmd.executeCommand(env, null);
			cmd.executeCommand(env, "src/test invalid/path.txt");
			cmd.executeCommand(env, "src/test/resources/read/testFile1.txt testFile1");
			cmd.executeCommand(env, " \"babe male\" mande\test1.hehe \" \"a i\\ drugi je grd\" \"");
		} catch(Exception e) {
			fail(e.getClass().getSimpleName() + ": " + e.getMessage());
		}
	}

	/**
	 * Method to check if the content of the given files is identical.
	 * 
	 * @param p1
	 * @param p2
	 */
	private void compareDocuments(Path p1, Path p2) {
		try {
			byte[] prvi = Files.readAllBytes(p1);
			byte[] drugi = Files.readAllBytes(p2);

			assertEquals(prvi.length, drugi.length);

			for (int i = 0; i < prvi.length; ++i) {
				assertEquals(prvi[i], drugi[i]);
			}
		} catch (IOException e) {
			fail(e.getClass().getSimpleName() + ": " + e.getMessage());
		}
	}

}
