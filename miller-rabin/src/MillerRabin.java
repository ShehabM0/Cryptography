import java.math.BigInteger;

public class MillerRabin {
    final BigInteger ZERO = BigInteger.ZERO;
    final BigInteger ONE = BigInteger.ONE;
    final BigInteger TWO = BigInteger.TWO;
    BigInteger N;
    
    MillerRabin(BigInteger N) {
        this.N = N;
    }
    
    public boolean isProbablePrime() {
        if(N.remainder(TWO).compareTo(ZERO) == 0) return false;

        BigInteger n = N.subtract(ONE);
        int k = 0;
        while(n.remainder(TWO.pow(k)).compareTo(ZERO) == 0) // n / 2^k
            k++;
        k--;
        BigInteger q = n.divide(TWO.pow(k)); // n = 2^k * q 

        for(BigInteger a = TWO; a.compareTo(n) < 0; a = a.add(ONE)) {
            if(a.gcd(N).compareTo(ONE) != 0) continue;
            
            BigInteger m = a.modPow(q, N);
            int i = 0;
            while(i < k) {
                int comp1 = m.compareTo(ONE);
                int comp2 = m.compareTo(n);
                if(i == 0) {
                    if(comp1 == 0 || comp2 == 0) {
                        System.out.println("Witness is: " + a);
                        return true;
                    }
                } else {
                    if(comp1 == 0) return false;
                    if(comp2 == 0) {
                        System.out.println("Witness is: " + a);
                        return true;
                    }
                }
                
                m = m.modPow(TWO, N);
                i++;
            }
        }

        return true;
    }

}
