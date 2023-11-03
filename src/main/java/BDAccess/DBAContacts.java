package BDAccess;

import Database.DBConnection;
import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBAContacts {
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from contacts";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contacts cSet = new Contacts(contactID, contactName, email);
                contactsList.add(cSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contactsList;
    }
    public static void getContactName() {
        ObservableList<Contacts> contacts = DBAContacts.getContacts();
        for (Contacts contact : contacts) {
            System.out.println(contact.getContact_Name());
        }
    }
    public static ObservableList<Contacts> getContacts(){
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
        try
        {
            String sql = "SELECT * from contacts";


            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();
            if(resultSet != null) {
                while (resultSet.next()) {
                    int contactID = resultSet.getInt("Contact_ID");
                    String contactName = resultSet.getString("Contact_Name");
                    String email = resultSet.getString("Email");

                    Contacts cSet = new Contacts(contactID, contactName, email);
                    contactsList.add(cSet);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return contactsList;
    }
}
