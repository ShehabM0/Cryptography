package transposition;

import java.util.HashMap;
import java.util.Map;

public class Transposition {
	String text;
	String key;
	
	char[][] mat;
	Map<Integer, Integer> mp = new HashMap<>();
	
	boolean[] vis = new boolean[26];
	
	int row_div;
	int row_rem;
	int row;
	
	Transposition(String key) {
		this.key = filterKey(key);
		buildMap();
	}
	
	public String encrypt(String plaintext) {
		init(plaintext);
		buildMatrixEnc(plaintext);
		
		int key_len = key.length();
		String res = "";
		for(int j = 0; j < key_len; j++) {
			for(int i = 0; i < row; i++)
				if(mat[i][mp.get(j)] != '_')
					res += mat[i][mp.get(j)];
		}
		return res;
	}
	
	public String decrypt(String ciphertext) {
		init(ciphertext);
		buildMatrixDec(ciphertext);
		
		String res = "";
		for(int i = 0; i < row; i++)
			for(int j = 0; j < key.length(); j++)
				if(mat[i][j] != '_')
					res += mat[i][j];
		
		return res;
	}
	
	private void init(String text) {
		int text_len = text.length(), key_len = key.length();
		
		this.row_div = text_len / key_len;
		this.row_rem = text_len % key_len;
		this.row = row_div + (row_rem == 0? 0 : 1);
		
		mat = new char[row][key_len];
		
		for(int i = 0; i < row; i++)
			for(int j = 0; j < key_len; j++)
				mat[i][j] = '_';
	}
	
	private void buildMatrixEnc(String plaintext) {
		int c = 0;
		for(int i = 0; i < row; i++)
			for(int j = 0; j < key.length(); j++)
				if(c < plaintext.length())
					mat[i][j] = plaintext.charAt(c++);
	}
	
	public void buildMatrixDec(String ciphertext) {
		int c = 0;
		int key_len = key.length();
		for(int j = 0; j < key_len; j++) {
			if(mp.get(j) < row_rem) {
				for(int i = 0; i < row; i++)
					mat[i][mp.get(j)] = ciphertext.charAt(c++);
			} else {
				for(int i = 0; i < row_div; i++)
					mat[i][mp.get(j)] = ciphertext.charAt(c++);
			}
		}
	}
	
	private void buildMap() {
		for(int i = 0; i < key.length(); i++)
			mp.put(key.charAt(i) - '0', i);
	}
	
	public String filterPlaintext(String plaintext) {
		plaintext = plaintext.toLowerCase();
		String res = "";
		for(int i = 0; i < plaintext.length(); i++) {
			char c = plaintext.charAt(i);
			if(c >= 'a' && c <= 'z') res += c;
			vis[c - 'a'] = true;
		}
		return res;
	}
	
	public String filterKey(String key) {
		key = key.toLowerCase();
		String res = "";
		for(int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			if(c >= 'a' && c <= 'z') res += (char) c - 'a';
			else if(c >= '0' && c <= '9') res += (char) c - '0' - 1;
		}
		return res;
	}
	
	private void displayMatrix() {
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < key.length(); j++)
				System.out.print(mat[i][j]);
			System.out.println();
		}
				
	}
	
	private void displayMap() {
		for (Map.Entry<Integer, Integer> me : mp.entrySet())
	           System.out.print(me.getKey() + " : " + me.getValue() + '\n'); 
	}
	
}
