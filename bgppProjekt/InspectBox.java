package bgppProjekt;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * One of the ActionBox panels, this one handles creating the interface for editing exsisting selected reservations
 */

public class InspectBox extends JPanel 
{
	private ArrayList<Car> cars;
	private Reservation selectedReservation;
	
	private JPanel dataPanel;
	
	private JTextField nameField;
	private JTextField phoneField;
	private JTextField carField;

	private JPanel startDropdowns;
	private JPanel endDropdowns;
	private JComboBox<Integer> startDate;
	private JComboBox<Integer> startMonth;
	private JComboBox<Integer> startYear;
	private JComboBox<Integer> endDate;
	private JComboBox<Integer> endMonth;
	private JComboBox<Integer> endYear;
	
	private JPanel buttonPanel; 
	private JButton saveButton;
	private JButton deleteButton;

	public InspectBox() 
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
		saveButton = new JButton("Save Changes");

		deleteButton = new JButton("Delete Reservation");

		buttonPanel.add(saveButton);
		buttonPanel.add(deleteButton);

		add(buttonPanel, BorderLayout.SOUTH);

	}
	
	/**
	 * Filling out the cars array with a list of all available cars
	 */
	public void fillCars(ArrayList<Car> c) {
		cars = c;
	}
	
	/**
	 * Constructs a new Reservation-object from the data in the fields
	 * The database ID of the reservation is preserved
	 * @return A new Reservation-object
	 */
	public Reservation getNewReservation() {
		Reservation r = new Reservation(
				selectedReservation.getId(),
				selectedReservation.getCarId(),
				new GregorianCalendar(
						(int) startYear.getSelectedItem(), 
						(int) startMonth.getSelectedItem() - 1, 
						(int) startDate.getSelectedItem()),
				new GregorianCalendar(
						(int) endYear.getSelectedItem(),  
						(int) endMonth.getSelectedItem() - 1, 
						(int) endDate.getSelectedItem()),
				phoneField.getText(),
				nameField.getText());
		
		return r;
	}
	
	/**
	 * @return the saveButton
	 */
	public JButton getSaveButton() {
		return saveButton;
	}

	/**
	 * @return the deleteButton
	 */
	public JButton getDeleteButton() {
		return deleteButton;
	}

	/**
	 * Fills out the datafields with data from a reservation-object
	 * @param r The reservation to be shown
	 */
	public void showReservation(Reservation r) 
	{
		selectedReservation = r;

		nameField.setText(r.getCustomerName());
		phoneField.setText(r.getCustomerPhone());
		for (Car c : cars) {
			if (c.getId() == r.getCarId()) {
				carField.setText(c.getName());
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
