package aes;

import java.io.FileNotFoundException;

public class Main {

    static AesHelper helper = new AesHelper();

    public static void main(String[] args) throws FileNotFoundException {
        String text= "54776F204F6E65204E696E652054776F";
        String key = "5468617473206D79204B756E67204675";
        boolean is_hexa = true;
//        text= "0123456789abcdeffedcba9876543210";
//        key = "0f1571c947d9e8590cb7add6af7f6798";
//        boolean is_hexa = true;

//        key="Thats my Kung Fu";
//        text="Two One Nine Two";
//        boolean is_hexa = false;

        Aes aes = new Aes(text, key, is_hexa);
        String ciphertext = aes.encrypt();
        
        AesDec aes_dec = new AesDec(ciphertext, key, is_hexa);
        text = aes_dec.decrypt();
    }
}