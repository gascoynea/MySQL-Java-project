package Model;

import java.sql.Timestamp;

public class Customers {
    private int customerID;
    private String customerName;
    private String address;
    private String postCode;
    private String phone;
    private Timestamp createDate;
    private String createdBy;

    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;
    private String division;


    public Customers(int inCustomerId, String inCustomerName, String inAddress, String inPostalCode, String inPhone, Timestamp inCreateDate, String inCreatedBy, Timestamp inLastUpdate, String inLastUpdatedBy, int inDivisionId, String inDivision) {
        this.customerID = inCustomerId;
        this.customerName = inCustomerName;
        this.address = inAddress;
        this.postCode = inPostalCode;
        this.phone = inPhone;
        this.createDate = inCreateDate;
        this.createdBy = inCreatedBy;
        this.lastUpdate = inLastUpdate;
        this.lastUpdatedBy = inLastUpdatedBy;
        this.divisionID = inDivisionId;
        this.division = inDivision;
    }
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

}
