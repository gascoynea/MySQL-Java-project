package com.example.demo;
import BDAccess.DBAAppointments;
import Database.DBConnection;
import Model.Appointments;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

/**
 * Main appointment Controller used to manipulate the Main Appointment FXML
 */
public class MainAppointmetController implements Initializable {
    @FXML
    public Button appointmentSearchButton;
    public Button monthTypeButton;
    @FXML
    private Button addApptButton;

    @FXML
    public TableColumn<Appointments, Integer> apptIDCol;

    @FXML
    private Label apptLabel;

    @FXML
    private Button apptsCustRecordsButton;

    @FXML
    public TableColumn<Appointments, Integer> contactCol;

    @FXML
    public TableColumn<Appointments, String> createdByCol;

    @FXML
    public TableColumn<Appointments, Integer> customerIDCol;

    @FXML
    public TableColumn<Appointments, Timestamp> dateCreatedCol;

    @FXML
    public TableColumn<Appointments, String> descriptionCol;

    @FXML
    private Button editApptButton;

    @FXML
    public TableColumn<Appointments, Timestamp> endDandTCol;

    @FXML
    public TableColumn<Appointments, Timestamp> lastUpdateCol;

    @FXML
    public TableColumn<Appointments, String> lastUpdatedByCol;

    @FXML
    public TableColumn<Appointments, String> locationCol;

    @FXML
    private Button removeApptButton;

    @FXML
    public TableColumn<Appointments, Timestamp> startDandTCol;

    @FXML
    public TableView<Appointments> tableView;

    @FXML
    public TableColumn<Appointments, String> titleCol;

    @FXML
    public TableColumn<Appointments, String> typeCol;

    @FXML
    public TableColumn<Appointments, Integer> userIDCol;
    ObservableList<Appointments> apptList = FXCollections.observableArrayList();
    public Appointments selectedAppointment = null;
    private Parent root;
    private Stage stage;
    private Scene scene;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptList = DBAAppointments.getAllAppointments();
        apptIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("type"));
        startDandTCol.setCellValueFactory(new PropertyValueFactory<Appointments, Timestamp>("start"));
        endDandTCol.setCellValueFactory(new PropertyValueFactory<Appointments, Timestamp>("end"));
        dateCreatedCol.setCellValueFactory(new PropertyValueFactory<Appointments, Timestamp>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<Appointments, Timestamp>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<Appointments, String>("lastUpdatedBy"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("customerId"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("userId"));
        contactCol.setCellValueFactory(new PropertyValueFactory<Appointments, Integer>("contactId"));
        tableView.setItems(apptList);

        boolean apptIn15 = false;
        LocalDateTime nowMinus15 = LocalDateTime.now().minusMinutes(15);
        LocalDateTime nowPlus15 = LocalDateTime.now().plusMinutes(15);

        LocalDateTime apptStart;
        LocalDateTime appointmentTime = null;
        int appointmentID = 0;
        for(Appointments appointment : apptList){
            apptStart = appointment.getStart().toLocalDateTime();
            if(apptStart.isAfter(nowMinus15) && apptStart.isBefore(nowPlus15)){
                appointmentID = appointment.getAppointmentID();
                appointmentTime = apptStart;
                apptIn15 = true;
            }

        }
        if(apptIn15){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("There is an appoinment within 15 minutes of Login!");
            alert.setContentText("Please review Appointment info!\nAppointment ID: " + appointmentID + "\nStart Time: " + appointmentTime);
            alert.showAndWait();

        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No upcoming appointments in 15 minutes.");
            alert.setContentText("Have a good day!");
            alert.showAndWait();
        }
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void onCustomerRecordsClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main Customers.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 365);
        Stage stage = new Stage();
        stage.setTitle("Customers List");
        stage.setScene(scene);
        stage.show();
        ((Stage) apptsCustRecordsButton.getScene().getWindow()).close();
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void onAddClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Add Appointment.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 650);
        Stage stage = new Stage();
        stage.setTitle("Add Appointment to a known user");
        stage.setScene(scene);
        stage.show();
        ((Stage) addApptButton.getScene().getWindow()).close();
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void onEditClick(ActionEvent actionEvent) throws IOException {
        try{
            if(tableView.getSelectionModel().getSelectedItem() != null) {
                selectedAppointment = tableView.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Edit Appointment.fxml"));
                root = loader.load();
                EditAppointmentController scene2controller = loader.getController();
                scene2controller.populateTextFields(selectedAppointment);
                stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("Edit known appointment");
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
     * @param actionEvent
     * @throws SQLException
     */
    @FXML
    public void onRemoveButtonClick(ActionEvent actionEvent) throws SQLException {
        if(tableView.getSelectionModel().selectedItemProperty().get() != null) {
            Connection connection = DBConnection.openConnection();
            Appointments appointmentinfo = tableView.getSelectionModel().selectedItemProperty().get();
            int apptIDDel = appointmentinfo.getAppointmentID();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to remove this appointment with ID: " + apptIDDel + ", Title of: " + appointmentinfo.getTitle() + "?");
            alert.setContentText("Please select 'OK' to remove. Thank you.");
            alert.showAndWait();
            if(alert.getResult().getButtonData().isDefaultButton()){
                for(Appointments appointment : apptList){
                    if(appointment.getAppointmentID() == apptIDDel){
                        String delAppt = "DELETE FROM appointments WHERE Appointment_ID=?";
                        PreparedStatement psDelete = connection.prepareStatement(delAppt);
                        psDelete.setInt(1, apptIDDel);
                        psDelete.executeUpdate();
                        psDelete.close();
                        tableView.setItems(DBAAppointments.getAllAppointments());
                    }
                }
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("An appointment was cancelled!");
                alert.setContentText("Appointment with an appointment ID of: " + apptIDDel + " With type: " + appointmentinfo.getType());
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No appointment selected.");
            alert.setContentText("Please select an appointment to remove. Thank you.");
            alert.showAndWait();
        }
    }

    /**
     * Opens up Appointment Search FXML when appointment search button pressed.
     * @param actionEvent
     * @throws IOException
     */
    public void onAppointmentSearchButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Appoinment Search.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1065, 365);
        Stage stage = new Stage();
        stage.setTitle("Appointment Search");
        stage.setScene(scene);
        stage.show();
        ((Stage) appointmentSearchButton.getScene().getWindow()).close();
    }

    /**
     * This method activates when the reports button is pressed.
     * Opens up Customer Appointment Report.
     * @param actionEvent
     * @throws IOException
     */
    public void oonMonthTypeButtonSelected(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Customer Appointment Report.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        Stage stage = new Stage();
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
        ((Stage) monthTypeButton.getScene().getWindow()).close();
    }
}
