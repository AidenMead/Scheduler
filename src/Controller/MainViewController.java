package Controller;

import DAO.AppointmentsDAOImpl;
import DAO.ContactsDAOImpl;
import DAO.UsersDAOImpl;
import Model.Appointments;
import Model.Contacts;
import Model.Users;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {
    @FXML public TableView<Appointments> apptTable;
    @FXML public TableColumn<Appointments, Integer> AppointmentID;
    @FXML public TableColumn<Appointments, String> Title;
    @FXML public TableColumn<Appointments, String> Description;
    @FXML public TableColumn<Appointments, String> Location;
    @FXML public TableColumn<Appointments, String> Contact;
    @FXML public TableColumn<Appointments, String> Type;
    @FXML public TableColumn<Appointments, String> Start;
    @FXML public TableColumn<Appointments, LocalDate> StartDate;
    @FXML public TableColumn<Appointments, LocalTime> StartTime;
    @FXML public TableColumn<Appointments, String> End;
    @FXML public TableColumn<Appointments, LocalDate> EndDate;
    @FXML public TableColumn<Appointments, LocalTime> EndTime;
    @FXML public TableColumn<Appointments, Integer> CustomerID;

    @FXML public RadioButton viewAllBtn;
    @FXML public RadioButton byWeekBtn;
    @FXML public RadioButton byMonthBtn;
    @FXML public DatePicker datePicker;
    public ToggleGroup tableFilters = new ToggleGroup();

    public static Alert apptSoon = new Alert(Alert.AlertType.INFORMATION);

    AppointmentsDAOImpl appointments = new AppointmentsDAOImpl();
    ContactsDAOImpl contacts = new ContactsDAOImpl();

    ObservableList<Appointments> listAppointments = FXCollections.observableArrayList(appointments.getAll("appointments"));
    ObservableList<Contacts> listContacts = FXCollections.observableArrayList(contacts.getAll("contacts"));

    public MainViewController() throws SQLException {
    }

    /**
     * Sets the main appointments table on the main screen, which allows the user to view all of the appointments.
     */
    public void setApptTable() {

        for(Appointments a: listAppointments){
            a.setStart_Date(a.getStart().toLocalDate());
            a.setStart_Time(a.getStart().toLocalTime());
            a.setEnd_Date(a.getEnd().toLocalDate());
            a.setEnd_Time(a.getEnd().toLocalTime());
        }

        Main.getContactNameFromId(listAppointments, listContacts);
        AppointmentID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Location.setCellValueFactory(new PropertyValueFactory<>("Location"));
        Contact.setCellValueFactory(new PropertyValueFactory<>("Contact_Name"));
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        StartDate.setCellValueFactory(new PropertyValueFactory<>("Start_Date"));
        StartTime.setCellValueFactory(new PropertyValueFactory<>("Start_Time"));
        EndDate.setCellValueFactory(new PropertyValueFactory<>("End_Date"));
        EndTime.setCellValueFactory(new PropertyValueFactory<>("End_Time"));
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));

        apptTable.getColumns().setAll(AppointmentID, Title, Description, Location, Contact, Type, Start, End, CustomerID);
        apptTable.setItems(listAppointments);
    }

    /**
     * Handles button event to change to the appointments management screen.
     * @param actionEvent
     * @throws IOException
     */
    public void manageApptBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/ManageAppointmentsView.fxml"));
        Main.window.setTitle("Manage Appointments");
        Main.window.setScene(new Scene(root, 1200, 800));
        Main.window.show();
    }

    /**
     * Handles button event to change to the customer management screen.
     * @param actionEvent
     * @throws IOException
     */
    public void manageCustBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/ManageCustomersView.fxml"));
        Main.window.setTitle("Manage Customers");
        Main.window.setScene(new Scene(root, 1200, 800));
        Main.window.show();
    }

    /**
     * Checks whether this is an appointment within 15 minutes of the user logging in, and displays an alert
     * with the information if there is, or an alert saying there is no appointments if there isn't.
     *
     * Lambda expression is used here to iterate through each of the appointments in a cleaner way that an for loop
     */
    public void checkForUpcomingAppointment() {
        LocalDateTime now = LocalDateTime.now();

        apptSoon.setHeaderText("No Upcoming Appointments");
        apptSoon.setContentText("There are no appointments within the next 15 minutes.");

        listAppointments.forEach(appt -> {
            if (appt.getStart_Date().isEqual(now.toLocalDate())) {
                if (appt.getStart_Time().isAfter(now.toLocalTime()) && appt.getStart_Time().isBefore(now.toLocalTime().plusMinutes(15))) {
                    apptSoon.setHeaderText("Upcoming Appointment");
                    apptSoon.setContentText("There is an appointment starting in less than 15 minutes. " +
                            "\nAppointment ID: " + appt.getAppointment_ID() + " \nDate: " + appt.getStart_Date() + " \nTime: " + appt.getStart_Time());
                }
            }
        });
    }

    /**
     * Interface specific method which runs at scene initialization.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setApptTable();
        apptTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        checkForUpcomingAppointment();
        datePicker.setValue(LocalDate.now());
        viewAllBtn.setToggleGroup(tableFilters);
        byWeekBtn.setToggleGroup(tableFilters);
        byMonthBtn.setToggleGroup(tableFilters);
        tableFilters.selectToggle(viewAllBtn);
    }

    /**
     * Filters the displayed appointments to those that are occurring during the same week as selected in the datepicker.
     * Week is displayed Sun-Sat, so if a Wed. is selected, it will show the 3 days prior, the day selected, and the 3 days after.
     * @param actionEvent
     */
    public void setTableToWeek(ActionEvent actionEvent) {
        ObservableList<Appointments> byWeek = FXCollections.observableArrayList();
        for(Appointments a: listAppointments){
            if(a.getStart().getYear() == datePicker.getValue().getYear()) {
                if (a.getStart_Date().getMonth() == datePicker.getValue().getMonth()) {
                    var day = datePicker.getValue().getDayOfWeek().getValue();
                    var startOfWeek = datePicker.getValue().minusDays(day);

                    for (int i = 0; i < 7; i++) {
                        if (startOfWeek.equals(a.getStart_Date())) {
                            byWeek.add(a);
                        }
                        startOfWeek = startOfWeek.plusDays(1);
                    }
                }
            }
        }
        apptTable.setItems(byWeek);
    }

    /**
     * Sets the appointments displayed to shoow only those for the month which matches that of the date selected in the date picker.
     * @param actionEvent
     */
    public void setTableToMonth(ActionEvent actionEvent) {
        ObservableList<Appointments> byMonth = FXCollections.observableArrayList();
        for(Appointments a: listAppointments){
            if(a.getStart_Date().getMonth() == datePicker.getValue().getMonth()){
                byMonth.add(a);
            }
        }
        apptTable.setItems(byMonth);
    }

    /**
     * Button event handler to take the user to the report showing Appointments based on type and month.
     * @param actionEvent
     * @throws IOException
     */
    public void typeMonthReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/TypeMonthReportView.fxml"));
        Main.window.setTitle("Number of Appointments by Type and Month");
        Main.window.setScene(new Scene(root, 1200, 800));
        Main.window.centerOnScreen();
        Main.window.show();
    }

    /**
     * Handles the button event to take the  user to the report which shows appointments based on selected customer.
     * @param actionEvent
     * @throws IOException
     */
    public void byContactReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/ByContactReportView.fxml"));
        Main.window.setTitle("View Appointments by Customer");
        Main.window.setScene(new Scene(root, 1200, 800));
        Main.window.centerOnScreen();
        Main.window.show();
    }

    /**
     * Handles the button event to take the user to the report showing appointments based on Country and Type.
     * @param actionEvent
     * @throws IOException
     */
    public void byCountryReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/ByCountryReportView.fxml"));
        Main.window.setTitle("View Appointments by Country");
        Main.window.setScene(new Scene(root, 1200, 800));
        Main.window.centerOnScreen();
        Main.window.show();
    }

    /**
     * Handles sorting the table automatically if a new date is picked - otherwise the user would have to re-select
     * the radio button they want to get the table to re-sort.
     * @param actionEvent
     */
    public void autoSortDates(ActionEvent actionEvent) {
        if(byWeekBtn.isSelected()){
            setTableToWeek(actionEvent);
        } else if(byMonthBtn.isSelected()){
            setTableToMonth(actionEvent);
        } else {
            setApptTable();
        }
    }

    /**
     * Allows the user to log out, and resets the current user, then takes the user back to the main login screen.
     * @param actionEvent
     * @throws IOException
     */
    public void handleLogOut(ActionEvent actionEvent) throws IOException {

        UsersDAOImpl.currentUser = null;
        Parent root = FXMLLoader.load(getClass().getResource("../Views/LoginView.fxml"));
        Main.window.setTitle("Log In to Schedule");
        Main.window.setScene(new Scene(root, 600, 400));
        Main.window.centerOnScreen();
        Main.window.show();
    }
}
