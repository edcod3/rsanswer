package io.github.edcod3.RSAnswer;

import java.math.BigInteger;

public class RSAnswer {

    // RSA Values for Encryption/Decyption
    // Index 0: exponent/ciphertext
    // Index 1: modulus/private key
    // Index 2: empty/modulus
    public static BigInteger[] RsaValues = new BigInteger[3];

    public static String message;

    public static void main(String[] args) {
        String operation = Parser.ParseArgs(args);
        if (operation.equals("encrypt")) {
            Encrypt();
        } else {
            Decrypt();
        }
    }

    public static void Encrypt() {
        BigInteger messageBigInt = Converter.StringToBigInt(message);
        System.out.println(messageBigInt);
        BigInteger ciphertext = messageBigInt.modPow(RsaValues[0], RsaValues[1]);
        System.out.printf("Ciphertext: %d", ciphertext);
    }

    public static void Decrypt() {
        // FIXME: Correctly decrypt ciphertext.
        // Currently the decryption prints out an incorrect plaintext.
        BigInteger plaintextBigInt = RsaValues[0].modPow(RsaValues[1], RsaValues[2]);
        System.out.println(plaintextBigInt);
        String plaintext = Converter.BigIntToString(plaintextBigInt);
        System.out.printf("Plaintext: %s", plaintext);
    }
}
