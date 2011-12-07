package bgppProjekt;

import java.util.Calendar;

/**
 * A class to represent a single Reservation entity from the database
 * 
 * @author Kasra Tahmasebi
 *
 */
public class Reservation {
	
		private int id;
		private Car car;
		private Calendar startingDate;
		private Calendar endDate;
		private String customerPhone;
		private String customerName;
		
		/**
		 * The Reservation constructor
		 * 
		 * @param i the ID of the car, from the database
		 * @param c the Car-object that this reservation is associated with
		 * @param date1 The starting date of the reservation
		 * @param date2 The end date of the reservation
		 * @param phone The phone number of the customer
		 * @param name The name of the customer
		 */
		public Reservation(int i, Car c, Calendar date1, Calendar date2, String phone, String name) {
			id 				= i;
			car 			= c;
			startingDate 	= date1;
			endDate 		= date2;
			customerPhone 	= phone;
			customerName 	= name;
		}

		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * @return the car
		 */
		public Car getCar() {
			return car;
		}

		/**
		 * @return the startingDate
		 */
		public Calendar getStartingDate() {
			return startingDate;
		}

		/**
		 * @return the endDate
		 */
		public Calendar getEndDate() {
			return endDate;
		}

		/**
		 * @return the customerPhone
		 */
		public String getCustomerPhone() {
			return customerPhone;
		}

		/**
		 * @return the customerName
		 */
		public String getCustomerName() {
			return customerName;
		}

		/**
		 * @param car the car to set
		 */
		public void setCar(Car car) {
			this.car = car;
		}

		/**
		 * @param startingDate the startingDate to set
		 */
		public void setStartingDate(Calendar startingDate) {
			this.startingDate = startingDate;
		}

		/**
		 * @param endDate the endDate to set
		 */
		public void setEndDate(Calendar endDate) {
			this.endDate = endDate;
		}

		/**
		 * @param customerPhone the customerPhone to set
		 */
		public void setCustomerPhone(String customerPhone) {
			this.customerPhone = customerPhone;
		}

		/**
		 * @param customerName the customerName to set
		 */
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		
		
		

}
