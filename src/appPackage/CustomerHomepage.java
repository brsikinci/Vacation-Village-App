package appPackage;

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

public class CustomerHomepage extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton addButton, previousPageButton;
	private JRadioButton massActivityRadioButton, individualActivityRadioButton;
	private JLabel notificationText;
	private Color customBlue;
	private Dimension screenSize;
	private int width, height;
	private Container container;
	private CustomerHomepageEventHandler handler;
	private final int numberOfFields = 3;
	private JLabel[] labels;
	private JTextField[] textFields;
	private ActivityType activityType;
	private String[] labelNames = {"Name", "Date", "Hour"};
	private Database database;
	private SQLCommands command;
	private String contactPhone;
	
	public CustomerHomepage()
	{
		setTitle("Customer Homepage");
		setSize(1920, 1080);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(false);
		
		container = getContentPane();
		container.setLayout(null);
		customBlue = new Color(0, 168, 225);
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		
		UIManager.put("Button.select", customBlue);
		
		labels = new JLabel[numberOfFields];
		textFields = new JTextField[numberOfFields];
		
		handler = new CustomerHomepageEventHandler();
		database = new Database();
		command = new SQLCommands();

		int heightAdjuster = 275;
		for(int i = 0; i < numberOfFields; i++) {
			labels[i] = new JLabel("");
			labels[i].setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
			labels[i].setVisible(false);
			labels[i].setBounds(width/2 - 175, height/2 - heightAdjuster, 350, 35);
			heightAdjuster -= 100;
		}
		
		heightAdjuster = 240;
		for(int i = 0; i < numberOfFields; i++) {
			textFields[i] = new JTextField();
			textFields[i].setVisible(false);
			textFields[i].setBounds(width/2 - 175, height/2 - heightAdjuster, 350, 35);
			heightAdjuster -= 100;
		}
				
		addButton = new JButton("Add");
		addButton.setBounds(width/2 - 175, height/2 + 200, 350, 100);
		addButton.setForeground(Color.WHITE);
		addButton.setBackground(customBlue);
		addButton.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		addButton.setBorder(null);
		addButton.setFocusPainted(false);
		
		previousPageButton = new JButton("Back");
		previousPageButton.setBounds(20, 20, 100, 50);
		previousPageButton.setForeground(Color.WHITE);
		previousPageButton.setBackground(customBlue);
		previousPageButton.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		previousPageButton.setBorder(null);
		previousPageButton.setFocusPainted(false);
		
		massActivityRadioButton = new JRadioButton("Mass Activity", false);
		massActivityRadioButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		massActivityRadioButton.setBounds(width/2 - 700, height/2 - 390, 200, 40);
		
		individualActivityRadioButton = new JRadioButton("Individual Activity", false);
		individualActivityRadioButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		individualActivityRadioButton.setBounds(width/2 + 500, height/2 - 390, 400, 40);
		
		notificationText = new JLabel("");
		notificationText.setBounds(width/2 - 180, height/2 - 450, 380, 250);
		notificationText.setForeground(Color.BLACK);
		notificationText.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		
		for(int i = 0; i < numberOfFields; i++) {
			container.add(labels[i]);
			container.add(textFields[i]);
		}
		
		container.add(massActivityRadioButton);
		container.add(individualActivityRadioButton);
		container.add(addButton);
		container.add(previousPageButton);
		
		massActivityRadioButton.addActionListener(handler);
		individualActivityRadioButton.addActionListener(handler);
		addButton.addActionListener(handler);
		previousPageButton.addActionListener(handler);
	}

	public class CustomerHomepageEventHandler implements ActionListener {		
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == addButton) {
				if(database.customerHasAppointment(contactPhone, textFields[0].getText())) {
					notificationText.setText("You already have an appointment at this time");
					notificationText.setForeground(Color.RED);
					return;
				}
				
				if(activityType == ActivityType.MASS) {
					if(database.capacityFull(textFields[0].getText())) {
						notificationText.setText("Capacity is full");
						notificationText.setForeground(Color.RED);
						return;
					}
				}
				
				else if(activityType == ActivityType.INDIVIDUAL) {
					if(database.getCustomerAge(contactPhone) < database.getActivityAgeRequirement(textFields[0].getText())) {
						notificationText.setText("Age requirement is not satisfied " + String.valueOf(database.getActivityAgeRequirement(textFields[0].getText())));
						notificationText.setForeground(Color.RED);
						return;
					}
				}
				
				int customerID = database.getCustomerID(contactPhone);
				int animatorID = database.getAnimatorIdFromActivity(textFields[0].getText());
				int activityID = database.getActivityID(textFields[0].getText());
				String dateTime = "";
				
				if(activityType == ActivityType.MASS)
					dateTime += (textFields[1].getText() + " " + textFields[2].getText());
				else if(activityType == ActivityType.INDIVIDUAL)
					dateTime += (textFields[1].getText() + " " + textFields[2].getText());
				
				if(customerID != -1 && activityID != -1 && animatorID != -1)
					database.insert(command.insertAppointment(customerID, activityID, animatorID, dateTime));
			}
			
			else if (event.getSource() == previousPageButton) {
				setVisible(false);
			}
			
			else if(event.getSource() == massActivityRadioButton) {		
				activityType = ActivityType.MASS;
				
				individualActivityRadioButton.setSelected(false);
				
				for(int i = 0; i < numberOfFields; i++) {
					labels[i].setText(labelNames[i]);
					labels[i].setVisible(true);
					textFields[i].setVisible(true);					
				}
			}
			
			else if(event.getSource() == individualActivityRadioButton) {
				activityType = ActivityType.INDIVIDUAL;
				
				massActivityRadioButton.setSelected(false);
				
				for(int i = 0; i < numberOfFields; i++) {
					labels[i].setText(labelNames[i]);
					labels[i].setVisible(true);
					textFields[i].setVisible(true);					
				}
			}
		}
	}
	
	public void setContactPhone(String contactPhone) 
	{
		this.contactPhone = contactPhone;
	}
}
