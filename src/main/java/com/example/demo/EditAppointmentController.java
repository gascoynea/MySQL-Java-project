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
import java.lang.reflect.Type;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class EditAppointmentController implements Initializable {

    @FXML
    public TextField appointmentIDField;
    @FXML
    public Button saveButton;
    @FXML
    public TextField customerNameTF;
    @FXML
    public ComboBox contactNameCB;
    public ComboBox customerNameCB;

    @FXML
    private Button cancelButton;

    @FXML
    public TextField contactField;

    @FXML
    public TextField createDateField;

    @FXML
    public TextField createdByFeild;

    @FXML
    public TextField customerIDField;

    @FXML
    public TextField descriptionField;

    @FXML
    public TextField endField;

    @FXML
    public TextField lastUpdateField;

    @FXML
    public TextField lastUpdatedByField;

    @FXML
    public TextField locationField;

    @FXML
    public TextField startField;

    @FXML
    public TextField titleField;

    @FXML
    public TextField typeField;

    @FXML
    public TextField userIDField;
    Appointments appointmentInformation = null;
    ObservableList<Appointments> currentAppointmentsList = DBAAppointments.getAllAppointments();
    ObservableList<Customers> customersList = DBACustomers.getAllCustomers();
    ObservableList<Contacts> contactsList = DBAContacts.getAllContacts();
    ObservableList<String> contactNamesList = FXCollections.observableArrayList();
    ObservableList<String> customersNamesList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Contacts contact : contactsList){
            contactNamesList.add(contact.getContact_Name());
        }
        contactNameCB.setItems(contactNamesList);

        for(Customers customer: customersList){
            customersNamesList.add(customer.getCustomerName());
        }
        customerNameCB.setItems(customersNamesList);
        lastUpdateField.setText(getTime());
        lastUpdatedByField.setText(LoginFormController.reference.userName);
    }
    public void onCancelButtonClick(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to cancel editing this appointment?");
        alert.setContentText("Please select 'OK' to cancel. Thank you.");
        alert.showAndWait();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 400);
        Stage stage = new Stage();
        stage.setTitle("Main Appointments");
        stage.setScene(scene);
        stage.show();
        ((Stage) cancelButton.getScene().getWindow()).close();
    }


    public void populateTextFields(Appointments populatingAppointment) {
        appointmentInformation = populatingAppointment;
        appointmentIDField.setText(String.valueOf(populatingAppointment.getAppointmentID()));
        titleField.setText(populatingAppointment.getTitle());
        descriptionField.setText(populatingAppointment.getDescription());
        locationField.setText(populatingAppointment.getLocation());
        typeField.setText(populatingAppointment.getType());
        startField.setText(String.valueOf(populatingAppointment.getStart()));
        endField.setText(String.valueOf(populatingAppointment.getEnd()));
        createDateField.setText(String.valueOf(populatingAppointment.getCreateDate()));
        createdByFeild.setText(populatingAppointment.getCreatedBy());
        lastUpdateField.setText(String.valueOf(populatingAppointment.getLastUpdate()));
        lastUpdatedByField.setText(populatingAppointment.getLastUpdatedBy());
        customerIDField.setText(String.valueOf(populatingAppointment.getCustomerId()));
        userIDField.setText(String.valueOf(populatingAppointment.getUserId()));
        contactField.setText(String.valueOf(populatingAppointment.getContactId()));
        for (Customers customer : customersList){
            if(customer.getCustomerID() == appointmentInformation.getCustomerId()){
                customerNameTF.setText(customer.getCustomerName());
                customerNameCB.getSelectionModel().select(customerNameTF.getText());
                for(Contacts contact : contactsList) {
                    String conID = String.valueOf(contact.getConID());
                    if (conID.equals(contactField.getText())) {
                        contactNameCB.getSelectionModel().select(contact.getContact_Name());
                    }
                }
            }
        }
        lastUpdateField.setText(getTime());
        lastUpdatedByField.setText(LoginFormController.reference.userName);
    }

    public void onSaveButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        try
        {
            if(customerNameCB.getValue() == null || contactNameCB.getValue() == null || appointmentIDField.getText().equals("") || titleField.getText().equals("")
            || descriptionField.getText().equals("") || locationField.getText().equals("") || typeField.getText().equals("") || startField.getText().equals("")
            || endField.getText().equals("") || createDateField.getText().equals("") || createdByFeild.getText().equals("") || lastUpdateField.getText().equals("")
            || lastUpdatedByField.getText().equals("") || customerIDField.getText().equals("") || userIDField.getText().equals("") || contactField.getText().equals("")
            || customerNameTF.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("One or more Text Fields or Combo Box's are empty.");
                alert.setContentText("Please fill in all fields or box's before saving. Thank you.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Are you sure you want to update this appointment?");
                alert.setContentText("Please select 'OK' to update. Thank you.");
                alert.showAndWait();
                System.out.println(currentAppointmentsList.get(0).getTitle());
                appointmentInformation.setAppointmentID(Integer.parseInt(appointmentIDField.getText()));
                appointmentInformation.setTitle(titleField.getText());
                appointmentInformation.setDescription(descriptionField.getText());
                appointmentInformation.setLocation(locationField.getText());
                appointmentInformation.setType(typeField.getText());
                appointmentInformation.setStart(Timestamp.valueOf(startField.getText()));
                appointmentInformation.setEnd(Timestamp.valueOf(endField.getText()));
                appointmentInformation.setCreateDate(Timestamp.valueOf(createDateField.getText()));
                appointmentInformation.setCreatedBy(createdByFeild.getText());
                appointmentInformation.setLastUpdate(Timestamp.valueOf(lastUpdateField.getText()));
                appointmentInformation.setLastUpdatedBy(lastUpdatedByField.getText());
                appointmentInformation.setCustomerId(Integer.parseInt(customerIDField.getText()));
                appointmentInformation.setUserId(Integer.parseInt(userIDField.getText()));
                appointmentInformation.setContactId(Integer.parseInt(contactField.getText()));
                System.out.println(appointmentInformation.getDescription() + appointmentInformation.getAppointmentID());
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1250, 400);
                Stage stage = new Stage();
                stage.setTitle("Main Appointments");
                stage.setScene(scene);
                stage.show();
                ((Stage) cancelButton.getScene().getWindow()).close();
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Update appointment in Table List
//        try {
//            for (Appointments appointment : currentAppointmentsList) {
//                if (appointment.getAppointmentID() == appointmentInformation.getAppointmentID()) {
//                    appointment.setTitle(appointmentInformation.getTitle());
//                    appointment.setDescription(appointmentInformation.getDescription());
//                    appointment.setLocation(appointmentInformation.getLocation());
//                    appointment.setLastUpdatedBy(appointmentInformation.getLastUpdatedBy());
//                    System.out.println(appointment.getTitle());
//                    System.out.println(currentAppointmentsList.get(0).getTitle());
//                }
//            }
//            //Truncate Table data in appointments
//            Connection conn = DBConnection.getConnection();
//            String sql1 = "TRUNCATE client_schedule.appointments";
//            PreparedStatement truncatePS = conn.prepareStatement(sql1);
//            truncatePS.executeQuery();
//            //Add new Data to DB Table appointments
//            for(Appointments appt : currentAppointmentsList){
//                String sql2 = "insert into client_schedule.appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)" + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//                PreparedStatement ps = conn.prepareStatement(sql2);
//                ps.setInt(1, appt.getAppointmentID());
//                ps.setString(2, appt.getTitle());
//                ps.setString(3, appt.getDescription());
//                ps.setString(4, appt.getLocation());
//                ps.setString(5, appt.getTitle());
//                ps.setTimestamp(6,appt.getStart());
//                ps.setTimestamp(7, appt.getEnd());
//                ps.setTimestamp(8, appt.getCreateDate());
//                ps.setString(9, appt.getCreatedBy());
//                ps.setTimestamp(10, appt.getLastUpdate());
//                ps.setString(11, appt.getLastUpdatedBy());
//                ps.setInt(12, appt.getCustomerId());
//                ps.setInt(13, appt.getUserId());
//                ps.setInt(14, appt.getContactId());
//                ps.executeQuery();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
    @FXML
    public void onContactNameSelected(ActionEvent actionEvent) {
        String contactName = contactNameCB.getValue().toString();
        int contactID = 0;
        for (Contacts contact : contactsList){
            if(contactName.equals(contact.getContact_Name())){
                contactID = contact.getConID();
            }
        }
        contactField.setText(String.valueOf(contactID));
    }
    @FXML
    public void onCustomerNameSelected(ActionEvent actionEvent) {
        String customerName = customerNameCB.getValue().toString();
        int customerID = 0;
        for (Customers customer : customersList) {
            if(customer.getCustomerName().equals(customerName)){
                customerID = customer.getCustomerID();
            }
        }
        customerIDField.setText(String.valueOf(customerID));
//        customerNameTF.setText(customerName);
    }
    public String getTime(){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        return timeStamp;
    }
}

