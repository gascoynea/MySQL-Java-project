package Model;

/**
 * Used to create Contact objects
 */
public class Contacts {
    private int conID;
    private String contact_Name;
    private String email;


    /**
     * @param contactID
     * @param contactName
     * @param email
     */
    public Contacts(int contactID, String contactName, String email) {
        this.conID = contactID;
        this.contact_Name = contactName;
        this.email = email;
    }

    /**
     * @return
     */
    public int getConID() {
        return conID;
    }

    /**
     * @param conID
     */
    public void setConID(int conID) {
        this.conID = conID;
    }

    /**
     * @return
     */
    public String getContact_Name() {
        return contact_Name;
    }

    /**
     * @param contact_Name
     */
    public void setContact_Name(String contact_Name) {
        this.contact_Name = contact_Name;
    }

    /**
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
