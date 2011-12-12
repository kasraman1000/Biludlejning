package bgppProjekt;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.RowId;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


public class Database {
	static Date date;
		// connection objekt til databaseforbindelsen
		private static Connection conn = null;
		// driveren til databasen
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
		public static void initiateDb() {
			try {
				System.out.println("sup we are 1 line below try, trololo lazy");
				Statement initiate = conn.createStatement();
				initiate.executeUpdate(
				"CREATE TABLE Car"+
				"(id int(11) NOT NULL auto_increment," +
				"`type` int(11) NOT NULL,"+
				"`name` text NOT NULL,"+
				"PRIMARY KEY (id))"+
				"ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;");
				
				
				initiate.executeUpdate(
			
				"CREATE TABLE Reservation"+
				"(id int(11) NOT NULL auto_increment,"+
				"`carID` int(11) NOT NULL,"+
				"`phone` int(11) NOT NULL,"+
				"`name` text NOT NULL,"+
				"`start` date NOT NULL,"+
				"`end` date NOT NULL,"+
				"PRIMARY KEY (id))"+
				"ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;");
			

		}				catch (SQLException e){
				e.printStackTrace();
				}
			System.out.println("gotta catch em all");
		}
	
/*		public static void select() {
			try {
				Statement select = conn.createStatement();
				String getIt = "SELECT * FROM `Reservation`";
				ResultSet result = select.executeQuery(getIt);
				
				
				System.out.println("Crelde owns so he brought results!");
				while (result.next()) {
					int id = result.getInt(1);
					Date startDate = result.getDate(5);
					Date endDate = result.getDate(6);
					System.out.println("id = " + id);
					System.out.println("Start Date = " + startDate);
					System.out.println("End Date = " + endDate);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	*/	
		public static Date getStartDate(int id) {
			try {
			Statement select = conn.createStatement();
			
			String getIt = "SELECT id,start FROM `Reservation`";
			ResultSet result = select.executeQuery(getIt);
			System.out.println(result);
			//RowId derp = result.getRowId(id);
			//((ResultSet) derp).getString(5);
			String startDate = result.getString(5);
			System.out.println("Startdate = "+ startDate);
			
			//date = Date.valueOf(derp);
			
											}
							
			 catch (Exception e) {
			
				e.printStackTrace();
			}
			System.out.println(date);
			return date;
			
		}

		// This method closes the database

	public void closeDb() {
		try {
			conn.close();
		} catch(Exception e) {}
	}
}	
