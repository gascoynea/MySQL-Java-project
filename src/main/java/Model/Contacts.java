package Model;

public class Contacts {
    private int conID;
    private String contact_Name;
    private String email;


    public Contacts(int contactID, String contactName, String email) {
        this.conID = contactID;
        this.contact_Name = contactName;
        this.email = email;
    }

    public int getConID() {
        return conID;
    }

    public void setConID(int conID) {
        this.conID = conID;
    }

    public String getContact_Name() {
        return contact_Name;
    }

    public void setContact_Name(String contact_Name) {
        this.contact_Name = contact_Name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
