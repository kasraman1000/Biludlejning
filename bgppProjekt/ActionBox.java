package bgppProjekt;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ActionBox extends JPanel {

	// The layout manager for this panel
	CardLayout cards;
	// The two states the Panel can be in
	InspectBox inspectBox;
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
		inspectBox = new InspectBox();
		newReservationBox = new JPanel();

		cards.addLayoutComponent(searchBox, "SEARCH");
		cards.addLayoutComponent(inspectBox, "INSPECT");
		cards.addLayoutComponent(newReservationBox, "NEW");
		
		

		searchBox.add(new JLabel("SEARCH WINDOW GOES HERE!"));
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
	
	
	
	private class InspectBox extends JPanel 
	{
		JPanel dataPanel;
		JPanel buttonPanel; 
		JTextField nameField;
		JTextField phoneField;
		JTextField carField;
		
		public InspectBox() 
		{
			makeFrame();
		}
		
		private void makeFrame() 
		{
			setLayout(new BorderLayout());
			
			// Adding all the data fields
			dataPanel = new JPanel();
			dataPanel.setLayout(new GridLayout(0,2));
			
			dataPanel.add(new JLabel("Name: "));
			nameField = new JTextField();
			dataPanel.add(nameField);
			
			dataPanel.add(new JLabel("Phone Number: "));
			phoneField = new JTextField();
			dataPanel.add(phoneField);
			
			dataPanel.add(new JLabel("Car Name: "));
			carField = new JTextField();
			dataPanel.add(carField);
			
			dataPanel.add(new JLabel("Start Date: "));
			dataPanel.add(new JLabel("End Date: "));
			
			add(dataPanel, BorderLayout.CENTER);
			
			// Adding the buttons
			buttonPanel = new JPanel();
			JButton saveButton = new JButton("Save Changes");
			JButton deleteButton = new JButton("Delete Reservation");
			
			buttonPanel.add(saveButton);
			buttonPanel.add(deleteButton);
			
			add(buttonPanel, BorderLayout.SOUTH);
			
		}
		
		public void showReservation(Reservation r) 
		{
			nameField.setText(r.getCustomerName());
			phoneField.setText(r.getCustomerPhone());
			carField.setText(r.getCarId());
		}
	}

}
