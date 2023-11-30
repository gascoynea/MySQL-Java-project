package com.example.demo;

import BDAccess.DBAAppointments;
import BDAccess.DBACustomers;
import Database.DBConnection;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This controller is used to manipulate the Main Customers FXML.
 *
 * Lambda expression in searchCustomersList method
 */
public class MainCustomerController implements Initializable {
    @FXML
    public TextField customerSearchTextField;
    @FXML
    public Button searchButton;
    @FXML
    public Button addCustomerButton;
    @FXML
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

    /**
     * Initializes window with certain data
     * Usually I use to populate fields that just require a list of objects from a DB
     * Such as a tableview or ComboBox
     * @param url
     * @param resourceBundle
     */
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

    /**
     *Opens the Customer Record FXML when clicked.
     * Also, alerts of no customer selected from table view.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void onCustInfoButonclick(ActionEvent actionEvent) throws IOException {
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

    /**
     * Opens the Main Appointment FXML when pressed.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void onApptsButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 400);
        Stage stage = new Stage();
        stage.setTitle("Current Appointments");
        stage.setScene(scene);
        stage.show();
        ((Stage) apptsButton.getScene().getWindow()).close();
    }

    /**
     * Method calls the searchCustomersList method when button pressed.
     * @param actionEvent
     */
    @FXML
    public void onSearchButtonClick(ActionEvent actionEvent) {
        CustomerRec_Table.getItems().clear();
        CustomerRec_Table.getItems().addAll(searchCustomersList(customerSearchTextField.getText(), DBACustomers.getAllCustomers()));
    }

    /**
     * LAMBDA EXPRESSION: Lambda expression 1 for allows for less line space while doing more functions
     *
     * Method to search through table view by customer ID or customer name.
     * @param searchText
     * @param listCustomers
     * @return
     */
    @FXML
    private List<Customers> searchCustomersList(String searchText, List<Customers> listCustomers){
        List<String> searchTextWordsArray = Arrays.asList(searchText.trim().split(" "));
        /*
        Lambda expression 1 for allows for less line space while doing more functions.
         */
        return listCustomers.stream().filter(input -> {
            return searchTextWordsArray.stream().allMatch(word ->
                    input.getCustomerName().toLowerCase().contains(word.toLowerCase()) || Integer.toString(input.getCustomerID()).contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }

    /**
     * Method called when Add customer button clicked.
     * Opens up Add Customer FXML.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void onAddCustomerButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Add Customer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 275);
        Stage stage = new Stage();
        stage.setTitle("Add new customer");
        stage.setScene(scene);
        stage.show();
        ((Stage) customerInfoButton.getScene().getWindow()).close();
    }

    /**
     * Method called when delete button clicked.
     * Causes selected customer in the CustomerRec_Table tableview to be deleted and removed form the
     * Table as well as the DB.
     * Deletes all appointments with customer id of customer being deleted.
     * @param actionEvent
     */
    @FXML
    public void onDeleteButtonClick(ActionEvent actionEvent) {
        Customers customerInfo = CustomerRec_Table.getSelectionModel().selectedItemProperty().get();
        Connection connection = DBConnection.openConnection();
        try {
            if(customerInfo == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No customer selected.");
                alert.setContentText("Please select a customer to delete. Thank you.");
                alert.showAndWait();
            }
            else {
                int counter = 0;
                for (Appointments appointment : appointmentsList) {
                    if (appointment.getCustomerId() == customerInfo.getCustomerID()) {
                        counter += 1;
                    }
                }
                if (counter == 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Are you sure you want to delete this customer record?");
                    alert.setContentText("Please select 'OK' to delete. Thank you.");
                    alert.showAndWait();
                    if(alert.getResult().getButtonData().isCancelButton()){
                        alert.close();
                    }
                    else {
                        for (Customers customer : customerList){
                            if(customer.getCustomerID() == customerInfo.getCustomerID()){
                                int custid = customer.getCustomerID();
                                String deleteCustomer = "DELETE FROM customers WHERE Customer_ID = ?";
                                PreparedStatement psDelete = connection.prepareStatement(deleteCustomer);
                                psDelete.setInt(1, custid);
                                psDelete.executeUpdate();
                                psDelete.close();
                                CustomerRec_Table.setItems(DBACustomers.getAllCustomers());
                            }
                        }
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("You just deleted a customer record.");
                        alert.setContentText("Keep it up!");
                        alert.showAndWait();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("There are " + counter + " appointment/s made for this customer.");
                    alert.setContentText("Do you want to delete all appointments attached to the customer record as well as the customer record?.");
                    alert.showAndWait();
                    if(alert.getResult().getButtonData().isCancelButton()){
                        alert.close();
                    }
                    else{
                        for (Customers customer : customerList){
                            if(customer.getCustomerID() == customerInfo.getCustomerID()){
                                int custid = customer.getCustomerID();
                                for(Appointments appointment : appointmentsList) {
                                    if(appointment.getCustomerId() == custid) {
                                        int apptID = appointment.getAppointmentID();
                                        String deleteAppointment = "DELETE FROM appointments WHERE Customer_ID = ?";
                                        PreparedStatement psApptDelete = connection.prepareStatement(deleteAppointment);
                                        psApptDelete.setInt(1, custid);
                                        psApptDelete.executeUpdate();
                                        psApptDelete.close();
                                        String deleteCustomer = "DELETE FROM customers WHERE Customer_ID = ?";
                                        PreparedStatement psDelete = connection.prepareStatement(deleteCustomer);
                                        psDelete.setInt(1, custid);
                                        psDelete.executeUpdate();
                                        psDelete.close();
                                        CustomerRec_Table.setItems(DBACustomers.getAllCustomers());
                                    }
                                }
                            }
                        }
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("You just deleted a customer record.");
                        alert.setContentText("Keep it up!");
                        alert.showAndWait();
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
