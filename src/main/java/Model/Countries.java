package Model;

import java.sql.Timestamp;

public class Countries {

    private int id;
    private String name;
    private Timestamp create_Date;
    private String created_By;
    private Timestamp last_Updated;
    private String last_Updated_By;

    public Countries(int id, String name){
//        Timestamp create_Date, String created_By, Timestamp last_Update, String last_Updated_By
        this.id = id;
        this.name = name;
//        this.create_Date = create_Date;
//        this.created_By = created_By;
//        this.last_Updated = last_Update;
//        this.last_Updated_By = last_Updated_By;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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
}
