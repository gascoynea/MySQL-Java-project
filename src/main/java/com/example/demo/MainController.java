package com.example.demo;

import BDAccess.DBAUsers;
import Model.Users;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import org.xml.sax.ErrorHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Button loginButton;
    public TextField userNameLogin;
    public TextField passwordLogin;
    public Label locationLabel;
    public Label loginFormText;

    @FXML


    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML

    public void onLoginClick(ActionEvent actionEvent) throws IOException {
        ObservableList<Users> usersList = DBAUsers.getAllUsers();
        String loginName = userNameLogin.getText();
        String password = passwordLogin.getText();
        List namesList = new ArrayList();
        List passwordsList = new ArrayList<>();
            for (Users user : usersList){
                namesList.add(user.getUserName());
                passwordsList.add(user.getUserPassword());
            }
            if(namesList.contains(loginName)){
                if(passwordsList.contains(password)){
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    stage.setTitle("Hello!");
                    stage.setScene(scene);
                    stage.show();
                    ((Stage) loginButton.getScene().getWindow()).close();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Wrong Password!");
                    alert.setContentText("Please reinput your password. Thank you.");
                    alert.showAndWait();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong Username!");
                alert.setContentText("Please reinput your username. Thank you.");
                alert.showAndWait();
            }
    }
}
/*
-Determine users location and display it on login form as a label.
-Login form in english or french dependant on the users pc language settings.
-have the error control messages reflect the users language.
 */