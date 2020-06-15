package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

public class TestEnvironment implements Environment {
	
	@Override
	public String readLine() throws ShellIOException {
		return null;
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return null;
	}

	@Override
	public Character getMultilineSymbol() {
		return null;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
	}

	@Override
	public Character getPromptSymbol() {
		return null;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
	}

	@Override
	public Character getMorelinesSymbol() {
		return null;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
	}

}
