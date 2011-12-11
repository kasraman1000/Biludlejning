package bgppProjekt;
import java.sql.DriverManager;
import com.mysql.jdbc.Connection;



public class Main {
	// connection objekt til databaseforbindelsen
	private static Connection conn = null;
	//driveren til databasen
	private static String driver = "com.mysql.jdbc.Driver";
	
	private static String dbName = "BiludlejningCrelde";
	private static String username = "Crelde", password = "bil";
	
	
	
	static private GUITest guiTest;
	
	
	
	public static void main(String[] args) {
		
		guiTest = new GUITest();
		connect();
	}

	
	public static Boolean connect() {
		boolean isConnected = false;
		// is where we connect fur real
		try {
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://" +
					"itu.dk/" + dbName, username, password);
			isConnected = true;
			if (isConnected = true){
				System.out.println("Winner winner chicken dinner");
			}
		
		} catch (Exception e) {
			System.out.println("Fail to connect db");
		}
		
		
		
		return isConnected;
	}
	// disconnection purposes
	
	public void closeDb() {
		try {
			conn.close();
		} catch(Exception e) {}
	}
}	
