package des;

import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
	    String p1 = "0123456789ABCDEF";
	    String k1 = "133457799BBCDFF1";
	    String c1 = "85E813540F0AB405";
	    
	    String p2 = "02468aceeca86420";
	    String k2 = "0f1571c947d9e859";
	    String c2 = "da02ce3a89ecac3b";
	    
	    String p3 = "she21hab";
        String k3 = "133457799BBCDFF1";
        String c3 = "66b49a13921d983e";

	    Des des = new Des(k1); 
	    String ciphertext = des.encrypt(p1);
	    System.out.println(ciphertext);
	    
	    String plaintext = des.decrypt(ciphertext);
	    System.out.println(plaintext);
	}
}