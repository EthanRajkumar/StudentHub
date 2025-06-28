import java.sql.ResultSet;
import java.sql.SQLException;

public class Debug {
    public static void ResetPasswords()
    {
        // Adding login to .db file
        String update = "DROP TABLE LOGIN;";
        SqlExecuter.RunUpdate("", update);
        update = "CREATE TABLE IF NOT EXISTS LOGIN (EMAIL txt PRIMARY KEY, ROLE txt, PASSWORD txt);";
        SqlExecuter.RunUpdate("", update);

        String updatePasswordQuery;

        ResultSet rs = SqlExecuter.RunQuery("", "SELECT * FROM STUDENT");

        try {
            while (rs.next())
            {
                updatePasswordQuery = "INSERT INTO LOGIN VALUES ('" + rs.getString("EMAIL") + "', 'student', '" + rs.getString("EMAIL") + "');";
                SqlExecuter.RunUpdate("", updatePasswordQuery);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

        rs = SqlExecuter.RunQuery("", "SELECT * FROM INSTRUCTOR");

        try {
            while (rs.next())
            {
                updatePasswordQuery = "INSERT INTO LOGIN VALUES ('" + rs.getString("EMAIL") + "', 'instructor', '" + rs.getString("EMAIL") + "');";
                SqlExecuter.RunUpdate("", updatePasswordQuery);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

        rs = SqlExecuter.RunQuery("", "SELECT * FROM ADMIN");

        try {
            while (rs.next())
            {
                updatePasswordQuery = "INSERT INTO LOGIN VALUES ('" + rs.getString("EMAIL") + "', 'admin', '" + rs.getString("EMAIL") + "');";
                SqlExecuter.RunUpdate("", updatePasswordQuery);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }
}
