package seng202.group7.controllers;

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
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import seng202.group7.data.Crime;
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
import java.util.Objects;
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
    private TableView<Crime> tableView;


    /**
     * This method is run during the loading of the data view fxml file.
     * It generates what values will be stored in the columns.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> possibleColumns = Arrays.asList("Case Number,CaseNumber", "Date,date",
            "Primary Description,PrimaryDescription","Secondary Description,SecondaryDescription", "Domestic,Domestic",
            "X Coordinate,XCoord", "Y Coordinate,YCoord", "Latitude,Latitude","Longitude,Longitude",
            "Location Description,LocationDescription", "Block,Block", "Iucr,Iucr", "FBI CD,FbiCD", "Arrest,arrest", 
            "Beat,Beat", "Ward,Ward");

        ContextMenu contextMenu = new ContextMenu();
        
        for (String columnName : possibleColumns) {
            TableColumn<Crime, ?> newColumn = createColumn(columnName, contextMenu);
            tableView.getColumns().add(newColumn);
        }
         
        tableView.setContextMenu(contextMenu);

        // On a double click and the row isn't empty it will trigger the swap view method.
        tableView.setRowFactory( tv -> {
            TableRow<Crime> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Crime rowData = row.getItem();
                    swapViews(event, rowData);
                }
            });
            return row;
        });
        setTableContent();
    }

    /**
     * Creates a new column with the given name and adds it to the tables context menu.
     * @param columnName The name of the column
     * @param contextMenu The tables context menu
     * @return The new column
     */
    private TableColumn<Crime, ?> createColumn(String columnName, ContextMenu contextMenu) {
        
        String[] columnData = columnName.split(",");
        TableColumn<Crime, String> newColumn = new TableColumn<>(columnData[0]);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(columnData[1]));
        
        if (columnData[0].equals("Date")) {
            newColumn.setCellValueFactory(setup -> {
                SimpleStringProperty property = new SimpleStringProperty();
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                property.setValue(dateFormat.format(setup.getValue().getDate()));
                return property;
            });
        }
        MenuItem columnMenu = new MenuItem(columnData[0]);
        // Make it so when clicked will hide/show the column
        columnMenu.setOnAction(event -> {
            visibilityToggleInit(event);
        });

        contextMenu.getItems().add(columnMenu);
        
        if (ControllerData.getInstance().getVisibleColumns() == null) {
            ControllerData.getInstance().setVisibleColumns(
                Arrays.asList("Case Number", "Date", "Primary Description","Arrest", "Ward")
                );
        }
        List<String> visibleColumns = ControllerData.getInstance().getVisibleColumns();
        // Only show default columns
        if (!visibleColumns.contains(columnData[0])) {
            newColumn.setVisible(false);
        }
        return newColumn;
    }

    /**
     * Makes the menuItem toggle the visibility of the relevant column.
     * @param event The event that triggers this method.
     */
    private void visibilityToggleInit(ActionEvent event) {
        for (TableColumn<Crime, ?> col : tableView.getColumns()) {
            if (col.getText().equals(((MenuItem) event.getSource()).getText())) {
                col.setVisible(!col.visibleProperty().get());
                List<String> visibleColumns = ControllerData.getInstance().getVisibleColumns();
                if (visibleColumns.contains(col.getText())){
                    visibleColumns.remove(col.getText());
                } else {
                    visibleColumns.add(col.getText());
                }
                return;
            }
        }
    }


    /**
     * This method stores that current state of the table and the selected row in the ControllerData and then loads,
     * the detailed data view screen and swaps it for the current raw data view screen.
     *
     * @param event         The double click mouse event trigger.
     * @param rowData       The Crime object from the selected row.
     */
    private void swapViews(MouseEvent event, Crime rowData) {
        // This section must come first as the rowData is need when initializing the crimeEdit FXML.
        ControllerData controllerData = ControllerData.getInstance();
        controllerData.setCurrentRow(rowData);
        
        Pagination page = (Pagination) frame.getParent().getParent();

        // Changes page to force a refresh of the data in the table
        int currentPage = controllerData.getCurrentPage();
        if (currentPage == 0) {
            page.setCurrentPageIndex(currentPage + 1);
        } else {
            page.setCurrentPageIndex(currentPage - 1);
        }
        controllerData.setCurrentPage(currentPage);

        Node table = page.getParent();
        try {
            BorderPane detailView = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/entryView.fxml")));
            // Changes center screen to the crime edit.
            ((BorderPane) table.getParent()).setCenter(detailView);
            controllerData.setTableState(table);
            
        } catch (IOException | NullPointerException e) {
            MainScreen.createErrorWin(new CustomException("Error caused when loading the Entry View screens FXML file.", e.getClass().toString()));
        }
    }


    /**
     * Creates an observable list which is used to store the data that will be displayed in the table.
     */
    private void setTableContent() {
        // Gets the current set of reports based on the pagination's current page.
        ArrayList<Report> reports = DataAccessor.getInstance().getPageSet(ControllerData.getInstance().getCurrentList());
        ObservableList<Crime> data = FXCollections.observableArrayList();
        // As the reports can be either a crime or an incident we must check the object type and cast them appropriately.
        for (Report report : reports) {
            data.add((Crime) report);
        }
        tableView.setItems(data);
    }
}
