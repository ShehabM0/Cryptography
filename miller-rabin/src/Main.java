import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        BigInteger N = new BigInteger("12124908241");
        MillerRabin miller = new MillerRabin(N);
        System.out.println(miller.isProbablePrime());
    }
}
