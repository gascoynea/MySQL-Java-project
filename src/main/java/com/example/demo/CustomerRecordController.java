package com.example.demo;

import BDAccess.DBAAppointments;
import BDAccess.DBACountries;
import BDAccess.DBACustomers;
import BDAccess.DBAFirstLevelDivisions;
import Database.DBConnection;
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
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Manipulates customer record FXML.
 */
public class CustomerRecordController implements Initializable {
    @FXML
    public TextField countryIDTF;
    @FXML
    public TextField divisionIDTF;
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
    ObservableList<Appointments> customerAppointmentsList = FXCollections.observableArrayList();
    ObservableList<Appointments> customerAppointmentsListUpdated = FXCollections.observableArrayList();
    ObservableList<Customers> customersList = DBACustomers.getAllCustomers();
    ObservableList<FirstLevelDivisions> divisionsList = DBAFirstLevelDivisions.getAllFirstLevelDivisions();
    ObservableList<Countries> countriesList = DBACountries.getAllCountries();
    ObservableList<Appointments> apptList = DBAAppointments.getAllAppointments();
    ObservableList<String> divisionNames = DBAFirstLevelDivisions.getDivisionNames();
    ObservableList<String> countryNames = DBACountries.getCountryNames();
    ObservableList<String> updatedDivNames = FXCollections.observableArrayList();
    Customers customer;

    public Appointments customerAppointmentInfo;
    private Customers customerInfo;
    private static PreparedStatement preparedStatement;

    /**
     * Prepopulates tableviewappointments
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Appointment Table populations
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
        tableViewAppointments.setItems(customerAppointmentsList);
    }

    /**
     * On save button pressed.
     * Checks if text boxes are empty.
     * Confirmation for saving customer into Database.
     * Updates customer to Database.
     * @param actionEvent
     */
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

                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    int custID = Integer.parseInt(customerIDTF.getText());
                    String custName = nameTF.getText();
                    String custAddress = addressTF.getText();
                    String custPostalCode = postalcodeTF.getText();
                    String custPhoneNumber = phoneNumberTF.getText();
                    int custDivisionID = Integer.parseInt(divisionIDTF.getText());

                    LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
                    String now2 = now.format(format);
                    LocalDateTime lastUpdateTime = LocalDateTime.parse(now2, format);

                    String custLastUpdatedBy = LoginFormController.reference.userName;
                    String createdBy = customer.getCreatedBy();
                    LocalDateTime custcd = customer.getCreateDate().toLocalDateTime();

//                    Adding customer to the DB
                    String customerStatement = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?," +
                            " Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?," +
                            " Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

                    preparedStatement = DBConnection.getConnection().prepareStatement(customerStatement);

                    preparedStatement.setInt(1, custID);
                    preparedStatement.setString(2, custName);
                    preparedStatement.setString(3, custAddress);
                    preparedStatement.setString(4, custPostalCode);
                    preparedStatement.setString(5, custPhoneNumber);
                    preparedStatement.setTimestamp(6, Timestamp.valueOf(custcd));
                    preparedStatement.setString(7, createdBy);
                    preparedStatement.setTimestamp(8, Timestamp.valueOf(lastUpdateTime));
                    preparedStatement.setString(9, custLastUpdatedBy);
                    preparedStatement.setInt(10, custDivisionID);
                    preparedStatement.setInt(11, custID);
                    preparedStatement.execute();

                    System.out.println("executed");
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

