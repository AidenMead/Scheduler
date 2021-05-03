package Model;

import java.time.LocalDateTime;

public class FLDivisions {
    private int Division_ID;
    private String Division;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;
    private int Country_ID;

    /**
     * Returns the division id
     * @return
     */
    public int getDivision_ID() {
        return Division_ID;
    }

    /**
     * Sets the division id
     * @param division_ID
     */
    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

    /**
     * Returns the division
     * @return
     */
    public String getDivision() {
        return Division;
    }

    /**
     * Sets the division
     * @param division
     */
    public void setDivision(String division) {
        Division = division;
    }

    /**
     * Returns the LocalDateTime the division was created
     * @return
     */
    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    /**
     * Sets the LocalDateTime the division was created
     * @param create_Date
     */
    public void setCreate_Date(LocalDateTime create_Date) {
        Create_Date = create_Date;
    }

    /**
     * Returns who created the division
     * @return
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * Sets who created the division
     * @param created_By
     */
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    /**
     * Return LocalDateTime of last update
     * @return
     */
    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    /**
     * Sets LocalDateTime of the last update
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
     * Sets who last updated.
     * @param last_Updated_By
     */
    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
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
     * Returns the division instead of the objects location in memory.
     * @return
     */
    @Override
    public String toString() {
        return getDivision();
    }
}
