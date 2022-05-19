package appPackage;

public class SQLCommands {
	private String name;
	private int age;
	private int roomNumber;
	private long contactPhone;
	private String expertiseArea;
	private long ssn;
	private String surname;
	
	public String createManagerTable()
	{
		return "CREATE TABLE MANAGER ( USERNAME VARCHAR(50) PRIMARY KEY, PASSWORD VARCHAR(50) )";
	}
	
	public String createCustomerTable()
	{
		return "CREATE TABLE CUSTOMER " +
				"( " +
				"CUSTOMER_ID SERIAL," +
				"PASSWORD VARCHAR(50)," + 
				"VACATION_VILLAGE_ID SERIAL," +
				"NAME VARCHAR(50)," + 
				"AGE INT," + 
				"ROOM_NUMBER INT," +
				"CONTACT_PHONE bigint, " +
				"CONSTRAINT customer_pkey PRIMARY KEY(CUSTOMER_ID)" +
				")";
	}
	
	public String createAnimatorTable()
	{
		return "CREATE TABLE ANIMATOR " + 
				"( "+
				"PASSWORD VARCHAR(50), " + 
				"EMPLOYEE_ID SERIAL, " + 
				"NAME VARCHAR(50), " + 
				"PHONE_NUMBER bigint, " + 
				"EXPERTISE_AREA VARCHAR(50), " + 
				"CONSTRAINT animator_pkey PRIMARY KEY(EMPLOYEE_ID)" +
				")";
	}
	
	public String createEquipPersonTable()
	{
		return "CREATE TABLE EQUIP_PERSON " +
				"( " + 
				"SSN INT, " + 
				"NAME VARCHAR(50), " + 
				"SURNAME VARCHAR(50)," +
				"CONTACT_PHONE bigint, " + 
				"CONSTRAINT equip_person_pkey PRIMARY KEY(SSN)" +
				")";
	}
	
	public String createEquipmentTable()
	{
		return "CREATE TABLE EQUIPMENT (\n"
				+ "	NAME VARCHAR(100),\n"
				+ "	PURPOSE VARCHAR(100),\n"
				+ "	SSN INT,\n"
				+ "	CONSTRAINT equipment_pkey PRIMARY KEY(SSN),\n"
				+ "	CONSTRAINT equipment_fkey FOREIGN KEY(SSN) REFERENCES EQUIP_PERSON(SSN)\n"
				+ ")";
	}
	
	public String createEmergencyInfoTable()
	{
		return "CREATE TABLE EMERGENCY_INFO (\n"
				+ "	ACTIVITY_ID INT,\n"
				+ "	PHONE_NUMBER BIGINT,\n"
				+ "	LOCKER_NUMBER INT,\n"
				+ "	CONSTRAINT emergencyinfo_pkey PRIMARY KEY(PHONE_NUMBER, LOCKER_NUMBER)\n"
				+ ")";
	}
	
	public String createActivityTable()
	{
		return "CREATE TABLE ACTIVITY " + 
				"( " + 
				"ACTIVITY_ID SERIAL, " + 
				"INTERNET_LINK VARCHAR(100), " + 
				"NAME VARCHAR(100), " + 
				"CAPACITY INT, " +
				"ACTIVITY_TYPE VARCHAR(50), " +
				"DATE VARCHAR(100), " + 
				"HOUR VARCHAR(100), " +
				"AGE_REQUIREMENT INT, " +
				"ANIMATOR_ID INT, " +
				"CONSTRAINT activity_pkey PRIMARY KEY(ACTIVITY_ID)" + 
				")";
	}
	
	public String createAppointmentTable()
	{
		return "CREATE TABLE APPOINTMENT " + 
				"( " +
				"CUSTOMER_ID INT, " +
				"ACTIVITY_ID INT, " + 
				"ANIMATOR_ID INT, " +
				"DATETIME VARCHAR(100), " +
				"CONSTRAINT appointment_pkey PRIMARY KEY (CUSTOMER_ID, ACTIVITY_ID, DATETIME), " +
				"CONSTRAINT customer_fkey FOREIGN KEY(CUSTOMER_ID) REFERENCES CUSTOMER(CUSTOMER_ID), " + 
				"CONSTRAINT activity_fkey FOREIGN KEY(ACTIVITY_ID) REFERENCES ACTIVITY(ACTIVITY_ID)" +
				")";
	}
	
