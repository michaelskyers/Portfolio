//package prework;
import java.util.ArrayList;
import java.util.Scanner;

public class Management_System {
	
	private Scanner input = new Scanner(System.in);
	private Scanner input2 = new Scanner(System.in);
	
	private ArrayList<Project> ProjectList;
	private ArrayList<Assigned> AssignedProjectsList;
	
	private ArrayList<Employee> EmployeeList;
	private ArrayList<Assigned> AssignedEmployeesList;

	/*
	 * Methods
	 */
	
	//Constructor
	public Management_System()
	{
		ProjectList = new ArrayList<Project>();
		AssignedProjectsList = new ArrayList<Assigned>();
		
		EmployeeList = new ArrayList<Employee>();
		AssignedEmployeesList = new ArrayList<Assigned>();
	}
	
	//Create projects and populate list
	public void createProjects(int n)
	{
		for (int i = 1; i <= n; i++)
		{
			print("Please enter the details for Project "+i+" of "+n);
			print("Name:");
			String name = ask();
			
			print("\nDescription:");
			String desc = ask();
			
			print("\nStart Date:");
			String start = ask();
			
			print("\nEnd Date:");
			String end = ask();
			
			if (ProjectList.add(new Project(name,desc,start,end)))
			{
				print(""+
						"Project has been successfully added\n"+
						"///////////////////////////////////\n");
			}
			else
				print("There was an error adding this project\n");
			
		}
	}
	
	//Create employees and populate employee list
	public void createEmployees(int n)
	{
		for (int i = 1; i <= n; i++)
		{
			print("Please enter the details for Employee "+i+" of "+n);
			print("First Name:");
			String fname = ask();
			
			print("\nLast Name:");
			String lname = ask();
			
			print("\nDepartment:");
			String dept = ask();
			
			print("\nRole:");
			String role = ask();
			
			if (EmployeeList.add(new Employee(fname,lname,dept,role)))
			{
				print(""+
						"Employee has been successfully added\n"+
						"///////////////////////////////////\n");
			}
			else
				print("There was an error adding this employee\n");
			
		}
	}
	
	//Gets Project from ID
	public Project getProject(String ProjectID)
	{
		for (Project p : ProjectList)
		{
			if (p.getID().equals(ProjectID))
				return p;
		}
		return null;
	}
	
	//Gets Employee from ID
	public Employee getEmployee(String EmployeeID)
	{
		for (Employee e : EmployeeList)
		{
			if (e.getID().equals(EmployeeID))
				return e;
		}
		return null;
	}
	
	/*
	 * Assigns project to an employee
	 * This is a three-part procedure
	 * This first method checks to see if the supplied IDs are valid
	 */
	public void assignProject()
	{
		boolean valid = false;
		
		while (!valid)
		{
			print("\nPlease enter the ID for the project you wish to assign:");
			String ProjectID = ask();
			
			print("\nPlease enter the ID for the employee you wish to assign it to:");
			String EmployeeID = ask();
			
			if (ProjectID.charAt(0)==('P') && Integer.parseInt(ProjectID.substring(1))>100)
			{
				if (EmployeeID.charAt(0)==('E') && Integer.parseInt(EmployeeID.substring(1))>200)
				{
					valid = true;
					assignHelper(ProjectID,EmployeeID);
				}
				else
				{
					print("\nIncorrect format: Employee ID should be in the form E2XX, where 2XX is an integr greater than 200.");
				}
			}
			else
			{
				print("\nIncorrect format: Project ID should be in the form P1XX, where 1XX is an integr greater than 100.");
			}
		}
	}
	// This method checks for the existence of the project and employee supplied
	public boolean assignHelper(String ProjectID, String EmployeeID)
	{
		boolean p = isProject(ProjectID);
		boolean e = isEmployee(EmployeeID);
		if(p && e)
			return assignHelper2(ProjectID, EmployeeID);
		else if (!p && e)
		{
			print("Project not found on records.");
			return false;
		}
		else if (p && !e)
		{
			print("Employee not found on records.");
			return false;
		}
		return false;
	}
	
