package bgppProjekt;

import java.util.GregorianCalendar;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit TestCase Class testing the Reservation class.
 * 
 * 
 */
//Creates a car object, and single fields with the same information, to test the constructor and the getters for the Reservation class.
public class TestReservation extends TestCase {
	public void testReservationMethods() {
		GregorianCalendar date1 = new GregorianCalendar(2011, 12, 24);
		GregorianCalendar date2 = new GregorianCalendar(2011, 12, 31);
		GregorianCalendar date3 = new GregorianCalendar(2011, 12, 24);
		GregorianCalendar date4 = new GregorianCalendar(2011, 12, 31);
		String phone = "88888888";
		String name = "Peter";
		Reservation res = new Reservation(1, 1, date1, date2, "88888888", "Peter");
		assertTrue(res.getId()==1);
		assertTrue(res.getCarId()==1);
		assertTrue(res.getStartingDate().equals(date3));
		assertTrue(res.getEndDate().equals(date4));
		assertTrue(res.getCustomerPhone().equals(phone));
		assertTrue(res.getCustomerName().equals(name));
	}
	
	public static Test suite(){
		return new TestSuite(TestReservation.class);
	}
}

