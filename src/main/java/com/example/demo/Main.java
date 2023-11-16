package com.example.demo;

import BDAccess.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Database.DBConnection.openConnection();
//        System.out.println(DBACountries.getCountryName());
        DBACustomers.getCustomerNames();
//        DBAContacts.getContactName();
//        DBAAppointments.getAppointmentID();
//        DBAFirstLevelDivisions.getDivisionName();
//        DBAUsers.getUserNames();
        launch();
        Database.DBConnection.closeConnection();
    }
}