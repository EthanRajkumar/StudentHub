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

class StudentTest {

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

        /*SqlExecuter.RunUpdate(url, "CREATE TABLE IF NOT EXISTS STUDENT (ID txt PRIMARY KEY, NAME txt NOT NULL, SURNAME txt NOT NULL, " +
                "GRADYEAR INTEGER NOT NULL, MAJOR txt NOT NULL, EMAIL txt NOT NULL);");
        SqlExecuter.RunUpdate(url, "DELETE FROM STUDENT;");
        SqlExecuter.RunUpdate(url, "INSERT INTO STUDENT VALUES ");*/
        SqlExecuter.RunUpdate(url, "CREATE TABLE IF NOT EXISTS COURSE (CRN INTEGER PRIMARY KEY, TITLE txt NOT NULL, " +
                "DEPARTMENT txt NOT NULL, TIME INTEGER NOT NULL, DAYS txt NOT NULL, SEMESTERS txt NOT NULL, YEAR INTEGER NOT NULL, " +
                "CREDITS INTEGER NOT NULL, SEATS INTEGER NOT NULL, INSTRUCTOR txt, STUDENTS txt);");
        SqlExecuter.RunUpdate(url, "DELETE FROM COURSE;");
        // Insert sample courses used to detect conflictions
        SqlExecuter.RunUpdate(url, "INSERT INTO COURSE VALUES (50001, 'Circuit Theory', 'BSEE', 9301015, 'Monday, Wednesday, Friday', " +
                "'Spring, Summer', 2025, 4, 40, '', '10100');");
        SqlExecuter.RunUpdate(url, "INSERT INTO COURSE VALUES (50002, 'Circuit Theory 2', 'BSEE', 9301015, 'Monday, Wednesday, Friday', " +
                "'Spring, Summer', 2025, 4, 20, '', '');");
        SqlExecuter.RunUpdate(url, "INSERT INTO COURSE VALUES (50003, 'Circuit Theory 3', 'BSEE', 12301415, 'Monday, Wednesday, Friday', " +
                "'Spring, Summer', 2025, 4, 10, '', '10001 10002 10003 10004 10005 10006 10007 10008 10009 10010');");
        SqlExecuter.RunUpdate(url, "INSERT INTO COURSE VALUES (50004, 'Computer Networks', 'BSEE', 15001645, 'Monday, Wednesday, Friday', " +
                "'Spring, Summer', 2025, 4, 10, '', '');");
    }

    @AfterEach
    void takeDown() {
        System.setIn(originalSystemIn); //restores original System.in
        System.setOut(originalSystemOut); // Restores original System.out
        SqlExecuter.CloseDatabase(); // Closes in-memory database
    }

    @Test
    void AddCourse_AlreadyRegistered() throws SQLException {
        String simulatedInput = "Circuit Theory\n";    //user input to be simulated
        testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        Student tester = new Student("Matthew", "Spillane", "10100", 2026, "BSCO", "spillanem@wit.edu");

        boolean result = tester.AddClass();

        // Restore original System.out
        System.setOut(origOut);
        // Compile the output into a readable string
        String lines = testOutputStream.toString();
        // Print the output (for reference and debugging)
        System.out.println(lines);

        assertEquals(false, result);
        assertEquals(true, lines.contains("You are already enrolled in this course."));
    }

    @Test
    void AddCourse_TimeConflict() throws SQLException {
        String simulatedInput = "Circuit Theory 2\n";    //user input to be simulated
        testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        Student tester = new Student("Matthew", "Spillane", "10100", 2026, "BSCO", "spillanem@wit.edu");

        boolean result = tester.AddClass();

        // Restore original System.out
        System.setOut(origOut);
        // Compile the output into a readable string
        String lines = testOutputStream.toString();
        // Print the output (for reference and debugging)
        System.out.println(lines);

        assertEquals(false, result);
        assertEquals(true, lines.contains("Time conflict detected."));
    }

    @Test
    void AddCourse_CourseFull() throws SQLException {
        String simulatedInput = "Circuit Theory 3\n";    //user input to be simulated
        testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        Student tester = new Student("Matthew", "Spillane", "10100", 2026, "BSCO", "spillanem@wit.edu");

        boolean result = tester.AddClass();

        // Restore original System.out
        System.setOut(origOut);
        // Compile the output into a readable string
        String lines = testOutputStream.toString();
        // Print the output (for reference and debugging)
        System.out.println(lines);

        ResultSet rs = SqlExecuter.RunQuery(url, "SELECT * FROM COURSE WHERE TITLE = 'Circuit Theory 3';");
        assertEquals(false, rs.getString("STUDENTS").contains(tester.GetID()));
        assertEquals(true, lines.contains("This course is currently full!"));
        assertEquals(false, result);
    }

    @Test
    void AddCourse_Nonexistent() throws SQLException {
        String simulatedInput = "Circuit Theory 4\n";    //user input to be simulated
        testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        Student tester = new Student("Matthew", "Spillane", "10100", 2026, "BSCO", "spillanem@wit.edu");

        boolean result = tester.AddClass();

        // Restore original System.out
        System.setOut(origOut);
        // Compile the output into a readable string
        String lines = testOutputStream.toString();
        // Print the output (for reference and debugging)
        System.out.println(lines);

        assertEquals(false, result);
        assertEquals(true, lines.contains("Invalid course ID detected."));
    }

    @Test
    void AddCourse_Success() throws SQLException {
        String simulatedInput = "Computer Networks\n";    //user input to be simulated
        testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        Student tester = new Student("Matthew", "Spillane", "10100", 2026, "BSCO", "spillanem@wit.edu");

        boolean result = tester.AddClass();

        // Restore original System.out
        System.setOut(origOut);
        // Compile the output into a readable string
        String lines = testOutputStream.toString();
        // Print the output (for reference and debugging)
        System.out.println(lines);

        assertEquals(true, result);
        assertEquals(true, lines.contains("Matthew Spillane has signed up for course Computer Networks."));
    }

    @Test
    void RemoveCourse_NotRegistered() throws SQLException {
        String simulatedInput = "Circuit Theory 2\n";    //user input to be simulated
        testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        Student tester = new Student("Matthew", "Spillane", "10100", 2026, "BSCO", "spillanem@wit.edu");

        boolean result = tester.RemoveClass();

        // Restore original System.out
        System.setOut(origOut);
        // Compile the output into a readable string
        String lines = testOutputStream.toString();
        // Print the output (for reference and debugging)
        System.out.println(lines);

        ResultSet rs = SqlExecuter.RunQuery(url, "SELECT * FROM COURSE WHERE TITLE = 'Circuit Theory 2';");

        assertEquals(false, rs.getString("STUDENTS").contains(tester.GetID()));
        assertEquals(false, result);
        assertEquals(true, lines.contains("You are not currently enrolled in this course."));
    }

    @Test
    void RemoveCourse_Nonexistent() throws SQLException {
        String simulatedInput = "Circuit Theory 4\n";    //user input to be simulated
        testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        Student tester = new Student("Matthew", "Spillane", "10100", 2026, "BSCO", "spillanem@wit.edu");

        boolean result = tester.RemoveClass();

        // Restore original System.out
        System.setOut(origOut);
        // Compile the output into a readable string
        String lines = testOutputStream.toString();
        // Print the output (for reference and debugging)
        System.out.println(lines);

        assertEquals(false, result);
        assertEquals(true, lines.contains("Invalid course ID detected."));
    }

    @Test
    void RemoveCourse_Success() throws SQLException {
        String simulatedInput = "Circuit Theory\n";    //user input to be simulated
        testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        Student tester = new Student("Matthew", "Spillane", "10100", 2026, "BSCO", "spillanem@wit.edu");

        boolean result = tester.RemoveClass();

        // Restore original System.out
        System.setOut(origOut);
        // Compile the output into a readable string
        String lines = testOutputStream.toString();
        // Print the output (for reference and debugging)
        System.out.println(lines);

        ResultSet rs = SqlExecuter.RunQuery(url, "SELECT * FROM COURSE WHERE TITLE = 'Circuit Theory';");

        assertEquals(false, rs.getString("STUDENTS").contains(tester.GetID()));
        assertEquals(true, result);
        assertEquals(true, lines.contains("Matthew Spillane has unregistered for course Circuit Theory"));
    }
}