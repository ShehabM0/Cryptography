package hill;

import java.util.ArrayList;
import java.util.Scanner;

// RRLMWBKASPDH rrlmwbkaspdh

public class Hill {
	int nKey = 3;
	int[][] key;
	
	Hill(int[][] key) {
		this.key = key;
	}
	
	public int modInv(int a, int x) {
		for(int i = 1; i < x; i++)
			if((a * i) % x == 1)
				return i;
		return -1;
	}
	
	public String encrypt(String s) {
		s = filterStr(s);
		ArrayList<ArrayList<Character>> vector = build(s);
		String ans = "";
		int[][] a;
		
	    for(int i = 0; i < vector.size(); i++) {
	    	a = new int[1][nKey];
	    	for(int j = 0; j < vector.get(0).size(); j++)
	    		a[0][j] = (int)vector.get(i).get(j) - 'a';

	    	int[][] res = multiply(a, key);
	    	for(int q = 0; q < nKey; q++)
	    		ans += (char)((res[0][q]) + 'a'); 
	    }
	    
	    return ans;
	}
	
	public String decrypt(String s) {
		int d = getDeterminant(key);
	    int dInv = modInv(d, 26);
	    int[][] adj = getAdjoint(key);
	    key = multiply(adj, dInv); // new key for dec
		
		ArrayList<ArrayList<Character>> vector = build(s);
		String ans = "";
		int[][] a;
		
	    for(int i = 0; i < vector.size(); i++) {
	    	a = new int[1][nKey];
	    	for(int j = 0; j < vector.get(0).size(); j++)
	    		a[0][j] = (int)vector.get(i).get(j) - 'a';

	    	int[][] res = multiply(a, key);
	    	for(int q = 0; q < nKey; q++)
	    		ans += (char)((res[0][q]) + 'a'); 
	    }
	    
	    return ans;
	}
	
	public ArrayList<ArrayList<Character>> build(String s) {
		ArrayList<ArrayList<Character>> vector = new ArrayList<>();
	    ArrayList<Character> v = new ArrayList<>();
	    int n = s.length();
	    
	    int k = 0;
	    for(int i = 0; i < n; i++) {
	    	char c = s.charAt(i);
	    	v.add(c);
	    	k = (k + 1) % nKey;
	    	if(k == 0) {
	    		vector.add(v);
	    		v = new ArrayList<>();
	    	}
	    }
	    if(k != 0) {
	    	for(int i = k; i < nKey; i++) v.add('x');
	    	vector.add(v);
	    }
	    
	    return vector;
	}
	
	public String filterStr(String s) {
		s = s.toLowerCase();
		String res = "";
		for(int i = 0; i < s.length(); i++)
			if(s.charAt(i) >= 'a' && s.charAt(i) <= 'z')
				res += s.charAt(i);
		return res;
	}
	
	public boolean isInvertible(int[][] mat) {
		int det = getDeterminant(mat);
		return (det != 0);
	}
	
	public int[][] multiply(int[][] mat, int k) {
		int n = mat.length, m = mat[0].length;

		int[][] res = new int[n][m];
	    for(int i =0; i < n; i++) // mat1 row
	        for(int j = 0; j < m; j++) // mat2 column
	                res[i][j] = (mat[i][j] * k) % 26;
	    
	    return res;
	}
	
	public int[][] multiply(int[][] mat1, int[][] mat2) {
		int n, m;
		int n1 = mat1.length, m1 = mat1[0].length;
		int n2 = mat2.length, m2 = mat2[0].length;

		if(m1 != n2) return null;
		else {
			n = n1;
			m = m2;
		}
		
		int[][] res = new int[n][m];
	    for(int i =0; i < n; i++) // mat1 row
	        for(int j = 0; j < m; j++) // mat2 column
	            for(int k = 0; k < m1; k++) // mat1 columns = mat2 rows, m1 or n2
	                res[i][j] = (res[i][j] + (mat1[i][k] * mat2[k][j])) % 26;
	    
	    return res;
	}
	
	public double[][] multiply(double[][] mat1, int[][] mat2) {
		int n = mat1.length, m = n;
	    double[][] res = new double[n][m];

	    for(int i =0; i < n; i++) // mat1 row
	        for(int j = 0; j < n; j++) // mat2 column
	            for(int k = 0; k < m; k++) // mat2 row
	                res[i][j] += mat1[i][k] * mat2[k][j];

	    return res;
	}
	
	public double[][] getInverse(int[][] mat) {
		int n = mat.length, m = n;
		int det = getDeterminant(mat);
		int[][] adj = getAdjoint(mat);
		
		double[][] inv = new double[n][m];
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				inv[i][j] = (double) ((double)adj[i][j] / (double) (det));
		
		return inv;
	}
	
	public int getDeterminant(int[][] mat) {
		int n = mat.length, m = n;
		int ans = 0;
		
		int i = 0;
		for(int j = 0; j < n; j++) {
			int mul = mat[i][j];
			int new_i = i + 1,
					new_j = (j + 1) % m;
			int diag1 = mat[new_i][new_j] * mat[(new_i + 1) % n][(new_j + 1) % m],
					diag2 = mat[(new_i + 1) % n][new_j] * mat[new_i][(new_j + 1) % m];
			ans += mul * (diag1 - diag2);
		}
		
		return (ans >= 0)? ans % 26 : (26 - (-1 * ans % 26));
	}
	
	public int[][] getAdjoint(int[][] mat) {
		int n = mat.length, m = n;
		int[][] adj = new int[n][m];
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				int new_i = (i + 1) % n,
						new_j = (j + 1) % m;
				int diag1 = mat[new_i][new_j] * mat[(new_i + 1) % n][(new_j + 1) % m],
						diag2 = mat[(new_i + 1) % n][new_j] * mat[new_i][(new_j + 1) % m];
				adj[i][j] = diag1 - diag2;
			}
		}
		
		int[][] transp = new int[n][m];
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				transp[j][i] = (adj[i][j] > 0)? adj[i][j] % 26 : (26 - (-1 * adj[i][j] % 26));

		return transp;
	}
	
	public void display(int[][] a) {
		int n = a.length, m = a[0].length;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++)
				System.out.print(a[i][j] + " ");
			System.out.println();
		}
	}
	public void display(double[][] a) {
		int n = a.length, m = a[0].length;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++)
				System.out.print(a[i][j] + " ");
			System.out.println();
		}
	}
}

