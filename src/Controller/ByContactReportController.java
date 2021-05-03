package Controller;

import DAO.ContactsDAOImpl;
import DAO.CustomersDAOImpl;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ByContactReportController implements Initializable {
    @FXML public TableView byCustomerTable;
    @FXML public TableColumn apptIdCol;
    @FXML public TableColumn custCol;
    @FXML public TableColumn custIdCol;
    @FXML public TableColumn custNameCol;
    @FXML public TableColumn contactCol;
    @FXML public TableColumn contactIdCol;
    @FXML public TableColumn contactNameCol;
    @FXML public TableColumn titleCol;
    @FXML public TableColumn typeCol;
    @FXML public TableColumn descriptionCol;
    @FXML public TableColumn startCol;
    @FXML public TableColumn startDateCol;
    @FXML public TableColumn startTimeCol;
    @FXML public TableColumn endCol;
    @FXML public TableColumn endDateCol;
    @FXML public TableColumn endTimeCol;

    @FXML public ComboBox<Contacts> contactDropdown;

    ContactsDAOImpl contact = new ContactsDAOImpl();
    ObservableList<Contacts> contactList = FXCollections.observableArrayList(contact.getAll("contacts"));

    CustomersDAOImpl customer = new CustomersDAOImpl();
    ObservableList<Customers> custList = FXCollections.observableArrayList(customer.getAll("customers"));

    public ByContactReportController() throws SQLException {
    }

    /**
     * Sets the customer dropdown with all current customers from the customers table.
     */
    public void setDropdown(){
        contactDropdown.setItems(contactList);
    }

    /**
     * Populates the table which shows all of the appointments for the above selected customer.
     * @throws SQLException
     */
    public void setByContactTable() throws SQLException {
        ObservableList<Appointments> contactAppointments = FXCollections.observableArrayList(contact.findContactAppointments(contactDropdown.getValue()));
        Main.getContactNameFromId(contactAppointments, contactList);
        Main.getCustomerNameFromId(contactAppointments, custList);

        for(Appointments a: contactAppointments){
            a.setStart_Date(a.getStart().toLocalDate());
            a.setStart_Time(a.getStart().toLocalTime());
            a.setEnd_Date(a.getEnd().toLocalDate());
            a.setEnd_Time(a.getEnd().toLocalTime());
        }


        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("Contact_Name"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("Start_Date"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("Start_Time"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("End_Date"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("End_Time"));

        byCustomerTable.getColumns().setAll(apptIdCol, contactCol, titleCol, typeCol, descriptionCol, startCol, endCol, custCol);
        byCustomerTable.setItems(contactAppointments);
    }

    /**
     * Interface specific method that runs on scene initialization.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDropdown();

    }

    /**
     * Handles button event to send the user back to the main veiw.
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
}
