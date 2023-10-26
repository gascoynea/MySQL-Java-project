package BDAccess;

import Database.DBConnection;
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
                Users userSet = new Users(userID, userName, userPassword);
                usersList.add(userSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersList;
    }

    public static void checkDateConversion(){
        System.out.println("CREATE DATE TEST");
        String sql = "select Create_Date from countries";
        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}

