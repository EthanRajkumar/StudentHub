public class User {
	String first_name, last_name, id;
	
	public User(String firstName, String lastName, String ID)
	{
		first_name = firstName;
		last_name = lastName;
		id = ID;
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
}
