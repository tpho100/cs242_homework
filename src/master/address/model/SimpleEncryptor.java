package master.address.model;

public class SimpleEncryptor extends Encryptor
{
	private byte encryptionKey;
	
	public byte getEncryptionKey()
	{
		return encryptionKey;
	}

	@Override
	public String textEncrypt(String plainText) 
	{	
		long sum = 0; //Ran into problems using int because the value of sum became very large for strings with many characters
		// Calculate the encryption key
		for(int i  = 0; i < plainText.length();i++)
		{ 
			sum += plainText.charAt(i);
		}
			
		encryptionKey = (byte) (sum % 128);
		
		//Begin encrypting the text
		char[] tempText = plainText.toCharArray();
		String cipherText = "";
		
		for(int i  = 0; i < plainText.length();i++)
		{	
			tempText[i] += 4;
			tempText[i] ^= encryptionKey;
			cipherText += tempText[i];
		}
		return cipherText;
	}

	public String textEncrypt(String plainText, byte key){
		char[] tempText = plainText.toCharArray();
		String cipherText = "";

		for(int i  = 0; i < plainText.length();i++)
		{
			tempText[i] += 4;
			tempText[i] ^= key;
			cipherText += tempText[i];
		}
		return cipherText;

	}

	@Override
	public String textDecrypt(String cipherText, byte key) 
	{
		
		char[] tempText = cipherText.toCharArray();
		String plainText = "";
		
		for(int i = 0; i < cipherText.length(); i++)
		{
			tempText[i] ^= key;
			tempText[i] -= 4;
			plainText += tempText[i];
		}
		
		return plainText;
	}

}
