
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Scanner;
//import java.sql.ResultSetMetaData;

public class Admin extends User {
	String title, office, email;

	public Admin(String first_name, String last_name, String id, String in_title, String in_office, String in_email)
	{
		super(first_name, last_name, id);
		title = in_title;
		office = in_office;
		email = in_email;
	}

	public String GetTitle()
	{
		return title;
	}

	public String GetOffice()
	{
		return office;
	}

	public String GetEmail()
	{
		return email;
	}
	
	public void CreateCourse() {
		//System.out.println("Successfully created class ID " + classID + ".");
		System.out.println("Successfully called CreateCourse function");
		int CRN = 0; String courseName = ""; String courseDept = ""; int time = 0; String[] days = new String[]{"", "", "", "", "", "", ""}; String[] semesters = new String[]{"", "", "", ""};
		int year = 0; int cred = 0; int seats = 0;
		int enterDays = 1; int enterSemesters = 1; boolean active = true; int menu;
		boolean doneName = false; boolean doneDept = false; boolean doneID = false; boolean doneTime = false; boolean doneDays = false;
		boolean doneSemesters = false; boolean doneYear = false; boolean doneCred = false; boolean doneSeats = false;
		Scanner reader = new Scanner(System.in);
		while (active) {
			System.out.println("Select an attribute of the course to edit: \n1: Edit course name \n2: Edit course department \n3: Edit course ID \n" +
					"4: Edit time of day \n5: Edit days of the week \n6: Edit semesters offered \n7: Edit year offered \n8: Edit credits \n9: Edit seats \n0. Exit");
			menu = reader.nextInt();
			reader.nextLine();
			switch(menu){
				case 1:
					doneName = false;
					while (!doneName) {
						System.out.println("Enter course name: ");
                        courseName = reader.nextLine();
						doneName = true;
                    }
					continue;
				case 2:
					doneDept = false;
					while (!doneDept) {
						System.out.println("Enter course department acronym: ");
						courseDept = reader.nextLine();
						if (!(courseDept.equals("BSEE") || courseDept.equals("BSCO") || courseDept.equals("BCOS") || courseDept.equals("HUSS") || courseDept.equals("BSAS"))) {
							System.out.println("Invalid department (must be BSEE, BSCO, BCOS, HUSS, or BSAS)");
						}
						else {
							doneDept = true;
						}
					}
					continue;
				case 3:
					doneID = false;
					while (!doneID) {
						System.out.println("Enter course ID (5 digits): ");
						CRN = reader.nextInt();
						reader.nextLine();	//consume leftover newline to enable consecutive use of nextLine
						if (CRN > 99999 || CRN < 10000) {
							System.out.println("Invalid course ID (must be a 5 digit number)");
						}
						else {
							doneID = true;
						}
					}
					continue;
				case 4:
					doneTime = false;
					while (!doneTime) {
						System.out.println("Enter course time in 8 digit format, with first 4 digits being start time in military time format and last 4 digits being end time in the same format: ");
						time = reader.nextInt();
						reader.nextLine();	//consume leftover newline to enable consecutive use of nextLine
						if (time > 99999999 || time < 1000000 || ((time / 10000) % 100) > 59 || (time % 100) > 59) {
							System.out.println("Invalid time; restarting course creation...");
						}
						else  {
							doneTime = true;
						}
					}
					continue;
				case 5:
					int daysMenu;
					while (!doneDays) {
						System.out.println("Select day to toggle on/off: \n1: Sunday \n2: Monday \n3:Tuesday \n4:Wednesday \n5:Thursday \n6:Friday \n7:Saturday \n0: Done");
						daysMenu = reader.nextInt();
						reader.nextLine();
						switch(daysMenu) {
							case 1:
								if (days[0].equals("")){
									days[0] = "Sunday";
									System.out.println("This course is now set to occur on Sunday");
								}
								else {
									days[0] = "";
									System.out.println("This course is now not set to occur on Sunday");
								}
								continue;
							case 2:
								if (days[1].equals("")){
									days[1] = "Monday";
									System.out.println("This course is now set to occur on Monday");
								}
								else {
									days[1] = "";
									System.out.println("This course is now not set to occur on Monday");
								}
								continue;
							case 3:
								if (days[2].equals("")){
									days[2] = "Tuesday";
									System.out.println("This course is now set to occur on Tuesday");
								}
								else {
									days[2] = "";
									System.out.println("This course is now not set to occur on Tuesday");
								}
								continue;
							case 4:
								if (days[3].equals("")){
									days[3] = "Wednesday";
									System.out.println("This course is now set to occur on Wednesday");
								}
								else {
									days[3] = "";
									System.out.println("This course is now not set to occur on Wednesday");
								}
								continue;
							case 5:
								if (days[4].equals("")){
									days[4] = "Thursday";
									System.out.println("This course is now set to occur on Thursday");
								}
								else {
									days[4] = "";
									System.out.println("This course is now not set to occur on Thursday");
								}
								continue;
							case 6:
								if (days[5].equals("")){
									days[5] = "Friday";
									System.out.println("This course is now set to occur on Friday");
								}
								else {
									days[5] = "";
									System.out.println("This course is now not set to occur on Friday");
								}
								continue;
							case 7:
								if (days[6].equals("")){
									days[6] = "Saturday";
									System.out.println("This course is now set to occur on Saturday");
								}
								else {
									days[6] = "";
									System.out.println("This course is now not set to occur on Saturday");
								}
								continue;
							case 0:
								doneDays = true;
							default:
								continue;
						}
					}
					continue;
				case 6:
					int semMenu;
					while (!doneSemesters) {
						System.out.println("Select semester to toggle on/off: \n1: Spring \n2: Summer \n3: Fall \n0: Done");
						semMenu = reader.nextInt();
						reader.nextLine();
						switch (semMenu) {
							case 1:
								if (semesters[0].equals("")){
									semesters[0] = "Spring";
									System.out.println("This course is now set to be offered in the Spring");
								}
								else {
									semesters[0] = "";
									System.out.println("This course is now set to not be offered in the Spring");
								}
								continue;
							case 2:
								if (semesters[1].equals("")){
									semesters[1] = "Summer";
									System.out.println("This course is now set to be offered in the Summer");
								}
								else {
									semesters[1] = "";
									System.out.println("This course is now set to not be offered in the Summer");
								}
								continue;
							case 3:
								if (semesters[2].equals("")){
									semesters[2] = "Fall";
									System.out.println("This course is now set to be offered in the Fall");
								}
								else {
									semesters[2] = "";
									System.out.println("This course is now set to not be offered in the Fall");
								}
								continue;
							case 0:
								doneSemesters = true;
							default:
								continue;
						}
					}
					continue;
				case 7:
					System.out.println("Enter the year that this course section is being offered: ");
					year = reader.nextInt();
					reader.nextLine();	//consume leftover newline to enable consecutive use of nextLine
					doneYear = true;
					continue;
				case 8:
					System.out.println("Enter the amount of credits offered from this course: ");
					cred = reader.nextInt();
					reader.nextLine();	//consume leftover newline to enable consecutive use of nextLine
					doneCred = true;
					continue;
				case 9:
					System.out.println("Enter the maximum amount of seats available for this section: ");
					seats = reader.nextInt();
					doneSeats = true;
					continue;
				case 0:
					if (!doneName || !doneID || !doneDept || !doneTime || !doneSemesters || !doneYear || !doneDays || !doneCred || !doneSeats) {
						System.out.println("Not all attributes of the course were set; please go back and finish");
					}
					else {
						active = false;
						break;
					}
				default:
					continue;

			}
		}
		Course newCourse = new Course(courseName, courseDept, CRN, time, days, semesters, year, cred, seats);
		System.out.println("Course created: \nName: " + newCourse.GetTitle() + "\nDepartment: " + newCourse.GetDepartment() + "\nCourse ID: " + newCourse.GetCRN()
		+ "\nTime: " + newCourse.GetTime() + "\nDays: " + Arrays.toString(newCourse.GetDays()) + "\nSemesters: " + Arrays.toString(newCourse.GetSemesters()) +
				"\nYear: " + newCourse.GetYear() + "\nCredits: " + newCourse.GetCredits() + "\nSeats: " + newCourse.GetSeats());
	}
	
