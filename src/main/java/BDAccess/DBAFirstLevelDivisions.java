package BDAccess;

import Database.DBConnection;
import Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class DBAFirstLevelDivisions {
    public static ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions() {
        ObservableList<FirstLevelDivisions> firstLevelDivisionsList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Division_ID, Division, Country_ID from first_level_divisions";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int divID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
//                Timestamp create_Date = rs.getTimestamp("Create_Date");
//                String created_by = rs.getString("Created_By");
//                Timestamp last_Update = rs.getTimestamp("Last_Update");
//                String last_Updated_By = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("Country_ID");

                FirstLevelDivisions divSet = new FirstLevelDivisions(divID, division, countryID);
//                create_Date, created_by, last_Update, last_Updated_By, countryID
                firstLevelDivisionsList.add(divSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return firstLevelDivisionsList;
    }
    public static ObservableList<String> getDivisionName(){
        ObservableList<FirstLevelDivisions> divisionsList = DBAFirstLevelDivisions.getAllFirstLevelDivisions();
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        for (FirstLevelDivisions division : divisionsList){
            divisionNames.add(division.getDivision());
//            System.out.println(division.getDivision() + division.getDivID() + division.getCountryID());
        }
        return divisionNames;
    }
}
