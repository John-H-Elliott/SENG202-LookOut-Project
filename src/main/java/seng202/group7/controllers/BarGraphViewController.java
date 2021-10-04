package seng202.group7.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import seng202.group7.analyses.CrimeFrequency;
import seng202.group7.analyses.Rank;
import seng202.group7.analyses.Tuple;
import seng202.group7.data.DataAccessor;
import seng202.group7.data.Report;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * Graph View Controller controls displays three different bar graphs based on user input from Graph Menu Controller.
 *
 * @author Jack McCorkindale
 * @author Sam McMillan
 */
public class BarGraphViewController implements Initializable {

    @FXML
    private BarChart<String, Integer> crimeChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Node frame;

    /**
     * This method is run during the loading of the graph menu fxml file.
     *
     * @param location      A URL object.
     * @param resources     A ResourceBundle object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Called from the Graph View controller when the scene is initialized, checks what input is currently
     * in the combo box from graph menu class with pseudo classes and loads the correct graph accordingly.
     *
     */

    public void prepareBarGraph(String option) {
        ArrayList<Report> data = DataAccessor.getInstance().getAll();
        ArrayList<Tuple<String, Integer>> dataSet;
        if (option.equals("Most Frequent Crime Types")) {
            dataSet = Rank.primaryFrequencyRank(data);
            Collections.reverse(dataSet);
            generateBarGraph(dataSet, "Most Frequent Types of Crime", "Crime Types", "Frequency of Crime");
        } else if (option.equals("Most Dangerous Wards")) {
            dataSet = Rank.wardFrequencyRank(data);
            Collections.reverse(dataSet);
            generateBarGraph(dataSet, "Most Dangerous Wards", "Ward", "Frequency of Crime");
        } else if (option.equals("Most Dangerous Streets")) {
            dataSet = Rank.streetRank(data);
            Collections.reverse(dataSet);
            generateBarGraph(dataSet, "Most Dangerous Streets", "Street", "Frequency of Crime");
        } else if (option.equals("Crime Over Time")) {
            ArrayList<Report> sortedData = DataAccessor.getInstance().getAllSortedByDate();
            ArrayList<CrimeFrequency> crimeOverTime = Rank.crimeOverTime(sortedData);
        }
    }


    /**
     *Create the graph by inputting the values into the bar chart object.
     *
     * @param data      The arraylist of String integer tuples from Rank class
     * @param title     The String title of the graph
     * @param xLabel    The x axis label of the graph
     * @param yLabel    The y axis label of the graph
     */

    public void generateBarGraph(ArrayList<Tuple<String, Integer>> data, String title, String xLabel, String yLabel) {
        XYChart.Series<String, Integer> dataSet = new XYChart.Series<>();
        int i = 0;
        for (Tuple<String, Integer> tuple: data) {
            i++;
            if (i == 20) {
                break;
            }
            dataSet.getData().add(new XYChart.Data<> (String.valueOf(tuple.x), tuple.y));
        }
        this.crimeChart.setLegendVisible(false);
        this.crimeChart.getData().addAll(dataSet);
        this.crimeChart.setTitle(title);
        this.xAxis.setLabel(xLabel);
        this.yAxis.setLabel(yLabel);
    }
}