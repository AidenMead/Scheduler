package Controller;

import DAO.UsersDAOImpl;
import Model.Appointments;
import Model.Users;
import Utility.DateTimeUtility;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

import static Utility.DateTimeUtility.zoneId;


public class LoginController implements Initializable {
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label Login;
    @FXML private Button loginSubmitBtn;
    @FXML private Label prezoneLabel;
    @FXML private Label zoneIdLabel;
    private String userLang = Locale.getDefault().getLanguage();
    private Alert userAlert = new Alert(Alert.AlertType.ERROR);
    private Alert passAlert = new Alert(Alert.AlertType.ERROR);

    /**
     * A ResourceBundle is only needed when the language is not English. Getting the
     * bundle too early will result in an error (because there is no resource bundle
     * for English, so it is better to only get the bundle when we actually need it,
     * which is after we know the language ia not English.
     * @return
     */
    public ResourceBundle getResourceBundle() {
        ResourceBundle rb = ResourceBundle.getBundle("Utility/Lang", Locale.getDefault());

        return rb;
    }

    /**
     * When called, will log any string passed as the parameter. Called in the submitLogin button event handler
     * to log the user data that is attempting to log in.
     * @param userData
     * @throws IOException
     */
    public void logLoginAttempt(String userData) throws IOException {
        FileWriter filename = new FileWriter("login_activity.txt", true);
        PrintWriter print = new PrintWriter(filename);

        print.println(userData);
        print.close();

    }

    /**
     * Sets the language and language values based on the default system language (set to userLang above).
     */
    public void setLang() {
        if(!userLang.equals("en")) {
            ResourceBundle rb = getResourceBundle();

            if (userLang.equals("fr")) {
                Login.setText(rb.getString("login"));
                loginSubmitBtn.setText(rb.getString("submit"));
                username.setPromptText(rb.getString("username"));
                password.setPromptText(rb.getString("password"));
                prezoneLabel.setText(rb.getString("zone"));
            }
        }
    }

    /**
     * Sets the label to display the user's zone information.
     */
    public void setZone() {
        zoneIdLabel.setText(zoneId.toString());
    }

    /**
     * Displays an error if the DB doesn't return a user.
     * @param currentUser - this is the Users object returned from the DB
     * @return
     */
    public boolean validateUser(Users currentUser) {
        if(currentUser.getUser_Name() == null) {
            if (userLang.equals("fr")) {
                ResourceBundle rb = getResourceBundle();
                userAlert.setContentText(rb.getString("userError"));
            } else {
                userAlert.setContentText("User not found.");
            }
            userAlert.show();
            return false;
        }
        return true;
    }

    /**
     * Checks if the username entered matches the username entered, and if so, checks if the associated password
     * is the correct one for that username. If it is not, it returns an error.
     * @param currentUser
     * @param user
     * @param pass
     * @return
     */
    public boolean validatePassword(Users currentUser, String user, String pass){
        if (currentUser.getUser_Name().equals(user)){
            if (currentUser.getPassword().equals(pass)) {
                return true;
            } else {
                if (userLang.equals("fr")) {
                    ResourceBundle rb = getResourceBundle();
                    passAlert.setContentText(rb.getString("passwordError"));
                } else {
                    passAlert.setContentText("Password is incorrect.");
                }
                passAlert.show();
            }
        }
        return false;
    }

    /**
     * Handles the submit button event, where user data is validated and the user is passed on to the main view.
     * Also initiates the login log.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void submitLogin(ActionEvent actionEvent) throws IOException, SQLException {
        String user = username.getText();
        String pass = password.getText();

        UsersDAOImpl DbUsername = new UsersDAOImpl();

        ObservableList<Users> signOnUser = DbUsername.selectOne("User_Name = '" + user + "'");
        Users currentUser = signOnUser.get(0);

        String userData = "Username entered: " + user + " | Password entered: " + pass + " | Attempted On: " + LocalDateTime.now().withNano(0).withSecond(0) + "EST";

        if(validateUser(currentUser)) {
            if(validatePassword(currentUser, user, pass)){
                logLoginAttempt(userData + " | Login Successful!");
                DbUsername.setCurrentUser(currentUser);

                Parent root = FXMLLoader.load(getClass().getResource("../Views/MainView.fxml"));
                Main.window.setTitle("Appointments");
                Main.window.setScene(new Scene(root, 1200, 800));
                Main.window.centerOnScreen();
                Main.window.show();
                MainViewController.apptSoon.show();

                return; //exit function to prevent printing Login Failed message
            }
        }
        logLoginAttempt(userData + " | Login Failed!");
    }

    /**
     * Interface specifc method that runs on scene initialization.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLang();
        setZone();
    }
}
