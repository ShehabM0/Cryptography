package transposition;

import java.util.HashMap;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		
		String key = "4712563";
		String s = "KillCoronaVirusAtTweleveAmTomorrow";
		
		Transposition t = new Transposition(key);
		String enc = t.encrypt(s);
		System.out.println(enc);
		String dec = t.decrypt(enc);
		System.out.println(dec);
		
		System.out.println(2+3.2);
		
		if(key == s) {
			
		} else {
			
		}
		int q = 3;
		q/=1.32     ;
		System.out.println(q);
		
		for(int i =0; i == 3; i++)
			System.out.println("A");
	}
}
