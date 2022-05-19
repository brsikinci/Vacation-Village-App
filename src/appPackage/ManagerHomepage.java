package appPackage;

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;
import java.util.Random;

enum PersonToBeAdded {
	CUSTOMER,
	ANIMATOR,
	EQUIP_PERSON,
	MANAGER
}

public class ManagerHomepage extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton addButton, previousPageButton;
	JRadioButton customerRadioButton, animatorRadioButton, equipPersonRadioButton;
	private JLabel addNotificationText;
	private Color customBlue;
	private Dimension screenSize;
	private int width, height;
	private Container container;
	private ManagerHomepageEventHandler handler;
	private final int numberOfFields = 4;
	private JLabel[] labels;
	private JTextField[] textFields;
	private PersonToBeAdded personToBeAdded;
	private String[] customerLabels = {"Name", "Age", "Room number", "Contact phone"};
	private String[] animatorLabels = {"Name", "Age", "Expertise Area"};
	private String[] equipPersonLabels = {"SSN", "Name", "Surname", "Contact phone"};
	private SQLCommands command = new SQLCommands();
	private Database database = new Database();
	private String password;

	
	public ManagerHomepage()
	{
		setTitle("Manager Homepage");
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
		
		handler = new ManagerHomepageEventHandler();

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
		
		addNotificationText = new JLabel("");
		addNotificationText.setBounds(width/2 - 100, height/2 + 250, 380, 250);
		addNotificationText.setForeground(Color.BLACK);
		addNotificationText.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
				
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
		
		customerRadioButton = new JRadioButton("Customer", false);
		customerRadioButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		customerRadioButton.setBounds(width/2 - 700, height/2 - 390, 200, 40);
		
		animatorRadioButton = new JRadioButton("Animator", false);
		animatorRadioButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		animatorRadioButton.setBounds(width/2 - 100, height/2 - 390, 200, 40);
		
		equipPersonRadioButton = new JRadioButton("Equip Person", false);
		equipPersonRadioButton.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		equipPersonRadioButton.setBounds(width/2 + 500, height/2 - 390, 200, 40);		
		
		for(int i = 0; i < numberOfFields; i++) {
			container.add(labels[i]);
			container.add(textFields[i]);
		}
		
		container.add(customerRadioButton);
		container.add(animatorRadioButton);
		container.add(equipPersonRadioButton);
		container.add(addButton);
		container.add(previousPageButton);
		container.add(addNotificationText);
		
		customerRadioButton.addActionListener(handler);
		animatorRadioButton.addActionListener(handler);
		equipPersonRadioButton.addActionListener(handler);
		addButton.addActionListener(handler);		
		previousPageButton.addActionListener(handler);
	}
	
	public class ManagerHomepageEventHandler implements ActionListener {		
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == addButton) {				
				if(personToBeAdded == PersonToBeAdded.CUSTOMER) {
						password = passwordGenerator();
						
						try {
							database.insert(command.insertCustomer(textFields[0].getText(),
											Integer.valueOf(textFields[1].getText()),
											Integer.valueOf(textFields[2].getText()),
											Long.valueOf(textFields[3].getText()),
											password));
							
							addNotificationText.setText("Insertion successful");
							addNotificationText.setForeground(Color.GREEN);
						}
						catch(Exception genericException) {
							addNotificationText.setText("Insertion failed");
							addNotificationText.setForeground(Color.RED);
						}
				}
				
				else if(personToBeAdded == PersonToBeAdded.ANIMATOR) {
					password = passwordGenerator();
					
					try {
						database.insert(command.insertAnimator(textFields[0].getText(),
										Long.valueOf(textFields[1].getText()),
										textFields[2].getText(),
										password));
						
						addNotificationText.setText("Insertion successful");
						addNotificationText.setForeground(Color.GREEN);
					}
					catch(Exception genericException) {
						addNotificationText.setText("Insertion failed");
						addNotificationText.setForeground(Color.RED);
					}
				}
				
				else if(personToBeAdded == PersonToBeAdded.EQUIP_PERSON) {					
					try {
						database.insert(command.insertEquipPerson(Integer.valueOf(textFields[0].getText()),
										textFields[1].getText(),
										textFields[2].getText(),
										Long.valueOf(textFields[3].getText())));
						
						addNotificationText.setText("Insertion successful");
						addNotificationText.setForeground(Color.GREEN);
					}
					catch(Exception genericException) {
						addNotificationText.setText("Insertion failed");
						addNotificationText.setForeground(Color.RED);
					}
				}
			}
			
			else if (event.getSource() == previousPageButton) {
				setVisible(false);
			}
			
			else if(event.getSource() == customerRadioButton) {		
				personToBeAdded = PersonToBeAdded.CUSTOMER;
				
				animatorRadioButton.setSelected(false);
				equipPersonRadioButton.setSelected(false);
				
				for(int i = 0; i < numberOfFields; i++) {
					labels[i].setText(customerLabels[i]);
					labels[i].setVisible(true);
					textFields[i].setVisible(true);					
				}
			}
			
			else if(event.getSource() == animatorRadioButton) {
				personToBeAdded = PersonToBeAdded.ANIMATOR;
				
                customerRadioButton.setSelected(false);
			    equipPersonRadioButton.setSelected(false);
				
				for(int i = 0; i < numberOfFields - 1; i++) {
					labels[i].setText(animatorLabels[i]);
					labels[i].setVisible(true);
					textFields[i].setVisible(true);					
				}
				
				labels[numberOfFields - 1].setText("");
				textFields[numberOfFields - 1].setVisible(false);
			}
			
			else if(event.getSource() == equipPersonRadioButton) {				
				personToBeAdded = PersonToBeAdded.EQUIP_PERSON;
				
				animatorRadioButton.setSelected(false);
				customerRadioButton.setSelected(false);
				
				for(int i = 0; i < numberOfFields; i++) {
					labels[i].setText(equipPersonLabels[i]);
					labels[i].setVisible(true);
					textFields[i].setVisible(true);
				}
			}
		}
	}
	
	String passwordGenerator()
	{
		Random random = new Random();
		int passwordNum = random.nextInt(99999) + 1;
		String password = String.valueOf(passwordNum);
		return password;
	}
}
