package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;
/**
 * Entity manager factory provider to provide the factory across the whole application.
 * @author Vedran Kolka
 *
 */
public class JPAEMFProvider {

	public static EntityManagerFactory emf;
	
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}