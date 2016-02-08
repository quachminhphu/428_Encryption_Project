import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*
 * Question 1: Ciphertext is [E5, 0E, A6, 1E, 50, A3, D2, 69]
 * Question 2: Plaintext  is "CPSC 428"
 */

public class DESApi {
	// Useful method
	public static void incrementByteArrayByOne(byte [] b) {    
		// starting from back, add one and check if carry over
		  // if no carry over, stop and return
		  // if carry over, increment next byte and check again
		  	for(int i = b.length-1; i>=0  ; i--){
				b[i]++;
		  		if(b[i] != 0x00){
		    		return;
		    		}
		     	}   
		return;
	}

	/*
	 * Print out a byte array in hex format
	 */
	public static void printByteArray(byte [] array)
	    {
		System.out.print("[");
		for(int i = 0; i < array.length-1; i++)
		{
		    System.out.print(Integer.toHexString((array[i]>>4)&0x0F).toUpperCase());
		    System.out.print(Integer.toHexString(array[i]&0x0F).toUpperCase() + ", ");
		}
		System.out.print(Integer.toHexString(array[array.length-1]>>4&0x0F).toUpperCase());
		System.out.println(Integer.toHexString(array[array.length-1]&0x0F).toUpperCase() + "]");
	    }
	
	/*
	 * DES Encryption
	 */
	public static byte[] DES_Encrypt(String plaintext){
		Cipher cipher;
		SecretKeySpec key;
		byte[] ciphertext= null;
		byte [] keyBytes;
		byte [] plainttextByte;
		
		// set up key 
		keyBytes = new byte[] {(byte) 0x7A, (byte) 0x90, (byte) 0xC8, (byte) 0x36, (byte) 0x44, (byte) 0x0E, (byte) 0x18, (byte) 0x76};
		plainttextByte = plaintext.getBytes();
		//encrypt
		key = new SecretKeySpec(keyBytes, "DES");
		
		try
		{
			
			cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			ciphertext= cipher.doFinal(plainttextByte);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ciphertext;
	}
	
	/*
	 * DES Decryption
	 */
	public static byte[] DES_Decrypt(byte[] ciphertext, byte[] keyBytes){
		Cipher cipher;
		SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");;
		byte [] plainttextByte = null;
		
		try
		{
			
			cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			plainttextByte = cipher.doFinal(ciphertext);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return plainttextByte;
	}
	
	/*
	 * DES finding key
	 */
	public static byte[] DES_FindingKey(byte[] plaintext, byte[] ciphertext, byte[] partOfKey){
		byte[] fullKey = new byte[plaintext.length];
		for(int i = 0; i < Math.min(partOfKey.length, plaintext.length); ++i){
			fullKey[i] = partOfKey[i];
		}
		//Finding key
		
		byte[] plaintextGuess = new byte[plaintext.length];
		while(!assertByteArraysEqual(plaintext, plaintextGuess)){
			plaintextGuess = DES_Decrypt(ciphertext, fullKey);
			System.out.print("--k: ");
			printByteArray(fullKey);
			if(!assertByteArraysEqual(plaintext, plaintextGuess))
				incrementByteArrayByOne(fullKey);
		}
		return fullKey;
	}
	
	/*
	 * DES - Guessing plaintext
	 */
	public static ArrayList<String> DES_guessPlaintext(byte[] ciphertext, byte[] partialKey, int iterations){
		ArrayList<String> result = new ArrayList<String>();
		
		byte[] key = new byte[ciphertext.length];
		byte[] plaintext = null;
		for(int i = 0; i < Math.min(key.length, partialKey.length); ++i){
			key[i] = partialKey[i];
		}
		
		for(int i = 0; i < iterations; ++i){
			plaintext = DES_Decrypt(ciphertext, key);
			if(isOnlyAscii(plaintext)){
				result.add("p: " + new String(plaintext) + " |k: " + new String(key));
			}
			incrementByteArrayByOne(key);
		}
		
		return result;
	}
	
	
	private static boolean assertByteArraysEqual(byte[] array1, byte[] array2){
		if(array1.length != array2.length)
			return false;
		for(int i = 0; i < array1.length; ++i){
			if(array1[i] != array2[i])
				return false;
		}
		return true;
	}
	
	//TODO: Implement
	private static boolean isOnlyAscii(byte[] array){
		for(byte b : array){
			//If is whitespace...
			if (b == (byte) 0x20)
				return true;
			//Or uppercse...
			if (b >= (byte) 0x41 && b <= (byte) 0x5A)
				return true;
			//Or lowercase...
			if(b >= (byte) 0x61 && b <= (byte)0x7A)
				return true;
		}
		return false;
	}
	
	public static void main(String[] args){
		// Question 1 
		String myPlaintText = "Dee Bugg";
		System.out.print("Question 1: Ciphertext is ");
		printByteArray(DES_Encrypt(myPlaintText));
		
		//Question 2
		byte[] cipher2 = { (byte) 0x9D, (byte) 0x1C, (byte) 0x1D, (byte) 0x94, (byte) 0x8F, (byte) 0x21, (byte) 0x55, (byte) 0xC5};
		byte[] key2    = { (byte) 0x46, (byte) 0xAA, (byte) 0x20, (byte) 0x1E, (byte) 0xF4, (byte) 0x3C, (byte) 0x92, (byte) 0xD2};
		String plain2  = new String(DES_Decrypt(cipher2, key2));
		System.out.println("Question 2: Plaintext is "+plain2);
		
		//Question 3
		byte[] cipher3 = { (byte) 0xA5, (byte) 0x99, (byte) 0x04, (byte) 0x72, (byte) 0x39, (byte) 0x95, (byte) 0x41, (byte) 0xEC};
		byte[] partialKey = { (byte) 0x90, (byte) 0x4E, (byte) 0xF2, (byte) 0xCC};
		String plaintext = "Captains";
		byte[] fullKey = DES_FindingKey(plaintext.getBytes(), cipher3, partialKey);
		System.out.print("Question 3: Full key is: ");
		printByteArray(fullKey);
		
		//Question 4
	}
	
}
