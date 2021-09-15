package io.github.edcod3.RSAnswer;

import java.math.BigInteger;
import java.util.HashMap;

public class RSAnswer {

    // HashMap of all relevant RSA Values
    public static HashMap<String, BigInteger> RsaValues = new HashMap<String, BigInteger>();

    // Plaintext Message
    public static String message;

    public static void main(String[] args) {
        String[] operation = Parser.ParseArgs(args);
        if (operation[0].equals("encrypt")) {
            Encrypt(operation[1]);
        } else if (operation[0].equals("attack")) {
            Attacks.main();
        } else {
            Decrypt(operation[1]);
        }
    }

    // Encrypt plaintext: m**e % n
    public static void Encrypt(String calculationType) {

        if (calculationType.equals("Modulus-Factors")) {
            // Calculate modulus if p & q given
            CalculateModulus();
        }

        BigInteger messageBigInt = Converter.StringToBigInt(message);
        BigInteger ciphertext = messageBigInt.modPow(RsaValues.get("exponent"), RsaValues.get("modulus"));

        System.out.printf("[*] Ciphertext: %d", ciphertext);
    }

    // Decrypt ciphertext: c**d % n
    public static void Decrypt(String calculationType) {
        if (calculationType.equals("Modulus-Factors")) {
            // Calculate modulus & d if p & q given
            CalculatePrivateKey();
            CalculateModulus();
        }

        BigInteger plaintextBigInt = RsaValues.get("ciphertext").modPow(RsaValues.get("d"), RsaValues.get("modulus"));
        String plaintext = Converter.BigIntToString(plaintextBigInt);

        System.out.printf("[*] Plaintext: %s", plaintext);
    }

    // Calculate d:
    // 1. calculate phi: (p - 1) * (q - 1)
    // 2. calculate d as the modular multiplicative inverse such that:
    // d * e mod phi = 1
    public static void CalculatePrivateKey() {
        BigInteger[] subtractedFactors = { RsaValues.get("p").subtract(BigInteger.valueOf(1)),
                RsaValues.get("q").subtract(BigInteger.valueOf(1)) };
        BigInteger phi = subtractedFactors[0].multiply(subtractedFactors[1]);
        BigInteger d = RsaValues.get("exponent").modInverse(phi);
        RsaValues.put("d", d);
    }

    // Calculate modulus: p * q
    public static void CalculateModulus() {
        BigInteger modulus = RsaValues.get("p").multiply(RsaValues.get("q"));
        RsaValues.remove("q");
        RsaValues.remove("p");
        RsaValues.put("modulus", modulus);
    }
}
