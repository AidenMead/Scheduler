package Controller;

import DAO.*;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageCustomersController implements Initializable {
    @FXML public TableView<Customers> customersTable;
    @FXML public TableColumn<Customers, Integer> customerIdCol;
    @FXML public TableColumn<Customers, String> customerNameCol;
    @FXML public TableColumn<Customers, String> addressCol;
    @FXML public TableColumn<Customers, String> divisionCol;
    @FXML public TableColumn<Customers, String> countryCol;
    @FXML public TableColumn<Customers, String> postalCodeCol;
    @FXML public TableColumn<Customers, String> phoneCol;

    @FXML public TextField customerIdInput;
    @FXML public TextField customerNameInput;
    @FXML public TextField addressStreetInput;
    @FXML public TextField addressCityInput;
    @FXML public TextField postalCodeInput;
    @FXML public TextField phoneInput;

    @FXML public ComboBox<FLDivisions> divisionDropdown;
    @FXML public ComboBox<Countries> countryDropdown;

    @FXML public Label addCustNotice;
    @FXML public Label editCustNotice;
    @FXML public Label editCustNoticeId;

    private boolean isEdit = false;

    AppointmentsDAOImpl apptDAO = new AppointmentsDAOImpl();
    CustomersDAOImpl customerDAO = new CustomersDAOImpl();
    FLDivisionsDAOImpl divisonsDAO = new FLDivisionsDAOImpl();
    CountriesDAOImpl countriesDAO = new CountriesDAOImpl();

    ObservableList<Customers> customersList = FXCollections.observableArrayList(customerDAO.getAll("customers"));
    ObservableList<FLDivisions> divList = FXCollections.observableArrayList(divisonsDAO.getAll("first_level_divisions"));
    ObservableList<Countries> countryList = FXCollections.observableArrayList(countriesDAO.getAll("countries"));

    public ManageCustomersController() throws SQLException {
    }

    /**
     * Takes a Customer's Address value and splits it into a Street and a City to populate in the separate text fields.
     * @param customer
     * @return
     */
    public String[] separateAddress(Customers customer) {
        String[] split = customer.getAddress().split(",");
        return split;
    }

    /**
     * Takes the textfields and combines them to store in the table's single address column.
     * @param street
     * @param city
     * @return
     */
    public String combineAddress(String street, String city){
        String fullAddress = street + ", " + city;
        return fullAddress;
    }

    /**
     * Sets the division and country names from the IDs to display in the table.
     * @param custList
     * @param divList
     * @param countryList
     */
    public void setDivisionNames(ObservableList<Customers> custList,  ObservableList<FLDivisions> divList, ObservableList<Countries> countryList){
        for(Customers c: custList){
            for(FLDivisions d: divList){
                if(c.getDivision_ID() == d.getDivision_ID()) {
                    c.setDivision_Name(d.getDivision());
                    c.setCountry_ID(d.getCountry_ID());
                }


            }
            for(Countries country: countryList) {
                if (c.getCountry_ID() == country.getCountry_ID()) {
                    c.setCountry(country.getCountry());
                }
            }
        }
    }

    /**
     * Sets the customers table.
     */
    public void setCustomersTableView() {
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("Division_Name"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        customersTable.getColumns().setAll(customerIdCol, customerNameCol, addressCol, divisionCol, countryCol, postalCodeCol, phoneCol);
        customersTable.setItems(customersList);
    }

    /**
     * Sets the country and division dropdowns.
     */
    public void setDropdowns() {
        divisionDropdown.setPromptText("Select Country First");
        countryDropdown.setItems(countryList);
    }

    /**
     * Handles the button event to send the user back to the main view.
     * @param actionEvent
     * @throws IOException
     */
    public void backHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/MainView.fxml"));
        Main.window.setTitle("Appointments");
        Main.window.setScene(new Scene(root, 1200, 800));
        Main.window.centerOnScreen();
        Main.window.show();
    }

    /**
     * Gets the division from the ID.
     * @param cust
     * @param divList
     * @return
     */
    public FLDivisions getDivById(Customers cust, ObservableList<FLDivisions> divList){
        FLDivisions foundDiv = null;
        for(FLDivisions d: divList){
            if(cust.getDivision_ID() == d.getDivision_ID()){
                foundDiv = d;
            }
        }
        return foundDiv;
    }

    /**
     * Gets the country from the names.
     * @param cust
     * @param countryList
     * @return
     */
    public Countries getCountryByName(Customers cust, ObservableList<Countries> countryList){
        Countries foundCountry = null;
        for(Countries c: countryList){
            if(cust.getCountry() == c.getCountry()){
                foundCountry = c;
            }
        }
        return foundCountry;
    }

    /**
     * Button handler which sets the text fields and dropdowns to the customer selected in the table view.
     */
    public void editSelected() {
        Customers currentSelected = customersTable.getSelectionModel().getSelectedItem();
        String[] address = separateAddress(currentSelected);

        customerIdInput.setText(String.valueOf(currentSelected.getCustomer_ID()));
        customerNameInput.setText(currentSelected.getCustomer_Name());

        //checking to make sure there was a street and city (two values in the array), just in case legacy DB data without city is used
        addressStreetInput.setText(address[0]);
        if(address.length > 1) {
            addressCityInput.setText(address[1]);
        } else {
            addressCityInput.setText("No City Provided");
        }

        postalCodeInput.setText(currentSelected.getPostal_Code());
        phoneInput.setText(currentSelected.getPhone());
        divisionDropdown.setValue(getDivById(currentSelected, divList));
        countryDropdown.setValue(getCountryByName(currentSelected, countryList));
        addCustNotice.setVisible(false);
        editCustNotice.setVisible(true);
        editCustNoticeId.setVisible(true);
        editCustNoticeId.setText(String.valueOf(currentSelected.getCustomer_ID()));

        isEdit = true;
    }

    /**
     * Clears all textfields and selections.
     * @param actionEvent
     */
    public void clearSelection(ActionEvent actionEvent) {
        customerIdInput.clear();
        customerNameInput.clear();
        addressStreetInput.clear();
        addressCityInput.clear();
        postalCodeInput.clear();
        phoneInput.clear();
        divisionDropdown.getSelectionModel().clearSelection();
        divisionDropdown.setPromptText("Select Country First");
        countryDropdown.getSelectionModel().clearSelection();
        countryDropdown.setPromptText("Country");
        addCustNotice.setVisible(true);
        editCustNotice.setVisible(false);
        editCustNoticeId.setVisible(false);

        customersTable.getSelectionModel().clearSelection();

        isEdit = false;
    }

    /**
     * Saves new or updates customers.
     * @param actionEvent
     * @throws SQLException
     */
    public void saveCustomer(ActionEvent actionEvent) throws SQLException {
        Customers currentCust = customersTable.getSelectionModel().getSelectedItem();
        FLDivisions currentDivision = divisionDropdown.getSelectionModel().getSelectedItem();
        Countries currentCountry = countryDropdown.getSelectionModel().getSelectedItem();
        int currentCustIndex = customersList.indexOf(currentCust);

        int customerId;
        String customerName = customerNameInput.getText();
        String address = combineAddress(addressStreetInput.getText(), addressCityInput.getText());
        String postalCode = postalCodeInput.getText();
        String phone = phoneInput.getText();
        LocalDateTime createDate;
        String createdBy;
        LocalDateTime lastUpdate = LocalDateTime.now();
        String lastUpdatedBy = UsersDAOImpl.currentUser.getUser_Name();
        int division = currentDivision.getDivision_ID();

        if(isEdit){
            customerId = Integer.parseInt(customerIdInput.getText());
            createDate = currentCust.getCreate_Date();
            createdBy = currentCust.getCreated_By();
        } else {
            customerId = customerDAO.nextCustId;
            createDate = LocalDateTime.now();
            createdBy = UsersDAOImpl.currentUser.getUser_Name();
        }

        //set the new customer values - Intentionally set differently than the appointment method, which uses a constructor
        Customers custToSave = new Customers();
        custToSave.setCustomer_Name(customerName);
        custToSave.setAddress(address);
        custToSave.setPostal_Code(postalCode);
        custToSave.setPhone(phone);
        custToSave.setCreate_Date(createDate);
        custToSave.setCreated_By(createdBy);
        custToSave.setLast_Update(lastUpdate);
        custToSave.setLast_Updated_By(lastUpdatedBy);
        custToSave.setDivision_ID(division);

        custToSave.setDivision_Name(currentDivision.getDivision());
        custToSave.setCountry(currentCountry.getCountry());

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);

        if(validateFields()) {
            if (isEdit) {
                custToSave.setCustomer_ID(customerId);
                customerDAO.update(custToSave);
                customersList.set(currentCustIndex, custToSave);
                confirm.setHeaderText("Existing Customer Updated");
                confirm.setContentText("Customer Id: " + custToSave.getCustomer_ID() + " has been updated.");
            } else {
                customerDAO.insert(custToSave);
                custToSave.setCustomer_ID(customerDAO.nextCustId);
                customersList.add(custToSave);
                confirm.setHeaderText("New Customer Added");
                confirm.setContentText("Customer ID: " + custToSave.getCustomer_ID() + " has been added.");

            }
            confirm.show();
            clearSelection(actionEvent);
        }
    }

    /**
     * Sets the divisions dropdown to only shows the divisions available for the select country. Sets the dropdown each
     * time an event occurs on the countryDropdown combobox.
     * @param actionEvent
     */
    public void filterDivisions(ActionEvent actionEvent) {
        if(countryDropdown.getSelectionModel().getSelectedIndex() != -1) {
            ObservableList<FLDivisions> filteredDivisions = FXCollections.observableArrayList();
            Countries selectedCountry = countryDropdown.getSelectionModel().getSelectedItem();
            for (FLDivisions div : divList) {
                if (selectedCountry.getCountry_ID() == div.getCountry_ID()) {
                    filteredDivisions.add(div);
                }
            }
            divisionDropdown.setItems(filteredDivisions);
        }
    }

    /**
     * When a customer is deleted, all corresponding appointments must be deleted first. This handles deleting the
     * appointments if the user selects that they want to continue with deleting the customer and associated appointments.
     *
     * Lambda expression is used  here to iterate through the appointments in a cleaner way than a for loop.
     * @param appts
     * @return
     */
    public int deleteAppointments(ObservableList<Appointments> appts){
        var ref = new Object() {
            int count = 0;
        };
        appts.forEach(t-> {
            try {
                apptDAO.delete(t);
                ref.count++;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        return ref.count;
    }

    /**
     * Handles deleting the customer.
     * @param actionEvent
     * @throws SQLException
     */
    public void deleteCust(ActionEvent actionEvent) throws SQLException {
        Customers currentSelected = customersTable.getSelectionModel().getSelectedItem();

        //calls a customer methods in customersDAO just to get associated appointments and puts them in a list
        ObservableList<Appointments> associatedAppointments = FXCollections.observableArrayList(customerDAO.findCustAppointments(currentSelected));

        //creates an array to display in confirmation of delete
        ArrayList<String> ids = new ArrayList<>();
        for(Appointments a: associatedAppointments){
            ids.add(String.valueOf(a.getAppointment_ID()));
        }

        Alert confrimDelete = new Alert(Alert.AlertType.CONFIRMATION);

        if(!associatedAppointments.isEmpty()){
            Alert deleteAppts = new Alert(Alert.AlertType.CONFIRMATION);
            deleteAppts.setHeaderText("Contact has existing appointments.");
            deleteAppts.setContentText("All associated appointments for this contact must be deleted before contact can be removed. \nClick \"OK\" to delete associated appointments and customer.");
            Optional<ButtonType> result = deleteAppts.showAndWait();
            ButtonType button = result.orElse(ButtonType.CANCEL);

            if (button == ButtonType.OK) {
                if(deleteAppointments(associatedAppointments) > 0 && customerDAO.delete(currentSelected)) {

                    customersList.remove(currentSelected);

                    confrimDelete.setHeaderText("Customer and associated appointments have been deleted.");
                    confrimDelete.setContentText("Deleted Customer ID: " + currentSelected.getCustomer_ID() + " - " + currentSelected.getCustomer_Name() +
                            "\nDeleted Appointment ID(s): " + ids); //I don't like this id display - I want to find a better way to display the deleted appointments
                    confrimDelete.show();
                }
            }
        } else {
            if(customerDAO.delete(currentSelected)){
                confrimDelete.setHeaderText("Customer deleted");
                confrimDelete.setContentText("Customer had no associated appointments. \nCustomer has been deleted successfully.");

                customersList.remove(currentSelected);
                confrimDelete.show();
            }

        }
    }

    /**
     * Verifies all fields have been filled before continuing.
     * @return
     */
    public boolean validateFields() {
        String customerName = customerNameInput.getText();
        String addressStreet = addressStreetInput.getText();
        String addressCity = addressCityInput.getText();
        String postalCode = postalCodeInput.getText();
        String phone = phoneInput.getText();

        if( (customerName == null || customerName.isEmpty())
            || (addressStreet == null || addressStreet.isEmpty())
            || (addressCity == null || addressCity.isEmpty())
            || (postalCode == null || postalCode.isEmpty())
            || (phone == null || phone.isEmpty())
            || (divisionDropdown.getSelectionModel().getSelectedItem() == null)
        ) {
            Alert fillFields = new Alert(Alert.AlertType.WARNING);
            fillFields.setHeaderText("All fields required.");
            fillFields.setContentText("Make sure all fields have been filled before continuing.");
            fillFields.show();
            return false;
        }
        return true;
    }

    /**
     * Interface specific method which runs on scene initialization.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setCustomersTableView();
        setDropdowns();
        setDivisionNames(customersList, divList, countryList);
        addCustNotice.setVisible(true);
        editCustNotice.setVisible(false);
        editCustNoticeId.setVisible(false);
    }
}
