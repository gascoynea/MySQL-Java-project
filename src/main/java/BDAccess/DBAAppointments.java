package BDAccess;

import Database.DBConnection;
import Model.Appointments;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBAAppointments {
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int aID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp create_date = rs.getTimestamp("Create_Date");
                String created_by = rs.getString("Created_By");
                Timestamp last_Update = rs.getTimestamp("Last_Update");
                String last_Updated_By = rs.getString("Last_Updated_By");
                int customer_ID = rs.getInt("Customer_ID");
                int user_ID = rs.getInt("User_ID");
                int contact_ID = rs.getInt("Contact_ID");
                Appointments aSet = new Appointments(aID, title, description, location, type, start, end, create_date, created_by, last_Update, last_Updated_By, customer_ID, user_ID, contact_ID);
                appointmentsList.add(aSet);
                //System.out.println("created by:" + aID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(appointmentsList.get(0).getAppointmentID());
        return appointmentsList;
    }
    public static void getAppointmentID() {
        ObservableList<Appointments> appointments = DBAAppointments.getAllAppointments();
        for (Appointments appts : appointments) {
//            System.out.println(appts.getaID() + " " + appts.getTitle());
        }
    }
}
