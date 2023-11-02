package hr.fer.oprpp1.hw01.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw01.ComplexNumber;

public class ComplexNumberTest {

	@Test
	public void constructorTest() {
		ComplexNumber num = new ComplexNumber(1, 1);
		
		assertEquals(1, num.getReal());
		assertEquals(1, num.getImaginary());
	}
	
	@Test
	public void fromRealTest() {
		
		ComplexNumber num = ComplexNumber.fromReal(1);
		
		assertEquals(1, num.getReal());
		assertEquals(0, num.getImaginary());
	}
	
	@Test
	public void fromImaginaryTest() {
		
		ComplexNumber num = ComplexNumber.fromImaginary(1);
		
		assertEquals(0, num.getReal());
		assertEquals(1, num.getImaginary());
	}
	
	
	@Test
	public void fromMagnitudeAndAngleTest() {
		ComplexNumber num = ComplexNumber.fromMagnitudeAndAngle(1, Math.PI /2);
		
		assertEquals(0, Math.round(num.getReal()));
		assertEquals(1, num.getImaginary());
		
	}
	
	@Test
	public void parseTests() {
		
		String s;
		
		s = "1 + 3.15i";
		ComplexNumber num = ComplexNumber.parse(s);
		assertEquals(1, num.getReal());
		assertEquals(3.15, num.getImaginary());
		
		s = "+1";
		num = ComplexNumber.parse(s);
		assertEquals(1, num.getReal());
		assertEquals(0, num.getImaginary());
		
		s = "-i";
		num = ComplexNumber.parse(s);
		assertEquals(0, num.getReal());
		assertEquals(-1, num.getImaginary());
		
		s = "-1 + i";
		num = ComplexNumber.parse(s);
		assertEquals(-1, num.getReal());
		assertEquals(1, num.getImaginary());
	}
	
	@Test
	public void GetMagnitudeAndGetAngleTest() {
		ComplexNumber num = new ComplexNumber(1, 0);
		
		assertEquals(1, num.getMagnitude());
		assertEquals(Math.atan2(0, 1), num.getAngle());
	}
	
	@Test
	public void addTests(){
		ComplexNumber num = new ComplexNumber(1, 1);
		ComplexNumber num1 = new ComplexNumber(-1, -1);
		
		ComplexNumber num2 = num.add(num1);
		
		assertEquals(0, num2.getReal());
		assertEquals(0, num2.getImaginary());
	}
	
	@Test
	public void subTests() {
		ComplexNumber num = new ComplexNumber(1, 1);
		ComplexNumber num1 = new ComplexNumber(-1, -1);
		
		ComplexNumber num2 = num.sub(num1);
		
		assertEquals(2, num2.getReal());
		assertEquals(2, num2.getImaginary());
		
	}
	
	@Test
	public void mulTests() {
		
		ComplexNumber num = new ComplexNumber(2, -1);
		ComplexNumber num1 = new ComplexNumber(3, 4);
		
		ComplexNumber num2 = num.mul(num1);
		
		assertEquals(10, num2.getReal());
		assertEquals(5, num2.getImaginary());
		
	}
	
	@Test
	public void divTests() {
		ComplexNumber num = new ComplexNumber(2, 0);
		ComplexNumber num1 = new ComplexNumber(1, 1);
		
		ComplexNumber num2 = num.div(num1);

		
		assertEquals(1, num2.getReal());
		assertEquals(-1, Math.round(num2.getImaginary()));
		
	}
	
	@Test
	public void powerTest() {
		ComplexNumber num = new ComplexNumber(0, 1);
		
		num = num.power(2);
		
		assertEquals(-1, num.getReal());
		assertEquals(0, Math.round(num.getImaginary()));
		
		
	}
	
	@Test
	public void rootTest() {
		ComplexNumber num = new ComplexNumber(0, 2);
		
		ComplexNumber[] expected = num.root(2);
		
		assertEquals(1, Math.round(expected[0].getReal() * 100) / 100);
		assertEquals(1, Math.round(expected[0].getImaginary() * 100) / 100);
		
		assertEquals(-1, Math.round(expected[1].getReal() * 100) / 100);
		assertEquals(-1, Math.round(expected[1].getImaginary() * 100) / 100);
		

	}
	
	@Test
	public void toStringTest() {
		ComplexNumber num1 = new ComplexNumber(1, 2);
		ComplexNumber num2 = new ComplexNumber(12, -2);
		ComplexNumber num3 = new ComplexNumber(0, -1);
		
		assertEquals("1.0 + 2.0i", num1.toString());
		assertEquals("12.0 - 2.0i", num2.toString());
		assertEquals("-i", num3.toString());
	}
	
	
	
	
}
