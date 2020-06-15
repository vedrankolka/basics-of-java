package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;
/**
 * A program that takes roots of a polynomial through the standard input.
 * @author Vedran Kolka
 *
 */
public class Newton {
	// the polynomial given through 
	private static ComplexRootedPolynomial polynomialRooted;
	private static ComplexPolynomial polynomial;
	private static ComplexPolynomial derived;
	private static final double THRESHOLD = 1e-3;
	private static final int M = 16 * 16 * 16;

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\r\n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.");
		List<Complex> roots = new ArrayList<>();
		try (Scanner sc = new Scanner(System.in)) {
			int i = 1;
			while (true) {
				System.out.printf("Root %d> ", i++);
				String input = sc.nextLine();
				if (input.equals("done")) {
					break;
				}
				Complex z = Complex.parse(input);
				roots.add(z);
			}

		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return;
		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		Complex[] nullpoints = new Complex[roots.size()];
		for (int i = 0; i < nullpoints.length; ++i) {
			nullpoints[i] = roots.get(i);
		}
		polynomialRooted = new ComplexRootedPolynomial(Complex.ONE, nullpoints);
		polynomial = polynomialRooted.toComplexPolynom();
		derived = polynomial.derive();
		FractalViewer.show(new MyFractalProducer());
	}

	/**
	 * A fractal producer that uses a fixed thread pool for parallelization of
	 * calculating the indexes for the drawn picture.
	 * 
	 * @author Vedran Kolka
	 *
	 */
	public static class MyFractalProducer implements IFractalProducer {

		// A threadpool available for executing tasks
		private ExecutorService pool;
		// number of available cores on this computer
		private int cores;
		// number of jobs for each core of the computer
		private static final int NUMBER_OF_JOBS_PER_CORE = 8;

		public MyFractalProducer() {
			this.cores = Runtime.getRuntime().availableProcessors();
			this.pool = Executors.newFixedThreadPool(cores, new DaemonicThreadFactory());
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

			System.out.println("Let the calculating begin...");
			short[] data = new short[height * width];

			List<Future<?>> workResults = new ArrayList<Future<?>>();
			// the number of jobs
			int n = cores * NUMBER_OF_JOBS_PER_CORE;
			int heightOfStripe = height / n;

			for (int i = 0; i < n; ++i) {
				int heightStart = i * heightOfStripe;
				int heightEnd = (i + 1) * heightOfStripe;
				// because of integer division, the last stripe might not be covered
				if (i == n - 1) {
					heightEnd = height;
				}
				Work w = new Work(width, height, heightStart, heightEnd, reMin, reMax, imMin, imMax, data, cancel);
				workResults.add(pool.submit(w));
			}

			for (Future<?> f : workResults) {
				// block until the work is done
				try {
					f.get();
				} catch (InterruptedException | ExecutionException e) {
				}

			}

			System.out.println("Calculating finished, let's tell it to our GUI over here!");
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}

		/**
		 * Maps the given coordinated in the picture to the complex plane defined by the
		 * given minimum and maximum coordinates. e.g. (0, 0) -> z = reMin + i*imMin
		 * (width-1, height-1) -> z = reMax + i*imMax
		 * 
		 * @param x
		 * @param y
		 * @param width
		 * @param height
		 * @param reMin
		 * @param reMax
		 * @param imMin
		 * @param imMax
		 * @return
		 */
		private static Complex mapToComplexPlain(int x, int y, int width, int height, double reMin, double reMax,
				double imMin, double imMax) {
			double real = reMin + x * (reMax - reMin) / width;
			double imaginary = imMax - y * (imMax - imMin) / height;
			return new Complex(real, imaginary);
		}

		/**
		 * A factory for daemonic threads.
		 * 
		 * @author Vedran Kolka
		 *
		 */
		private static class DaemonicThreadFactory implements ThreadFactory {

			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}

		}

		/**
		 * A Runnable that does a part of work for the IFractalProducer that is
		 * determines by given startHeight and endHeight of the picture for which the
		 * IFractalProducer is producing the data.
		 * 
		 * @author Vedran Kolka
		 *
		 */
		public static class Work implements Runnable {
			// width of the picture
			private int width;
			// height of the picture
			private int height;
			// start of the part of the picture for which this Work is responsible
			private int heightStart;
			// end of the part of the picture for which this Work is responsible
			private int heightEnd;
			// the minimum value of a Complex's real part
			private double reMin;
			// the maximum value of a Complex's real part
			private double reMax;
			// the minimum value of a Complex's imaginary part
			private double imMin;
			// the maximum value of a Complex's imaginary part
			private double imMax;
			// array of shorts where the results of computing are written
			private short[] data;
			// An atomic flag to check if calculating was canceled
			private AtomicBoolean cancel;

			/**
			 * Constructor for Work that takes all arguments needed for it to do its part of
			 * work
			 * 
			 * @param width       of the picture for which it is calculating
			 * @param height      of the picture for which it is calculating
			 * @param heightStart start index of the part of the picture this Work is
			 *                    calculating
			 * @param heightEnd   end index of the part of the picture this Work is
			 *                    calculating
			 * @param reMin       minimum real part of a Complex in the current picture
			 * @param reMax       maximum real part of a Complex in the current picture
			 * @param imMin       minimum imaginary part of a Complex in the current picture
			 * @param imMax       maximum imaginary part of a Complex in the current picture
			 * @param data        - array of shorts where this Work writes the calculated
			 *                    data enough to a root of the polynomial
			 * @param cancel      an atomic boolean flag used to check if the Work has been
			 *                    canceled
			 */
			public Work(int width, int height, int heightStart, int heightEnd, double reMin, double reMax, double imMin,
					double imMax, short[] data, AtomicBoolean cancel) {
				this.width = width;
				this.height = height;
				this.heightStart = heightStart;
				this.heightEnd = heightEnd;
				this.reMin = reMin;
				this.reMax = reMax;
				this.imMin = imMin;
				this.imMax = imMax;
				this.data = data;
				this.cancel = cancel;
			}

			@Override
			public void run() {
				int offset = width * heightStart;

				for (int y = heightStart; y < heightEnd; ++y) {

					for (int x = 0; x < width; ++x) {
						// if the work was canceled, abort mission
						if (cancel.get()) {
							return;
						}
						Complex zn = MyFractalProducer.mapToComplexPlain(x, y, width, height, reMin, reMax, imMin,
								imMax);

						int count = 0;
						while (count++ < M) {
							Complex numerator = polynomial.apply(zn);
							Complex denominator = derived.apply(zn);
							Complex znold = zn;
							Complex fraction = numerator.divide(denominator);
							zn = zn.sub(fraction);
							double module = znold.sub(zn).module();
							if (module <= THRESHOLD)
								break;
						}
						int index = polynomialRooted.indexOfClosestRootFor(zn, 2 * THRESHOLD);
						data[offset++] = (short) (index + 1);
					}
				}

			}

		}

	}

}
