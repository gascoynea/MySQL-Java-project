package BDAccess;

import Database.DBConnection;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Used to access Country data form the data base.
 */
public class DBACountries {
    /**
     * Generates an Observable list of Countrys.
     * @return
     */
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> countryList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Country_ID, Country from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int cID = rs.getInt("Country_ID");
                String cName = rs.getString("Country");
                Countries cSet = new Countries(cID, cName);
                countryList.add(cSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryList;
    }

    /**
     * Used to check date conversions
     */
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

    /**
     * Generates an Observable list of country names.
     * @return
     */
    public static ObservableList<String> getCountryNames(){
        ObservableList<Countries> countries = DBACountries.getAllCountries();
        ObservableList<String> countryNames = FXCollections.observableArrayList();
//        ArrayList<String> countryNames = new ArrayList<>();
        for (Countries country : countries){
            countryNames.add(country.getName());
//            System.out.println(country.getName() + " " + country.getId());
        }
        return countryNames;
    }
}
