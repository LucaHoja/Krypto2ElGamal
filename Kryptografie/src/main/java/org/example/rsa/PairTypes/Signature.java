package org.example.rsa.PairTypes;

import java.math.BigInteger;

public class Signature {
    private String r;
    private String s;

    public Signature(String r, String s) {
        this.r = r;
        this.s = s;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return  "r=" + r + "\n" +
                ", s=" + s;
    }
}
