package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program preko naredbnog retka čita širinu i visinu pravokutnika.
 * Ako argumenti nisu zadani preko naredbenog retka
 * program traži korisnika da upiše pozitivne vrijednosti za širinu i visinu.
 * Zatim ispisuje površinu i opseg pravokutnika.
 * @author Vedran Kolka
 *
 */
public class Rectangle {

	public static void main(String[] args) {
		
		if(args.length!=0 && args.length!=2) {
			System.out.println("Broj argumenata je kriv.");
			return;
		}
		
		double sirina;
		double visina;
		
		if(args.length==2) {
			try {
				sirina = Double.parseDouble(args[0]);
				visina = Double.parseDouble(args[1]);
				if(sirina<0 || visina<0) {
					throw new NumberFormatException();
				}
			} catch(NumberFormatException e) {
				System.out.println("Argumenti zadani preko naredbenog retka nisu"
						+ " ispravno napisani brojevi.");
				return;
			}
		} else {
			Scanner sc = new Scanner(System.in);
			sirina = unosPodatka(sc, "širinu");
			visina = unosPodatka(sc, "visinu");
		}
		
		ispisPodataka(sirina, visina);
		
	}
	
	/**
	 * S ulaza sc čita dok se ne unese pozitivni decimalni broj
	 * @param sc
	 * @param dimenzija
	 * @return pozitivan decimalni broj
	 */
	private static double unosPodatka(Scanner sc, String dimenzija) {
		
		double rezultat;
		while(true) {
			System.out.printf("Unesite %s > ", dimenzija);
			String token = sc.next();
			try {
				rezultat = Double.parseDouble(token);
			} catch(NumberFormatException e) {
				System.out.printf("'%s' se ne može protumačiti kao broj.%n", token);
				continue;
			}
			if(rezultat>=0) {
				break;
			}
			System.out.println("Unijeli ste negativnu vrijednost.");
		}
		
		return rezultat;
		
	}
	
	/**
	 * Ispisuje podatke o površini i opsegu pravokutnika stranica x i y.
	 * @param x
	 * @param y
	 */
	private static void ispisPodataka(double x, double y) {
		System.out.printf("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f "
				+ "te opseg %.1f.%n", x, y, x*y, 2*(x+y));
	}
	
}
