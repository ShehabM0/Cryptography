package rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Rsa r = new Rsa();
        String plaintext = "Hello World!!";
    
        ArrayList<BigInteger> enc = r.encrypt(plaintext);
        System.out.println("Encrypted: ");
        for(BigInteger a : enc) System.out.print(a); System.out.println();

        System.out.println("Decrypted: ");
        String dec = r.decrypt(enc);
        System.out.println(dec);
    }
}
