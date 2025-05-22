import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void SQLMode() {
        // connection string
        var url = "jdbc:sqlite:Data/assignment3.db";

        try (var conn = DriverManager.getConnection(url)) {
            Statement statement = conn.createStatement();
            System.out.println("Connection to SQLite has been established.");
            ResultSet rs = statement.executeQuery("SELECT * FROM STUDENT");

/*
            statement.executeUpdate("INSERT INTO STUDENT VALUES (10011, 'Ethan', 'Rajkumar', 2026, 'BSCO', 'rajkumare@wit.edu');"); //Add student Ethan
            statement.executeUpdate("INSERT INTO STUDENT VALUES (10012, 'Chris', 'Le', 2026, 'BSCO', 'lec5@wit.edu');"); //Add student Chris
*/

//            statement.executeUpdate("DELETE FROM INSTRUCTOR WHERE ID = 20006"); //Daniel was deleted

//            statement.executeUpdate("UPDATE ADMIN SET TITLE = 'Vice-President' WHERE ID = 30002"); //Update Vera's title to be Vice-President
            while (rs.next())
            {
                System.out.println(rs.getString("NAME"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        SQLMode();
    }
}