package appPackage;

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

enum ActivityType {
	MASS,
	INDIVIDUAL
}

enum OtherType {
	EQUIPMENT,
	EMERGENCY_INFO
}

enum ActivityOrOther {
	ACTIVITY,
	OTHER
}

public class AnimatorHomepage extends JFrame{
	private static final long serialVersionUID = 1L;
	private JButton addButton, deleteButton, previousPageButton;
	private JRadioButton activityRadioButton, equipmentRadioButton, emergencyInfoRadioButton, massActivityRadioButton, individualActivityRadioButton;
	private JLabel notificationText;
	private Color customBlue;
	private Dimension screenSize;
	private int width, height;
	private Container container;
	private AnimatorHomepageEventHandler handler;
	private final int numberOfFields = 6;
	private JLabel[] labels;
	private JTextField[] textFields;
	private ActivityType activityType;
	private OtherType otherType;
	private ActivityOrOther entityType;
	private SQLCommands command;
	private Database database;
	private String contactPhone;
	private String[] massActivityLabels = {"Name", "Activity Type", "Internet Link", "Capacity", "Date", "Hour"};
	private String[] individualActivityLabels = {"Name", "Internet Link", "Age Requirement"};
	private String[] emergencyInfoLabels = {"Activity Name", "Locker Number", "Phone Number"};
	private String[] equipmentLabels = {"Name", "Purpose", "Equip Person SSN"};

	
	public AnimatorHomepage()
	{
		setTitle("Animator Homepage");
		setSize(1920, 1080);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(false);
		
		container = getContentPane();
		container.setLayout(null);
		customBlue = new Color(0, 168, 225);
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();

		labels = new JLabel[numberOfFields];
		textFields = new JTextField[numberOfFields];
		
		handler = new AnimatorHomepageEventHandler();
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
		addButton.setBounds(width/2 - 375, height/2 + 335, 350, 100);
		addButton.setForeground(Color.WHITE);
		addButton.setBackground(customBlue);
		addButton.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		addButton.setBorder(null);
		addButton.setFocusPainted(false);
		
		deleteButton = new JButton("Delete");
		deleteButton.setBounds(width/2 + 25, height/2 + 335, 350, 100);
		deleteButton.setForeground(Color.WHITE);
		deleteButton.setBackground(customBlue);
		deleteButton.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		deleteButton.setBorder(null);
		deleteButton.setFocusPainted(false);
		
		previousPageButton = new JButton("Back");
		previousPageButton.setBounds(20, 20, 100, 50);
		previousPageButton.setForeground(Color.WHITE);
		previousPageButton.setBackground(customBlue);
		previousPageButton.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		previousPageButton.setBorder(null);
		previousPageButton.setFocusPainted(false);
		
		notificationText = new JLabel("");
		notificationText.setBounds(width/2 - 180, height/2 - 450, 380, 250);
		notificationText.setForeground(Color.BLACK);
		notificationText.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		
		activityRadioButton = new JRadioButton("Activity", false);
		activityRadioButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		activityRadioButton.setBounds(width/2 - 700, height/2 - 390, 200, 40);
		
		equipmentRadioButton = new JRadioButton("Equipment", false);
		equipmentRadioButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		equipmentRadioButton.setBounds(width/2 - 100, height/2 - 390, 200, 40);
		
		emergencyInfoRadioButton = new JRadioButton("Emergency Information", false);
		emergencyInfoRadioButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		emergencyInfoRadioButton.setBounds(width/2 + 500, height/2 - 390, 300, 40);

		massActivityRadioButton = new JRadioButton("Mass Activity", false);
		massActivityRadioButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		massActivityRadioButton.setBounds(width/2 - 700, height/2 - 290, 200, 40);
		massActivityRadioButton.setVisible(false);
		
		individualActivityRadioButton = new JRadioButton("Individual Activity", false);
		individualActivityRadioButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		individualActivityRadioButton.setBounds(width/2 - 700, height/2 - 240, 400, 40);
		individualActivityRadioButton.setVisible(false);
		
		for(int i = 0; i < numberOfFields; i++) {
			container.add(labels[i]);
			container.add(textFields[i]);
		}
		
		container.add(activityRadioButton);
		container.add(massActivityRadioButton);
		container.add(individualActivityRadioButton);
		container.add(equipmentRadioButton);
		container.add(emergencyInfoRadioButton);
		container.add(addButton);
		container.add(deleteButton);
		container.add(previousPageButton);
		container.add(notificationText);
		
		activityRadioButton.addActionListener(handler);
		massActivityRadioButton.addActionListener(handler);
		individualActivityRadioButton.addActionListener(handler);
		equipmentRadioButton.addActionListener(handler);
		emergencyInfoRadioButton.addActionListener(handler);
		addButton.addActionListener(handler);
		deleteButton.addActionListener(handler);
		previousPageButton.addActionListener(handler);
	}
	
