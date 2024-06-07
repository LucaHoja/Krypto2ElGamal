package org.example.rsa.Handler;


import org.example.rsa.Algorithms.Blockchiffre;
import org.example.rsa.Algorithms.ExtendedEuclidean;
import org.example.rsa.Algorithms.FastExponentiation;
import org.example.rsa.Algorithms.Utilities;
import org.example.rsa.PairTypes.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class EGHandler {

    private static final BigInteger _charSetSize = BigInteger.valueOf(55296);

    private int millerRabinTrials;
    private int primeNumberLength;


    public EGHandler(int millerRabinTrials, int primeNumberLength) {
        this.millerRabinTrials = millerRabinTrials;
        this.primeNumberLength = primeNumberLength;
    }

    public int getMillerRabinTrials() {
        return millerRabinTrials;
    }

    public void setMillerRabinTrials(int millerRabinTrials) {
        this.millerRabinTrials = millerRabinTrials;
    }

    public int getPrimeNumberLength() {
        return primeNumberLength;
    }

    public void setPrimeNumberLength(int primeNumberLength) {
        this.primeNumberLength = primeNumberLength;
    }


    public EncryptedMessage encryptMessage(String m, PublicKey key) {
        String filledMessage = Blockchiffre.fillMessage(m, calcBlockLength(key.getP()));
        ArrayList<BigInteger> bigIntList = Blockchiffre.messageToBigIntBlockList(filledMessage, calcBlockLength(key.getP()));
        BigInteger k = Utilities.getRandomBigInteger(BigInteger.ONE, key.getP().subtract(BigInteger.TWO));
        BigInteger a = FastExponentiation.exponentiation(key.getG(),k,key.getP());
        BigInteger bHelper = FastExponentiation.exponentiation(key.getY(),k,key.getP());
        ArrayList<BigInteger> encryptedList = new ArrayList<>();
        for (BigInteger block : bigIntList){
            encryptedList.add(block.multiply(bHelper).mod(key.getP()));
        }
        String encryptedMessage = Blockchiffre.bigIntListToString(encryptedList, calcBlockLength(key.getP())+1);
        return new EncryptedMessage(encryptedMessage, a);
    }

    public String decryptMessage(EncryptedMessage m, PrivateKey key){
        String filledMessage = Blockchiffre.fillMessage(m.getMessage(), calcBlockLength(key.getP())+1);
        BigInteger z = FastExponentiation.exponentiation(m.getA(), key.getP().subtract(BigInteger.ONE).subtract(key.getX()), key.getP());
        ArrayList<BigInteger> bigIntList = Blockchiffre.messageToBigIntBlockList(filledMessage, calcBlockLength(key.getP())+1);
        ArrayList<BigInteger> decryptedList = new ArrayList<>();
        for (BigInteger block: bigIntList){
            decryptedList.add(block.multiply(z).mod(key.getP()));
        }
        return Blockchiffre.bigIntListToString(decryptedList, calcBlockLength(key.getP()));
    }

    public Signature sign(String message, PrivateKey key) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(message.getBytes(StandardCharsets.UTF_8));
        BigInteger hashedInteger = new BigInteger(1, hashbytes);
        BigInteger k;
        do {
            k = Utilities.getRandomBigInteger(BigInteger.ZERO, BigInteger.TWO.pow(key.getP().bitLength()-1));
        } while (!ExtendedEuclidean.gcd(k,key.getP()).equals(BigInteger.ONE));
        //!k.gcd(key.getP().subtract(BigInteger.ONE)).equals(BigInteger.ONE)
        //BigInteger r = key.getG().modPow(k, key.getP());
        BigInteger r = FastExponentiation.exponentiation(key.getG(),k, key.getP());
        //BigInteger kInv = k.modInverse(key.getP().subtract(BigInteger.ONE));
        BigInteger kInv = ExtendedEuclidean.getModInverse(k,key.getP().subtract(BigInteger.ONE));
        BigInteger s = (hashedInteger.subtract(key.getX().multiply(r)).multiply(kInv)).mod(key.getP().subtract(BigInteger.ONE));

        return new Signature(r.toString(16),s.toString(16));
    }

    public boolean verify(String message, String rString, String sString, PublicKey key) throws NoSuchAlgorithmException {
        BigInteger r = new BigInteger(rString, 16);
        BigInteger s = new BigInteger(sString, 16);
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(message.getBytes(StandardCharsets.UTF_8));
        BigInteger hashedInteger = new BigInteger(1, hashbytes);
        if (r.compareTo(BigInteger.ONE) < 0 || r.compareTo(key.getP().subtract(BigInteger.ONE)) > 0) {
            return false;
        }

        BigInteger v1 = FastExponentiation.exponentiation(key.getY(),r,key.getP()).multiply(FastExponentiation.exponentiation(r,s,key.getP())).mod(key.getP());
        BigInteger v2 = FastExponentiation.exponentiation(key.getG(),hashedInteger,key.getP());

        return v1.equals(v2);
    }

    public static int calcBlockLength(BigInteger p) {
        int blockLength = 1;
        while (_charSetSize.pow(blockLength).compareTo(p) < 0) {
            blockLength++;
        }
        blockLength--;
        return blockLength;
    }

    public EGKeyPair generateEGKeyPair(){
        BigInteger[] securePrime = this.getSecurePrime();
        BigInteger p = securePrime[0];
        BigInteger g = securePrime[2];
        BigInteger a = BigInteger.TWO.pow(primeNumberLength - 1);
        BigInteger b = BigInteger.TWO.pow(primeNumberLength);
        BigInteger x;
        do {
            x = Utilities.getRandomBigInteger(a,b);
        } while (x.compareTo(BigInteger.ONE) <= 0 || x.compareTo(p.subtract(BigInteger.TWO)) >= 0);


        BigInteger y = g.modPow(x, p);

        PublicKey publicKey = new PublicKey(p, g, y);
        PrivateKey privateKey = new PrivateKey(p, g, x);
        return new EGKeyPair(privateKey, publicKey);
    }

    public BigInteger[] getSecurePrime(){
        BigInteger a = BigInteger.TWO.pow(primeNumberLength - 1);
        BigInteger b = BigInteger.TWO.pow(primeNumberLength);

        BigInteger q, p, g;

        long startTime = System.nanoTime();
        // Schritt 1: Berechnung von q und p = 2q + 1
//        do {
//            i++;
//            q = Utilities.generateRandomPrime(a,b,millerRabinTrials);
//            p = q.multiply(BigInteger.TWO).add(BigInteger.ONE);
//        } while (Utilities.isNotPrime(p,millerRabinTrials, 200));
        BigInteger[] primes = Utilities.generateRandomSafePrime(a,b,millerRabinTrials);
        assert primes != null;
        q = primes[0];
        p = primes[1];

        System.out.println("p: " + p);
        System.out.println("q: " + q);
        // Schritt 2: Finden einer Primitivwurzel g
        do {
            g = Utilities.getRandomBigInteger(BigInteger.TWO,b);
        } while (g.compareTo(BigInteger.ONE) <= 0 || g.compareTo(p.subtract(BigInteger.ONE)) >= 0 ||
                !FastExponentiation.exponentiation(g,q,p).equals(p.subtract(BigInteger.ONE)));
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Key Generation done it took: " + duration/1_000_000_000 + " seconds");

        return new BigInteger[]{p, q, g};
    }
}
