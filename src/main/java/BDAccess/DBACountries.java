package BDAccess;

import Database.DBConnection;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBACountries {
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> countryList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int cID = rs.getInt("Country_ID");
                String cName = rs.getString("Country");
                Timestamp create_Date = rs.getTimestamp("Create_Date");
                String created_by = rs.getString("Created_By");
                Timestamp last_Update = rs.getTimestamp("Last_Update");
                String last_Updated_By = rs.getString("Last_Updated_By");

                Countries cSet = new Countries(cID, cName, create_Date, created_by, last_Update, last_Updated_By);
                countryList.add(cSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countryList;
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
    public static void getCountryName(){
        ObservableList<Countries> countries = DBACountries.getAllCountries();
        for (Countries country : countries){
            System.out.println(country.getName());
        }
    }
}
