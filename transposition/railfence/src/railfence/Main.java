package railfence;

public class Main {
	public static void main(String[] args) {
		String plaintext = "shehab";
		int depth = 2;

		Railfence rf = new Railfence();

		String ciphertext = rf.encrypt(plaintext, depth);
		System.out.println(ciphertext);

		ciphertext = "shbeah";
		depth = 4;
		plaintext = rf.decrypt(ciphertext, depth);
		System.out.println(plaintext);
	}
}
