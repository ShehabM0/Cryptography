package des;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Des {
    final String HEX = "0123456789ABCDEF";
    DesHelper helper = new DesHelper();
    
    String p = ""; // plain text
    String k = ""; // key
    
    ArrayList<Integer> plain = new ArrayList<>();
    ArrayList<Integer> plain_ip = new ArrayList<>();
    
    ArrayList<ArrayList<Integer>> roundkey;
    Key key;
    
    int n = 8;
    
    Des(String key) throws FileNotFoundException {
        this.k = key;
        
        // tables input
        helper.readTables();
        
        // build key
        buildKey(key);
        
        // build IP
        int k = 1;
        for(int j = n - 1; j > -1; j--) {
            for(int i = n / 2; i < n; i++) {
                helper.ip[i][j] = k++;
                helper.ip[i - 4][j] = k++;
            }
        }
        
        // build final IP
        k = 1;
        int j = 1;
        while(k < 65) {
            if(j >= n) j = 0;
            for(int i = n - 1; i > -1; i--)
                helper.finalip[i][j] = k++;
            j += 2;
        }
        
    }
    
    private void buildKey(String key) {
        this.key = new Key(helper.pc1, helper.shift, helper.pc2);
        this.key.build(key);
        roundkey = this.key.roundkey;
    }
    
    public String encrypt(String plaintext, boolean isEncrypt) {
        plaintext = plaintext.toUpperCase();
        plaintext = this.key.helper.validateStrLen(plaintext, true);
        this.p = plaintext;
        
        // build plain 64bit
        plain = this.key.helper.hexaToBin(p);
        
        // permutated plain text
        plain_ip = ip(plain);
        
        // L, R 32bit
        ArrayList<Pair> LR = new ArrayList<>();
        ArrayList<Integer> L = new ArrayList<>();
        ArrayList<Integer> R = new ArrayList<>();
        int mid = plain_ip.size() / 2;
        for(int i = 0; i < mid; i++) L.add(plain_ip.get(i));
        for(int i = mid; i < plain_ip.size(); i++) R.add(plain_ip.get(i));      
        LR.add(new Pair(L, R));
        buildLR(LR, isEncrypt);
        
        
        Pair p = LR.get(16);
        L = p.x;
        R = p.y;
        ArrayList<Integer> RL = new ArrayList<>();
        for(int i : R) RL.add(i);
        for(int i : L) RL.add(i);
        RL = finalip(RL); // 64bit
        
        String enc = getEncrypt(RL);
        return enc;
    }
    
    private String getEncrypt(ArrayList<Integer> a) {
        String res = "", temp = "";
        int ik = 0;
        for(int i = 0; i < a.size(); i++) {
            temp += (char)(a.get(i) + '0');
            ik++; ik %= 4;
            if(ik == 0) {
                int idx = helper.binToInt(temp);
                res += HEX.charAt(idx);
                temp = "";
            }
        }
        return res;
    }
    
    // rounds
    private void buildLR(ArrayList<Pair> LR, boolean isEncrypt) {
        int i = 1;
        while(i < 17) {
            ArrayList<Integer> Li_1 = LR.get(i - 1).x;
            ArrayList<Integer> Ri_1 = LR.get(i - 1).y;
            ArrayList<Integer> Ki = key.roundkey.get(isEncrypt? i - 1 : 17 - i - 1);

            ArrayList<Integer> f = f(Ri_1, Ki);
            ArrayList<Integer> Ri = new ArrayList<>();
            for(int j = 0; j < f.size(); j++) // 32bit
                Ri.add(Li_1.get(j) ^ f.get(j));
            LR.add(new Pair(Ri_1, Ri));
            i++;
        }
    }
    
    /*
     * expansion(Ri)
     * Ki
     * 
     * expansion(Ri) ^ Ki
     * 
     * S-Box(expansion(Ri) ^ Ki)
     * 
     * permutation(S-Box(expansion(Ri-1) + Ki))
     */
    private ArrayList<Integer> f(ArrayList<Integer> R, ArrayList<Integer> K) { // feistel
        R = expansion(R);
        ArrayList<Integer> R_XOR = new ArrayList<>();
        for(int i = 0; i < R.size(); i++)
            R_XOR.add(R.get(i) ^ K.get(i));
        ArrayList<Integer> R_box = sbox(R_XOR);
        ArrayList<Integer> R_perm = permutatebox(R_box);
        return R_perm;
    }
    
    private ArrayList<Integer> sbox(ArrayList<Integer> R) {
        int ibox = 0;
        int from = 0, to = 5;
        ArrayList<Integer> R_box = new ArrayList<>();
        
        while(to < 49) {
            int[][] box = helper.boxes.get(ibox); // 4x16
            // row
            String row_str = (char)(R.get(from) + '0') + "" + (char)(R.get(to) + '0');
            int row = helper.binToInt(row_str);
            // column
            String col_str = "";
            for(int i = from + 1; i < to; i++)
                col_str += (char)(R.get(i) + '0');
            int col = helper.binToInt(col_str);
            
            int box_val = box[row][col];
            String box_val_str = (char) (box_val + '0') + "";
            ArrayList<Integer> box_val_bin = key.helper.hexaToBin(box_val_str);
            for(int i : box_val_bin)
                R_box.add(i);
            
            from = to + 1;
            to = from + 5;
            ibox++;
        }
        return R_box; 
    }
    
    private ArrayList<Integer> permutatebox(ArrayList<Integer> R) {
        ArrayList<Integer> R_perm = new ArrayList<>();
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 8; j++)
                R_perm.add(R.get(helper.boxperm[i][j] - 1));
        return R_perm;
    }
    
    private ArrayList<Integer> ip(ArrayList<Integer> plain) {
        ArrayList<Integer> plain_ip = new ArrayList<>();
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                plain_ip.add(plain.get(helper.ip[i][j] - 1));
        return plain_ip;
    }
    
    private ArrayList<Integer> finalip(ArrayList<Integer> a) {
        ArrayList<Integer> res = new ArrayList<>();
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                res.add(a.get(helper.finalip[i][j] - 1));
        return res;
    }
    
    // 32bit -> 48bit
    private ArrayList<Integer> expansion(ArrayList<Integer> a) {
        int n = a.size(); // 32bit
        ArrayList<Integer> res = new ArrayList<>();
        int ik = 0;
        for(int i = 0; i < n; i++) {
            if(ik == 0) res.add(a.get((i - 1 + n) % n));
            res.add(a.get(i));
            if(ik == 3) res.add(a.get((i + 1) % n));
            ik++; ik %= 4;
        }
        
        return res;
    }
    
    public String encrypt(String plaintext) {
        return encrypt(plaintext, true);
    }
    
    public String decrypt(String ciphertext) {
        return encrypt(ciphertext, false);
    }
}

/*
Ln = Rn-1
Rn = Ln-1 + f(Rn-1, kn)

-- Coded tables --
IP
Expansion
Final IP
*/

