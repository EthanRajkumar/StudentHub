import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Scanner;

public class SqlExecuter {

    public static Connection SQLConnection;

    public static void OpenDatabase(String url)
    {
        try {
            if (SQLConnection != null && !SQLConnection.isClosed())
                SQLConnection.close();

            SQLConnection = DriverManager.getConnection(url);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void CloseDatabase()
    {
        try {
            if (SQLConnection == null || SQLConnection.isClosed())
                return;

            SQLConnection.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet RunQuery(String url, String query)
    {
        try {
            if (SQLConnection == null || SQLConnection.isClosed())
                return null;

            Statement statement = SQLConnection.createStatement();
            return statement.executeQuery(query);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static void RunUpdate(String url, String update)
    {
        try {
            Statement statement = SQLConnection.createStatement();
            statement.executeUpdate(update);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}