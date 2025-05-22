public class Admin extends User {
	String title, office, email;

	public Admin(String first_name, String last_name, String id, String in_title, String in_office, String in_email)
	{
		super(first_name, last_name, id);
		title = in_title;
		office = in_office;
		email = in_email;
	}
	
	public void CreateCourse(int classID)
	{
		System.out.println("Successfully created class ID " + classID + ".");
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