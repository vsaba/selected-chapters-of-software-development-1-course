package hr.fer.oprpp1.hw04.db.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw04.db.IFilter;
import hr.fer.oprpp1.hw04.db.StudentDatabase;
import hr.fer.oprpp1.hw04.db.StudentRecord;

public class StudentDatabaseTest  {
	
	@Test
	public void testForJmbag() throws IOException{
		List<String> s = Files.readAllLines(Paths.get("src/test/resources/database/database.txt"), StandardCharsets.UTF_8);
		
		StudentDatabase db = new StudentDatabase(s);
		StudentRecord record = new StudentRecord("0000000019", "Slaven"	,"Gvardijan", 4);
		
		assertEquals(record, db.forJMBAG("0000000019"));
	}
	
	@Test
	public void IFilterFalse() throws IOException{
		List<String> s = Files.readAllLines(Paths.get("src/test/resources/database/database.txt"), StandardCharsets.UTF_8);
		
		StudentDatabase db = new StudentDatabase(s);
		IFilter i1 = record -> false;
		
		assertEquals(0, db.filter(i1).size());
		
	}
	
	@Test
	public void IFilterTrue() throws IOException{
		List<String> s = Files.readAllLines(Paths.get("src/test/resources/database/database.txt"), StandardCharsets.UTF_8);
		
		StudentDatabase db = new StudentDatabase(s);
		IFilter i1 = record -> true;
		
		assertEquals(63, db.filter(i1).size());
	}

}
