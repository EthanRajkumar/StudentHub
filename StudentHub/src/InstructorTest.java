import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteConnection;

//import static org.mockito.Mockito.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


public class InstructorTest {

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

        // Populate a fresh INSTRUCTOR table
        SqlExecuter.RunUpdate(url, "CREATE TABLE IF NOT EXISTS INSTRUCTOR (ID txt PRIMARY KEY, NAME txt NOT NULL, " +
                "SURNAME txt NOT NULL, TITLE txt NOT NULL, HIREYEAR INTEGER NOT NULL, DEPT txt NOT NULL, " +
                "EMAIL txt NOT NULL);");
        SqlExecuter.RunUpdate(url, "DELETE FROM INSTRUCTOR;");

        // Populate a fresh STUDENT table
        SqlExecuter.RunUpdate(url, "CREATE TABLE IF NOT EXISTS STUDENT (ID txt PRIMARY KEY, NAME txt NOT NULL, " +
                "SURNAME txt NOT NULL, GRADEYEAR INTEGER NOT NULL, MAJOR txt NOT NULL, " +
                "EMAIL txt NOT NULL);");
        SqlExecuter.RunUpdate(url, "DELETE FROM STUDENT;");

        // Populate a fresh COURSE table
        SqlExecuter.RunUpdate(url, "CREATE TABLE IF NOT EXISTS COURSE (CRN INTEGER PRIMARY KEY, TITLE txt NOT NULL, " +
                "DEPARTMENT txt NOT NULL, TIME INTEGER NOT NULL, DAYS txt NOT NULL, SEMESTERS txt NOT NULL, YEAR INTEGER NOT NULL, " +
                "CREDITS INTEGER NOT NULL, SEATS INTEGER NOT NULL, INSTRUCTOR txt, STUDENTS txt);");
        SqlExecuter.RunUpdate(url, "DELETE FROM COURSE;");

        // Insert a sample Instructor
        SqlExecuter.RunUpdate(url, "INSERT INTO INSTRUCTOR VALUES (20001, 'Joseph', 'Fourier', 'Full Prof.', 1820, " +
                "'BSEE', 'fourierj');");

        // Insert sample Students
        SqlExecuter.RunUpdate(url, "INSERT INTO STUDENT VALUES ('10001', 'Ethan', 'Rajkumar', 2020, " +
                "'BSEE', 'rajkumare');");
        SqlExecuter.RunUpdate(url, "INSERT INTO STUDENT VALUES ('10002', 'Chris', 'Le', 2030, " +
                "'BSEE', 'lec5');");

        // Insert a sample course used to detect conflictions
        SqlExecuter.RunUpdate(url, "INSERT INTO COURSE VALUES (50000, 'Circuit Theory', 'BSEE', 9301015, 'Monday, Wednesday, Friday', " +
                "'Spring, Summer', 2025, 4, 40, '20001', '10001 10002');");

        SqlExecuter.RunUpdate(url, "INSERT INTO COURSE VALUES (50005, 'Embedded Systems', 'BSEE', 9301015, 'Monday, Wednesday, Friday', " +
                "'Spring, Summer', 2025, 4, 40, '', '10001 10002');");
    }

    @AfterEach
    void takeDown() {
        System.setIn(originalSystemIn); //restores original System.in
        System.setOut(originalSystemOut); // Restores original System.out
        SqlExecuter.CloseDatabase(); // Closes in-memory database
    }

    @Test
    void PrintCourseRoster_Exists() {
        String simulatedInput = "Circuit Theory\n";    //user input to be simulated
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        Instructor instructor = new Instructor("Joseph", "Fourier", "20001", "Full Prof.", 1820, "BSEE","fourierj"); //Instructor needs values so it knows what instructor is using the functions
        instructor.PrintCourseRoster();

        // Get output and assert expected lines
        String output = testOutputStream.toString();

        // Restore original System.out
        System.setOut(origOut);

        // Print the output (for reference and debugging)
        System.out.print(output);

        // Test if we came across the message when running the simulated user input
        assertTrue(output.contains("Course: Circuit Theory"));
        assertTrue(output.contains("Student: Ethan ID: 10001"));
        assertTrue(output.contains("Student: Chris ID: 10002"));
    }

    @Test
    void PrintCourseRoster_DoesntTeach() {
        String simulatedInput = "Embedded Systems\n";    //user input to be simulated
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        Instructor instructor = new Instructor("Joseph", "Fourier", "20001", "Full Prof.", 1820, "BSEE","fourierj"); //Instructor needs values so it knows what instructor is using the functions
        instructor.PrintCourseRoster();

        // Get output and assert expected lines
        String output = testOutputStream.toString();

        // Restore original System.out
        System.setOut(origOut);

        // Print the output (for reference and debugging)
        System.out.println("Doesn't Teach Test");
        System.out.print(output);

        // Test if we came across the message when running the simulated user input
        assertTrue(output.contains("That course does not exist or you do not teach this course."));
    }

    @Test
    void PrintCourseRoster_NotExist() {
        String simulatedInput = "Non-Existant Course\n";    //user input to be simulated
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        Instructor instructor = new Instructor("Joseph", "Fourier", "20001", "Full Prof.", 1820, "BSEE","fourierj"); //Instructor needs values so it knows what instructor is using the functions
        instructor.PrintCourseRoster();

        // Get output and assert expected lines
        String output = testOutputStream.toString();

        // Restore original System.out
        System.setOut(origOut);

        // Print the output (for reference and debugging)
        System.out.println("Not Exists Test");
        System.out.print(output);

        // Test if we came across the message when running the simulated user input
        assertTrue(output.contains("That course does not exist or you do not teach this course."));
    }

}
