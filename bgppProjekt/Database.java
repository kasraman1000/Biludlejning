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
	static int carID;
	static int phone;
	static String CostumerName;
	static int type;
	static String carName;
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
		}*/
	public static int getcarID(int id) {
		try {
		Statement select = conn.createStatement();		
		String getIt = "SELECT * FROM Reservation WHERE id="+id;
		ResultSet result = select.executeQuery(getIt);
		result.next();
		carID = result.getInt(2);		
		System.out.println("carID = "+ carID);
			}							
		 catch (Exception e) {			
			e.printStackTrace();
		}
		return carID;			
	}
	public static int getPhone(int id) {
		try {
		Statement select = conn.createStatement();		
		String getIt = "SELECT * FROM Reservation WHERE id="+id;
		ResultSet result = select.executeQuery(getIt);
		result.next();
		phone = result.getInt(3);		
		System.out.println("Phone Number = "+ phone);
			}							
		 catch (Exception e) {			
			e.printStackTrace();
		}
		return phone;			
	}
	public static String getCostumerName(int id) {
		try {
		Statement select = conn.createStatement();		
		String getIt = "SELECT * FROM Reservation WHERE id="+id;
		ResultSet result = select.executeQuery(getIt);
		result.next();
		CostumerName = result.getString(4);	
		System.out.println("Name = "+ CostumerName);
			}							
		 catch (Exception e) {			
			e.printStackTrace();
		}
		return CostumerName;			
	}
	public static Date getStartDate(int id) {
			try {
			Statement select = conn.createStatement();			
			String getIt = "SELECT * FROM Reservation WHERE id="+id;
			ResultSet result = select.executeQuery(getIt);
			result.next();
			String startDate = result.getString(5);
			date = Date.valueOf(startDate);
			System.out.println("Startdate = "+ date);
				}							
			 catch (Exception e) {			
				e.printStackTrace();
			}
			return date;			
		}	
	public static Date getEndDate(int id) {
			try {
			Statement select = conn.createStatement();		
			String getIt = "SELECT * FROM Reservation WHERE id="+id;
			ResultSet result = select.executeQuery(getIt);
			result.next();
			String endDate = result.getString(5);
			date = Date.valueOf(endDate);
			System.out.println("endDate = "+ date);
				}							
			 catch (Exception e) {			
				e.printStackTrace();
			}
			return date;			
		}
	public static int getType(int id) {
		try {
		Statement select = conn.createStatement();		
		String getIt = "SELECT * FROM Car WHERE id="+id;
		ResultSet result = select.executeQuery(getIt);
		result.next();
		int type = result.getInt(2);
		System.out.println("Car:");
		System.out.println("Type = "+ type);
			}							
		 catch (Exception e) {			
			e.printStackTrace();
		}
		return type;			
	}	
	public static String getCarName(int id) {
		try {
		Statement select = conn.createStatement();		
		String getIt = "SELECT * FROM Car WHERE id="+id;
		ResultSet result = select.executeQuery(getIt);
		result.next();
		String carName = result.getString(2);
		System.out.println("Carname = "+ carName);
			}							
		 catch (Exception e) {			
			e.printStackTrace();
		}
		return carName;			
	}			
	// This method closes the database
	public void closeDb() {
		try {
			conn.close();
		} catch(Exception e) {}
	}

}	
