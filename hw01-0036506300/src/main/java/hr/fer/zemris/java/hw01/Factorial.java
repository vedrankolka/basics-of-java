package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program preko standardnog ulaza prima cijele brojeve od 3 do 20 te računa
 * i ispisuje faktorijele unesenih brojeva. Program zavšava unosom riječi 'kraj'
 * @author Vedran Kolka
 *
 */
public class Factorial {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		while(true) {
			
			System.out.printf("Unesite broj> ");
			String unos = sc.next();
			
			if(unos.equals("kraj")) {
				break;
			}
			int broj;
			try {
				broj = Integer.parseInt(unos);
			} catch(NumberFormatException e) {
				System.out.printf("'%s' nije cijeli broj.%n", unos);
				continue;
			}
			if(broj<3 || broj>20) {
				System.out.printf("'%d' nije broj u dozvoljenom rasponu.%n", broj);
				continue;
			}
			double rezultat = 0;
			try {
				rezultat = factorial(broj);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			System.out.printf("%d! = %.0f%n", broj, rezultat);
			
		}
		
		sc.close();
		
	}
	
	/**
	 * Računa faktorijelu broja x
	 * @param x
	 * @return x!
	 * @throws IllegalArgumentException za broj manji od 0 ili veći od 170
	 */
	public static double factorial(double x) throws IllegalArgumentException {
		
		if(x<0) {
			throw new IllegalArgumentException("Ne može se računati faktorijela negativnog broja.");
		}
		
		if(x>170) {
			throw new IllegalArgumentException("Broj je prevelik za računanje faktorijela.");
		}
		
		if(x==0) {
			return 1;
		}
		
		return x*factorial(x-1);
		
	}

}
