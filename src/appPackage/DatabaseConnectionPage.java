package appPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DatabaseConnectionPage extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton connectButton;
	private Color customBlue;
	private JLabel connectionText;
	private Dimension screenSize;
	private int width, height;
	private Container container;
	private ConnectDatabaseEventHandler handler;
	private LoginPage loginPage;
	
	public DatabaseConnectionPage() {
		container = getContentPane();
		
		container.setLayout(null);
		
		// Creating a custom blue color
		customBlue = new Color(0, 168, 225);
		
		// Get screen width and height
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)screenSize.getWidth();
        height = (int)screenSize.getHeight();
		
        // Preventing buttons from changing color when clicked
		UIManager.put("Button.select", customBlue);
 			
		// Creating the connect button
		connectButton = new JButton("Connect");
		connectButton.setBounds(width/2 - 175, height/2 - 50, 350, 100);
		connectButton.setForeground(Color.WHITE);
		connectButton.setBackground(customBlue);
		connectButton.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		connectButton.setBorder(null);
		connectButton.setFocusPainted(false);
		
		connectionText = new JLabel("");
		connectionText.setBounds(width/2 - 215, height/2, 500, 250);
		
		handler = new ConnectDatabaseEventHandler();
		loginPage = new LoginPage();
		
		// Adding components to the container
		container.add(connectButton);
		container.add(connectionText);
		connectButton.addActionListener(handler);
	}
	
	public class ConnectDatabaseEventHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) 
		{
			if (event.getSource() == connectButton) {
				Database database = new Database();
				try {
					if (database.connectionSuccessful()) {
						connectionText.setText("Connection to Database Successful");
						connectionText.setForeground(Color.GREEN);
						connectionText.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
						setVisible(false);
						loginPage.setVisible(true);
					}
					
		            else {
		            	connectionText.setText("Connection to Database Failed");
						connectionText.setForeground(Color.RED);
						connectionText.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		            }
				}
				catch(Exception genericException) {
					JOptionPane.showMessageDialog(null, "Unidentified error occured.");
				}	
			}
		}
	}
}



