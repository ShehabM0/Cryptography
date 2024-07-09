package rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Key {
    final BigInteger ZERO = BigInteger.ZERO;
    final BigInteger ONE = BigInteger.ONE;
    final BigInteger TWO = BigInteger.TWO;
    final int bitLength = 1024;

    Pair publicKey, privateKey;

    Key() {
        BigInteger p = randomProbablePrime(bitLength);
        BigInteger q = randomProbablePrime(bitLength);
        while(q.compareTo(p) == 0) q = randomProbablePrime(bitLength);

        BigInteger n = p.multiply(q);
        BigInteger phin = (p.subtract(ONE)).multiply(q.subtract(ONE));
        
        BigInteger e = getE(phin);
        BigInteger d = e.modInverse(phin);
        
        this.publicKey = new Pair(e, n);
        this.privateKey = new Pair(d, n);

        System.out.println("P: " + p + "\nQ: " + q + '\n');
    }
    
    public BigInteger randomProbablePrime(int bitLength) {
        Random ran = new Random();
        BigInteger probable = BigInteger.probablePrime(bitLength, ran);
        return probable;
    }
    
    public BigInteger getE(BigInteger phi) {
        BigInteger e = getRandomBig(phi);
        while(e.gcd(phi).compareTo(ONE) != 0) e = getRandomBig(phi);
        return e;
    }

    public BigInteger getRandomBig(BigInteger r) {
        if(r.compareTo(new BigInteger("2")) <= 0) {
            System.out.println("Error!! UpperBound must be > 2.");
            return ONE;
        }
        Random rand = new Random();
        BigInteger e = new BigInteger(r.bitLength(), rand);
        while(e.compareTo(r) >= 0 || e.compareTo(ONE) <= 0) e = new BigInteger(r.bitLength(), rand);
        return e;
    }
    
    
    
                            ///////////////     ///////////////    
    public ArrayList<BigInteger> sieve(BigInteger N) { // 1e7
        Set<BigInteger> composite = new HashSet<BigInteger>();
        for(BigInteger i = TWO; i.compareTo(N) <= 0; i = i.add(ONE)) {
            if(composite.contains(i)) continue;
            for(BigInteger j = i.multiply(i); j.compareTo(N) <= 0; j = j.add(i))
                composite.add(j);
        }
        
        ArrayList<BigInteger> primes = new ArrayList<>();
        for(BigInteger i = TWO; i.compareTo(N) <= 0; i = i.add(ONE))
            if(!composite.contains(i))
                primes.add(i);
        
        return primes;
    }

    public ArrayList<BigInteger> probablePrime(BigInteger N) {
        ArrayList<BigInteger> primes = new ArrayList<>();
        primes.add(TWO);
        for(BigInteger i = TWO.add(ONE); i.compareTo(N) <= 0; i = i.add(TWO))
            if(i.isProbablePrime(1))
                primes.add(i);
        return primes;
    }
    
    public boolean isPrime(BigInteger N) {
        for(BigInteger i = TWO; (i.multiply(i)).compareTo(N) <= 0; i = i.add(ONE)) {
            BigInteger[] div_rem = N.divideAndRemainder(i);
            if(div_rem[1] == ZERO)
                return false;
        }
        return true;
    }
    public BigInteger getEloop(BigInteger l, BigInteger r) {
        BigInteger phi = r;
        ArrayList<BigInteger> elist = new ArrayList<>();

        for(BigInteger i = l; i.compareTo(r) < 0; i = i.add(ONE))
            if(i.gcd(phi).compareTo(ONE) == 0)
                elist.add(i);

        int idx = getRandomInt(0, elist.size() - 1);
        return elist.get(idx);
    }

    public int getRandomInt(int l, int r) {
        int val = (int) (Math.random() * (r - l + 1) + l);
        return val;
    }
}


class Pair {
    BigInteger f, s;
    Pair(BigInteger n, BigInteger m) {
        f = n;
        s = m;
    }
}
