import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//User responsible for Student functionality is Ethan Rajkumar
public class Student extends User {
	int expected_grad_year;
	String major, email;

	public Student(String first_name, String last_name, String id, int in_expected_grad_year, String in_major, String in_email) {
		super(first_name, last_name, id);
		expected_grad_year = in_expected_grad_year;
        major = in_major;
		email = in_email;
	}


	public int GetGraduationYear()
	{
		return expected_grad_year;
	}

	public String GetMajor()
	{
		return major;
	}

	public String GetEmail()
	{
		return email;
	}

	public void SearchClass(int classID)
	{
		System.out.println("Successfully searched for class ID " + classID + ".");
	}

	public boolean AddClass()
	{
		Scanner scanner = new Scanner(System.in);
		String title = "";
		ResultSet rs = null;
		String query;

		try
		{
			System.out.println("Please enter the name of the course you wish to add (case sensitive): ");
			title = scanner.nextLine();

			query = "SELECT * FROM COURSE WHERE TITLE = '" + title + "';";
			rs = SqlExecuter.RunQuery("", query);

			if (rs.getString("TITLE") == null) {
				System.out.println("Invalid course ID detected.");
				return false;
			}

			String students = rs.getString("STUDENTS");

			if (students == null)
			{
				students = "";
			}
			else if (students.contains(id)) {
				System.out.println("You are already enrolled in this course.");
				return false;
			}
			else
			{
				String[] ids = students.split(" ");

				if (ids.length >= rs.getInt("SEATS"))
				{
					System.out.println("This course is currently full!");
					return false;
				}
				else if (IsTimeConflict(rs.getInt("TIME"), rs.getString("DAYS").split(" "), rs.getString("SEMESTERS").split(" "), rs.getInt("YEAR")))
				{
					System.out.println("Time conflict detected.");
					return false;
				}
			}

			students += " " + id;
			students = students.trim();

			query = "UPDATE COURSE SET STUDENTS = '" + students + "' WHERE TITLE = '" + title + "';";
			SqlExecuter.RunUpdate("", query);

			System.out.println(first_name + " " + last_name + " has signed up for course " + rs.getString("TITLE") + ".");
			return true;
		}
		catch (SQLException e)
		{
			System.out.println(e);
			return false;
		}
	}

	private boolean IsTimeConflict(int time, String[] days, String[] semesters, int year)
	{
		// Query the database to pull up every course the student is currently registered for.
		String query = "SELECT * FROM COURSE WHERE STUDENTS LIKE '%" + id + "%';";
		ResultSet rs = SqlExecuter.RunQuery("", query);

		// Parse the 'time' field to get the start and end times of the desired course
		int startTime = time / 10000, endTime = time % 10000;
		// Holds values for the class we're checking for time conflicts with
		int currentTime, currentStartTime, currentEndTime, currentYear;
		String currentSemesters, currentDays;

		try {
			// For every course currently registered...
			while (rs.next()) {
				// Set values of current course to check for conflicts
				currentTime = rs.getInt("TIME");
				currentStartTime = currentTime / 10000;
				currentEndTime = currentTime % 10000;
				currentDays = rs.getString("DAYS");
				currentSemesters = rs.getString("SEMESTERS");
				currentYear = rs.getInt("YEAR");

				boolean overlappingSemester = false, overlappingDay = false;

				if (currentYear != year)
					continue;

				// For every semester in the desired class, check if a registered class runs on the same semester
				for (int i = 0; i < semesters.length; i++) {
					if (currentSemesters.contains(semesters[i])) {
						overlappingSemester = true;
						break;
					}
				}

				// If the classes don't run on the same semesters,  there can't be a conflict between the two
				if (!overlappingSemester)
					continue;

				// For every day in the desired class, check if a registered class runs on the same day
				for (int i = 0; i < days.length; i++) {
					if (currentDays.contains(days[i])) {
						overlappingDay = true;
						break;
					}
				}

				// If the classes don't run on the same days, there can't be a conflict between the two
				if (!overlappingDay)
					continue;

				// As a last check, test if the times overlap each other in some manner
				if (currentStartTime <= startTime && startTime <= currentEndTime)
					return true;
				else if (currentStartTime <= endTime && endTime <= currentEndTime)
					return true;
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
			return true;
		}

		// If we haven't found a time conflict in any currently registered class, there is no overall time conflict
		return false;
	}


	public boolean RemoveClass()
	{
		Scanner scanner = new Scanner(System.in);
		String title = "";

		ResultSet rs = null;
		String query;

		try {

			System.out.println("Please enter the name of the course you wish to add (case sensitive): ");
			title = scanner.nextLine();

			query = "SELECT * FROM COURSE WHERE TITLE = '" + title + "';";
			rs = SqlExecuter.RunQuery("", query);

			if (rs.getString("TITLE") == null) {
				System.out.println("Invalid course ID detected.");
				return false;
			}

			String students = rs.getString("STUDENTS");

			if (!students.contains(id)) {
				System.out.println("You are not currently enrolled in this course.");
				return false;
			}

			students = students.replace(id, "");
			students = students.replace(id + " ", "");
			students = students.trim();

			query = "UPDATE COURSE SET STUDENTS = '" + students + "' WHERE TITLE = '" + title + "';";
			SqlExecuter.RunUpdate("", query);

			System.out.println(first_name + " " + last_name + " has unregistered for course " + rs.getString("TITLE") + ".");
			return true;
		}
		catch (SQLException e)
		{
			System.out.println(e);
			return false;
		}
	}

	public void PrintSchedule()
	{
		System.out.println(first_name + " " + last_name + "'s schedule:");

		String query = "SELECT * FROM COURSE WHERE STUDENTS LIKE '%" + id + "%';";
		ResultSet rs = SqlExecuter.RunQuery("", query);

		String title;
		int i = 0;
		try {
			while (rs.next()) {
				i++;
				title = rs.getString("TITLE");

				System.out.println("Class " + i + ": " + title);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}

		if (i == 0)
			System.out.println("[Schedule is empty]");
	}
}

