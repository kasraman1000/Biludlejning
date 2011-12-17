package bgppProjekt;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI implements ActionListener {
	// List of all cars
	private ArrayList<Car> cars;
	
	// The reservation currently being edited
	private Reservation selectedReservation;

	// Interface components //
	// the Main frame (window)
	final JFrame frame;
	Container contentPane;

	// The graphical Calendar
	final GuiCalendar guiCalendar;

	// Dropdowns above the Calendar
	JPanel dropdowns;
	final JComboBox<CarType> carTypeList;
	final JComboBox<String> monthList;
	final JComboBox<Integer> yearList;

	// Bottom Panel
	JPanel actionPanel;
	final JPanel actionBox;
	JPanel buttonPanel;

	// Buttons at bottom Panel (Outside Actionbox)
	JButton editReservationButton;
	JButton newReservationButton;
	JButton findButton;
	
	// Layout Manager for the ActionBox
	CardLayout actionCards;
	
	// Panels/States for the ActionBox
	InspectBox inspectBox;
	SearchBox searchBox;
	NewReservationBox newReservationBox;
	JPanel blankBox;


	public GUI() {
		cars = Database.initCars();

		// Grabbing content pane
		frame = new JFrame("Biludlejning");
		contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());

		// Adding the Calendar
		guiCalendar = new GuiCalendar(this);
		contentPane.add(guiCalendar, BorderLayout.CENTER);
		guiCalendar.fillCars(cars);

		// Adding dropdowns, and their respective listeners, above the calendar
		dropdowns = new JPanel();
		dropdowns.setLayout(new FlowLayout(FlowLayout.CENTER));
		contentPane.add(dropdowns, BorderLayout.NORTH);

		//// Dropdown for Car types
		dropdowns.add(new JLabel("Car type:"));

		carTypeList = new JComboBox<CarType>(CarType.values());
		dropdowns.add(carTypeList);

		carTypeList.setSelectedItem(guiCalendar.getSelectedCarType());
		carTypeList.addActionListener(new CarTypeListListener());

		//// Dropdown for months
		dropdowns.add(new JLabel("Month:"));

		String[] months = {
				"January", "Febuary", "March", "April", "May",
				"June", "July", "August", "Sebtemper", "October",
				"November", "December"
		};
		monthList = new JComboBox<String>(months);
		dropdowns.add(monthList);

		monthList.setSelectedIndex(guiCalendar.getSelectedMonth());
		monthList.addActionListener(new MonthListListener());

		//// Dropdown for years
		dropdowns.add(new JLabel("Year:"));

		yearList = new JComboBox<Integer>();
		for (int i = 2000; i < 2020; i++) {
			yearList.addItem(i);
		}
		dropdowns.add(yearList);

		yearList.setSelectedItem(guiCalendar.getSelectedYear());
		yearList.addActionListener(new YearListListener());

		// bottom panel for actionbox and buttons
		actionPanel = new JPanel();
		actionPanel.setLayout(new BorderLayout());
		contentPane.add(actionPanel, BorderLayout.SOUTH);

		// Adding buttons and eventlisteners
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,1));
		actionPanel.add(buttonPanel, BorderLayout.EAST);

		editReservationButton = new JButton("Edit Selected Reservation");
		editReservationButton.addActionListener(new EditReservationButtonListener());

		newReservationButton = new JButton("New Reservation");
		newReservationButton.addActionListener(new NewReservationButtonListener());

		findButton = new JButton("Find...");
		findButton.addActionListener(new FindButtonListener());

		buttonPanel.add(editReservationButton);
		buttonPanel.add(newReservationButton);
		buttonPanel.add(findButton);
		
		// Adding actionbox
		actionBox = new JPanel();
		actionCards = new CardLayout();
		actionBox.setLayout(actionCards);
		actionPanel.add(actionBox, BorderLayout.CENTER);
		
		searchBox = new SearchBox();
		inspectBox = new InspectBox();
		newReservationBox = new NewReservationBox();
		blankBox = new JPanel();
		
		actionCards.addLayoutComponent(searchBox, "SEARCH");
		actionCards.addLayoutComponent(inspectBox, "INSPECT");
		actionCards.addLayoutComponent(newReservationBox, "NEW");
		actionCards.addLayoutComponent(blankBox, "BLANK");

		actionBox.add(searchBox, "SEARCH");
		actionBox.add(inspectBox, "INSPECT");	
		actionBox.add(newReservationBox, "NEW");
		actionBox.add(blankBox, "BLANK");
		
		
		

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


	// Eventlisteners //
	
	// Invoked when the car type dropdown has been used
	private class CarTypeListListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			guiCalendar.setSelectedCarType((CarType) carTypeList.getSelectedItem());
			frame.validate();
		}
	}

	// Invoked when the Month dropdown has been used
	private class MonthListListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			guiCalendar.setSelectedMonth(monthList.getSelectedIndex());
		}
	}

	// Invoked when the Year dropdown has been used
	private class YearListListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			guiCalendar.setSelectedYear((int) yearList.getSelectedItem());
		}
	}

	// Invoked when the "Edit selected Reservation"-button has been pressed
	private class EditReservationButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			if (guiCalendar.getSelectedReservation() != null) {
				selectedReservation = guiCalendar.getSelectedReservation();
				inspectBox.showReservation(selectedReservation);
				actionCards.show(actionBox,"INSPECT");
			}
		}
	}

	// Invoked when the "New Reservation"-button has been pressed
	private class NewReservationButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			System.out.println("Making a new reservation");
			newReservationBox.newReservation();
			actionCards.show(actionBox, "NEW");
		}
	}
	
	// Invoked when the "Find"-button has been pressed
	private class FindButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			actionCards.show(actionBox, "SEARCH");
		}
	}


}
