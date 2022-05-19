package appPackage;

import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	private static final boolean deleteDatabase = false;
	private boolean connectionSuccessful = false;
	private Connection connection = null;
	private Statement statement = null;
	
	public Database()
    {       
        try
        {
        	// Connect to the server
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
            
            if(connection != null)
            {
            	connectionSuccessful = true;
            	createAllTables();
    			if(deleteDatabase)
    				deleteAllTables();
            }
        }
        catch (Exception genericException)
        {
        	JOptionPane.showMessageDialog(null, "Unidentified connection error occured.");
        }
    }
	
	public boolean connectionSuccessful()
	{
		return connectionSuccessful;
	}
	
	private void createAllTables()
	{
		try {
			SQLCommands command = new SQLCommands();			
			statement = connection.createStatement();
			
			if(!tableExists("customer"))
				statement.executeUpdate(command.createCustomerTable());
			
			if(!tableExists("animator"))
				statement.executeUpdate(command.createAnimatorTable());
			
			if(!tableExists("equip_person"))
				statement.executeUpdate(command.createEquipPersonTable());
			
			if(!tableExists("manager")) {
				statement.executeUpdate(command.createManagerTable());
				statement.executeUpdate(command.insertManager());
			}
			
			if(!tableExists("activity"))
				statement.executeUpdate(command.createActivityTable());
			
			if(!tableExists("equipment"))
				statement.executeUpdate(command.createEquipmentTable());
			
			if(!tableExists("emergency_info"))
				statement.executeUpdate(command.createEmergencyInfoTable());
			
			if(!tableExists("appointment"))
				statement.executeUpdate(command.createAppointmentTable());
			
			if(!tableExists("activity_history"))
				statement.executeUpdate(command.createActivityHistoryTable());

			statement.executeUpdate(command.createMassActivityView());
			statement.executeUpdate(command.createIndividualActivityView());
			statement.executeUpdate(command.createInsertDeletedActivityFunction());
			statement.executeUpdate(command.createInsertDeletedActivityTrigger());
			statement.close();
		}
		catch(Exception genericException) {
			JOptionPane.showMessageDialog(null, "Unidentified table creation error occured.");
			genericException.printStackTrace();
		}
	}

	private boolean tableExists(String tableName)
	{
		boolean exists = false;
		
		try {
			ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null);
			
			if(resultSet.next())
				exists = true;
		}
		catch(Exception generalException) {
			JOptionPane.showMessageDialog(null, "Unidentified error in tableExists() function.");
		}
		
		return exists;
	}	

	
	public void insert(String command)
	{
		try {
			statement = connection.createStatement();
			statement.executeUpdate(command);
			statement.close();
		}
		catch(Exception genericException) {
			JOptionPane.showMessageDialog(null, "Unidentified insertion error occured.");
		}
	}
	
	public String getPassword(String userInfo, PersonToBeAdded personType)
	{
		try {
			statement = connection.createStatement();
			SQLCommands commands = new SQLCommands();
			ResultSet resultSet = null;
			String temp = "";
			
			if(personType == PersonToBeAdded.CUSTOMER)
				resultSet = statement.executeQuery(commands.getCustomer(userInfo));
			
			else if(personType == PersonToBeAdded.ANIMATOR)
				resultSet = statement.executeQuery(commands.getAnimator(userInfo));
			
			else if(personType == PersonToBeAdded.MANAGER)
				resultSet = statement.executeQuery(commands.getManager(userInfo));
			
			if(resultSet == null)
				return (new String(""));
			
			while(resultSet.next())
				temp = resultSet.getString("password");
			
			resultSet.close();
			statement.close();
			
			return temp;
		}
		catch(Exception genericException) {
			JOptionPane.showMessageDialog(null, "Unidentified error in getting user password.");
			return (new String());
		}
	}

	public int getCustomerID(String contactPhone)
	{
		int id = -1;
		
		try {
			statement = connection.createStatement();
			SQLCommands commands = new SQLCommands();
			ResultSet resultSet = null;
			
			resultSet = statement.executeQuery(commands.getCustomer(contactPhone));
			
			if(resultSet == null) 
				return id;
			
			while(resultSet.next())
				id = resultSet.getInt("customer_id");
			
			statement.close();
			resultSet.close();
		}
		catch(Exception genericException) {
			JOptionPane.showMessageDialog(null, "Cannot get customer id.");
			id = -1;
		}
		
		return id;
	}
	
	public int getAnimatorID(String contactPhone)
	{
		int animatorID = -1;
		
		try {
			statement = connection.createStatement();
			SQLCommands commands = new SQLCommands();
			ResultSet resultSet = null;
			
			resultSet = statement.executeQuery(commands.getAnimator(contactPhone));
			
			if(resultSet == null) 
				return animatorID;
			
			while(resultSet.next())
				animatorID = resultSet.getInt("employee_id");
			
			statement.close();
			resultSet.close();
		}
		catch(Exception genericException) {
			JOptionPane.showMessageDialog(null, "Cannot get animator id.");
			animatorID = -1;
		}
		
		return animatorID;
	}
	
	public int getActivityID(String activityName)
	{
		int activityID = -1;
		
		try {
			statement = connection.createStatement();
			SQLCommands commands = new SQLCommands();
			ResultSet resultSet = null;
			
			resultSet = statement.executeQuery(commands.getActivity(activityName));
			
			if(resultSet == null) 
				return activityID;
			
			while(resultSet.next()) {
				activityID = resultSet.getInt("activity_id");
			}
			
			statement.close();
			resultSet.close();
		}
		catch(Exception genericException) {
			JOptionPane.showMessageDialog(null, "Cannot get activity id.");
			activityID = -1;
		}
		
		return activityID;
	}
	
	public int getActivityAgeRequirement(String activityName)
	{
		int ageRequirement = -1;
		
		try {
			statement = connection.createStatement();
			SQLCommands commands = new SQLCommands();
			ResultSet resultSet = null;
			
			resultSet = statement.executeQuery(commands.getIndActivityAgeRequirement(activityName));
			
			if(resultSet == null) 
				return ageRequirement;
			
			while(resultSet.next())
				ageRequirement = resultSet.getInt("age_requirement");
			
			statement.close();
			resultSet.close();
		}
		catch(Exception genericException) {
			JOptionPane.showMessageDialog(null, "Cannot get age requirement.");
			
			ageRequirement = -1;
		}
		
		return ageRequirement;
	}
	
	public int getAnimatorIdFromActivity(String activityName)
	{
		int animatorID = -1;
		
		try {
			statement = connection.createStatement();
			SQLCommands commands = new SQLCommands();
			ResultSet resultSet = null;
			
			resultSet = statement.executeQuery(commands.getActivity(activityName));
			
			if(resultSet == null) 
				return animatorID;
			
			while(resultSet.next())
				animatorID  = resultSet.getInt("animator_id");
			
			statement.close();
			resultSet.close();
		}
		catch(Exception genericException) {
			JOptionPane.showMessageDialog(null, "Cannot get animator id from activity.");
			animatorID  = -1;
		}
		
		return animatorID;
	}
	
	public int getCustomerAge(String contactPhone)
	{
		int age = -1;
		
		try {
			statement = connection.createStatement();
			SQLCommands commands = new SQLCommands();
			ResultSet resultSet = null;
			
			resultSet = statement.executeQuery(commands.getCustomer(contactPhone));
			
			if(resultSet == null) 
				return age;
			
			while(resultSet.next())
				age = resultSet.getInt("age");
			
			statement.close();
			resultSet.close();
		}
		catch(Exception genericException) {
			JOptionPane.showMessageDialog(null, "Cannot get customer age.");
			age = -1;
		}
		
		return age;
	}
	
	public boolean userExists(String userInfo, PersonToBeAdded personType)
	{
		try {
			statement = connection.createStatement();
			SQLCommands commands = new SQLCommands();
			ResultSet resultSet = null;
			boolean userExists = false;
			
			if(personType == PersonToBeAdded.CUSTOMER)
				resultSet = statement.executeQuery(commands.getCustomer(userInfo));
			
			else if(personType == PersonToBeAdded.ANIMATOR)
				resultSet = statement.executeQuery(commands.getAnimator(userInfo));
			
			else if(personType == PersonToBeAdded.MANAGER)
				resultSet = statement.executeQuery(commands.getManager(userInfo));

			if(resultSet == null)
				return userExists;
			
			while(resultSet.next()) {
				userExists = true;
			}
			
			resultSet.close();
			statement.close();
			return userExists;
		}
		catch(Exception generalException) {
			JOptionPane.showMessageDialog(null, "Unidentified error in finding whether the user exists or not.");
			return false;
		}
	}
	
	public boolean customerHasAppointment(String contactPhone, String datetime)
	{
		boolean hasAppointment = false;
		
		try {
			statement = connection.createStatement();
			SQLCommands commands = new SQLCommands();
			ResultSet resultSet = null;
			int customerID = 0;
			int activityID = 0;
			String dateTime = new String("");
			
			resultSet = statement.executeQuery(commands.getCustomer(contactPhone));
			
			if(resultSet == null) 
				return hasAppointment;
			
			while(resultSet.next())
				customerID = resultSet.getInt("CUSTOMER_ID");

			if(resultSet == null) 
				return hasAppointment;
			
			while(resultSet.next())
				activityID = resultSet.getInt("ACTIVITY_ID");
			
			if(resultSet == null) 
				return hasAppointment;
			
			while(resultSet.next())
				dateTime = resultSet.getString("DATETIME");
			
			resultSet = statement.executeQuery(commands.customerHasAppointment(customerID, activityID, dateTime));
			
			if(resultSet == null) 
				return false;
			
			while(resultSet.next())
				hasAppointment = true;
			
			statement.close();
			resultSet.close();
		}
		catch(Exception genericException) {
			JOptionPane.showMessageDialog(null, "Cannot get appointment.");
			hasAppointment = false;
		}
		
		return hasAppointment;
	}
	
	public boolean capacityFull(String activityName)
	{
		boolean full = false;
		
		try {
			statement = connection.createStatement();
			SQLCommands commands = new SQLCommands();
			ResultSet resultSet = null;
			int sumPerson = 0;
			int capacity = 0;
			
			resultSet = statement.executeQuery(commands.getMassActivityCount(activityName));
			
			if(resultSet == null) 
				return false;
			
			while(resultSet.next())
				sumPerson = resultSet.getInt("counter");
			
			resultSet = statement.executeQuery(commands.getMassActivityCapacity(activityName));
			
			if(resultSet == null) 
				return false;
			
			while(resultSet.next())
				capacity = resultSet.getInt("capacity");
			
			if(sumPerson == capacity) 
				full = true;
			
			statement.close();
			resultSet.close();
		}
		catch(Exception genericException) {
			JOptionPane.showMessageDialog(null, "Cannot get capacity.");
			full = false;
		}
		
		return full;
	}
	
	public boolean deleteActivity(String activityName)
	{
		boolean activityDeleted = false;
		
		try {
			statement = connection.createStatement();
			SQLCommands commands = new SQLCommands();
			ResultSet resultSet = null;
			
			statement.executeUpdate(commands.deleteActivityWithName(activityName));
			activityDeleted = true;
		}
		catch(Exception genericException) {
			JOptionPane.showMessageDialog(null, "Cannot delete activity.");
		    activityDeleted = false;
		}
		
		return activityDeleted;
	}
	
	private void deleteAllTables()
	{
		try {			
			statement = connection.createStatement();
			SQLCommands command = new SQLCommands();
			
			statement.executeUpdate(command.deleteCustomerTable());
			statement.executeUpdate(command.deleteAnimatorTable());
			statement.executeUpdate(command.deleteEquipPersonTable());
			statement.executeUpdate(command.deleteManagerTable());
			statement.executeUpdate(command.deleteEquipmentTable());
			statement.executeUpdate(command.deleteEmergencyInfoTable());
			statement.executeUpdate(command.deleteActivityTable());
			statement.executeUpdate(command.deleteActivityHistoryTable());
			statement.executeUpdate(command.deleteAppointmentTable());
			
			statement.close();
		}
		catch(Exception genericException) {
			JOptionPane.showMessageDialog(null, "Cannot delete tables.");
		}
	}

}
