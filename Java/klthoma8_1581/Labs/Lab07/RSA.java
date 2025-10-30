import java.math.BigInteger;
import java.util.Random;

public class RSA {

    private BigInteger n, d, e;

    // Constructor for RSA with p, q, and e as input strings
    public RSA(String p, String q, String e) {
        BigInteger pNum = new BigInteger(p);
        BigInteger qNum = new BigInteger(q);
        n = pNum.multiply(qNum);
        BigInteger totient = (pNum.subtract(BigInteger.ONE)).multiply(qNum.subtract(BigInteger.ONE));
        this.e = new BigInteger(e);
        this.d = this.e.modInverse(totient); // Use this.e which is a BigInteger
    }

    // Method to calculate the totient
    public BigInteger totient(BigInteger p, BigInteger q) {
        return (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    }

    // Method to generate a random e value
    public BigInteger generateE(BigInteger p, BigInteger q) {
        BigInteger totient = totient(p, q);
        Random randomN = new Random();
        BigInteger e;

        do {
            e = new BigInteger(totient.bitLength(), randomN);
        } while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(totient) >= 0 || !e.gcd(totient).equals(BigInteger.ONE));

        return e;
    }

    // Method to generate d (private key)
    public BigInteger generateD(BigInteger e, BigInteger totient) {
        return e.modInverse(totient);
    }

    // Method to encrypt a message
    public String encrypt(String message) {
        return new BigInteger(message.getBytes()).modPow(e, n).toString();
    }

    // Method to decrypt a message
    public String decrypt(String message) {
        return new String(new BigInteger(message).modPow(d, n).toByteArray());
    }
}
