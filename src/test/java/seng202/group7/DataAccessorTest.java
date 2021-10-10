package seng202.group7;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group7.data.Crime;
import seng202.group7.data.CustomException;
import seng202.group7.data.DataAccessor;
import seng202.group7.data.Report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Tests the features of importing and getting data using the data accessor.
 *
 * @author John Elliott
 */
public class DataAccessorTest {

    public static DataAccessor accessor = DataAccessor.getInstance();

    /**
     * Changes the connection to the test database.
     */
    @BeforeAll
    public static void changeConnection() {
        // Changes the connection to the testDatabase.db
        try {
            accessor.changeConnection("src/test/files/TestDatabase.db");
        } catch (CustomException e) {
            System.err.println("Change connection: " + e.getMessage());
        }
    }

    /**
     * Adds the single test crime to the database and ensures no other entries exist.
     */
    @BeforeEach
    public void createNewTestDatabase() {
        try {
            Statement stmt = accessor.getConnection().createStatement();
            stmt.execute("DELETE FROM crimes");
            stmt.execute("DELETE FROM reports");
            stmt.execute("DELETE FROM lists");
            stmt.execute("INSERT INTO lists(id, name) VALUES(1, 'testList')");
            stmt.close();

            Crime crimeOne = new Crime("TestNumber", LocalDateTime.now(), null, null, "test", "test", null, null, null, null, null, null, null, null, null, null);
            
            accessor.editCrime(crimeOne, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CustomException e) {
            e.printStackTrace();
        }

    }

    /**
     * Checks that the size of the database is correct.
     */
    @Test
    public void checkSize() {
        try {
            assertEquals(accessor.getSize(1), 1);
        } catch (CustomException e) {
            fail();
        }
    }

    /**
     * Adds a entry and test deleting it.
     */
    @Test
    public void deleteTest() {
        try {
            Crime crimeTwo = new Crime("TestToDelete", LocalDateTime.now(), null, null, "test", "test", null, null, null, null, null, null, null, null, null, null);
            accessor.editCrime(crimeTwo, 1);
            accessor.deleteReport("TestToDelete", 1);
            Crime crime = accessor.getCrime("TestToDelete", 1);
            assertNull(crime);
        } catch (CustomException e) {
            fail();
        }
    }

    /**
     * Reads a test CSV and checks it adds all data correctly.
     */
    @Test
    public void readToDBTest() {
        try {
            accessor.importFile(new File("src/test/files/testCSV.csv"), 1, "REPLACE", true);
        } catch (CustomException e) {
            if (!e.getMessage().contains("complete")) {
                fail("Database failed to read csv file");
            }
        }
        try {
            Crime crime = accessor.getCrime("JE163990", 1);
            assertEquals(crime.getId(), "JE163990");
        } catch (CustomException e) {
            fail("CSV file read incorrectly.");
        }
    }

    /**
     * Reads another database into the testing database and checks it adds the data correctly.
     */
    @Test
    public void importDBTest() {
        try {
            accessor.importFile(new File("src/test/files/TestImporting.db"), 1, "REPLACE", true);
            Crime crime = accessor.getCrime("TestNumber", 1);
            assertEquals(crime.getId(), "TestNumber");
        } catch (CustomException e) {
           fail();
        }
    }

    @Test
    public void getListIdTest() {
        try {
            assertEquals(1, accessor.getListId("testList"));
        } catch (CustomException e) {
            fail();
        }
    }

    /**
     * Checks that the page set get the correctly set of reports.
     */
    @Test
    public void pageSetTest() {
        try {
            List<Report> reports = accessor.getPageSet(1);
            assertEquals(((Crime) reports.get(0)).getId(), "TestNumber");
        } catch (CustomException e) {
            fail();
        }
    }

}
