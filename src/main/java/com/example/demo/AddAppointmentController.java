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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class AddAppointmentController implements Initializable {
    @FXML
    public TextField createdByTF;
    @FXML
    public ComboBox contactListCB;
    @FXML
    public DatePicker dateDP;
    @FXML
    public ComboBox hourOfAppointmentCB;
    @FXML
    public ComboBox endHourOfAppointmentCB1;
    @FXML
    public TextField appointmentStart;
    @FXML
    public TextField appointmentEnd;
    @FXML
    public Button checkAvailabilityButton;
    @FXML
    public TextField estStartTF;
    @FXML
    public TextField estEndTF;
    @FXML
    private TextField apptTF;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField contactIDTF;

    @FXML
    private TextField customerIDTF;

    @FXML
    private ComboBox customerListDD;

    @FXML
    private TextField dateCreatedTF;

    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField lastUpdatedByTF;

    @FXML
    private TextField lastUpdatedTF;

    @FXML
    private TextField locationTF;

    @FXML
    private Button saveButton;

    @FXML
    private TextField titleTF;

    @FXML
    private TextField typeTF;

    @FXML
    private TextField userIDTF;
    ObservableList<Customers> customerList = FXCollections.observableArrayList();
    ObservableList<Appointments> appointmentsList =  FXCollections.observableArrayList();
    ObservableList<Contacts> contactsList =  FXCollections.observableArrayList();
    ObservableList<LocalTime> hourSlots = FXCollections.observableArrayList();
    private static PreparedStatement preparedStatement;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Populating Customer Name ComboBox
        customerList = DBACustomers.getAllCustomers();
        ObservableList<String> customerNames = FXCollections.observableArrayList();
        for(Customers customer : customerList){
            customerNames.add(customer.getCustomerName());
        }
        customerListDD.setItems(customerNames);

        //Populating appointment ID Text Field with generated ID from method newAppointmentID()
        appointmentsList = DBAAppointments.getAllAppointments();
        ObservableList<Integer> appointmentIDsList = FXCollections.observableArrayList();
        for (Appointments appoinment : appointmentsList){
            appointmentIDsList.add(appoinment.getAppointmentID());
        }
        apptTF.setText(String.valueOf(newAppointmentID(appointmentIDsList)));

        //Populating COntact Names in contact name combo box
        contactsList = DBAContacts.getAllContacts();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for(Contacts contact : contactsList){
            contactNames.add(contact.getContact_Name());
            }
        contactListCB.setItems(contactNames);

        //populating username, userID, created by, and last updated by. As well as, setting time for last update and create date
        userIDTF.setText(String.valueOf(LoginFormController.reference.userID));
        createdByTF.setText(LoginFormController.reference.userName);
        lastUpdatedByTF.setText(LoginFormController.reference.userName);
        dateCreatedTF.setText(getTime());
        lastUpdatedTF.setText(getTime());

        int hour = 1;
        while (hour <= 23){
            LocalTime lt = LocalTime.of(hour, 00);
            hourSlots.add(lt);
            hour += 1;
        }
        hourOfAppointmentCB.setItems(hourSlots);
        endHourOfAppointmentCB1.setItems(hourSlots);
//        }
    }
    public void onSaveButtonClick(ActionEvent actionEvent) {
        try {
            if(customerListDD.getValue() == null || contactListCB.getValue() == null || apptTF.getText().equals("") || titleTF.getText().equals("")
                    || descriptionTF.getText().equals("") || locationTF.getText().equals("") || typeTF.getText().equals("") || hourOfAppointmentCB.getValue() == null
                    || endHourOfAppointmentCB1.getValue() == null || dateCreatedTF.getText().equals("") || createdByTF.getText().equals("") || lastUpdatedTF.getText().equals("")
                    || lastUpdatedByTF.getText().equals("") || customerIDTF.getText().equals("") || userIDTF.getText().equals("") || contactIDTF.getText().equals("")
                    || dateDP.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("One or more Text Fields or Combo Box's are empty.");
                alert.setContentText("Please fill in all fields or box's before saving. Thank you.");
                alert.showAndWait();
            }
            else {
                String dateSelected = dateDP.getValue().toString();
                String startTimeSelectedLocal = hourOfAppointmentCB.getValue().toString();
                String endTimeSelectedLocal = endHourOfAppointmentCB1.getValue().toString();

                String dateStart = dateSelected + " " + startTimeSelectedLocal;
                String dateEnd = dateSelected + " " + endTimeSelectedLocal;

                LocalDateTime startAppointment = LocalDateTime.parse(dateStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                LocalDateTime endAppointment = LocalDateTime.parse(dateEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedStart = startAppointment.format(format);
                String formattedEnd = endAppointment.format(format);

                //All time zone IDs local, UTC, and EST
                ZoneId local = ZoneId.of(ZoneId.systemDefault().getId());
                ZoneId EST = ZoneId.of("America/New_York");
                ZoneId UTC = ZoneId.of("UTC");

                ZonedDateTime localStartAppointment = startAppointment.atZone(local);
                ZonedDateTime utcStartAppointment = startAppointment.atZone(UTC);
                ZonedDateTime estStartAppointment = startAppointment.atZone(EST);

                ZonedDateTime localeEndAppointment = endAppointment.atZone(local);
                ZonedDateTime utcEndAppointment = endAppointment.atZone(UTC);
                ZonedDateTime estEndAppointment = endAppointment.atZone(EST);

                ZoneOffset localOffset = localStartAppointment.getOffset();
                ZoneOffset utcOffset = utcStartAppointment.getOffset();
                ZoneOffset estOffset = estStartAppointment.getOffset();

                ZoneOffset localOffsetEnd = localeEndAppointment.getOffset();
                ZoneOffset utcOffsetEnd = utcEndAppointment.getOffset();
                ZoneOffset estOffsetEnd = estEndAppointment.getOffset();

                int secOffsetlocal = localOffset.getTotalSeconds();
                int secOffsetUTC = utcOffset.getTotalSeconds();
                int secOffset = estOffset.getTotalSeconds();

                int secOffsetlocalEnd = localOffsetEnd.getTotalSeconds();
                int secOffsetUTCEnd = utcOffsetEnd.getTotalSeconds();
                int secOffsetEnd = estOffsetEnd.getTotalSeconds();

                LocalDateTime localAdjustedTime = startAppointment.plusSeconds(secOffsetlocal);
                LocalDateTime utcAdjustedTime = startAppointment.plusSeconds(secOffsetUTC);
                LocalDateTime estAdjustedTime = startAppointment.plusSeconds(secOffset);


                LocalDateTime localAdjustedTimeEnd = endAppointment.plusSeconds(secOffsetlocalEnd);
                LocalDateTime utcAdjustedTimeEnd = endAppointment.plusSeconds(secOffsetUTCEnd);
                LocalDateTime estAdjustedTimeEnd = endAppointment.plusSeconds(secOffsetEnd);

                String localTimeStart = localAdjustedTime.format(format);
                String utcTimeStart = utcAdjustedTime.format(format);
                String estTimeStart = estAdjustedTime.format(format);

                String localTimeEnd = localAdjustedTimeEnd.format(format);
                String utcTimeEnd = utcAdjustedTimeEnd.format(format);
                String estTimeEnd = estAdjustedTimeEnd.format(format);

                ObservableList<LocalDateTime> apptStart = FXCollections.observableArrayList();
                ObservableList<LocalDateTime> apptEnd = FXCollections.observableArrayList();
                if (localAdjustedTime.isAfter(localAdjustedTimeEnd) || localAdjustedTime.isEqual(localAdjustedTimeEnd)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("The start time is after the end time or is the same time.");
                    alert.setContentText("Please choose a start before the end time to continue.");
                    alert.showAndWait();
                    return;
                }
                if (localAdjustedTime.getDayOfWeek().toString() == "SATURDAY" || localAdjustedTime.getDayOfWeek().toString() == "SUNDAY") {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("The appointment falls on a weekend.");
                    alert.setContentText("Please choose a day between Monday and Friday.");
                    alert.showAndWait();
                    return;
                }
                if (estAdjustedTime.getHour() <8 || estAdjustedTime.getHour() > 21 || estAdjustedTimeEnd.getHour() > 22 || estAdjustedTimeEnd.getHour() < 9) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("The appointment falls outside of the available working hours of 08:00 to 22:00 EST (8AM to 10PM EST).");
                    alert.setContentText("Please choose a time between the correct working hours.");
                    alert.showAndWait();
                    return;
                }
                for(Appointments appointment : appointmentsList) {
                    apptStart.add(appointment.getStart().toLocalDateTime());
                    apptEnd.add(appointment.getEnd().toLocalDateTime());
                    LocalDateTime startKnown = appointment.getStart().toLocalDateTime();
                    LocalDateTime endKnown = appointment.getStart().toLocalDateTime();

                    if ((startKnown.isAfter(localAdjustedTime) || startKnown.isEqual(localAdjustedTime)) && (endKnown.isEqual(localAdjustedTimeEnd) || endKnown.isAfter(localAdjustedTimeEnd))) {
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
                estStartTF.setText(estTimeStart);
                estEndTF.setText(estTimeEnd);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Are you sure you want to save this appointment?");
                alert.setContentText("Please select 'OK' to save. Thank you.");
                alert.showAndWait();
                if(alert.getResult().getButtonData().isCancelButton()) {
                    alert.close();
                }
                else {
                    int appointmentID = Integer.parseInt(apptTF.getText());
                    String title = titleTF.getText();
                    String description = descriptionTF.getText();
                    String location = locationTF.getText();
                    String type = typeTF.getText();
                    LocalDateTime start = utcAdjustedTime;
                    LocalDateTime end = utcAdjustedTimeEnd;
                    System.out.println(dateCreatedTF.getText());
                    LocalDateTime createDate = LocalDateTime.parse(dateCreatedTF.getText(), format);
                    ZonedDateTime cd = ZonedDateTime.of(createDate, ZoneId.of("UTC"));
                    String createdBy = createdByTF.getText();
                    LocalDateTime lastUpdatedTime = LocalDateTime.parse(lastUpdatedTF.getText(), format);
                    ZonedDateTime lut = ZonedDateTime.of(lastUpdatedTime, ZoneId.of("UTC"));
                    String lastUpdatedBy = lastUpdatedByTF.getText();
                    int custid = Integer.parseInt(customerIDTF.getText());
                    int userid = Integer.parseInt(userIDTF.getText());
                    int contactid = Integer.parseInt(contactIDTF.getText());
                    //Adding appointment to the DB
                    String insertStatementAppointments = "INSERT INTO appointments (Appointment_ID, Title, Description," +
                            " Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By," +
                            " Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    preparedStatement = DBConnection.getConnection().prepareStatement(insertStatementAppointments);
                    preparedStatement.setInt(1,appointmentID);
                    preparedStatement.setString(2, title);
                    preparedStatement.setString(3, description);
                    preparedStatement.setString(4, location);
                    preparedStatement.setString(5, type);
                    preparedStatement.setTimestamp(6, Timestamp.valueOf(start));
                    preparedStatement.setTimestamp(7, Timestamp.valueOf(end));
                    preparedStatement.setTimestamp(8, Timestamp.valueOf(cd.toLocalDateTime()));
                    preparedStatement.setString(9, createdBy);
                    preparedStatement.setTimestamp(10, Timestamp.valueOf(lut.toLocalDateTime()));
                    preparedStatement.setString(11, lastUpdatedBy);
                    preparedStatement.setInt(12,custid);
                    preparedStatement.setInt(13,contactid);
                    preparedStatement.setInt(14,userid);
                    preparedStatement.execute();
                    System.out.println("PS executed");
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1250, 400);
                    Stage stage = new Stage();
                    stage.setTitle("Main Appointments");
                    stage.setScene(scene);
                    stage.show();
                    ((Stage) cancelButton.getScene().getWindow()).close();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onCancelButtonClick(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you do not want to save this appointment?");
        alert.setContentText("Please select 'OK' to cancel. Thank you.");
        alert.showAndWait();

        if(alert.getResult().getButtonData().isCancelButton()) {
            alert.close();
        }
        else{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1250, 400);
            Stage stage = new Stage();
            stage.setTitle("Current Appointments");
            stage.setScene(scene);
            stage.show();
            ((Stage) cancelButton.getScene().getWindow()).close();
        }
    }
    @FXML
    public void onCustomerNameSelected(ActionEvent actionEvent){
        if(customerListDD.isFocused()) {
            String customerName = customerListDD.getValue().toString();
            String custNameTemp;
            for (Customers customer : customerList) {
                custNameTemp = customer.getCustomerName();
                if (custNameTemp == customerName) {
                    customerIDTF.setText(String.valueOf(customer.getCustomerID()));
                    }
            }
        }
    }
    @FXML
    public void onContactNameSelected(ActionEvent actionEvent){
        if(contactListCB.isFocused()){
            String contactName = contactListCB.getValue().toString();
            String contactNameTemp;
            for(Contacts contact : contactsList){
                contactNameTemp = contact.getContact_Name();
                if (contactNameTemp == contactName){
                    contactIDTF.setText(String.valueOf(contact.getConID()));
                }
            }
        }
    }
    @FXML
    public int newAppointmentID(List appointmentIDs){
        int newAppointmentID;
        int possibleID = 1;
        while (appointmentIDs.contains(possibleID)){
            possibleID += 1;
        }
        newAppointmentID = possibleID;
        return newAppointmentID;
    }
    @FXML
    public String getTime(){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        return timeStamp;
    }
    //Used to Populate certain fields like customer name, customer ID when adding to a customer from the customers record
    @FXML
    public void populateTextFields(Customers customerInfo) {
        Customers customerInformation = customerInfo;
        for(Customers customer : customerList){
            if(customer.getCustomerName().equals(customerInformation.getCustomerName())){
                customerListDD.getSelectionModel().select(customer.getCustomerName());
                customerIDTF.setText(String.valueOf(customer.getCustomerID()));
            }
        }
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
            String formattedStart = startAppointment.format(format);
            String formattedEnd = endAppointment.format(format);

            //All time zone IDs local, UTC, and EST
            ZoneId local = ZoneId.of(ZoneId.systemDefault().getId());
            ZoneId EST = ZoneId.of("America/New_York");
            ZoneId UTC = ZoneId.of("UTC");

            ZonedDateTime localStartAppointment = startAppointment.atZone(local);
            ZonedDateTime utcStartAppointment = startAppointment.atZone(UTC);
            ZonedDateTime estStartAppointment = startAppointment.atZone(EST);

            ZonedDateTime localeEndAppointment = endAppointment.atZone(local);
            ZonedDateTime utcEndAppointment = endAppointment.atZone(UTC);
            ZonedDateTime estEndAppointment = endAppointment.atZone(EST);

            ZoneOffset localOffset = localStartAppointment.getOffset();
            ZoneOffset utcOffset = utcStartAppointment.getOffset();
            ZoneOffset estOffset = estStartAppointment.getOffset();

            ZoneOffset localOffsetEnd = localeEndAppointment.getOffset();
            ZoneOffset utcOffsetEnd = utcEndAppointment.getOffset();
            ZoneOffset estOffsetEnd = estEndAppointment.getOffset();

            int secOffsetlocal = localOffset.getTotalSeconds();
            int secOffsetUTC = utcOffset.getTotalSeconds();
            int secOffset = estOffset.getTotalSeconds();

            int secOffsetlocalEnd = localOffsetEnd.getTotalSeconds();
            int secOffsetUTCEnd = utcOffsetEnd.getTotalSeconds();
            int secOffsetEnd = estOffsetEnd.getTotalSeconds();

            LocalDateTime localAdjustedTime = startAppointment.plusSeconds(secOffsetlocal);
            LocalDateTime utcAdjustedTime = startAppointment.plusSeconds(secOffsetUTC);
            LocalDateTime estAdjustedTime = startAppointment.plusSeconds(secOffset);


            LocalDateTime localAdjustedTimeEnd = endAppointment.plusSeconds(secOffsetlocalEnd);
            LocalDateTime utcAdjustedTimeEnd = endAppointment.plusSeconds(secOffsetUTCEnd);
            LocalDateTime estAdjustedTimeEnd = endAppointment.plusSeconds(secOffsetEnd);

            String localTimeStart = localAdjustedTime.format(format);
            String utcTimeStart = utcAdjustedTime.format(format);
            String estTimeStart = estAdjustedTime.format(format);

            String localTimeEnd = localAdjustedTimeEnd.format(format);
            String utcTimeEnd = utcAdjustedTimeEnd.format(format);
            String estTimeEnd = estAdjustedTimeEnd.format(format);
            if (dateDP.getValue() == null || hourOfAppointmentCB.getValue() == null || endHourOfAppointmentCB1.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The date, start time, or end time have not been selected.");
                alert.setContentText("Please choose a date, start, or end time to continue.");
                alert.showAndWait();
            }
            if (localAdjustedTime.isAfter(localAdjustedTimeEnd) || localAdjustedTime.isEqual(localAdjustedTimeEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The start time is after the end time or is the same time.");
                alert.setContentText("Please choose a start before the end time to continue.");
                alert.showAndWait();
            } else if (localAdjustedTime.getDayOfWeek().toString() == "SATURDAY" || localAdjustedTime.getDayOfWeek().toString() == "SUNDAY") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The appointment falls on a weekend.");
                alert.setContentText("Please choose a day between Monday and Friday.");
                alert.showAndWait();
            } else if (estAdjustedTime.getHour() < 8 || estAdjustedTime.getHour() > 21 || estAdjustedTimeEnd.getHour() > 22 || estAdjustedTimeEnd.getHour() < 9) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The appointment falls outside of the available working hours of 08:00 to 22:00 EST (8AM to 10PM EST).");
                alert.setContentText("Please choose a time between the correct working hours.");
                alert.showAndWait();
            } else {
                appointmentStart.setText(localTimeStart);
                appointmentEnd.setText(localTimeEnd);
                estStartTF.setText(estTimeStart);
                estEndTF.setText(estTimeEnd);
            }
        }
    }
}
