import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Opens the connection to assignment3.db
        var url = "jdbc:sqlite:Data/assignment3.db";
        SqlExecuter.OpenDatabase(url);

        // Sets the password of all users to their email (for debug purposes)
        Debug.ResetPasswords();

        // Runs the user interface of the program
        TextInterface.Start();

        // Close the database connection
        SqlExecuter.CloseDatabase();
    }
}