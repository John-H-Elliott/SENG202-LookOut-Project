package seng202.group7.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seng202.group7.controllers.data.ControllerData;


public class ControllerDataTest {

    private static ControllerData controllerData;

    @BeforeAll
    public static void makeReports() {
        controllerData = ControllerData.getInstance();
    }

    @Test
    public void getWhereQuery_blank() {
        assertEquals("", controllerData.getWhereQuery());
    }

    @Test
    public void getWhereQuery_searchPopulated() {
        controllerData.setSearchQuery("id=");
    }
    
}
