package Model;

import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Customers {
    private int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;
    private int Division_ID;

    private String Division_Name;
    private String Country;
    private int Country_ID;

    /**
     * No-parameter constructor
     */
    public Customers() {
    }

    /**
     * Full constructor taking all arguements for each of the columns in the table
     * @param customer_ID
     * @param customer_Name
     * @param address
     * @param postal_Code
     * @param phone
     * @param create_Date
     * @param created_By
     * @param last_Update
     * @param last_Updated_By
     * @param division_ID
     */
    public Customers(int customer_ID, String customer_Name, String address, String postal_Code, String phone, LocalDateTime create_Date, String created_By, LocalDateTime last_Update, String last_Updated_By, int division_ID) {
        Customer_ID = customer_ID;
        Customer_Name = customer_Name;
        Address = address;
        Postal_Code = postal_Code;
        Phone = phone;
        Create_Date = create_Date;
        Created_By = created_By;
        Last_Update = last_Update;
        Last_Updated_By = last_Updated_By;
        Division_ID = division_ID;
    }

    /**
     * Returns the customer ID
     * @return
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }

    /**
     * Sets the customer ID
     * @param customer_ID
     */
    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    /**
     * Returns the customer name
     * @return
     */
    public String getCustomer_Name() {
        return Customer_Name;
    }

    /**
     * Sets the customer name
     * @param customer_Name
     */
    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    /**
     * Returns the address
     * @return
     */
    public String getAddress() {
        return Address;
    }

    /**
     * Sets the address
     * @param address
     */
    public void setAddress(String address) {
        Address = address;
    }

    /**
     * Returns the postal code
     * @return
     */
    public String getPostal_Code() {
        return Postal_Code;
    }

    /**
     * Sets the postal code
     * @param postal_Code
     */
    public void setPostal_Code(String postal_Code) {
        Postal_Code = postal_Code;
    }

    /**
     * Returns the phone number
     * @return
     */
    public String getPhone() {
        return Phone;
    }

    /**
     * Sets the phone number
     * @param phone
     */
    public void setPhone(String phone) {
        Phone = phone;
    }

    /**
     * Returns the LocalDateTime of when customer was created
     * @return
     */
    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    /**
     * Sets the LocalDateTime of when the customer was created
     * @param create_Date
     */
    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    /**
     * Returns who created the customer
     * @return
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * Sets who created the customer
     * @param created_By
     */
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    /**
     * Returns the LocalDateTime of when the last update was
     * @return
     */
    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    /**
     * Sets the LocalDateTime of when the last update was
     * @param last_Update
     */
    public void setLast_Update(LocalDateTime last_Update) {
        Last_Update = last_Update;
    }

    /**
     * Returns who last updated the customer
     * @return
     */
    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    /**
     * Sets who last updated the customer
     * @param last_Updated_By
     */
    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    /**
     * Returns the division ID
     * @return
     */
    public int getDivision_ID() {
        return Division_ID;
    }

    /**
     * Sets the division ID
     * @param division_ID
     */
    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

    /**
     * Returns the division name
     * @return
     */
    public String getDivision_Name() {
        return Division_Name;
    }

    /**
     * Sets the division name
     * @param division_Name
     */
    public void setDivision_Name(String division_Name) {
        Division_Name = division_Name;
    }

    /**
     * Returns country
     * @return
     */
    public String getCountry() {
        return Country;
    }

    /**
     * Sets country
     * @param country
     */
    public void setCountry(String country) {
        Country = country;
    }

    /**
     * Returns country ID
     * @return
     */
    public int getCountry_ID() {
        return Country_ID;
    }

    /**
     * Sets country ID
     * @param country_ID
     */
    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }

    /**
     * Method to display the customer ID and name, instead of objects location in memory.
     * @return
     */
    @Override
    public String toString(){
        return (getCustomer_ID() + " - " + getCustomer_Name());
    }
}
