//package prework;

public class Project {
	
	private String ID, name, description, startDate, endDate;
	private static int IDcount = 100;
	
	public Project(String name, String desc, String start, String end)
	{
		this.name = name; description = desc;
		startDate = start; endDate = end;
		
		IDcount++;
		ID = "P"+IDcount;
	}
	
	public String getID()
	{
		return ID;
	}
	
	public String get(String info)
	{
		switch(info)
		{
		case "name":
			info = name;
			break;
		
		case "desc":
			info = description;
			break;
			
		case "start":
			info = startDate;
			break;
			
		case "end":
			info = endDate;
			break;
		}
		return info;
	}
	
	public void setName(String newName)
	{
		name = newName;
	}
	public void setDescription(String desc)
	{
		description = desc;
	}
	public void setStart(String start)
	{
		startDate = start;
	}
	public void setEnd(String end)
	{
		endDate = end;
	}
	
	public String toString()
	{
		return ""+
				"\nProject ID  : "+ID+
				"\nProject Name: "+name+
				"\nDescription : "+description+
				"\nStart Date  : "+startDate+
				"\nEnd Date    : "+endDate;
	}

}
