import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class User {
	String first_name, last_name, id;
	
	public User(String firstName, String lastName, String ID)
	{
		first_name = firstName;
		last_name = lastName;
		id = ID;
	}

	public String GetFirstName()
	{
		return first_name;
	}

	public String GetLastName()
	{
		return last_name;
	}

	public String GetID()
	{
		return id;
	}
	
	public void SetFirstName(String firstName)
	{
		first_name = firstName;
	}
	
	public void SetLastName(String lastName)
	{
		last_name = lastName;
	}
	
	public void SetID(String ID)
	{
		id = ID;
	}
	
	public void PrintAll()
	{
		System.out.println("User: " + first_name + " " + last_name + ", ID: " + id);
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

	//copied from Instructor so that Student and Admin can also use function
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
}
