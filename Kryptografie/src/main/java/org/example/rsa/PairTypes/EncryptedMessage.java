package org.example.rsa.PairTypes;

import java.math.BigInteger;

public class EncryptedMessage {
    private String message;
    private BigInteger a;

    public EncryptedMessage(String message, BigInteger a) {
        this.message = message;
        this.a = a;
    }

    public String getMessage() {
        return message;
    }

    public BigInteger getA() {
        return a;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
