package master.address.model;

public abstract class Encryptor
{ //abstract class
	public abstract String textEncrypt(String plainText);
	public abstract String textDecrypt(String cipherText, byte key);
}
