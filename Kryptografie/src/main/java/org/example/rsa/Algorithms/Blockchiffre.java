package org.example.rsa.Algorithms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Blockchiffre {
    private static final BigInteger _charSetSize = BigInteger.valueOf(55296);
    private static final String messageFiller = "\u0000";

    /**
     * Berechnung Blocklänge nach IT-Security Skript
     * charSetSize = _charSetSize = 55295
     *
     * @param n = p*q
     */
    public static int calculateBlockLength(BigInteger n) {
        int charBlockLength = 1;

        while (_charSetSize.pow(charBlockLength).compareTo(n) < 0) {
            charBlockLength++;
        }

        charBlockLength--;
        return charBlockLength;
    }

    /**
     * checks correctness of block length based on script
     *
     * @param charBlockLength = Blocklänge
     * @param n               = p*q
     * @return True if blocklength correct
     */
    private static boolean checkBlockLength(int charBlockLength, BigInteger n) {
        return _charSetSize.pow(charBlockLength).compareTo(n) < 0
                && n.compareTo(_charSetSize.pow(charBlockLength + 1)) < 0;
    }

    /**
     * Auffüllen der Länge des Textes auf eine Länge die ganzzahlig durch die Blocklänge teilbar ist
     *
     * @param message         = gesamter Klartext
     * @param charBlockLength = Blocklänge
     * @return FilledMessage
     */
    public static String fillMessage(String message, int charBlockLength) {
        StringBuilder filledMessage = new StringBuilder(message);
        if (!(message.length() % charBlockLength == 0)) {
            int messageLengthDifference = charBlockLength - (message.length() % charBlockLength);
            filledMessage.append(messageFiller.repeat(messageLengthDifference));
        }
        return filledMessage.toString();
    }


    /**
     * message to BigInteger Block representation
     *
     * @param message = message
     * @param blockLength = Blöcklänge
     * @return
     */
    public static ArrayList<BigInteger> messageToBigIntBlockList(String message, int blockLength) {
        List<Integer> unicodeList = messageToIntList(message);
        ArrayList<BigInteger> messageInt = new ArrayList<>();

        int messageLength = message.length();
        for (int i = 0; i < messageLength; i += blockLength) {
            BigInteger blockValue = BigInteger.ZERO;
            for (int j = 0; j < blockLength; j++) {
                blockValue = blockValue.multiply(_charSetSize)
                        .add(BigInteger.valueOf(unicodeList.get(i + j)));
            }
            messageInt.add(blockValue);
        }

        return messageInt;
    }

    /**
     * message to integer representation
     *
     * @param message = message
     * @return List of Char -> Int Representation
     */
    private static ArrayList<Integer> messageToIntList(String message) {
        ArrayList<Integer> messageInt = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            messageInt.add((int) message.charAt(i));
        }
        return messageInt;
    }

    /**
     * BigInteger Block representation to message
     *
     * @param bigIntMessage = Block Representation of a String
     * @param blockLength = Blocklänge
     * @return message
     */
    public static String bigIntListToString(ArrayList<BigInteger> bigIntMessage, int blockLength) {
        List<Integer> unicodeList = new ArrayList<>();
        for (BigInteger cipherBlock : bigIntMessage) {
            List<Integer> unicodeBlockList = new ArrayList<>();
            for (int i = 0; i < blockLength; i++) {
                BigInteger unicode = cipherBlock.mod(_charSetSize);
                unicodeBlockList.add(unicode.intValue());
                cipherBlock = cipherBlock.divide(_charSetSize);
            }
            Collections.reverse(unicodeBlockList);
            unicodeList.addAll(unicodeBlockList);
        }

        StringBuilder unicodeMessage = new StringBuilder();
        for (int number : unicodeList) {
            unicodeMessage.appendCodePoint(number);
        }

        return unicodeMessage.toString();
    }
}
