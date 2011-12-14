package bgppProjekt;

import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ActionBox extends JPanel {

	// The layoutmanager for this panel
	CardLayout cards;
	// The two states the Panel can be in
	JPanel inspectBox;
	JPanel searchBox;
	JPanel newReservationBox;
	
	Reservation selectedReservation;

	public ActionBox() {
		makeFrame();
	}

	private void makeFrame() {
		cards = new CardLayout();
		setLayout(cards);

		searchBox = new JPanel();
		inspectBox = new JPanel();
		newReservationBox = new JPanel();

		cards.addLayoutComponent(searchBox, "SEARCH");
		cards.addLayoutComponent(inspectBox, "INSPECT");
		cards.addLayoutComponent(newReservationBox, "NEW");

		searchBox.add(new JLabel("SEARCH WINDOW GOES HERE!"));
		inspectBox.add(new JLabel("INSPECT WINDOW GOES HERE!"));
		newReservationBox.add(new JLabel("NEW RESERVATION WINDOW GOES HERE!"));

		add(searchBox, "SEARCH");
		add(inspectBox, "INSPECT");	
		add(newReservationBox, "NEW");	

	}
	
	/**
	 * Switches the ActionBox to 'find'-mode
	 */
	public void find() {
		cards.show(this, "SEARCH");
	}

	/**
	 * Switches the ActionBox to 'new reservation'-mode
	 */
	public void newReservation() {
		cards.show(this, "NEW");
	}
	
	/**
	 * Switches the ActionBox to 'inspect'-mode
	 * @param id the ID of the reservation entity to be inspected
	 */
	public void inspect(int id) {
		System.out.println("Inspecting ID: " + id);
	}
	
	/**
	 * Switches the ActionBox to 'inspect'-mode
	 * @param reservation The reservation-object to be inspected
	 */
	public void inspect(Reservation reservation) {
		System.out.println("Inspecting Reservation-object with ID: " + reservation.getId());
		selectedReservation = reservation;
		
		cards.show(this,"INSPECT");
	}

}
