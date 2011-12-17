package bgppProjekt;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewReservationBox extends JPanel
{
	
	private ArrayList<Car> cars;
	
	private JPanel dataPanel;

	private JTextField nameField;
	private JTextField phoneField;
 
	private JPanel carPanel;
	private JComboBox<CarType> carTypeList;
	private JComboBox<Car> carList;

	private JPanel startDropdowns;
	private JPanel endDropdowns;
	private JComboBox<Integer> startDate;
	private JComboBox<Integer> startMonth;
	private JComboBox<Integer> startYear;
	private JComboBox<Integer> endDate;
	private JComboBox<Integer> endMonth;
	private JComboBox<Integer> endYear;
 
	private JPanel buttonPanel;
	private JButton addButton;
	private JButton cancelButton;
	
	
	public NewReservationBox() 
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
		addButton = new JButton("Add Reservation");

		cancelButton = new JButton("Cancel");

		buttonPanel.add(addButton);
		buttonPanel.add(cancelButton);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public void fillCars(ArrayList<Car> c) {
		cars = c;
	}
	
	/**
	 * @return the addButton
	 */
	public JButton getAddButton() {
		return addButton;
	}

	/**
	 * @return the cancelButton
	 */
	public JButton getCancelButton() {
		return cancelButton;
	}
	
	/**
	 * Constructs a new Reservation-object from the data in the fields
	 * @return A new Reservation-object
	 */
	public Reservation getNewReservation() {
		Reservation r = new Reservation(
				0,		// Does not have a database ID yet
				((Car) carList.getSelectedItem()).getId(),
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

