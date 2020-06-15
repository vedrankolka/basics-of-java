package hr.fer.zemris.java.hw16.dao.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.hw16.dao.DAO;
import hr.fer.zemris.java.hw16.dao.DAOException;
import hr.fer.zemris.java.hw16.model.PictureDescriptor;
import hr.fer.zemris.java.hw16.web.servlets.ThumbnailsServlet;

/**
 * Implementacija sučelja {@link DAO} koja podatke o slikama čuva u tekstualnoj
 * datoteci "opsinik.txt", a slike u direktorijima "slike" i "thumbnails".
 * 
 * @author Vedran Kolka
 *
 */
public class DAOImpl implements DAO {
	/** Relativna staza do datoteke koja opisuje sve slike */
	public static final String OPISI_PATH = "WEB-INF/opisnik.txt";
	/** Relativna staza do direktorija thumbnails unutar aplikacije */
	public static final String THUMBNAILS_DIR = "WEB-INF/thumbnails";
	/** Relativna staza do direktorija slike unutar aplikacije */
	public static final String PICTURES_DIR = "WEB-INF/slike";
	/** Najveća dimenzija stranice umanjene sličice */
	public static final int THUMBNAIL_SIZE = 150;
	/** Mapa imena slika razvrstana u skupove po imenima tag-ova */
	private static Map<String, Set<PictureDescriptor>> picturesByTags;
	/** Mapa objekata {@link PictureDescriptor} mapirana po imenu */
	private static Map<String, PictureDescriptor> picturesByNames;

	@Override
	public Set<String> getTags() throws DAOException {
		if (picturesByTags == null) {
			throw new DAOException("DAO not initialized.");
		}
		return picturesByTags.keySet();
	}

	@Override
	public Set<PictureDescriptor> getPictures(String tag) throws DAOException {
		if (picturesByTags == null) {
			throw new DAOException("DAO was not initialized.");
		}
		return picturesByTags.get(tag);
	}

	@Override
	public PictureDescriptor getPicture(String fileName) throws DAOException {
		return picturesByNames.get(fileName);
	}

	@Override
	public synchronized BufferedImage getThumbnail(String fileName, String root) throws DAOException {

		Path rootDir = Paths.get(root);
		Path thumbnailsDir = rootDir.resolve(THUMBNAILS_DIR);

		try {
			// ako direktorij thumbnails ne postoji, stvori ga
			if (!Files.exists(thumbnailsDir)) {
				Files.createDirectory(thumbnailsDir);
			}
			// resolve-amo stazu do tražene sličice i provjerimo postoji li
			Path thumbnailPath = thumbnailsDir.resolve(fileName);
			BufferedImage thumbnail = null;

			if (!Files.exists(thumbnailPath)) {
				// ako umanjena sličica ne postoji, pročitaj original i umanji ga
				BufferedImage bim = getImage(fileName, root);
				thumbnail = minimize(bim);
				// zapiši sličicu u direktorij thumbnails pomoću ByteArrayOutputStream-a
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ImageIO.write(thumbnail, "jpg", bos);
				Files.write(thumbnailPath, bos.toByteArray());
			} else {
				// ako postoji, pročitaj ju
				thumbnail = ImageIO.read(Files.newInputStream(thumbnailPath));
			}

			return thumbnail;
		} catch (IOException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public synchronized BufferedImage getImage(String fileName, String root) throws DAOException {
		try {
			Path rootDir = Paths.get(root);
			Path picturesDir = rootDir.resolve(PICTURES_DIR);
			Path originalPicturePath = picturesDir.resolve(fileName);
			return ImageIO.read(Files.newInputStream(originalPicturePath));
		} catch (IOException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public synchronized void load(String path) throws DAOException {
		if (picturesByTags != null && picturesByNames != null)
			return;

		picturesByTags = new HashMap<>();
		picturesByNames = new HashMap<>();

		try {
			Path realPath = Paths.get(path).resolve(OPISI_PATH);
			List<String> lines = Files.readAllLines(realPath, StandardCharsets.UTF_8);
			for (int i = 0; i < lines.size(); i += 3) {
				String filename = lines.get(i);
				String description = lines.get(i + 1);
				String[] tags = readTags(lines.get(i + 2));
				PictureDescriptor pic = new PictureDescriptor(filename, description, tags);
				picturesByNames.put(filename, pic);
				mapPictureByTags(pic);
			}

		} catch (IOException | IndexOutOfBoundsException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * Dodaje sliku u sve skupove u mapi čiji ključevi odgovaraju tag-ovim dane
	 * slike <code>picture</code>
	 * 
	 * 
	 * @param slika za dpdati u mapu
	 */
	private static void mapPictureByTags(PictureDescriptor picture) {
		// za svaki tag slike nađi set, stvori ga ako još ne posotji i dodaj sliku u set
		for (String tag : picture.getTags()) {
			Set<PictureDescriptor> tagged = picturesByTags.get(tag);
			if (tagged == null) {
				tagged = new HashSet<>();
				picturesByTags.put(tag, tagged);
			}
			tagged.add(picture);
		}
	}

	/**
	 * Reads the tags from the line by spliting them by a comma and trimming them.
	 * 
	 * @param line that contains the tags separated by commas
	 * @return array of tags
	 */
	private static String[] readTags(String line) {
		String[] tags = line.split(",");
		for (int i = 0; i < tags.length; ++i) {
			tags[i] = tags[i].trim();
		}
		return tags;
	}

	/**
	 * Skalira danu sliku <code>bim</code> tako da je veća stranica bude jednaka
	 * {@link ThumbnailsServlet#THUMBNAIL_SIZE} i vraća novu skaliranu sliku.
	 * 
	 * @param bim slika za skalirati
	 * @return skalirana slika
	 * @throws IOException
	 */
	private static BufferedImage minimize(BufferedImage bim) throws IOException {

		int width = bim.getWidth();
		int height = bim.getHeight();
		// odredimo nove dimenzije slike tako da je veća stranica jednaka THUMBNAIL_SIZE
		boolean widthBigger = width > height;
		int smallerSide = widthBigger ? THUMBNAIL_SIZE * height / width : THUMBNAIL_SIZE * width / height;

		int newWIdth = widthBigger ? THUMBNAIL_SIZE : smallerSide;
		int newHeight = widthBigger ? smallerSide : THUMBNAIL_SIZE;
		// kreiramo novu sliku te ju nacrtamo pomoću originala i izračunatih dimenzija
		BufferedImage bimSmall = new BufferedImage(newWIdth, newHeight, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bimSmall.createGraphics();
		g2d.drawImage(bim, 0, 0, newWIdth, newHeight, 0, 0, width, height, null);
		g2d.dispose();

		return bimSmall;
	}
}