	// This method checks if the project is already assigned and then assigns accordingly
	private boolean assignHelper2(String ProjectID, String EmployeeID)
	{
		if(isAssigned(AssignedEmployeesList, EmployeeID))
		{
			Assigned eList = getAssignedList('e',EmployeeID);
			if (eList.isCompanion(ProjectID))
			{
				print("The project is already assigned to this employee");
				return false;
			}
			else
			{
				eList.getCompanions().add(ProjectID);
				if(isAssigned(AssignedProjectsList, ProjectID))
				{
					Assigned pList = getAssignedList('p',ProjectID);
					pList.getCompanions().add(EmployeeID);
				}
				else
				{
					AssignedProjectsList.add(new Assigned(ProjectID,EmployeeID));
				}
				print("Project has been assigned to the employee");
				return true;
			}
		}
		else
		{
			AssignedEmployeesList.add(new Assigned(EmployeeID, ProjectID));
			if(isAssigned(AssignedProjectsList, ProjectID))
			{
				Assigned pList = getAssignedList('p',ProjectID);
				pList.getCompanions().add(EmployeeID);
			}
			else
			{
				AssignedProjectsList.add(new Assigned(ProjectID,EmployeeID));
			}
			print("Project has been assigned to the employee");
			return true;
		}
	}
	// Works in the same way as the original with the exception that the project ID is given
	private void assignProject(String ProjectID)
	{
		boolean valid = false;
		
		while (!valid)
		{
			print("\nPlease enter the ID for the employee you wish to assign it to:");
			String EmployeeID = ask();
			
			if (EmployeeID.charAt(0)==('E') && Integer.parseInt(EmployeeID.substring(1))>200)
			{
				valid = true;
				assignHelper(ProjectID,EmployeeID);
			}
			else
			{
				print("\nIncorrect format: Employee ID should be in the form E2XX, where 2XX is an integr greater than 200.");
			}
		}
	}
	
	//
	public void displayProjects()
	{
		if(ProjectList.size()>0)
		{
			for (Project p : ProjectList)
			{
				if(isAssigned(AssignedProjectsList,p.getID()))
				{
					print(p.toString());
					print(">> Assigned Team Members:");
					for (String empID : getAssignedList('p',p.getID()).getCompanions())
					{
						Employee e = getEmployee(empID);
						print("\nName: "+e.get("name")+"\tID: "+e.getID()+"\nRole: "+e.get("role"));
					}
				}
				else
				{
					print(p.toString());
					print(">> There are no assigned team members.");
				}
			}
		}
		else
		{
			print ("There are no projects on record.");
		}
	}
	
	//Displays the names and roles of employees assigned to a given project
	public void AssignedEmployees(String ProjectID)
	{
		if (AssignedProjectsList.size() > 0)
		{
			for (Assigned project : AssignedProjectsList)
			{
				if(project.getID().equals(ProjectID))
				{
					print("\nTeam members assigned to this project are:\n");
					for (String employee : project.getCompanions())
					{
						Employee e = getEmployee(employee);
						print("Name: "+e.get("name")+"\nRole: "+e.get("role"));
						
					}
					break;
				}
			}
		}
		else
		{
			print ("No projects have been assigned to any team member");
		}
	}
	
	//Displays the projects that are assigned to an employee along with other team members
	public void AssignedProjects(String EmployeeID)
	{
		if (AssignedEmployeesList.size() > 0)
		{
			for(Assigned employee : AssignedEmployeesList)
			{
				if (employee.getID().equals(EmployeeID))
				{
					print("Projects assigned to this employee:\n");
					for(String project : employee.getCompanions())
					{
						Project p = getProject(project);
						print(p.toString());
						AssignedEmployees(project);	
					}
					break;
				}
			}
		}
		else
		{
			print("No projects have been assigned to any team member.");
		}
	}
	
	//Gives the user the option to edit project details or change assignments
	public void updateProject()
	{
		print("Please enter the ID for the project you wish to modify:\n");
		String choice = ask(); //correct input assumed
		
		if(isProject(choice))
		{
			Project p = getProject(choice);

			print("The current details are:");
			print(p.toString());
			AssignedEmployees(choice);
			print("/////////////////////////////////\n");

			print(""+
					"\nPlease select an option:"+
					"\n( 1 ) to update project details"+
					"\n( 2 ) to change project assignments"+
					"\n\n(0) to exit");
			int option = askInt();

			if(option == 1)
			{
				print("Please enter the new details of the project\n");

				print("Name:");
				p.setName(ask());

				print("\nDescription:");
				p.setDescription(ask());

				print("\nStart Date:");
				p.setStart(ask());

				print("\nEnd Date:");
				p.setEnd(ask());
			}
			else if (option == 2)
			{
				print(""+
						"\nPlease select an option:"+
						"\n( 3 ) to assign an additional team member"+
						"\n( 4 ) to reassign team members");
				int updateOption = askInt();

				switch(updateOption)
				{
				case 3:
				{
					assignProject(choice);
					break;
				}
				case 4:
				{
					deleteAssignments(choice);
					print("How many employees do you wish to assign the project to?");
					int team = askInt();
					for (int i = 1; i <= team; i++)
					{
						assignProject(choice);
					}
					break;
				}
				}
			}
			else if (option == 0)
			{
				print ("exiting...");
			}
		}
		else
			print("Project does not exist...");
	}
	
	//Removes all project assignments and then deletes project
	public void deleteProject(String ProjectID)
	{
		if(isProject(ProjectID))
		{
			deleteAssignments(ProjectID);
			for (Project delete : ProjectList)
			{
				if(delete.getID().equals(ProjectID))
				{
					ProjectList.remove(delete);
					print("Project has been successfully deleted.");
					break;
				}
			}
		}
		else
			print("There is no record of the project on the system.");
	}
	
