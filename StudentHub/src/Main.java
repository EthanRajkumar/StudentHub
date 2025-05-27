import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void SQLMode() {
        // connection string
        var url = "jdbc:sqlite:Data/assignment3.db";

        //new table Course
        var courses = "CREATE TABLE IF NOT EXISTS COURSE ("
                + " CRN INTEGER PRIMARY KEY,"
                + " title txt NOT NULL,"
                + " department txt NOT NULL,"
                + " time txt NOT NULL,"
                + " days txt NOT NULL,"
                + " semesters txt NOT NULL,"
                + " year INTEGER NOT NULL,"
                + " credits INTEGER NOT NULL,"
                + " seats INTEGER NOT NULL"
                + ");";

        try (var conn = DriverManager.getConnection(url)) {
            Statement statement = conn.createStatement();
            Statement statement2 = conn.createStatement();
            System.out.println("Connection to SQLite has been established.");
            ResultSet rs = statement.executeQuery("SELECT * FROM STUDENT");

            statement.execute(courses);
            /*
            statement.executeUpdate("INSERT INTO STUDENT VALUES (10011, 'Ethan', 'Rajkumar', 2026, 'BSCO', 'rajkumare@wit.edu');"); //Add student Ethan
            statement.executeUpdate("INSERT INTO STUDENT VALUES (10012, 'Chris', 'Le', 2026, 'BSCO', 'lec5@wit.edu');"); //Add student Chris

            statement.executeUpdate("DELETE FROM INSTRUCTOR WHERE ID = 20006"); //Daniel was deleted

            statement.executeUpdate("UPDATE ADMIN SET TITLE = 'Vice-President' WHERE ID = 30002"); //Update Vera's title to be Vice-President
            */
            /*
            String course = "INSERT INTO COURSE VALUES (0000, 'Circuit Theory', 'BSEE', '8:00 - 9:30', 'Monday Wednesday', 'Spring', 2025, 4, 20);";
            statement.executeUpdate(course);

            String course = "INSERT INTO COURSE VALUES (0001, 'Computer Networks', 'BSCO', '10:00 - 11:45', 'Tuesday Wednesday Friday', 'Fall', 2025, 4, 30);";
            statement.executeUpdate(course);

            course = "INSERT INTO COURSE VALUES (0002, 'Pediatrics', 'BCOS', '2:00 - 3:30', 'Thursday Friday', 'Summer', 2025, 3, 24);";
            statement.executeUpdate(course);

            course = "INSERT INTO COURSE VALUES (0003, 'Economics', 'HUSS', '12:00 - 1:45', 'Tuesday Friday', 'Fall Spring', 2025, 3, 24);";
            statement.executeUpdate(course);

            course = "INSERT INTO COURSE VALUES (0004, 'Substances', 'BSAS', '8:00 - 9:30', 'Tuesday Wednesday Friday', 'Fall Summer', 2025, 3, 24);";
            statement.executeUpdate(course);
            */

            /*String findMatchingProfessors = "SELECT NAME, SURNAME FROM INSTRUCTOR INNER JOIN COURSE ON INSTRUCTOR.DEPT = COURSE.department;";
            rs = statement.executeQuery(findMatchingProfessors);

            while (rs.next())
            {
                System.out.println(rs.getString("NAME") + " " + rs.getString("SURNAME"));
            }*/

            String findCourses = "SELECT title, department FROM COURSE;";
            rs = statement.executeQuery(findCourses);

            ResultSet rs_1;

            while (rs.next())
            {
                System.out.println(rs.getString("department"));
                rs_1 = statement2.executeQuery("SELECT NAME, SURNAME FROM INSTRUCTOR WHERE DEPT='" + rs.getString("department") + "';");

                while (rs_1.next())
                {
                    System.out.println("Instructor " + rs_1.getString("NAME") + " " + rs_1.getString("SURNAME") + " teaches course " + rs.getString("title") + ".");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        SQLMode();
    }
}