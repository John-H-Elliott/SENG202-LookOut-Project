package seng202.group7.analyses;

import seng202.group7.data.Report;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.lang.Math;


/**
 * This class adds a set of static methods that allow reports to be compared to each other.
 *
 *  @author Sam McMillan
 */
public final class Comparer {

    /**
     * Compares two reports and returns the time difference.
     *
     * @param reportOne     Data to be compared with "reportTwo"
     * @param reportTwo     Data to be compared with "reportOne"
     * @return              A list of long values [Minutes, Hours, Days, Years]
     */
    public static ArrayList<Long> timeDifference(Report reportOne, Report reportTwo) {
        ArrayList<Long> timeDifferences = new ArrayList<>();
        Duration difference = Duration.between(reportTwo.getDate(), reportOne.getDate()); //Returns total time in seconds
        timeDifferences.add(Math.abs(TimeUnit.SECONDS.toMinutes(difference.getSeconds()) % 60));
        timeDifferences.add(Math.abs(TimeUnit.SECONDS.toHours(difference.getSeconds()) % 24));
        timeDifferences.add(Math.abs(TimeUnit.SECONDS.toDays(difference.getSeconds()) % 365));
        timeDifferences.add(Math.abs(TimeUnit.SECONDS.toDays(difference.getSeconds()) / 365));
        return timeDifferences;
    }

    /**
     * Returns the displacement between the two reports in km.
     *
     * @param reportOne data to be compared with "reportTwo"
     * @param reportTwo data to be compared with "reportOne"
     * @return A double value corresponding the displacement between the two reports in km
     */

    public static double locationDifference(Report reportOne, Report reportTwo) {
        double lat1 = 0;
        double lat2 = 0;
        double lon1 = 0;
        double lon2 = 0;
        try {
            lat1 = reportOne.getLatitude();
            lat2 = reportTwo.getLatitude();
            lon1 = reportOne.getLongitude();
            lon2 = reportTwo.getLongitude();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344; //Converting to kilometres
        return (dist);
    }
}
