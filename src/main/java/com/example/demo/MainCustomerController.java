package com.example.demo;

import BDAccess.DBAAppointments;
import BDAccess.DBACustomers;
import Model.Appointments;
import Model.Customers;
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
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class MainCustomerController implements Initializable {
    public TextField customerSearchTextField;
    public Button searchButton;
    public Button addCustomerButton;
    public Button deleteButton;
    @FXML
    private TableColumn<Customers, String> divisionCol;
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
    ObservableList<Appointments> appointmentsList = DBAAppointments.getAllAppointments();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerList = DBACustomers.getAllCustomers();

        customerID_Col.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        Address_Col.setCellValueFactory(new PropertyValueFactory<>("address"));
        PostalCode_Col.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<Customers, String>("division"));
        PhoneNum_Col.setCellValueFactory(new PropertyValueFactory<>("phone"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

        CustomerRec_Table.setItems(customerList);
    }
    @FXML
    public void onCustInfoButtonclick(ActionEvent actionEvent) throws IOException {
        try{
            if(CustomerRec_Table.getSelectionModel().selectedItemProperty().get() != null) {
                Customers selectedCustomer = CustomerRec_Table.getSelectionModel().selectedItemProperty().get();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer Record.fxml"));
                Parent root = loader.load();
                CustomerRecordController scene2controller = loader.getController();
                scene2controller.populateScene(selectedCustomer);
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No customer selected.");
                alert.setContentText("Please select a customer. Thank you.");
                alert.showAndWait();
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public void onAddCustomerButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Add Customer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 275);
        Stage stage = new Stage();
        stage.setTitle("Add new customer");
        stage.setScene(scene);
        stage.show();
        ((Stage) customerInfoButton.getScene().getWindow()).close();
    }

    public void onDeleteButtonClick(ActionEvent actionEvent) {
        Customers customerInfo = CustomerRec_Table.getSelectionModel().selectedItemProperty().get();
        try {
            int counter = 0;
            for(Appointments appointment : appointmentsList){
                if(appointment.getCustomerId() == customerInfo.getCustomerID()) {
                    counter += 1;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("There are appointments made for this customer.");
                    alert.setContentText("Please delete all appointment related to this customer to delete customer record. Thank you.");
                    alert.showAndWait();
                }
            }
            if (counter == 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Are you sure you want to delete this customer record?");
                alert.setContentText("Please select 'OK' to delete. Thank you.");
                alert.showAndWait();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public void onCustomerRecordSelected(MouseEvent mouseEvent) {
//        Customers selectedCustomer = CustomerRec_Table.getSelectionModel().selectedItemProperty().get();
//        System.out.println(selectedCustomer.getCustomerName());
////        return selectedCustomer;
//    }
}
