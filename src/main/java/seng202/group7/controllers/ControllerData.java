package seng202.group7.controllers;


import javafx.scene.layout.GridPane;
import seng202.group7.CSVDataAccessor;
import seng202.group7.Crime;
import seng202.group7.Report;
import seng202.group7.SQLiteAccessor;

import java.io.File;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class acts as a connection between the controllers passing data through here so that only the controller's
 * initialization it can get the relevant data it needs. It accomplishes this by being a singleton class so only one
 * instance of it can exist.
 *
 * @author John Elliott
 */
public final class ControllerData {
    /**
     * This creates a singleton instants of the class.
     */
    private final static ControllerData INSTANCE = new ControllerData();

    private Crime currentRow;

    /**
     * Stores the current reports (data) being used by the table and maps.
     */
    private ArrayList<Report> reports = new ArrayList<>();

    /**
     * Stores the current table state so when the back button is pushed the position is the same.
     */
    private GridPane tableState;


    /**
     * The constructor which is made private so that it can not be initialized from other classes.
     */
    private ControllerData() {}

    /**
     * Used to get the singleton instants of the class when assessing. This is done over a "static" class due to it
     * implementing an interface.
     *
     * @return INSTANCE     The only instants of this class.
     */
    public static ControllerData getInstance() {
        return INSTANCE;
    }

    /**
     * Setter for storing a crime object assigned to a selected row.
     *
     * @param rowData       The selected crime object.
     */
    public void setCurrentRow(Crime rowData) {
        currentRow = rowData;
    }

    /**
     * Getter for retrieving a crime object assigned to a selected row.
     *
     * @return currentRow       The stored selected crime object.
     */
    public Crime getCurrentRow() {
        return currentRow;
    }

    /**
     * Setter for storing the current state of the table.
     *
     * @param tableView       The table state.
     */
    public void setTableState(GridPane tableView) {
        tableState = tableView;
    }

    /**
     * Getter for retrieving the current state of the table.
     *
     * @return tableState       The current state of the table.
     */
    public GridPane getTableState() {
        return tableState;
    }

    /**
     * A getter for the list of reports currently stored.
     *
     * @return reports      The list of reports currently being used.
     */
    public ArrayList<Report> getReports() {
        return reports;
    }


    //TODO Im not sure this belongs in this class it might belong in the DataViewController class.
    /**
     * Makes a screen to get a file from a user using the FilerChooser class.
     * Then it sends this files data to the data access layer and returns the data that need to be stored.
     *
     * @param event     The event action that was triggered.
     */
    public void getFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/test/files"));
        fileChooser.setTitle("Select file");
        fileChooser.getExtensionFilters().add(new ExtensionFilter(".csv, .db files", "*.csv", "*.db"));

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        // Launches the file chooser.
        File selectedFile = fileChooser.showOpenDialog(stage);
        // If the file chooser is exited before a file is selected it will be a NULL value and should not continue.
        if (selectedFile != null) {
            ArrayList<Report> reports;
            String fileName = selectedFile.getName();
            if (fileName.substring(fileName.length()-3).equals("csv")) {
                // Uses the CSVDataAccessor class to read the file and get the list of data as an array list of reports.
                reports = CSVDataAccessor.getInstance().read(selectedFile);
                chooseDBDirectory(stage, reports);
            } else {
                reports = SQLiteAccessor.getInstance().read(selectedFile);
            }
            
            // Uses the singleton class ControllerData which can allow the reports to be store
            // and then retrieved by other controllers.
            ControllerData.getInstance().setReports(reports);
            DataViewController.updateTableContents(reports);
        }
    }

        /**
     * Launches a fileChooser to choose directory to create a DB in
     * @param stage stage to show the filechooser on
     * @param reports report to write to the DB
     * @return reports in the DB
     */
    private ArrayList<Report> chooseDBDirectory(Stage stage, ArrayList<Report> reports) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/test/files"));
        fileChooser.setTitle("Create .sqlite file");
        fileChooser.getExtensionFilters().add(new ExtensionFilter(".db files", "*.db"));
        File selectedFile = fileChooser.showSaveDialog(stage);
        if(selectedFile != null) {
            reports = convertCSVtoDB(reports, selectedFile);
        }
        return reports;
    }

        /**
     * creates a new database and populates it
     * @param reports reports to write to database
     * @param selectedFile location to create the database in
     * @return reports in the database
     */
    private ArrayList<Report> convertCSVtoDB(ArrayList<Report> reports, File selectedFile) {
        SQLiteAccessor.getInstance().connect(selectedFile);
        SQLiteAccessor.getInstance().create(selectedFile);
        SQLiteAccessor.getInstance().write(reports, selectedFile);
        reports = SQLiteAccessor.getInstance().read(selectedFile);
        return reports;
    }
    
    /**
     * A setter for the list of reports.
     *
     * @param reports       A list of new reports.
     */
    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }


}
