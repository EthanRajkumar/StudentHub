
import java.util.Arrays;
import java.util.Scanner;

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
						System.out.println("Not all parameters of the course were set; please go back and finish");
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
	
	public void CreateUser(String first_name, String last_name, String id)
	{
		System.out.println("Successfully created user " + first_name + " " + last_name + ", ID: " + id + ".");
	}
	
	public void DeleteUser(String id)
	{
		System.out.println("Successfully deleted user ID: " + id + ".");
	}
	
	public void AddStudentToCourse(String studentID, int courseID)
	{
		System.out.println("Successfully added student ID: " + studentID + " to course ID: " + courseID + ".");
	}
	
	public void RemoveStudentFromCourse(String studentID, int courseID)
	{
		System.out.println("Successfully removed student ID: " + studentID + " from course ID: " + courseID + ".");
	}
	
	public void SearchClass(int classID)
	{
		System.out.println("Successfully searched for class ID " + classID + ".");
	}
	
	public void PrintRoster(int classID)
	{
		System.out.println("Successfully printer the roster for class ID " + classID + ".");
	}
}