package bgppProjekt;

import java.util.ArrayList;


public class Main {
	
	public static void main(String[] args) {
		

		//GUITest guiTest = new GUITest();

	//	GUI gui = new GUI();
		Database.connect();
		//Database.select();
		//Database.initiateDb();
/*
		Database.getcarID(1);
		Database.getPhone(1);
		Database.getCostumerName(1);
		D
		Database.getEndDate(1);
		Database.getType(1);
		Database.getCarName(1);
		*/
		Database.initCars();
		Database.initReservs();
Database.getStartDate(1);
		


	}
}
