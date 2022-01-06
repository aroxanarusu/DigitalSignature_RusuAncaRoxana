package com.example.rsadss;

import javafx.scene.control.Alert;

import java.io.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Utility {
    private static Utility utility = null;

    public static Utility getInstance() {
        if (utility == null) {
            utility = new Utility();
        }
        return utility;
    }

    public static void genRSAKeyPairAndSaveToFile(KeyPair keyPair, String alg) {

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String privateKeyLocation = null;
        String publicKeyLocation = null;

        DataOutputStream dos = null;
        try {
            File out = new File(alg+"PublicKey");
            OutputStream os = new FileOutputStream(out);
            publicKeyLocation = out.getAbsolutePath();
            dos = new DataOutputStream(os);

            dos.write(publicKey.getEncoded());
            dos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setContentText("Keys were generated!" + publicKeyLocation);
//        alert.show();

        try {

            File out = new File(alg+"PrivateKey");
            OutputStream os = new FileOutputStream(out);
            privateKeyLocation = out.getAbsolutePath();
            dos = new DataOutputStream(os);

            dos.write(privateKey.getEncoded());
            dos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (dos != null)
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Keys were generated!\n" + publicKeyLocation + "\n" + privateKeyLocation) ;
        alert.show();

    }
}
