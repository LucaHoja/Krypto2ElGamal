package org.example.rsa.PairTypes;

public class EGKeyPair {
    private PrivateKey privKey;
    private PublicKey pubKey;

    public EGKeyPair(PrivateKey privKey, PublicKey pubKey) {
        this.privKey = privKey;
        this.pubKey = pubKey;
    }

    public PrivateKey getPrivKey() {
        return privKey;
    }

    public PublicKey getPubKey() {
        return pubKey;
    }
}
