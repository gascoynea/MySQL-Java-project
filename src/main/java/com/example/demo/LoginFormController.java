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
import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Login form Controller used to manipulate login form.
 */
public class LoginFormController implements Initializable {
    public Button loginButton;
    public TextField userNameLogin;
    public TextField passwordLogin;
    public Label locationLabel;
    public Label loginFormText;


    class reference{
        public static String userName;
        public static int userID;
    }

    /**
     * Populates specific fields on start up of the FXML.
     * @param url
     * @param Bundle
     */
    @FXML
    public void initialize(URL url, ResourceBundle Bundle) {
        //Getting local zone location.
        try {
            Locale locale = Locale.getDefault();
            ZoneId region = ZoneId.systemDefault().normalized();
            locationLabel.setText(String.valueOf(region));
            ResourceBundle language = ResourceBundle.getBundle("Bundle", locale);
//            System.out.println(Locale.getDefault());
            if (locale.getLanguage() != "en") {
                loginButton.setText(language.getString("login"));
                loginFormText.setText(language.getString("America"));
                locationLabel.setText(language.getString("America"));
                passwordLogin.setText(language.getString("Password"));
                userNameLogin.setText(language.getString("Username"));
                //System.out.println(region);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method called when login button click.
     * Checks if username and password is correct.
     * Opens up Main Appointments FXML
     * Calls Login Activity method to record login attempts and appends it to Login_Activity.txt
     * @param actionEvent
     * @throws IOException
     */
    @FXML

    public void onLoginClick(ActionEvent actionEvent) throws IOException {

        ObservableList<Users> usersList = DBAUsers.getAllUsers();
        String loginName = userNameLogin.getText();
        String password = passwordLogin.getText();
        Boolean success = false;
        List namesList = new ArrayList();
        List passwordsList = new ArrayList<>();
        Locale locale = Locale.getDefault();
        ResourceBundle language = ResourceBundle.getBundle("Bundle", locale);
            for (Users user : usersList){
                namesList.add(user.getUserName());
                passwordsList.add(user.getUserPassword());
            }
            if(namesList.contains(loginName)){
                if(passwordsList.contains(password)){

                    reference.userName = loginName;
                    for(Users user: usersList) {
                        if(loginName.equals(user.getUserName()))
                            //System.out.println(loginName + " " + user.getUserID());
                        reference.userID = user.getUserID();
                        success = true;
                    }
                    loginActivity(loginName, password, success);
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1400, 400);
                    Stage stage = new Stage();
                    stage.setTitle("Hello!");
                    stage.setScene(scene);
                    stage.show();
                    ((Stage) loginButton.getScene().getWindow()).close();
                }
                else {
                    if(locale.getLanguage() == "en") {
                        loginActivity(loginName, password, success);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Wrong Password!");
                        alert.setContentText("Please reinput your password. Thank you.");
                        alert.showAndWait();
                    }
                    else{
                        loginActivity(loginName, password, success);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(language.getString("Error"));
                        alert.setHeaderText(language.getString("WrongPass"));
                        alert.setContentText("ReinputPass");
                        alert.showAndWait();
                    }
                }
            }
            else{
                if(locale.getLanguage() == "en") {
                    loginActivity(loginName, password, success);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Wrong Username!");
                    alert.setContentText("Please reinput your username. Thank you.");
                    alert.showAndWait();
                }
                else {
                    loginActivity(loginName, password, success);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(language.getString("Error"));
                    alert.setHeaderText(language.getString("WrongUsername"));
                    alert.setContentText("Reinputusername");
                    alert.showAndWait();
                }
            }
    }

    /**
     * login activity reocrding method for the login_activity.txt
     * @param inUserName
     * @param inUserPassword
     * @param inFailSucceed
     * @throws IOException
     */
    public void loginActivity(String inUserName, String inUserPassword, Boolean inFailSucceed) throws IOException {
        String success = "";
        File log = new File("login_activity.txt");
        if(inFailSucceed.equals(false)) {
            success = "FAILED LOGIN";
        }
        else {
            success = "SUCCESSFUL LOGIN";
        }
        if(!log.exists()){
            log.createNewFile();
        }
        FileWriter fw = new FileWriter(log, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Username attempted: " + inUserName + ", " + "Password attempted: " + inUserPassword + ", " + "Attempt was a: " +  success + ", Login attempt Date and timestamp: "
                + LocalDate.now() + " --- " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
        bw.close();
    }
}
