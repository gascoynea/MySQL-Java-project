package com.example.demo;
import BDAccess.DBAAppointments;
import Model.Appointments;
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
import java.util.ResourceBundle;

public class MainAppointmetController implements Initializable {
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
    ObservableList<Appointments> apptList = null;
    public Appointments selectedAppointment = null;
    private Parent root;
    private Stage stage;
    private Scene scene;

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
    }

    public void onCustomerRecordsClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CustomerList.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 365);
        Stage stage = new Stage();
        stage.setTitle("Customers List");
        stage.setScene(scene);
        stage.show();
        ((Stage) apptsCustRecordsButton.getScene().getWindow()).close();
    }

    public void onAddClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Appoinment Information with DropMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 400);
        Stage stage = new Stage();
        stage.setTitle("Add Appointment to known user");
        stage.setScene(scene);
        stage.show();
        ((Stage) addApptButton.getScene().getWindow()).close();
    }

    public void onEditClick(ActionEvent actionEvent) throws IOException {
        try{
            if(tableView.getSelectionModel().getSelectedItem() != null) {
                selectedAppointment = tableView.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Appoinment Information.fxml"));
                root = loader.load();
                AppointmentInformationController scene2controller = loader.getController();
                scene2controller.populateTextFields(selectedAppointment);
                stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
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
}
