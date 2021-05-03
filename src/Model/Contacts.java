package Model;

public class Contacts {
    private int Contact_ID;
    private String Contact_Name;
    private String Email;

    /**
     * Returns the contact ID
     * @return
     */
    public int getContact_ID() {
        return Contact_ID;
    }

    /**
     * Sets the contact ID
     * @param contact_ID
     */
    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

    /**
     * Returns the contact name
     * @return
     */
    public String getContact_Name() {
        return Contact_Name;
    }

    /**
     * Sets the contact name
     * @param contact_Name
     */
    public void setContact_Name(String contact_Name) {
        Contact_Name = contact_Name;
    }

    /**
     * Returns the email
     * @return
     */
    public String getEmail() {
        return Email;
    }

    /**
     * Sets the email
     * @param email
     */
    public void setEmail(String email) {
        Email = email;
    }

    /**
     * Method to show the contact Id and contact name instead of just the object memory location.
     * @return
     */
    @Override
    public String toString(){
        return (getContact_ID() + " - " + getContact_Name());
    }
}
