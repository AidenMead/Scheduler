package Controller;

import DAO.*;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.Users;
import Utility.DateTimeUtility;
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
import java.sql.SQLOutput;
import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ManageAppointmentsController implements Initializable {
    @FXML public TableView<Appointments> addApptTable;
    @FXML public TableColumn<Appointments, Integer> addAppointmentID;
    @FXML public TableColumn<Appointments, String> addTitle;
    @FXML public TableColumn<Appointments, String> addDescription;
    @FXML public TableColumn<Appointments, String> addLocation;
    @FXML public TableColumn<Appointments, String> addContact;
    @FXML public TableColumn<Appointments, String> addType;
    @FXML public TableColumn<Appointments, String> addStart;
    @FXML public TableColumn<Appointments, String> addEnd;
    @FXML public TableColumn<Appointments, LocalDate> startDate;
    @FXML public TableColumn<Appointments, LocalTime> startTime;
    @FXML public TableColumn<Appointments, LocalDate> endDate;
    @FXML public TableColumn<Appointments, LocalTime> endTime;
    @FXML public TableColumn<Appointments, Integer> addCustomerID;
    @FXML public TableColumn<Appointments, Integer> addCustId;
    @FXML public TableColumn<Appointments, Integer> addCustName;

    @FXML public TextField addApptIDInput;
    @FXML public TextField addTitleInput;
    @FXML public TextArea addDescriptionInput;
    @FXML public TextField addLocationInput;
    @FXML public Spinner addStartTime;
    @FXML public DatePicker addStartDate;
    @FXML public Spinner addEndTime;
    @FXML public DatePicker addEndDate;

    @FXML public ComboBox<Customers> addCustomerDropdown;
    @FXML public ComboBox<Contacts> addContactsDropdown;
    @FXML public ComboBox<Users> userDropdown;
    @FXML public ComboBox<String> addTypeDropdown;

    @FXML public Label editingNotice;
    @FXML public Label addNotice;
    @FXML public Label editNoticeId;

    private boolean isEdit;

    AppointmentsDAOImpl appointments = new AppointmentsDAOImpl();
    ContactsDAOImpl contacts = new ContactsDAOImpl();
    CustomersDAOImpl customers = new CustomersDAOImpl();
    UsersDAOImpl users = new UsersDAOImpl();

    ObservableList<Appointments> listAppointments = FXCollections.observableArrayList(appointments.getAll("appointments"));
    ObservableList<Customers> listCustomers = FXCollections.observableArrayList(customers.getAll("customers"));
    ObservableList<Contacts> listContacts = FXCollections.observableArrayList(contacts.getAll("contacts"));
    ObservableList<Users> listUsers = FXCollections.observableArrayList(users.getAll("users"));

    public ManageAppointmentsController() throws SQLException {
    }

    /**
     * Sets the appointments table displayed on the management page.
     */
    public void setAddApptTable(){
        //Grabs the contact name to show in the table - only contact ID is stored in Appointments object.
        Main.getContactNameFromId(listAppointments, listContacts);
        //Grabs the customer name to show in the table - only customer ID is stored in Appointments object.
        Main.getCustomerNameFromId(listAppointments, listCustomers);

        //separates  the date and time so they can be displayed in two columns.
        for(Appointments a: listAppointments){
            a.setStart_Date(a.getStart().toLocalDate());
            a.setStart_Time(a.getStart().toLocalTime());
            a.setEnd_Date(a.getEnd().toLocalDate());
            a.setEnd_Time(a.getEnd().toLocalTime());
        }

        addAppointmentID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        addTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        addDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        addLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));

        addContact.setCellValueFactory(new PropertyValueFactory<>("Contact_Name"));
        addType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("Start_Date"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("Start_Time"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("End_Date"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("End_Time"));
        addCustId.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        addCustName.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));

        addApptTable.getColumns().setAll(addAppointmentID, addTitle, addDescription, addLocation, addContact, addType, addStart, addEnd, addCustomerID);
        addApptTable.setItems(listAppointments);
    }

    /**
     * Sets all of the dropdowns to the available options.
     */
    public void setDropdowns() {
        addCustomerDropdown.setItems(listCustomers);
        addCustomerDropdown.setPromptText("Select a customer");

        addContactsDropdown.setItems(listContacts);
        addContactsDropdown.setPromptText("Select a contact");

        userDropdown.setItems(listUsers);
        userDropdown.setPromptText("Select a user");

        ObservableList<String> types = FXCollections.observableArrayList("Discussion", "Coffee", "Meeting", "Huddle", "Check-in");

        addTypeDropdown.setItems(types);
        addTypeDropdown.setPromptText("Select an appointment type");
    }

    /**
     * Sets the time spinners.
     */
    public void setSpinners(){
        LocalTime startTime = LocalTime.of(8,0);
        LocalTime endTime = LocalTime.of(8,15);

        addStartTime.setValueFactory(new SpinnerValueFactory() {

            @Override
            public void decrement(int steps) {
                if(getValue() == null){
                    setValue(startTime);
                } else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.minusMinutes(15));
                }
            }

            @Override
            public void increment(int steps) {
                if(getValue() == null){
                    setValue(startTime);
                } else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.plusMinutes(15));
                }
            }
        });
        addStartTime.getValueFactory().setValue(startTime);

        addEndTime.setValueFactory(new SpinnerValueFactory() {

            @Override
            public void decrement(int steps) {
                if(getValue() == null){
                    setValue(endTime);
                } else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.minusMinutes(15));
                }

            }

            @Override
            public void increment(int steps) {
                if(getValue() == null){
                    setValue(endTime);
                } else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.plusMinutes(15));
                }
            }
        });
        addEndTime.getValueFactory().setValue(endTime);
    }

    /**
     * Selects the customer by using the Customer_ID from the appointment passed as a parameter.
     * @param appt
     * @param custList
     * @return
     */
    public Customers getCustomerById(Appointments appt, ObservableList<Customers> custList) {
        Customers foundCustomer = null;
        for(Customers c: custList){
            if (appt.getCustomer_ID() == c.getCustomer_ID()){
                foundCustomer = c;
            }
        }

        return foundCustomer;
    }

    /**
     * Selects the contact by using the Contact_ID from the appointment passed as a parameter.
     * @param appt
     * @param contList
     * @return
     */
    public Contacts getContactById(Appointments appt, ObservableList<Contacts> contList){
        Contacts foundContact = null;
        for(Contacts c: contList){
            if (appt.getContact_ID() == c.getContact_ID()){
                foundContact = c;
            }
        }
        return foundContact;
    }

    /**
     * Selects the user by using the User_ID from the appointment passed as a parameter.
     * @param appt
     * @param userList
     * @return
     */
    public Users getUserById(Appointments appt, ObservableList<Users> userList){
        Users foundUser= null;
        for(Users u: userList){
            if (appt.getUser_ID() == u.getUser_ID()){
                foundUser = u;
            }
        }
        return foundUser;
    }

    /**
     * Sets the textfields and dropdowns to the values of the selected appointment so the user can edit it.
     */
    public void editSelected(){
        Appointments currentSelected = addApptTable.getSelectionModel().getSelectedItem();

        addApptIDInput.setText(String.valueOf(currentSelected.getAppointment_ID()));
        addTitleInput.setText(String.valueOf(currentSelected.getTitle()));
        addDescriptionInput.setText(String.valueOf(currentSelected.getDescription()));
        addLocationInput.setText(String.valueOf(currentSelected.getLocation()));
        addCustomerDropdown.setValue(getCustomerById(currentSelected,listCustomers));
        addContactsDropdown.setValue(getContactById(currentSelected, listContacts));
        addTypeDropdown.setValue(String.valueOf(currentSelected.getType()));
        addStartDate.setValue(currentSelected.getStart().toLocalDate());
        addEndDate.setValue(currentSelected.getEnd().toLocalDate());
        addStartTime.getValueFactory().setValue(currentSelected.getStart().toLocalTime());
        addEndTime.getValueFactory().setValue(currentSelected.getEnd().toLocalTime());
        userDropdown.setValue(getUserById(currentSelected, listUsers));
        addNotice.setVisible(false);
        editingNotice.setVisible(true);
        editNoticeId.setVisible(true);
        editNoticeId.setText(String.valueOf(currentSelected.getAppointment_ID()));

        isEdit = true;
    }

    /**
     * Handles button event for sending the  user back to the main view.
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
     * Clears the text fields from any appointment data so the user can select a different appointment to edit.
     * @param actionEvent
     */
    public void clearSelection(ActionEvent actionEvent) {
        addApptIDInput.setText("Auto_Incremented");
        addTitleInput.clear();
        addDescriptionInput.clear();
        addLocationInput.clear();
        addCustomerDropdown.getSelectionModel().clearSelection();
        addContactsDropdown.getSelectionModel().clearSelection();
        addTypeDropdown.getSelectionModel().clearSelection();
        userDropdown.getSelectionModel().clearSelection();
        addStartDate.setValue(LocalDate.now());
        addEndDate.setValue(LocalDate.now());
        setSpinners();
        addNotice.setVisible(true);
        editingNotice.setVisible(false);
        editNoticeId.setVisible(false);
        addApptTable.getSelectionModel().clearSelection();

        isEdit = false;
    }

    /**
     * Method to run a check before an appointment is edited or added to make sure the times selected are within EST business hours.
     * @param start
     * @param end
     * @return
     */
    public boolean compareHours(LocalDateTime start, LocalDateTime end) {
        LocalDate date = start.toLocalDate();
        LocalTime businessStart = LocalTime.of(8, 00);
        LocalTime businessEnd = LocalTime.of(22, 00);
        ZoneId est = ZoneId.of("America/New_York");

        ZonedDateTime stToDT = LocalDateTime.of(date, businessStart).atZone(est);
        ZonedDateTime endToDT = LocalDateTime.of(date, businessEnd).atZone(est);

        Alert hrsAlert = new Alert(Alert.AlertType.WARNING);

        //If the above finds that the dates are not on the weekend, then it will check if the times are within EST business hours, and return false and alert if they are.
        if (DateTimeUtility.toEST(start).isBefore(ChronoLocalDateTime.from(stToDT)) || DateTimeUtility.toEST(end).isAfter(ChronoLocalDateTime.from(endToDT))) {
            hrsAlert.setHeaderText("Appointment outside of Business Hours");
            hrsAlert.setContentText("The appointment must be scheduled between 8:00am and 10:00pm EST." +
                    "\n\nYou have selected times that fall outside of those hours. Please adjust your selection and try again.");
            hrsAlert.show();
            return false;
        }
        return true;
    }

    /**
     * Checks to make sure a customer doesn't already have an appointment during the new/edited appointment's time frame.
     * @param newAppt
     * @param existingAppointment
     * @return
     */
    public boolean checkForCustOverlapAppt(Appointments newAppt, ObservableList<Appointments> existingAppointment) {
        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);

        //checks each appointment that exists and compares the new possible appointment times
        for(Appointments a: existingAppointment){

            //create variables just to make things easier to read
            LocalTime oldApptStart = a.getStart_Time();
            LocalTime oldApptEnd = a.getEnd_Time();
            LocalTime newApptStart = newAppt.getStart_Time();
            LocalTime newApptEnd = newAppt.getEnd_Time();

            //verifies that this is a new/different appointment than the one being edited. If it is the same appointment, we don't want to flag as a conflict.
            if(a.getAppointment_ID() != newAppt.getAppointment_ID()) {
                //Checks to see if the old appointment contains the customer in question
                if (a.getCustomer_ID() == newAppt.getCustomer_ID()) {
                    //then checks that the new and old appointment are occurring on the same day
                    if (a.getStart_Date().equals(newAppt.getStart().toLocalDate())) {
                        //last, checks to make sure that the appointments don't overlap
                        if (!newApptEnd.isBefore(oldApptStart) && !newApptStart.isAfter(oldApptEnd) ) {
                            overlapAlert.setHeaderText("Customer already booked.");
                            overlapAlert.setContentText("Customer is already scheduled within that timeframe.");
                            overlapAlert.show();
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Handles either saving the edited appointment or adding the new one.
     * @param actionEvent
     * @throws SQLException
     */
    public void saveAppointment(ActionEvent actionEvent) throws SQLException {
        Appointments currentSelected = addApptTable.getSelectionModel().getSelectedItem();
        Customers currentCustomer = addCustomerDropdown.getSelectionModel().getSelectedItem();
        Object currentType = addTypeDropdown.getSelectionModel().getSelectedItem();
        Contacts currentContact = addContactsDropdown.getSelectionModel().getSelectedItem();
        Users selectedUser = userDropdown.getSelectionModel().getSelectedItem();

        int apptIndex = listAppointments.indexOf(currentSelected);
        int newAppointmentID;
        String newTitle = addTitleInput.getText();
        String newDescription = addDescriptionInput.getText();
        String newLocation = addLocationInput.getText();
        String newType = String.valueOf(currentType);
        LocalDateTime newStart = LocalDateTime.of(addStartDate.getValue(), (LocalTime) addStartTime.getValue());
        LocalDate newStartDate = addStartDate.getValue();
        LocalTime newStartTime = (LocalTime) addStartTime.getValue();
        LocalDateTime newEnd = LocalDateTime.of(addEndDate.getValue(), (LocalTime) addEndTime.getValue());
        LocalDate newEndDate = addEndDate.getValue();
        LocalTime newEndTime = (LocalTime) addEndTime.getValue();
        String newCreatedBy;
        LocalDateTime newCreateDate;
        LocalDateTime newLastUpdated = LocalDateTime.now();
        String newLastUpdatedBy = users.getCurrentUser().getUser_Name();
        int newCustomerID = currentCustomer.getCustomer_ID();
        int newUserID = selectedUser.getUser_ID();
        int newContactID = currentContact.getContact_ID();

        if(isEdit) {
            newAppointmentID = Integer.parseInt(addApptIDInput.getText());
            newCreatedBy = currentSelected.getCreated_By();
            newCreateDate = currentSelected.getCreate_Date();
        } else {
            newAppointmentID = 0; //there's no way to know the ID, and we want the compareOverlapAppointments to always check when the appointment is new, so we need to set it to an ID that doesn't exist
            newCreatedBy = users.getCurrentUser().getUser_Name();
            newCreateDate = LocalDateTime.now();
        }

        //constructor to build the object with the applicable values above
        Appointments apptToSave = new Appointments(newAppointmentID,
                                                    newTitle,
                                                    newDescription,
                                                    newLocation,
                                                    newType,
                                                    newStart,
                                                    newEnd,
                                                    newCreateDate,
                                                    newCreatedBy,
                                                    newLastUpdated,
                                                    newLastUpdatedBy,
                                                    newCustomerID,
                                                    newUserID,
                                                    newContactID);


        //Set all of this so they populate correctly in the tableview - these are not set in the constructor
        apptToSave.setStart_Date(newStartDate);
        apptToSave.setStart_Time(newStartTime);
        apptToSave.setEnd_Date(newEndDate);
        apptToSave.setEnd_Time(newEndTime);
        apptToSave.setContact_Name(currentContact.getContact_Name());
        apptToSave.setCustomer_Name(currentCustomer.getCustomer_Name());

        Alert saveAlert = new Alert(Alert.AlertType.CONFIRMATION);

        if(validateFields()) {
            if (checkForCustOverlapAppt(apptToSave, listAppointments)) {
                if (compareHours(newStart, newEnd)) {
                    if (isEdit) {
                        appointments.update(apptToSave);
                        listAppointments.set(apptIndex, apptToSave);
                        saveAlert.setHeaderText("Existing appointment updated.");
                        saveAlert.setContentText("Appointment ID: " + apptToSave.getAppointment_ID() + " has been updated.");
                    } else {
                        appointments.insert(apptToSave);
                        apptToSave.setAppointment_ID(AppointmentsDAOImpl.nextApptId); //gets the ID when it's inserted and sets it so it shows correctly in table before next DB query
                        listAppointments.add(apptToSave);
                        saveAlert.setHeaderText("New appointment added.");
                        saveAlert.setContentText("Appointment ID: " + apptToSave.getAppointment_ID() + " has been added as a new appointment.");
                    }
                    saveAlert.show();
                    clearSelection(actionEvent); //clear the form after save
                }
            }
        }

    }

    /**
     * Verifies all fields have been filled before continuing.
     * @return
     */
    public boolean validateFields () {
        String newTitle = addTitleInput.getText();
        String newDescription = addDescriptionInput.getText();
        String newLocation = addLocationInput.getText();
        LocalDateTime newStart = LocalDateTime.of(addStartDate.getValue(), (LocalTime) addStartTime.getValue());
        LocalDateTime newEnd = LocalDateTime.of(addEndDate.getValue(), (LocalTime) addEndTime.getValue());

        if(newEnd.isBefore(newStart)){
            Alert timeIssue = new Alert(Alert.AlertType.WARNING);
            timeIssue.setHeaderText("Check start and end times.");
            timeIssue.setContentText("Start time must come before end time.");
            timeIssue.show();
            return false;
        }

        if ( (newTitle == null || newTitle.isEmpty())
            || (newDescription == null || newDescription.isEmpty())
            || (newLocation == null || newLocation.isEmpty())
            || (userDropdown.getSelectionModel().isEmpty())
            || (addCustomerDropdown.getSelectionModel().isEmpty())
            || (addContactsDropdown.getSelectionModel().isEmpty())
            || (addTypeDropdown.getSelectionModel().isEmpty())
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
     * Handles button event for deleting an appointment.
     * @param actionEvent
     * @throws SQLException
     */
    public void deleteAppt(ActionEvent actionEvent) throws SQLException {
        Appointments currentAppt = addApptTable.getSelectionModel().getSelectedItem();

        if(currentAppt == null){
            Alert noAppt = new Alert(Alert.AlertType.INFORMATION, "There is no appointment selected to delete. Select and appointment to delete.\n to return the main menu, use the back button.");
            noAppt.setHeaderText("No appointment selected to delete.");
            noAppt.show();
            return;
        }

        if(appointments.delete(currentAppt)) {
            listAppointments.remove(currentAppt);

            Alert confirm = new Alert(Alert.AlertType.INFORMATION);
            confirm.setHeaderText("Appointment Cancelled");
            confirm.setContentText("Appointment ID: " + currentAppt.getAppointment_ID() + "\n Appointment Type: " + currentAppt.getType());
            confirm.show();
        }
    }

    /**
     * Sets the end datepicker based on the start date pickers value to smooth use.
     * @param actionEvent
     */
    public void setEdit(ActionEvent actionEvent) {
        LocalDate start = addStartDate.getValue();
        addEndDate.setValue(start);
    }

    /**
     * Interface specific method called when scene is initialized.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAddApptTable();
        setDropdowns();
        setSpinners();
        addNotice.setVisible(true);
        editingNotice.setVisible(false);
        editNoticeId.setVisible(false);
    }
}
