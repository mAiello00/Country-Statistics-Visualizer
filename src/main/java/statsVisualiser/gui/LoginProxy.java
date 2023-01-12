package statsVisualiser.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginProxy implements ActionListener
{
	private JLabel usernameLabel;
	private JTextField usernameText;
	private JLabel passwordLabel;
	private JPasswordField passwordText;
	private JButton loginButton;
	private boolean isValidLoginCredentials = false;
	
	/**
	 * Creates the interface that the user sees when they attempt to log into the system.
	 * This is the proxy, so there is no actual calculation that occurs in this class.
	 * The purpose of this class is strictly to create the login window and allow the user to enter their credentials.
	 * The actual validation occurs in the ConcreteLoginValidator class
	 */
	public void createLogin()
	{
		
		
		JFrame frame = new JFrame();//creates the window
		JPanel panel = new JPanel();//for the layout of the window
		frame.setSize(350, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(panel);
		
		panel.setLayout(null);
		
		//Sets the username label for the panel
		usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(10, 20, 80, 25);
		panel.add(usernameLabel);
		
		//Sets the username text field so the user can enter text for their username
		usernameText = new JTextField();
		usernameText.setBounds(100, 20, 165, 25);
		panel.add(usernameText);
		
		//Sets the password label for the panel
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10,50,80,25);
		panel.add(passwordLabel);
		
		//Sets the password text field so the user can enter text for their password
		passwordText = new JPasswordField();
		passwordText.setBounds(100, 50, 165, 25);
		panel.add(passwordText);
		
		//Creates the button the user clicks when they have entered their login credentials
		loginButton = new JButton("Login");
		loginButton.setBounds(10, 80, 80, 25);
		panel.add(loginButton);
		loginButton.addActionListener(new LoginProxy());
		
		//The Action Listener is the method that is executed when the user clicks the login button
		loginButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				//Gets the username and password a a strind
				String user = usernameText.getText();
				String pass = passwordText.getText();
				String credentials = user +":"+pass;
				
				//Creates a new Concrete Validator object
				ConcreteLoginValidator validator = new ConcreteLoginValidator(credentials);
		
				try 
				{
					/**
					 * Sends the login information over to the Concrete Login Validator 
					 */
					isValidLoginCredentials = validator.checkLogin();
					
					if(isValidLoginCredentials)
					{	
						/**
						 * If the credentials are correct then qe can execute the main UI code
						 */
						JFrame UIFrame = MainUI.getInstance();
						UIFrame.setSize(900, 600);
						UIFrame.pack();
						UIFrame.setVisible(true);
					}
					else if(!isValidLoginCredentials)
					{
						/**
						 * If the login credentials are not correct then we create a pop-up stating that the 
						 * credentials are wrong and terminate the program once the button is clicked on the pop-up
						 */
						JFrame invalidFrame = new JFrame();
						JPanel invalidPanel = new JPanel();
						invalidFrame.setSize(350,200);
						invalidFrame.add(invalidPanel);
						invalidPanel.setLayout(null);
						
						JLabel errorMessage = new JLabel("");
						errorMessage.setBounds(10, 50, 80, 25);
						invalidPanel.add(errorMessage);
						
						JButton errorButton = new JButton("Incorrect Credentials");
						errorButton.setBounds(100, 50, 165, 25);
						invalidPanel.add(errorButton);
						
						errorButton.addActionListener(new ActionListener()
								{

									public void actionPerformed(ActionEvent e) 
									{
										System.exit(0);				
									}
								});
						
						invalidFrame.setVisible(true);
					}
				} 
				catch (FileNotFoundException e1) 
				{
					e1.printStackTrace();
				}		
			}
		});
		
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
	}
	
	public boolean getValidity()
	{
		System.out.println(isValidLoginCredentials);
		return isValidLoginCredentials;
	}
	
}
