import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteConnection;

import static org.mockito.Mockito.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

//Initial research/setup of Junit, test case class, test case functions, user input simulation, in-memory database initialization by Chris
//Test cases for Login done by Ethan

class LoginTest {

    private InputStream originalSystemIn;
    private ByteArrayInputStream testInputStream;

    private PrintStream originalSystemOut;
    private ByteArrayOutputStream testOutputStream;

    private static final String url = "jdbc:sqlite::memory:";

    @BeforeEach
    void setup() {
        // Keep original System.in and System.out streams
        originalSystemIn = System.in;
        originalSystemOut = System.out;
        // Open an in-memory database
        SqlExecuter.OpenDatabase(url);

        SqlExecuter.RunUpdate(url, "CREATE TABLE IF NOT EXISTS STUDENT (ID txt PRIMARY KEY, NAME txt NOT NULL, SURNAME txt NOT NULL, " +
                "GRADYEAR INTEGER NOT NULL, MAJOR txt NOT NULL, EMAIL txt NOT NULL);");
        SqlExecuter.RunUpdate(url, "DELETE FROM STUDENT;");
        SqlExecuter.RunUpdate(url, "CREATE TABLE IF NOT EXISTS INSTRUCTOR (ID txt PRIMARY KEY, NAME txt NOT NULL, " +
                "SURNAME txt NOT NULL, TITLE txt NOT NULL, HIREYEAR INTEGER NOT NULL, DEPT txt NOT NULL, " +
                "EMAIL txt NOT NULL);");
        SqlExecuter.RunUpdate(url, "DELETE FROM INSTRUCTOR;");
        SqlExecuter.RunUpdate(url, "CREATE TABLE IF NOT EXISTS ADMIN (ID txt PRIMARY KEY, NAME txt NOT NULL, " +
                "SURNAME txt NOT NULL, TITLE txt NOT NULL, OFFICE txt NOT NULL, EMAIL txt NOT NULL);");
        SqlExecuter.RunUpdate(url, "DELETE FROM INSTRUCTOR;");

        Student myStudent = new Student("Ethan", "Rajkumar", "10005", 2026, "BSCO", "rajkumare@wit.edu");
        SqlExecuter.RunUpdate(url, SqlSerializer.StudentToSql(myStudent, "STUDENT"));
        Instructor myInstructor = new Instructor("Eric", "Chandler", "50001", "Professor", 2022, "BSCO", "chandlere@wit.edu");
        SqlExecuter.RunUpdate(url, SqlSerializer.InstructorToSql(myInstructor, "INSTRUCTOR"));
        Admin myAdmin = new Admin("Chris", "Le", "80002", "Registrar", "Registrar", "lec5@wit.edu");
        SqlExecuter.RunUpdate(url, SqlSerializer.AdminToSql(myAdmin, "Admin"));
        Debug.ResetPasswords();
    }

    @AfterEach
    void takeDown() {
        System.setIn(originalSystemIn); //restores original System.in
        System.setOut(originalSystemOut); // Restores original System.out
        SqlExecuter.CloseDatabase(); // Closes in-memory database
    }

    @Test
    void Login_InvalidUsername() throws SQLException {
        String simulatedInput = "ethanrajkumar@harvard.edu\nrajkumare@wit.edu\nrajkumare@wit.edu\nrajkumare@wit.edu\n0\n";    //user input to be simulated
        testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        TextInterface.Start();

        // Restore original System.out
        System.setOut(origOut);
        // Compile the output into a readable string
        String lines = testOutputStream.toString();
        // Print the output (for reference and debugging)
        System.out.println(lines);

        assertEquals(true, lines.contains("Invalid login information. Please try again."));
    }

    @Test
    void Login_InvalidPassword() throws SQLException {
        String simulatedInput = "rajkumare@wit.edu\nethanrajkumar@harvard.edu\nrajkumare@wit.edu\nrajkumare@wit.edu\n0\n";    //user input to be simulated
        testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        TextInterface.Start();
        // Restore original System.out
        System.setOut(origOut);
        // Compile the output into a readable string
        String lines = testOutputStream.toString();
        // Print the output (for reference and debugging)
        System.out.println(lines);

        assertEquals(true, lines.contains("Invalid login information. Please try again."));
    }

    @Test
    void Login_Success_Student() throws SQLException {
        String simulatedInput = "rajkumare@wit.edu\nrajkumare@wit.edu\n0\n";    //user input to be simulated
        testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        TextInterface.Start();
        // Restore original System.out
        System.setOut(origOut);
        // Compile the output into a readable string
        String lines = testOutputStream.toString();
        // Print the output (for reference and debugging)
        System.out.println(lines);

        assertEquals(true, lines.contains("Signing in..."));
        assertEquals(true, lines.contains("student"));
    }

    @Test
    void Login_Success_Instructor() throws SQLException {
        String simulatedInput = "chandlere@wit.edu\nchandlere@wit.edu\n0\n";    //user input to be simulated
        testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        TextInterface.Start();
        // Restore original System.out
        System.setOut(origOut);
        // Compile the output into a readable string
        String lines = testOutputStream.toString();
        // Print the output (for reference and debugging)
        System.out.println(lines);

        assertEquals(true, lines.contains("Signing in..."));
        assertEquals(true, lines.contains("instructor"));
    }

    @Test
    void Login_Success_Admin() throws SQLException {
        String simulatedInput = "lec5@wit.edu\nlec5@wit.edu\n0\n";    //user input to be simulated
        testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        TextInterface.Start();
        // Restore original System.out
        System.setOut(origOut);
        // Compile the output into a readable string
        String lines = testOutputStream.toString();
        // Print the output (for reference and debugging)
        System.out.println(lines);

        assertEquals(true, lines.contains("Signing in..."));
        assertEquals(true, lines.contains("admin"));
    }
}