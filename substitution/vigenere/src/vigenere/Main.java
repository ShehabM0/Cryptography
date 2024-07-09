package vigenere;

public class Main {
	public static void main(String[] args) {
	    String key = "shehab";
	    String plaintext = "cryptography";
	   
	    Vigenere vig = new Vigenere(key);

	    String ciphertext = vig.encrypt(plaintext);
	    System.out.println(ciphertext);

	    plaintext = vig.decrypt(ciphertext);
	    System.out.println(plaintext);
	}
}
