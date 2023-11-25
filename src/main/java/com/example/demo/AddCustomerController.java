package com.example.demo;

import BDAccess.DBACountries;
import BDAccess.DBACustomers;
import BDAccess.DBAFirstLevelDivisions;
import Database.DBConnection;
import Model.Customers;
import Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    @FXML
    public TextField createdDateTF;
    @FXML
    public TextField lastUpdatedDateTF;
    @FXML
    private TextField addressTF;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<String> countryCB;

    @FXML
    private TextField countryIDTF;

    @FXML
    private TextField cratedByTF;

    @FXML
    private TextField customerIDTF;

    @FXML
    private TextField customerNameTF;

    @FXML
    private DatePicker dateCreatedDP;

    @FXML
    private ComboBox<String> divisionCB;

    @FXML
    private TextField divisionIDTF;

    @FXML
    private DatePicker lastUpdateDP;

    @FXML
    private TextField lastUpdatedByTF;

    @FXML
    private TextField phoneNumberTF;

    @FXML
    private TextField postalCodeTF;

    @FXML
    private Button saveButton;
    private
    ObservableList<String> divisionNamesList = DBAFirstLevelDivisions.getDivisionNames();
    ObservableList<String> countryNamesList = DBACountries.getCountryNames();
    ObservableList<String> customerNamesList = DBACustomers.getCustomerNames();
    ObservableList<Customers> customersList = DBACustomers.getAllCustomers();
    ObservableList<FirstLevelDivisions> divisionsList = DBAFirstLevelDivisions.getAllFirstLevelDivisions();
    private static PreparedStatement preparedStatement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Integer> custIDs = new ArrayList<Integer>();
        for(Customers customer : customersList){
            Integer custID = customer.getCustomerID();
            custIDs.add(custID);
        }
        customerIDTF.setText(String.valueOf(newCustomerID(custIDs)));
        countryCB.setItems(countryNamesList);
        divisionCB.setItems(divisionNamesList);
        cratedByTF.setText(LoginFormController.reference.userName);
        lastUpdatedByTF.setText(LoginFormController.reference.userName);
        createdDateTF.setText(getTime());
        lastUpdatedDateTF.setText(getTime());
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
    @FXML
    void onCancelButtonClick(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to cancel creating this new customer?");
        alert.setContentText("Please select 'OK' to cancel. Thank you.");
        alert.showAndWait();

        if(alert.getResult().getButtonData().isCancelButton()){
            alert.close();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Customers.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1250, 400);
            Stage stage = new Stage();
            stage.setTitle("Main Appointments");
            stage.setScene(scene);
            stage.show();
            ((Stage) cancelButton.getScene().getWindow()).close();
        }
    }
    @FXML
    void onCountrySelected(ActionEvent event) {
        String a = countryCB.getValue().toString();
        if (a.equals("U.S")) {
            int cID = 1;
            ObservableList<FirstLevelDivisions> divList = DBAFirstLevelDivisions.getAllFirstLevelDivisions();
            ObservableList<String> divNames = FXCollections.observableArrayList();
            for (FirstLevelDivisions division : divList) {
                if (division.getCountryID() == cID) {
                    divNames.add(division.getDivision());
                }
            }
            countryIDTF.setText(String.valueOf(cID));
            divisionCB.setItems(divNames);
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
            countryIDTF.setText(String.valueOf(cID));
            divisionCB.setItems(divNames);
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
            countryIDTF.setText(String.valueOf(cID));
            divisionCB.setItems(divNames);
        }

    }
    @FXML
    void onDivisionSelect(ActionEvent event) {
        String divName = divisionCB.getValue().toString();
        int divID = 0;
        for (FirstLevelDivisions division : divisionsList){
            if (division.getDivision().equals(divName)){
                divID = division.getDivID();
            }
        }
        divisionIDTF.setText(String.valueOf(divID));
    }
    @FXML
    void onSaveButtonClick(ActionEvent event) {
        try {
            if(customerIDTF.getText().equals("") || customerNameTF.getText().equals("") || addressTF.getText().equals("") || postalCodeTF.getText().equals("")
            || phoneNumberTF.getText().equals("") || countryCB.getValue() == null || countryIDTF.getText().equals("") || divisionCB.getValue() == null
            || divisionIDTF.getText().equals("") || createdDateTF.getText().equals("") || cratedByTF.getText().equals("") || lastUpdatedDateTF.getText().equals("")
            || lastUpdatedByTF.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("One or more Text Fields or Combo Box's are empty.");
                alert.setContentText("Please fill in all fields or box's before saving. Thank you.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Are you sure you want to save this customer?");
                alert.setContentText("Please select 'OK' to save. Thank you.");
                alert.showAndWait();

                if(alert.getResult().getButtonData().isCancelButton()) {
                    alert.close();
                }
                else {
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    int custID = Integer.parseInt(customerIDTF.getText());
                    String custName = customerNameTF.getText();
                    String custAddress = addressTF.getText();
                    String custPostalCode = postalCodeTF.getText();
                    String custPhoneNumber = phoneNumberTF.getText();
                    int custDivisionID = Integer.parseInt(divisionIDTF.getText());
                    String custCreatedDate = createdDateTF.getText();
                    LocalDateTime custcd = LocalDateTime.parse(custCreatedDate, format);
                    String custCreatedBy = cratedByTF.getText();
                    String custLastUpdateDateTime = lastUpdatedDateTF.getText();
                    LocalDateTime luDT =LocalDateTime.parse(custLastUpdateDateTime, format);
                    String custLastUpdatedBy = lastUpdatedByTF.getText();

                    //Adding customer to the DB
                    String customerStatement = "INSERT INTO customers (Customer_ID, Customer_Name, Address," +
                            " Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By," +
                            " Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
                    preparedStatement = DBConnection.getConnection().prepareStatement(customerStatement);
                    preparedStatement.setInt(1, custID);
                    preparedStatement.setString(2, custName);
                    preparedStatement.setString(3, custAddress);
                    preparedStatement.setString(4, custPostalCode);
                    preparedStatement.setString(5, custPhoneNumber);
                    preparedStatement.setTimestamp(6, Timestamp.valueOf(custcd));
                    preparedStatement.setString(7, custCreatedBy);
                    preparedStatement.setTimestamp(8, Timestamp.valueOf(luDT));
                    preparedStatement.setString(9, custLastUpdatedBy);
                    preparedStatement.setInt(10, custDivisionID);
                    preparedStatement.execute();
                    preparedStatement.close();

                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Customers.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1250, 400);
                    Stage stage = new Stage();
                    stage.setTitle("Main Customers");
                    stage.setScene(scene);
                    stage.show();
                    ((Stage) cancelButton.getScene().getWindow()).close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String getTime(){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        return timeStamp;
    }
}
