package BDAccess;

import Database.DBConnection;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBACustomers {
    public static ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> customersList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code," +
                    " customers.Phone, customers.Create_Date, customers.Created_By, customers.Last_Update, customers.Last_Updated_By," +
                    " customers.Division_ID, first_level_divisions.Division" +
                    " from customers INNER JOIN  first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customer_ID = rs.getInt("Customer_ID");
                String customer_Name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal_Code = rs.getString("Postal_Code");
                String division = rs.getString("Division");
                String phone = rs.getString("Phone");
                Timestamp create_Date = rs.getTimestamp("Create_Date");
                String created_By = rs.getString("Created_By");
                Timestamp last_Update = rs.getTimestamp("Last_Update");
                String last_Updated_By = rs.getString("Last_Updated_By");
                int division_ID = rs.getInt("Division_ID");

                Customers customerSet = new Customers(customer_ID, customer_Name, address, postal_Code, phone,
                        create_Date, created_By, last_Update, last_Updated_By, division_ID, division);

                customersList.add(customerSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customersList;
    }
    public static void getCustomerNames(){
        ObservableList<Customers> customers = DBACustomers.getAllCustomers();
        for (Customers customer : customers){
            System.out.println(customer.getCustomerName() + customer.getDivision());

        }
    }
}
