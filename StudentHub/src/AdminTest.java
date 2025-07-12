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

class AdminTest {

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

        // Populate a fresh COURSE table (we don't need the others for admin)
        SqlExecuter.RunUpdate(url, "CREATE TABLE IF NOT EXISTS COURSE (CRN INTEGER PRIMARY KEY, TITLE txt NOT NULL, " +
                "DEPARTMENT txt NOT NULL, TIME INTEGER NOT NULL, DAYS txt NOT NULL, SEMESTERS txt NOT NULL, YEAR INTEGER NOT NULL, " +
                "CREDITS INTEGER NOT NULL, SEATS INTEGER NOT NULL, INSTRUCTOR txt, STUDENTS txt);");
        SqlExecuter.RunUpdate(url, "DELETE FROM COURSE;");
        // Insert a sample course used to detect conflictions
        SqlExecuter.RunUpdate(url, "INSERT INTO COURSE VALUES (50000, 'Circuit Theory', 'BSEE', 9301015, 'Monday, Wednesday, Friday', " +
                "'Spring, Summer', 2025, 4, 40, '', '');");
    }

    @AfterEach
    void takeDown() {
        System.setIn(originalSystemIn); //restores original System.in
        System.setOut(originalSystemOut); // Restores original System.out
        SqlExecuter.CloseDatabase(); // Closes in-memory database
    }

    @Test
    void CreateCourse_NotExists() throws SQLException {
        String simulatedInput = "1\nTest Cases 101\n2\nBSCO\n3\n77777\n4\n12301315\n5\n2\n4\n0\n6\n2\n0\n7\n2025\n8\n3\n9\n20\n0\n";    //user input to be simulated
        testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Attempt a test given our simulated input
        Admin tester = new Admin("", "", "", "", "", "");

        tester.CreateCourse();

        // Query our in-memory database for the course we 'should' have inserted
        ResultSet rs = SqlExecuter.RunQuery(url, "SELECT * FROM COURSE WHERE CRN = 77777;");

        // Assert the course's information was formatter and displayed correctly
        assertEquals(77777, rs.getInt("CRN"));
        assertEquals("Test Cases 101", rs.getString("TITLE"));
        assertEquals("BSCO", rs.getString("DEPARTMENT"));
        assertEquals(12301315, rs.getInt("TIME"));
        assertEquals("Monday Wednesday", rs.getString("DAYS"));
        assertEquals("Summer", rs.getString("SEMESTERS"));
        assertEquals(2025, rs.getInt("YEAR"));
        assertEquals(3, rs.getInt("CREDITS"));
        assertEquals(20, rs.getInt("SEATS"));
    }

    @Test
    void CreateCourse_Exists() throws SQLException {
        String simulatedInput = "1\nTest Cases 101\n2\nBSCO\n3\n50000\n4\n12301315\n5\n2\n4\n0\n6\n2\n0\n7\n2025\n8\n3\n9\n20\n0\n";    //user input to be simulated
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        Admin tester = new Admin("", "", "", "", "", "");

        tester.CreateCourse();

        // Restore original System.out
        System.setOut(origOut);
        // Compile the output into a readable string
        String lines = testOutputStream.toString();
        // Print the output (for reference and debugging)
        System.out.println(lines);

        // The error message we're looking for
        String errorMsg = "[SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: COURSE.CRN)";

        // Test if we came across the error message when running the simulated user input
        assertEquals(true, lines.contains(errorMsg));
    }

    @Test
    void CreateCourse_Incomplete() throws SQLException {
        String simulatedInput = "1\nTest Cases 101\n2\nBSCO\n3\n77777\n4\n12301315\n5\n2\n4\n0\n0\n6\n2\n0\n7\n2025\n8\n3\n9\n20\n0\n";    //user input to be simulated
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        // Create an output stream we can read programmatically
        ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
        PrintStream origOut = System.out;
        System.setOut(new PrintStream(testOutputStream));

        // Attempt a test given our simulated input
        Admin tester = new Admin("", "", "", "", "", "");

        tester.CreateCourse();

        // Restore original System.out
        System.setOut(origOut);
        // Compile the output into a readable string
        String lines = testOutputStream.toString();
        // Print the output (for reference and debugging)
        System.out.println(lines);

        // The message we're looking for
        String errorMsg = "Not all attributes of the course were set; please go back and finish";

        // Test if we came across the message when running the simulated user input
        assertEquals(true, lines.contains(errorMsg));
    }

    @Test
    void DeleteCourse() {
    }
}