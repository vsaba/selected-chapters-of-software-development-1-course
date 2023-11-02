package hr.fer.oprpp1.hw02;

import java.nio.file.Files;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {

	public static void main(String[] args) throws IOException {
		String docBody = "";
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = (DocumentNode)parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = (DocumentNode)parser2.getDocumentNode();
		String newDocumentBody = document2.toString();
		boolean same = document.equals(document2); // ==> "same" must be true
		System.out.println(same);

	}
}
