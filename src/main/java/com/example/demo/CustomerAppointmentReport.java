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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CustomerAppointmentReport implements Initializable {
    @FXML
    public Button mainAppointmetsButton;
    @FXML
    public TableView contactTableView;
    @FXML
    public TableColumn appointmentIDCol;
    @FXML
    public TableColumn titleCol;
    @FXML
    public TableColumn typeCol;
    @FXML
    public TableColumn descriptionCol;
    @FXML
    public TableColumn startCol;
    @FXML
    public TableColumn endCol;
    @FXML
    public TableColumn customerIDCol;
    @FXML
    public ComboBox contactCB;
    @FXML
    public ComboBox customerCB;
    @FXML
    public TextField appointmentAmntTF;
    @FXML
    private ComboBox<String> appointmentType;

    @FXML
    private ComboBox<String> monthCB;

    @FXML
    private TextField monthTF;

    @FXML
    private TextField typeTF;
    ObservableList<Appointments> appointmentsList = DBAAppointments.getAllAppointments();
    ObservableList<LocalDateTime> appointmentsLocalDateTime = FXCollections.observableArrayList();
    ObservableList<String> appointmentTypesList = FXCollections.observableArrayList();
    ObservableList<String> monthsOccupied = FXCollections.observableArrayList();
    ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
    ////////////////////////////////////////////////
    ObservableList<Contacts> contactsList = DBAContacts.getAllContacts();
    ObservableList<String> contactNames = FXCollections.observableArrayList();
    ObservableList<Integer> contactIDs = FXCollections.observableArrayList();
    ObservableList<Appointments> contactSpecificAppointments = FXCollections.observableArrayList();

    ////////////////////////////////////////////////////
    ObservableList<Customers> customersList = DBACustomers.getAllCustomers();
    ObservableList<String> customerNames = DBACustomers.getCustomerNames();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for(Appointments appointment : appointmentsList){
            appointmentsLocalDateTime.add(appointment.getStart().toLocalDateTime());
        }
        for(LocalDateTime ldt : appointmentsLocalDateTime){
            if(!monthsOccupied.contains(ldt.getMonth().toString())) {
                monthsOccupied.add(String.valueOf(ldt.getMonth()));
            }
        }
        monthCB.setItems(monthsOccupied);

        ///////////////////////////////////////////////

        for(Appointments appointment : appointmentsList){
            appointmentTypesList.add(appointment.getType());
        }
        for(String type : appointmentTypesList){
            if(!appointmentTypes.contains(type)){
                appointmentTypes.add(type);
            }
        }
        appointmentType.setItems(appointmentTypes);

        /*
        2nd Lambda expression. Without having a for loop to reduce lines.
        Also, with more data this will create a faster process speed.
         */
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        ObservableList<Integer> contactIDS = FXCollections.observableArrayList();
        contactsList.forEach(contacts -> {
            contactNames.add(contacts.getContact_Name());
            contactIDS.add(contacts.getConID());
        });

        contactCB.setItems(contactNames);

        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("description"));
        startCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<Appointments, Timestamp>("end"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("customerId"));
        contactTableView.setItems(appointmentsList);

        customerCB.setItems(customerNames);

    }
    @FXML
    void onAppointmentTypeSelected(ActionEvent event) {
        String typeSelected = appointmentType.getValue();
        int count = 0;
        for (String type : appointmentTypesList){
            if(typeSelected.equals(type)){
                count += 1;
            }
        }
        typeTF.setText(String.valueOf(count));
    }

    @FXML
    void onMonthSelected(ActionEvent event) {
        String monthSelected = monthCB.getValue();
        int count = 0;
        for (LocalDateTime appointmentMonth : appointmentsLocalDateTime){
            if(monthSelected.equals(appointmentMonth.getMonth().toString())){
                count += 1;
            }
        }
        monthTF.setText(String.valueOf(count));
    }

    @FXML
    public void onMainAppointmetsButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 400);
        Stage stage = new Stage();
        stage.setTitle("Main Appointments");
        stage.setScene(scene);
        stage.show();
        ((Stage) mainAppointmetsButton.getScene().getWindow()).close();
    }
    @FXML
    public void onContactSelected(ActionEvent actionEvent) {
        contactSpecificAppointments = FXCollections.observableArrayList();
        String contactName = contactCB.getValue().toString();
        int contactID = 0;
        for(Contacts contact: contactsList){
            if(contact.getContact_Name().equals(contactName)){
                contactID = contact.getConID();
            }
        }
        for(Appointments appointment : appointmentsList){
            if(appointment.getContactId() == contactID){
                contactSpecificAppointments.add(appointment);
            }
        }
        contactTableView.setItems(contactSpecificAppointments);
    }
    @FXML
    public void onCustomerSelect(ActionEvent actionEvent) {
        String customerName = customerCB.getValue().toString();
        int customerID = 0;
        int counter = 0;
        for(Customers customers : customersList){
            if(customers.getCustomerName().equals(customerName)){
                customerID = customers.getCustomerID();
            }
        }
        for(Appointments appointments : appointmentsList){
            if(appointments.getCustomerId() == customerID){
                counter += 1;
            }
        }
        appointmentAmntTF.setText(String.valueOf(counter));
    }
}
