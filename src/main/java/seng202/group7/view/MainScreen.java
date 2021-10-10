package seng202.group7.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seng202.group7.controllers.ControllerData;
import seng202.group7.controllers.MenuController;
import seng202.group7.data.CustomException;
import seng202.group7.data.DataAccessor;

import javafx.scene.image.Image;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Initializes the GUI stage and loads the first FXML scene.
 *
 * @author Jack McCorkindale
 * @author John Elliott
 * @author Sam McMillan
 * @author Sami Elmadani
 * @author Shaylin Simadari
 */
public class MainScreen extends Application {

    private static Stage window;
    /**
     * Loads the first FXML file and sets it to the current scene for the stage.
     *
     * @param windowStage      The stage that the scene will be load onto.
     */
    @Override
    public void start(Stage windowStage) {
        window = windowStage;
        windowStage.setTitle("LookOut");
        windowStage.getIcons().add(new Image("/gui/logo.png"));
        // Loads first FXML scene. Checks to ensure that the file is not NULL.
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/startScreen.fxml")));
            Scene scene = new Scene(view);
            windowStage.setScene(scene);
            loadGoogleAPIs();
            windowStage.show();
        } catch (IOException | NullPointerException e) {
            createErrorWin(new CustomException("Error caused when loading the Start screens FXML file.", e.getClass().toString()));
        }
    }

    /**
     * Pre-Loads all the Google APIs and stores them in the controller data class to prevent
     * slow loading and re-loading. Also allows for the user to keep searches stored even when not focused on it.
     */
    private void loadGoogleAPIs() {
        ControllerData connData = ControllerData.getInstance();
        try {
            StackPane mapView = FXMLLoader.load(Objects.requireNonNull(MainScreen.class.getResource("/gui/mapView.fxml")));
            connData.setGoogleMap(mapView);
        } catch (IOException | NullPointerException e) {
            createErrorWin(new CustomException("Error caused when loading the Map View screens FXML file.", e.getClass().toString()));
        }
        WebView externalSearch = new WebView();
        externalSearch.getEngine().load("https://cse.google.com/cse?cx=59f99af6c7b75d889");
        connData.setGoogleSearch(externalSearch);
    }

    /**
     * As connection is made at the start of the application this method ensures,
     * that the database is closed at the end of the application.
     */
    @Override
    public void stop() {
        try {
            DataAccessor.getInstance().getConnection().close();
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }

    /**
     * Creates an error window that alerts the user to the problem and closes the application to avoid
     * errors with the database/other system from occurring.
     *
     * @param cause     The exception that was thrown.
     */
    public static void createErrorWin(CustomException cause) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Message: " + cause.getMessage());
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle(cause.getInfo());
        alert.setOnHidden(event -> window.close());
        alert.show();
    }

    /**
     * Creates a Warning window that alerts the user to the problem and continues the application.
     *
     * @param cause     The exception that was thrown.
     */
    public static void createWarnWin(CustomException cause) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Message: " + cause.getMessage());
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle(cause.getInfo());
        alert.show();
    }

    /**
     * Launches the application with the provided arguments passed through.
     * Uses the launch method which is inherited from this class's parent Application.
     *
     * @param args      The arguments given when running the compiled source code.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
