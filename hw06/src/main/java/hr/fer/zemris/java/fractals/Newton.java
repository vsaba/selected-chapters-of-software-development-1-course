package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * A class which calculates and shows a visual representation of the Newton-Raphson fractal
 * @author Vito Sabalic
 *
 */
public class Newton {
	
	/**
	 * Loads the roots for the ComplexPolynomial and starts the calculation of the Newton-Raphson fractal
	 * @param args The provided arguments
	 */
	public static void main(String[] args) {
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		int index = 1;
		
		Scanner sc = new Scanner(System.in);
		
		List<Complex> roots = new ArrayList<>();
		String line = new String();
		
		while(true) {
			
			System.out.printf("Root " + index + "> ");
			line = sc.nextLine();
			
			if(line.equalsIgnoreCase("done")) {
				if(index <= 2) {
					System.out.println("Please enter at least two roots!");
					continue;
				}
				
				System.out.println("Image of fractal will appear shortly. Thank you.");
				break;
			}
			
			if(line.equalsIgnoreCase("exit")) {
				System.out.println("Goodbye!");
				System.exit(0);
			}
			
			roots.add(Complex.parse(line));
			index++;
		}
		
		sc.close();
		
		Complex[] complexArray = new Complex[roots.size()];
		
		for(int i = 0; i < complexArray.length; i++) {
			
			complexArray[i] = roots.get(i);
		
		}
		
		ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, complexArray);
		
		FractalViewer.show(new MyProducer(rootedPolynomial));
		
	}
	
	
	
	/**
	 * An implementation of the {@link IFractalProducer} interface.
	 * Calculates the necessary values for the visualisation of the Newton-Raphson fractal
	 * @author Vito Sabalic
	 *
	 */
	public static class MyProducer implements IFractalProducer{

		
		/**
		 * The rooted version of the provided polynomial
		 */
		private ComplexRootedPolynomial roots;
		
		/**
		 * A constructor which assigns the current {@link ComplexRootedPolynomial} to the provided one
		 * @param roots
		 */
		public MyProducer(ComplexRootedPolynomial roots) {
			
			this.roots = roots;
			
		}
		
		
		/**
		 * Calculates all the necessary values for the visualisation of the Newton-Raphson fractal based on the current {@link ComplexRootedPolynomial}
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {
			
			
			int maxIter = 16 * 16 * 16;
			double convergenceThreshold = 0.001;
			
			int offset = 0;
			
			short[] data = new short[width * height];
			
			ComplexPolynomial polynomial = roots.toComplexPolynom();
			
			ComplexPolynomial derived = polynomial.derive();
			
			for(int y = 0; y < height; y++) {
				
				if(cancel.get()) {
					break;
				}
				
				for(int x = 0; x < width; x++) {
					
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height - 1.0) * (imMax - imMin) + imMin;
					
					Complex zn = new Complex(cre, cim);
					int iter = 0;
					double module = 0;
					
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						
						Complex znold = zn;
						
						Complex fraction = numerator.divide(denominator);
						zn = znold.sub(fraction);
						module = zn.sub(znold).module();
						
						iter++;
						
					} while (module > convergenceThreshold && iter < maxIter);
					
					int index = roots.indexOfClosestRootFor(zn, 0.002);
					data[offset++] = (short) (index + 1);
					
				}
				
			}
			
			observer.acceptResult(data, (short) (polynomial.order()+1), requestNo);
			
		}
			
	}

}
