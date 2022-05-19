package appPackage;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		DatabaseConnectionPage databaseConnectionPage = new DatabaseConnectionPage();
		
		databaseConnectionPage.setTitle("Database Connection Page");
		databaseConnectionPage.setSize(1920, 1080);
		databaseConnectionPage.setVisible(true);
		databaseConnectionPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
