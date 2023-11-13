package com.example.demo;

import BDAccess.DBACustomers;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerListController implements Initializable {
    public TextField customerSearchTextField;
    public Button searchButton;
    @FXML
    private TableColumn<Customers, String> Address_Col;

    @FXML
    private TableColumn<Customers, Integer> customerID_Col;

    @FXML
    private TableView<Customers> CustomerRec_Table;

    @FXML
    private TableColumn<Customers, String> PhoneNum_Col;

    @FXML
    private TableColumn<Customers, String> PostalCode_Col;

    @FXML
    private Button apptsButton;

    @FXML
    private TableColumn<Customers, Timestamp> createDateCol;

    @FXML
    private TableColumn<Customers, String> createdByCol;

    @FXML
    private Button customerInfoButton;

    @FXML
    private TableColumn<Customers, Integer> divisionIDCol;

    @FXML
    private TableColumn<Customers, Timestamp> lastUpdateCol;

    @FXML
    private TableColumn<Customers, String> lastUpdatedByCol;

    @FXML
    private TableColumn<Customers, String> nameCol;
    ObservableList<Customers> customerList = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerList = DBACustomers.getAllCustomers();
        customerID_Col.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerName"));
        Address_Col.setCellValueFactory(new PropertyValueFactory<Customers, String>("address"));
        PostalCode_Col.setCellValueFactory(new PropertyValueFactory<Customers, String>("postCode"));
        PhoneNum_Col.setCellValueFactory(new PropertyValueFactory<Customers, String>("phone"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<Customers, Timestamp>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<Customers, Timestamp>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("lastUpdatedBy"));
        divisionIDCol.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("divisionID"));
        CustomerRec_Table.setItems(customerList);
    }
    @FXML
    public void onCustInfoButtonclick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CustomerRecord.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Customer Information Sheet");
        stage.setScene(scene);
        stage.show();
        ((Stage) customerInfoButton.getScene().getWindow()).close();
    }
    @FXML
    public void onApptsButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 400);
        Stage stage = new Stage();
        stage.setTitle("Current Appointments");
        stage.setScene(scene);
        stage.show();
        ((Stage) apptsButton.getScene().getWindow()).close();
    }

    public void onSearchButtonClick(ActionEvent actionEvent) {
        CustomerRec_Table.getItems().clear();
        CustomerRec_Table.getItems().addAll(searchCustomersList(customerSearchTextField.getText(), DBACustomers.getAllCustomers()));
    }
    private List<Customers> searchCustomersList(String searchText, List<Customers> listCustomers){
        List<String> searchTextWordsArray = Arrays.asList(searchText.trim().split(" "));
        //Lambda expression 1
        return listCustomers.stream().filter(input -> {
            return searchTextWordsArray.stream().allMatch(word ->
                    input.getCustomerName().toLowerCase().contains(word.toLowerCase()) || Integer.toString(input.getCustomerID()).contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }
}
