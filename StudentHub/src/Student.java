public class Student extends User {
	
	public Student(String first_name, String last_name, String id, int expected_grad_year, String major, String email)
	{
		super(first_name, last_name, id);
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
