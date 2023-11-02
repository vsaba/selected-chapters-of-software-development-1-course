package hr.fer.oprpp1.hw02.demo;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptParserDemo {

	@Test
	public void test1() {
		String docBody = readExample(1);
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = (DocumentNode)parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = (DocumentNode)parser2.getDocumentNode();
		String newDocumentBody = document2.toString();
		assertTrue(document.equals(document2));
	}
	
	@Test
	public void test2() {
		String docBody = readExample(2);
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = (DocumentNode)parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = (DocumentNode)parser2.getDocumentNode();
		String newDocumentBody = document2.toString();
		assertTrue(document.equals(document2));
	}
	
	@Test
	public void test3() {
		String docBody = readExample(3);
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = (DocumentNode)parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = (DocumentNode)parser2.getDocumentNode();
		String newDocumentBody = document2.toString();
		assertTrue(document.equals(document2));
	}
	
	@Test
	public void test4() {
		String docBody = readExample(4);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void test5() {
		String docBody = readExample(5);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void test6() {
		String docBody = readExample(6);
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = (DocumentNode)parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = (DocumentNode)parser2.getDocumentNode();
		String newDocumentBody = document2.toString();
		assertTrue(document.equals(document2));
	}
	
	@Test
	public void test7() {
		String docBody = readExample(7);
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = (DocumentNode)parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = (DocumentNode)parser2.getDocumentNode();
		String newDocumentBody = document2.toString();
		assertTrue(document.equals(document2));
	}
	
	@Test
	public void test8() {
		String docBody = readExample(8);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void test9() {
		String docBody = readExample(9);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	
	private String readExample(int n) {
		  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
		    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
		    byte[] data = is.readAllBytes();
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		  } catch(IOException ex) {
		    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		  }
		}
}
