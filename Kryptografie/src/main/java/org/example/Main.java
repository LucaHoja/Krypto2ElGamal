package org.example;

import org.example.rsa.Algorithms.ExtendedEuclidean;
import org.example.rsa.Algorithms.Utilities;
import org.example.rsa.Handler.EGHandler;
import org.example.rsa.PairTypes.EGKeyPair;
import org.example.rsa.PairTypes.EncryptedMessage;
import org.example.rsa.PairTypes.Signature;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        //
        long startTime = System.nanoTime();
        EGHandler handler = new EGHandler(100, 256);
        EGKeyPair t= handler.generateEGKeyPair();
        EGKeyPair b= handler.generateEGKeyPair();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration);
        System.out.println(Utilities.getM());
        startTime = System.nanoTime();
        EncryptedMessage e = handler.encryptMessage("Dies ist der allererste Test und eine richtig gute Nachricht", t.getPubKey());
        String d = handler.decryptMessage(e, t.getPrivKey());
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println(duration);
        System.out.println(d);
        startTime = System.nanoTime();
        Signature signature = handler.sign("Dies ist der allererste Test und eine richtig gute Nachricht", t.getPrivKey());
        boolean verified = handler.verify("Dies ist der allererste Test und eine richtig gute Nachricht", signature.getR(), signature.getS(), t.getPubKey());
        //BigInteger t = BigInteger.probablePrime(256 - 1, random);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println(duration);
        System.out.println(verified);
        System.out.println(Utilities.getM());
//        long startTime = System.nanoTime();
//        BigInteger[] p = Utilities.generateRandomSafePrime(1024, 100);
//        long endTime = System.nanoTime();
//        long duration = (endTime - startTime);
//        System.out.println(duration);
//        System.out.println(p[1]);
//
//        startTime = System.nanoTime();
//        BigInteger[] q = ElGamalKeyGenerator.generateSecurePrime(1024);
//        endTime = System.nanoTime();
//        duration = (endTime - startTime);
//        System.out.println(duration);
//        System.out.println(q[0]);
//        System.out.println(q[1]);


//        long startTime = System.nanoTime();
//        Utilities.generateListOfRandomNumbers2(BigInteger.TWO,BigInteger.TWO.pow(512),200,200);
//        long endTime = System.nanoTime();
//        long duration = (endTime - startTime);
//        System.out.println(duration);

//        BigInteger t = BigInteger.valueOf(37);
//        BigInteger x = t.modInverse(BigInteger.valueOf(26));
//        System.out.println(x);
//        x = ExtendedEuclidean.getModInverse(t, BigInteger.valueOf(26));
//        System.out.println(x);
    }
}