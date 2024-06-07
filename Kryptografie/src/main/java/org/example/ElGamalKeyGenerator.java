package org.example;

import java.math.BigInteger;
import java.security.SecureRandom;

public class ElGamalKeyGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static void main(String[] args) {
        int bitLength = 1024; // Beispiel für die gewünschte Bitlänge
        long startTime = System.nanoTime();
        ElGamalKeyPair keyPair = generateElGamalKeyPair(bitLength);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.println(duration);
        System.out.println("Öffentlicher Schlüssel (p, g, y):");
        System.out.println("p: " + keyPair.getP());
        System.out.println("g: " + keyPair.getG());
        System.out.println("y: " + keyPair.getY());
        System.out.println("Privater Schlüssel x: " + keyPair.getX());
    }

    public static ElGamalKeyPair generateElGamalKeyPair(int bitLength) {
        // Schritt 1: Erzeuge sichere Primzahl p und Primitivwurzel g
        BigInteger[] securePrimeAndRoot = generateSecurePrime(bitLength);
        BigInteger p = securePrimeAndRoot[0];
        BigInteger g = securePrimeAndRoot[2];

        // Schritt 2: Wähle zufällige Zahl x
        BigInteger x;
        do {
            x = new BigInteger(bitLength - 1, random);
        } while (x.compareTo(BigInteger.ONE) <= 0 || x.compareTo(p.subtract(BigInteger.TWO)) >= 0);

        // Schritt 3: Berechne y = g^x mod p
        BigInteger y = g.modPow(x, p);

        return new ElGamalKeyPair(p, g, y, x);
    }

    public static BigInteger[] generateSecurePrime(int bitLength) {
        BigInteger q, p, g;
        int i = 0;
        // Berechnung von q und p = 2q + 1
        do {
            q = BigInteger.probablePrime(bitLength - 1, random);
            p = q.multiply(BigInteger.TWO).add(BigInteger.ONE);
        } while (!p.isProbablePrime(1000));

        // Finden einer Primitivwurzel g
        do {
            g = new BigInteger(bitLength, random);
        } while (g.compareTo(BigInteger.ONE) <= 0 || g.compareTo(p.subtract(BigInteger.ONE)) >= 0 ||
                !g.modPow(q, p).equals(p.subtract(BigInteger.ONE)));

        return new BigInteger[]{p, q, g};
    }

    static class ElGamalKeyPair {
        private final BigInteger p;
        private final BigInteger g;
        private final BigInteger y;
        private final BigInteger x;

        public ElGamalKeyPair(BigInteger p, BigInteger g, BigInteger y, BigInteger x) {
            this.p = p;
            this.g = g;
            this.y = y;
            this.x = x;
        }

        public BigInteger getP() {
            return p;
        }

        public BigInteger getG() {
            return g;
        }

        public BigInteger getY() {
            return y;
        }

        public BigInteger getX() {
            return x;
        }
    }
}
