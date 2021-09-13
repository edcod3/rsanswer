package io.github.edcod3.RSAnswer;

import java.math.BigInteger;

public class Converter {

    public static BigInteger StringToBigInt(String message) {
        byte[] messageBytes = message.getBytes();
        return new BigInteger(messageBytes);
    }

    public static String BigIntToString(BigInteger messageBigInt) {
        byte[] messageBytes = messageBigInt.toByteArray();
        return new String(messageBytes);
    }
}
