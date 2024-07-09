package des;

import java.util.ArrayList;

public class Key {
    String k = "133457799BBCDFF1";
    DesHelper helper = new DesHelper();
    
    ArrayList<Integer> key = new ArrayList<>();
    ArrayList<Integer> subkey = new ArrayList<>(); // pc1
    ArrayList<Pair> key_shift = new ArrayList<>();
    ArrayList<ArrayList<Integer>> roundkey = new ArrayList<>(); // pc2
    
    int[][] pc1 = new int[8][7];
    int[] shift = new int[16];
    int[][] pc2 = new int[8][6];
    
    Key(int[][] pc1, int[]shift, int[][] pc2) {
        this.pc1 = pc1;
        this.shift = shift;
        this.pc2 = pc2;
    }
    
    public void build(String key) {
        this.k = key.toUpperCase();
        
        // build key
        this.key = helper.hexaToBin(k);
        
        // build sub key
        subkey = pc1(this.key);
        
        // build sub key 56bit
        ArrayList<Integer> C = new ArrayList<>();
        ArrayList<Integer> D = new ArrayList<>();
        int mid = subkey.size() / 2;
        for(int i = 0; i < mid; i++) C.add(subkey.get(i));
        for(int i = mid; i < subkey.size(); i++) D.add(subkey.get(i));
        
        for(int i = 0; i < 16; i++) {
            leftShift(C, shift[i]);
            leftShift(D, shift[i]);
            
            ArrayList<Integer> new_C = new ArrayList<>();
            ArrayList<Integer> new_D = new ArrayList<>();
            for(int j : C) new_C.add(j);
            for(int j : D) new_D.add(j);
            
            key_shift.add(new Pair(new_C, new_D));
        }
        
        // build round_key 48bit
        for(Pair p : key_shift) {
            ArrayList<Integer> Ci = p.x;
            ArrayList<Integer> Di = p.y;
            ArrayList<Integer> CDi = new ArrayList<>();
            for(int i : Ci) CDi.add(i);
            for(int i : Di) CDi.add(i);
            ArrayList<Integer> keyi = pc2(CDi);
            roundkey.add(keyi);
        }
        
        // debug
        System.out.print("K: ");
        helper.printnxm(this.key, 8); System.out.println();
        System.out.print("K*: ");
        helper.printnxm(this.subkey, 7); System.out.println();
        helper.printkeyshift(this.key_shift);
        helper.printroundkey(this.roundkey);
    }
    

    // key 64bit -> 56bit, 8multiplications discarded
    private ArrayList<Integer> pc1(ArrayList<Integer> key) {
        ArrayList<Integer> subkey = new ArrayList<>();
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 7; j++)
                subkey.add(key.get(pc1[i][j] - 1));
        return subkey;
    }
    
    public void leftShift(ArrayList<Integer> a, int k) {
        int n = a.size();
        for(int i = 0; i < k; i++) {
            int val = a.get(0);
            for(int j = 1; j < n; j++)
                a.set(j - 1, a.get(j));
            a.set(n - 1, val);
        }       
    }
    
    // key 56bit -> 48bit, 8multiplications discarded
    private ArrayList<Integer> pc2(ArrayList<Integer> CD) {
        ArrayList<Integer> key = new ArrayList<>();
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 6; j++)
                key.add(CD.get(pc2[i][j] - 1));
        return key;
    }
}


