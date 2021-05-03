Application Title: Scheduler App.
Purpose: A application that allows a streamlined approach to keeping track of appointments and customers.
        The application utilizes a database for storing all applicable data, and JDBC for interfacing with the data.

Author: Aiden Mead
        **REDACTED PERSONAL INFORMATION**

Version: 3.0.1
Date: 3/4/2021

IDE Used: IntelliJ IDEA Ultimate Edition 2020.2
JDK-11.0.10
JavaFX-sdk-11.0.2

    ------------------------------------------ Running the Program -----------------------------------------------------


    SchedulerC195 is the root folder of the project. Import the project into IntelliJ. The database driver is included
    under within the lib folder - mysql-connector-java-8.0.22. Build and run the application.
    Once launched, the application will open on the main login screen. There are two users who can be used -
        Username: test | Password: test
        Username: admin | Password: admin


    ----------------------------------------- Main Screen Navigation ---------------------------------------------------

    Once logged in, the Main Appointments page will show. This will allow the user to view all of the appointments. A date
    can be chosen using the date picker in the upper left corner. If the "View All" filter is selected, all of the appointments
    will still be visible. But if "View by Month" or "View by Week" is selected, then the date chosen from the date picker
    will display the applicable dates. For "View by Month", it will show all appointments for the same month as the date
    chosen. For the "View by Week", it will show all appointments within that week, showing in a Sunday-Saturday format,
    surrounding the chosen date.

    In the upper right hand corner, the user can select to manage either appointments or customer. The two pages function
    similarly, but specific to each type. Once the user selects which to manage, they will be taken to the applicable page,
    where they will see a table of the existing appointments or customer.  **NOTE - selecting an appointment on the main
    view will NOT populate it in the management screen. **


    -------------------------------------- Using the management screens ------------------------------------------------

    To Add a New Appointment/Customer:
    The form is blank upon landing on the management screen. The user can fill in the blank form and hit "Save" to create
    a new appointment.

    To Edit and Existing Appointment/Customer:
    On the management page, the user can select an appointment/customer from the table to edit. Selecting the row in the
    will automatically populate the data in the form to be edited, and a notice at the bottom of the screen will display
    to make it clear that an appointment/customer is being edited. The user can alter the populated information and click
    "Save" to save the changes.

    Switch from Edit to New:
    If the user has selected an appointment/customer to edit, and realizes that they do not want to make changes, but
    would rather create a new appointment/customer instead,  the user can use the "Clear Selected to create a new
    appointment/customer" button. This will reset the form back to blank and allow new data to be put in.


    ----------------------------------------------------- Reports ------------------------------------------------------

    At the bottom of the main screen are several buttons to view reports. The first button is for a report which will show
    appointments which are sorted by the type and month of the appointment. The second button will take the user to a report
    which allows the user to select a contact from the organization, and display the corresponding appointments for that
    contact. And the third will take the user to a report which will show how many of each type of appointment exist based
    on the linked customer's country.

    When the user is finished with the application, the window "X" button can be used to close the application.


Third report: Mentioned above, the third report shows the number of each type of appointments based on the country of
    the customer which is linked to that appointment.
