package bgppProjekt;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class GUI implements ActionListener {
	
	private ArrayList<Car> cars;

	public GUI() {
		cars = Database.initCars();
		
		makeFrame();
	}

	/**
	 * Constructs the graphical interface
	 */
	private void makeFrame() {
		System.out.println("Making the primary JFrame");

		final JFrame frame = new JFrame("Biludlejning");
		Container contentPane = frame.getContentPane();

		contentPane.setLayout(new BorderLayout());

		// Adding the Calendar
		final GuiCalendar guiCalendar = new GuiCalendar(this);
		contentPane.add(guiCalendar, BorderLayout.CENTER);
		
		guiCalendar.fillCars(cars);

		// Adding dropdowns above the calendar
	
		JPanel dropdowns = new JPanel();
		dropdowns.setLayout(new FlowLayout(FlowLayout.CENTER));

		contentPane.add(dropdowns, BorderLayout.NORTH);

		// Dropdown for Car types
		dropdowns.add(new JLabel("Car type:"));

		final JComboBox<CarType> carTypeList = new JComboBox<CarType>(CarType.values());
		dropdowns.add(carTypeList);
		carTypeList.setSelectedItem(guiCalendar.getSelectedCarType());
		carTypeList.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				guiCalendar.setSelectedCarType((CarType) carTypeList.getSelectedItem());
				frame.validate();

			}
		});

		// Dropdown for months
		dropdowns.add(new JLabel("Month:"));

		String[] months = {
				"January", "Febuary", "March", "April", "May",
				"June", "July", "August", "Sebtemper", "October",
				"November", "December"
		};
		final JComboBox<String> monthList = new JComboBox<String>(months);
		dropdowns.add(monthList);
		monthList.setSelectedIndex(guiCalendar.getSelectedMonth());
		monthList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiCalendar.setSelectedMonth(monthList.getSelectedIndex());
			}
		});

		// Dropdown for years
		dropdowns.add(new JLabel("Year:"));

		final JComboBox<Integer> yearList = new JComboBox<Integer>();
		for (int i = 2000; i < 2020; i++) {
			yearList.addItem(i);
		}
		dropdowns.add(yearList);
		yearList.setSelectedItem(guiCalendar.getSelectedYear());
		yearList.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				guiCalendar.setSelectedYear((int) yearList.getSelectedItem());

			}
		});

		// bottom panel for actionbox and buttons
		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new BorderLayout());
		contentPane.add(actionPanel, BorderLayout.SOUTH);

		// adding actionbox
		final ActionBox actionBox = new ActionBox();
		actionPanel.add(actionBox, BorderLayout.CENTER);
		actionPanel.addAncestorListener(new AncestorListener() {
			public void ancestorRemoved(AncestorEvent arg0) {
				System.out.println("REMOVED!");
			}
			public void ancestorMoved(AncestorEvent arg0) {
				System.out.println("MOVED!");
			}
			public void ancestorAdded(AncestorEvent arg0) {
				System.out.println("ADDED!");
			}
		});
		
		actionBox.fillCars(cars);



		// Adding buttons 
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,1));
		actionPanel.add(buttonPanel, BorderLayout.EAST);

		JButton editReservationButton = new JButton("Edit Selected Reservation");
		editReservationButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) 
			{
				if (guiCalendar.getSelectedReservation() != null) {
					actionBox.inspect(guiCalendar.getSelectedReservation());
				}
				
				
			}
		});
		
		JButton newReservationButton = new JButton("New Reservation");
		newReservationButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				actionBox.newReservation();
				
			}
		});
		
		JButton findButton = new JButton("Find...");
		findButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				actionBox.find();

			}
		});

		buttonPanel.add(editReservationButton);
		buttonPanel.add(newReservationButton);
		buttonPanel.add(findButton);



		frame.pack();
		frame.setVisible(true);
		System.out.println("Going visible!");


	}

	public void doubleClicked() {
		// dunno what to do here, can't make shit work
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("Recieved action: " + e.getActionCommand());
	}


}
