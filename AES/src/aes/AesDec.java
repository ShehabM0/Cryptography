package aes;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class AesDec {
    AesHelper helper = new AesHelper();
    Key key;
    
    int state_rows = 4, state_cols = 4;
    
    ArrayList<ArrayList<Pair>> stateGrid = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<Pair>>> roundKey;
    Boolean is_hexa;
    
    AesDec(String plaintext, String key, Boolean is_hex_text) throws FileNotFoundException {
        helper.readTables();
        
        plaintext = validateStrLen(plaintext, is_hex_text);
        key = validateStrLen(key, is_hex_text);
        
        this.key = new Key(key, is_hex_text);
        this.roundKey = this.key.roundKey;
        this.is_hexa = is_hex_text;
        
        Collections.reverse(this.key.roundKey);
        
        initStateGrid();
        fillStateGrid(plaintext);
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
    
    private void initStateGrid() {
        for(int i = 0; i < state_rows; i++)
            stateGrid.add(new ArrayList<>());
        for(int i = 0; i < state_rows; i++)
            for(int j = 0; j < state_cols; j++)
                stateGrid.get(i).add(new Pair());
    }
    
    private void fillStateGrid(String plaintext) {
        for(int j = 0; j < state_cols; j++)
            for(int i = 0; i < state_rows; i++) {
                Pair p = new Pair();
                int cell_num = (state_rows * i) + j;
                p.f = plaintext.charAt(cell_num * 2);
                p.s = plaintext.charAt(cell_num * 2 + 1);
                stateGrid.get(j).add(i, p);
            }
    }
    
    
    private ArrayList<ArrayList<Pair>> AddRoundKey(ArrayList<ArrayList<Pair>> stateGrid, int roundKey_idx) {        
        
        ArrayList<ArrayList<Pair>> rkeyGrid = this.roundKey.get(roundKey_idx);
        ArrayList<ArrayList<Pair>> res = new ArrayList<>();
        
        
        
        for(int i = 0; i < state_rows; i++) {
            ArrayList<Pair> xor = xor(stateGrid.get(i), rkeyGrid.get(i));
            res.add(xor);
        }
              
        return res;
    }
    
    private ArrayList<ArrayList<Pair>> SubByte(ArrayList<ArrayList<Pair>> stateGrid) {
        ArrayList<ArrayList<Pair>> res = new ArrayList<>();
        ArrayList<Pair> sboxed_word = new ArrayList<>();
        
        for(ArrayList<Pair> a : stateGrid) {
            for(Pair p : a) {
                int c1 = helper.hexaToInt(p.f);
                int c2 = helper.hexaToInt(p.s);
                String hexa = helper.inv_sbox[c1][c2];
                hexa = hexa.toUpperCase();
                sboxed_word.add(new Pair(hexa.charAt(0), hexa.charAt(1)));
            }
            res.add((ArrayList<Pair>) sboxed_word.clone());
            sboxed_word.clear();
        }
        
        return res;
    }
    
    private ArrayList<ArrayList<Pair>> ShiftRows(ArrayList<ArrayList<Pair>> stateGrid) {
        for(int i = 0; i < state_rows; i++) {
            for(int k = 0; k < i; k++) { // shift i times
                Pair p = stateGrid.get(i).get(state_cols - 1);
                for(int j = state_cols - 2; j > -1; j--)
                    stateGrid.get(i).set(j + 1, stateGrid.get(i).get(j));
                stateGrid.get(i).set(0, p);
            }
        }

        return stateGrid;
    }
    
    private ArrayList<ArrayList<Pair>> MixColumns(ArrayList<ArrayList<Pair>> stateGrid) {
        ArrayList<ArrayList<Pair>> res = new ArrayList<>();
        String[][] matmutli = helper.inv_matmulti;
        
        for(int i = 0; i < state_rows; i++) {
            res.add(new ArrayList<Pair>());
            for(int j = 0; j < state_cols; j++)
                res.get(i).add(new Pair('0', '0'));            
        }
            
        
        for(int i = 0; i < state_rows; i++) {
            for(int j = 0; j < state_cols; j++) {
                for(int k = 0; k < state_rows; k++) {
                    String matmulti_str = matmutli[i][k];
                    Pair stateGrid_p = stateGrid.get(k).get(j);
                    Pair multi_res;
                    
                    int hexa_int = helper.hexaToInt(matmulti_str.charAt(1));
                    int X = 1;
                    if(hexa_int % 2 == 1) hexa_int--;
                    while(X < hexa_int) {
                        Pair ans = MixColumns02(stateGrid_p);
                        stateGrid_p = ans;
                        X *= 2;
                    }
                    
                    if(helper.hexaToInt(matmulti_str.charAt(1)) % 2 == 1)
                        stateGrid_p = xor(stateGrid_p, stateGrid.get(k).get(j));
                    multi_res = stateGrid_p;
                    
                    Pair current_state = res.get(i).get(j);
                    multi_res = xor(stateGrid_p, current_state);
                    res.get(i).set(j, multi_res);
                }
            }
        }
        
        return res;
    }
    
    private Pair MixColumns02(Pair stateGrid_p) {
        String stateGrid_str = stateGrid_p.f + "" + stateGrid_p.s;
        ArrayList<Integer> stateGrid_bin = helper.hexaToBin(stateGrid_str);
        if(stateGrid_bin.get(0) == 0) {
            ArrayList<Integer> shiftedStateGrid_bin = leftShift(stateGrid_bin, 1);
            return helper.binToHexa(shiftedStateGrid_bin);
        } else {
            ArrayList<Integer> shiftedStateGrid_bin = leftShift(stateGrid_bin, 1);
            Pair shiftedStateGrid_p = helper.binToHexa(shiftedStateGrid_bin);
            Pair matmulti02_p = new Pair('1', 'B');
            Pair xor_p = xor(shiftedStateGrid_p, matmulti02_p);
            return xor_p;
        }
    }
    
    private ArrayList<Pair> xor(ArrayList<Pair> word1, ArrayList<Pair> word2) {
        ArrayList<Pair> res = new ArrayList<>();
        for(int i = 0; i < state_cols; i++) {
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
    
    private ArrayList<Integer> leftShift(ArrayList<Integer> a, int k) {
        int n = a.size();
        for(int i = 0; i < k; i++) {
            for(int j = 1; j < n; j++)
                a.set(j - 1, a.get(j));
            a.set(n - 1, 0);
        }
        return a;
    }
    
    public String decrypt() {
        ArrayList<ArrayList<Pair>> addRoundKey, subByte, mixColumns;
        ArrayList<ArrayList<Pair>> shiftRows = new ArrayList<>();

        addRoundKey = AddRoundKey(stateGrid, 0); 
        for(int i = 1; i < 10; i++) {
            shiftRows = ShiftRows(addRoundKey);
            subByte = SubByte(shiftRows);
            addRoundKey = AddRoundKey(subByte, i); 
            stateGrid = MixColumns(shiftRows);
        }
        shiftRows = ShiftRows(addRoundKey);
        subByte = SubByte(shiftRows);
        stateGrid = AddRoundKey(subByte, 10); 

        
        String res = "";
        for(int j = 0; j < state_cols; j++)
            for(int i = 0; i < state_rows; i++) {
                Pair hexa = stateGrid.get(i).get(j);
//                if(this.is_hexa)
                    res += hexa.f + "" + hexa.s;
//                else {
//                    ArrayList<Integer> a = helper.hexaToBin(hexa.f + "" + hexa.s);
//                    res += (char) helper.binToInt(a);                    
//                }
            }
        
        return res;
    }
    
    public void printAAP(ArrayList<ArrayList<Pair>> a) {
        
        for(int i = 0; i < state_rows; i++) {
            for(int j = 0; j < state_cols; j++) {
                Pair p = a.get(i).get(j);
                System.out.print(p.f+ "" + p.s + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void printAP(ArrayList<Pair> a) {
        
        for(Pair p : a) {
            System.out.print(p.f + "" + p.s + " ");
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
}
