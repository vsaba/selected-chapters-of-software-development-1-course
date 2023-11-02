package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * A class which calculates and shows a visual representation of the Newton-Raphson fractal. 
 * It takes advantage of the parallelization technology
 * @author Vito Sabalic
 *
 */
public class NewtonParallel {
	
	/**
	 * Loads the roots for the ComplexPolynomial and starts the calculation of the Newton-Raphson
	 * fractal based on the provided arguments
	 * @param args The provided arguments
	 */
	public static void main(String[] args) {
		
		int[] workersAndThreads = new int[2];
		
		if(args.length == 0) {
			
			workersAndThreads[0] = Runtime.getRuntime().availableProcessors();
			workersAndThreads[1] = 4 * workersAndThreads[0];
			
		}
		else if(args.length == 2){
			
			try{
				
				workersAndThreads = extractArgs(args);
				
				if(workersAndThreads[0] <= 0 || workersAndThreads[0] > Runtime.getRuntime().availableProcessors()) {
					workersAndThreads[0] = Runtime.getRuntime().availableProcessors();
				}
				
				if(workersAndThreads[1] <= 0 ) {
					workersAndThreads[1] = 4 * Runtime.getRuntime().availableProcessors();
				}
			}
			catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}
		else {
			System.out.println("Please enter exactly two arguments!");
			System.exit(0);
		}
		
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		int index = 1;
		
		Scanner sc = new Scanner(System.in);
		
		List<Complex> roots = new ArrayList<>();
		String line = new String();
		
		while(true) {
			
			System.out.printf("Root " + index + "> ");
			line = sc.nextLine();
			
			if(line.isEmpty()) {
				
				System.out.println("Please enter a root!");
				continue;
				
			}
			
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
		
		FractalViewer.show(new MyProducer(rootedPolynomial, workersAndThreads));
		
	}
	
	
	/**
	 * Extracts the values from the provided arguments
	 * @param args The provided arguments
	 * @return Returns an array of the values which were extracted from the provided arguments
	 */
	private static int[] extractArgs(String[] args) {
		
		int[] result = new int[2];
		
		String[] pom = args[0].split("=");
		
		if(!(pom[0].equalsIgnoreCase("--workers") || pom[0].equalsIgnoreCase("--w"))) {
			throw new IllegalArgumentException("Please enter a valid argument!");
		}
		
		result[0] = Integer.parseInt(pom[1]);
		
		String[] pom1 = args[1].split("=");
		
		if(!(pom1[0].equalsIgnoreCase("--tracks") || pom1[0].equalsIgnoreCase("--t"))) {
			throw new IllegalArgumentException("Please enter a valid argument!");
		}
		
		result[1] = Integer.parseInt(pom1[1]);
				
		
		return result;
	}
	
	
	/**
	 * Represents the work a thread must do.
	 * Implements the {@link Runnable} interface
	 * @author Vito Sabalic
	 *
	 */
	public static class CalculationJob implements Runnable {
		
		/**
		 * The minimal real value
		 */
		double reMin;
		
		/**
		 * The maximum real value
		 */
		double reMax;
		
		/**
		 * The minimal imaginary value
		 */
		double imMin;
		
		/**
		 * The maximum imaginary value
		 */
		double imMax;
		
		/**
		 * The width of the window
		 */
		int width;
		
		/**
		 * The height of the window 
		 */
		int height;
		
		/**
		 * The minimal value on the y-axis
		 */
		int yMin;
		
		/**
		 * The maximum value on the y-axis
		 */
		int yMax;
		
		/**
		 * The maximum number of iterations
		 */
		int m;
		
		/**
		 * The field with the calculated data to be drawn onto the window
		 */
		short[] data;
		
		/**
		 * A value which is used to check whether a calculation should be stopped
		 */
		AtomicBoolean cancel;
		
		/**
		 * The rooted representation of the {@link ComplexPolynomial}
		 */
		ComplexRootedPolynomial roots;
		
		/**
		 * Represents an empty job
		 */
		public static CalculationJob NO_JOB = new CalculationJob();
		
		/**
		 * A simple constructor
		 */
		private CalculationJob() {
		
		}
		
		/**
		 * A simple constructor which assigns all the provided values to their respective current values
		 * @param reMin The provided minimum real value
		 * @param reMax The provided maximum real value
		 * @param imMin The provided minimum imaginary value
		 * @param imMax The provided maximum imaginary value
		 * @param width The provided width
		 * @param height The provided height
		 * @param yMin The provided minimum value on the y-axis
		 * @param yMax The provided maximum value on the y-axis
		 * @param m The provided maximum number of iterations
		 * @param data The provided data field
		 * @param cancel The provided cancel value
		 * @param roots The provided {@link ComplexRootedPolynomial}
		 */
		public CalculationJob(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data ,AtomicBoolean cancel, ComplexRootedPolynomial roots) {
			
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.cancel = cancel;
			this.data = data;
			this.roots = roots;
		
		}
		
		@Override
		public void run() {
			
			
			int maxIter = 16 * 16 * 16;
			double convergenceThreshold = 0.001;
			
			
			ComplexPolynomial polynomial = roots.toComplexPolynom();
			ComplexPolynomial derived = polynomial.derive();
			
			int offset = yMin * width;
			
			if(yMin == 0) {
				offset = 0;
			}
			
			for(int y = yMin; y <= yMax; y++) {
				
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
			
		}
	}
	
	
	/**
	 * An implementation of the {@link IFractalProducer} interface.
	 * Calculates the necessary values for the visualisation of the Newton-Raphson fractal
	 * and takes care of parallelization
	 * @author Vito Sabalic
	 *
	 */
	public static class MyProducer implements IFractalProducer {
		
		/**
		 * The provided arguments
		 */
		private int[] arguments;
		
		/**
		 * The rooted version of the provided polynomial 
		 */
		private ComplexRootedPolynomial roots;
		
		/**
		 * A constructor which assigns all the provided values to their respective current values
		 * @param roots
		 * @param arguments
		 */
		public MyProducer(ComplexRootedPolynomial roots, int[] arguments) {
			this.roots = roots; 
			this.arguments = arguments;
		}
		
		
		/**
		 * Calculates the necessary values for the visualisation of the Newton-Raphson fractal
		 * and takes care of parallelization
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			int m = 16*16*16;
			short[] data = new short[width * height];
			
			final int brojTraka = arguments[1] > height ? height : arguments[1];
			
			int brojYPoTraci = height / brojTraka;
			
			final BlockingQueue<CalculationJob> queue = new LinkedBlockingQueue<>();
			
			System.out.println("Number of threads: " + arguments[0]);
			System.out.println("Number of jobs: " + brojTraka);

			Thread[] radnici = new Thread[arguments[0]];
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							CalculationJob p = null;
							try {
								p = queue.take();
								if(p==CalculationJob.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==brojTraka-1) {
					yMax = height-1;
				}
				CalculationJob posao = new CalculationJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data ,cancel, roots);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(CalculationJob.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			observer.acceptResult(data, (short)(roots.toComplexPolynom().order() + 1), requestNo);
		}
	}
}
