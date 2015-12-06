package master.address.model;

import javafx.util.Pair;
import sun.swing.BakedArrayList;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TinoEncryptor {

    public static void tinoEncrypt(int bitLength, String eightByteKey, String plainText){
        try{
            DESSample des = new DESSample(eightByteKey);
            String cipherText = des.encrypt(plainText);

            ElGamalSample egl = new ElGamalSample();
            egl.generateKeys(bitLength);
            List< Pair<BigInteger,BigInteger> > cipherPair = new ArrayList<>();
            cipherPair = egl.encryptSring(eightByteKey);
            String decrypted = egl.decryptString(cipherPair);
            String c = des.decrypt(cipherText);
            System.out.println(c);



        }catch(Exception e){
            System.out.println("problem" + e.getMessage());
            e.printStackTrace();

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void main(String[] args) {

        tinoEncrypt(24,"12345612356","hello");

    }
}
