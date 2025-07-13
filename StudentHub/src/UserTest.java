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
public class UserTest {
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
                "'Spring', 2025, 4, 40, '20001', '10001 10002');");

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
    void SearchCourseByParam_Exist() {
        String simulatedInput = "Circuit Theory\nBSEE\nSpring\n2025";    //user input to be simulated
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        User user = new Student("", "", "", 2025, "", "");
        user.SearchCoursebyParam();

        // Get output and assert expected lines
        String output = testOutputStream.toString();

        // Restore original System.out
        System.setOut(origOut);

        // Print the output (for reference and debugging)
        System.out.print(output);

        // Test if we came across the message when running the simulated user input
        assertTrue(output.contains("Department: BSEE Course: Circuit Theory Semester: Spring Year: 2025"));

    }
}
