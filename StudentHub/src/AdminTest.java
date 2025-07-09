import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    private InputStream originalSystemIn;
    private static final String url = "jdbc:sqlite::memory:";
    private static Connection conn;

    @BeforeEach
    void setup() throws Exception {
        originalSystemIn = System.in;   //stores original System.in
        conn = DriverManager.getConnection(url);
        try (Statement statement = conn.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS COURSE ("
                     + "CRN INTEGER PRIMARY KEY,"
                     + " TITLE txt NOT NULL,"
                     + " DEPARTMENT txt NOT NULL,"
                     + " TIME INTEGER NOT NULL,"
                     + " DAYS txt NOT NULL,"
                     + " SEMESTERS txt NOT NULL,"
                     + " YEAR INTEGER NOT NULL,"
                     + " CREDITS INTEGER NOT NULL,"
                     + " SEATS INTEGER NOT NULL"
                     + ");");
            statement.executeUpdate("DELETE FROM COURSE");
        }


    }

    @AfterEach
    void takeDown() throws Exception {
        System.setIn(originalSystemIn); //restores original System.in
        if (conn != null) {
            conn.close();
        }
    }

    @Test
    void CreateCourse() throws SQLException {
        SqlExecuter.OpenDatabase(url);

        String simulatedInput = "1\nTest Cases 101\n2\nBSCO\n3\n77777\n4\n12301315\n5\n2\n4\n0\n6\n2\n0\n7\n2025\n8\n3\n9\n20\n0\n";    //user input to be simulated
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array

        Admin tester = new Admin("", "", "", "", "", "");

        //Connection conn = DriverManager.getConnection(url);
        Statement statement = conn.createStatement();
        tester.CreateCourse();

        //statement.executeUpdate("INSERT INTO COURSE VALUES (77777, 'tEST CASE 101', 'BSCO', 17381738, 'Monday Wednesday', 'Summer', 2025, 3, 20);");

        ResultSet rs = statement.executeQuery("SELECT CRN, TITLE, DEPARTMENT, TIME, DAYS, SEMESTERS, YEAR, CREDITS, SEATS FROM COURSE WHERE CRN = 77777");
        //assertTrue(rs.next());
        System.out.println(rs.getInt("CRN"));
        System.out.println(rs.getString("TITLE"));
        System.out.println(rs.getString("DEPARTMENT"));
        System.out.println(rs.getInt("TIME"));
        System.out.println(rs.getString("DAYS"));
        System.out.println(rs.getString("SEMESTERS"));
        System.out.println(rs.getInt("YEAR"));
        System.out.println(rs.getInt("CREDITS"));
        System.out.println(rs.getInt("SEATS"));

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
    void DeleteCourse() {
    }
}