package com.example.demo;

import BDAccess.DBAUsers;
import Model.Users;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Button loginButton;
    public TextField userNameLogin;
    public TextField passwordLogin;
    @FXML


    public void initialize(URL url, ResourceBundle resourceBundle){

    }
    @FXML

    public void onLoginClick(ActionEvent actionEvent) throws IOException {
        ObservableList<Users> usersList = DBAUsers.getAllUsers();
//        for(Users user : usersList){
//            System.out.println("CID: " + user.getUserID() + " CName and pass: " + user.getUserName() + " " + user.getUserPassword());
//        }
        String loginName = userNameLogin.getText();
        String password = passwordLogin.getText();
        for(Users user : usersList){
            if(loginName.equals(user.getUserName()) && password.equals(user.getUserPassword())){
                System.out.println("login successful");
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                Stage stage = new Stage();
                stage.setTitle("Hello!");
                stage.setScene(scene);
                stage.show();
            }
        }
//        System.out.println(loginName + " " + password);
    }
}