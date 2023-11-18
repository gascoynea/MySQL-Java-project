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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {
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
        //Populating Customer Name ComboBox
        customerList = DBACustomers.getAllCustomers();
        ObservableList<String> customerNames = FXCollections.observableArrayList();
        for(Customers customer : customerList){
            customerNames.add(customer.getCustomerName());
        }
        customerListDD.setItems(customerNames);

        //Populating appointment ID Text Field with generated ID from method newAppointmentID()
        appointmentsList = DBAAppointments.getAllAppointments();
        ObservableList<Integer> appointmentIDsList = FXCollections.observableArrayList();
        for (Appointments appoinment : appointmentsList){
            appointmentIDsList.add(appoinment.getAppointmentID());
        }
        apptTF.setText(String.valueOf(newAppointmentID(appointmentIDsList)));

        //Populating COntact Names in contact name combo box
        contactsList = DBAContacts.getAllContacts();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for(Contacts contact : contactsList){
            contactNames.add(contact.getContact_Name());
            }
        contactListCB.setItems(contactNames);

        //populating username, userID, created by, and last updated by. As well as, setting time for last update and create date
        userIDTF.setText(String.valueOf(LoginFormController.reference.userID));
        createdByTF.setText(LoginFormController.reference.userName);
        lastUpdatedByTF.setText(LoginFormController.reference.userName);
        dateCreatedTF.setText(getTime());
        lastUpdatedTF.setText(getTime());
    }
    public void onSaveButtonClick(ActionEvent actionEvent) {
        try {
            if(customerListDD.getValue() == null || contactListCB.getValue() == null || apptTF.getText().equals("") || titleTF.getText().equals("")
                    || descriptionTF.getText().equals("") || locationTF.getText().equals("") || typeTF.getText().equals("") || startDTTF.getText().equals("")
                    || endDTTF.getText().equals("") || dateCreatedTF.getText().equals("") || createdByTF.getText().equals("") || lastUpdatedTF.getText().equals("")
                    || lastUpdatedByTF.getText().equals("") || customerIDTF.getText().equals("") || userIDTF.getText().equals("") || contactIDTF.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("One or more Text Fields or Combo Box's are empty.");
                alert.setContentText("Please fill in all fields or box's before saving. Thank you.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Are you sure you want to save this appointment?");
                alert.setContentText("Please select 'OK' to save. Thank you.");
                alert.showAndWait();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1250, 400);
                Stage stage = new Stage();
                stage.setTitle("Main Appointments");
                stage.setScene(scene);
                stage.show();
                ((Stage) cancelButton.getScene().getWindow()).close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onCancelButtonClick(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you do not want to save this appointment?");
        alert.setContentText("Please select 'OK' to cancel. Thank you.");
        alert.showAndWait();
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
    public String getTime(){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        return timeStamp;
    }

    //Used to Populate certain fields like customer name, customer ID when adding to a customer from the customers record
    public void populateTextFields(Customers customerInfo) {
        Customers customerInformation = customerInfo;
        for(Customers customer : customerList){
            if(customer.getCustomerName().equals(customerInformation.getCustomerName())){
                System.out.println(customer.getCustomerName() + " " + customer.getCustomerID());
                customerListDD.getSelectionModel().select(customer.getCustomerName());
                customerIDTF.setText(String.valueOf(customer.getCustomerID()));
            }
        }
    }
}
