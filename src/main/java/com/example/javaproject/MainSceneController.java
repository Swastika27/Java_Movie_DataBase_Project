package com.example.javaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.LoginDTO;

import java.io.IOException;

public class MainSceneController {
    @FXML
    private TextField userText;
    @FXML
    private PasswordField passwordText;
//    private Main main;

    //public void setMain(Main main) {
//        this.main = main;
//    }

    public void loginAction(ActionEvent actionEvent) throws IOException {

        try {
            System.out.println("okay " + Main.socketWrapper);
        } catch (Exception e) {
            System.out.println("on login action " + e);
        }
        //main.socketWrapper.write(userText.getText());
        LoginDTO dto = new LoginDTO();
        dto.setUserName(userText.getText());
        dto.setPassword(passwordText.getText());
        Main.socketWrapper.write(dto);
        System.out.println("login dto written to server");
    }

    public void resetAction(ActionEvent actionEvent) {
        userText.setText(null);
        passwordText.setText(null);
    }

}