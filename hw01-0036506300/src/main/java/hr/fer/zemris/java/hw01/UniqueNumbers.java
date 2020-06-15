package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program preko standardnog ulaza prima cijele brojeve koje dodaje u uređeno binarno stablo ako broj već nije u stablu.
 * Završava unosom ključne riječi "kraj". Tada program ispisuje sadržaj stabla uzlazno i silazno.
 * @author Vedran Kolka
 *
 */
public class UniqueNumbers {

	/**
	 * Razred modelira čvor stabla.
	 * Čvor sadrži cjelobrojnu vrijednost i pokazivače na lijevo i desno dijete.
	 * @author Vedran Kolka
	 *
	 */
	public static class TreeNode{
		
		private int value;
		private TreeNode left;
		private TreeNode right;
		
		public TreeNode(int value) {
			this.value = value;
		}
		
		/**
		 * Metoda vraća vrijednost čvora.
		 * @return value
		 */
		public int getValue() {
			return value;
		}
		
		/**
		 * Metoda dodaje novi čvor s vrijednosti value u stablo ako on već ne postoji.
		 * @param glava
		 * @param value
		 * @return glava
		 */
		public static TreeNode addNode(TreeNode glava, int value) {
			
			if(glava==null) {
				return new TreeNode(value);
			}
			
			if(glava.value==value) {
				return glava;
			}
			
			if(value<glava.value) {
				glava.left = addNode(glava.left, value);
			} else {
				glava.right = addNode(glava.right, value);
			}
			
			return glava;
			
		}
		
		/**
		 * Metoda vraća broj čvorova u stablu.
		 * @param glava
		 * @return size
		 */
		public static int treeSize(TreeNode glava) {
			
			if(glava==null) {
				return 0;
			}
			
			return 1+treeSize(glava.left)+treeSize(glava.right);
			
		}
		
		/**
		 * Metoda provjerava sadrži li stablo danu vrijednost.
		 * @param glava
		 * @param value
		 * @return true ako stablo sadrži value, false inače
		 */
		public static boolean containsValue(TreeNode glava, int value) {
			
			if(glava==null) {
				return false;
			}
			
			if(value==glava.getValue()) {
				return true;
			}
			
			if(value<glava.getValue()) {
				return containsValue(glava.left, value);
			} else {
				return containsValue(glava.right, value);
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		TreeNode glava = null;
		
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
			if(!TreeNode.containsValue(glava, broj)) {
				glava = TreeNode.addNode(glava, broj);
				System.out.println("Dodano.");
			} else {
				System.out.println("Broj već postoji. Preskačem.");
			}
		}
		
		System.out.printf("Ispis od najmanjeg: ");
		ispisUzlazno(glava);
		System.out.printf("%nIspis od najvećeg: ");
		ispisSilazno(glava);
		System.out.println();
		
		sc.close();
		
	}
	
	/**
	 * Metoda na standardni izlaz ispisuje sve vrijednosti čvorova u stablu uzlazno.
	 * @param glava
	 */
	public static void ispisUzlazno(TreeNode glava) {
		
		if(glava==null) {
			return;
		}
		
		ispisUzlazno(glava.left);
		System.out.printf("%d ", glava.value);
		ispisUzlazno(glava.right);
		
	}
	
	/**
	 * Metoda na standardni izlaz ispisuje sve vrijednosti čvorova u stablu silazno.
	 * @param glava
	 */
	public static void ispisSilazno(TreeNode glava) {
		
		if(glava==null) {
			return;
		}
		
		ispisSilazno(glava.right);
		System.out.printf("%d ", glava.value);
		ispisSilazno(glava.left);
		
	}
	
}
