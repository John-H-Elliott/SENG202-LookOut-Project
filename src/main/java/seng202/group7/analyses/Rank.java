package seng202.group7.analyses;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import seng202.group7.data.Report;
import seng202.group7.data.Crime;


/**
 * A set of static methods that ranks and analyses data into lists for graphing view
 *
 * @author Sam McMillan
 * @author Shaylin Simadari
 */
public final class Rank {

    private Rank() {}

    /**
     * Parses the data and finds the highest to the lowest frequency of Report type (primary) in the whole data set.
     *
     * @param data  The selected data to be analysed for frequencies.
     * @return      A list of tuples (string(primary description), int (frequency)), sorted from lowest to highest
     */
    public static List<Tuple<String, Integer>> primaryFrequencyRank(List<Report> data) {
        HashMap<String, Integer> map = new HashMap<>();

        for(Report report : data){
            String primary = report.getPrimaryDescription();
            map.put(primary, map.getOrDefault(primary, 0) + 1);
        }
        return hashToList(map);
    }

    /**
     * Parses the data and finds the wards with the highest frequency of Report over the dataset.
     *
     * @param data  The selected data to be analysed for frequencies
     * @return      A list of tuples (Int(Ward), int (frequency)), sorted from lowest to highest
     */
    public static List<Tuple<String, Integer>> wardFrequencyRank(List<Report> data) {
        HashMap<String, Integer> map = new HashMap<>();

        for(Report report : data){
            if (((Crime) report).getWard() != null) {
                String ward = String.valueOf(((Crime) report).getWard());
                map.put(ward, map.getOrDefault(ward, 0) + 1);
            }
        }
        return hashToList(map);
    }

    /**
     * Parses the data and finds the beats with the highest frequency of crime over the dataset.
     * @param data The selected data to be analysed for frequencies
     * @return  A list of tuples (Int(Ward), int (frequency)), sorted from lowest to highest
     */
    public static List<Tuple<String, Integer>> beatFrequencyRank(List<Report> data) {
        HashMap<String, Integer> map = new HashMap<>();

        for(Report report: data) {
            if (((Crime) report).getBeat() != null) {
                String beat = String.valueOf(((Crime) report).getBeat());
                map.put(beat, map.getOrDefault(beat, 0) + 1);
            }
        }
        return hashToList(map);
    }

    /**
     * Parses the data and finds the highest to the lowest frequency of Report in an area (Most dangerous Street).
     *
     * @param data  The selected data to be analysed for frequencies
     * @return      A list of tuples (string(address), int (frequency)), sorted from lowest to highest
     */
    public static List<Tuple<String, Integer>> streetRank(List<Report> data) {
        HashMap<String, Integer> map = new HashMap<>();

        for(Report report : data){
            if (((Crime) report).getBlock() != null) {
                if (((Crime) report).getBlock().length() >= 6) {
                    String address = ((Crime) report).getBlock().substring(6);
                    map.put(address, map.getOrDefault(address, 0) + 1);
                }
            }
        }
        return hashToList(map);
    }

    /**
     * Parses the data and gets the frequency of crime within each month from the start and end date of the data set
     * @param data  The selected data to be analysed
     * @return A list of crime frequency arranged in chronological order
     */
    public static List<CrimeFrequency> crimeOverTime(List<Report> data) {
        List<CrimeFrequency>  crimeOverTime = getDateList(data);
        boolean crimeMatch;
        int index = 0;
        for (CrimeFrequency freq: crimeOverTime){
            crimeMatch = true;
            while (crimeMatch) {
                LocalDateTime date = data.get(index).getDate();
                String dateString = date.getMonthValue() + " " + date.getYear();
                if(dateString.equals(freq.getDate())) {
                    freq.setFrequency(freq.getFrequency() + 1);
                    index += 1;
                    if (index == data.size()) {
                        crimeMatch = false;
                    }
                } else {
                    crimeMatch = false;
                }
            }
        }
        return crimeOverTime;
    }

    /**
     * Used by Crime over time, populates a list of crime frequency for every month from the earliest to the latest date value in the data
     * @param data The selected data to be analysed
     * @return A list of crime frequency values with the frequency(int) set to 0, arranged in chronological order
     */
    public static List<CrimeFrequency> getDateList(List<Report> data) {
        ArrayList<CrimeFrequency>  crimeOverTime = new ArrayList<>();
        if (data.size() == 0) { //Checks if the query returned an empty list of data
            return crimeOverTime;
        }
        int yearValue = data.get(0).getDate().getYear();
        int monthValue = data.get(0).getDate().getMonthValue();
        int lastYear = data.get(data.size() - 1).getDate().getYear();
        int lastMonth = data.get(data.size() -1).getDate().getMonthValue();
        boolean lastValueNotFound = true;
        while(lastValueNotFound) {
            if (yearValue == lastYear && monthValue == lastMonth) {
                lastValueNotFound = false;
            }
            CrimeFrequency freq = new CrimeFrequency(monthValue + " " + yearValue, 0);
            crimeOverTime.add(freq);
            monthValue += 1;
            if (monthValue == 13) {
                monthValue = 1;
                yearValue += 1;
            }
        }
        return crimeOverTime;
    }

    /**
     * Helper function for block, primary, ward and beat frequency rank, converts a hash table into a sorted list of tuples.
     *
     * @param hashMap   A hash map of String key and int values corresponding to frequency of occurrence in the data.
     * @return          A list of tuples (String, int), sorted from highest to lowest frequency
     */
    public static  List<Tuple<String, Integer>> hashToList(HashMap<String, Integer> hashMap) {
        List<Tuple<String, Integer>> list = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            list.add(new Tuple<>(entry.getKey(), entry.getValue()));
        }
        list.sort((a, b) -> a.y - (int) b.y);
        return list;
    }
}