	public void DeleteCourse(int classID)
	{
		System.out.println("Successfully removed class ID " + classID + ".");
	}
	
	public void CreateUser()
	{
		//System.out.println("Successfully created user " + first_name + " " + last_name + ", ID: " + id + ".");
		String fName = ""; String lName = ""; String id = ""; String email = "";
		boolean fNameDone = false; boolean lNameDone = false; boolean idDone = false; boolean emailDone = false;
		boolean active = true;
		Scanner reader = new Scanner(System.in);
		int userSelect = 0;
		while (true) {
			System.out.println("Select a type of user to create: \n1: Student\n2: Instructor");
			userSelect = reader.nextInt();
			reader.nextLine();
			if (userSelect == 1 || userSelect == 2) {
				break;
			}
		}
		if (userSelect == 1) {
			System.out.println("Creating new Student");
			int expectedGradYear = 0; String major = "";					//attributes exclusive to Student
			boolean expectedGradYearDone = false; boolean majorDone = false;
			int studentMenu;
			while (active) {
				System.out.println("Select an attribute of the new Student to edit: \n1: First name\n2: Last name\n3: ID\n" +
						"4: Expected Graduation Year\n5: Major\n6: Email\n0: Done");
				studentMenu = reader.nextInt();
				reader.nextLine();
				switch (studentMenu){
					case 1:
						System.out.println("Enter first name of this student: ");
						fName = reader.nextLine();
						fNameDone = true;
						continue;
					case 2:
						System.out.println("Enter last name of this student: ");
						lName = reader.nextLine();
						lNameDone = true;
						continue;
					case 3:
						System.out.println("Enter ID of this student: ");
						id = reader.nextLine();
						idDone = true;
						continue;
					case 4:
						System.out.println("Enter the student's expected year of graduation: ");
						expectedGradYear = reader.nextInt();
						reader.nextLine();
						expectedGradYearDone = true;
						continue;
					case 5:
						System.out.println("Enter major of this student: ");
						major = reader.nextLine();
						majorDone = true;
						continue;
					case 6:
						System.out.println("Enter institutional email of this student: ");
						email = reader.nextLine();
						emailDone = true;
						continue;
					case 0:
						if (!fNameDone || !lNameDone || !idDone || !expectedGradYearDone || !majorDone || !emailDone) {
							System.out.println("Not all attributes of the student were set; please go back and finish");
							continue;
						}
						else {
							active = false;
							break;
						}
					default:
						continue;
				}
				Student newStudent = new Student(fName, lName, id, expectedGradYear, major, email);
				System.out.println("Creating student:\nFirst name: " + fName + "\nLast name: " + lName + "\nID: " + id +
						"\nExpected Year of Graduation: " + expectedGradYear + "\nMajor: " + major + "\nEmail: " + email);
				break;
			}
		}
		else {
			System.out.println("Creating new Instructor");
			String title = ""; int yearOfHire = 0; String department = "";	//attributes exclusive to Instructor
			boolean titleDone = false; boolean yearOfHireDone = false; boolean departmentDone = false;
			int instructorMenu;
			while (active) {
				System.out.println("Select an attribute of the new Student to edit: \n1: First name\n2: Last name\n3: ID\n" +
						"4: Title\n5: Year of Hire\n6: Department\n7: Email\n0: Done");
				instructorMenu = reader.nextInt();
				reader.nextLine();
				switch (instructorMenu){
					case 1:
						System.out.println("Enter first name of this instructor: ");
						fName = reader.nextLine();
						fNameDone = true;
						continue;
					case 2:
						System.out.println("Enter last name of this instructor: ");
						lName = reader.nextLine();
						lNameDone = true;
						continue;
					case 3:
						System.out.println("Enter ID of this instructor: ");
						id = reader.nextLine();
						idDone = true;
						continue;
					case 4:
						System.out.println("Enter the title of this instructor");
						title = reader.nextLine();
						titleDone = true;
						continue;
					case 5:
						System.out.println("Enter the instructors's year of hire: ");
						yearOfHire = reader.nextInt();
						reader.nextLine();
						yearOfHireDone = true;
						continue;
					case 6:
						System.out.println("Enter department of this instructor");
						department = reader.nextLine();
						departmentDone = true;
						continue;
					case 7:
						System.out.println("Enter institutional email of this instructor: ");
						email = reader.nextLine();
						emailDone = true;
						continue;
					case 0:
						if (!fNameDone || !lNameDone || !idDone || !titleDone || !yearOfHireDone || !departmentDone || !emailDone) {
							System.out.println("Not all attributes of the instructor were set; please go back and finish");
							continue;
						}
						else {
							active = false;
							break;
						}
					default:
						continue;
				}
				Instructor newInstructor = new Instructor(fName, lName, id, title, yearOfHire, department,email);
				System.out.println("Creating instructor:\nFirst name: " + fName + "\nLast name: " + lName + "\nID: " + id +
						"\nTitle: " + title + "\nYear of Hire: " + yearOfHire + "\nDepartment: " + department + "\nEmail: " + email);
				break;
			}

		}


	}
	
