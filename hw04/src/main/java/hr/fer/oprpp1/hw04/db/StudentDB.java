package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * An execution of the Student Database implementation
 * @author Vito Sabalic
 *
 */
public class StudentDB {

	public static void main(String[] args) throws IOException{
		List<String> s = Files.readAllLines(Paths.get("src/test/resources/database/database.txt"), StandardCharsets.UTF_8);
		
		StudentDatabase db = new StudentDatabase(s);
		
		Scanner sc = new Scanner(System.in);
		String query;
		while(true) {
			if(sc.hasNext()) {
				query = sc.nextLine();
				if(query.equalsIgnoreCase("exit")) {
					System.out.println("Goodbye!");
					break;
				}
				
				try {
					QueryParser parser = new QueryParser(query);
					if(parser.isDirectQuery()) {
						
						
						StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
						
						if(r == null) {
							System.out.println("Records selected: 0");
						}
						else {
							System.out.println(r);
							System.out.println("Records selected: 1");
						}
						
					} else {
						
						List<StudentRecord> records = db.filter(new QueryFilter(parser.getQuery()));
						if(records.size() == 0) {
							System.out.println("Records selected: 0");
						}
						else {
							ispis(records);
							System.out.println("Records selected: " + records.size());
							
						}
					}
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
		}
		
		sc.close();

	}
	
	private static void ispis(List<StudentRecord> records) {
	
		int longestName = 0;
		int longestLastName = 0;
		
		for(StudentRecord r: records) {
			if(r.getFirstName().length() > longestName) {
				longestName = r.getFirstName().length();
			}
			
			if(r.getLastName().length() > longestLastName) {
				longestLastName = r.getLastName().length();
			}
			
		}
		
		longestName += 2;
		longestLastName += 2;
		
		String frame = "+============+";
		
		for(int i = 0; i < longestName; i++) {
			frame += "=";
		}
		frame += "+";
		
		for(int i = 0; i < longestLastName; i++) {
			frame += "=";
		}
		frame += "+";
		
		frame += "===+";
	

		System.out.println(frame);
		for(StudentRecord r: records) {
			String output = "| " + r.getJmbag() + " | ";
			output += r.getLastName();
			
			int numberOfSpaces = longestLastName - r.getLastName().length() - 1;
			
			for(int i = 0; i < numberOfSpaces; i++) {
				output += " ";
			}
			
			output += "| " + r.getFirstName();
			
			numberOfSpaces = longestName - r.getFirstName().length()-1;
			
			for(int i = 0; i < numberOfSpaces; i++) {
				output += " ";
			}
			
			output += "| " + r.getMark() + " |";
			
			System.out.println(output);
			
		} 
		
		System.out.println(frame);
	}
	

	
	
	
	
	
	
	
}
