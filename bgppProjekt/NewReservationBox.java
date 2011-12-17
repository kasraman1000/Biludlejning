package bgppProjekt;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewReservationBox extends JPanel
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
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO outsource to GUI controller
				Database.newReservervation(
						((Car) carList.getSelectedItem()).getId(), 
						phoneField.getText(), 
						nameField.getText(), 
						new GregorianCalendar(
								(int) startYear.getSelectedItem(), 
								(int) startMonth.getSelectedItem() - 1, 
								(int) startDate.getSelectedItem()), 
								new GregorianCalendar(
										(int) endYear.getSelectedItem(),  
										(int) endMonth.getSelectedItem() - 1, 
										(int) endDate.getSelectedItem()));

				cards.show(getParent(), "BLANK");

			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO outsource to GUI controller
				cards.show(getParent(), "BLANK");	
			}
		});

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

