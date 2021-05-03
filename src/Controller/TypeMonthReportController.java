package Controller;

import DAO.AppointmentsDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TypeMonthReportController implements Initializable {
    @FXML public TableView typeMonthTable;
    @FXML public TableColumn monthColumn;
    @FXML public TableColumn typeColumn;
    @FXML public TableColumn countColumn;

    /**
     * Sets the table for the appointments sorted by month and type of appointment.
     */
    public void setTypeMonthTable() {
        AppointmentsDAOImpl appts = new AppointmentsDAOImpl();
        ObservableList typeMonthList = null;
        try {
            typeMonthList = FXCollections.observableArrayList(appts.getTypeMonthResults());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));

        typeMonthTable.getColumns().setAll(monthColumn, typeColumn, countColumn);
        typeMonthTable.setItems(typeMonthList);
    }

    /**
     * Button event handler to send the user back to the main view.
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
     * Interface specific method that runs on scene initialization.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTypeMonthTable();

    }
}
