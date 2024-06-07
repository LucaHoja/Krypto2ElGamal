package org.example.rsa.PairTypes;

import java.math.BigInteger;

public class PublicKey{
    private BigInteger p;
    private BigInteger g;
    private BigInteger y;


    public PublicKey(BigInteger p, BigInteger g, BigInteger y) {
        this.p = p;
        this.g = g;
        this.y = y;
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
    @Override
    public String toString() {
        return "p=" + this.getP() + "\n\n g=" + this.getG() + "\n\n y=" + this.getY();
    }
}
