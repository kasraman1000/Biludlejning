package bgppProjekt;
/**
 * Main Class, used to start the database connection and create the gui object, from which the rest of the code is run.
 * 
 *
 */
public class Main {
	
	public static void main(String[] args) {
		Database.connect();
		GUI gui = new GUI();
	}
}