	public String createActivityHistoryTable()
	{
		return "CREATE TABLE ACTIVITY_HISTORY("
				+ "	ACTIVITY_ID INT,"
				+ "	NAME VARCHAR(100),"
				+ "	INTERNET_LINK VARCHAR(100),"
				+ "	CONSTRAINT activityhistory_pkey PRIMARY KEY(ACTIVITY_ID)"
				+ ")";
	}
	
	public String createInsertDeletedActivityFunction()
	{
		return "CREATE OR REPLACE FUNCTION INSERT_DELETED_ACTIVITY_FUNCTION() RETURNS TRIGGER AS "
				+ "$$ "
				+ "BEGIN "
				+ "	INSERT INTO ACTIVITY_HISTORY VALUES(OLD.ACTIVITY_ID, OLD.NAME, OLD.INTERNET_LINK); "
				+ "	RETURN NULL;"
				+ "END;"
				+ "$$"
				+ "LANGUAGE 'plpgsql';";
	}
	
	public String createInsertDeletedActivityTrigger()
	{
		return "CREATE OR REPLACE TRIGGER INSERT_DELETED_ACTIVITY_TRIGGER\n"
				+ "BEFORE DELETE\n"
				+ "ON ACTIVITY\n"
				+ "FOR EACH ROW\n"
				+ "EXECUTE PROCEDURE INSERT_DELETED_ACTIVITY_FUNCTION()";
	}

	public String createIndividualActivityView()
	{
		return "CREATE OR REPLACE VIEW INDIVIDUAL_ACTIVITY AS\n"
				+ "SELECT ACTIVITY_ID, NAME, INTERNET_LINK, AGE_REQUIREMENT\n"
				+ "FROM ACTIVITY\n"
				+ "WHERE CAPACITY = -1";
	}
	
	public String createMassActivityView()
	{
		return "CREATE OR REPLACE VIEW MASS_ACTIVITY AS\n"
				+ "SELECT ACTIVITY_ID, NAME, INTERNET_LINK, CAPACITY, ACTIVITY_TYPE, DATE, HOUR\n"
				+ "FROM ACTIVITY\n"
				+ "WHERE CAPACITY <> -1";
	}
	
	public String insertCustomer(String name, int age, int number, long phone, String password)
	{
		this.name = name;
		this.age = age;
		roomNumber = number;
		contactPhone = phone;
		
		return "INSERT INTO CUSTOMER " +
		"VALUES( " + 
		" DEFAULT, " + 
		"'" + password + "', " +
		"DEFAULT, " + 
		"'" + name + "', " + 
		String.valueOf(age) + ", " + 
		String.valueOf(roomNumber) + ", " + 
		String.valueOf(contactPhone) + 
		" )";
	}
	
	public String insertAnimator(String name, long phone, String area, String password)
	{
		this.name = name;
		contactPhone = phone;
		expertiseArea = area;
		
		return "INSERT INTO ANIMATOR " +
		"VALUES(" +
		" '" + password + "', " +
		"DEFAULT, " + 
		"'" + name + "', " + 
		String.valueOf(contactPhone) + ", " +
		"'" + expertiseArea + "' )";
	}
	
	public String insertEquipPerson(int ssn, String name, String surname, long phone)
	{
		this.ssn = ssn;
		this.name = name;
		this.surname = surname;
		contactPhone = phone;
		
		return "INSERT INTO EQUIP_PERSON " +
		"VALUES(" + 
		String.valueOf(ssn) + ", " +  
		"'" + name + "', " + 
		"'" + surname + "', " 
		+ String.valueOf(contactPhone) + ")";
	}
	
	public String insertManager()
	{
		return "INSERT INTO MANAGER VALUES('manager', 'manager')";
	}
	
	public String insertEquipment(String name, String purpose, int ssn)
	{
		return "INSERT INTO EQUIPMENT VALUES(" + 
				"'" + name + "', " + 
				"'" + purpose + "', " + 
				String.valueOf(ssn) + 
				")";
	}
	
	public String insertEmergencyInformation(int activityID, int lockerNumber, Long phoneNumber)
	{
		return "INSERT INTO EMERGENCY_INFO VALUES(" + 
				String.valueOf(activityID) + ", " + 
				String.valueOf(phoneNumber) + ", " + 
				String.valueOf(lockerNumber) + 
				")";
	}
	
