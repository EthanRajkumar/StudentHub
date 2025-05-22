public class Instructor extends User {
	public Instructor(String first_name, String last_name, String id)
	{
		super(first_name, last_name, id);
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
