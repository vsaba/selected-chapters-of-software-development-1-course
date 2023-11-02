
package hr.fer.oprpp1.hw01;



/**
 * An implementation of a complex number with all the basic and necessary methods to use complex numbers.
 * Uses two double variables which represent the real and imaginary part of the complex number, respectively.
 * @author Vito Sabalic
 *
 */
public class ComplexNumber {
	
	private double real;
	private double imaginary;
	
	
	/**A basic constructor which associates values from the argument to the respective private variable
	 * @param real the real part of the complex number
	 * @param imaginary the imaginary part of the complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		
		this.real = real;
		this.imaginary = imaginary;
		
	}
	
	
	/**
	 * Creates an instance of the class ComplexNumber while using only the real part of the complex number.
	 * The imaginary part is set to 0
	 * @param real the real part of the complex number
	 * @return returns the new ComplexNumber instance
	 */
	public static ComplexNumber fromReal(double real) {
		
		return new ComplexNumber(real, 0.0);
	}
	
	/**
	 * Creates an instance of the class ComplexNumber while using only the imaginary part of the complex number.
	 * The real part is set to 0
	 * @param imaginary the imaginary part of the complex number
	 * @return returns the new ComplexNumber instance
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		
		return new ComplexNumber(0.0, imaginary);
		
	}
	
	/**
	 * Creates an instance of the class ComplexNumber while using the magnitude and the angle of the complex number.
	 * @param magnitude the magnitude of the complex number
	 * @param angle the angle of the complex number
	 * @return returns the new ComplexNumber instance
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
		
	}
	
	/**
	 * Creates an instance of the class ComplexNumber from the given string.
	 * It parses the string and allocates the values to the appropriate variables.
	 * @param s the string to be parsed
	 * @return returns the new ComplexNumber instance
	 */
	public static ComplexNumber parse(String s) {
		
		s = s.replaceAll("\\s", "");
		
		String re = "";
		String im = "";
		if((s.contains("+") && s.lastIndexOf("+") > 0) || (s.contains("-") && s.lastIndexOf("-") > 0)) {
			

			
			s = s.replaceAll("i", "");
			
			if(s.lastIndexOf("+") > 0) {
				re = s.substring(0, s.lastIndexOf("+"));
				im = s.substring(s.lastIndexOf("+"), s.length());
				
			}
			
			else if(s.lastIndexOf("-") > 0) {
				re = s.substring(0, s.lastIndexOf("-"));
				im = s.substring(s.lastIndexOf("-"), s.length());
			}
			
			if(im.length() == 1) {
				im = im.concat("1");
			}
			
			double newRe = Double.parseDouble(re);
			double newIm = Double.parseDouble(im);
			
			return new ComplexNumber(newRe, newIm);
			
		}
		
		else {
			if(s.endsWith("i")) {				
				im = s.replaceAll("i", "");
				if(im.length() == 1 && (im.contains("+") || im.contains("-"))) {
					im = im.concat("1");
				}
				else if(im.length() == 0) {
					im = "1";
				}
				
				return new ComplexNumber(0, Double.parseDouble(im));
				
			}
			else {
				re = s;
				
				return new ComplexNumber(Double.parseDouble(re), 0);
				
			}
		}
	}

	/**
	 * @return returns the real part of the complex number
	 */
	public double getReal() {
		return real;
	}
	
	
	
