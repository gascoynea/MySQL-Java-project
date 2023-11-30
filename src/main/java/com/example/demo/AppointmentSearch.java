package com.example.demo;

import BDAccess.DBAAppointments;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * used to manipulate Appointment Search FXML.
 */
public class AppointmentSearch implements Initializable {
    @FXML
    public Button mainAppointmentButton;
    @FXML
    private TableColumn<Appointments, Integer> appointmentIDCol;

    @FXML
    private RadioButton biWeeklyRB;

    @FXML
    private TableColumn<Appointments, Integer> contactCol;

    @FXML
    private TableColumn<Appointments, Integer> customerIDCol;

    @FXML
    private TableColumn<Appointments, String> descriptionCol;

    @FXML
    private TableColumn<Appointments, Timestamp> endDTCol;

    @FXML
    private TableColumn<Appointments, String> locationCol;

    @FXML
    private RadioButton monthlyRB;

    @FXML
    private TableColumn<Appointments, Timestamp> startDTCol;

    @FXML
    private TableView<Appointments> tableView;

    @FXML
    private TableColumn<Appointments, String> titleCol;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    @FXML
    private TableColumn<Appointments, Integer> userIDCol;
    ObservableList<Appointments> apptList = DBAAppointments.getAllAppointments();

    /**
     * Sorts Tableview appointments by one week ahead and behind and populates the table view with the
     * appointments that falls within range.
     * @param event
     */
    @FXML
    void onBiWeeklyRBSelected(ActionEvent event) {
        if(biWeeklyRB.isSelected()){
            monthlyRB.setSelected(false);
        }
        ObservableList<Appointments> weekAppointmentList = FXCollections.observableArrayList();
        LocalDateTime weekFromNow = LocalDateTime.now().plusWeeks(1);
        LocalDateTime weekBeforeNow = LocalDateTime.now().minusWeeks(1);

        if(apptList == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There are no appointment scheduled.");
            alert.setContentText("Please add some appointments.");
            alert.showAndWait();
        }
        else{
            for(Appointments appointment : apptList){
                if(appointment.getEnd().after(Timestamp.valueOf(weekBeforeNow)) && appointment.getEnd().before(Timestamp.valueOf(weekFromNow))){
                    weekAppointmentList.add(appointment);
                }
            }
        }
        tableView.setItems(weekAppointmentList);
    }

    /**
     * Sorts Tableview appointments by one month ahead and behind and populates the table view with the
     * appointments that falls within range.
     * @param event
     */
    @FXML
    void onMonthlyRBSelected(ActionEvent event) {
        if(monthlyRB.isSelected()){
            biWeeklyRB.setSelected(false);
        }
        ObservableList<Appointments> monthAppointmentList = FXCollections.observableArrayList();
        LocalDateTime monthFromNow = LocalDateTime.now().plusMonths(1);
        LocalDateTime monthBeforeNow = LocalDateTime.now().minusMonths(1);

        if(apptList == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There are no appointment scheduled.");
            alert.setContentText("Please add some appointments.");
            alert.showAndWait();
        }
        else{
            for(Appointments appointment : apptList){
                if(appointment.getEnd().after(Timestamp.valueOf(monthBeforeNow)) && appointment.getEnd().before(Timestamp.valueOf(monthFromNow))){
                    monthAppointmentList.add(appointment);
                }
            }
        }
        tableView.setItems(monthAppointmentList);
    }

    /**
     * populates table view with appointment list before weekly or mothly search.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptList = DBAAppointments.getAllAppointments();
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
        startDTCol.setCellValueFactory(new PropertyValueFactory<Appointments, Timestamp>("start"));
        endDTCol.setCellValueFactory(new PropertyValueFactory<Appointments, Timestamp>("end"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("customerId"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("userId"));
        contactCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("contactId"));
        tableView.setItems(apptList);
    }

    /**
     * On main appointment button pressed.
     * Opens Main Appointments FXML
     * @param actionEvent
     * @throws IOException
     */
    public void mainAppointmentButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Appointments.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 400);
        Stage stage = new Stage();
        stage.setTitle("Current Appointments");
        stage.setScene(scene);
        stage.show();
        ((Stage) mainAppointmentButton.getScene().getWindow()).close();
    }
}
