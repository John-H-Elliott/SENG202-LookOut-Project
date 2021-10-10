package seng202.group7.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import seng202.group7.data.CustomException;
import seng202.group7.data.QueryBuilder;
import seng202.group7.view.MainScreen;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group7.data.FilterConditions;
import seng202.group7.data.Serializer;

/**
 * Controller class. Linked to filter menu FXML.
 * Handles initialization of filter type boxes.
 * Links GUI to filter class.
 *
 * @author Sami Elmadani
 * @author Shaylin Simadari
 * @author John Elliott
 */
public class FilterController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private DatePicker datePicker2;

    @FXML
    private ComboBox<String> primaryBox;

    @FXML
    private ComboBox<String> locationBox;

    @FXML
    private TextField wardField;

    @FXML
    private TextField beatField;

    @FXML
    private ComboBox<String> arrestBox;

    @FXML
    private ComboBox<String> domesticBox;

    private static FilterConditions filterConditions;

    private ArrayList<Node> allValues;

    /**
     * This method is run during the loading of the data view fxml file.
     * It generates what values will be stored in the columns.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        primaryBox.setItems(FXCollections.observableArrayList(
                null, "THEFT", "ASSAULT", "DECEPTIVE PRACTICE", "BATTERY", "HOMICIDE", "OTHER OFFENSE",
                "CRIMINAL DAMAGE", "WEAPONS VIOLATION", "CRIMINAL TRESPASS", "ARSON", "MOTOR VEHICLE THEFT",
                "ROBBERY", "STALKING", "BURGLARY", "OFFENSE INVOLVING CHILDREN", "SEX OFFENSE", "CRIMINAL SEXUAL ASSAULT",
                "NARCOTICS", "PUBLIC PEACE VIOLATION", "PROSTITUTION", "INTERFERENCE WITH PUBLIC OFFICER",
                "INTIMIDATION", "CONCEALED CARRY LICENSE VIOLATION", "KIDNAPPING", "LIQUOR LAW VIOLATION",
                "OTHER NARCOTIC VIOLATION").sorted());

        locationBox.setItems(FXCollections.observableArrayList(
                null, "APARTMENT", "STREET", "SIDEWALK", "PARK PROPERTY", "RESIDENCE", "DRUG STORE",
                "DEPARTMENT STORE", "PARKING LOT / GARAGE (NON RESIDENTIAL)", "ALLEY", "RESIDENCE - PORCH / HALLWAY",
                "SMALL RETAIL STORE", "AUTO", "RESTAURANT", "GROCERY FOOD STORE", "CTA PLATFORM", "GAS STATION",
                "POLICE FACILITY / VEHICLE PARKING LOT", "TAXICAB", "RESIDENCE - YARD (FRONT / BACK)",
                "CONVENIENCE STORE", "GOVERNMENT BUILDING / PROPERTY", "OTHER (SPECIFY)", "CTA TRAIN",
                "SCHOOL - PRIVATE GROUNDS", "LAKEFRONT / WATERFRONT / RIVERBANK", "DRIVEWAY - RESIDENTIAL",
                "VEHICLE NON-COMMERCIAL", "HOSPITAL BUILDING / GROUNDS", "AUTO / BOAT / RV DEALERSHIP",
                "SCHOOL - PRIVATE BUILDING", "BAR OR TAVERN", "OTHER COMMERCIAL TRANSPORTATION", "CTA BUS STOP",
                "RESIDENCE - GARAGE", "SCHOOL - PUBLIC BUILDING", "COMMERCIAL / BUSINESS OFFICE", "VEHICLE - COMMERCIAL",
                "CHA PARKING LOT / GROUNDS", "MEDICAL / DENTAL OFFICE", "NURSING / RETIREMENT HOME",
                "ABANDONED BUILDING", "CLEANING STORE", "APPLIANCE STORE", "CHURCH / SYNAGOGUE / PLACE OF WORSHIP",
                "ATM (AUTOMATIC TELLER MACHINE)", "BANK", "HOTEL / MOTEL",
                "VEHICLE - OTHER RIDE SHARE SERVICE (LYFT, UBER, ETC.)", "CAR WASH",
                "COLLEGE / UNIVERSITY - RESIDENCE HALL", "JAIL / LOCK-UP FACILITY",
                "OTHER RAILROAD PROPERTY / TRAIN DEPOT", "CHA APARTMENT", "TAVERN / LIQUOR STORE", "CONSTRUCTION SITE",
                "CTA STATION", "PARKING LOT", "AIRPORT PARKING LOT", "AIRPORT TERMINAL UPPER LEVEL - SECURE AREA",
                "CTA PARKING LOT / GARAGE / OTHER PROPERTY", "ATHLETIC CLUB", "CHA HALLWAY / STAIRWELL / ELEVATOR",
                "CTA BUS", "BOWLING ALLEY", "AIRPORT TERMINAL LOWER LEVEL - NON-SECURE AREA", "VACANT LOT / LAND",
                "AIRPORT BUILDING NON-TERMINAL - NON-SECURE AREA", "BARBERSHOP", "BRIDGE", "COLLEGE / UNIVERSITY - GROUNDS",
                "VACANT LOT", "COIN OPERATED MACHINE", "LIBRARY", "CEMETERY", "CURRENCY EXCHANGE", "MOVIE HOUSE / THEATER",
                "HIGHWAY / EXPRESSWAY", "FACTORY / MANUFACTURING BUILDING", "POOL ROOM",
                "AIRPORT EXTERIOR - NON-SECURE AREA", "ANIMAL HOSPITAL", "PAWN SHOP",
                "AIRPORT BUILDING NON-TERMINAL - SECURE AREA", "AIRCRAFT", "WAREHOUSE",
                "AIRPORT TERMINAL UPPER LEVEL - NON-SECURE AREA", "BOAT / WATERCRAFT", "PORCH", "SCHOOL - PUBLIC GROUNDS",
                "HOUSE", "DAY CARE CENTER", "AIRPORT VENDING ESTABLISHMENT", "SPORTS ARENA / STADIUM").sorted());

        arrestBox.getItems().addAll(null, "Y", "N");

        domesticBox.getItems().addAll(null, "Y", "N");

        prepareValidation();

        loadGUIFields();
    }

    /**
     * Sets the types of validation required on each input node
     */ // TODO add validation for datepicker2
    private void prepareValidation() {
        TextField dateText = datePicker.getEditor();
        dateText.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try {
                    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate date = LocalDate.parse(dateText.getText(), dateTimeFormat);
                    datePicker.setValue(date);
                } catch (DateTimeParseException e) {
                    return;
                } finally {
                    activeValidate(event);
                }
            }
        });

        allValues = new ArrayList<>(Arrays.asList(datePicker, dateText, primaryBox, locationBox, wardField, beatField,
            arrestBox, domesticBox));
        
        datePicker.valueProperty().addListener((observable, oldDate, newDate)->{
            if(datePicker.getValue() == null){
                return;
            }
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
            dateText.setText(datePicker.getValue().format(dateTimeFormat));
            InputValidator.validate(dateText);
        });

        InputValidator.addValidation(dateText, InputType.DATE);
        InputValidator.addValidation(wardField, InputType.INTEGER);
        InputValidator.addValidation(beatField, InputType.INTEGER);
    }

    /**
     * When a key is pressed on a node with this set, send the node to validation
     * @param event The keyboard event trigger.
     */
    public void activeValidate(KeyEvent event) {
        Node inputBox = (Node) event.getSource();
        InputValidator.validate(inputBox);
    }

    /**
     * Loads a filter from a file
     * @param event   The event action that was triggered.
     */
    public void loadFilter(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.setTitle("Select filter");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("filters", "*.ser"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            filterConditions = Serializer.deserialize(file);
            loadGUIFields();
        }
    }

    /**
     * clears the current filter fields and clears filter from table
     * @param event   The event action that was triggered.
     */
    public void clearFilter(ActionEvent event) {
        datePicker.setValue(null);
        datePicker2.setValue(null);
        primaryBox.setValue(null);
        locationBox.setValue(null);
        wardField.setText("");
        beatField.setText("");
        arrestBox.setValue(null);
        domesticBox.setValue(null);
        applyFilter(event);
    }

    /**
     * Saves the current filter to a file
     * @param event   The event action that was triggered.
     */
    public void saveFilter(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.setTitle("Save filter");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("filters", "*.ser"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            saveGUIFields();
            Serializer.serialize(file, filterConditions);
        }
    }

    /**
     * Applies the current filter values to the table
     * @param event   The event action that was triggered.
     */
    public void applyFilter(ActionEvent event){
        for (Node node : allValues) {
            if (!InputValidator.validate(node)) {
                return;
            }
        }
        saveGUIFields();
        String query = QueryBuilder.where(filterConditions);

        // By setting this where query when the paginator is generated the data accessor will apply it to the search.
        ControllerData.getInstance().setFilterQuery(query);
        // As the side panels root is the main border panel we use .getRoot().
        BorderPane pane = (BorderPane) (((Node) event.getSource()).getScene()).getRoot();

        if (pane.getCenter().getId().equals("mapViewPane")) {
            try {
                // Changes side menu to the filter menu.
                StackPane mapView = ControllerData.getInstance().getGoogleMap();
                pane.setCenter(mapView);
                //reLoad pins.
                WebView map = (WebView) mapView.getChildren().get(0);
                MapController.updatePins(map);
            } catch (NullPointerException e) {
                MainScreen.createErrorWin(new CustomException("Error caused when loading the Map View screens FXML file.", e.getClass().toString()));
            }
        } else {
            try {
                BorderPane tableView = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/pages.fxml")));
                // Changes side menu to the filter menu.
                pane.setCenter(tableView);
            } catch (IOException | NullPointerException e) {
                MainScreen.createErrorWin(new CustomException("Error caused when loading the Pagination screens FXML file.", e.getClass().toString()));
            }
        }
    }

    /**
     * Clears the date stored in the date picker.
     */
    public void clearDate(){
        datePicker.setValue(null);
    }

    /**
     * Clears the date stored in the date picker 2.
     */
    public void clearDate2(){
        datePicker2.setValue(null);
    }

    /**
     * Returns the user to the general menu
     * Gets the current side panel and replaces it with the general menu panel.
     * @param event             The event action that was triggered.
     */
    public void toMenu(ActionEvent event) {
        saveGUIFields();
        // As the side panels root is the main border panel we use .getRoot().
        try {
            BorderPane pane = (BorderPane) (((Node) event.getSource()).getScene()).getRoot();
            VBox menuItems = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/generalMenu.fxml")));
            // Changes side menu to the filter menu.
            pane.setLeft(menuItems);
        } catch (IOException | NullPointerException e) {
            MainScreen.createErrorWin(new CustomException("Error caused when loading the General Menu screens FXML file.", e.getClass().toString()));
        }

    }

    private void loadGUIFields(){
        if(filterConditions == null){
            return;
        }
        datePicker.setValue(filterConditions.getDateFrom());
        datePicker2.setValue(filterConditions.getDateTo());
        primaryBox.setValue(filterConditions.getPrimaryDescription());
        locationBox.setValue(filterConditions.getLocationDescription());
        wardField.setText(filterConditions.getWard() == null ? "" : filterConditions.getWard().toString());
        beatField.setText(filterConditions.getBeat() == null ? "" : filterConditions.getBeat().toString());
        arrestBox.setValue(filterConditions.getArrest() == null ? null : filterConditions.getArrest() ? "Y" : "N");
        domesticBox.setValue(filterConditions.getDomestic() == null ? null : filterConditions.getDomestic() ? "Y" : "N");
    }

    private void saveGUIFields(){
        filterConditions = new FilterConditions(
                datePicker.getValue(),
                datePicker2.getValue(),
                primaryBox.getValue(),
                locationBox.getValue(),
                getIntegerFromString(wardField.getText()),
                getIntegerFromString(beatField.getText()),
                getBooleanFromString(arrestBox.getValue()),
                getBooleanFromString(domesticBox.getValue())
        );
    }

    /**
     * Gets the integer value from the string and ensures if the string is empty it returns a null value.
     */
    private Integer getIntegerFromString(String str) {
        return str.equals("") ? null : Integer.parseInt(str);
    }

    /**
     * Determines if a value in the combobox corresponds to a true or false value.
     */
    private Boolean getBooleanFromString(String str) {
        return str == null ? null : str.equals("Y");
    }
}
