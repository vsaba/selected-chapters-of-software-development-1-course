package hr.fer.oprpp1.hw05;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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
 * Class which either checks the sha-256 digest of a provided file or encrypt/decrypts a provided file
 * @author Vito Sabalic
 *
 */
public class Crypto {
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		
		if(args.length < 2 || args.length > 3) {
			throw new IllegalArgumentException("Incorrect number of arguments!");
		}

			
		if(args[0].equalsIgnoreCase("checksha")) {
			checksha(args[1]);
		}
		else if(args[0].equalsIgnoreCase("encrypt") || args[0].equalsIgnoreCase("decrypt")) {
			crypt(args[1], args[2], args[0].equalsIgnoreCase("encrypt"));
		}
		else {
			throw new IllegalArgumentException("The provided command is incorrect!");
		}
	}
	
	
	
	
	/**
	 * 
	 * Checks the sha-256 digest of the provided file
	 * @param file The provided file
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException
	 */
	private static void checksha(String file) throws IOException, NoSuchAlgorithmException{
		
		System.out.printf("Please provide expected sha-256 digest for " + file + ":\r\n"
				+ "> ");
		
		Scanner sc = new Scanner(System.in);
		String expected = sc.nextLine();
		sc.close();
		
		try (InputStream is = Files.newInputStream(Paths.get("./" + file))){
			byte[] array = new byte[4096];
			
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			
			
			int i;
			while((i = is.read(array)) != -1) {
				sha.update(array, 0, i);
			}
			
			byte[] newArray = sha.digest();
			
			String actual = Util.bytetohex(newArray);
			
			if(expected.equals(actual)) {
				System.out.println("Digesting completed. Digest of " + file + " matches expected digest.");
			}
			else {
				System.out.println("Digesting completed. Digest of " + file + " does not match the expected digest. Digest\r\n"
						+ "was: " + actual);
			}
		}
		catch(NoSuchFileException e) {
			throw new IllegalArgumentException("The provided file does not exist");
		}
	
		
		
	}
	
	/**
	 * Encrypts/decrypts the provided file based on the provided argument before running the program
	 * @param file The provided file
	 * @param newFile The new file name
	 * @param encrypt parameter which determines whether the method will decrypt or encrypt the provided file
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private static void crypt(String file, String newFile, boolean encrypt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, BadPaddingException {
		System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\r\n"
				+ "> ");
		
		Scanner sc = new Scanner(System.in);
		
		String keyText = sc.nextLine();
		
		
		System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):\r\n"
				+ "> ");
		
		String ivText = sc.nextLine();
		
		sc.close();
		
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		
		try(InputStream is = Files.newInputStream(Paths.get("./" + file));
				OutputStream os = Files.newOutputStream(Paths.get("./" + newFile))){
			
			byte[] array = new byte[4096];
			
			int i;
			while((i = is.read(array)) != -1) {
				
				os.write(cipher.update(array, 0, i));
			}
			
			
			byte[] newArray = cipher.doFinal();
			
			
			
			os.write(newArray);
		}
		catch(NoSuchFileException e) {
			throw new IllegalArgumentException("The provided file does not exist");
		}
		
		System.out.println((encrypt ? "Encryption " : "Decryption ") + "completed. Generated file " + newFile + " based on file " + file + ".");
	}

}
