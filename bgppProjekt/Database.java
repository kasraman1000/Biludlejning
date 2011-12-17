package bgppProjekt;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
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
	// Connects to the database
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
	// Creates two tables in the database
	public static void initiateDb() {
		try {
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
	// Returns id of the car in the reservation table
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

	// Returns the name of the car in the Cars table
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
	// Returns the type of car in the Cars table (as an int)
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
	// Returns an ArrayList of all the Car objects in the database
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
	// Function that takes all the relevant information to create a new Reservation entry in the database.
	public static void newReservervation(Reservation r) {
		try {
			ArrayList<Car> cars = initCars();
			CarType cType = null;
			int carID = r.getCarId();	
			// now checks for all the cars that match the id specified in the other forloop, then it finds its enum type
			for (Car c : cars){
				if (c.getId() == carID) {
					cType = c.getType();
				}
			}
			
			ArrayList<Reservation> res = grabPeriod(cType, r.getStartingDate(), r.getEndDate());
			int cId = r.getCarId();
			String phone = r.getCustomerPhone();
			String name = r.getCustomerName();
			GregorianCalendar start = r.getStartingDate();
			GregorianCalendar end = r.getEndDate();
					ArrayList<Reservation> reservArray = initReservs();
					int inputId = 1;
					boolean check1=false;
					boolean check2=false;
					boolean check3=false;
					boolean check4=false;
					boolean check5=false;
					boolean check6=false;
					boolean wasOccupied=true;

					for (Reservation reserv : reservArray)
					{
						if (reserv.getCarId()==inputId){
							//CASE 1:
							if (r.getEndDate().before(reserv.getStartingDate()) == true && r.getStartingDate().before(reserv.getStartingDate())){
								check1=true;
								System.out.println("check1 true");
							}
							else check1=false;

							//CASE2:

							if (r.getStartingDate().after(reserv.getEndDate()) == true && r.getEndDate().before(reserv.getEndDate())){
								check2=true;
								System.out.println("check2 true");
							}
							else check2=false;
							
							//CASE3:
							
							if (r.getStartingDate().before(reserv.getStartingDate()) == true && r.getEndDate().after(reserv.getEndDate())){
								check3=true;
								System.out.println("check3 true");
							}
							else check3=false;

							//CASE4:
							if (r.getStartingDate().before(reserv.getStartingDate()) == true && r.getEndDate().before(reserv.getEndDate()) && r.getEndDate().after(reserv.getStartingDate())){
								check4=true;
								System.out.println("check4 true");
							}
							else check4=false;
							
							//CASE5:
							if (r.getStartingDate().after(reserv.getStartingDate()) == true && r.getEndDate().after(reserv.getEndDate()) && r.getStartingDate().before(reserv.getEndDate())){
								check5=true;
								System.out.println("check5 true");
							}
							else check5=false;
							
							//CASE6:
							
							if (r.getStartingDate().after(reserv.getStartingDate()) == true && r.getEndDate().before(reserv.getEndDate())){
								check6=true;
								System.out.println("check6 true");
							}
							else check6=false;					
						}
						if (check3==true || check4 == true || check5 == true || check6==true)
						{
							System.out.println("denied, inteferred with existing reservations");
							wasOccupied = true;
							break;
						}
						else if (check1 == true || check2 == true){
							System.out.println("dates were fine, creating new reservation");	
							wasOccupied = false;
						}
					}
					

				if (wasOccupied ==false){
				java.sql.Date startD = new Date(start.getTimeInMillis());
				System.out.println(startD);
				Date endD = new Date(end.getTimeInMillis());
				System.out.println(endD);
				Statement select = conn.createStatement();	
				select.executeUpdate("INSERT INTO `Reservation` (`carID`, `phone`, `name`, `start`, `end`) VALUES ('" + cId + "', '" + phone + "', '" +  name + "', '" + startD + "', '" + endD + "');");
			}
		}							
		catch (Exception e) {			
			e.printStackTrace();
		}			
	}
	// Edits a given reservation fields in the database.
	public static void editReservation(Reservation r) {
//		try {
//			ArrayList<Car> cars = initCars();
//			CarType cType = null;
//			int carID = r.getCarId();	
//			// now checks for all the cars that match the id specified in the other forloop, then it finds its enum type
//			for (Car c : cars){
//				if (c.getId() == carID) {
//					cType = c.getType();
//				}
//			}
//			//			ArrayList<Reservation> res = grabPeriod(cType, r.getStartingDate(), r.getEndDate());
//			ArrayList<Reservation> res = initReservs();
//			int cId = r.getCarId();
//			String phone = r.getCustomerPhone();
//			String name = r.getCustomerName();
//			GregorianCalendar start = r.getStartingDate();
//			GregorianCalendar end = r.getEndDate();
//
//			//System.out.println(r.getEndDate());
//			boolean startCheck = true;
//			boolean endCheck = true;
//			boolean isOccupied = false;
//			for (Reservation re : res){
//				if (r.getId()!=re.getId())
//					System.out.println(r.getID);
//				{
//					//STARTCHECK
//					if (r.getStartingDate().after(re.getEndDate()) == true ){
//
//						startCheck = true;
//					}
//					else {
//						SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yy");
//						SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yy");
//						String dato1 = sdf1.format(r.getStartingDate().getTime());
//						String dato2 = sdf2.format(re.getEndDate().getTime());
//						System.out.println(dato1);
//						System.out.println(dato2);
//						startCheck = false;
//					}
//					//ENDCHECK
//					if (r.getEndDate().before(re.getStartingDate()) == true){
//						endCheck = true;
//					}
//					else {
//						endCheck = false;
//					}
//					if (startCheck == true && endCheck == false || startCheck == false && endCheck == true){
//						//Reservation is valid, and does not interfere with other reservations.
//						isOccupied = false;
//					}
//					else if (startCheck == true && endCheck == true || startCheck == false && endCheck == false){
//						//First case: Both are true, (r) starts before (re), and ends before 
//						//Second Case: Reservation is "inside" of another, meaning it starts after another and ends before the other one does.
//						isOccupied = true;
//						System.out.println("start =" + startCheck );
//						System.out.println("end =" + endCheck);
//						System.out.println("isOccupied ="+ isOccupied);
//					}
//				}
//				if (isOccupied == false){
//					java.sql.Date startD = new Date(start.getTimeInMillis());
//					System.out.println(startD);
//					Date endD = new Date(end.getTimeInMillis());
//					System.out.println(endD);
//					Statement select = conn.createStatement();	
//					select.executeUpdate("UPDATE `Reservation` SET `carID`='" + cId + "', `phone`='" + phone + "', `name`='" +  name + "', `start`='" + startD + "', `end`='" + endD + "' WHERE `id`='" + id + "';");
//				}
//			}	
//		}
//		catch (Exception e) {			
//			e.printStackTrace();
//		}
	}
	// Returns the phone number of the	 costumer, in the reservation table
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
	// Returns the name of the costumer, in the reservation table
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
	// Returns the starting date of the reservation in a GregorianCalendar format
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
	// Returns the ending date of the reservation in a GregorianCalendar format
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
	// Returns the reservation by id
	public static Reservation getReserv(int id){
		Reservation resid;
		carID = getcarID(id);
		phone = getPhone(id);
		CostumerName = getCostumerName(id);
		GregorianCalendar greg1 = getStartDate(id);
		GregorianCalendar greg2 = getEndDate(id);

		resid = new Reservation(id, carID, greg1, greg2, phone, CostumerName);
		return resid;
	}
	// Returns and ArrayList of the Reservations that match the phonenumber
	public static ArrayList<Reservation> searchPhone(String phone){
		ArrayList<Reservation> res = initReservs();
		ArrayList<Reservation> resPhone = new ArrayList<Reservation>();
		for (Reservation r : res){
			if(phone.equals(r.getCustomerPhone()) ){
				resPhone.add(r);
			}
		}
		return resPhone;
	}
	// Deletes a reservation by id
	public static void delReserv(int id){
		try{
			Statement select = conn.createStatement();
			ArrayList<Reservation> res = initReservs();
			for (Reservation r: res){
				if (id == r.getId()){
					select.executeUpdate("DELETE FROM `Reservation` WHERE `id` = '" + id + "'");		
				}

			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

	}
	// Returns an ArrayList of all the reservation objects in the database
	public static ArrayList<Reservation> initReservs(){
		ArrayList<Reservation> res = new ArrayList<Reservation>();
		GregorianCalendar cal;
		GregorianCalendar cal1;

		try {			
			Statement select = conn.createStatement();		
			String getIt = "SELECT * FROM Reservation";
			ResultSet result = select.executeQuery(getIt);
			while (result.next()) {
				cal = new GregorianCalendar();
				cal1 = new GregorianCalendar();

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
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	// This method searches through the reservations and gathers the reservations that match the search criteria in an arraylist.
	public static ArrayList<Reservation> grabPeriod(CarType carType, GregorianCalendar monthStart, GregorianCalendar monthEnd) {
		System.out.println("Pulling from Database...");
		ArrayList<Car> cars = initCars();
		ArrayList<Reservation> res = initReservs();
		ArrayList<Reservation> outputRes = new ArrayList<Reservation>();

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

			// We check if the carType matches the carType of the reservation and it ends after the months starts and starts before the month ends.
			boolean startCheck = true;
			boolean endCheck = true;
			boolean isInMonth = false;
			if (cType == carType){
				////////////////

				//STARTCHECK
				if ((monthStart).after(r.getEndDate()) == true ){
					startCheck = true;
				}
				else {
					startCheck = false;
				}
				//ENDCHECK
				if ((monthEnd).before(r.getStartingDate()) == true){
					endCheck = true;
				}
				else {
					endCheck = false;
				}
				if (startCheck == true && endCheck == false || startCheck == false && endCheck == true){
					//Reservation is valid, and does not interfere with other reservations.
					isInMonth = false;
				}
				else if (startCheck == true && endCheck == true || startCheck == false && endCheck == false){
					//First case: Both are true, (r) starts before (re), and ends before 
					//Second Case: Reservation is "inside" of another, meaning it starts after another and ends before the other one does.
					isInMonth = true;
				}
			}
			if (isInMonth == true){				
				outputRes.add(r);
			}
		}

		System.out.println("Total size of outputRes: " + outputRes.size());
		return outputRes;

	}
	// This method closes the database
	public void closeDb() {
		try {
			conn.close();
		} catch(Exception e) {}
	}

}	
