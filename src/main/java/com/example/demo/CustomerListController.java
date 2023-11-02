package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerListController {
    public TableView CustomerRec_Table;
    public TableColumn CustomerID_Col;
    public TableColumn LastName_Col;
    public TableColumn FirstName_Col;
    public TableColumn Address_Col;
    public TableColumn PostalCode_Col;
    public TableColumn PhoneNum_Col;
    public Button customerInfoButton;
    public Button apptsButton;

    public void onCustInfoButtonclick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CustomerRecord.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Customer Information Sheet");
        stage.setScene(scene);
        stage.show();
        ((Stage) customerInfoButton.getScene().getWindow()).close();
    }

    public void onApptsButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Current Appointments");
        stage.setScene(scene);
        stage.show();
        ((Stage) apptsButton.getScene().getWindow()).close();
    }
}
