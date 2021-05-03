package Model;

import java.time.LocalDateTime;

public class Users {
    private int User_ID;
    private String User_Name;
    private String Password;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;

    /**
     * Returns user id
     * @return
     */
    public int getUser_ID() {
        return User_ID;
    }

    /**
     * Sets user ID
     * @param user_ID
     */
    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    /**
     * Returns user name
     * @return
     */
    public String getUser_Name() {
        return User_Name;
    }

    /**
     * Sets user name
     * @param user_Name
     */
    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    /**
     * Returns password
     * @return
     */
    public String getPassword() {
        return Password;
    }

    /**
     * Sets password
     * @param password
     */
    public void setPassword(String password) {
        Password = password;
    }

    /**
     * Returns the LocalDateTime create date
     * @return
     */
    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    /**
     * Sets the LocalDateTIme create date
     * @param create_Date
     */
    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    /**
     * Returns who created the user
     * @return
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * Sets who created the user
     * @param created_By
     */
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    /**
     * Returns LocalDateTIme of last update
     * @return
     */
    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    /**
     * Sets the LocalDateTime of the last update
     * @param last_Update
     */
    public void setLast_Update(LocalDateTime last_Update) {
        Last_Update = last_Update;
    }

    /**
     * Returns who last updated user
     * @return
     */
    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    /**
     * Sets who last updated user
     * @param last_Updated_By
     */
    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    /**
     * Returns the user ID and user name instead of the objects location in memory.
     * @return
     */
    @Override
    public String toString() {
        return getUser_ID() + " - " + getUser_Name();
    }
}
