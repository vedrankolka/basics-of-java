package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * An iterable collection of prime numbers.
 * @author Vedran Kolka
 *
 */
public class PrimesCollection implements Iterable<Integer> {
	/**
	 * Number of prime numbers in this collection.
	 */
	private int n;

	public PrimesCollection(int n) {
		this.n = n;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimesIterator();
	}
	/**
	 * Iterator over the PrimesCollection class
	 * @author Vedran Kolka
	 *
	 */
	public class PrimesIterator implements Iterator<Integer> {
		/**
		 * Index of the next consecutive prime number to return.
		 */
		private int i;
		/**
		 * The last returned prime number, or 1 if no prime numbers were yet returned.
		 */
		private int lastPrime;

		public PrimesIterator() {
			i = 0;
			lastPrime = 1;
		}

		@Override
		public boolean hasNext() {
			return i < n;
		}

		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more prime numbers in this collection.");
			}
			// start calculating from the last prime number + 1
			int prime = lastPrime + 1;
			while (!isPrime(prime))
				prime++;
			i++;
			lastPrime = prime;
			return prime;
		}

	}
	/**
	 * Check if given <code>n</code> is a prime number or not.
	 * @param n
	 * @return <code>true</code> if it is, <code>false</code> otherwise
	 */
	private static boolean isPrime(int n) {
		if (n == 2) return true;
		if (n % 2 == 0) return false;
		for(int i = 3 ; i*i<=n ; i += 2) {
			if( n % i == 0) return false;
		}
		return true;
	}

}
