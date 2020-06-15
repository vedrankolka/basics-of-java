package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.db.simplelexer.LexerException;

/**
 * A program that creates a StudentDatabase and offers a query-like interaction
 * with the created database.
 * The program reads a single argument from the command line that is a path to
 * the file where the student record are written.
 * @author Vedran Kolka
 *
 */
public class StudentDB {
	
	private static final String PROMPT = "> ";

	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("Wrong number of arguments.");
			return;
		}
		StudentDatabase db;
		//try to read the file and load the database
		try {
			List<String> lines = Files.readAllLines(
					 Paths.get(args[0]),
					 StandardCharsets.UTF_8
					);
			db = new StudentDatabase(lines);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return;
		}

		
		try(Scanner sc = new Scanner(System.in)) {
			while(true) {
				System.out.print(PROMPT);
				String command = sc.next();
				
				if("exit".equals(command)) {
					System.out.println("Goodbye!");
					break;
				}
				
				if("query".equals(command)) {
					try {
						executeQuery(sc.nextLine(), db);
					} catch (IllegalArgumentException | LexerException e) {
						System.out.println(e.getMessage());
					}
					continue;
				}
				System.out.println("Unknown command. Type 'query' for a query or 'exit' to exit");
			}
		}
	}
	
	/**
	 * The method executes the query given in the <code>text</code> form.
	 * It retrieves the queried records and formats them in a table
	 * and writes them on the standard output.
	 * @param text to parse
	 * @param StudentDatabase from which to retrieve records
	 */
	private static void executeQuery(String text, StudentDatabase db) {
		
		QueryParser qp = new QueryParser(text);
		List<StudentRecord> records = getQueriedRecords(qp, db);
		List<String> output = formatQueryOutput(records);
		if(qp.isDirectQuery()) {
			System.out.println("Using index for record retreival.");
		}
		output.forEach(System.out::println);
		System.out.println("Records selected: " + records.size());
	}
	
	/**
	 * Retrieves the queried records from the given database <code>db</code>.
	 * @param qp - QueryParser to use for getting query conditions
	 * @param db - StudentData from which to retrieve records
	 * @return List of StudentRecords
	 * note : the method is package private so that it can be tested
	 */
	static List<StudentRecord> getQueriedRecords(QueryParser qp, StudentDatabase db) {
		List<StudentRecord> records;
		
		if(qp.isDirectQuery()) {
			records = new ArrayList<>();
			StudentRecord queriedRecord = db.forJMBAG(qp.getQueriedJMBAG());
			if(queriedRecord != null) {
				records.add(queriedRecord);
			}
		} else {
			QueryFilter filter = new QueryFilter(qp.getQuery());
			records = db.filter(filter);
		}
		
		return records;
	}
	
	/**
	 * Formats the given <code>records</code> to a List of strings
	 * where the first and last are border lines of the table.
	 * @param records
	 * @return List<String>
	 */
	private static List<String> formatQueryOutput(List<StudentRecord> records){
		List<String> formatted = new ArrayList<String>();
		if(records.size() == 0) {
			return formatted;
		}
		//the maximum length of a last name of the given records
		int maxLN = records.stream()
				.mapToInt( r -> r.getLastName().length())
				.max().getAsInt();
		//the maximum length of a first name of the given records
		int maxFN = records.stream()
				 .mapToInt( r -> r.getFirstName().length())
				 .max().getAsInt();
		
		String borderLine = constructBorderLine(records, maxLN, maxFN);
		formatted.add(borderLine);
		for(StudentRecord r : records) {
			StringBuilder sb = new StringBuilder();
			//append the jmbag and last name
			sb.append(String.format("| %s | %s", r.getJmbag(), r.getLastName()));
			//pad the length difference with spaces
			for( int i = 0 ; i<maxLN-r.getLastName().length() ; ++i) {
				sb.append(' ');
			}
			//append the first name
			sb.append(String.format(" | %s", r.getFirstName()));
			//pad the length difference with spaces
			for( int i = 0 ; i<maxFN-r.getFirstName().length() ; ++i) {
				sb.append(' ');
			}
			sb.append(String.format(" | %d |", r.getGrade()));
			formatted.add(sb.toString());
		}
		formatted.add(borderLine);
		
		return formatted;
	}
	
	/**
	 * Constructs a border line based on the maximum length of the lastName
	 * and firstName from the given <code>records</code>.
	 * @param records
	 * @return String representing the border of the formatted table for output
	 */
	private static String constructBorderLine(List<StudentRecord> records,
												int maxLastName, int maxFirstName) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("+============+=");
		for(int i = 0 ; i<maxLastName ; ++i) {
			sb.append('=');
		}
		sb.append("=+=");
		for(int i = 0 ; i<maxFirstName ; ++i) {
			sb.append('=');
		}
		sb.append("=+===+");
		return sb.toString();
	}
	
}
