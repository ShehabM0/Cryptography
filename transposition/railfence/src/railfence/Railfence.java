package railfence;

public class Railfence {
	String text;
	int depth;
	char[][] mat;
	
	private void init() {
		int n = text.length();
		mat = new char[depth][n];
		for(int i = 0; i < depth; i++)
			for(int j = 0; j < n; j++)
				mat[i][j] = '_';
		this.text = filter(text);
	}
	
	private void buildGridEnc() {
		int n = text.length();
		
		int i = 0;
		boolean zigzag = true;
		for(int j = 0; j < n; j++) {
			if(i == depth) {
				zigzag = !zigzag;
				i = depth - 2;
			}
			else if(i == -1) {
				zigzag = !zigzag;
				i = 1;
			}
			mat[i][j] = text.charAt(j);
			i = zigzag? i + 1 : i - 1;
		}

		displayGrid();
	}

	private void buildGridDec() {
		int n = text.length();
		
		int i = 0;
		boolean zigzag = true;
		for(int j = 0; j < n; j++) {
			if(i == depth) {
				zigzag = !zigzag;
				i = depth - 2;
			}
			else if(i == -1) {
				zigzag = !zigzag;
				i = 1;
			}
			mat[i][j] = '*';
			i = zigzag? i + 1 : i - 1;
		}
		
		int idx = 0;
		for(i = 0; i < depth; i++)
		    for(int j = 0; j < n; j++)
		        if(mat[i][j] == '*')
		            mat[i][j] = text.charAt(idx++);
		
		displayGrid();
	}
	
	public String encrypt(String plaintext, int depth) {
	    this.depth = depth;
		this.text = plaintext;
		init();
		buildGridEnc();
		
		int n = plaintext.length();
		String res = "";
		for(int i = 0; i < depth; i++)
			for(int j = 0; j < n; j++)
				if(mat[i][j] != '_')
					res += mat[i][j];
		
		return res.toUpperCase();
	}

	public String decrypt(String ciphertext, int depth) {
	    this.depth = depth;
		this.text = ciphertext;
		init();
		buildGridDec();

		int n = ciphertext.length();
		String res = "";
		int i = 0;
		boolean zigzag = true;
		for(int j = 0; j < n; j++) {
			if(i == depth) {
				zigzag = !zigzag;
				i = depth - 2;
			}
			else if(i == -1) {
				zigzag = !zigzag;
				i = 1;
			}
			res += mat[i][j];
			i = zigzag? i + 1 : i - 1;
		}
		
		return res.toUpperCase();
	}
	
	public String filter(String text) {
		int n = text.length();
		text = text.toLowerCase();
		String res = "";
		for(int i = 0; i < n; i++) {
			char c = text.charAt(i);
			if(c != ' ') res += c;
		}
		return res;
	}
	
	private void displayGrid() {
		for(int i = 0; i < depth; i++) {
			for(int j = 0; j < text.length(); j++)
				System.out.print(mat[i][j]);
			System.out.println();
		}
		System.out.println();
	}
	
}
