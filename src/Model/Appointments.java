package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointments {
   private int Appointment_ID;
   private String Title;
   private String Description;
   private String Location;
   private String Type;
   private LocalDateTime Start;
   private LocalDateTime End;
   private LocalDateTime Create_Date;
   private String Created_By;
   private LocalDateTime Last_Update;
   private String Last_Updated_By;
   private int Customer_ID;
   private int User_ID;
   private int Contact_ID;

   private String Contact_Name;
   private String Customer_Name;
   private LocalDate Start_Date;
   private LocalTime Start_Time;
   private LocalDate End_Date;
   private LocalTime End_Time;
   private String month;
   private int count;
   private String Country;

    /**
     * No-argument constructor for basic class construction.
     */
    public Appointments() {
    }

    /**
     * Full object constructor that takes all required fields as parameters.All fields listed are those necessary to build
     * a full object for record insertion into the database.
     * @param appointment_ID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param create_Date
     * @param created_By
     * @param last_Update
     * @param last_Updated_By
     * @param customer_ID
     * @param user_ID
     * @param contact_ID
     */
    public Appointments(int appointment_ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime create_Date, String created_By, LocalDateTime last_Update, String last_Updated_By, int customer_ID, int user_ID, int contact_ID) {
        Appointment_ID = appointment_ID;
        Title = title;
        Description = description;
        Location = location;
        Type = type;
        Start = start;
        End = end;
        Create_Date = create_Date;
        Created_By = created_By;
        Last_Update = last_Update;
        Last_Updated_By = last_Updated_By;
        Customer_ID = customer_ID;
        User_ID = user_ID;
        Contact_ID = contact_ID;
    }

    /**
     * Returns the appointment Id
     * @return
     */
    public int getAppointment_ID() {
        return Appointment_ID;
    }

    /**
     * Sets the appointment Id
     * @param appointment_ID
     */
    public void setAppointment_ID(int appointment_ID) {
        Appointment_ID = appointment_ID;
    }

    /**
     * Returns the title
     * @return
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Sets the title
     * @param title
     */
    public void setTitle(String title) {
        Title = title;
    }

    /**
     * Returns the description
     * @return
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Sets the description
     * @param description
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * Returns the location
     * @return
     */
    public String getLocation() {
        return Location;
    }

    /**
     * Sets the location
     * @param location
     */
    public void setLocation(String location) {
        Location = location;
    }

    /**
     * Returns the type
     * @return
     */
    public String getType() {
        return Type;
    }

    /**
     * Sets the type
     * @param type
     */
    public void setType(String type) {
        Type = type;
    }

    /**
     * Returns the start LocalDateTime
     * @return
     */
    public LocalDateTime getStart() {
        return Start;
    }

    /**
     * Sets the start LocalDateTime
     * @param start
     */
    public void setStart(LocalDateTime start) {
        Start = start;
    }

    /**
     * Returns the end LocalDateTime
     * @return
     */
    public LocalDateTime getEnd() {
        return End;
    }

    /**
     * Sets the end LocalDateTime
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        End = end;
    }

    /**
     * Returns the created date LocalDateTime
     * @return
     */
    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    /**
     * Sets the created date LocalDateTime
     * @param create_Date
     */
    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    /**
     * Returns who created the appointment
     * @return
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * Sets who created the appointment
     * @param created_By
     */
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    /**
     * Returns the LocalDateTime for the last update
     * @return
     */
    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    /**
     * Sets the LocalDateTime for the last update
     * @param last_Update
     */
    public void setLast_Update(LocalDateTime last_Update) {
        Last_Update = last_Update;
    }

    /**
     * Returns who last updated the appointment
     * @return
     */
    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    /**
     * Sets who last updated the appointment
     * @param last_Updated_By
     */
    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    /**
     * Returns the Customer ID for the appointment
     * @return
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }

    /**
     * Sets the customer ID for the appointment
     * @param customer_ID
     */
    public void setCustomer_ID(int customer_ID) {
        Customer_ID = customer_ID;
    }

    /**
     * Returns the user ID for the appointment
     * @return
     */
    public int getUser_ID() {
        return User_ID;
    }

    /**
     * Sets the user ID for the appointment
     * @param user_ID
     */
    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    /**
     * Returns the contact ID for the appointment
     * @return
     */
    public int getContact_ID() {
        return Contact_ID;
    }

    /**
     * Sets the contact ID for the appointment
     * @param contact_ID
     */
    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }

    /**
     * Returns the contact name linked to the appointment
     * @return
     */
    public String getContact_Name() {
        return Contact_Name;
    }

    /**
     * Sets the contact name linked to the appointment
     * @param contact_Name
     */
    public void setContact_Name(String contact_Name) {
        Contact_Name = contact_Name;
    }

    /**
     * Returns the start date
     * @return
     */
    public LocalDate getStart_Date() {
        return Start_Date;
    }

    /**
     * Sets the start date
     * @param start_Date
     */
    public void setStart_Date(LocalDate start_Date) {
        Start_Date = start_Date;
    }

    /**
     * Returns the start time
     * @return
     */
    public LocalTime getStart_Time() {
        return Start_Time;
    }

    /**
     * Sets the start time
     * @param start_Time
     */
    public void setStart_Time(LocalTime start_Time) {
        Start_Time = start_Time;
    }

    /**
     * Returns the end date
     * @return
     */
    public LocalDate getEnd_Date() {
        return End_Date;
    }

    /**
     * Sets the end date
     * @param end_Date
     */
    public void setEnd_Date(LocalDate end_Date) {
        End_Date = end_Date;
    }

    /**
     * Returns the end time
     * @return
     */
    public LocalTime getEnd_Time() {
        return End_Time;
    }

    /**
     * Sets the end time
     * @param end_Time
     */
    public void setEnd_Time(LocalTime end_Time) {
        End_Time = end_Time;
    }

    /**
     * Returns a count - used as a holder for report values
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Returns a month - used as a holder for report data
     * @return
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets the month
     * @param month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Gets the customer name linked to the appointment
     * @return
     */
    public String getCustomer_Name() {
        return Customer_Name;
    }

    /**
     * Sets the customer name linked to the appointment
     * @param customer_Name
     */
    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    /**
     * Returns the country
     * @return
     */
    public String getCountry() {
        return Country;
    }

    /**
     * Sets the country
     * @param country
     */
    public void setCountry(String country) {
        Country = country;
    }
}
