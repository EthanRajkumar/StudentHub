import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Instructor extends User {
	int year_of_hire;
	String title, department, email;

	public Instructor(String first_name, String last_name, String id, String in_title, int in_year_of_hire, String in_department, String in_email)
	{
		super(first_name, last_name, id);
		title = in_title;
		year_of_hire = in_year_of_hire;
		department = in_department;
		email = in_email;
	}

	public int GetYearOfHire()
	{
		return year_of_hire;
	}

	public String GetTitle()
	{
		return title;
	}

	public String GetDepartment()
	{
		return department;
	}

	public String GetEmail()
	{
		return email;
	}
	
	public static void SearchCoursebyDept()
	{
		var url = "jdbc:sqlite:Data/assignment3.db";
		Scanner scanner = new Scanner(System.in);
		System.out.println("What is the department of the course you're looking for?");
		String user_dept = scanner.nextLine();
		while(!user_dept.equals("BSEE") && !user_dept.equals("BSCO") && !user_dept.equals("BCOS") && !user_dept.equals("HUSS") && !user_dept.equals("BSAS")) {
			System.out.println("That is not a valid department, try again.");
			System.out.println("What is the dept of the course you're looking for?");
			user_dept = scanner.nextLine();
		}
		ResultSet rs = SqlExecuter.RunQuery(url, "SELECT * FROM COURSE WHERE DEPARTMENT = '" + user_dept + "';");
		try {
			while (rs.next())
			{
				System.out.println("Department: " + rs.getString("DEPARTMENT") + " Course: " + rs.getString("TITLE") + " Year: " + rs.getInt("YEAR"));

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void SearchCoursebyParam()
	{
		// title, department, semester, year
		var url = "jdbc:sqlite:Data/assignment3.db";
		Scanner scanner = new Scanner(System.in);
		System.out.println("What is the title of the course you're looking for? (Type '0' to ignore this parameter)");
		String user_title = scanner.nextLine();
		boolean title = !user_title.equals("0");

		System.out.println("What is the department of the course you're looking for? (Type '0' to ignore this parameter)");
		String user_dept = scanner.nextLine();
		while(!user_dept.equals("BSEE") && !user_dept.equals("BSCO") && !user_dept.equals("BCOS") && !user_dept.equals("HUSS") && !user_dept.equals("BSAS") && !user_dept.equals("0")) {
			System.out.println("That is not a valid department, try again.");
			System.out.println("What is the dept of the course you're looking for? (Type '0' to ignore this parameter)");
			user_dept = scanner.nextLine();
		}
		boolean department = !user_dept.equals("0");

        System.out.println("Which semester do you wish to search by? (Type '0' to ignore this parameter)");
		String user_sem = scanner.nextLine();
		while(!user_sem.equals("Fall") && !user_sem.equals("Spring") && !user_sem.equals("Summer") && !user_sem.equals("0")) {
			System.out.println("That is not a valid semester, try again.");
			System.out.println("Which semester do you wish to search by? (Type '0' to ignore this parameter)");
			user_sem = scanner.nextLine();
		}
		boolean semester = !user_sem.equals("0");

		System.out.println("What year do you wish to search by? (Type '0' to ignore this parameter)");
		Integer user_year = scanner.nextInt();
		while(!user_year.equals(2025) && !user_year.equals(0)) {
			System.out.println("That is not a valid year, try again.");
			System.out.println("What year do you wish to search by? (Type '0' to ignore this parameter)");
			user_year = scanner.nextInt();
		}
		boolean year = !user_year.equals(0);

		String query = "SELECT * FROM COURSE";
		boolean firstCondition = true;

		if (title || department || semester || year) {
			query += " WHERE";
		}

		if (title) {
			query += (firstCondition ? " " : " AND ") + "TITLE = '" + user_title + "'";
			firstCondition = false;
		}
		if (department) {
			query += (firstCondition ? " " : " AND ") + "DEPARTMENT = '" + user_dept + "'";
			firstCondition = false;
		}
		if (semester) {
			query += (firstCondition ? " " : " AND ") + "SEMESTERS = '" + user_sem + "'";
			firstCondition = false;
		}
		if (year) {
			query += (firstCondition ? " " : " AND ") + "YEAR = " + user_year;
		}

		ResultSet rs = SqlExecuter.RunQuery(url, query);

		try {
			boolean found = false;
			while (rs.next()) {
				if (title || department || semester || year)
				{
					found = true;
					System.out.println("Department: " + rs.getString("DEPARTMENT")
							+ " Course: " + rs.getString("TITLE")
							+ " Semester: " + rs.getString("SEMESTERS")
							+ " Year: " + rs.getInt("YEAR"));
				}

			}
			if (!found)
			{
				System.out.println("No matching courses found.");
			}
		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}
	}
	
	public void PrintCourseRoster()
	{
		var url = "jdbc:sqlite:Data/assignment3.db";
		ResultSet rs = SqlExecuter.RunQuery(url, "SELECT * FROM COURSE WHERE INSTRUCTOR = '" + id + "';");
		String query2 = "";
		try {
			while (rs.next()) {
				String students = (rs.getString("STUDENTS"));
				String[] studentArray = students.split(" ");
				System.out.println("Course: " + rs.getString("TITLE"));
				for(int i = 0; i < studentArray.length; i++) {
					query2 = "SELECT * FROM STUDENT WHERE ID = '" + studentArray[i] + "';";
					ResultSet rs2 = SqlExecuter.RunQuery(url, query2);
					while(rs2.next()) {
						System.out.println("Student: " + rs2.getString("NAME") + " ID: " + rs2.getString("ID"));
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}
	}


	public void SearchCourseRoster()
	{
		var url = "jdbc:sqlite:Data/assignment3.db";
		Scanner scanner = new Scanner(System.in);
		System.out.println("What course do you want to search through?");
		String course = scanner.nextLine();
		ResultSet rs = SqlExecuter.RunQuery(url, "SELECT * FROM COURSE WHERE INSTRUCTOR = '" + id + "' AND TITLE = '" + course + "';");
		System.out.println("What student do you wish to search for? (Type in format first_name' 'last_name)");
		String user_student = scanner.nextLine();
		String[] students_name = user_student.split(" ");
		String first_name = students_name[0];
		String last_name = students_name[1];
		String query2 = "";

		try {
			System.out.println("Course is '" + course + "'.");
			while (rs.next()) {
				System.out.println("Course: " + rs.getString("TITLE"));
				query2 = "SELECT ID FROM STUDENT WHERE NAME = '" + first_name + "' AND SURNAME = '" + last_name + "';";
				ResultSet rs2 = SqlExecuter.RunQuery(url, query2);
				boolean found = false;

				while(rs2.next()) {
					found = true;
					System.out.println("Student: " + first_name + " " + last_name + " was found.");
				}

				if(!found) {
					System.out.println("Student: " + first_name + " " + last_name + " was not found.");
				}

			}
		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}

	}
	
	public void PrintTeachingSchedule()
	{
		var url = "jdbc:sqlite:Data/assignment3.db";
		ResultSet rs = SqlExecuter.RunQuery(url, "SELECT * FROM COURSE WHERE INSTRUCTOR = '" + id + "';");
		try {
			System.out.println("Here is your schedule!");
			while (rs.next()) {
				System.out.println("Course: " + rs.getString("TITLE") + " Time: " + rs.getString("TIME"));
			}
		} catch (SQLException e) {
		System.out.println("Database error: " + e.getMessage());
		}
	}

}
