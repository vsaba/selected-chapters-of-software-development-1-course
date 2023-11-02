package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class which stores and handles different {@link StudentRecord} instances
 * @author Vito Sabalic
 *
 */
public class StudentDatabase {
	
	/**
	 * A list of assigned records
	 */
	List<StudentRecord> records;
	
	
	/**
	 * A list of assigned indexes based on jmbag
	 */
	Map<String, Integer> indexes;
	
	/**
	 * A constructor which assigns records to the records variable and assigns indexes based on jmbag to the indexes varaible
	 * @param lines The StudentRecords to be added
	 */
	public StudentDatabase(List<String> lines) {
		
		records = new ArrayList<>();
		indexes = new HashMap<>();
		
		
		int index = 0;
		
		for(String line: lines) {
			line = line.trim();
			
			String jmbag = parseJmbag(line);
			line = line.replaceFirst(jmbag, "");
			line = line.trim();
			

			
			String lastName = parseLastName(line);
			line = line.replaceFirst(lastName, "");
			line = line.trim();
			
			String firstName = parseFirstName(line);
			line = line.replaceFirst(firstName, "");
			line = line.trim();
			
			if(Character.isLetter(line.charAt(0))) {
				String lastName2 = parseLastName(line);
				line = line.replaceFirst(lastName2, "");
				line = line.trim();
				
				String tmp = firstName;
				firstName = lastName2;
				lastName2 = tmp;
				
				lastName = lastName + " " + lastName2;
			}
			
			int mark = parseMark(line);
			
			if(mark < 1 || mark > 5) {
				throw new IllegalArgumentException("Mark cannot be smaller than 1 or larger than 5");
			}
			
			
			if(indexes.containsKey(jmbag)) {
				throw new IllegalArgumentException("Student already exists");
			}
			
			indexes.put(jmbag, index++);
			records.add(new StudentRecord(jmbag, firstName, lastName, mark));
			
			
		}
	}
	
	/**
	 * Returns a student record based on the provided jmbag
	 * @param jmbag The provided jmbag
	 * @return if a record is found, returns it, otherwise returns null
	 */
	public StudentRecord forJMBAG(String jmbag) {
		
		if(!(indexes.containsKey(jmbag))) {
			return null;
		}
		
		return records.get(indexes.get(jmbag));
	}
	
	/**
	 * Filters student records based on the provided filter
	 * @param filter The provided filter
	 * @return returns a list of students which passed the filter test
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> toReturn = new ArrayList<>();
		
		for(StudentRecord record: records) {
			if(filter.accepts(record)) {
				toReturn.add(record);
			}
		}
		
		return toReturn;
	}
	
	
	
	/**
	 * Finds a jmbag in the given string
	 * @param s The given string
	 * @return Returns a string of the found jmbag
	 */
	private String parseJmbag(String s) {
		char[] data = s.toCharArray();
		int index = 0;
		
		while(index < data.length && Character.isDigit(data[index])) {
			index++;
		}
		
		return new String(data, 0, index);
	}
	
	/**
	 * Finds the first name in the string
	 * @param s The given string
	 * @return Returns a string of the found first name
	 */
	private String parseFirstName(String s) {
		char[] data = s.toCharArray();
		int index = 0;
		
		while(index < data.length && Character.isLetter(data[index])) {
			index++;
		}
		
		return new String(data, 0, index);
	}
	
	/**
	 * Finds the last name in the string
	 * @param s The given string
	 * @return Returns a string of the found last name
	 */
	private String parseLastName(String s) {
		char[] data = s.toCharArray();
		int index = 0;
		
		while(index < data.length && Character.isLetter(data[index]) || data[index] == '-') {
			index++;
		}
		
		return new String(data, 0, index);
	}
	
	/**
	 * Finds the mark in the string
	 * @param s The given string
	 * @return Returns a string of the found mark
	 */
	private int parseMark(String s) {
		if(!(Character.isDigit(s.toCharArray()[0]))){
			throw new IllegalArgumentException();
		}
		
		return Integer.parseInt(s);
	}

}
