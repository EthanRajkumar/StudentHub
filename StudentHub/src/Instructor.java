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
	
	public void SearchClass(int classID)
	{
		System.out.println("Successfully searched for class ID " + classID + ".");
	}
	
	public void PrintClassList()
	{
		System.out.println("Successfully printed instructor " + first_name + " " + last_name + "'s class list.");
	}
	
	public void PrintSchedule()
	{
		System.out.println("Successfully printed instructor " + first_name + " " + last_name + "'s schedule.");
	}
}
