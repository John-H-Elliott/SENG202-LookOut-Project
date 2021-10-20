# **SENG202**-LookOut-Application

---

## **Overview**

This project focused on making a crime analysis system that allowed users to import crime data into a database and provided filtering, searching, mapping and graphing features. This was a **Java** based project. Therefore, the GUI used the **JavaFX** module. Other languages used were **SQLite** for connecting to the database and **HTML** which was used in the mapping system. Information on the design process of the application can be found in [Documents](./Documents).

> Note: The API key for the google map has been removed from the [mapview](./Source%Code/src/main/resources/networking/mapView.html) HTML file of the current and past commits.

---

## How to run the .jar file from the command line

1.
    Make sure the machine this is being run on has [Java 14][javaWebsite] installed.  
    If problems exist ensure your system is using the correct version.

2.
    In the terminal navigate to the [.jar](LookOut.jar) files location.

3.
    Once inside the directory. Run the following code, for Linux;

    ```Console
    java -jar LookOut.jar
    ```

    For Window run;

    ```Console
    java -jar LookOutAlternative.jar
    ```

---

## How to load source code into IntelliJ IDEA


1.
    Navigating to the **["Source Code"](Source&#32;Code/)** folder which should contain the **["src"](Source&#32;Code/src/)** directory.

2.
    In IntelliJ IDEA click **"Open"** and then find the source code folder location and click **"OK"**.  
    Ensure that you select to trust the maven project if given the option.

---

## How to run source code in IntelliJ IDEA

1.
    Navigate to the **["src/main/java/seng202/group7"](Source&#32;Code/src/main/java/seng202/group7/)** package.

2.
    Click on the App class. This class starts the application.

3.
    Click the Run button for the App class and the application will start.  
    Note that maven may need to download some information, ensure you wait for this to complete.

---

## How to build project code in IntelliJ IDEA

If there are any issues with building the maven project when running the App.

1.
    Click on the "build project" button (which has a hammer icon) located near the run button.

---

## How to load source code into eclipse

1.
    Navigating to the **["Source Code"](Source&#32;Code/)** folder which should contain the **["src"](Source&#32;Code/src/)** directory.

2.
    In eclipse under **"File"** click **"Import"** and then click **"Maven/Existing Maven Projects"**.

3.
    Add the location of the directory of the source code as the root directory.

4.
    Click finish and make any necessary changes to the configuration of the files.

---

## How to run source code in eclipse

1.
    Navigate to the **["src/main/java/seng202/group7"](Source&#32;Code/src/main/java/seng202/group7/)** package.

2.
    Click on the App class. This class starts the application.

3.
    Click the Run button for the App class and the application will start.

---

## How to build project code in eclipse:

If there are any issues with building the maven project when importing.

1.
    Right click on the project folder **["Source Code"](Source&#32;Code/)** and select **"Run As"** and then select **"Maven Install"**.

[javaWebsite]: https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html "Java-14 Archive Download"
