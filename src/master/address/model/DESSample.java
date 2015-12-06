package master.address.model;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Created by Thanh-Phong on 12/5/2015.
 */
public class DESSample {

    private Cipher encryptCipher;
    private Cipher decryptCipher;
    private String easyKey;
    private SecretKey key;

    public String getEasyKey() {
        return easyKey;
    }


    public void setEasyKey(String easyKey) {
        this.easyKey = easyKey;
        // decode the base64 encoded string
        byte[] decodedKey = Base64.getDecoder().decode(this.easyKey);
        // rebuild key using SecretKeySpec
        this.key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
    }

    public DESSample(String k) throws Exception{
        setEasyKey(k);
        encryptCipher = Cipher.getInstance("DES");
        decryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE,key);
        decryptCipher.init(Cipher.DECRYPT_MODE,key);
    }

    /*public static void main(String[] args){
        try{
            DESSample encrypter = new DESSample("12345612356");
            String encrypted = encrypter.encrypt("hello");
            System.out.println(encrypted);

            String decrypted = encrypter.decrypt(encrypted);
            System.out.println(decrypted);

        }catch(Throwable e){
            System.out.println(e.getMessage());
            e.printStackTrace();

        }
    }*/

    public String encrypt(String plainText) throws Throwable{

        byte[] utf8 = plainText.getBytes();
        byte[] enc = encryptCipher.doFinal(utf8);

        return new sun.misc.BASE64Encoder().encode(enc);
    }

    public String decrypt(String cipherText) throws Throwable{

        byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(cipherText);
        byte[] utf8 = decryptCipher.doFinal(dec);

        return new String(utf8,"UTF8");
    }

}
