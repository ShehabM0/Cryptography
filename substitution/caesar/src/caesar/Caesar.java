package caesar;

import java.util.ArrayList;

public class Caesar {
	int getVal(int n, int k) {
		return (n + k + 26) % 26;
	}
	
	private char getChar(int n, boolean f) {
		return f ? (char)(n + 'a') : (char)(n + 'A');
	}
	
	public String encryption(String s, int k) {
		String res = "";
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int p;
			if(c >= 'a' && c <= 'z') {
				p = getVal(c - 'a', k);
				res += getChar(p, true);
			}
			else if(c >= 'A' && c <= 'Z') {
				p = getVal(c - 'A', k);
				res += getChar(p, false);
			}
			else res += c;
		}
		return res;
	}
	
	public String decryption(String s, int k) {
		String res = "";
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int p;
			if(c >= 'a' && c <= 'z') {
				p = getVal(c - 'a', -1 * k);
				res += getChar(p, true);
			}
			else if(c >= 'A' && c <= 'Z') {
				p = getVal(c - 'A', -1 * k);
				res += getChar(p, false);
			}
			else res += c;
		}
		return res;
	}
	
	public ArrayList<String> attack(String s) {
		ArrayList<String> list = new ArrayList<>();
		for(int k = 0; k < 26; k++) {
			String decreyptedStr = decryption(s, k);
			list.add(decreyptedStr);
		}
		return list;
	}
}
