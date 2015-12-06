package master.address.model;

import javafx.util.Pair;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Thanh-Phong on 12/5/2015.
 */
public class ElGamalSample {

    private BigInteger p;
    private BigInteger g;
    private BigInteger a;
    private BigInteger b;
    private BigInteger k;
    private BigInteger y;
    private BigInteger y1, y2;
    private Random random;

    public static BigInteger getGenerator(BigInteger p, Random s){
        int count = 0;

        while(count < 100){
            BigInteger random = new BigInteger(p.bitCount()-1,s);
            BigInteger exponent = new BigInteger("1");
            BigInteger nextNum = random.mod(p);

            while(!nextNum.equals(BigInteger.ONE) ){
                nextNum = (nextNum.multiply(random).mod(p));
                exponent = exponent.add(BigInteger.ONE);
            }

            if(exponent.equals(p.subtract(BigInteger.ONE))){
                return random;
            }
        }

        return null;
    }

    public void generateKeys(int bitLength){
        SecureRandom sr = new SecureRandom();
        p = BigInteger.probablePrime(bitLength,sr);
        g = getGenerator(p, sr);

        if( g != null){
            a = new BigInteger(p.bitCount()-1,sr);
            b = g.modPow(a,p);
            k = new BigInteger(p.bitCount()-1,sr);
        }
    }

    public Pair<BigInteger,BigInteger> encrypt(char c){
        long u = (long) c;
        BigInteger m = BigInteger.valueOf(c);
        BigInteger c1 = g.modPow(k,p);
        BigInteger c2 = b.modPow(k,p);

        c2 = (c2.multiply(m)).mod(p);
        Pair<BigInteger,BigInteger> cipher = new Pair(c1,c2);
        return cipher;
    }

    public List< Pair<BigInteger,BigInteger> > encryptSring(String plainText){
        List<Pair<BigInteger,BigInteger>> cipherText = new ArrayList<>();

        for(int i = 0 ; i < plainText.length(); i++){
            char[] word = plainText.toCharArray();
            Pair<BigInteger,BigInteger> pair = encrypt(word[i]);
            cipherText.add(pair);
        }
        return cipherText;
    }

    public char decrypt(Pair<BigInteger,BigInteger> cipherPair){
        BigInteger inverse = (cipherPair.getKey()).modPow(a,p);
        inverse = inverse.modInverse(p);
        BigInteger msg = inverse.multiply(cipherPair.getValue());
        msg = msg.mod(p);
        return (char) msg.intValue();
    }

    public String decryptString(List< Pair<BigInteger,BigInteger> > cipherPairList){
        String plainText = "";

        for(Pair<BigInteger,BigInteger> pair : cipherPairList){
            char c = decrypt(pair);
            plainText = plainText + c;
        }

        return plainText;
    }

    public static void main(String[] args) throws Exception{


    }
}
