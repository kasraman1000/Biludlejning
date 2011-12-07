package bgppProjekt;

/**
 * A class to represent a single car from the database
 * 
 * @author Kasra Tahmasebi
 */
public class Car {
	
	private CarType type;
	private String name;
	
	/**
	 * The Car constructor
	 * 
	 * @param type The type of car
	 * @param name The name of the car
	 */
	public Car(CarType type, String name) {
		this.type = type;
		this.name = name;
	}

	/**
	 * @return The type of car
	 */
	public CarType getType() {
		return type;
	}

	/**
	 * @return The name of the car
	 */
	public String getName() {
		return name;
	}
	

}
