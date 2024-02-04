//package prework;
import java.util.ArrayList;

/*
 * This class serves as a data structure to keep track of assigned project ID
 * and the employee IDs they are assigned to.
 * It will also help employee IDs keep track of the project IDs they are assigned to
 */

public class Assigned {
	
	private String ID; //can be product or employee
	
	/*
	 * IF ID is for a product
	 * THEN Companions will be Employees IDs
	 * and vice versa...
	 */
	private ArrayList<String> Companions;
	
	public Assigned(String ID, String compID)
	{
		this.ID = ID;
		Companions = new ArrayList<String>();
		Companions.add(compID);
	}
	
	public String getID()
	{
		return ID;
	}
	public ArrayList<String> getCompanions()
	{
		return Companions;
	}
	//checks if a given project or employee is assigned to a this employee or project
	public boolean isCompanion(String ID)
	{
		return Companions.contains(ID);
	}

}
