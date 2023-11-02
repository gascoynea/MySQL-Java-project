package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ApptInfoDropDownController {
    public Button saveButton;
    public Button cancelButton;

    public void onSaveButtonClick(ActionEvent actionEvent) {
    }

    public void onCancelButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Current Appointments");
        stage.setScene(scene);
        stage.show();
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}
