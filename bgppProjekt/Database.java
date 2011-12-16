package bgppProjekt;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Database {
	static Date date;
	static Date date1;	
	static int carID;
	static String phone;
	static String CostumerName;
	static int type;
	static String carName;
	static String name;
	static int id;


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

	public static String getPhone(int id) {
		try {
			Statement select = conn.createStatement();		
			String getIt = "SELECT * FROM Reservation WHERE id="+id;
			ResultSet result = select.executeQuery(getIt);
			result.next();
			phone = result.getString(3);		
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

	public static GregorianCalendar getStartDate(int id) {
		GregorianCalendar cal = new GregorianCalendar();
		try {
			Statement select = conn.createStatement();			
			String getIt = "SELECT * FROM Reservation WHERE id="+id;
			ResultSet result = select.executeQuery(getIt);
			result.next();
			String startDate = result.getString(5);
			date = Date.valueOf(startDate);
			cal.setTime(date);
			System.out.println("Startdate = "+ cal);
		}							
		catch (Exception e) {			
			e.printStackTrace();
		}
		return cal;			
	}	
	public static GregorianCalendar getEndDate(int id) {
		GregorianCalendar cal = new GregorianCalendar();
		try {
			Statement select = conn.createStatement();		
			String getIt = "SELECT * FROM Reservation WHERE id="+id;
			ResultSet result = select.executeQuery(getIt);
			result.next();
			String endDate = result.getString(5);
			date = Date.valueOf(endDate);
			cal.setTime(date);
			System.out.println("endDate = "+ cal);
		}							
		catch (Exception e) {			
			e.printStackTrace();
		}
		return cal;			
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

	public static ArrayList<Car> initCars(){
		ArrayList<Car> cars = new ArrayList<Car>();
		try {			
			Statement select = conn.createStatement();		
			String getIt = "SELECT * FROM Car";
			ResultSet result = select.executeQuery(getIt);
			while (result.next()) {
				int carID = result.getInt(1);
				int type = result.getInt(2);
				String name = result.getString(3);
				CarType cType = null;

				if (type == 1){
					cType = CarType.SEDAN;
				}
				else if (type == 2){
					cType = CarType.VAN;
				}
				else if (type == 3){
					cType = CarType.STATIONCAR;
				}
				else if (type == 4){
					cType = CarType.SPORTSCAR;
				}	
				cars.add(new Car(cType, name, carID));

			}	
		}		
		catch (Exception e) {
			e.printStackTrace();
		}

		return cars;
	}

	public static ArrayList<Reservation> initReservs(){
		ArrayList<Reservation> res = new ArrayList<Reservation>();
		GregorianCalendar cal = new GregorianCalendar();
		GregorianCalendar cal1 = new GregorianCalendar();

		try {			
			Statement select = conn.createStatement();		
			String getIt = "SELECT * FROM Reservation";
			ResultSet result = select.executeQuery(getIt);
			while (result.next()) {
				int id = result.getInt(1);
				int carID = result.getInt(2);
				String phone = result.getString(3);
				String name = result.getString(4);
				String startDate = result.getString(5);
				date = Date.valueOf(startDate);
				cal.setTime(date);
				String endDate = result.getString(6);
				date1 = Date.valueOf(endDate);
				cal1.setTime(date1);

				res.add(new Reservation(id, carID, cal, cal1, phone, name));
			}	
			System.out.println(res);	
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	// This method searches through the reservations and gathers the reservations that match the search criteria in an arraylist.
	public static ArrayList<Reservation> grabMonth(CarType carType, GregorianCalendar month) {
		ArrayList<Car> cars = initCars();
		ArrayList<Reservation> res = initReservs();
		ArrayList<Reservation> outputRes = new ArrayList<Reservation>();
		
		// Figure out start and end of month
		GregorianCalendar monthStart = new GregorianCalendar(
				month.get(Calendar.YEAR), 
				month.get(Calendar.MONTH), 
				month.getActualMinimum(Calendar.DAY_OF_MONTH));
		monthStart.add(Calendar.DAY_OF_MONTH, -1);
		
		GregorianCalendar monthEnd = new GregorianCalendar(
				month.get(Calendar.YEAR), 
				month.get(Calendar.MONTH), 
				month.getActualMaximum(Calendar.DAY_OF_MONTH));
		monthStart.add(Calendar.DAY_OF_MONTH, 1);
		
		// Gets all the car ids from the reservations
		CarType cType = null;
		for (Reservation r : res){
			int carID = r.getCarId();	
			// now checks for all the cars that match the id specified in the other forloop, then it finds its enum type
			for (Car c : cars){
				if (c.getId() == carID) {
					cType = c.getType();
				}
			}
//			int cTypeInt = 0;	
//
//			if (cType == CarType.SEDAN){
//				cTypeInt = 1;
//			}
//			else if (cType == CarType.VAN){
//				cTypeInt = 2;
//			}
//			else if (cType == CarType.STATIONCAR){
//				cTypeInt = 3;
//			}
//			else if (cType == CarType.SPORTSCAR){
//				cTypeInt = 4;
//			}
			r.getEndDate();
			// We check if the carType matches the carType of the reservation and it ends after the months starts and starts before the month ends.
			if (cType == carType && r.getEndDate().after(monthStart) == true && r.getStartingDate().before(monthEnd) == true){
				outputRes.add(r);
			}




		}
		System.out.println(outputRes);
		return outputRes;


	}

	// This method closes the database
	public void closeDb() {
		try {
			conn.close();
		} catch(Exception e) {}
	}

}	
