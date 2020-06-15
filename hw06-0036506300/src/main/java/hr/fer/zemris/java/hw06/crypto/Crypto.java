package hr.fer.zemris.java.hw06.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * A class that offers three operations. 1) calculating the digest of a given file
 * 2) Encrypting a given file  3) Decrypting a given file .
 * @author Vedran Kolka
 *
 */
public class Crypto {
	
	public static void main(String ...args) {
		
		if(args.length < 2) {
			System.err.println("Not enough arguments.");
			return;
		}
		
		try (Scanner sc = new Scanner(System.in)){
			
			if(args[0].equalsIgnoreCase("checksha")) {
				
				Path path = Paths.get(args[1]);
				System.out.printf("Please provide expected sha-256 digest for %s%n>", args[1]);
				String expected = sc.next().toLowerCase();
				String digested = digestToString(path);
				writeDigestMessage(expected, digested, path.getFileName().toString());
				return;
			//if it is not a digest command, it must have 2 more arguments
			} else if(args.length != 3) {
				System.err.println("Invalid arguments.");
				return;	
			}
			//if this passed, a Cipher object is needed for the other two commands
			//which can only be encrypt or decrypt, thus the last else
			boolean encrypt;
			if(args[0].equalsIgnoreCase("encrypt")) {
				encrypt = true;
			} else if(args[0].equalsIgnoreCase("decrypt")) {
				encrypt = false;
			} else {
				System.err.println("Unknown command.");
				return;
			}
			System.out.printf(
					"Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):%n>");
			String keyText = sc.next();
			System.out.printf(
					"Please provide initialization vector as hex-encoded text (32 hex-digits):%n>");
			String ivText = sc.next();
			Cipher cipher = getInitializedCipher(keyText, ivText, encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE);
			Path source = Paths.get(args[1]);
			Path destination = Paths.get(args[2]);
			crypt(cipher, source, destination);
			System.out.printf(
					"%s completed. Generated file %s based on file %s.%n",
					encrypt ? "Encryption" : "Decryption", args[2], args[1]);
			
		} catch(IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * The method reads from <code>source</code> file, encrypts/decrypts (depending on the
	 * initialization of the given <code>cipher</code>) it using the given <code>cipher</code>
	 * and writes the ciphertext/plain text to the given <code>destination</code>.
	 * @param cipher
	 * @param source
	 * @param destination
	 * @throws IOException
	 */
	private static void crypt(Cipher cipher, Path source, Path destination) throws IOException {
		
		try(InputStream is = Files.newInputStream(source);
			OutputStream os = Files.newOutputStream(destination)) {
			
			byte[] buffer = new byte[1024];
			
			while(true) {
				int r = is.read(buffer);
			 	if(r<1) break;
			 	os.write(cipher.update(buffer, 0, r));
			 	os.flush();
			}
			os.write(cipher.doFinal());
			os.flush();
			
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			//this should never execute because the buffers are hardcoded to the same size
			e.printStackTrace();
		}

	}

	/**
	 * Creates and initializes a cipher object with hardcoded algorithms:
	 *  AES algorithm for the secret key.
	 *  AES, CBC and PKCS5Padding for the parameter specifications.
	 * @param keyText
	 * @param ivText
	 * @param mode
	 * @return initialized Cipher object
	 */
	private static Cipher getInitializedCipher(String keyText, String ivText, int mode) {
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(mode, keySpec, paramSpec);
			return cipher;
		} catch (InvalidAlgorithmParameterException |NoSuchAlgorithmException |
				NoSuchPaddingException | InvalidKeyException e) {
			//this should never execute because the arguments are hardcoded correctly
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Writes an appropriate message on the standard output depending on the given parameters.
	 * @param expected
	 * @param digested
	 * @param path
	 */
	private static void writeDigestMessage(String expected, String digested, String path) {
		if(expected.equalsIgnoreCase(digested)) {
			System.out.println("Digesting completed."
					+ " Digest of " + path + " matches expected digest.");
		} else {
			System.out.printf(
					"Digesting completed. Digest of %s does not match the expected digest.%n"
					+ "Digest was: %s", path, digested);
		}
	}
	
	/**
	 * Digests the file with path <code>path</code> with the sha-256 algorithm and returns
	 * the string representation of the digest in hexadecimal symbols.
	 * @param path
	 * @return string representing the digest value in hexadecimal symbols.
	 * @throws IOException
	 */
	private static String digestToString(Path path) throws IOException {
		
		try(InputStream is = Files.newInputStream(path)){
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] buffer = new byte[1024];
			while(true) {
				int r = is.read(buffer);
			 	if(r<1) break;
			 	md.update(buffer, 0, r);
			}
			byte[] digestedBytes = md.digest();
			return Util.bytetohex(digestedBytes);
		} catch (NoSuchAlgorithmException e) {
			//this should never be executed because the algorithm is hardcoded
			e.printStackTrace();
			return null;
		}
	}
	
}
