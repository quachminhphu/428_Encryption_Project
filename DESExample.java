import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class DESExample 
{

//might expect DES to look like this, but Java takes object-oriented approach of course
//public static byte[] DES_Encrypt(byte[] plaintext, byte[] key){
//	byte[] ciphertext;
//	// encrypt
//	return ciphertext;
//}


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
	

	public static void main(String[] args) 
	{
		Cipher cipher;
		SecretKeySpec key;

		byte [] keyBytes;
		byte [] pt;
		byte [] ct;
		
		// Set up the key bytes (64 bits --> 8 bytes)
		keyBytes = new byte[] {(byte) 0x02, (byte) 0x40, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
		
		// Here comes the plaintext block (also, 64 bits --> 8 bytes)...
		String ptString = new String("Abcdefgh");	
		pt = ptString.getBytes();		
		System.out.println("plaintext: "+ptString);
		// Set up the key so it can be used in the algorithm
		key = new SecretKeySpec(keyBytes, "DES");
		
		try
		{
			// Create the actual DES cipher, in Electronic Code Book mode, with no padding
			cipher = Cipher.getInstance("DES/ECB/NoPadding");
		
			// Initialize the cipher with the key and set it up for encryption
			cipher.init(Cipher.ENCRYPT_MODE, key);
        
			// Encrypt the plaintext
			ct = cipher.doFinal(pt);
        
			// Display the ciphertext
			System.out.println("ciphertext bytes:");
			printByteArray(ct);
			

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
