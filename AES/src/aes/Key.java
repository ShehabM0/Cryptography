package aes;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Key {
    AesHelper helper = new AesHelper();
    
    int key_rows = 4, key_cols = 4;
    int words_rows = 44, words_cols = 4;
    ArrayList<ArrayList<Pair>> keyGrid = new ArrayList<>();
    ArrayList<ArrayList<Pair>> words = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<Pair>>> roundKey = new ArrayList<>();
    
    Key(String key, Boolean is_hex_text) throws FileNotFoundException {
        helper.readTables();
        
        initKeyGrid();
        fillKeyGrid(key);
        
        initWordsGrid();
        fillWordsGrid();
        
        
        generateWords();
//        printWordsGrid();
        
        initRoundKey();
        transposeRoundKey();
//        printRoundKey();
        
    }
    
    public String validateStrLen(String s, Boolean is_hex_text) {
        int sz;
        char fill_char;
        if(is_hex_text) {
            sz = 2 * 16;
            fill_char = '0';
            s = s.toUpperCase();
        } else {
            sz = 16;
            fill_char = Character.MIN_VALUE;
        }
        
        String res = "";
        for(int i = 0; i < sz; i++) res += fill_char;
        
        for(int i = 0; i < Math.min(sz, s.length()); i++)
            res = res.substring(0, i) + s.charAt(i) + res.substring(i + 1);
        
        if(!is_hex_text) {
            String hexa = "";
            for(int i = 0; i < sz; i++)
                hexa += helper.charTextToHexa(res.charAt(i));
            return hexa;
        }
        
        return res;
    }

    private void initKeyGrid() {
        for(int i = 0; i < key_rows; i++)
            keyGrid.add(new ArrayList<>());
        for(int i = 0; i < key_rows; i++)
            for(int j = 0; j < key_cols; j++)
                keyGrid.get(i).add(new Pair());
    }
    
    private void fillKeyGrid(String key) {
        for(int j = 0; j < key_cols; j++)
            for(int i = 0; i < key_rows; i++) {
                Pair p = new Pair();
                int cell_num = (key_rows * i) + j;
                p.f = key.charAt(cell_num * 2);
                p.s = key.charAt(cell_num * 2 + 1);
                keyGrid.get(j).add(i, p);
            }
    }
    
    private void initWordsGrid() {
        for(int i = 0; i < words_rows; i++)
            words.add(new ArrayList<Pair>());
    }
    
    // w0, w1, w2, w3
    private void fillWordsGrid() {
        for(int j = 0; j < key_cols; j++) {
            ArrayList<Pair> word = new ArrayList<>();
            for(int i = 0; i < key_rows; i++)
                word.add(keyGrid.get(i).get(j));
            words.set(j, word);
        }
    }
    
    private void generateWords() {
        int round = 0;
        ArrayList<Pair> w = words.get(3);
        ArrayList<Pair> shifted_word = RotWord(w);
        ArrayList<Pair> sboxed_word = SubWord(shifted_word);
        ArrayList<Pair> z = g(sboxed_word, round++);
        for(int i = 4; i < 44; i++) {
            ArrayList<Pair> left_xor = words.get(i - 1);
            ArrayList<Pair> right_xor = words.get(i - 4);
            if(i % 4 == 0) {
                left_xor = words.get(i - 4);
                right_xor = z;
            }
            ArrayList<Pair> xor = xor(left_xor, right_xor); 
            words.set(i, xor);
            
            if((i + 1) % 4 == 0 && round < 10) {
                w = words.get(i);
                shifted_word = RotWord(w);
                sboxed_word = SubWord(shifted_word);
                z = g(sboxed_word, round++);
            }
        }
    }
    
    private ArrayList<Pair> RotWord(ArrayList<Pair> word) {
        word = (ArrayList<Pair>) word.clone();
        int n = word.size();
        int k = 1;
        for(int i = 0; i < k; i++) {
            Pair p = word.get(0);
            for(int j = 1; j < n; j++)
                word.set(j - 1, word.get(j));
            word.set(n - 1, p);
        }
        return word;
    }
    
    private ArrayList<Pair> SubWord(ArrayList<Pair> word) {
        ArrayList<Pair> sboxed_word = new ArrayList<>();
        for(Pair p : word) {
            int c1 = helper.hexaToInt(p.f);
            int c2 = helper.hexaToInt(p.s);
            String hexa = helper.sbox[c1][c2];
            hexa = hexa.toUpperCase();
            sboxed_word.add(new Pair(hexa.charAt(0), hexa.charAt(1)));
        }
        return sboxed_word;
    }
    
    private ArrayList<Pair> g(ArrayList<Pair> word, int rcon_idx) {
        ArrayList<Pair> z = new ArrayList<>();
        
        String rcon_str = helper.rcon[rcon_idx];
        
        Pair word_p = word.get(0);
        Pair rcon_p = new Pair(rcon_str.charAt(0), rcon_str.charAt(1));
        
        Pair xor = xor(word_p, rcon_p);

        z.add(xor);
        for(int j = 1; j < words_cols; j++)
            z.add(word.get(j));
        
        return z;
    }
    
    private ArrayList<Pair> xor(ArrayList<Pair> word1, ArrayList<Pair> word2) {
        ArrayList<Pair> res = new ArrayList<>();
        
        for(int i = 0; i < (int) word1.size(); i++) {
            Pair p = xor(word1.get(i), word2.get(i));
            res.add(p);
        }
        
        return res;
    }
    
    private Pair xor(Pair word1, Pair word2) {
        String word1_str = word1.f + "" + word1.s;
        ArrayList<Integer> word1_bin = helper.hexaToBin(word1_str);
        
        String word2_str = word2.f + "" + word2.s;
        ArrayList<Integer> word2_bin = helper.hexaToBin(word2_str);
        
        ArrayList<Integer> xor = new ArrayList<>();
        for(int i = 0; i < (int) word1_bin.size(); i++)
            xor.add(word1_bin.get(i) ^ word2_bin.get(i));
        
        Pair hexa = helper.binToHexa(xor);
        
        return hexa;
    }
    
    public void initRoundKey() {
        ArrayList<ArrayList<Pair>> rKey = new ArrayList<>();
        for(int i = 0; i < words_rows; i++) {
            rKey.add(words.get(i));
            if((i + 1) % 4 == 0) {
                roundKey.add((ArrayList<ArrayList<Pair>>) rKey.clone());
                rKey.clear();
            }
        }
    }
    
    public void transposeRoundKey() {
        ArrayList<ArrayList<ArrayList<Pair>>> res = new ArrayList<>();
        ArrayList<ArrayList<Pair>> rKey_matrix = new ArrayList<>();
        ArrayList<Pair> rKey = new ArrayList<>();
        
        for(ArrayList<ArrayList<Pair>> matrix : roundKey) {
            for(int j = 0; j < 4; j++) {
                for(int i = 0; i < 4; i++)
                    rKey.add(matrix.get(i).get(j));
                rKey_matrix.add((ArrayList<Pair>) rKey.clone());
                rKey.clear();
            }
            res.add((ArrayList<ArrayList<Pair>>) rKey_matrix.clone());
            rKey_matrix.clear();
        }
        
        this.roundKey = res;
    }
    
public void printAAP(ArrayList<ArrayList<Pair>> a) {
        
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                Pair p = a.get(i).get(j);
                System.out.print(p.f+ "" + p.s + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printKeyGrid() {
        System.out.println("------key------");
        for(int i = 0; i < key_rows; i++) {
            for(int j = 0; j < key_cols; j++) {
                Pair p = keyGrid.get(i).get(j);
                System.out.print(p.f+ "" + p.s + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void printWordsGrid() {
        System.out.println("------words------");
        for(int i = 0; i < words_rows; i++) {
            System.out.print("w" + i + ": ");
            for(Pair p : words.get(i)) {
                System.out.print(p.f + "" + p.s + " ");
            }
            if((i + 1) % 4 == 0) System.out.println("\n-----------");
            else System.out.println();
        }
        System.out.println();
    }
    
    public void printRoundKey() {
        for(ArrayList<ArrayList<Pair>> a : roundKey) {
            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 4; j++)
                    System.out.print(a.get(i).get(j).f + "" + a.get(i).get(j).s + " ");
                System.out.println();
            }
            System.out.println("-----------");
        }
        System.out.println();
    }
    
    public void printSingleRoundKey(int idx) {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++)
                System.out.print(roundKey.get(idx).get(i).get(j).f + "" + roundKey.get(idx).get(i).get(j).s + " ");
            System.out.println();
        }
        System.out.println();
    }
    
    public void printHexa(ArrayList<Pair> a) {
        System.out.println("------word------");
        for(Pair p : a)
            System.out.print(p.f+ "" + p.s + " ");
        System.out.println();
    }
}
