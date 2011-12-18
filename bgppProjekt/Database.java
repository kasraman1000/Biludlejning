package bgppProjekt;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import com.mysql.jdbc.Connection;

/**
 * Class containing all functions handled between our program and the database.
 */

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

	// The Connection object we use to open and close the connection between program and database.
	private static Connection conn = null;
	// The Driver object for our Database
	private static String driver = "com.mysql.jdbc.Driver";		
	private static String dbName = "BiludlejningCrelde";
	private static String username = "Crelde", password = "bil";
	/**
	 * Function that creates a connection to the database
	 * 
	 * @return Returns true if we are connected to the database, false if not.
	 */
	public static Boolean connect() {
		boolean isConnected = false;
		// Here we connect to the database
		try {
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://" +
					"mysql.itu.dk/" + dbName, username, password);
			isConnected = true;
		} catch (Exception e) {
		}			
		return isConnected;
	}
	/**
	 * Creates two tables in the database, and fills in all the cars, only called one single time.
	 */
	public static void initiateDb() {
		try {
			Statement initiate = conn.createStatement();
			//Creates the Car Table
			initiate.executeUpdate(
					"CREATE TABLE Car"+
							"(id int(11) NOT NULL auto_increment," +
							"`type` int(11) NOT NULL,"+
							"`name` text NOT NULL,"+
							"PRIMARY KEY (id))"+
					"ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;");
			//Creates the Reservation Table
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
			//Inserts all our cars into the Car Table.
			initiate.executeUpdate("INSERT INTO Car VALUES (1, 'Sedan1');" +
					"INSERT INTO Car VALUES (1, 'Sedan2');"+
					"INSERT INTO Car VALUES (1, 'Sedan3');"+
					"INSERT INTO Car VALUES (1, 'Sedan4');"+
					"INSERT INTO Car VALUES (1, 'Sedan5');"+
					"INSERT INTO Car VALUES (2, 'Van1');"+
					"INSERT INTO Car VALUES (2, 'Van2');"+
					"INSERT INTO Car VALUES (2, 'Van3');"+
					"INSERT INTO Car VALUES (2, 'Van4');"+
					"INSERT INTO Car VALUES (2, 'Van5');"+
					"INSERT INTO Car VALUES (3, 'Stationcar1');"+
					"INSERT INTO Car VALUES (3, 'Stationcar2');"+
					"INSERT INTO Car VALUES (3, 'Stationcar3');"+
					"INSERT INTO Car VALUES (3, 'Stationcar4');"+
					"INSERT INTO Car VALUES (3, 'Stationcar5');"+
					"INSERT INTO Car VALUES (4, 'Sportscar1');"+
					"INSERT INTO Car VALUES (4, 'Sportscar2');"+
					"INSERT INTO Car VALUES (4, 'Sportscar3');"+
					"INSERT INTO Car VALUES (4, 'Sportscar4');"+
					"INSERT INTO Car VALUES (4, 'Sportscar5');");

		}				catch (SQLException e){
			e.printStackTrace();
		}
	}
	/**
	 * Function that is used to get an ArrayList of all the cars in our Database.
	 * @return ArrayList of all the Car objects in the database
	 */
	public static ArrayList<Car> initCars(){
		ArrayList<Car> cars = new ArrayList<Car>();
		try {			
			Statement select = conn.createStatement();		
			// Choose ALL cars from Car Table
			String getIt = "SELECT * FROM Car";
			ResultSet result = select.executeQuery(getIt);
			while (result.next()) {
				int carID = result.getInt(1);
				int type = result.getInt(2);
				String name = result.getString(3);
				CarType cType = null;
				//Converting the databases int-values of cartypes to actual CarTypes.
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
				//Finally adding each car to an ArrayList
				cars.add(new Car(cType, name, carID));
			}	
		}		
		catch (Exception e) {
			e.printStackTrace();
		}

		return cars;
	}
	/**
	 * Function to create a new Reservation entry in the Reservation Database if it does not conflict with existing reservations.
	 * 
	 * @param r Reservation object to be created in the Database.
	 * @return Returns Success = true if the Reservation didnt conflict with existing reservations.
	 */
	public static boolean newReservervation(Reservation r) {
		boolean success = false;
		try {			
			int cId = r.getCarId();
			String phone = r.getCustomerPhone();
			String name = r.getCustomerName();
			GregorianCalendar start = r.getStartingDate();
			GregorianCalendar end = r.getEndDate();
			ArrayList<Reservation> reservArray = initReservs();
			//Set of booleans used to check if the new reservation doesn't overlap existing reservations.
			boolean check1=true;
			boolean check2=false;
			boolean check3=false;
			boolean check4=false;
			boolean check5=false;
			boolean check6=false;
			boolean wasOccupied=true;
			// For loop to check all cases to see which case the reservation is compared to all existing reservations with the same CarId.
			for (Reservation reserv : reservArray)
			{
				if (reserv.getCarId()==r.getCarId()){
					//CASE 1:
					if (r.getEndDate().before(reserv.getStartingDate()) == true && r.getStartingDate().before(reserv.getStartingDate())){
						check1=true;
					}
					else{
						check1=false;
					}
					//CASE2:

					if (r.getStartingDate().after(reserv.getEndDate()) == true && r.getEndDate().after(reserv.getEndDate())){
						check2=true;
					}
					else{
						check2=false;
					}
					//CASE3:

					if (r.getStartingDate().before(reserv.getStartingDate()) == true && r.getEndDate().after(reserv.getEndDate())){
						check3=true;
						break;
					}
					else{
						check3=false;
					}
					//CASE4:
					if (r.getStartingDate().before(reserv.getStartingDate()) == true && r.getEndDate().before(reserv.getEndDate()) && r.getEndDate().after(reserv.getStartingDate())){
						check4=true;
						break;
					}
					else{
						check4=false;
					}
					//CASE5:
					if (r.getStartingDate().after(reserv.getStartingDate()) == true && r.getEndDate().after(reserv.getEndDate()) && r.getStartingDate().before(reserv.getEndDate())){
						check5=true;
						break;
					}
					else{
						check5=false;
					}
					//CASE6:

					if (r.getStartingDate().after(reserv.getStartingDate()) == true && r.getEndDate().before(reserv.getEndDate())){
						check6=true;
						break;
					}
					else{
						check6=false;;
					}				
				}

			}
			if (check3==true || check4 == true || check5 == true || check6==true)
			{
				// One of the negative checks were true, which means the new reservation is conflicting with at least one other reservation,
				// And will therefore not be allowed to be created.
				wasOccupied = true;
			}
			else if (check1 == true || check2 == true){
				// If the negative checks were not true, one of the the positive will be true, and we will set occupied to false and sucess to true.	
				wasOccupied = false;
				success = true;
			}
			//If legal dates, telling the database to create the entry in the reservation table.
			if (wasOccupied ==false){
				java.sql.Date startD = new Date(start.getTimeInMillis());
				Date endD = new Date(end.getTimeInMillis());
				Statement select = conn.createStatement();	
				select.executeUpdate("INSERT INTO `Reservation` (`carID`, `phone`, `name`, `start`, `end`) VALUES ('" + cId + "', '" + phone + "', '" +  name + "', '" + startD + "', '" + endD + "');");				
			}
		}							
		catch (Exception e) {			
			e.printStackTrace();
		}
		return success;			
	}
	/**
	 * Function that edits a Reservation entry in the database if it does not conflict with existing reservations.
	 * 
	 * @param r Reservation object representing the values and id to be updated in the Reservation table in the database.
	 * @return Returns Success = true if the Reservation didnt conflict with existing reservations.
	 */
	public static boolean editReservation(Reservation r) {
		boolean success = false;
		try {
			int id = r.getId();
			int cId = r.getCarId();
			String phone = r.getCustomerPhone();
			String name = r.getCustomerName();
			GregorianCalendar start = r.getStartingDate();
			GregorianCalendar end = r.getEndDate();
			ArrayList<Reservation> reservArray = initReservs();
			//Set of booleans used to check if the reservations new dates doesnt overlap existing reservations.
			boolean check1=true;
			boolean check2=false;
			boolean check3=false;
			boolean check4=false;
			boolean check5=false;
			boolean check6=false;
			boolean wasOccupied=true;
			// For loop to check all cases to see which case the reservation is compared to all existing reservations with the same CarId.
			for (Reservation reserv : reservArray)
			{
				if (r.getId() != reserv.getId()){
					if (reserv.getCarId()==r.getCarId()){
						//CASE1:
						if (r.getEndDate().before(reserv.getStartingDate()) == true && r.getStartingDate().before(reserv.getStartingDate())){
							check1=true;
						}
						else{
							check1=false;
						}

						//CASE2:
						if (r.getStartingDate().after(reserv.getEndDate()) == true && r.getEndDate().after(reserv.getEndDate())){
							check2=true;
						}
						else{
							check2=false;
						}

						//CASE3:
						if (r.getStartingDate().before(reserv.getStartingDate()) == true && r.getEndDate().after(reserv.getEndDate())){
							check3=true;
							break;
						}
						else{
							check3=false;
						}

						//CASE4:
						if (r.getStartingDate().before(reserv.getStartingDate()) == true && r.getEndDate().before(reserv.getEndDate()) && r.getEndDate().after(reserv.getStartingDate())){
							check4=true;
							break;
						}
						else{
							check4=false;
						}

						//CASE5:
						if (r.getStartingDate().after(reserv.getStartingDate()) == true && r.getEndDate().after(reserv.getEndDate()) && r.getStartingDate().before(reserv.getEndDate())){
							check5=true;
							break;
						}
						else{
							check5=false;
						}

						//CASE6:
						if (r.getStartingDate().after(reserv.getStartingDate()) == true && r.getEndDate().before(reserv.getEndDate())){
							check6=true;
							break;
						}
						else{
							check6=false;
						}				
					}

				}
			}	
			if (check3==true || check4 == true || check5 == true || check6==true)
			{
				// One of the negative checks were true, which means the new reservation is conflicting with at least one other reservation,
				// And will therefore not be allowed to be created.
				wasOccupied = true;
				success = false;
			}
			else if (check1 == true || check2 == true){
				// If the negative checks were not true, one of the the positive will be true, and we will set occupied to false and sucess to true.	
				wasOccupied = false;
				success = true;
			}
			//If legal dates, telling the database to update the entry in the reservation table.
			if (wasOccupied == false){
				java.sql.Date startD = new Date(start.getTimeInMillis());
				Date endD = new Date(end.getTimeInMillis());
				Statement select = conn.createStatement();	
				select.executeUpdate("UPDATE `Reservation` SET `carID`='" + cId + "', `phone`='" + phone + "', `name`='" +  name + "', `start`='" + startD + "', `end`='" + endD + "' WHERE `id`='" + id + "';");
			}

		}
		catch (Exception e) {			
			e.printStackTrace();
		}
		return success;
	}
	/**
	 * Deletes a Reservation entry in the database.
	 * 
	 * @param id The Id of the reservation we want deleted from the Reservation table in the Database.
	 */
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

	/**
	 * Returns an ArrayList of all the reservation objects in the database
	 * @return ArrayList of all the Reservation objects in the database
	 */
	public static ArrayList<Reservation> initReservs(){
		ArrayList<Reservation> res = new ArrayList<Reservation>();
		GregorianCalendar cal;
		GregorianCalendar cal1;

		try {			
			Statement select = conn.createStatement();		
			String getIt = "SELECT * FROM Reservation";
			ResultSet result = select.executeQuery(getIt);
			while (result.next()) {
				//Set of variables used to create the Reservation objects.
				cal = new GregorianCalendar();
				cal1 = new GregorianCalendar();

				int id = result.getInt(1);
				int carID = result.getInt(2);
				String phone = result.getString(3);
				String name = result.getString(4);
				String startDate = result.getString(5);
				//Converts the Databases' simple date formats to GregorianCalendar Objects.
				date = Date.valueOf(startDate);
				cal.setTime(date);
				String endDate = result.getString(6);
				date1 = Date.valueOf(endDate);
				cal1.setTime(date1);

				// Adds the Reservation to the ArrayList.
				res.add(new Reservation(id, carID, cal, cal1, phone, name));
			}	
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	/**
	 * Function that returns an ArrayList of Reservations which are within a month certain period ( a month ) and share the same CarType.
	 * 
	 * @param carType CarType we want the Reservations to be referencing.
	 * @param monthStart Defines the starting date of the period we want.
	 * @param monthEnd Defines the ending date of the period we want.
	 * @return ArrayList of Reservations who cover dates between monthStart and monthEnd and share the same CarType.
	 */
	public static ArrayList<Reservation> grabPeriod(CarType carType, GregorianCalendar monthStart, GregorianCalendar monthEnd) {
		ArrayList<Car> cars = initCars();
		ArrayList<Reservation> res = initReservs();
		ArrayList<Reservation> outputRes = new ArrayList<Reservation>();
		// Gets all the car ids from the reservations
		CarType cType = null;
		for (Reservation r : res){
			int carID = r.getCarId();	
			// Checks for all the cars that match the id specified in the other forloop, then it finds its enum type.
			for (Car c : cars){
				if (c.getId() == carID) {
					cType = c.getType();
				}
			}
			boolean startCheck = true;
			boolean endCheck = true;
			boolean isInMonth = false;
			// We check if the carType matches the carType of the reservation and it ends after the months starts and starts before the month ends.
			if (cType == carType){

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
					//Reservation is invalid, and is not a part of the period we are interested in.
					isInMonth = false;
				}
				else if (startCheck == true && endCheck == true || startCheck == false && endCheck == false){
					/// Reservation is part of the period we want, and will be added to the outputRes Array.
					isInMonth = true;
				}
			}
			if (isInMonth == true){				
				outputRes.add(r);
			}
		}
		return outputRes;

	}

	/**
	 * This method closes the database
	 */
	public static void closeDb() {
		try {
			conn.close();
		} catch(Exception e) {}
	}

}	