    /**
     * When cancel button pressed.
     * Asks for confirmation with alert.
     * if confirmed opens MAin Customers FXML
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * On delete button clicked.
     * If record appointment not selected error shown.
     * Confirmation for deletion of appointment form customer.
     * Deletes appointment form database and refreshes appointment list.
     * @param actionEvent
     */
    public void onDeleteButtonClick(ActionEvent actionEvent) {
        Connection connection = DBConnection.openConnection();
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
                    Appointments appointment = tableViewAppointments.getSelectionModel().selectedItemProperty().get();
                    for (Appointments appointments : apptList) {
                        if(appointment.getAppointmentID() == appointments.getAppointmentID()) {
                            String apptDelete = "DELETE FROM appointments WHERE Appointment_ID = ?";
                            PreparedStatement appointmentDelete = connection.prepareStatement(apptDelete);
                            appointmentDelete.setInt(1, appointment.getAppointmentID());
                            appointmentDelete.executeUpdate();
                            appointmentDelete.close();
                            appointmentTableRefresh(appointment.getAppointmentID());
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Opens add appointment FXML on ass appointment clicked.
     * @param actionEvent
     * @throws IOException
     */
    public void onAddAppointmentButtonClick(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Add Appointment.fxml"));
            Parent root = loader.load();
            AddAppointmentController scene2controller = loader.getController();
            scene2controller.populateTextFields(customer);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Add Appointment");
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Opens Edit appointment FXML on ass appointment clicked.
     * Also, check to see if appointment was selected from table view.
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * Used to create unique customer ID.
     * @param customerIDs
     * @return
     */
    public int newCustomerID(List customerIDs){
        int newCustomerID;
        int possibleID = 1;
        while (customerIDs.contains(possibleID)){
            possibleID += 1;
        }
        newCustomerID = possibleID;
        return newCustomerID;
    }

    /**
     * Auto populates Country ID when country is selected in country combo box.
     * populates available divisions in divsions combo box with pertinent division names.
     * @param actionEvent
     */
    public void onCountrySelected(ActionEvent actionEvent) {
        String countrySelected = countryComBox.getValue();
        int cID = 0;
        ObservableList<String> divNameTemp = FXCollections.observableArrayList();
        if(countrySelected.equals("U.S")){
            cID = 1;
            for (FirstLevelDivisions divisions : divisionsList){
                if (divisions.getCountryID() == cID){
                    divNameTemp.add(divisions.getDivision());
                }
            }
        }
        if(countrySelected.equals("UK")){
            cID = 2;
            for (FirstLevelDivisions divisions : divisionsList){
                if (divisions.getCountryID() == cID){
                    divNameTemp.add(divisions.getDivision());
                }
            }
        }
        if(countrySelected.equals("Canada")){
            cID = 3;
            for (FirstLevelDivisions divisions : divisionsList){
                if (divisions.getCountryID() == cID){
                    divNameTemp.add(divisions.getDivision());
                }
            }
        }
        updatedDivNames = divNameTemp;
        stateProvinceComBox.setItems(updatedDivNames);
        countryIDTF.setText(String.valueOf(cID));
    }

    /**
     * Populates fields with data from selected customer from the Main customers FXML table view.
     * @param customerInformation
     */
    public void populateScene(Customers customerInformation){
        customer = customerInformation;
        customerIDTF.setText(String.valueOf(customer.getCustomerID()));
        countryComBox.setItems(countryNames);
        stateProvinceComBox.setItems(divisionNames);
        Customers customerInfo = customerInformation;
        int idCustomer = customerInfo.getCustomerID();
        String nameCustomer = customerInfo.getCustomerName();
        String address = customerInfo.getAddress();
        String postalCode = customerInfo.getPostCode();
        String phone = customerInfo.getPhone();
        int divisionIDCustomer = customerInfo.getDivisionID();
        int countryID = 0;
        String countryName = "";
        String divName = "";

        countryIDTF.setText(String.valueOf(idCustomer));
        nameTF.setText(nameCustomer);
        addressTF.setText(address);
        postalcodeTF.setText(postalCode);
        phoneNumberTF.setText(phone);
        divisionIDTF.setText(String.valueOf(divisionIDCustomer));
        for (FirstLevelDivisions division : divisionsList){
            if(division.getDivID() == divisionIDCustomer){
                countryID = division.getCountryID();
                divName = division.getDivision();
                if(countryID == 1){
                    countryName = "U.S";
                }
                if(countryID == 2){
                    countryName = "UK";
                }
                if(countryID == 3){
                    countryName = "Canada";
                }
            }
        }
        countryIDTF.setText(String.valueOf(countryID));
        stateProvinceComBox.setValue(divName);
        countryComBox.setValue(countryName);

        //Populating appointment window
        for(Appointments appointment : apptList){
            if(appointment.getCustomerId() == customerInfo.getCustomerID()){
                customerAppointmentsList.add(appointment);
            }
        }
    }

    /**
     * Auto Populated division ID text field once division name selected.
     * @param actionEvent
     */
    public void onDivisionSelected(ActionEvent actionEvent) {
        String divName = stateProvinceComBox.getValue();
        ObservableList<FirstLevelDivisions> divList = DBAFirstLevelDivisions.getAllFirstLevelDivisions();
        for(FirstLevelDivisions division : divList){
            if (division.getDivision().equals(divName)){
                divisionIDTF.setText(String.valueOf(division.getDivID()));
            }
        }
    }

    /**
     * Was going to be implemented to refersh appointment table but found a different way to do.
     * @param inAppointmentID
     */
    public void appointmentTableRefresh(int inAppointmentID){
        ObservableList<Appointments> appointmentsObservableList = DBAAppointments.getAllAppointments();
        ObservableList<Appointments> refreshedAppt = FXCollections.observableArrayList();
        for(Appointments appointment : appointmentsObservableList){
            if(appointment.getCustomerId() == customer.getCustomerID()){
                refreshedAppt.add(appointment);
            }
            tableViewAppointments.setItems(refreshedAppt);
        }
    }
}

