package org.example.rsa.PairTypes;

import java.math.BigInteger;

public class PrivateKey{

    private BigInteger p;
    private BigInteger x;
    private BigInteger g;

    public PrivateKey(BigInteger p,BigInteger g, BigInteger x) {
        this.p = p;
        this.g = g;
        this.x = x;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getG() {
        return g;
    }

    @Override
    public String toString() {
        return "x=" + this.getX();
    }
}
