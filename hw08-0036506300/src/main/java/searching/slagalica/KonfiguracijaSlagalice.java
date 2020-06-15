package searching.slagalica;

import java.util.Arrays;
import java.util.Objects;

/**
 * Razred modelira jednu konfigariciju slagalice gdje 0 označava prazno polje
 * slagalice.
 * 
 * @author Vedran Kolka
 *
 */
public class KonfiguracijaSlagalice {
	private static final int DIGIT_OFFSET = 48;
	/**
	 * Polje koje pamti gdje se nalazi koje polje slagaice.
	 */
	private int[] polje;
	/**
	 * Polje znakova za ispis konfiguracije.
	 */
	private char[] poljeZaIspis;
	/**
	 * Indeks praznine u konfiguraciji.
	 */
	private int praznina;

	/**
	 * Stvara konfiguraciju slagalice s predanim poljem koje predstavlja stanje slagalice.
	 * Nalazi indeks praznine i stvara polje za ispis konfiguracije.
	 * @param polje
	 * @throws IllegalStateException ako polje nema prazninu (0)
	 */
	public KonfiguracijaSlagalice(int[] polje) {
		this.polje = polje;
		this.poljeZaIspis = new char[9];
		boolean imaPrazninu = false;
		for(int i = 0 ; i < 9 ; i++) {
			if(polje[i] == 0) {
				praznina = i;
				imaPrazninu = true;
				poljeZaIspis[i] = '*';
			} else {
				poljeZaIspis[i] = (char)(polje[i] + DIGIT_OFFSET);
			}
		}
		if(!imaPrazninu)
			throw new IllegalStateException("Polje ne sadrži prazninu!");
	}

	/**
	 * Vraća kopiju polja koje opisuje stanje slagalice.
	 * @return kopija polja
	 */
	public int[] getPolje() {
		return Arrays.copyOf(polje, 9);
	}

	/**
	 * Vraća indeks praznog polja u slagalici.
	 * @return indeks praznine
	 */
	public int indexOfSpace() {
		return praznina;
	}
	
	@Override
	public String toString() {
		return String.format("%n%c %c %c%n%c %c %c%n%c %c %c",
				poljeZaIspis[0], poljeZaIspis[1], poljeZaIspis[2],
				poljeZaIspis[3], poljeZaIspis[4], poljeZaIspis[5],
				poljeZaIspis[6], poljeZaIspis[7], poljeZaIspis[8]);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(polje);
		result = prime * result + Arrays.hashCode(poljeZaIspis);
		result = prime * result + Objects.hash(praznina);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KonfiguracijaSlagalice))
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		return Arrays.equals(polje, other.polje);
	}
	
	

}
