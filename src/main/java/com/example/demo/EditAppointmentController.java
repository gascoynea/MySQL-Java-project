package com.example.demo;

import BDAccess.DBAAppointments;
import BDAccess.DBAContacts;
import BDAccess.DBACustomers;
import Database.DBConnection;
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
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class EditAppointmentController implements Initializable {

    @FXML
    public TextField appointmentIDField;
    @FXML
    public Button saveButton;
    @FXML
    public TextField customerNameTF;
    @FXML
    public ComboBox contactNameCB;
    @FXML
    public ComboBox customerNameCB;
    @FXML
    public TextField appointmentEnd;
    @FXML
    public TextField appointmentStart;
    @FXML
    public DatePicker dateDP;
    @FXML
    public ComboBox endHourOfAppointmentCB1;
    @FXML
    public ComboBox hourOfAppointmentCB;
    @FXML
    public TextField estEndTF;
    @FXML
    public TextField estStartTF;
    @FXML
    public Button checkAvailabilityButton;
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
    public TextField lastUpdateField;

    @FXML
    public TextField lastUpdatedByField;
    @FXML
    public TextField locationField;
    
    @FXML
    public TextField titleField;

    @FXML
    public TextField typeField;

    @FXML
    public TextField userIDField;
    Appointments appointmentInformation = null;
    ObservableList<Appointments> appointmentsList =  FXCollections.observableArrayList();
    ObservableList<Customers> customersList = DBACustomers.getAllCustomers();
    ObservableList<Contacts> contactsList = DBAContacts.getAllContacts();
    ObservableList<String> contactNamesList = FXCollections.observableArrayList();
    ObservableList<String> customersNamesList = FXCollections.observableArrayList();
    private static PreparedStatement preparedStatement;
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
        int hour = 1;
        ObservableList hourSlots = FXCollections.observableArrayList();
        while (hour <= 23){
            LocalTime lt = LocalTime.of(hour, 00);
            hourSlots.add(lt);
            hour += 1;
        }
        hourOfAppointmentCB.setItems(hourSlots);
        endHourOfAppointmentCB1.setItems(hourSlots);
    }
    public void onCancelButtonClick(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to cancel editing this appointment?");
        alert.setContentText("Please select 'OK' to cancel. Thank you.");
        alert.showAndWait();

        if(alert.getResult().getButtonData().isCancelButton()) {
            alert.close();
        }
        else{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1400, 400);
            Stage stage = new Stage();
            stage.setTitle("Main Appointments");
            stage.setScene(scene);
            stage.show();
            ((Stage) cancelButton.getScene().getWindow()).close();
        }
    }
    public void populateTextFields(Appointments populatingAppointment) {
        appointmentInformation = populatingAppointment;
        appointmentIDField.setText(String.valueOf(populatingAppointment.getAppointmentID()));
        titleField.setText(populatingAppointment.getTitle());
        descriptionField.setText(populatingAppointment.getDescription());
        locationField.setText(populatingAppointment.getLocation());
        typeField.setText(populatingAppointment.getType());

//        LocalDateTime apptStart = populatingAppointment.getStart().toLocalDateTime();
//        String apptStartFormatted = apptStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        appointmentStart.setText(apptStartFormatted);
        appointmentStart.setText(String.valueOf(populatingAppointment.getStart()));

//        LocalDateTime apptEnd = populatingAppointment.getEnd().toLocalDateTime();
//        String apptEndFormatted = apptEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        appointmentEnd.setText(apptEndFormatted);
        appointmentEnd.setText(String.valueOf(populatingAppointment.getEnd()));

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
        LocalDateTime dateTimeDefault = populatingAppointment.getStart().toLocalDateTime();
        LocalDate dateDefault = LocalDate.from(dateTimeDefault);
        dateDP.setValue(dateDefault);
        ZoneId EST = ZoneId.of("America/New_York");
        TimeZone localOffset = TimeZone.getDefault();
        TimeZone estOffsetTime = TimeZone.getTimeZone(EST);

        int localHoursOffset = localOffset.getOffset(new java.util.Date().getTime()) / 1000 / 60 / 60;
        int estHoursOffset = estOffsetTime.getOffset(new Date().getTime()) / 1000 / 60 / 60;
        int localToestOffset = localHoursOffset - estHoursOffset;
        LocalDateTime estStartDateTime = appointmentInformation.getStart().toLocalDateTime().minusHours(localToestOffset);
        LocalDateTime estEndDateTime = appointmentInformation.getEnd().toLocalDateTime().minusHours(localToestOffset);
        estStartTF.setText(String.valueOf(estStartDateTime));
        estEndTF.setText(String.valueOf(estEndDateTime));
    }
    public void onSaveButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        try
        {
            if(customerNameCB.getValue() == null || contactNameCB.getValue() == null || appointmentIDField.getText().equals("") || titleField.getText().equals("")
            || descriptionField.getText().equals("") || locationField.getText().equals("") || typeField.getText().equals("") || hourOfAppointmentCB.getValue() == null
            || endHourOfAppointmentCB1.getValue() == null || createDateField.getText().equals("") || createdByFeild.getText().equals("") || lastUpdateField.getText().equals("")
            || lastUpdatedByField.getText().equals("") || customerIDField.getText().equals("") || userIDField.getText().equals("") || contactField.getText().equals("")
            || dateDP.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("One or more Text Fields or Combo Box's are empty.");
                alert.setContentText("Please fill in all fields or box's before saving. Thank you.");
                alert.showAndWait();
            }
            else {
                System.out.println("1");
                String dateSelected = dateDP.getValue().toString();
                String startTimeSelectedLocal = hourOfAppointmentCB.getValue().toString();
                String endTimeSelectedLocal = endHourOfAppointmentCB1.getValue().toString();
                String dateStart = dateSelected + " " + startTimeSelectedLocal;
                String dateEnd = dateSelected + " " + endTimeSelectedLocal;
                System.out.println(dateStart);
                LocalDateTime startAppointment = LocalDateTime.parse(dateStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                LocalDateTime endAppointment = LocalDateTime.parse(dateEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                //All time zone IDs local, UTC, and EST
                ZoneId local = ZoneId.of(ZoneId.systemDefault().getId());
                ZoneId EST = ZoneId.of("America/New_York");
                ZoneId UTC = ZoneId.of("UTC");
                ZonedDateTime estStartAppointment = startAppointment.atZone(EST);
                TimeZone localOffset = TimeZone.getDefault();
                TimeZone estOffsetTime = TimeZone.getTimeZone(EST);
                int localHoursOffset = localOffset.getOffset(new Date().getTime()) / 1000 / 60 / 60;
                int estHoursOffset = estOffsetTime.getOffset(new Date().getTime()) / 1000 / 60 / 60;
                int localToestOffset = localHoursOffset - estHoursOffset;
                LocalDateTime estStartDateTime = startAppointment.minusHours(localToestOffset);
                LocalDateTime estEndDateTime = endAppointment.minusHours(localToestOffset);
                String strEstStartDateTime = String.valueOf(estStartDateTime);
                String strEstEndDateTime = String.valueOf(estEndDateTime);
                String localTimeStart = startAppointment.format(format);
                String localTimeEnd = endAppointment.format(format);
                ObservableList<LocalDateTime> apptStart = FXCollections.observableArrayList();
                ObservableList<LocalDateTime> apptEnd = FXCollections.observableArrayList();
                if (startAppointment.isAfter(endAppointment) || startAppointment.isEqual(endAppointment)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("The start time is after the end time or is the same time.");
                    alert.setContentText("Please choose a start before the end time to continue.");
                    alert.showAndWait();
                    return;
                }
                if (startAppointment.getDayOfWeek().toString() == "SATURDAY" || startAppointment.getDayOfWeek().toString() == "SUNDAY") {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("The appointment falls on a weekend.");
                    alert.setContentText("Please choose a day between Monday and Friday.");
                    alert.showAndWait();
                    return;
                }
                if (estStartDateTime.getHour() < 8 || estStartDateTime.getHour() > 21 || estEndDateTime.getHour() > 22 || estEndDateTime.getHour() < 9) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("The appointment falls outside of the available working hours of 08:00 to 22:00 EST (8AM to 10PM EST).");
                    alert.setContentText("Please choose a time between the correct working hours.");
                    alert.showAndWait();
                    return;
                }
                for (Appointments appointment : appointmentsList) {
                    if(appointment.getStart().equals(Timestamp.valueOf(startAppointment)) || appointment.getStart().after(Timestamp.valueOf(startAppointment))
                            && (appointment.getEnd().equals(Timestamp.valueOf(endAppointment)) || appointment.getEnd().before(Timestamp.valueOf(endAppointment)))){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("The appointment time overlaps with another appointment.");
                        alert.setContentText("Please choose another time for the appointment.");
                        alert.showAndWait();
                        return;
                    }
                }
                appointmentStart.setText(localTimeStart);
                appointmentEnd.setText(localTimeEnd);
                estStartTF.setText(strEstStartDateTime);
                estEndTF.setText(strEstEndDateTime);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Are you sure you want to save this appointment?");
                alert.setContentText("Please select 'OK' to save. Thank you.");
                alert.showAndWait();
                if (alert.getResult().getButtonData().isCancelButton()) {
                    alert.close();
                } else {
                    int appointmentID = Integer.parseInt(appointmentIDField.getText());
                    String title = titleField.getText();
                    String description = descriptionField.getText();
                    String location = locationField.getText();
                    String type = titleField.getText();
                    LocalDateTime start = startAppointment;
                    LocalDateTime end = endAppointment;
                    Timestamp dateCreated = Timestamp.valueOf(createDateField.getText());
                    String createdBy = createdByFeild.getText();
                    Timestamp lastUpdatedTimeStamp = Timestamp.valueOf(LocalDateTime.now());
                    String lastUpdatedBy = lastUpdatedByField.getText();
                    int custid = Integer.parseInt(customerIDField.getText());
                    int userid = Integer.parseInt(userIDField.getText());
                    int contactid = Integer.parseInt(contactField.getText());
                    //Adding appointment to the DB
                    String insertStatementAppointments = "UPDATE appointments SET Appointment_ID = ?, Title = ?," +
                            " Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?," +
                            " Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Customer_ID = ?";
                    preparedStatement = DBConnection.getConnection().prepareStatement(insertStatementAppointments);
                    preparedStatement.setInt(1, appointmentID);
                    preparedStatement.setString(2, title);
                    preparedStatement.setString(3, description);
                    preparedStatement.setString(4, location);
                    preparedStatement.setString(5, type);
                    preparedStatement.setTimestamp(6, Timestamp.valueOf(start));
                    preparedStatement.setTimestamp(7, Timestamp.valueOf(end));
                    preparedStatement.setTimestamp(8, dateCreated);
                    preparedStatement.setString(9, createdBy);
                    preparedStatement.setTimestamp(10, lastUpdatedTimeStamp);
                    preparedStatement.setString(11, lastUpdatedBy);
                    preparedStatement.setInt(12, custid);
                    preparedStatement.setInt(13, contactid);
                    preparedStatement.setInt(14, userid);
                    preparedStatement.setInt(15, appointmentID);
                    preparedStatement.execute();
                    preparedStatement.close();
                    System.out.println("PS executed");
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1400, 400);
                    Stage stage = new Stage();
                    stage.setTitle("Main Appointments");
                    stage.setScene(scene);
                    stage.show();
                    ((Stage) cancelButton.getScene().getWindow()).close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
    @FXML
    public void onCheckClick(ActionEvent actionEvent) {
        if (dateDP.getValue() == null || hourOfAppointmentCB.getValue() == null || endHourOfAppointmentCB1.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The date, start time, or end time have not been selected.");
            alert.setContentText("Please choose a date, start, or end time to continue.");
            alert.showAndWait();
        } else {
            String dateSelected = dateDP.getValue().toString();
            String startTimeSelectedLocal = hourOfAppointmentCB.getValue().toString();
            String endTimeSelectedLocal = endHourOfAppointmentCB1.getValue().toString();
            String dateStart = dateSelected + " " + startTimeSelectedLocal;
            String dateEnd = dateSelected + " " + endTimeSelectedLocal;

            LocalDateTime startAppointment = LocalDateTime.parse(dateStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime endAppointment = LocalDateTime.parse(dateEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //All time zone IDs local, UTC, and EST
            ZoneId local = ZoneId.of(ZoneId.systemDefault().getId());
            ZoneId EST = ZoneId.of("America/New_York");
            ZoneId UTC = ZoneId.of("UTC");
            ZonedDateTime estStartAppointment = startAppointment.atZone(EST);
            TimeZone localOffset = TimeZone.getDefault();
            TimeZone estOffsetTime = TimeZone.getTimeZone(EST);
            int localHoursOffset = localOffset.getOffset(new Date().getTime()) / 1000 / 60 / 60;
            int estHoursOffset = estOffsetTime.getOffset(new Date().getTime()) / 1000 / 60 / 60;
            int localToestOffset = localHoursOffset - estHoursOffset;
            int localToUTCHourOffset = (localHoursOffset);
            LocalDateTime estStartDateTime = startAppointment.minusHours(localToestOffset);
            LocalDateTime estEndDateTime = endAppointment.minusHours(localToestOffset);
            String strEstStartDateTime = String.valueOf(estStartDateTime);
            String strEstEndDateTime = String.valueOf(estEndDateTime);
            String localTimeStart = startAppointment.format(format);
            String localTimeEnd = endAppointment.format(format);
            ObservableList<LocalDateTime> apptStart = FXCollections.observableArrayList();
            ObservableList<LocalDateTime> apptEnd = FXCollections.observableArrayList();
            if (startAppointment.isAfter(endAppointment) || startAppointment.isEqual(endAppointment)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The start time is after the end time or is the same time.");
                alert.setContentText("Please choose a start before the end time to continue.");
                alert.showAndWait();
                return;
            }
            if (startAppointment.getDayOfWeek().toString() == "SATURDAY" || startAppointment.getDayOfWeek().toString() == "SUNDAY") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The appointment falls on a weekend.");
                alert.setContentText("Please choose a day between Monday and Friday.");
                alert.showAndWait();
                return;
            }
            if (estStartDateTime.getHour() < 8 || estStartDateTime.getHour() > 21 || estEndDateTime.getHour() > 22 || estEndDateTime.getHour() < 9) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The appointment falls outside of the available working hours of 08:00 to 22:00 EST (8AM to 10PM EST).");
                alert.setContentText("Please choose a time between the correct working hours.");
                alert.showAndWait();
                return;
            }
            for (Appointments appointment : appointmentsList) {
                if(appointment.getStart().equals(Timestamp.valueOf(startAppointment)) || appointment.getStart().after(Timestamp.valueOf(startAppointment))
                        && (appointment.getEnd().equals(Timestamp.valueOf(endAppointment)) || appointment.getEnd().before(Timestamp.valueOf(endAppointment)))){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("The appointment time overlaps with another appointment.");
                    alert.setContentText("Please choose another time for the appointment.");
                    alert.showAndWait();
                    return;
                }
            }
            appointmentStart.setText(localTimeStart);
            appointmentEnd.setText(localTimeEnd);
            estStartTF.setText(strEstStartDateTime);
            estEndTF.setText(strEstEndDateTime);
        }
    }
}

