import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void connect() {
        // connection string
        var url = "jdbc:sqlite:Data/assignment3.db";

        try (var conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement()) {
            System.out.println("Connection to SQLite has been established.");
            ResultSet rs = statement.executeQuery("SELECT * FROM STUDENT WHERE (MAJOR = 'BSAS')");

            while (rs.next())
            {
                System.out.println(rs.getString("NAME") + " is a student in BSAS.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        connect();
    }
}