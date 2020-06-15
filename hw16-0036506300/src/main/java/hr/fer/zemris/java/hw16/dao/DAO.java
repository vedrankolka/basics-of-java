package hr.fer.zemris.java.hw16.dao;

import java.awt.image.BufferedImage;
import java.util.Set;

import hr.fer.zemris.java.hw16.model.PictureDescriptor;

/**
 * Sučelje prema sloju za perzistenciju podataka.
 * <p>
 * Prije korištenja ostalih metoda potrebno je pozvati metodu
 * {@link #load(String)} kako bi se potrebni resursi inicijalizirali.
 * 
 * @author Vedran Kolka
 *
 */
public interface DAO {

	/**
	 * Dohvaća skup svih tagova nad dostupnim slikama.
	 * 
	 * @return skup svih tagova nad dostupnim slikama
	 * @throws DAOException
	 */
	public Set<String> getTags() throws DAOException;

	/**
	 * Dohvaća skup svih objekata {@link PictureDescriptor} koji u svojim tag-ovima
	 * sadrže dani <code>tag</code>.
	 * 
	 * @param tag - tag čije slike se traže
	 * @return skup svih objekata {@link PictureDescriptor} koji sadže dani
	 *         <code>tag</code>
	 * @throws DAOException
	 */
	public Set<PictureDescriptor> getPictures(String tag) throws DAOException;

	/**
	 * Vraća objekt {@link PictureDescriptor} koji opisuje sliku imena
	 * <code>fileName</code>.
	 * 
	 * @param fileName ime tražene slike
	 * @return {@link PictureDescriptor} koji opisuje traženu sliku.
	 * @throws DAOException
	 */
	public PictureDescriptor getPicture(String fileName) throws DAOException;

	/**
	 * Dohvaća umanjenu sliku (thumbnail) slike imena <code>fileName</code> koja se
	 * nalazi u aplikaciji s korijenskim direktorijem <code>root</code>.
	 * 
	 * @param fileName ime tražene slike
	 * @param root     korijenski direktorij aplikacije
	 * @return umanjenu sliku (maximalnih veličina 150x150)
	 * @throws DAOException
	 */
	public BufferedImage getThumbnail(String fileName, String root) throws DAOException;

	/**
	 * Dohvaća sliku imena <code>fileName</code> koja se nalazi u aplikaciji s
	 * korijenskim direktorijem <code>root</code>.
	 * 
	 * @param fileName ime tražene slike
	 * @param root     korijenski direktorij aplikacije
	 * @return sliku
	 * @throws DAOException
	 */
	public BufferedImage getImage(String fileName, String root) throws DAOException;

	/**
	 * Inicijalizira potrebne resurse sloja za perzistenciju podataka.
	 * 
	 * @param path - staza koja predstavlja korijenski direktorij aplikacije
	 * @throws DAOException
	 */
	public void load(String path) throws DAOException;
}
