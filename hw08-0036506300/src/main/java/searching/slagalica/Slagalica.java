package searching.slagalica;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;
/**
 * Models a slagalica.
 * @author Vedran Kolka
 *
 */
public class Slagalica implements Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>,
		Supplier<KonfiguracijaSlagalice>, Predicate<KonfiguracijaSlagalice> {
	/**
	 * Početna konfiguracija slagalice.
	 */
	private KonfiguracijaSlagalice s0;

	public Slagalica(KonfiguracijaSlagalice s0) {
		this.s0 = s0;
	}

	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		int[] stanja = t.getPolje();
		for (int i = 0; i < 8; i++) {
			if (stanja[i] != i + 1)
				return false;
		}
		return true;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return s0;
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		List<Transition<KonfiguracijaSlagalice>> prijelazi = new ArrayList<>();
		int praznina = t.indexOfSpace();
		// ako nije u lijevom stupcu, mogući potez je pomicanje ulijevo
		if (praznina % 3 != 0) {
			int[] polje = zamijeni(t.getPolje(), praznina, praznina - 1);
			KonfiguracijaSlagalice k = new KonfiguracijaSlagalice(polje);
			prijelazi.add(new Transition<>(k, 1));
		}
		// ako nije u desnom stupcu, mogući potez je pomicanje udesno
		if (praznina % 3 != 2) {
			int[] polje = zamijeni(t.getPolje(), praznina, praznina + 1);
			KonfiguracijaSlagalice k = new KonfiguracijaSlagalice(polje);
			prijelazi.add(new Transition<>(k, 1));
		}
		// ako nije u gornjem redku, mogući potez je pomicanje prema gore
		if (praznina > 2) {
			int[] polje = zamijeni(t.getPolje(), praznina, praznina - 3);
			KonfiguracijaSlagalice k = new KonfiguracijaSlagalice(polje);
			prijelazi.add(new Transition<>(k, 1));
		}
		// ako nije u gornjem redku, mogući potez je pomicanje prema gore
		if (praznina < 6) {
			int[] polje = zamijeni(t.getPolje(), praznina, praznina + 3);
			KonfiguracijaSlagalice k = new KonfiguracijaSlagalice(polje);
			prijelazi.add(new Transition<>(k, 1));
		}
		
		return prijelazi;
	}

	/**
	 * U predanom polju zamijenjuje vrijenosti na indeksima i i j i vraća
	 * promijenjeno polje.
	 * 
	 * @param polje
	 * @param i
	 * @param j
	 * @return predano polje, ali sa zamijenjenim elementima na indeksima i i j
	 */
	private int[] zamijeni(int[] polje, int i, int j) {
		int pom = polje[j];
		polje[j] = polje[i];
		polje[i] = pom;
		return polje;
	}

}
