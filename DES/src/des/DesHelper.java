package des;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class DesHelper {
    int[][] ip = new int[8][8];
    int[][] finalip = new int[8][8];
    
    int[][] boxperm = new int[4][8];
    int[][] pc1 = new int[8][7];
    int[][] pc2 = new int[8][6];
    int[] shift = new int[16];

    ArrayList<int[][]> boxes = new ArrayList<>();

    public void readTables() throws FileNotFoundException {
        // read tables files
        File file1 = new File("./src/des/tables/pc.txt");
        File file2 = new File("./src/des/tables/s-box.txt");
        File file3 = new File("./src/des/tables/shift.txt");
        File file4 = new File("./src/des/tables/s-box-perm.txt");
        Scanner scpc = new Scanner(file1);
        Scanner scbox = new Scanner(file2);
        Scanner scshift = new Scanner(file3);
        Scanner scboxperm = new Scanner(file4);
        
        // input
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 7; j++)
                pc1[i][j] = scpc.nextInt();
        
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 6; j++)
                pc2[i][j] = scpc.nextInt();
        
        for(int i = 0; i < 16; i++)
            shift[i] = scshift.nextInt();
        
        for(int i = 0; i < 8; i++) {
            int[][] a = new int [4][16];
            for(int j = 0; j < 4; j++)
                for(int k = 0; k < 16; k++)
                    a[j][k] = scbox.nextInt();
            boxes.add(a);
        }
        
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 8; j++)
                boxperm[i][j] = scboxperm.nextInt();
        
        scpc.close();
        scbox.close();
        scshift.close();
        scboxperm.close();
    }

    public String reverseStr(String s) {
        String res = "";
        for(int i = s.length() - 1; i > -1; i--)
            res += s.charAt(i);
        return res;
    }
    
    private int hexatoInt(char c) {
        return (c >= 'A' && c <= 'F') ?
                (c - 'A' + 10) : (c - '0');
    }

    // hexadecimal to binary 0-15
    public ArrayList<Integer> hexaToBin(String hexadecimal) {
        ArrayList<Integer> a = new ArrayList<>();
        
        for(int i = 0; i < hexadecimal.length(); i++) {
            String res = "", new_res = "";
            int val = hexatoInt(hexadecimal.charAt(i));
            while(val > 0) {
                res += (char) val % 2;
                val /= 2;
            }
            res = reverseStr(res);
            
            int res_sz = res.length();
            while(res_sz < 4) {
                new_res += "0";
                res_sz++;
            }
            new_res += res;
    
            for(int j = 0; j < 4; j++)
                a.add(new_res.charAt(j) - '0');
        }
        return a;
    }
    
    public int binToInt(String bin) {
        int n = bin.length(); 
        int res = 0;
        for(int i = n - 1; i > -1; i--)
            if(bin.charAt(i) == '1')
                res += Math.pow(2, n - i - 1);
        return res;
    }
    
    public String validateStrLen(String s, boolean is_hex_text) {
        int sz = (is_hex_text)? 16 : 8;
        String res = "0".repeat(sz);
        for(int i = 0; i < Math.min(sz, s.length()); i++)
            res = res.substring(0, i) + s.charAt(i) + res.substring(i + 1);
        return res;
    }
    
    // text to binary 0-255
    public ArrayList<Integer> textToBin(String s) {
        ArrayList<Integer> a = new ArrayList<>();
        
        for(int i = 0; i < s.length(); i++) {
            String res = "", new_res = "";
            int val = (int) s.charAt(i);
            while(val > 0) {
                res += (char) val % 2;
                val /= 2;
            }
            res = reverseStr(res);
            
            int res_sz = res.length();
            while(res_sz < 8) {
                new_res += "0";
                res_sz++;
            }
            new_res += res;
    
            for(int j = 0; j < 8; j++)
                a.add(new_res.charAt(j) - '0');
        }
        return a;
    }
    
    ///////// //////////

    /*
      key:     8x8 -> 64
      subkey:  8x7 -> 56
    */
    public void printnxm(ArrayList<Integer> s, int k) {
        for(int i = 0; i < s.size(); i++) {
            System.out.print(s.get(i));
            if((i + 1) % k == 0) System.out.print(" ");
        }
    }
    
    public void printnxm(int[][] a, int n, int m) { 
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++)
                System.out.print(a[i][j] + " ");
            System.out.println();
        }
    }
   
    
    public void printkeyshift(ArrayList<Pair> key_shift) {
        int cnt = 1;
        for(Pair p : key_shift) {
            System.out.print("C" + cnt + " :"); printnxm(p.x, 7);
            System.out.println();
            System.out.print("D" + cnt++ + " :"); printnxm(p.y, 7);
            System.out.println('\n');
        }
    }
    
    public void printroundkey(ArrayList<ArrayList<Integer>> roundkey) {
        int ik = 0, cnt = 1;
        for(ArrayList<Integer> key : roundkey) {
            System.out.print("K" + cnt++ + " :");
            for(int i : key) {
                ik++; ik %= 6;
                System.out.print(i);
                if(ik == 0) System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    
    public void printLR(ArrayList<Pair> LR) {
        System.out.println(LR.size());
        int j = 0;
        for(Pair p : LR) {
            System.out.print("L" + j + " :");
            printnxm(p.x, 4);
            System.out.println();
            System.out.print("R" + j++ + " :");
            printnxm(p.y, 4);
            System.out.println('\n');
        }
    }
    
    public void printsboxes(ArrayList<int[][]> boxes) {
        for(int[][] box : boxes) {
            for(int j = 0; j < 4; j++) {
                for(int k = 0; k < 16; k++)
                    System.out.print(box[j][k] + " ");
                System.out.println();
            }
            System.out.println();
        }
    }    
}

