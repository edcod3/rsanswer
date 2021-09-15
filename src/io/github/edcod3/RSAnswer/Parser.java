package io.github.edcod3.RSAnswer;

import java.math.BigInteger;

public class Parser {

    public static void Usage() {
        String usageEncrypt = "Usage (Encryption): java -classpath bin io.github.edcod3.RSAnswer.RSAnswer encrypt -m <message> -e <exponent> [-n <modulus>|-p P -q Q]";
        String usageDecrypt = "Usage (Decryption): java -classpath bin io.github.edcod3.RSAnswer.RSAnswer decrypt -c <ciphertext> [-d <private key> -n <modulus>|-p P -q Q]";
        System.out.println(usageEncrypt);
        System.out.println(usageDecrypt);

    }

    public static String[] ParseArgs(String[] args) {
        String modulusFactorsGiven = "Key-Pair";
        if (args[0].equals("encrypt")) {
            for (int i = 1; i < args.length; i++) {
                switch (args[i]) {
                    case "-m":
                        RSAnswer.message = args[i + 1];
                        break;
                    case "-e":
                        RSAnswer.RsaValues.put("exponent", new BigInteger(args[i + 1]));
                        break;
                    case "-n":
                        RSAnswer.RsaValues.put("modulus", new BigInteger(args[i + 1]));
                        // RSAnswer.RsaValues[1] = new BigInteger(args[i + 1]);
                        break;
                    case "-p":
                        RSAnswer.RsaValues.put("p", new BigInteger(args[i + 1]));
                        modulusFactorsGiven = "Modulus-Factors";
                        break;
                    case "-q":
                        RSAnswer.RsaValues.put("q", new BigInteger(args[i + 1]));
                        modulusFactorsGiven = "Modulus-Factors";
                        break;

                }
            }
            if ((args.length < 7) || (RSAnswer.message == "")) {
                System.out.println("Missing Values!");
                Usage();
            }
        } else if (args[0].equals("decrypt")) {
            for (int i = 1; i < args.length; i++) {
                switch (args[i]) {
                    case "-c":
                        RSAnswer.RsaValues.put("ciphertext", new BigInteger(args[i + 1]));
                        break;
                    case "-d":
                        RSAnswer.RsaValues.put("d", new BigInteger(args[i + 1]));
                        break;
                    case "-n":
                        RSAnswer.RsaValues.put("modulus", new BigInteger(args[i + 1]));
                        break;
                    case "-p":
                        RSAnswer.RsaValues.put("p", new BigInteger(args[i + 1]));
                        modulusFactorsGiven = "Modulus-Factors";
                        break;
                    case "-q":
                        RSAnswer.RsaValues.put("q", new BigInteger(args[i + 1]));
                        modulusFactorsGiven = "Modulus-Factors";
                        break;
                    case "-e":
                        RSAnswer.RsaValues.put("exponent", new BigInteger(args[i + 1]));
                        break;
                }
            }
            if ((args.length < 7) || (RSAnswer.message == "")) {
                System.out.println("Missing Values!");
                Usage();
            }
        } else {
            Usage();
        }

        String[] returnValues = { args[0], modulusFactorsGiven };

        return returnValues;
    }
}
