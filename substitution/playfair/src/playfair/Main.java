package playfair;

public class Main {
    public static void main(String[] args) {
        String key = "MONARCHY";
        Playfair pf = new Playfair(key);

        String s = "balloons";
        String ciphertext = pf.encrypt(s);
        System.out.println(ciphertext);

        String plaintext = pf.decrypt(ciphertext);
        System.out.println(plaintext);
    }
}