	/**
	 * @return returns the imaginary part of the complex number
	 */
	public double getImaginary() {
		return imaginary;
	}

	
	/**
	 * @return returns the magnitude of the complex number
	 */
	public double getMagnitude() {
		
		return Math.sqrt((Math.pow(real, 2)) + Math.pow(imaginary, 2));
		
	}
	
	
	/**
	 * @return returns the appropriate angle of the complex number, 
	 * while taking into account the position of the complex number in the Cartesian coordinate system
	 */
	public double getAngle() {
		double angle = Math.atan2(imaginary, real);
		
		if(angle < 0) {
			return angle + 2*Math.PI;
		}
		
		return angle;
	}
	
	
	/**
	 * Adds the given instance of the ComplexNumber to this instance of the ComplexNumber
	 * @param c the instance of ComplexNumber to be added
	 * @return returns a new instance of ComplexNumber
	 */
	public ComplexNumber add(ComplexNumber c) {
		
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
		
	}
	
	
	/**
	 * Subtracts the given instance of the ComplexNumber from this instance of the ComplexNumber
	 * @param c the instance of ComplexNumber to subtract
	 * @return returns a new instance of ComplexNumber
	 */
	public ComplexNumber sub(ComplexNumber c) {
		
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
		
	}
	
	
	/**
	 * Multiplies the given instance of the ComplexNumber with this instance of the ComplexNumber
	 * @param c the instance of ComplexNumber which multiplies
	 * @return returns a new instance of ComplexNumber
	 */
	public ComplexNumber mul(ComplexNumber c) {
		
		double newReal = (this.real * c.real) - (this.imaginary * c.imaginary);
		double newImaginary = (this.real * c.imaginary) + (this.imaginary * c.real);
		
		return new ComplexNumber(newReal, newImaginary);
	}
	
	
	/**
	 * Divides this instance of the ComplexNumber with the given instance of ComplexNumber
	 * @param c the ComplexNumber which divides
	 * @return returns anew instance of ComplexNumber
	 */
	public ComplexNumber div(ComplexNumber c) {
		
		double newMagnitude = this.getMagnitude() / c.getMagnitude();
		double newAngle = this.getAngle() - c.getAngle();
		
		double newReal = newMagnitude * Math.cos(newAngle);
		double newImaginary = newMagnitude * Math.sin(newAngle);
		
		return new ComplexNumber(newReal, newImaginary);
		
	}
	
	
	/**
	 * calculates the n-th power of this instance of the ComplexNumber, n being the provided argument
	 * @param n the number of the power
	 * @return returns a new instance of ComplexNumber
	 */
	public ComplexNumber power(int n) {
		
		double newMagnitude = Math.pow(this.getMagnitude(), n);
		
		double newReal = newMagnitude * Math.cos(n * this.getAngle());
		double newImaginary = newMagnitude * Math.sin(n * this.getAngle());
		
		return new ComplexNumber(newReal, newImaginary);
		
	}
	
	
	/**
	 * Calculates the n-th root of this instance of the ComplexNumber, n being the provided argument
	 * @param n the number of the root
	 * @return returns a field of ComplexNumbers which represent the root of the initial instance
	 */
	public ComplexNumber[] root(int n) {
		
		ComplexNumber[] roots = new ComplexNumber[n];
		
		double newMagnitude = Math.pow(this.getMagnitude(), 1/(float)n);
		
		double newReal;
		double newImaginary;
		
		for(int i = 0; i < n; i++) {
			
			newReal = newMagnitude * Math.cos((this.getAngle() + 2 * i * Math.PI) / n);
			newImaginary = newMagnitude * Math.sin((this.getAngle() + 2 * i * Math.PI) / n);
			
			roots[i] = new ComplexNumber(newReal, newImaginary);
			
		}
		
		return roots;
		
	}
	
	
	@Override
	public String toString() {
		
		String imaginaryString = new String();
		
		if(imaginary == 1) {
			imaginaryString = "i";
		}
		else if(imaginary == -1) {
			imaginaryString = "-i";
		}
		else {
			imaginaryString = String.valueOf(imaginary) + "i";
		}
		
		
		if(real == 0) {
			return imaginaryString;
		}
		
		else if(imaginary == 0) {
			
			return String.valueOf(real);
		
		}
		
		else {
			if(imaginary < 0) {
				//double absImaginary = Math.abs(imaginary);
				return String.valueOf(real) + " - " + String.valueOf(Math.abs(imaginary)) + "i";
			}
		
			return String.valueOf(real) + " + " + String.valueOf(Math.abs(imaginary)) + "i";
		
		}
	}

}
