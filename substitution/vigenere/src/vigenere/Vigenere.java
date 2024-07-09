package vigenere;

public class Vigenere {
    private String key;

    Vigenere(String key) {
        this.key = key.toLowerCase();
    }

	public String fullKey(String s) {
		s = s.toLowerCase();
		int n = s.length();
		int m = key.length();

		String res = "";
		int j = 0;
		for(int i = 0; i < n; i++) {
			res += key.charAt(j++);
			j %= m;
		}

		return res;
	}
	
	public String encrypt(String plaintext) {
		plaintext = plaintext.toLowerCase(); key = fullKey(plaintext);
		int n = plaintext.length();
		String res = "";
		for(int i = 0; i < n; i++)
			res += (char)((((int)plaintext.charAt(i) - 'a' % 26 + (int)key.charAt(i) - 'a' % 26) % 26) + 'A');
		return res;
	}
	
	public String decrypt(String ciphertext) {
		ciphertext = ciphertext.toLowerCase(); key = fullKey(ciphertext); 
		int n = ciphertext.length();
		String res = "";
		for(int i = 0; i < n; i++)
			res += (char)(((int)(ciphertext.charAt(i) - 'a') - (int)(key.charAt(i) - 'a') + 26) % 26 + 'a');
		return res;
	}
}
