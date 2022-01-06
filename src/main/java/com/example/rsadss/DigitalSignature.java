// Java implementation for Generating
// and verifying the digital signature

package com.example.rsadss;

// Imports

import javax.xml.bind.DatatypeConverter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Scanner;

public class DigitalSignature {

    // Signing Algorithm
    private static final String SIGNING_ALGORITHM = "SHA256withRSA";
    private static final String RSA = "RSA";
    private static Scanner sc;

    // Function to implement Digital signature
    // using SHA256 and RSA algorithm
    // by passing private key.
    public static byte[] createDigitalSignature(byte[] input, PrivateKey Key, String alg) throws Exception {
        Signature signature = Signature.getInstance(alg);
        signature.initSign(Key);
        signature.update(input);
        return signature.sign();
    }

    // Generating the asymmetric key pair
    // using SecureRandom class
    // functions and RSA algorithm.
    public static KeyPair generateKeyPair(String algorithm) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(2048, secureRandom);
        return keyPairGenerator.generateKeyPair();
    }

    // Function for Verification of the
    // digital signature by using the public key
    public static boolean verifyDigitalSignature(byte[] input, byte[] signatureToVerify, PublicKey key, String alg) throws Exception {
        Signature signature = Signature.getInstance(alg);
        signature.initVerify(key);
        signature.update(input);
        return signature.verify(signatureToVerify);
    }
}
