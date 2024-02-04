//package prework;
import java.util.Scanner;

public class Application {
	
	private static Management_System mgmt = new Management_System();
	
	//Driver method for starting the application
	public static void main(String[] args)
	{
		start();
	}
	
	//Main Menu - Navigation hub
	private static void start()
	{		
		System.out.println(""+
				"\n#######################################################"+
				"\n#                   Company XYZ                       #"+
				"\n#       Project and Employee Management System        #"+
				"\n#######################################################"+
				"\n_______________________________________________________"+
				"\n\n                    Main Menu"+
				"\n"+
				"\n-------------------------------------------------------"+
				"\nPlease note that you will need the Employee ID and/or "+
				"\nProject ID for most operations."+
				"\nThese can be viewed using menu options 1 and 2."+
				"\n-------------------------------------------------------"+
				"\n1. View Employees"+
				"\n2. View Projects"+
				"\n3. Input Employee Data"+
				"\n4. Input Project Data"+
				"\n5. Assign Employees to Projects"+
				"\n6. Update Project Details"+
				"\n7. Project Search (shows assigned employees)"+
				"\n8. Employee Search (shows assigned projects)"+
				"\n9. Delete Project"+
				"\n"+
				"\n0. Exit"+
				"\n"+
				"");
		
		/////////
		boolean valid = false;
		int nav = -1;

		while (!valid)
		{
			try 
			{
				System.out.print("Please select a number option from the menu\n> ");
				nav = askInt();
				valid = (nav < 10 && nav >= 0);
			}
			catch(Exception e)
			{
				System.out.println("Invalid entry: you must enter a whole number");
				ask();
			}
		}
		
		switch(nav)
		{
		case 0:
			System.exit(0);
		
		case 1:
		{
			viewEmployees();
			break;
		}	
		
		case 2:
		{
			viewProjects();
			break;
		}
		
		case 3:
		{
			inputEmployees();
			break;
		}
		
		case 4:
		{
			inputProjects();
			break;
		}
		
		case 5:
		{
			assignProjects();
			break;
		}
		
		case 6:
		{
			updateProjects();
			break;
		}
		
		case 7:
		{
			projectSearch();
			break;
		}
		
		case 8:
		{
			employeeSearch();
			break;
		}
		
		case 9:
		{
			deleteProject();
			break;
		}
		}
	}
	//
	private static void viewEmployees()
	{
		mgmt.displayEmployees();
		toStart(); //return to main menu
	}
	//
	private static void viewProjects()
	{
		mgmt.displayProjects();
		toStart(); //return to main menu
	}
	//
	private static void inputEmployees()
	{
		print("How many employees are you going to add? \n(Enter 0 to cancel)\n");
		int num = askInt();
		
		if(num != 0)
		{
			mgmt.createEmployees(num);
		}
		
		toStart(); //return to main menu
	}
	//
	private static void inputProjects()
	{
		print("How many projects are you going to add? \n(Enter 0 to cancel)\n");
		int num = askInt();
		
		if(num != 0)
		{
			mgmt.createProjects(num);
		}
		
		toStart(); //return to main menu
	}
	//
	private static void assignProjects()
	{
		boolean valid = false;
		String employeeID = "", projectID = "";
		
		while (!valid)
		{
			print("Please enter the ID for the employee you wish to assign projects to:");
			employeeID = ask();
			
			if (employeeID.charAt(0)==('E') && Integer.parseInt(employeeID.substring(1))>200)
				valid = true;
			
			else
				print("\nIncorrect format: Employee ID should be in the form E2XX, where 2XX is an integer greater than 200.");
		}
		
		print ("\nHow many projects do you wish to assign to this employee?");
		int num = askInt();
		
		for (int i = 1; i <= num; i++)
		{
			valid = false;
			
			while (!valid)
			{
				print("Please enter the ID for project "+i+" of "+num+", that you wish to assign to this employee:");
				projectID = ask();
				
				if (projectID.charAt(0)==('P') && Integer.parseInt(projectID.substring(1))>100)
					valid = true;
				
				else
					print("\nIncorrect format: Project ID should be in the form P1XX, where 1XX is an integer greater than 100.");
			}
			
			if(!mgmt.assignHelper(projectID,employeeID))
			{
				print(""
						+ "Project Assignment Failed..."
						+ "\nPlease ensure that the project and employee exist on the system.");
				if(i!= num)
				{
					print(""
							+ "\nIf you wish to cancel and start over, Enter 0..."
							+ "\nOtherwise, enter 1 to continue...");
					int choice = askInt();
					
					if (choice == 0)
						break;
					else
						print(">>>\n");
				}
			}
			else
			{
				print("Project has been assigned successfully.\n>>>");
			}
		}
		
		toStart(); //return to main menu
	}
	//
	private static void updateProjects()
	{
		print("Please note that this application can only change project assignments from the project perspective");
		pause();
		
		mgmt.updateProject();
		
		toStart(); //return to main menu
	}
	//
	private static void projectSearch()
	{
		print("Please enter the ID of the project you wish to search for");
		String projectID = ask();
		
		mgmt.AssignedEmployees(projectID);
		
		toStart(); //return to main menu
	}
	//
	private static void employeeSearch()
	{
		print("Please enter the ID of the employee you wish to search for");
		String employeeID = ask();
		
		mgmt.AssignedProjects(employeeID);
		
		toStart(); //return to main menu
	}
	//
	private static void deleteProject()
	{
		print(""
				+ "\nPlease enter the ID for the project you wish to delete..."
				+ "\nEnter 'C' if you wish to cancel...");
		String projectID = ask();
		
		if (!projectID.equalsIgnoreCase("C"))
		{
			mgmt.deleteProject(projectID);
		}
		
		toStart(); //return to main menu
	}
	
	/*
	 * Utility Methods
	 */
	private static void print(Object x)
	{
		System.out.println(x);
	}
	
	private static String ask()
	{
		@SuppressWarnings("resource")
		Scanner input = new Scanner (System.in);
		return input.nextLine();
	}
	
	private static int askInt()
	{
		@SuppressWarnings("resource")
		Scanner input2 = new Scanner (System.in);
		return input2.nextInt();
	}
	
	private static void toStart()
	{
		print(">>>\n");
		print("Press the enter key to return to the main menu...");
		ask();
		start();
	}
	
	private static void pause()
	{
		print("Press the enter key to continue...");
		ask();
	}
}
