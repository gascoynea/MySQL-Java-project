package Model;

import java.sql.Timestamp;

/**
 * USed for creating Customer Objects
 */
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


    /**
     * @param inCustomerId
     * @param inCustomerName
     * @param inAddress
     * @param inPostalCode
     * @param inPhone
     * @param inCreateDate
     * @param inCreatedBy
     * @param inLastUpdate
     * @param inLastUpdatedBy
     * @param inDivisionId
     * @param inDivision
     */
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

    /**
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * @param postCode
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * @return
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

}
