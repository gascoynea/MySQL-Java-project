package com.example.demo;

import BDAccess.DBAAppointments;
import BDAccess.DBAContacts;
import BDAccess.DBACustomers;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ApptInfoDropDownController implements Initializable {
    @FXML
    public TextField createdByTF;
    @FXML
    public ComboBox contactListCB;
    @FXML
    private TextField apptTF;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField contactIDTF;

    @FXML
    private TextField customerIDTF;

    @FXML
    private ComboBox customerListDD;

    @FXML
    private TextField dateCreatedTF;

    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField endDTTF;

    @FXML
    private TextField lastUpdatedByTF;

    @FXML
    private TextField lastUpdatedTF;

    @FXML
    private TextField locationTF;

    @FXML
    private Button saveButton;

    @FXML
    private TextField startDTTF;

    @FXML
    private TextField titleTF;

    @FXML
    private TextField typeTF;

    @FXML
    private TextField userIDTF;
    ObservableList<Customers> customerList = null;
    ObservableList<Appointments> appointmentsList = null;
    ObservableList<Contacts> contactsList = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerList = DBACustomers.getAllCustomers();
        ObservableList<String> customerNames = FXCollections.observableArrayList();
        for(Customers customer : customerList){
            customerNames.add(customer.getCustomerName());
        }
        customerListDD.setItems(customerNames);

        appointmentsList = DBAAppointments.getAllAppointments();
        ObservableList<Integer> appointmentIDsList = FXCollections.observableArrayList();
        for (Appointments appoinment : appointmentsList){
            appointmentIDsList.add(appoinment.getAppointmentID());
        }
        apptTF.setText(String.valueOf(newAppointmentID(appointmentIDsList)));

        contactsList = DBAContacts.getAllContacts();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for(Contacts contact : contactsList){
            contactNames.add(contact.getContact_Name());
            }
        contactListCB.setItems(contactNames);
        userIDTF.setText(String.valueOf(MainController.reference.userID));
        createdByTF.setText(MainController.reference.userName);
        lastUpdatedByTF.setText(MainController.reference.userName);

    }
    public void onSaveButtonClick(ActionEvent actionEvent) {
    }

    public void onCancelButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 400);
        Stage stage = new Stage();
        stage.setTitle("Current Appointments");
        stage.setScene(scene);
        stage.show();
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
    public void onCustomerNameSelected(ActionEvent actionEvent){
        if(customerListDD.isFocused()) {
            String customerName = customerListDD.getValue().toString();
            String custNameTemp;
            for (Customers customer : customerList) {
                custNameTemp = customer.getCustomerName();
                if (custNameTemp == customerName) {
                    customerIDTF.setText(String.valueOf(customer.getCustomerID()));
                    }
            }
        }
    }

    public void onContactNameSelected(ActionEvent actionEvent){
        if(contactListCB.isFocused()){
            String contactName = contactListCB.getValue().toString();
            String contactNameTemp;
            for(Contacts contact : contactsList){
                contactNameTemp = contact.getContact_Name();
                if (contactNameTemp == contactName){
                    contactIDTF.setText(String.valueOf(contact.getConID()));
                }
            }
        }
    }

    public int newAppointmentID(List appointmentIDs){
        int newAppointmentID;
        int possibleID = 1;
        while (appointmentIDs.contains(possibleID)){
            possibleID += 1;
        }
        newAppointmentID = possibleID;
        return newAppointmentID;
    }

}
