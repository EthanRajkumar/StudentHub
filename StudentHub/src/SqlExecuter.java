import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SqlExecuter {

    public static ResultSet RunQuery(String url, String query)
    {
        ResultSet rs = null;

        try (var conn = DriverManager.getConnection(url)) {
            Statement statement = conn.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rs;
    }

    public static void RunUpdate(String url, String update)
    {
        try (var conn = DriverManager.getConnection(url)) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(update);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}