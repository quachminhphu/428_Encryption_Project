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
	public static String DES_Decrypt(byte[] ciphertext, byte[] keyBytes){
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
		return new String(plainttextByte);
	}
	
	/*
	 * DES finding key
	 */
	public static byte[] DES_FindingKey(byte[] plaintext, byte[] ciphertext, byte[] partOfKey){
		byte[] fullKey = null;
		//Finding key
		
		return fullKey;
	}
	
	public static void main(String[] args){
		// Question 1 
		String myPlaintText = "Dee Bugg";
		System.out.print("CipherText is: ");
		printByteArray(DES_Encrypt(myPlaintText));
		
		//Question 2
		byte[] cipher2 = { (byte) 0x9D, (byte) 0x1C, (byte) 0x1D, (byte) 0x94, (byte) 0x8F, (byte) 0x21, (byte) 0x55, (byte) 0xC5};
		byte[] key2    = { (byte) 0x46, (byte) 0xAA, (byte) 0x20, (byte) 0x1E, (byte) 0xF4, (byte) 0x3C, (byte) 0x92, (byte) 0xD2};
		String plain2  = DES_Decrypt(cipher2, key2);
		System.out.println("Question 2: Plaintext is "+plain2);
		
	}
	
}
