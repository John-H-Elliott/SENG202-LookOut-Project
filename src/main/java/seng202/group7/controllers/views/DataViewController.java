package seng202.group7.controllers.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import seng202.group7.controllers.data.ControllerData;
import seng202.group7.data.CustomException;
import seng202.group7.data.Report;
import seng202.group7.data.DataAccessor;
import seng202.group7.view.MainScreen;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * The controller, used by / linked to, the Data View FXML file.
 * Handles the generation of the table on initialization.
 *
 * @author Jack McCorkindale
 * @author John Elliott
 */
public class DataViewController implements Initializable {
    /**
     * This is the GridPane that holds the table and is the root node.
     */
    @FXML
    private BorderPane frame;

    /**
     * This is the Table.
     */
    @FXML
    private TableView<Report> tableView;
    /**
     * This is the columns of the table.
     */
    @FXML
    private TableColumn<Report, String> caseCol;
    @FXML
    private TableColumn<Report, String> wardCol;
    @FXML
    private TableColumn<Report, String> descCol;
    @FXML
    private TableColumn<Report, String> dateCol;
    @FXML
    private TableColumn<Report, Boolean> arrestCol;


    /**
     * This method is run during the loading of the data view fxml file.
     * It generates what values will be stored in the columns.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        caseCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        wardCol.setCellValueFactory(new PropertyValueFactory<>("Ward"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("PrimaryDescription"));
        arrestCol.setCellValueFactory(new PropertyValueFactory<>("Arrest"));
        // Sets up a call to firstly create a DateTime pattern and then coverts our local date time stored in class.
        dateCol.setCellValueFactory(setup -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    property.setValue(dateFormat.format(setup.getValue().getDate()));
                    return property;
                });

        // On a double click and the row isn't empty it will trigger the swap view method.
        tableView.setRowFactory( tv -> {
            TableRow<Report> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Report rowData = row.getItem();
                    swapViews(event, rowData);
                }
            });
            return row;
        });

        // Refreshes the table data when the table view is returned to.
        frame.parentProperty().addListener((obs, oldParent, newParent) -> {
            if (newParent != null) {
                newParent.parentProperty().addListener((obs1, oldParent1, pagination) -> {
                    pagination.getParent().parentProperty().addListener((obs2, oldParent2, newParent2) -> {
                        if (newParent2 != null) {
                            setTableContent();
                        }
                    });

                });
            }
        });
        setTableContent();
    }


    /**
     * This method stores that current state of the table and the selected row in the ControllerData and then loads,
     * the detailed data view screen and swaps it for the current raw data view screen.
     *
     * @param event         The double click mouse event trigger.
     * @param rowData       The Crime object from the selected row.
     */
    private void swapViews(MouseEvent event, Report rowData) {
        // This section must come first as the rowData is need when initializing the crimeEdit FXML.
        ControllerData controllerData = ControllerData.getInstance();
        controllerData.setCurrentRow(rowData);
        
        BorderPane rootPane = (BorderPane) frame.getParent().getParent().getParent().getParent();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/entryView.fxml"));
            Node newFrame = loader.load();

            ((EntryController) loader.getController()).setLastFrame(rootPane.getCenter());

            rootPane.setCenter(newFrame);
        } catch (IOException | IllegalStateException e) {
            MainScreen.createErrorWin(new CustomException("Error caused when loading the Entry View screens FXML file.", e.getClass().toString()));
        }
    }


    /**
     * Creates an observable list which is used to store the data that will be displayed in the table.
     */
    private void setTableContent() {
        // Gets the current set of reports based on the pagination's current page.
        try {
            List<Report> reports = DataAccessor.getInstance().getPageSet(ControllerData.getInstance().getCurrentList());
            ObservableList<Report> data = FXCollections.observableList(reports);
            tableView.setItems(data);
        } catch (CustomException e) {
            MainScreen.createWarnWin(e);
        }

    }
}
