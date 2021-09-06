package seng202.group7.controllers;

import seng202.group7.CSVDataAccessor;
import seng202.group7.Crime;
import seng202.group7.Report;

import java.io.File;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
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

    /**
     * Stores the current reports (data) being used by the table and maps.
     */
    private ArrayList<Report> reports = new ArrayList<>();

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
     * A getter for the list of reports currently stored.
     *
     * @return reports      The list of reports currently being used.
     */
    public ArrayList<Report> getReports() {
        return reports;
    }

    public static void getFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/test/files"));
        fileChooser.setTitle("Open data file");
        // Limits the types of files to only CSV.
        fileChooser.getExtensionFilters().add(new ExtensionFilter(".csv files", "*.csv"));

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        // Launches the file chooser.
        File selectedFile = fileChooser.showOpenDialog(stage);
        // If the file chooser is exited before a file is selected it will be a NULL value and should not continue.
        if (selectedFile != null) {
            // Uses the CSVDataAccessor class to read the file and get the list of data as an array list of reports.
            ArrayList<Report> reports = CSVDataAccessor.getInstance().read(selectedFile);
            // Uses the singleton class ControllerData which can allow the reports to be store
            // and then retrieved by other controllers.
            ControllerData.getInstance().setReports(reports);

            DataViewController.updateTableContents(reports);
            
            // try {
            //     BorderPane test = (((BorderPane) ((Node) event.getSource()).getScene().getRoot()));
            //     test.getCenter();
            //     TableView test2 = (TableView<Crime>) ((BorderPane) test.getCenter()).getChildren().get(0);
            //     System.out.println(test);
            //     test2.refresh();
            // } catch (Exception e) {
            //     //TODO: handle exception
            //     System.out.println(e);
            // }            
        }
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
