import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*
 * Question 1: Ciphertext is [E5, 0E, A6, 1E, 50, A3, D2, 69]
 * Question 2: Plaintext  is "CPSC 428"
<<<<<<< HEAD
 * Question 3: Most recent guess was --k: [90, 4E, F2, CC, 04, 3C, AE, 2F]
 * Question 4 : plaintext: Good Job |key: [BA, 54, 68, 08, 12, D4, C6, 9E]
=======
 * Question 3: Full key is [90, 4E, F2, CC, 86, 02, 4A, 16]
 * Question 4: There are several keys that lead to "Good Job":
 * p: Good Job |k: [BA, 54, 68, 08, 12, D4, C6, 9E]
 * p: Good Job |k: [BA, 54, 68, 08, 12, D4, C6, 9F]
 * p: Good Job |k: [BA, 54, 68, 08, 12, D4, C7, 9E]
 * p: Good Job |k: [BA, 54, 68, 08, 12, D4, C7, 9F]
 * p: Good Job |k: [BA, 54, 68, 08, 12, D5, C6, 9E]
 * p: Good Job |k: [BA, 54, 68, 08, 12, D5, C6, 9F]
 * p: Good Job |k: [BA, 54, 68, 08, 12, D5, C7, 9E]
 * p: Good Job |k: [BA, 54, 68, 08, 12, D5, C7, 9F]
 * It is impossible to tell which the original key was, but as there are so few to test,
 * it isn't significant.
>>>>>>> origin/master
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
	 * Generates a String from a byte array
	 */
<<<<<<< HEAD
	public static String printByteArray(byte [] array)
	    {
		String myWord="";
		myWord = myWord+ ("[");
		for(int i = 0; i < array.length-1; i++)
		{
		    myWord = myWord + (Integer.toHexString((array[i]>>4)&0x0F).toUpperCase());
		    myWord = myWord +(Integer.toHexString(array[i]&0x0F).toUpperCase() + ", ");
		}
		myWord = myWord + (Integer.toHexString(array[array.length-1]>>4&0x0F).toUpperCase());
		myWord = myWord +(Integer.toHexString(array[array.length-1]&0x0F).toUpperCase() + "]");
	    return myWord;
	    }
=======
	public static String byteArrayToString(byte[] array) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < array.length - 1; i++) {
			sb.append(Integer.toHexString((array[i] >> 4) & 0x0F)
					.toUpperCase());
			sb.append(Integer.toHexString(array[i] & 0x0F).toUpperCase()
					+ ", ");
		}
		sb.append(Integer.toHexString(
				array[array.length - 1] >> 4 & 0x0F).toUpperCase());
		sb.append(Integer.toHexString(array[array.length - 1] & 0x0F)
				.toUpperCase() + "]");
		
		return sb.toString();
	}
