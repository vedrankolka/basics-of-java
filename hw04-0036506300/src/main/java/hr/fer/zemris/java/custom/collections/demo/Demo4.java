package hr.fer.zemris.java.custom.collections.demo;

import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

public class Demo4 {

	public static void main(String[] args) {
		
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		try {
			while(iter.hasNext()) {
				SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
				if(pair.getKey().equals("Ivana")) {
					iter.remove();
					iter.remove();
				}
			}
		} catch(IllegalStateException e) {
			System.out.println("Cannot remove two times in a row!");
		}
		
	}
	
}
