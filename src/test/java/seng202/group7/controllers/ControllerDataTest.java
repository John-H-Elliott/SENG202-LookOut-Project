package seng202.group7.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seng202.group7.controllers.data.ControllerData;


public class ControllerDataTest {

    @BeforeAll
    public static void makeReports() {
        ControllerData controllerData = ControllerData.getInstance();
    }

    /**
     * Tests timeDifference with a specific case to check if returns the correct value for minutes.
     */
    @Test
    public void timeDifferenceTest_specificCase() {
        
    }
    
}
