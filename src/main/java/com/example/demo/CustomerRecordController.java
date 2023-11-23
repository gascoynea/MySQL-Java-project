package com.example.demo;

import BDAccess.DBAAppointments;
import BDAccess.DBACountries;
import BDAccess.DBACustomers;
import BDAccess.DBAFirstLevelDivisions;
import Model.Appointments;
import Model.Countries;
import Model.Customers;
import Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerRecordController implements Initializable {
    @FXML
    private Button addAppointmentButton;

    @FXML
    private TextField addressTF;

    @FXML
    private TableColumn<Appointments, Integer> apptIDCol;

    @FXML
    private Button cancelButton;

    @FXML
    private TableColumn<Appointments, Integer> contactIDCol;

    @FXML
    private ComboBox<String> countryComBox;

    @FXML
    private TableColumn<Appointments, String> createdByCol;

    @FXML
    private TableColumn<Appointments, Integer> customerIDCol;

    @FXML
    private TextField customerIDTF;

    @FXML
    private TableColumn<Appointments, Timestamp> dateCreatedCol;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Appointments, String> descriptionCol;

    @FXML
    private Button editButton;

    @FXML
    private TableColumn<Appointments, Timestamp> endCol;

    @FXML
    private TableColumn<Appointments, String> lastUpdatedByCol;

    @FXML
    private TableColumn<Appointments, Timestamp> lastUpdatedCol;

    @FXML
    private TableColumn<Appointments, String> locationCol;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField phoneNumberTF;

    @FXML
    private TextField postalcodeTF;

    @FXML
    private Button saveButton;

    @FXML
    private TableColumn<Appointments, Timestamp> startCol;

    @FXML
    private ComboBox<String> stateProvinceComBox;

    @FXML
    private TableView<Appointments> tableViewAppointments;

    @FXML
    private TableColumn<Appointments, String> titleCol;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    @FXML
    private TableColumn<Appointments, Integer> userIDCol;
    ObservableList<String> divisionNames = DBAFirstLevelDivisions.getDivisionNames();
    ObservableList<String> countryNames = DBACountries.getCountryNames();
    ObservableList<Customers> customersList = DBACustomers.getAllCustomers();
    ObservableList<Appointments> appointmentsList = DBAAppointments.getAllAppointments();
    public Appointments customerAppointmentInfo;
    private Customers customerInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Have to create two methods. one for creating a new custID for adding a new record and one for bring in the customer if from a record being updated

        List<Integer> custIDs = new ArrayList<Integer>();
        for(Customers customer : customersList){
            Integer custID = customer.getCustomerID();
            custIDs.add(custID);
        }
        customerIDTF.setText(String.valueOf(newCustomerID(custIDs)));
        countryComBox.setItems(countryNames);
        stateProvinceComBox.setItems(divisionNames);

        //Appointment Table populations
        ObservableList<Appointments> apptList = DBAAppointments.getAllAppointments();
        apptIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<Appointments, Timestamp>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<Appointments, Timestamp>("end"));
        dateCreatedCol.setCellValueFactory(new PropertyValueFactory<Appointments, Timestamp>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("createdBy"));
        lastUpdatedCol.setCellValueFactory(new PropertyValueFactory<Appointments, Timestamp>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("lastUpdatedBy"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("customerId"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("userId"));
        contactIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("contactId"));
//        for(Appointments appointment : apptList){
//
//        }
//        tableViewAppointments.setItems(apptList);
    }
    public void onSaveClick(ActionEvent actionEvent) {
        try {
            if(customerIDTF.getText().equals("") || nameTF.getText().equals("") || addressTF.getText().equals("") || postalcodeTF.getText().equals("")
                    || phoneNumberTF.getText().equals("") || countryComBox.getValue() == null || stateProvinceComBox.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("One or more Text Fields or Combo Box's are empty.");
                alert.setContentText("Please fill in all fields or box's before saving. Thank you.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Are you sure you want to save this edit to this customer?");
                alert.setContentText("Please select 'OK' to save. Thank you.");
                alert.showAndWait();

                if(alert.getResult().getButtonData().isCancelButton()){
                    alert.close();
                }
                else {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Customers.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1250, 400);
                    Stage stage = new Stage();
                    stage.setTitle("Main Customers");
                    stage.setScene(scene);
                    stage.show();
                    ((Stage) cancelButton.getScene().getWindow()).close();

                    /*
                    !!!!!!!!!!!!!!!!!!!!!!!!!!!

                    Code for saving this customers new information in the main customer table view as well as updating the database

                    !!!!!!!!!!!!!!!!!!!!!!!!!!!
                     */

                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void onCancelButtonCllick(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to cancel editing this customer?");
        alert.setContentText("Please select 'OK' to cancel. Thank you.");
        alert.showAndWait();

        if(alert.getResult().getButtonData().isCancelButton()){
            alert.close();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Customers.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1250, 400);
            Stage stage = new Stage();
            stage.setTitle("Customer Records");
            stage.setScene(scene);
            stage.show();
            ((Stage) cancelButton.getScene().getWindow()).close();
        }
    }
    public void onDeleteButtonClick(ActionEvent actionEvent) {
        try {
            if(tableViewAppointments.getSelectionModel().selectedItemProperty().get() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You need to select an appointment to delete.");
                alert.setContentText("Please click on an appointment in the table. Thank you.");
                alert.showAndWait();
                }
           else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Are you sure you want to delete this appointment?");
                alert.setContentText("Please select 'OK' to delete. Thank you.");
                alert.showAndWait();
                if(alert.getResult().getButtonData().isCancelButton()){
                    alert.close();
                }
                else {
                    //Code for appointment deletion and have table view be updated to reflect deletion as well as database and other table views such as main appt table
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void onAddAppointmentButtonClick(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Add Appointment.fxml"));
            Parent root = loader.load();
            AddAppointmentController scene2controller = loader.getController();
            scene2controller.populateTextFields(customerInfo);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Add Appointment");
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void onEditButtonClick(ActionEvent actionEvent) throws IOException {
        try{
            if(tableViewAppointments.getSelectionModel().getSelectedItem() != null) {
                Appointments selectedAppointment = tableViewAppointments.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Edit Appointment.fxml"));
                Parent root = loader.load();
                EditAppointmentController scene2controller = loader.getController();
                scene2controller.populateTextFields(selectedAppointment);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Edit Appointment");
                stage.setScene(scene);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No appointment selected.");
                alert.setContentText("Please select an appointment. Thank you.");
                alert.showAndWait();
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int newCustomerID(List customerIDs){
        int newCustomerID;
        int possibleID = 1;
        while (customerIDs.contains(possibleID)){
            possibleID += 1;
        }
        newCustomerID = possibleID;
        return newCustomerID;
    }
    public void onCountrySelected(ActionEvent actionEvent) {
        String a = countryComBox.getValue().toString();
        if(a.equals("U.S")){
            int cID = 1;
            ObservableList<FirstLevelDivisions> divList = DBAFirstLevelDivisions.getAllFirstLevelDivisions();
            ObservableList<String> divNames = FXCollections.observableArrayList();
            for(FirstLevelDivisions division : divList){
                if(division.getCountryID() == cID){
                    divNames.add(division.getDivision());
                }
            }
            stateProvinceComBox.setItems(divNames);
        }
        else if(a.equals("UK")){
            int cID = 2;
            ObservableList<FirstLevelDivisions> divList = DBAFirstLevelDivisions.getAllFirstLevelDivisions();
            ObservableList<String> divNames = FXCollections.observableArrayList();
            for(FirstLevelDivisions division : divList){
                if(division.getCountryID() == cID){
                    divNames.add(division.getDivision());
                }
            }
            stateProvinceComBox.setItems(divNames);
        }
        else {
            int cID = 3;
            ObservableList<FirstLevelDivisions> divList = DBAFirstLevelDivisions.getAllFirstLevelDivisions();
            ObservableList<String> divNames = FXCollections.observableArrayList();
            for(FirstLevelDivisions division : divList){
                if(division.getCountryID() == cID){
                    divNames.add(division.getDivision());
                }
            }
            stateProvinceComBox.setItems(divNames);
        }
    }
    public void onMouseCountrySelect(MouseEvent mouseEvent) {
//
    }
    public void populateScene(Customers customerInformation){
        //Appointments customerAppointments,
        customerInfo = customerInformation;
        ObservableList<FirstLevelDivisions> firstLevelDivisionsList = DBAFirstLevelDivisions.getAllFirstLevelDivisions();
        ObservableList<Countries> countriesList = DBACountries.getAllCountries();
        ObservableList<Appointments> apptList = DBAAppointments.getAllAppointments();
        ObservableList<Appointments> customerAppointmentsList = FXCollections.observableArrayList();
        customerIDTF.setText(String.valueOf(customerInfo.getCustomerID()));
        nameTF.setText(String.valueOf(customerInfo.getCustomerName()));
        addressTF.setText(String.valueOf(customerInfo.getAddress()));
        postalcodeTF.setText(String.valueOf(customerInfo.getPostCode()));
        phoneNumberTF.setText(String.valueOf(customerInfo.getPhone()));
        stateProvinceComBox.getSelectionModel().select(String.valueOf(customerInfo.getDivision()));
        for (FirstLevelDivisions division : firstLevelDivisionsList){
            if(division.getDivID() == customerInfo.getDivisionID()){
               int countryID = division.getCountryID();
               for(Countries country : countriesList){
                   if(country.getId() == countryID){
                       countryComBox.getSelectionModel().select(country.getName());
                   }
               }
            }
        }
        for(Appointments appointment : apptList){
            if(appointment.getCustomerId() == customerInfo.getCustomerID()){
                customerAppointmentsList.add(appointment);
            }
        }
        tableViewAppointments.setItems(customerAppointmentsList);
    }
}