	public void DeleteUser(String id)
	{
		System.out.println("Successfully deleted user ID: " + id + ".");
	}
	
	public void AddStudentToCourse() throws SQLException {
		//System.out.println("Successfully added student ID: " + studentID + " to course ID: " + courseID + ".");
		System.out.println("Add student to course selected...");
		Scanner reader = new Scanner(System.in);
		int courseID;
		String studentID;

		PrintAllCourses();

		System.out.println("Enter the course ID of the course you'd like to add a student to: ");
		courseID = reader.nextInt();
		reader.nextLine();

		PrintCourseRoster(courseID);

		System.out.println("Enter the ID of the student you'd like to add to this course: ");
		studentID = reader.nextLine();

		var url = "jdbc:sqlite:Data/assignment3.db";
		String query = "SELECT STUDENTS FROM COURSE";
		ResultSet rs = SqlExecuter.RunQuery(url, query);
		String students = rs.getString("STUDENTS");
		String[] studentArray = students.split(" ");
        String update;
        if (studentArray[0].isBlank()) {
            update = "UPDATE COURSE SET STUDENTS = STUDENTS ||" + "'" + studentID + "'" + " WHERE CRN = " + courseID;
        }
		else {
            update = "UPDATE COURSE SET STUDENTS = STUDENTS ||" + "' " + studentID + "'" + " WHERE CRN = " + courseID;
        }
        SqlExecuter.RunUpdate(url, update);

    }
	
