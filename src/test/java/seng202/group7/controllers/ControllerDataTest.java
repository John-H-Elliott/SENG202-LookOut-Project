package seng202.group7.controllers;

import org.junit.jupiter.api.BeforeAll;
import seng202.group7.controllers.data.ControllerData;


public class ControllerDataTest {

    @BeforeAll
    public static void makeReports() {
        ControllerData controllerData = ControllerData.getInstance();
    }

    
}
