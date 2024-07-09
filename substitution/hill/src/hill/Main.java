package hill;

import java.util.Scanner;

public class Main {
	static int nKey = 3;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {

		String s = "paymoremoney";

	    int[][] key = new int [nKey][nKey];
	    read(key);

	    Hill hill = new Hill(key);

		s = hill.filterStr(s);
	    
	    String ciphertext = hill.encrypt(s);
	    System.out.println(ciphertext);
	    
	    String plaintext = hill.decrypt(ciphertext);
	    System.out.println(plaintext);
	}
	
	public static void read(int[][] a) {
		int n = a.length, m = a[0].length;
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				a[i][j] = sc.nextInt();
	}
	
}

