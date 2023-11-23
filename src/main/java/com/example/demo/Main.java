package com.example.demo;

import BDAccess.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.TimeZone;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login Form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Database.DBConnection.openConnection();

//        String[] zoneList = TimeZone.getAvailableIDs();
//        String est = "EST";
//        System.out.println(TimeZone.getDefault());
//        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
//        System.out.println(TimeZone.getDefault());

//        System.out.println(DBACountries.getCountryNames());
//        DBACustomers.getCustomerNames();
//        DBAContacts.getContactName();
//        DBAAppointments.getAppointmentID();
//        DBAFirstLevelDivisions.getDivisionNames();
//        DBAUsers.getUserNames();
        launch();
        Database.DBConnection.closeConnection();
    }
}