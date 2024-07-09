package rsa;

import java.math.BigInteger;
import java.util.ArrayList;

public class Rsa {
    private Pair publicKey, privateKey;
    
    Rsa() {
        Key key = new Key();
        this.publicKey = key.publicKey;
        this.privateKey = key.privateKey;
    }
    
    public ArrayList<BigInteger> encrypt(String plaintext) {
        ArrayList<Integer> asc = textToAsc(plaintext);
        ArrayList<BigInteger> enc_asc = new ArrayList<>();
        
        for(int a : asc) {
            BigInteger b = encrypt(BigInteger.valueOf(a));
            enc_asc.add(b);
        }

        return enc_asc;
    }
    
    public BigInteger encrypt(BigInteger plaintext) {
        BigInteger e = publicKey.f;
        BigInteger n = publicKey.s;
        if(plaintext.compareTo(n) >= 0) {
            System.out.println("plaintext (M) must be less than " + n + "!!");
            System.exit(0);
        }
        BigInteger ciphertext = plaintext.modPow(e, n);
        return ciphertext;
    }

    public String decrypt(ArrayList<BigInteger> ciphertext) {
        String dec = "";
        ArrayList<BigInteger> dec_asc = new ArrayList<>();
        
        for(BigInteger a : ciphertext) {
            BigInteger b = decrypt(a);
            dec_asc.add(b);
        }

        for(BigInteger b : dec_asc) {
            dec += (char) b.intValue();
        }

        return dec;
    }

    public BigInteger decrypt(BigInteger ciphertext) {
        BigInteger d = privateKey.f;
        BigInteger n = privateKey.s;
        BigInteger plaintext = ciphertext.modPow(d, n);
        return plaintext;
    }
    
    public ArrayList<Integer> textToAsc(String s) {
        ArrayList<Integer> asc = new ArrayList<>();
        for(int i = 0; i < s.length(); i++) {
            int asc_val = (char) s.charAt(i);
            asc.add(asc_val);
        }
        return asc;
    }
}
