# **SENG202**-LookOut-Application

## How to run the .jar file from the command line

---

1.
    Make sure the machine this is being run on has [Java 14][javaWebsite] installed.  
    If problems exist ensure your system is using the correct version.

2.
    In the terminal navigate to the [.jar](LookOut.jar) files location.

3.
    When inside the can project directory. Run the following code, for Windows:

    ```Console
    java -jar LookOut.jar
    ```

    If issues exist instead try running the alternative .jar file from within the same directory:

    ```Console
    java -jar LookOutAlternative.jar
    ```

## How to load source code into IntelliJ IDEA

---

1.
    Navigating to the **["Source Code"](Source&#32;Code/)** folder which should contain the **["src"]("Source Code"/src/)** directory.

2.
    In IntelliJ IDEA click **"Open"** and then find the source code folder location and click **"OK"**.  
    Ensure that you select to trust the maven project if given the option.

## How to run source code in IntelliJ IDEA

---

1.
    Navigate to the **["src/main/java/seng202/group7"]("Source Code"/src/main/java/seng202/group7/)** package.

2.
    Click on the App class. This class starts the application.

3.
    Click the Run button for the App class and the application will start.  
    Note that maven may need to download some information, ensure you wait for this to complete.

## How to build project code in IntelliJ IDEA

---

If there are any issues with building the maven project when running the App.

1.
    Click on the "build project" button (which has a hammer icon) located near the run button.

## How to load source code into eclipse

---

1.
    Navigating to the **"Source Code"** folder which should contain the **"src"** directory.

2.
    In eclipse under **"File"** click **"Import"** and then click **"Maven/Existing Maven Projects"**.

3.
    Add the location of the directory of the source code as the root directory.

4.
    Click finish and make any necessary changes to the configuration of the files.

## How to run source code in eclipse

---

1.
    Navigate to the **"src/main/java/seng202/group7"** package.

2.
    Click on the App class. This class starts the application.

3.
    Click the Run button for the App class and the application will start.

## How to build project code in eclipse:

---

If there are any issues with building the maven project when importing.

1.
    Right click on the project folder **"Source Code"** and select **"Run As"** and then select **"Maven Install"**.

[javaWebsite]: https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html "Java-14 Archive Download"
