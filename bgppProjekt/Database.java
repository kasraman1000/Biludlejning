package bgppProjekt;
import java.sql.DriverManager;
import com.mysql.jdbc.Connection;

public class Database {
		// connection objekt til databaseforbindelsen
		private static Connection conn = null;
		//driveren til databasen
		private static String driver = "com.mysql.jdbc.Driver";		
		private static String dbName = "BiludlejningCrelde";
		private static String username = "Crelde", password = "bil";
		
		public static Boolean connect() {
			boolean isConnected = false;
			// Here we connect to the database
			try {
				Class.forName(driver);
				conn = (Connection) DriverManager.getConnection("jdbc:mysql://" +
						"mysql.itu.dk/" + dbName, username, password);
				isConnected = true;
				if (isConnected = true){
					System.out.println("Winner winner chicken dinner");
				}
			
			} catch (Exception e) {
				System.out.println("Fail to connect db");
			}			
			return isConnected;
		}

	// This method closes the database

	public void closeDb() {
		try {
			conn.close();
		} catch(Exception e) {}
	}
}	
