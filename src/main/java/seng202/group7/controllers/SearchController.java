package seng202.group7.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import seng202.group7.data.CustomException;
import seng202.group7.view.MainScreen;
import seng202.group7.data.QueryBuilder;
import java.io.IOException;
import java.util.Objects;

/**
 * This menu allows a user to pick a field to search and then attempts to match
 * text entered by the user to that in the database.
 *
 * @author John Elliott
 * @author Shaylin Simadari
 */
public class SearchController {
    @FXML
    private TextField inputField;
    @FXML
    private Label errorLabel;


    /**
     * Gets the current side panel and replaces it with the general menu panel.
     *
     * @param event             The event action that was triggered.
     */
    public void toMenu(ActionEvent event){
        // As the side panels root is the main border panel we use .getRoot().
        BorderPane pane = (BorderPane) (((Node) event.getSource()).getScene()).getRoot();
        try {
            VBox menuItems = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("/gui/generalMenu.fxml")));
            // Changes side menu to the filter menu.
            pane.setLeft(menuItems);
        } catch (IOException | NullPointerException e) {
            MainScreen.createErrorWin(new CustomException("Error caused when loading the General Menu screens FXML file.", e.getClass().toString()));
        }

    }

    /**
     * This method ensures that the user has typed in something before the search is allowed.
     */
    public boolean validateText() {
        if (inputField.getText() == null) {
            errorLabel.setText("No Input Given");
            return false;
        } else {
            errorLabel.setText("");
            return true;
        }
    }

    /**
     * This method is used to determine the condition that should be applied to when the paginator is initialized
     * and therefore allow a user to search based on the text entered.
     *
     * @param event             The event action that was triggered.
     */
    public void search(KeyEvent event) {
        if(!validateText()){
            return;
        }
        // Determines the condition that will be used.
        String query = QueryBuilder.search(inputField.getText());
        // By setting this where query when the paginator is generated the data accessor will apply it to the search.
//        ControllerData.getInstance().getWhereQuery().startsWith("WHERE")
        ControllerData.getInstance().setSearchQuery(query);
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


}
