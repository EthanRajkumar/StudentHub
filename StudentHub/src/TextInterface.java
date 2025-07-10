import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TextInterface {
    public static void Start()
    {
        Scanner scanner = new Scanner(System.in);

        String email_input = "", password_input = "", loginQuery, role = "";

        boolean loginFound = false;

        System.out.println("Welcome to StudentHub!");

        while (!loginFound) {
            System.out.println("Enter your email:");
            email_input = scanner.nextLine();
            System.out.println("Enter your Password:");
            password_input = scanner.nextLine();

            loginQuery = "SELECT * FROM LOGIN WHERE EMAIL = '" + email_input + "' AND PASSWORD = '" + password_input + "';";
            ResultSet rs = SqlExecuter.RunQuery("", loginQuery);

            try {
                if (rs.next()) {
                    loginFound = true;
                    role = rs.getString("ROLE").toUpperCase();
                }
            }
            catch (SQLException e)
            {
                System.out.println("Erm");
            }

            System.out.println();

            if (!loginFound)
                System.out.println("Invalid login information. Please try again.");
            else
                System.out.println("Found login information. Signing in...");

            System.out.println();
        }

        String id_input = "";

        String query = "SELECT * FROM " + role + " WHERE EMAIL = '" + email_input + "';";
        ResultSet rs = SqlExecuter.RunQuery("", query);

        try {
            if(rs.next()) {
                id_input = rs.getString("ID");
                System.out.println("Welcome, " + role.toLowerCase() + " " + rs.getString("NAME") + " " + rs.getString("SURNAME") + ".");
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }

        int user_choice = -1;

        switch (role)
        {
            case "STUDENT":
            {
                ResultSet rs_student = SqlExecuter.RunQuery("", "SELECT * FROM STUDENT WHERE ID = '" + id_input + "';");
                Student student = SqlSerializer.StudentFromSql(rs_student);

                while (user_choice != 0) {
                    System.out.println();
                    System.out.println("Please choose from the following options:");
                    System.out.println("1.) Course Search");
                    System.out.println("2.) Advanced Course Search");
                    System.out.println("3.) Add Course");
                    System.out.println("4.) Remove Course");
                    System.out.println("5.) Show Schedule");
                    System.out.println("0.) Logout");

                    user_choice = scanner.nextInt();

                    switch (user_choice)
                    {
                        case 1: {
                            User.SearchCoursebyDept();
                            break;
                        }
                        case 2: {
                            User.SearchCoursebyParam();
                            break;
                        }
                        case 3: {
                            student.AddClass();
                            break;
                        }

                        case 4: {
                            student.RemoveClass();
                            break;
                        }

                        case 5: {
                            student.PrintSchedule();
                            break;
                        }

                        case 0: {
                            System.out.println("Thank you for visiting StudentHub.");
                            break;
                        }

                        default: {
                            System.out.println("Invalid input. Please try again.");
                            break;
                        }
                    }
                }
            }

            case "INSTRUCTOR": {
                ResultSet rs_student = SqlExecuter.RunQuery("", "SELECT * FROM INSTRUCTOR WHERE ID = '" + id_input + "';");
                Instructor instructor = SqlSerializer.InstructorFromSql(rs_student);

                while (user_choice != 0) {
                    System.out.println();
                    System.out.println("Please choose from the following options:");
                    System.out.println("1.) Course Search");
                    System.out.println("2.) Advanced Course Search");
                    System.out.println("3.) Show Schedule");
                    System.out.println("4.) Show Course Roster");
                    System.out.println("5.) Search Course roster");
                    System.out.println("0.) Logout");

                    user_choice = scanner.nextInt();

                    switch (user_choice) {
                        case 1: {
                            User.SearchCoursebyDept();
                            break;
                        }
                        case 2: {
                            User.SearchCoursebyParam();
                            break;
                        }
                        case 3: {
                            instructor.PrintTeachingSchedule();
                            break;
                        }

                        case 4: {
                            instructor.PrintCourseRoster();
                            break;
                        }

                        case 5: {
                            instructor.SearchCourseRoster();
                            break;
                        }

                        case 0: {
                            System.out.println("Thank you for visiting StudentHub.");
                            break;
                        }

                        default: {
                            System.out.println("Invalid input. Please try again.");
                            break;
                        }
                    }
                }
            }

            case "ADMIN": {
                ResultSet rs_student = SqlExecuter.RunQuery("", "SELECT * FROM ADMIN WHERE ID = '" + id_input + "';");
                Admin admin = SqlSerializer.AdminFromSql(rs_student);

                while (user_choice != 0) {
                    System.out.println();
                    System.out.println("Please choose from the following options:");
                    System.out.println("1.) Course Search");
                    System.out.println("2.) Advanced Course Search");
                    System.out.println("3.) Add Course");
                    System.out.println("4.) Add User");
                    System.out.println("5.) Link Instructor to Course");
                    System.out.println("6.) Unlink Instructor to Course");
                    System.out.println("7.) Register Student for Course");
                    System.out.println("8.) Unregister Student for Course");
                    System.out.println("0.) Logout");

                    user_choice = scanner.nextInt();

                    switch (user_choice) {
                        case 1: {
                            User.SearchCoursebyDept();
                            break;
                        }
                        case 2: {
                            User.SearchCoursebyParam();
                            break;
                        }
                        case 3: {
                            admin.CreateCourse(SqlExecuter.SQLConnection);
                            break;
                        }

                        case 4: {
                            admin.CreateUser();
                            break;
                        }

                        case 5: {
                            admin.LinkInstructorToCourse();
                            break;
                        }

                        case 6: {
                            admin.UnlinkInstructorFromCourse();
                            break;
                        }

                        case 7: {
                            try {
                                admin.AddStudentToCourse();
                            }
                            catch (SQLException e) {
                                System.out.println(e);
                            }

                            break;
                        }

                        case 8:
                        {
                            try {
                                admin.RemoveStudentFromCourse();
                            }
                            catch (SQLException e)
                            {
                                System.out.println(e);
                            }

                            break;
                        }

                        case 0: {
                            System.out.println("Thank you for visiting StudentHub.");
                            break;
                        }

                        default: {
                            System.out.println("Invalid input. Please try again.");
                            break;
                        }
                    }
                }
            }
        }
    }
}
