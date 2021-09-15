package io.github.edcod3.RSAnswer;

import java.math.BigInteger;
import java.util.HashMap;

public class RSAnswer {

    public static HashMap<String, BigInteger> RsaValues = new HashMap<String, BigInteger>();

    public static String message;

    public static void main(String[] args) {
        String[] operation = Parser.ParseArgs(args);
        if (operation[0].equals("encrypt")) {
            Encrypt(operation[1]);
        } else {
            Decrypt(operation[1]);
        }
    }

    public static void Encrypt(String calculationType) {
        if (calculationType.equals("Modulus-Factors")) {
            CalculateModulus();
        }
        BigInteger messageBigInt = Converter.StringToBigInt(message);
        BigInteger ciphertext = messageBigInt.modPow(RsaValues.get("exponent"), RsaValues.get("modulus"));
        System.out.printf("Ciphertext: %d", ciphertext);
    }

    public static void Decrypt(String calculationType) {
        if (calculationType.equals("Modulus-Factors")) {
            CalculatePrivateKey();
            CalculateModulus();
        }
        BigInteger plaintextBigInt = RsaValues.get("ciphertext").modPow(RsaValues.get("d"), RsaValues.get("modulus"));
        String plaintext = Converter.BigIntToString(plaintextBigInt);
        System.out.printf("Plaintext: %s", plaintext);
    }

    public static void CalculatePrivateKey() {
        BigInteger[] subtractedFactors = { RsaValues.get("p").subtract(BigInteger.valueOf(1)),
                RsaValues.get("q").subtract(BigInteger.valueOf(1)) };
        BigInteger phi = subtractedFactors[0].multiply(subtractedFactors[1]);
        BigInteger d = RsaValues.get("exponent").modInverse(phi);
        RsaValues.put("d", d);
    }

    public static void CalculateModulus() {
        BigInteger modulus = RsaValues.get("p").multiply(RsaValues.get("q"));
        RsaValues.remove("q");
        RsaValues.remove("p");
        RsaValues.put("modulus", modulus);
    }
}
