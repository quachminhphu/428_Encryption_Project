
public class DESApi {
	
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
	public static byte[] DES_Encrypt(byte[] plaintext, byte[] key){
		byte[] ciphertext= null;
		//encrypt
		return ciphertext;
	}
	
	/*
	 * DES Decryption
	 */
	public static byte[] DES_Decrypt(byte[] ciphertext, byte[] key){
		byte[] plaintext = null;
		//decrypt
		return plaintext;
	}
	
	/*
	 * DES finding key
	 */
	public static byte[] DES_FindingKey(byte[] plaintext, byte[] ciphertext, byte[] partOfKey){
		byte[] fullKey = null;
		//Finding key
		
		return fullKey;
	}
	
	
}
