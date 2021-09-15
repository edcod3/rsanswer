package io.github.edcod3.RSAnswer;

import java.math.BigInteger;

public class RSAnswer {

    // RSA Values for Encryption/Decyption
    // Index 0: exponent/ciphertext
    // Index 1: modulus or p/private key or p
    // Index 2: empty or q/modulus or q
    // Index 3: empty / empty or exponent
    public static BigInteger[] RsaValues = new BigInteger[4];

    public static String message;

    public static void main(String[] args) {
        String[] operation = Parser.ParseArgs(args);
        if (operation[0].equals("encrypt")) {
            Encrypt(operation);
        } else {
            Decrypt(operation);
        }
    }

    public static void Encrypt(String[] calculationType) {
        if (calculationType[1].equals("Modulus-Factors")) {
            BigInteger modulus = CalculateModulus(calculationType[0]);
            RsaValues[1] = modulus;
            RsaValues[2] = BigInteger.valueOf(0);
        }
        BigInteger messageBigInt = Converter.StringToBigInt(message);
        BigInteger ciphertext = messageBigInt.modPow(RsaValues[0], RsaValues[1]);
        System.out.printf("Ciphertext: %d", ciphertext);
    }

    public static void Decrypt(String[] calculationType) {
        if (calculationType[1].equals("Modulus-Factors")) {
            BigInteger privateKey = CalculatePrivateKey();
            BigInteger modulus = CalculateModulus(calculationType[0]);
            RsaValues[1] = privateKey;
            RsaValues[2] = modulus;
        }
        BigInteger plaintextBigInt = RsaValues[0].modPow(RsaValues[1], RsaValues[2]);
        String plaintext = Converter.BigIntToString(plaintextBigInt);
        System.out.printf("Plaintext: %s", plaintext);
    }

    public static BigInteger CalculatePrivateKey() {
        BigInteger[] subtractedFactors = { RsaValues[1].subtract(BigInteger.valueOf(1)),
                RsaValues[2].subtract(BigInteger.valueOf(1)) };
        BigInteger phi = subtractedFactors[0].multiply(subtractedFactors[1]);
        BigInteger d = RsaValues[3].modInverse(phi);
        return d;
    }

    public static BigInteger CalculateModulus(String operationType) {
        BigInteger modulus = RsaValues[1].multiply(RsaValues[2]);
        return modulus;
    }
}
