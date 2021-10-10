package seng202.group7;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng202.group7.analyses.Rank;
import seng202.group7.analyses.Tuple;
import seng202.group7.data.Crime;
import seng202.group7.data.Report;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests ranking reports.
 *
 * @author Sam McMillan
 */
public class RankTest {

    private static List<Report> data;

    /**
     * Creates a list of reports to be used for the ranked tests.
     */
    @BeforeAll
    public static void setup() {
        data = new ArrayList<>();
        LocalDateTime date2 = LocalDateTime.of(2020, 2, 20, 19, 33);
        Crime reportOne = new Crime("1", date2, "073XX S SOUTH SHORE DR", "NA", "THEFT", "NA", null, false, false, 1, 1, "NA", 1183633, 1851786, 41.748486365, -87.602675062);
        Crime reportTwo = new Crime("2", date2, "109XX S WALLACE ST", "NA", "SEX OFFENSE", "NA", null, false, false, 1, 20, "NA", 1148227, 1899678, 41.880660786, -87.731186405);
        Crime reportThree = new Crime("3", date2, "029XX S DR MARTIN LUTHER KING JR DR", "NA", "THEFT", "NA", null, false, false, 1, 10, "NA", 1148227, 1899678, 41.880660786, -87.731186405);
        Crime reportFour = new Crime("4", date2, "064XX S DR MARTIN LUTHER KING JR DR", "NA", "MURDER", "NA", null, false, false, 1, 1, "NA", 1148227, 1899678, 41.880660786, -87.731186405);
        Crime reportFive = new Crime("5", date2, "055XX S DR MARTIN LUTHER KING JR DR", "NA", "THEFT", "NA", null, false, false, 1, 1, "NA", 1148227, 1899678, 41.880660786, -87.731186405);
        data.add(reportOne);
        data.add(reportTwo);
        data.add(reportThree);
        data.add(reportFour);
        data.add(reportFive);
    }

    /**
     * Tests to see the correct crime type(theft) is returned from frequency ranker
     */
    @Test
    public void primaryFrequencyRank() {
        List<Tuple<String, Integer>> list = Rank.primaryFrequencyRank(data);
        assertEquals("THEFT", list.get(list.size() - 1).x);
    }


    /**
     * Tests to see the correct ward (1) is returned as it has the highest amount of crimes in the data set
     */
    @Test
    public void wardFrequencyRank() {
        List<Tuple<String, Integer>> list = Rank.wardFrequencyRank(data);
        assertEquals("1", list.get(list.size() - 1).x);
    }

    /**
     * Tests to see the correct street is returned ("S DR MARTIN LUTHER KING JR DR") is returned as it has the
     * highest crime rate in the data set
     */
    @Test
    public void streetRankTest() {
        List<Tuple<String, Integer>> list = Rank.streetRank(data);
        assertEquals("S DR MARTIN LUTHER KING JR DR", list.get(list.size() - 1).x);
    }
}
