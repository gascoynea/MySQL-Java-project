package Model;

import java.sql.Timestamp;

/**
 * USed to create a Division object
 */
public class FirstLevelDivisions {
    private int divID;
    private String division;
    private Timestamp create_Date;
    private String created_By;
    private Timestamp last_Updated;
    private String last_Updated_By;
    private int countryID;

    /**
     * @param divID
     * @param division
     * @param countryID
     */
    public FirstLevelDivisions(int divID, String division, int countryID) {
        this.divID = divID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * @return
     */
    public int getDivID() {
        return divID;
    }

    /**
     * @param divID
     */
    public void setDivID(int divID) {
        this.divID = divID;
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

    /**
     * @return
     */
    public Timestamp getCreate_Date() {
        return create_Date;
    }

    /**
     * @param create_Date
     */
    public void setCreate_Date(Timestamp create_Date) {
        this.create_Date = create_Date;
    }

    /**
     * @return
     */
    public String getCreated_By() {
        return created_By;
    }

    /**
     * @param created_By
     */
    public void setCreated_By(String created_By) {
        this.created_By = created_By;
    }

    /**
     * @return
     */
    public Timestamp getLast_Updated() {
        return last_Updated;
    }

    /**
     * @param last_Updated
     */
    public void setLast_Updated(Timestamp last_Updated) {
        this.last_Updated = last_Updated;
    }

    /**
     * @return
     */
    public String getLast_Updated_By() {
        return last_Updated_By;
    }

    /**
     * @param last_Updated_By
     */
    public void setLast_Updated_By(String last_Updated_By) {
        this.last_Updated_By = last_Updated_By;
    }

    /**
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
