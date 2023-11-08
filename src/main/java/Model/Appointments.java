package Model;


import java.sql.Timestamp;

public class Appointments {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createDate;
    private String created_By;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customer_id;
    private int userId;
    private int contactId;
    public Appointments(int appointmentID, String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp createDate, String created_By, Timestamp lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.created_By = created_By;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customer_id = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    public int getaID() {
        return appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public Timestamp getStart() {
        return start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public String getCreated_By() {
        return created_By;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public int getUserId() {
        return userId;
    }

    public int getContactId() {
        return contactId;
    }
}
