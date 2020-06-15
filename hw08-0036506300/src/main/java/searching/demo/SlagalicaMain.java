package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

public class SlagalicaMain {

	public static void main(String[] args) {
		
		if(args.length != 1 || args[0].length() != 9) {
			System.out.println("Dragi korisniče, potrebno je dati jedan argument sa 9 znamenki.");
			return;
		}
		
		int [] pocetno = new int[9];
		char[] znakovi = args[0].toCharArray();
		for(int i = 0 ; i < 9 ; ++i) {
			pocetno[i] = znakovi[i] - 48;
		}
		Set<Integer> brojevi = new TreeSet<>();
		for(int i = 0 ; i < 9 ; ++i) {
			brojevi.add(i);
		}
		for(Integer i : pocetno) {
			brojevi.remove(i);
		}
		
		if(brojevi.size() > 0) {
			System.out.println("Trebaju biti sve znamenke od 0 do 9.");
			return;
		}
		
		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(pocetno));
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);
		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});
			
			SlagalicaViewer.display(rješenje);
		}
	}
	
}
