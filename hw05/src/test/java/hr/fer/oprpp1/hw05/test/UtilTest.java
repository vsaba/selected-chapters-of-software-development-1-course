package hr.fer.oprpp1.hw05.test;




import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw05.Util;

public class UtilTest {
	
	@Test
	public void test1() {
		
		byte[] b = {1, -82, 34};
		
		byte[] b1 = Util.hextobyte("01ae22");
		
		
		assertEquals(b.length, b1.length);
		
		for(int i = 0; i < b.length; i++) {
			assertEquals(b[i], b1[i]);
		}
		
	}
	
	@Test
	public void test2() {
		byte[] b = {1, -30, 64};
		
		byte[] b1 = Util.hextobyte("01e240");
		
		
		assertEquals(b.length, b1.length);
		
		for(int i = 0; i < b.length; i++) {
			assertEquals(b[i], b1[i]);
		}
	}
	
	@Test
	public void test3() {
		
		assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
		
	}
	
	@Test
	public void test4() {
		
		assertEquals("01e240", Util.bytetohex(new byte[] {1, -30, 64}));
		
	}

}
