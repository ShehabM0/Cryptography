package aes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AesHelper {
    final String HEX = "0123456789ABCDEF";
    
    String[][] sbox = new String[16][16];
    String[] rcon = new String[10];
    String[][] matmulti = new String[4][4];
    
    String[][] inv_sbox = new String[16][16];
    String[][] inv_matmulti = new String[4][4];

    public void readTables() throws FileNotFoundException {
        // read tables files
        File file1 = new File("./src/aes/tables/rcon.txt");
        File file2 = new File("./src/aes/tables/s-box.txt");
        File file3 = new File("./src/aes/tables/matrix-multi.txt");
        File file4 = new File("./src/aes/tables/inv-s-box.txt");
        File file5 = new File("./src/aes/tables/inv-matrix-multi.txt");
        Scanner scrcon = new Scanner(file1);
        Scanner scsbox = new Scanner(file2);
        Scanner scmatmulti = new Scanner(file3);
        Scanner scinvsbox = new Scanner(file4);
        Scanner scinvmatmulti = new Scanner(file5);
        
        // input
        for(int i = 0; i < 16; i++)
            for(int j = 0; j < 16; j++)
                sbox[i][j] = scsbox.next();
        
        for(int i = 0; i < 10; i++)
            rcon[i] = scrcon.next();
        
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                matmulti[i][j] = scmatmulti.next();
        
        for(int i = 0; i < 16; i++)
            for(int j = 0; j < 16; j++)
                inv_sbox[i][j] = scinvsbox.next();
        
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                inv_matmulti[i][j] = scinvmatmulti.next();
        
        scrcon.close();
        scsbox.close();
        scmatmulti.close();
        scinvsbox.close();
        scinvmatmulti.close();
    }
    
    public String reverseStr(String s) {
        String res = "";
        for(int i = s.length() - 1; i > -1; i--)
            res += s.charAt(i);
        return res;
    }
    
    // hexadecimal(0-9, A-F) -> integer(0-15)
    public int hexaToInt(char c) {
        c = Character.toUpperCase(c);
        return (c >= 'A' && c <= 'F') ?
                (c - 'A' + 10) : (c - '0');
    }
    
    // integer(0-15) -> hexadecimal(0-9, A-F)
    public char intToHexa(int n) {
        return HEX.charAt(n);
    }
    
    // string hexadecimal(0-9, A-F) -> 4bit-binary
    public ArrayList<Integer> hexaToBin(String hexadecimal) {
        ArrayList<Integer> bin = new ArrayList<>();
        
        for(int i = 0; i < hexadecimal.length(); i++) {
            String res = "";
            int val = hexaToInt(hexadecimal.charAt(i));
            while(val > 0) {
                res += (char) val % 2;
                val /= 2;
            }
            
            int res_sz = res.length();
            while(res_sz < 4) {
                res += "0";
                res_sz++;
            }
            res = reverseStr(res);
    
            for(int j = 0; j < 4; j++)
                bin.add(res.charAt(j) - '0');
        }
        
        return bin;
    }
    
    // 8bit-binary -> hexadecimal(0-9, A-F)
    public Pair binToHexa(ArrayList<Integer> bin) {
        String left_bin = "", right_bin = "";
        int mid = bin.size() / 2;
        for(int i = 0; i < mid; i++) left_bin += (char)(bin.get(i) + '0');
        for(int i = mid; i < (int) bin.size(); i++) right_bin += (char)(bin.get(i) + '0');

        int left_int = binToInt(left_bin);
        int right_int = binToInt(right_bin);
        
        char left_hexa = intToHexa(left_int);
        char right_hexa = intToHexa(right_int);
        
        return new Pair(left_hexa, right_hexa);
    }
    
    public int binToInt(String bin) {
        int n = bin.length(); 
        int res = 0;
        for(int i = n - 1; i > -1; i--)
            if(bin.charAt(i) == '1')
                res += Math.pow(2, n - i - 1);
        return res;
    }
    
    public int binToInt(ArrayList<Integer> bin) {
        int n = bin.size(); 
        int res = 0;
        for(int i = n - 1; i > -1; i--)
            if(bin.get(i) == 1)
                res += Math.pow(2, n - i - 1);
        return res;
    }
    
    // integer(0-255) -> 8bit-binary
    public ArrayList<Integer> decimalToBin(int decimal) {
        ArrayList<Integer> bin = new ArrayList<>();
        
        String res = "";
        while(decimal > 0) {
            res += (char) decimal % 2;
            decimal /= 2;
        }
        
        int res_sz = res.length();
        while(res_sz < 8) {
            res += "0";
            res_sz++;
        }
        res = reverseStr(res);

        for(int j = 0; j < 8; j++)
            bin.add(res.charAt(j) - '0');
            
        return bin;
    }
    
    public String charTextToHexa(char text_char) {
        String hexa = "";
        
        ArrayList<Integer> bin = decimalToBin((int) text_char);
        
        Pair hexa_p = binToHexa(bin);
        
        hexa = hexa_p.f + "" + hexa_p.s;
        return hexa;
    }
}

class Pair {
    char f, s;
    Pair() {
        this.f = this.s = '#';
    }
    Pair(char c1, char c2) {
        this.f = c1;
        this.s = c2;
    }
}

/*
for(int i = 0; i < 16; i++) {
    for(int j = 0; j < 16; j++)
        System.out.print(sbox[i][j] + " ");
    System.out.println();
}
*/