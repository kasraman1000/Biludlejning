package bgppProjekt;

/**
 * A class to represent a single car from the database
 * 
 * 
 */
public class Car {
	
	private CarType type;
	private String name;
	private int id;
	
	/**
	 * The Car constructor
	 * 
	 * @param type The type of car
	 * @param name The name of the car
	 */
	public Car(CarType type, String name, int id) {
		this.type = type;
		this.name = name;
		this.id   = id;
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
	
	/**
	 * @return The id of the car
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return Returns the cars name.
	 */
	public String toString() {
		return getName();
	}

}
