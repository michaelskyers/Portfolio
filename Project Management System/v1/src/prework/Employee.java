//package prework;

public class Employee {
	
	private String ID, fname, lname, dept, role;
	private static int ID_count = 200;
	
	//Constructor
	public Employee (String fname, String lname, String dept, String role)
	{
		ID_count++;
		ID = "E" + ID_count;
		this.fname = fname; this.lname = lname;
		this.dept = dept; this.role = role;
	}
	
	public String getID()
	{
		return ID;
	}
	
	public String get( String info )
	{
		switch(info) {
		
		case "name":
			info = fname+" "+lname;
			break;
		
		case "dept":
			info = dept;
			break;
		
		case "role":
			info = role;
			break;
		}
		
		return info;
	}

	public String toString()
	{
		return ""+
				"\nEmployee ID  : "+ID+
				"\nName         : "+fname+" "+lname+
				"\nDepartment   : "+dept+
				"\nRole         : "+role;
	}
}
