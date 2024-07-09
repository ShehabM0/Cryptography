package playfair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Playfair {
	int n = 5;
	boolean[] vis = new boolean[26];
	boolean[] visNum = new boolean[10];
	char[][] grid = new char[5][5]; // key
	ArrayList<Pair> digs = new ArrayList<>(); // diagrams
	Map<Character, Pairn> idx = new HashMap<>();  
	
	Playfair(String key) {
    	key = filterKey(key);
		buildGrid(key);
		
		buildIdx();
	}
	
	public String encrypt(String plaintext) {
		plaintext = filter(plaintext);
		buildDiagram(plaintext);

		String res = "";
		for(Pair p : digs) {
			Pairn cellFrom = idx.get(p.x);
			Pairn cellTo = idx.get(p.y);
			
			if(p.x < 'a' || p.x > 'z') {
				res += p.x + "" + p.y;
				continue;
			}
			if(p.y < 'a' || p.y > 'z') {
				res += p.x + "" + p.y;
				continue;
			}
			
			if(cellFrom == null) {
				if(p.x == 'i') cellFrom = idx.get('j');
				else cellFrom = idx.get('i');
			}

			Pairn xx, yy;
			if(cellFrom.y == cellTo.y) {
				int xFrom, xTo;
				if(cellFrom.x + 1 == n) xFrom = 0;
				else xFrom = cellFrom.x + 1;
				
				if(cellTo.x + 1 == n) xTo = 0;
				else xTo = cellTo.x + 1;
				xx = new Pairn(xFrom, cellFrom.y);
				yy = new Pairn(xTo, cellTo.y);
			}
			else if(cellFrom.x == cellTo.x) {
				int yFrom, yTo;
				if(cellFrom.y + 1 == n) yFrom = 0;
				else yFrom = cellFrom.y + 1;
				
				if(cellTo.y + 1 == n) yTo = 0;
				else yTo = cellTo.y + 1;
				xx = new Pairn(cellFrom.x, yFrom);
				yy = new Pairn(cellTo.x, yTo);
			}
			else {
				xx = new Pairn(cellFrom.x, cellTo.y);
				yy = new Pairn(cellTo.x, cellFrom.y);				
			}
			
			res += grid[xx.x][xx.y] + "" + grid[yy.x][yy.y];
		}
		return res;
	}
	
	public String decrypt(String ciphertext) {
		ciphertext = filter(ciphertext);
		buildDiagram(ciphertext);

		String res = "";
		for(Pair p : digs) {
			Pairn cellFrom = idx.get(p.x);
			Pairn cellTo = idx.get(p.y);
			
			if(p.x < 'a' || p.x > 'z') {
				res += p.x + "" + p.y;
				continue;
			}
			if(p.y < 'a' || p.y > 'z') {
				res += p.x + "" + p.y;
				continue;
			}
			
			if(cellFrom == null) {
				if(p.x == 'i') cellFrom = idx.get('j');
				else cellFrom = idx.get('i');
			}

			Pairn xx, yy;
			if(cellFrom.y == cellTo.y) {
				int xFrom, xTo;
				if(cellFrom.x - 1 == -1) xFrom = n - 1;
				else xFrom = cellFrom.x - 1;
				
				if(cellTo.x - 1 == -1) xTo = n - 1;
				else xTo = cellTo.x - 1;
				xx = new Pairn(xFrom, cellFrom.y);
				yy = new Pairn(xTo, cellTo.y);
			}
			else if(cellFrom.x == cellTo.x) {
				int yFrom, yTo;
				if(cellFrom.y - 1 == -1) yFrom = n - 1;
				else yFrom = cellFrom.y - 1;
				
				if(cellTo.y - 1 == -1) yTo = n - 1;
				else yTo = cellTo.y - 1;
				xx = new Pairn(cellFrom.x, yFrom);
				yy = new Pairn(cellTo.x, yTo);
			}
			else {
				xx = new Pairn(cellFrom.x, cellTo.y);
				yy = new Pairn(cellTo.x, cellFrom.y);				
			}
			
			char r = grid[yy.x][yy.y];
			if(r == 'x') res += grid[xx.x][xx.y];
			else res += grid[xx.x][xx.y] + "" + grid[yy.x][yy.y];
		}
		return res;
	}
	
	public void buildGrid(String s) {
		int i = 0, j = 0;
		for(int c = 0; c < s.length(); c++) {
			char cr = s.charAt(c);
			if(j == n) {
				j = 0;
				i++;
			}
			if(cr >= '0' && cr <= '9') {
				if(!visNum[(int)cr - '0']) {
					grid[i][j] = cr;
					visNum[(int)cr - '0'] = true;
					j++;
				}
				continue;
			}
			if(!vis[(int)cr - 'a']) {
				grid[i][j] = cr;
				vis[(int)cr - 'a'] = true;
				j++;
			}
		}
		
		for(int c = 0; c < 26; c++) {
			if(j == n) {
				j = 0;
				i++;
			}
			if(i == n) break;
			if(!vis[c]) {
				if((char)(c + 'a') == 'i' || (char)(c + 'a') == 'j') {
					vis[(int)'i' - 'a'] = true;
					vis[(int)'j' - 'a'] = true;
				}
				vis[c] = true;
				grid[i][j] = (char)(c + 'a');
				j++;
			}
		}
	}
	
	public void buildDiagram(String s) {
	    digs.clear();

		int c = 0;
		while(c < s.length() - 1) {
			Pair p;
			char ch1 = s.charAt(c);
			char ch2 = s.charAt(c + 1);
			if(ch1 == ch2) {
				p = new Pair(ch1, 'x');
				c++;
			}
			else {
				p = new Pair(ch1, ch2);
				c += 2;
			}
			digs.add(p);
		}
		if(c < s.length()) {
			Pair p = new Pair(s.charAt(c), 'x');
			digs.add(p);
		}
	}
	
	public void buildIdx() {
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++) {
				Pairn p = new Pairn(i, j);
				idx.put(grid[i][j], p);
			}
	}
	
	public String filterKey(String s) {
		s = s.toLowerCase();
		String res = "";
		for(int i = 0; i < s.length(); i++)
			if(s.charAt(i) >= 'a' && s.charAt(i) <= 'z')
				res += s.charAt(i);
		return res;
	}
	
	public String filter(String s) {
		String res = "";
		for(int i = 0; i < s.length(); i++)
			res += s.charAt(i);
		return res.toLowerCase();
	}
	
	public void printGrid() {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++)
				System.out.print(grid[i][j]);
			System.out.println();
		}
	}
	
	public void printDig() {
		for(Pair p : digs) {
			System.out.println(p.x + " " + p.y);
		}
	}
	
	public void printIdx() {
		for(Map.Entry<Character, Pairn> p : idx.entrySet()) {
			System.out.println(p.getKey() + " " + (p.getValue()).x + "-" + (p.getValue()).y );
		}
	}
}

class Pair {
	char x, y;
	Pair(char c1, char c2) {
	    this.x = c1;
	    this.y = c2;
	}
}

class Pairn {
	int x, y;
	Pairn(int n1, int n2) {
	    this.x = n1;
	    this.y = n2;
	}
}