	public String insertMassActivity(String name, String activityType, String link, int capacity, String date, String hour, int animatorID)
	{
		return "INSERT INTO ACTIVITY " + 
				"VALUES(" +
				"DEFAULT, " +
				"'" + link + "', " +
				"'" + name + "', " + 
				String.valueOf(capacity) + ", " + 
				"'" + activityType + "', " +
				"'" + date + "', " +
				"'" + hour + "', " +
				"-1, " +
				String.valueOf(animatorID) + 
				")";
	}
	
	public String insertIndividualActivity(String name, String link, int requirement, int animatorID)
	{
		return "INSERT INTO ACTIVITY " + 
				"VALUES(" +
				"DEFAULT, " +
				"'" + link + "', " +
				"'" + name + "', " + 
				"-1, " + 
				"'None', " +
				"'None', " +
				"'None', " +
				String.valueOf(requirement) + ", " +
				String.valueOf(animatorID) + 
				")";
	}
	
	public String insertAppointment(int customerID, int activityID, int animatorID, String datetime)
	{
		return "INSERT INTO APPOINTMENT " +
				"VALUES(" + 
				String.valueOf(customerID) + ", " +
				String.valueOf(activityID) + ", " + 
				String.valueOf(animatorID) + ", " +
				"'" + datetime + "'" +
				")";
	}
	
	public String getCustomer(String phone)
	{
		return "SELECT * FROM CUSTOMER WHERE CONTACT_PHONE = " + phone;
	}
	
	public String getAnimator(String phone)
	{
		return "SELECT * FROM ANIMATOR WHERE PHONE_NUMBER = " + phone;
	}
	
	public String getManager(String username)
	{
		return "SELECT * FROM MANAGER WHERE USERNAME = '" + username + "'";
	}
	
	public String getActivity(String name)
	{
		return "SELECT * FROM ACTIVITY WHERE NAME = '" + name + "'";
	}
	
	public String getMassActivityCount(String name)
	{
		return "SELECT COUNT(*) AS COUNTER" +
				"FROM MASS_ACT " + 
				"GROUP BY NAME " + 
				"HAVING NAME = '" + name + "'"; 
	}
	
	public String getMassActivityCapacity(String name)
	{
		return "SELECT CAPACITY FROM MASS_ACTIVITY WHERE NAME = '" + name + "'";
	}
	
	public String getIndActivityAgeRequirement(String name)
	{
		return "SELECT AGE_REQUIREMENT FROM INDIVIDUAL_ACTIVITY WHERE NAME = '"	+ name + "'";
	}
	
	public String customerHasAppointment(int customerID, int activityID, String dateTime)
	{
		return "SELECT * FROM APPOINTMENT WHERE CUSTOMER_ID = " 
				+ String.valueOf(customerID) + " AND ACTIVITY_ID = " 
				+ String.valueOf(activityID) + " AND DATETIME = "
				+ "'" + dateTime + "'"; 
	}
	
	public String deleteActivityWithName(String name)
	{
		return "DELETE FROM ACTIVITY WHERE NAME = '" + name + "'";
	}
	
	public String deleteCustomerTable()
	{
		return "DROP TABLE IF EXISTS CUSTOMER CASCADE";
	}
	
	public String deleteAnimatorTable()
	{
		return "DROP TABLE IF EXISTS ANIMATOR CASCADE";
	}
	
	public String deleteEquipPersonTable()
	{
		return "DROP TABLE IF EXISTS EQUIP_PERSON CASCADE";
	}
	
	public String deleteManagerTable()
	{
		return "DROP TABLE IF EXISTS MANAGER CASCADE";
	}
	
	public String deleteActivityTable()
	{
		return "DROP TABLE IF EXISTS ACTIVITY CASCADE"; 
	}
	
	public String deleteEmergencyInfoTable()
	{
		return "DROP TABLE IF EXISTS EMERGENCY_INFO CASCADE";
	}
	
	public String deleteEquipmentTable()
	{
		return "DROP TABLE IF EXISTS EQUIPMENT CASCADE";
	}

	public String deleteActivityHistoryTable()
	{
		return "DROP TABLE IF EXISTS ACTIVITY_HISTORY CASCADE";
	}
	
	public String deleteAppointmentTable()
	{
		return "DROP TABLE IF EXISTS APPOINTMENT CASCADE"; 
	}
}