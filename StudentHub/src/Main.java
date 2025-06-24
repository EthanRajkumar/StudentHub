import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    public static void SQLMode() {
        // Opens the connection to assignment3.db
        var url = "jdbc:sqlite:Data/assignment3.db";
        SqlExecuter.OpenDatabase(url);

        // Creates a SQL query
        String query = "SELECT * FROM ADMIN";

        // Populates a SQL resultSet containing all search results
        ResultSet rs = SqlExecuter.RunQuery(url, query);

        try {
            // For every entry found...
            while (rs.next()) {
                // Turn it into an Admin object and print its information
                SqlSerializer.AdminFromSql(rs).PrintAll();

                // Code for other classes (requires a SQL statement querying the relevant table):
                // SqlSerializer.StudentFromSql(rs).PrintAll();
                // SqlSerializer.InstructorFromSql(rs).PrintAll();
            }
        } catch (SQLException e) {
            // If we have an error, print it to the console
            System.out.println(e);
        }

        // Close the database connection
        SqlExecuter.CloseDatabase();
    }

    public static void main(String[] args) {
        SQLMode();
    }
}