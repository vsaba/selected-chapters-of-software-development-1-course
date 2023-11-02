package hr.fer.oprpp1.hw04.db.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw04.db.ComparisonOperators;

public class ComparisonOperatorsTest {
	
	@Test
	public void testLess() {
		String s1 = "AAAA";
		String s2 = "AAA";
		
		assertTrue(ComparisonOperators.LESS.satisfied(s2, s1));
		
	}
	
	@Test
	public void testLessOrEquals() {
		String s1 = "AAAA";
		String s2 = "AAAA";
		
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied(s2, s1));
		
	}
	
	@Test
	public void testGreater() {
		String s1 = "AAAA";
		String s2 = "AAA";
		
		assertTrue(ComparisonOperators.GREATER.satisfied(s1, s2));
		
	}
	
	@Test
	public void testGreaterOrEquals() {
		String s1 = "AAAA";
		String s2 = "AAAA";
		
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied(s2, s1));
		
	}
	
	@Test
	public void testEquals() {
		String s1 = "AAAA";
		String s2 = "AAAA";
		
		assertTrue(ComparisonOperators.EQUALS.satisfied(s2, s1));
	}
	
	@Test
	public void testNotEquals() {
		String s1 = "AAAA";
		String s2 = "AAA";
		
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied(s2, s1));
		
	}
	
	@Test
	public void testLike() {
		String s1 = "AAA";
		String s2 = "AA**A";
		
		assertTrue(!(ComparisonOperators.LIKE.satisfied(s2, s1)));
		
		
	}

}
