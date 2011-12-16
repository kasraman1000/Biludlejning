package bgppProjekt;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ActionBox extends JPanel {

	private ArrayList<Car> cars;

	// The layout manager for this panel
	CardLayout cards;
	// The two states the Panel can be in
	InspectBox inspectBox;
	JPanel searchBox;
	NewReservationBox newReservationBox;

	Reservation selectedReservation;

	public ActionBox() {
		makeFrame();
	}

	private void makeFrame() {
		cards = new CardLayout();
		setLayout(cards);

		searchBox = new JPanel();
		inspectBox = new InspectBox();
		newReservationBox = new NewReservationBox();

		cards.addLayoutComponent(searchBox, "SEARCH");
		cards.addLayoutComponent(inspectBox, "INSPECT");
		cards.addLayoutComponent(newReservationBox, "NEW");



		add(searchBox, "SEARCH");
		add(inspectBox, "INSPECT");	
		add(newReservationBox, "NEW");	

	}

	public void fillCars(ArrayList<Car> c) {
		cars = c;
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
		System.out.println("Making a new reservation");
		newReservationBox.newReservation();
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
		inspectBox.showReservation(selectedReservation);

		cards.show(this,"INSPECT");
	}



	private class InspectBox extends JPanel 
	{
		JPanel dataPanel;
		JPanel buttonPanel; 
		JTextField nameField;
		JTextField phoneField;
		JTextField carField;

		JPanel startDropdowns;
		JPanel endDropdowns;
		JComboBox<Integer> startDate;
		JComboBox<Integer> startMonth;
		JComboBox<Integer> startYear;
		JComboBox<Integer> endDate;
		JComboBox<Integer> endMonth;
		JComboBox<Integer> endYear;

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
			carField.setEditable(false);
			dataPanel.add(carField);

			// dropdowns for starting date
			dataPanel.add(new JLabel("Start Date: "));
			startDropdowns = new JPanel();

			startDate = new JComboBox<Integer>();
			for (int i = 1; i <= 31; i++) {
				startDate.addItem(i);
			}

			startMonth = new JComboBox<Integer>();
			for (int i = 1; i <= 12; i++) {
				startMonth.addItem(i);
			}

			startYear = new JComboBox<Integer>();
			for (int i = 2000; i <= 2019; i++) {
				startYear.addItem(i);
			}

			startDropdowns.add(startDate);
			startDropdowns.add(startMonth);
			startDropdowns.add(startYear);

			dataPanel.add(startDropdowns);

			// dropdowns for end date
			dataPanel.add(new JLabel("end Date: "));
			endDropdowns = new JPanel();

			endDate = new JComboBox<Integer>();
			for (int i = 1; i <= 31; i++) {
				endDate.addItem(i);
			}

			endMonth = new JComboBox<Integer>();
			for (int i = 1; i <= 12; i++) {
				endMonth.addItem(i);
			}

			endYear = new JComboBox<Integer>();
			for (int i = 2000; i <= 2019; i++) {
				endYear.addItem(i);
			}

			endDropdowns.add(endDate);
			endDropdowns.add(endMonth);
			endDropdowns.add(endYear);

			dataPanel.add(endDropdowns);

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
			for (Car c : cars) {
				if (c.getId() == r.getCarId()) {
					carField.setText(c.getName());
					System.out.println(c.getName());
				}
			}

			startDate.setSelectedItem(r.getStartingDate().get(Calendar.DATE));
			startMonth.setSelectedItem(1 + r.getStartingDate().get(Calendar.MONTH));
			startYear.setSelectedItem(r.getStartingDate().get(Calendar.YEAR));
			endDate.setSelectedItem(r.getEndDate().get(Calendar.DATE));
			endMonth.setSelectedItem(1 + r.getEndDate().get(Calendar.MONTH));
			endYear.setSelectedItem(r.getEndDate().get(Calendar.YEAR));

		}
	}

	private class NewReservationBox extends JPanel
	{
		JPanel dataPanel;
		JPanel buttonPanel; 
		JTextField nameField;
		JTextField phoneField;

		JPanel carPanel;
		JComboBox<CarType> carTypeList;
		JComboBox<Car> carList;

		JPanel startDropdowns;
		JPanel endDropdowns;
		JComboBox<Integer> startDate;
		JComboBox<Integer> startMonth;
		JComboBox<Integer> startYear;
		JComboBox<Integer> endDate;
		JComboBox<Integer> endMonth;
		JComboBox<Integer> endYear;

		public NewReservationBox() 
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

			// dropdowns for choosing car
			dataPanel.add(new JLabel("Car: "));
			carPanel = new JPanel();
			carTypeList = new JComboBox<CarType>(CarType.values());
			carTypeList.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					// Clear the carList and populate it again
					System.out.println(e.getActionCommand());
					carList.removeAllItems();
					for (Car c : cars) {
						if (c.getType() == carTypeList.getSelectedItem()) {
							System.out.println("adding " + c);
							carList.addItem(c);
						}
					}
					validate();
					
				}
			});			
			carList = new JComboBox<Car>();
			
			carPanel.add(carTypeList);
			carPanel.add(carList);
			
			dataPanel.add(carPanel);

			// dropdowns for starting date
			dataPanel.add(new JLabel("Start Date: "));
			startDropdowns = new JPanel();

			startDate = new JComboBox<Integer>();
			for (int i = 1; i <= 31; i++) {
				startDate.addItem(i);
			}

			startMonth = new JComboBox<Integer>();
			for (int i = 1; i <= 12; i++) {
				startMonth.addItem(i);
			}

			startYear = new JComboBox<Integer>();
			for (int i = 2000; i <= 2019; i++) {
				startYear.addItem(i);
			}

			startDropdowns.add(startDate);
			startDropdowns.add(startMonth);
			startDropdowns.add(startYear);

			dataPanel.add(startDropdowns);

			// dropdowns for end date
			dataPanel.add(new JLabel("End Date: "));
			endDropdowns = new JPanel();

			endDate = new JComboBox<Integer>();
			for (int i = 1; i <= 31; i++) {
				endDate.addItem(i);
			}

			endMonth = new JComboBox<Integer>();
			for (int i = 1; i <= 12; i++) {
				endMonth.addItem(i);
			}

			endYear = new JComboBox<Integer>();
			for (int i = 2000; i <= 2019; i++) {
				endYear.addItem(i);
			}

			endDropdowns.add(endDate);
			endDropdowns.add(endMonth);
			endDropdowns.add(endYear);

			dataPanel.add(endDropdowns);

			add(dataPanel, BorderLayout.CENTER);

			// Adding the buttons
			buttonPanel = new JPanel();
			JButton addButton = new JButton("Add Reservation");
			JButton cancelButton = new JButton("Cancel");

			buttonPanel.add(addButton);
			buttonPanel.add(cancelButton);

			add(buttonPanel, BorderLayout.SOUTH);

		}

		/**
		 * Shows a blank form for filling out a reservation
		 */
		public void newReservation() 
		{

			nameField.setText("");
			phoneField.setText("");
			
			carTypeList.setSelectedIndex(0);

			Calendar cal = Calendar.getInstance();
			startDate.setSelectedItem(cal.get(Calendar.DATE));
			startMonth.setSelectedItem(1 + cal.get(Calendar.MONTH));
			startYear.setSelectedItem(cal.get(Calendar.YEAR));
			endDate.setSelectedItem(1 + cal.get(Calendar.DATE));
			endMonth.setSelectedItem(1 + cal.get(Calendar.MONTH));
			endYear.setSelectedItem(cal.get(Calendar.YEAR));

		}


	}

}
