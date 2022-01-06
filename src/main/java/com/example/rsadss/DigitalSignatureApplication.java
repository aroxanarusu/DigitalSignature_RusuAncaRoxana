package com.example.rsadss;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;

public class DigitalSignatureApplication extends Application {
    byte[] signature = new byte[1111111];

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(DigitalSignatureApplication.class.getResource("hello-view.fxml"));
        Label label = new Label();
        Label label2 = new Label();

        DigitalSignature ds = new DigitalSignature();
        Utility utility = Utility.getInstance();

        MenuItem menuItem1 = new MenuItem("DSA");
        MenuItem menuItem2 = new MenuItem("RSA");

        MenuButton menuButton = new MenuButton("Generate Keys", null, menuItem1, menuItem2);

        final KeyPair[] keyPair = new KeyPair[2];

        Label selectedAlgorithm = new Label();

        MenuItem encryptButton1 = new MenuItem("SHA224withRSA");
        MenuItem encryptButton2 = new MenuItem("SHA256withRSA");
        MenuItem encryptButton3 = new MenuItem("SHA224withDSA");
        MenuItem encryptButton4 = new MenuItem("SHA256withDSA");
        MenuButton menuButtonEncrypt = new MenuButton("Select Algorithm", null, encryptButton1, encryptButton2, encryptButton3,encryptButton4);

        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {


                byte[] fileContent = new byte[0];
                try {
                    fileContent = Files.readAllBytes(Paths.get(label.getText()));
                    signature = ds.createDigitalSignature(fileContent, keyPair[0].getPrivate(),((MenuItem)e.getSource()).getText());
                    System.out.println(((MenuItem)e.getSource()).getText());
                    System.out.println("Signature Value:\n " + DatatypeConverter.printHexBinary(signature));
                    label2.setText(((MenuItem)e.getSource()).getText());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };


        encryptButton1.setOnAction(event2);
        encryptButton2.setOnAction(event2);
        encryptButton3.setOnAction(event2);
        encryptButton4.setOnAction(event2);




        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                try {
                    keyPair[0] = ds.generateKeyPair(((MenuItem)e.getSource()).getText());
                    utility.genRSAKeyPairAndSaveToFile(keyPair[0], ((MenuItem)e.getSource()).getText());
                    selectedAlgorithm.setText(((MenuItem)e.getSource()).getText());

                    if (selectedAlgorithm.getText().equals("RSA")){
                        encryptButton3.setDisable(true);
                        encryptButton4.setDisable(true);
                        encryptButton1.setDisable(false);
                        encryptButton2.setDisable(false);

                    }else if (selectedAlgorithm.getText().equals("DSA")){
                        encryptButton1.setDisable(true);
                        encryptButton2.setDisable(true);
                        encryptButton3.setDisable(false);
                        encryptButton4.setDisable(false);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        menuItem1.setOnAction(event1);
        menuItem2.setOnAction(event1);

        String path = null;

        FileChooser fileChooser = new FileChooser();
        Button button = new Button("Select File");
        button.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile!=null){
                label.setText(selectedFile.getAbsolutePath());
            }
        });





        Button verify = new Button("Verify Signature");
        verify.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                try {
                    System.out.println(selectedAlgorithm.getText());
                    byte[] fileContent = Files.readAllBytes(Paths.get(label.getText()));
                    System.out.println("Signature Value:\n " + DatatypeConverter.printHexBinary(signature));
                    System.out.println("Verification: " + ds.verifyDigitalSignature(fileContent, signature, keyPair[0].getPublic(), label2.getText()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        GridPane layout= new GridPane();
        layout.add(button,0,1);
        layout.add(verify,0,4);
        layout.add(menuButton,0,2);
        layout.add(menuButtonEncrypt,0,3);


        Scene scene = new Scene(layout, 320, 240);
        stage.setTitle("Digital Signature");
        stage.setScene(scene);
        stage.show();
    }

    private void setPath(String path, String absolutePath) {
        path = absolutePath;
    }

    public static void main(String[] args) {
        launch();
    }

}