package com.example.demo;
import Model.Appointments;
import BDAccess.DBAAppointments;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainAppointmetController implements Initializable {
    public Label apptLabel;
    public Button apptsCustRecordsButton;
    public Button addApptButton;
    public Button removeApptButton;
    public Button editApptButton;
    public TableView tableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onCustomerRecordsClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CustomerList.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Customers List");
        stage.setScene(scene);
        stage.show();
        ((Stage) apptsCustRecordsButton.getScene().getWindow()).close();
    }

    public void onAddClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Appoinment Information with DropMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Add Appointment to known user");
        stage.setScene(scene);
        stage.show();
        ((Stage) addApptButton.getScene().getWindow()).close();
    }

    public void onEditClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CustomerRecord.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Customer Information Sheet");
        stage.setScene(scene);
        stage.show();
        ((Stage) editApptButton.getScene().getWindow()).close();
    }
}
