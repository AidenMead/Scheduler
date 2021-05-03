package Model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Countries {
    private int Country_ID;
    private String Country;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;

    /**
     * Returns the country ID
     * @return
     */
    public int getCountry_ID() {
        return Country_ID;
    }

    /**
     * Sets the country ID
     * @param country_ID
     */
    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
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

    /**
     * Returns the date the country was created
     * @return
     */
    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    /**
     * Sets the date the country was created
     * @param create_Date
     */
    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    /**
     * Returns who created the country
     * @return
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * Sets who created the country
     * @param created_By
     */
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    /**
     * Returns the last update date/time
     * @return
     */
    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    /**
     * Sets the last update date/time
     * @param last_Update
     */
    public void setLast_Update(LocalDateTime last_Update) {
        Last_Update = last_Update;
    }

    /**
     * Returns who last updated
     * @return
     */
    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    /**
     * Sets who last updated
     * @param last_Updated_By
     */
    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    /**
     * Used to display the country field instead of the object's location in memory
     * @return
     */
    @Override
    public String toString(){
        return getCountry();
    }
}
