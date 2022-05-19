package appPackage;

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

public class LoginPage extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JButton managerButton, customerButton, animatorButton;
	private Color customBlue;
	private Dimension screenSize;
	private int width, height;
	private Container container;
	private LoginPageEventHandler handler;
	private ManagerLoginPage managerLoginPage;
	private CustomerLoginPage customerLoginPage;
	private AnimatorLoginPage animatorLoginPage;
	
	public LoginPage()
	{
		container = getContentPane();
		
		container.setLayout(null);
		
		// Creating a custom blue color
		customBlue = new Color(0, 168, 225);
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)screenSize.getWidth();
        height = (int)screenSize.getHeight();
        
        setTitle("Login Page");
        setSize(1920, 1080);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(false);

        // Preventing buttons from changing color when clicked
		UIManager.put("Button.select", customBlue);
		
		managerButton = new JButton("Manager");
		managerButton.setBounds(width/2 - 175, height/2 - 200, 350, 100);
		managerButton.setForeground(Color.WHITE);
		managerButton.setBackground(customBlue);
		managerButton.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		managerButton.setBorder(null);
		managerButton.setFocusPainted(false);
		
		customerButton = new JButton("Customer");
		customerButton.setBounds(width/2 - 175, height/2 - 50, 350, 100);
		customerButton.setForeground(Color.WHITE);
		customerButton.setBackground(customBlue);
		customerButton.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		customerButton.setBorder(null);
		customerButton.setFocusPainted(false);
		
		animatorButton = new JButton("Animator");
		animatorButton.setBounds(width/2 - 175, height/2 + 100, 350, 100);
		animatorButton.setForeground(Color.WHITE);
		animatorButton.setBackground(customBlue);
		animatorButton.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		animatorButton.setBorder(null);
		animatorButton.setFocusPainted(false);
		
		handler = new LoginPageEventHandler();
		
		managerLoginPage = new ManagerLoginPage();
		customerLoginPage = new CustomerLoginPage();
		animatorLoginPage = new AnimatorLoginPage();
		
		container.add(managerButton);
		container.add(customerButton);
		container.add(animatorButton);
		
		managerButton.addActionListener(handler);
		customerButton.addActionListener(handler);
		animatorButton.addActionListener(handler);
	}
	
	public class LoginPageEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) 
		{
			try {
				if (event.getSource() == managerButton) {
					managerLoginPage.setVisible(true);
				}
				
				else if (event.getSource() == customerButton) {
					customerLoginPage.setVisible(true);
				}
				
				else if (event.getSource() == animatorButton) {
					animatorLoginPage.setVisible(true);
				}
			}
			catch(Exception genericException) {
				JOptionPane.showMessageDialog(null, "Unidentified error occured.");
			}	
			
		}
	}
}
