package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;

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

	@Override
	public Path getCurrentDirectory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurrentDirectory(Path path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getSharedData(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSharedData(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

}
