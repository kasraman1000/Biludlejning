package bgppProjekt;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit TestCase Class testing the Car class.
 * 
 * 
 */

//Creates a car object, and tests the constructor and the getters for the Car class.
public class TestCar extends TestCase {
	public void testCarMethods() {
		Car car = new Car(CarType.SEDAN, "Sedan1", 1);
		assertTrue(car.getId()==1);
		assertTrue(car.getType()==CarType.SEDAN);
		String name = "Sedan1";
		assertTrue(name.equals(car.getName()));
		assertTrue(name.equals(car.toString()));
	}
	
	public static Test suite(){
		return new TestSuite(TestCar.class);
	}
}

