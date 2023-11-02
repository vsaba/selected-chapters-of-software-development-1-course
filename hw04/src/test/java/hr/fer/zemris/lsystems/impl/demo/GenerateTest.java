package hr.fer.zemris.lsystems.impl.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class GenerateTest {
	
	@Test
	public void testGenerateLevel0() {
		LSystemBuilderImpl impl = new LSystemBuilderImpl();
		
		LSystem s = impl.registerProduction('F', "F+F--F+F").setAxiom("F").build();
		
		String str = s.generate(0);
		
		assertEquals("F", str);
		
	}
	
	@Test
	public void testGenerateLevel1() {
		LSystemBuilderImpl impl = new LSystemBuilderImpl();
		
		LSystem s = impl.registerProduction('F', "F+F--F+F").setAxiom("F").build();
		
		String str = s.generate(1);
		
		assertEquals("F+F--F+F", str);
		
	}
	
	@Test
	public void testGenerateLevel2() {
		LSystemBuilderImpl impl = new LSystemBuilderImpl();
		
		LSystem s = impl.registerProduction('F', "F+F--F+F").setAxiom("F").build();
		
		String str = s.generate(2);
		
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", str);
		
	}
	

}
