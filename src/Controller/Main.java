package Controller;

import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

import static Utility.DBConnection.startConnection;
import static Utility.DBConnection.closeConnection;

public class Main extends Application {

    public static Stage window;

    /**
     * Sets the initial stage and scene.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../Views/LoginView.fxml"));
        window.setTitle("Log In to Schedule");
        window.setScene(new Scene(root, 600, 400));
        window.show();
    }

    /**
     * Establishes the database connection and launches the app. Upon exit, it closes the database connection.
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        startConnection();
        launch(args);
        closeConnection();
    }

    /**
     * Method used throughout the app to get the contact name from the id.
     * @param apptList
     * @param contactList
     */
    public static void getContactNameFromId(ObservableList<Appointments> apptList, ObservableList<Contacts> contactList) {
        for(Appointments a: apptList){
            for(Contacts c: contactList){
                if(a.getContact_ID() == c.getContact_ID()){
                    a.setContact_Name(c.getContact_Name());
                }
            }
        }
    }

    /**
     * Method used throughout the app to get the customer name from the id.
     * @param apptList
     * @param custList
     */
    public static void getCustomerNameFromId(ObservableList<Appointments> apptList, ObservableList<Customers> custList) {
        for(Appointments a: apptList){
            for(Customers c: custList){
                if(a.getCustomer_ID() == c.getCustomer_ID()){
                    a.setCustomer_Name(c.getCustomer_Name());
                }
            }
        }
    }



}