	//removes all assignments for a designated project
	public void deleteAssignments(String ProjectID)
	{
		if(isAssigned(AssignedProjectsList,ProjectID))
		{
			ArrayList<Assigned> toRemove = new ArrayList<Assigned>(); //created to temporarily hold items that need ProjectID removed
			for (Assigned employee : AssignedEmployeesList)
				if(employee.isCompanion(ProjectID))
					toRemove.add(employee);
			for (int i = 0; i < toRemove.size(); i++)
			{
				toRemove.get(i).getCompanions().remove(ProjectID);
				if (toRemove.get(i).getCompanions().isEmpty())
					AssignedEmployeesList.remove(toRemove.get(i));
			}
			
			AssignedProjectsList.remove(getAssignedList('p',ProjectID));
			print("All assignments for this project has been removed.");
		}
		else
			print("No assignments found for this project.");
	}
	
	
	//////////////////////////////////////////////////////////
	
	/*
	 * Utility Methods
	 */
	
	//////////////////////////////////////////////////////////
	
	public void print(Object x)
	{
		System.out.println(x);
	}
	
	public String ask()
	{
		return input.nextLine();
	}
	
	public int askInt()
	{
		return input2.nextInt();
	}
	
	//checks if a project exists using the project ID
	private boolean isProject(String ID)
	{
		for (Project p : ProjectList)
		{
			if(p.getID().equals(ID))
			{
				return true;
			}
		}
		return false;
	}
	//checks if a an employee exists using the employee ID
	private boolean isEmployee(String ID)
	{
		for (Employee e : EmployeeList)
		{
			if(e.getID().equals(ID))
			{
				return true;
			}
		}
		return false;
	}
	//Checks if a project or employee is assigned
	private boolean isAssigned(ArrayList<Assigned> aList, String ID)
	{
		for (Assigned a : aList)
		{
			if(a.getID().equals(ID))
			{
				return true;
			}
		}
		return false;
	}
	//returns an employee or project ID with its companions
	private Assigned getAssignedList(char type, String ID)
	{
		if (type == 'E' || type == 'e')
		{
			for (Assigned employee : AssignedEmployeesList)
				if (employee.getID().equals(ID))
					return employee;
		}
		else if (type == 'P' || type == 'p')
		{
			for (Assigned project : AssignedProjectsList)
				if (project.getID().equals(ID))
					return project;
		}
		return null;
	}
	
	//////////////////////////////////////////////////////////
		
	/*
	* TESTER METHODS
	* Create Projects and Employees from arrays
	*/
	
	//////////////////////////////////////////////////////////
	
	public void create(String[][] pList, String[][] eList)
	{
		for (String[] p : pList)
		{
			ProjectList.add(new Project(p[0],p[1],p[2],p[3]));
		}
		for (String[] e : eList)
		{
			EmployeeList.add(new Employee(e[0],e[1],e[2],e[3]));
		}
	}
	
	//This method has been integrated into the application
	public void displayEmployees()
	{
		if(EmployeeList.size() == 0)
			print("There are no employees on record.");
		
		for (Employee e : EmployeeList)
		{
			print(e.toString());
			if(isAssigned(AssignedEmployeesList, e.getID()))
			{
				print("Assigned projects:");
				for (String project : getAssignedList('e', e.getID()).getCompanions())
				{
					System.out.print(project+" ");
				}
				print("");
			}
			else
				print("No assigned projects...");
		}
	}
	
	/*
	 * This method checks if the project is already assigned
	 * and then assigns accordingly
	 * Assigns project to employee
	 */
	public boolean assign(String ProjectID, String EmployeeID)
	{
		if(isAssigned(AssignedEmployeesList, EmployeeID))
		{
			Assigned eList = getAssignedList('e',EmployeeID);
			if (eList.isCompanion(ProjectID))
			{
				print("The project is already assigned to this employee");
				return false;
			}
			else
			{
				eList.getCompanions().add(ProjectID);
				if(isAssigned(AssignedProjectsList, ProjectID))
				{
					Assigned pList = getAssignedList('p',ProjectID);
					pList.getCompanions().add(EmployeeID);
				}
				else
				{
					AssignedProjectsList.add(new Assigned(ProjectID,EmployeeID));
				}
				print("Project has been assigned to the employee");
				return true;
			}
		}
		else
		{
			AssignedEmployeesList.add(new Assigned(EmployeeID, ProjectID));
			if(isAssigned(AssignedProjectsList, ProjectID))
			{
				Assigned pList = getAssignedList('p',ProjectID);
				pList.getCompanions().add(EmployeeID);
			}
			else
			{
				AssignedProjectsList.add(new Assigned(ProjectID,EmployeeID));
			}
			print("Project has been assigned to the employee");
			return true;
		}
	}
	
	//
}
