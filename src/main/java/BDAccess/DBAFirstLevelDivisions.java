package BDAccess;

import Database.DBConnection;
import Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 * Used to access Divisions data form the data base.
 */
public class DBAFirstLevelDivisions {
    /**
     * Generates an Observable list of First Level Divisions.
     * @return
     */
    public static ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions() {
        ObservableList<FirstLevelDivisions> firstLevelDivisionsList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Division_ID, Division, Country_ID from first_level_divisions";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int divID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                FirstLevelDivisions divSet = new FirstLevelDivisions(divID, division, countryID);
                firstLevelDivisionsList.add(divSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return firstLevelDivisionsList;
    }

    /**
     * Generates an Observable list of First Level Divisions names.
     * @return
     */
    public static ObservableList<String> getDivisionNames(){
        ObservableList<FirstLevelDivisions> divisionsList = DBAFirstLevelDivisions.getAllFirstLevelDivisions();
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        for (FirstLevelDivisions division : divisionsList){
            divisionNames.add(division.getDivision());
//            System.out.println(division.getDivision() + division.getDivID() + division.getCountryID());
        }
        return divisionNames;
    }
}
