package BDAccess;

import Database.DBConnection;
import Model.Countries;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBAUsers {
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> usersList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                Users userSet = new Users(userID, userName, userPassword, createDate, createdBy, lastUpdate, lastUpdatedBy);
                usersList.add(userSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersList;
    }
    public static void getUserNames(){
        ObservableList<Users> users = DBAUsers.getAllUsers();
        for (Users user : users){
            System.out.println(user.getUserName());
        }
    }
}

