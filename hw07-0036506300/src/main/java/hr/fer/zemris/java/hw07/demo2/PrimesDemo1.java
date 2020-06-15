package hr.fer.zemris.java.hw07.demo2;

public class PrimesDemo1 {

	public static void main(String[] args) {
		
		PrimesCollection primesCollection = new PrimesCollection(5);
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}

}