	public void RemoveStudentFromCourse() throws SQLException {
		//System.out.println("Successfully removed student ID: " + studentID + " from course ID: " + courseID + ".");
		System.out.println("Remove student from course selected...");
		Scanner reader = new Scanner(System.in);
		int courseID;
		String studentID;

		PrintAllCourses();

		System.out.println("Enter the course ID of the course you'd like to remove a student from: ");
		courseID = reader.nextInt();
		reader.nextLine();

		PrintCourseRoster(courseID);
		System.out.println("Enter the ID of the student you'd like to remove from this course: ");
		studentID = reader.nextLine();

		var url = "jdbc:sqlite:Data/assignment3.db";
		String query = "SELECT STUDENTS FROM COURSE";
		ResultSet rs = SqlExecuter.RunQuery(url, query);
		String students = rs.getString("STUDENTS");
		//String[] studentArray = students.split(" ");
		String update;

		if (!students.contains(studentID) || students.isBlank()) {
			System.out.println("Entered user ID is not registered under this course; roster unchanged");
		}

		students = students.replace(studentID, "");
		update = "UPDATE COURSE SET STUDENTS = " + "'" + students + "'" + " WHERE CRN = " + courseID;

		SqlExecuter.RunUpdate(url, update);


	}

	public void LinkInstructorToCourse() {
		System.out.println("Link instructor to course selected...");
		Scanner reader = new Scanner(System.in);
		int courseID;

		PrintAllCourses();

		System.out.println("Enter the course ID of the course you'd like to link an instructor to: ");
		courseID = reader.nextInt();
		reader.nextLine();

		PrintCourseRoster(courseID);

	}

	public void UnlinkInstructorFromCourse() {
		System.out.println("Unlink instructor from course selected...");
		Scanner reader = new Scanner(System.in);
		int courseID;

		PrintAllCourses();

		System.out.println("Enter the course ID of the course you'd like to unlink an instructor from: ");
		courseID = reader.nextInt();
		reader.nextLine();

		PrintCourseRoster(courseID);

	}
	
	public void SearchClass(int classID)
	{
		System.out.println("Successfully searched for class ID " + classID + ".");
	}

	public void PrintCourseRoster(int courseID)
	{
		var url = "jdbc:sqlite:Data/assignment3.db";
		ResultSet rs = SqlExecuter.RunQuery(url, "SELECT * FROM COURSE WHERE CRN = '" + courseID + "';");
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

	public void PrintAllCourses() {
		//Print all courses
		var url = "jdbc:sqlite:Data/assignment3.db";
		SqlExecuter.OpenDatabase(url);

		String query = "SELECT * FROM COURSE";
		ResultSet rs = SqlExecuter.RunQuery(url, query);

		try (var conn = DriverManager.getConnection(url)) {

			while (rs.next()) {
				SqlSerializer.CourseFromSql(rs).PrintAll();
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}