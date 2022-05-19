package appPackage;

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;


public class ManagerLoginPage extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton signInButton, previousPageButton;
	private JTextField usernameTextField, passwordTextField;
	private JLabel usernameText, passwordText, loginErrorText;
	private Color customBlue;
	private Dimension screenSize;
	private int width, height;
	private Container container;
	private ManagerLoginEventHandler handler;
	private ManagerHomepage managerHomepage;
	private Database database;
	
	public ManagerLoginPage() {
		container = getContentPane();
		
		container.setLayout(null);
		
		// Creating a custom blue color
		customBlue = new Color(0, 168, 225);
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)screenSize.getWidth();
        height = (int)screenSize.getHeight();
        
        setTitle("Manager Login Page");
        setSize(1920, 1080);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(false);
		
		 // Creating the text fields
		usernameTextField = new JTextField();
		usernameTextField.setBounds(width/2 - 175, height/2 - 240, 350, 35);
		usernameTextField.setForeground(Color.BLACK);
		usernameTextField.setBackground(Color.WHITE);
		usernameTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		
		passwordTextField = new JTextField();
		passwordTextField.setBounds(width/2 - 175, height/2 - 140, 350, 35);
		passwordTextField.setForeground(Color.BLACK);
		passwordTextField.setBackground(Color.WHITE);
		passwordTextField.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
				
		// Creating the necessary texts
		usernameText = new JLabel("Username");
		usernameText.setBounds(width/2 - 175, height/2 - 385, 200, 250);
		usernameText.setForeground(Color.BLACK);
		usernameText.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		
		passwordText = new JLabel("Password");
		passwordText.setBounds(width/2 - 175, height/2 - 285, 200, 250);
		passwordText.setForeground(Color.BLACK);
		passwordText.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		
		loginErrorText = new JLabel("");
		loginErrorText.setBounds(width/2 - 180, height/2 - 450, 380, 250);
		loginErrorText.setForeground(Color.BLACK);
		loginErrorText.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		
		signInButton = new JButton("Sign In");
		signInButton.setBounds(width/2 - 175, height/2 + 10, 350, 100);
		signInButton.setForeground(Color.WHITE);
		signInButton.setBackground(customBlue);
		signInButton.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		signInButton.setBorder(null);
		signInButton.setFocusPainted(false);
		
		previousPageButton = new JButton("Back");
		previousPageButton.setBounds(20, 20, 100, 50);
		previousPageButton.setForeground(Color.WHITE);
		previousPageButton.setBackground(customBlue);
		previousPageButton.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		previousPageButton.setBorder(null);
		previousPageButton.setFocusPainted(false);
		
		handler = new ManagerLoginEventHandler();
		managerHomepage = new ManagerHomepage();
		database = new Database();
		
		container.add(signInButton);
		container.add(usernameTextField);
		container.add(passwordTextField);
		container.add(usernameText);
		container.add(passwordText);
		container.add(loginErrorText);
		container.add(previousPageButton);
		
		signInButton.addActionListener(handler);
		previousPageButton.addActionListener(handler);
	}
	
	public class ManagerLoginEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) 
		{
			try {
				if (event.getSource() == signInButton) {
					if(database.connectionSuccessful()) {
						if(database.userExists(usernameTextField.getText(), PersonToBeAdded.MANAGER)) {
							if(passwordTextField.getText().equals(database.getPassword(usernameTextField.getText(), PersonToBeAdded.MANAGER)))
								managerHomepage.setVisible(true);
							else {
								loginErrorText.setText("Incorrect username or password");
								loginErrorText.setForeground(Color.RED);
							}
						}
						else {
							loginErrorText.setText("Incorrect username or password.");
							loginErrorText.setForeground(Color.RED);
						}
					}
				}
				
				else if (event.getSource() == previousPageButton) {
					setVisible(false);
				}
			}
			catch(Exception genericException) {
				JOptionPane.showMessageDialog(null, "Unidentified error occured.");
			}	
			
		}
	}
}
