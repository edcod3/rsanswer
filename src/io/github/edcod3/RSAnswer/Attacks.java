package io.github.edcod3.RSAnswer;

import java.math.BigInteger;

public class Attacks {
    public static void main() {
        System.out.println("[*] Trying to find factors of n");
        Boolean factorsKnown = knownFactors();
        if (!factorsKnown && RSAnswer.RsaValues.get("exponent").equals(BigInteger.valueOf(3))) {
            System.out.println("[*] Trying cube root attack");
            cubeRootAttack();
        } else {
            System.out.println("[!] Ciphertext couldn't be decrypted :(");
            System.exit(-1);
        }
    }

    // Find factors of n from factordb
    public static Boolean knownFactors() {
        String uri = "http://factordb.com/index.php?query=" + String.valueOf(RSAnswer.RsaValues.get("modulus"));
        String response = Utils.makeRequest(uri);
        String isFullyFactored = Utils.regexFilter("<td>(.*?)</td>", response, 1);
        if (isFullyFactored.equals("FF")) {
            extractFactors(response, 1, "p");
            extractFactors(response, 2, "q");
            RSAnswer.Decrypt("Modulus-Factors");
            return true;
        } else {
            System.out.println("[!] Factors not found...");
            return false;
        }
    }

    public static void extractFactors(String response, int groupPosition, String factorName) {
        String nonCapturingGroup = "<a href=\"index\\.php\\?id=.*\">.*";
        String capturingGroup = "<a href=\"index\\.php\\?id=(.*?)\">.*";
        String extractedFactor = Utils.regexFilter(nonCapturingGroup + capturingGroup + capturingGroup, response,
                groupPosition);
        System.out.printf("[*] extracted %s: %s\n", factorName, extractedFactor);
        RSAnswer.RsaValues.put(factorName, new BigInteger(extractedFactor));
    }

    // checks if c ** 3 > n
    public static Boolean checkCubeRoot() {
        int isGreater = RSAnswer.RsaValues.get("ciphertext").mod(BigInteger.valueOf(3))
                .compareTo(RSAnswer.RsaValues.get("ciphertext"));
        if (isGreater > 0) {
            return true;
        } else {
            return false;
        }
    }

    // The plaintext message is the cube root of the ciphertext
    // if c ** 3 > n
    public static void cubeRootAttack() {
        BigInteger plaintextBigInt = cubeRoot(RSAnswer.RsaValues.get("ciphertext"));
        String plaintext = Converter.BigIntToString(plaintextBigInt);
        System.out.printf("[*] Plaintext: %s", plaintext);
        System.exit(0);
    }

    // Cube-Root converted from python
    // Source: https://github.com/JohnHammond/ctf-katana#cryptography
    public static BigInteger cubeRoot(BigInteger x) {
        BigInteger y = BigInteger.valueOf(0);
        BigInteger y3, d, y3_x, y3_x_y, d_div_2;
        BigInteger y1 = BigInteger.valueOf(2);
        while (!y.equals(y1)) {
            y = y1;
            y3 = y.pow(3);
            d = y3.multiply(BigInteger.valueOf(2)).add(x);
            y3_x = y3.add(x.multiply(BigInteger.valueOf(2)));
            y3_x_y = y.multiply(y3_x);
            d_div_2 = d.divide(BigInteger.valueOf(2));
            y1 = y3_x_y.add(d_div_2);
            y1 = y1.divide(d);
        }
        return y;
    }
}