>>>>>>> origin/master
	
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
	public static byte[] DES_FindingKey2(byte[] plaintext, byte[] ciphertext, byte[] partOfKey){
		byte[] fullKey = new byte[plaintext.length];
		for(int i = 0; i < Math.min(partOfKey.length, plaintext.length); ++i){
			fullKey[i] = partOfKey[i];
		}
		//Finding key
<<<<<<< HEAD
		byte[] plaintextGuess = new byte[plaintext.length];
		while(!assertByteArraysEqual(plaintext, plaintextGuess)){
			plaintextGuess = DES_Decrypt(ciphertext, fullKey);
			System.out.print("--k: ");
			System.out.println (printByteArray(fullKey));
=======
		
		int c = 0;
		
		byte[] plaintextGuess = new byte[plaintext.length];
		while(!assertByteArraysEqual(plaintext, plaintextGuess)){
			plaintextGuess = DES_Decrypt(ciphertext, fullKey);
			if(c % 10000 == 0){
				System.out.print("--k: ");
				byteArrayToString(fullKey);
			}
>>>>>>> origin/master
			if(!assertByteArraysEqual(plaintext, plaintextGuess))
				incrementByteArrayByOne(fullKey);
			
			++c;
		}
		return fullKey;
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
		boolean assertArray = false;
		assertArray = assertByteArraysEqual(plaintext, plaintextGuess);
		while(! assertArray){
			plaintextGuess = DES_Decrypt(ciphertext, fullKey);
			System.out.print("--k: ");
			System.out.println(printByteArray(fullKey));
			assertArray = assertByteArraysEqual(plaintext, plaintextGuess);
			if(!assertArray)
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
			System.out.println("---"+ i);
			plaintext = DES_Decrypt(ciphertext, key);
			if(i % 100000 == 0){
				System.out.println("--k: " +	byteArrayToString(key));
			
			}
			if(isOnlyAscii(plaintext)){
<<<<<<< HEAD
				//for testing
				String myWord = new String(plaintext);
				System.out.println(myWord);
				result.add("p: " + myWord + " |k: " + printByteArray(key));
=======
				result.add("p: " + new String(plaintext) + " |k: " + byteArrayToString(key));
>>>>>>> origin/master
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
<<<<<<< HEAD
		String myWord = new String(array);
		
		for(char b : myWord.toCharArray()){
			if (isAsciiPrintable(b) == false) {
	              return false;
	          }
=======
		for(byte b : array){
			//If is whitespace...
			if(!Character.isLetter((char)b) && !Character.isWhitespace((char)b)){
				return false;
			}
			if((char) b > 'z') return false;
>>>>>>> origin/master
		}
		return true;
	}
	public static boolean isAsciiPrintable(char ch) {
	      return ch == 32 || (ch>=64 && ch<=90) || (ch >= 97 && ch <= 122);
	  }
	
	public static void main(String[] args){
<<<<<<< HEAD
		// Question 1 
		String myPlaintText = "Dee Bugg";
		System.out.print("Question 1: Ciphertext is ");
		System.out.println (printByteArray(DES_Encrypt(myPlaintText)));
		
		//Question 2
		byte[] cipher2 = { (byte) 0x9D, (byte) 0x1C, (byte) 0x1D, (byte) 0x94, (byte) 0x8F, (byte) 0x21, (byte) 0x55, (byte) 0xC5};
		byte[] key2    = { (byte) 0x46, (byte) 0xAA, (byte) 0x20, (byte) 0x1E, (byte) 0xF4, (byte) 0x3C, (byte) 0x92, (byte) 0xD2};
		String plain2  = new String(DES_Decrypt(cipher2, key2));
		System.out.println("Question 2: Plaintext is "+plain2);
		
		//Question 3
		/*
		byte[] cipher3 = { (byte) 0x9D, (byte) 0x1C, (byte) 0x1D, (byte) 0x94, (byte) 0x8F, (byte) 0x21, (byte) 0x55, (byte) 0xC5};
		byte[] partialKey = { (byte) 0x46, (byte) 0xAA, (byte) 0x20, (byte) 0x1E, (byte) 0xF4, (byte) 0x3C, (byte) 0x92};
		String plaintext = "CPSC 428";
		byte[] fullKey = DES_FindingKey(plaintext.getBytes(), cipher3, partialKey);
		System.out.print("Question 3: Full key is: ");
		printByteArray(fullKey);
		*/
		//Question 4
		
		byte[] cipher4 = { (byte) 0xB1, (byte) 0x80, (byte) 0xE8, (byte) 0x05, (byte) 0x4E, (byte) 0x7D, (byte) 0xD6, (byte) 0x4C};
		byte[] key4    = { (byte) 0xBA, (byte) 0x54, (byte) 0x68, (byte) 0x08, (byte) 0x12};
		ArrayList<String> questionList = new ArrayList<>();
		questionList.addAll(DES_guessPlaintext(cipher4, key4, 16777216));
		for (int i=0; i<questionList.size();i++){
			System.out.println(questionList.get(i));
		}
		
=======
//		// Question 1 
//		String myPlaintText = "Dee Bugg";
//		System.out.print("Question 1: Ciphertext is ");
//		System.out.println(byteArrayToString(DES_Encrypt(myPlaintText)));
//		
//		//Question 2
//		byte[] cipher2 = { (byte) 0x9D, (byte) 0x1C, (byte) 0x1D, (byte) 0x94, (byte) 0x8F, (byte) 0x21, (byte) 0x55, (byte) 0xC5};
//		byte[] key2    = { (byte) 0x46, (byte) 0xAA, (byte) 0x20, (byte) 0x1E, (byte) 0xF4, (byte) 0x3C, (byte) 0x92, (byte) 0xD2};
//		String plain2  = new String(DES_Decrypt(cipher2, key2));
//		System.out.println("Question 2: Plaintext is "+plain2);
//		
//		//Question 3
//		byte[] cipher3 = { (byte) 0xA5, (byte) 0x99, (byte) 0x04, (byte) 0x72, (byte) 0x39, (byte) 0x95, (byte) 0x41, (byte) 0xEC};
//		byte[] partialKey = { (byte) 0x90, (byte) 0x4E, (byte) 0xF2, (byte) 0xCC, (byte) 0x04, (byte) 0x3C, (byte) 0xAE, (byte) 0x2F};
//		String plaintext = "Captains";
//		byte[] fullKey = DES_FindingKey(plaintext.getBytes(), cipher3, partialKey);
//		System.out.print("Question 3: Full key is: ");
//		System.out.println(byteArrayToString(fullKey));
//		
		//Question 4
//		byte[] cipher4 = { (byte) 0xB1, (byte) 0x80, (byte) 0xE8, (byte) 0x05, (byte) 0x4E, (byte) 0x7D, (byte) 0xD6, (byte) 0x4C};
//		byte[] partKey = { (byte) 0xBA, (byte) 0x54, (byte) 0x68, (byte) 0x08, (byte) 0x12};
//		ArrayList<String> results = DES_guessPlaintext(cipher4, partKey, 16581375);
//		for(String s : results){
//			System.out.println(s);
//		}
>>>>>>> origin/master
	}
	
	
	
}
