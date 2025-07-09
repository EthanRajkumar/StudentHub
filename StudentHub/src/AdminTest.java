import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    private InputStream originalSystemIn;
    private static final String url = "jdbc:sqlite::memory:";

    @BeforeEach
    void setup() throws Exception {
        originalSystemIn = System.in;   //stores original System.in
        try (Connection conn = DriverManager.getConnection(url); Statement statement = conn.createStatement()) {
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
            statement.execute("DELETE FROM COURSE");
        }
    }

    @AfterEach
    void takeDown() throws Exception {
        System.setIn(originalSystemIn); //restores original System.in
    }

    @Test
    void CreateCourse() throws SQLException {

        String simulatedInput = "1\nTest Cases 101\n2\nBSCO\n3\n77777\n4\n12301315\n5\n2\n4\n0\n6\n2\n0\n7\n2025\n8\n3\n9\n20\n0\n";    //user input to be simulated
        ByteArrayInputStream testInputStream = new ByteArrayInputStream(simulatedInput.getBytes());  //user input in the form of a byte array
        System.setIn(testInputStream);  //set System.in as byte array
        Scanner reader = new Scanner(System.in);

        Admin tester = new Admin("", "", "", "", "", "");
        tester.CreateCourse();


    }

    @Test
    void DeleteCourse() {
    }
}