package statsVisualiser.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConcreteLoginValidator 
{
	/**
	 * 
	 */
	private String loginCredentials;
	private String line = "";
	
	/**
	 * Gets the credentials from the proxy login validator
	 * @param credentials sets the login credentials 
	 */
	public ConcreteLoginValidator(String credentials)
	{
		loginCredentials = credentials;
	}
	
	/**
	 * Compare the login credentials passed to the validator with the login credentials in the 
	 * database. If there is a match, then we return true
	 * @return true if the login credentials match a set of credentials n the database
	 * @throws FileNotFoundException if the file cannot be found
	 */
	public boolean checkLogin() throws FileNotFoundException
	{
		File file = new File("Login_Database.txt");//gets the file
		Scanner scan = new Scanner(file);
		//go through each line of the file and compare it to hte passed credentials
		while(scan.hasNextLine())
		{
			line = scan.nextLine();
			
			if(loginCredentials.equals(line))
			{
				return true;
			}
		}
		
		return false;
	}
}