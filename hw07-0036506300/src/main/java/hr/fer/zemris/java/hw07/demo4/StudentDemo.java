package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDemo {

	public static void main(String[] args) {
		List<StudentRecord> records = readRecords(Paths.get("./studenti.txt"));
		int i = 1;
		// 1.
		long broj = vratiBodovaViseOd25(records);
		print(i++);
		System.out.println(broj);
		// 2.
		long broj5 = vratiBrojOdlikasa(records);
		print(i++);
		System.out.println(broj5);
		// 3.
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		print(i++);
		odlikasi.forEach(System.out::println);
		// 4.
		List<StudentRecord> odlikasiSortirani = vratiSortiranuListuOdlikasa(records);
		print(i++);
		odlikasiSortirani.forEach(System.out::println);
		// 5.
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		print(i++);
		nepolozeniJMBAGovi.forEach(System.out::println);
		// 6.
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		print(i++);
		mapaPoOcjenama.forEach( (o, l) -> {System.out.format("Ocjena %d (%d) :%n", o, l.size());
											l.forEach(System.out::println);
										  });
		// 7.
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		print(i++);
		mapaPoOcjenama2.forEach( (o, b) -> System.out.format("Ocjena %d : %d studenata.%n", o, b) );
		// 8.
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		print(i++);
		prolazNeprolaz.forEach( (b, l) -> {System.out.format("Prošli = %b (%d) :%n", b, l.size());
											l.forEach(System.out::println);
										  });
		
	}
	
	private static void print(int i) {
		System.out.format("Zadatak %d%n=========%n", i);
	}

	public static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream()
					  .filter( r -> r.getMIscore() + r.getZIscore() + r.getLabScore() > 25)
					  .count();
	}
	
	public static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(r -> r.getGrade() == 5)
				.count();
	}
	
	public static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records){
		 return records.stream()
		  .filter(r -> r.getGrade() == 5)
		  .collect(Collectors.toList());
	}
	
	public static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records){
		Comparator<StudentRecord> comp = (r1, r2) -> Double.compare(
				r2.getMIscore() + r2.getZIscore() + r2.getLabScore(),
				r1.getMIscore() + r1.getZIscore() + r1.getLabScore()
				);
		
		 return records.stream()
		  .filter(r -> r.getGrade() == 5)
		  .sorted(comp)
		  .collect(Collectors.toList());
	}
	
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream()
					  .filter( r -> r.getGrade() == 1)
					  .map(r -> r.getJmbag())
					  .collect(Collectors.toList());
	}
	
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream()
					  .collect(Collectors.groupingBy(StudentRecord::getGrade));
	}
	
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream()
				      .collect(Collectors.toMap(
				    		  StudentRecord::getGrade,  //key mapper
				    		  r -> 1,					//value mapper
				    		  (i1, i2) -> i1 + 1		//if there is another student with the same grade
				    		  ));						//increase the value by one
	}
	
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream()
					  .collect(Collectors.partitioningBy( r -> r.getGrade()>1));
	}

	/**
	 * Reads StudentRecords from the file from given <code>path</code>.
	 * @param path
	 * @return list of student records
	 */
	private static List<StudentRecord> readRecords(Path path) {
		try {
			List<String> lines = Files.readAllLines(path);
			List<StudentRecord> records = convert(lines);
			return records;
			
		} catch(IOException | NumberFormatException e) {
			System.err.println(e.getMessage());
			System.exit(1);
			return null; //TODO konze kak ovo nije unreachable code
		}
	}
	
	/**
	 * Reads StudentRecord properties from each line and creates a StudentRecord for each line.
	 * @param lines
	 * @return list of student records
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<StudentRecord>();

		for(String l : lines) {
			if(l.length() == 0) continue;
			String[] line = l.split("\t");
			String jmbag = line[0];
			String lastName = line[1];
			String firstName = line[2];
			double mIscore = Double.parseDouble(line[3]);
			double zIscore = Double.parseDouble(line[4]);
			double labScore = Double.parseDouble(line[5]);
			int grade = Integer.parseInt(line[6]);
			records.add(
					new StudentRecord(jmbag, lastName, firstName, mIscore, zIscore, labScore, grade)
					);
		}
		return records;
	}
	
}
