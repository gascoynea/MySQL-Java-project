package com.example.demo;

import BDAccess.DBACountries;
import BDAccess.DBACustomers;
import BDAccess.DBAFirstLevelDivisions;
import Model.Customers;
import Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.net.URL;
import java.text.SimpleDateFormat;
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
    ObservableList<String> divisionNamesList = DBAFirstLevelDivisions.getDivisionNames();
    ObservableList<String> countryNamesList = DBACountries.getCountryNames();
    ObservableList<String> customerNamesList = DBACustomers.getCustomerNames();
    ObservableList<Customers> customersList = DBACustomers.getAllCustomers();
    ObservableList<FirstLevelDivisions> divisionsList = DBAFirstLevelDivisions.getAllFirstLevelDivisions();

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
        cratedByTF.setText(MainController.reference.userName);
        lastUpdatedByTF.setText(MainController.reference.userName);
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
    void onCancelButtonClick(ActionEvent event) {

    }

    @FXML
    void onCountrySelected(ActionEvent event) {
        String a = countryCB.getValue().toString();
        System.out.println(a + 1);
        if (a.equals("U.S")) {
            System.out.println(a + 2);
            int cID = 1;
            ObservableList<FirstLevelDivisions> divList = DBAFirstLevelDivisions.getAllFirstLevelDivisions();
            ObservableList<String> divNames = FXCollections.observableArrayList();
            System.out.println("you are good so far in the US");
            for (FirstLevelDivisions division : divList) {
                if (division.getCountryID() == cID) {
                    divNames.add(division.getDivision());
                }
            }
            countryIDTF.setText(String.valueOf(cID));
            divisionCB.setItems(divNames);
        }
        else if(a.equals("UK")){
            System.out.println(a + 2);
            int cID = 2;
            ObservableList<FirstLevelDivisions> divList = DBAFirstLevelDivisions.getAllFirstLevelDivisions();
            ObservableList<String> divNames = FXCollections.observableArrayList();
            System.out.println("you are good so far in the UK");
            for(FirstLevelDivisions division : divList){
                if(division.getCountryID() == cID){
                    divNames.add(division.getDivision());
                }
            }
            countryIDTF.setText(String.valueOf(cID));
            divisionCB.setItems(divNames);
        }
        else {
            System.out.println(a + 2);
            int cID = 3;
            ObservableList<FirstLevelDivisions> divList = DBAFirstLevelDivisions.getAllFirstLevelDivisions();
            ObservableList<String> divNames = FXCollections.observableArrayList();
            System.out.println("you are good so far in the syrup land");
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
    }
    public String getTime(){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        return timeStamp;
    }
}
