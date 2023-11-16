package Model;

import java.sql.Timestamp;

public class FirstLevelDivisions {
    private int divID;
    private String division;
    private Timestamp create_Date;
    private String created_By;
    private Timestamp last_Updated;
    private String last_Updated_By;
    private int countryID;
    public FirstLevelDivisions(int divID, String division, int countryID) {
//        Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int countryID
        this.divID = divID;
        this.division = division;
//        this.create_Date = createDate;
//        this.created_By = createdBy;
//        this.last_Updated = lastUpdate;
//        this.last_Updated_By = lastUpdatedBy;
        this.countryID = countryID;
    }

    public int getDivID() {
        return divID;
    }

    public void setDivID(int divID) {
        this.divID = divID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Timestamp getCreate_Date() {
        return create_Date;
    }

    public void setCreate_Date(Timestamp create_Date) {
        this.create_Date = create_Date;
    }

    public String getCreated_By() {
        return created_By;
    }

    public void setCreated_By(String created_By) {
        this.created_By = created_By;
    }

    public Timestamp getLast_Updated() {
        return last_Updated;
    }

    public void setLast_Updated(Timestamp last_Updated) {
        this.last_Updated = last_Updated;
    }

    public String getLast_Updated_By() {
        return last_Updated_By;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        this.last_Updated_By = last_Updated_By;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
