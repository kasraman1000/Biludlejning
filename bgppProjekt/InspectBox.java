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

public class InspectBox extends JPanel 
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
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO outsource to GUI controller
				Database.editReservation(
						selectedReservation.getId(), 
						selectedReservation.getCarId(), 
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

			}
		});

		JButton deleteButton = new JButton("Delete Reservation");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO outsource to GUI controller
				Database.delReserv(selectedReservation.getId());
				selectedReservation = null;

				cards.show(getParent(), "BLANK");
			}
		});

		buttonPanel.add(saveButton);
		buttonPanel.add(deleteButton);

		add(buttonPanel, BorderLayout.SOUTH);

	}
	
	/**
	 * Fills out the datafields with data from a reservation-object
	 * @param r The reservation to be shown
	 */
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
		startMonth.setSelectedItem(2 + r.getStartingDate().get(Calendar.MONTH));
		startYear.setSelectedItem(r.getStartingDate().get(Calendar.YEAR));
		endDate.setSelectedItem(r.getEndDate().get(Calendar.DATE));
		endMonth.setSelectedItem(2 + r.getEndDate().get(Calendar.MONTH));
		endYear.setSelectedItem(r.getEndDate().get(Calendar.YEAR));

	}
}