	public class AnimatorHomepageEventHandler implements ActionListener {		
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == addButton) {				
				if(activityType == ActivityType.MASS && entityType == ActivityOrOther.ACTIVITY) {
					database.insert(command.insertMassActivity(
							textFields[0].getText(), 
							textFields[1].getText(), 
							textFields[2].getText(), 
							Integer.valueOf(textFields[3].getText()), 
							textFields[4].getText(), 
							textFields[5].getText(),
							database.getAnimatorID(contactPhone)
							));
					notificationText.setText(textFields[0].getText() + " Added to Database");
					notificationText.setForeground(Color.GREEN);
				}
				
				else if(activityType == ActivityType.INDIVIDUAL && entityType == ActivityOrOther.ACTIVITY) {
					database.insert(command.insertIndividualActivity(
							textFields[0].getText(), 
							textFields[1].getText(), 
							Integer.valueOf(textFields[2].getText()), 
							database.getAnimatorID(contactPhone)
							));
					
					notificationText.setText(textFields[0].getText() + " Added to Database");
					notificationText.setForeground(Color.GREEN);
				}
				
				else if(otherType == OtherType.EQUIPMENT && entityType == ActivityOrOther.OTHER) {
					database.insert(command.insertEquipment(
							textFields[0].getText(), 
							textFields[1].getText(), 
							Integer.valueOf(textFields[2].getText())
							));
					
					notificationText.setText("Equipment Added");
					notificationText.setForeground(Color.GREEN);
				}
				
				else if(otherType == OtherType.EMERGENCY_INFO && entityType == ActivityOrOther.OTHER) {
					database.insert(command.insertEmergencyInformation(
							database.getActivityID(textFields[0].getText()), 
							Integer.valueOf(textFields[1].getText()),
							Long.valueOf(textFields[2].getText()) 
							));
					
					notificationText.setText("Emergency Information Added");
					notificationText.setForeground(Color.GREEN);
				}
			}
			
			else if (event.getSource() == deleteButton) {
				database.deleteActivity(textFields[0].getText());
				notificationText.setText("Deletion successful");
				notificationText.setForeground(Color.GREEN);
			}
			
			else if (event.getSource() == previousPageButton) {
				setVisible(false);
			}
			
			else if(event.getSource() == activityRadioButton) {				
				equipmentRadioButton.setSelected(false);
				emergencyInfoRadioButton.setSelected(false);
				
				massActivityRadioButton.setVisible(true);
				individualActivityRadioButton.setVisible(true);
			}
			
			else if(event.getSource() == massActivityRadioButton) {
				activityType = ActivityType.MASS;
				entityType = ActivityOrOther.ACTIVITY;
				individualActivityRadioButton.setSelected(false);
				
				for(int i = 0; i < numberOfFields; i++) {
					labels[i].setText(massActivityLabels[i]);
					labels[i].setVisible(true);
					textFields[i].setVisible(true);					
				}
			}
			
			else if(event.getSource() == individualActivityRadioButton) {
				activityType = ActivityType.INDIVIDUAL;
				entityType = ActivityOrOther.ACTIVITY;
				massActivityRadioButton.setSelected(false);
				
				for(int i = 0; i < numberOfFields - 3; i++) {
					labels[i].setText(individualActivityLabels[i]);
					labels[i].setVisible(true);
					textFields[i].setVisible(true);					
				}
				
				labels[numberOfFields - 1].setText("");
				textFields[numberOfFields - 1].setVisible(false);
				labels[numberOfFields - 2].setText("");
				textFields[numberOfFields - 2].setVisible(false);
				labels[numberOfFields - 3].setText("");
				textFields[numberOfFields - 3].setVisible(false);
			}
			
			else if(event.getSource() == equipmentRadioButton) {
				otherType = OtherType.EQUIPMENT;
				entityType = ActivityOrOther.OTHER;
				
				activityRadioButton.setSelected(false);
				emergencyInfoRadioButton.setSelected(false);
				massActivityRadioButton.setSelected(false);
				individualActivityRadioButton.setSelected(false);
				
				massActivityRadioButton.setVisible(false);
				individualActivityRadioButton.setVisible(false);
				
				for(int i = 0; i < numberOfFields - 3; i++) {
					labels[i].setText(equipmentLabels[i]);
					labels[i].setVisible(true);
					textFields[i].setVisible(true);					
				}
				
				labels[numberOfFields - 1].setText("");
				textFields[numberOfFields - 1].setVisible(false);
				labels[numberOfFields - 2].setText("");
				textFields[numberOfFields - 2].setVisible(false);
				labels[numberOfFields - 3].setText("");
				textFields[numberOfFields - 3].setVisible(false);
			}
			
			else if(event.getSource() == emergencyInfoRadioButton) {				
				otherType = OtherType.EMERGENCY_INFO;
				entityType = ActivityOrOther.OTHER;
				
				activityRadioButton.setSelected(false);
				equipmentRadioButton.setSelected(false);
				massActivityRadioButton.setSelected(false);
				individualActivityRadioButton.setSelected(false);
				
				massActivityRadioButton.setVisible(false);
				individualActivityRadioButton.setVisible(false);
				
				for(int i = 0; i < numberOfFields - 3; i++) {
					labels[i].setText(emergencyInfoLabels[i]);
					labels[i].setVisible(true);
					textFields[i].setVisible(true);
				}
				
				labels[numberOfFields - 1].setText("");
				textFields[numberOfFields - 1].setVisible(false);
				labels[numberOfFields - 2].setText("");
				textFields[numberOfFields - 2].setVisible(false);
				labels[numberOfFields - 3].setText("");
				textFields[numberOfFields - 3].setVisible(false);
			}
		}
	}
	
	public void setContactPhone(String contactPhone)
	{
		this.contactPhone = contactPhone;
	}
}
