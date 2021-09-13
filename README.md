# RSAnswer
## A simple RSA encryptor/decryptor


### Installation
Clone the repository

```bash
git clone <giturl>
```

### Setup

Compile the `RSAnswer` package

Windows:
```bat
javac -d bin .\src\io\github\edcod3\RSAnswer\*.java
```

Linux:
```bash
javac -d bin ./src/io/github/edcod3/RSAnswer/*.java
```

### Usage

Usage (Encryption):
```bash 
java -classpath bin io.github.edcod3.RSAnswer.RSAnswer encrypt -m <message> -e <exponent> -n <modulus>
```

Usage (Decryption): 
```bash
java -classpath bin io.github.edcod3.RSAnswer.RSAnswer decrypt -c <ciphertext> -d <private key> -n <modulus>
```

### To-Do

- Encryption/Decryption with p & q instead of n & d
- Common RSA Attacks
    - cube root attack
    - find p & q given n (factordb)
    - common modulus attack