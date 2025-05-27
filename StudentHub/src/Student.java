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
	
	public void AddClass(int classID)
	{
		System.out.println("Successfully added student " + first_name + " " + last_name + " to class ID " + classID + ".");
	}
	
	public void RemoveClass(int classID)
	{
		System.out.println("Successfully removed student " + first_name + " " + last_name + " from class ID " + classID + ".");
	}
	
	public void PrintSchedule()
	{
		System.out.println("Successfully printed student " + first_name + " " + last_name + "'s schedule.");
	}
}
