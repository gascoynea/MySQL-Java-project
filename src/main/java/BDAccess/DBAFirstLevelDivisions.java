package BDAccess;

import Database.DBConnection;
import Model.Countries;
import Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBAFirstLevelDivisions {
    public static ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions() {
        ObservableList<FirstLevelDivisions> firstLevelDivisionsList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from first_level_divisions";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int divID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                Timestamp create_Date = rs.getTimestamp("Create_Date");
                String created_by = rs.getString("Created_By");
                Timestamp last_Update = rs.getTimestamp("Last_Update");
                String last_Updated_By = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("Country_ID");

                FirstLevelDivisions divSet = new FirstLevelDivisions(divID, division, create_Date, created_by, last_Update, last_Updated_By, countryID);
                firstLevelDivisionsList.add(divSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return firstLevelDivisionsList;
    }
    public static void getDivisionName(){
        ObservableList<FirstLevelDivisions> divisionsList = DBAFirstLevelDivisions.getAllFirstLevelDivisions();
        for (FirstLevelDivisions division : divisionsList){
            System.out.println(division.getDivision());
        }
    }
}
