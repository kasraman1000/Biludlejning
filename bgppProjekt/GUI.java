package bgppProjekt;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The central class that instantiates and handles all graphical components, and also serves as a "controller", listening for all actions
 */

public class GUI implements ActionListener {
	// List of all cars
	private ArrayList<Car> cars;

	// The reservation currently being edited
	private Reservation selectedReservation;

	// Interface components //
	// the Main frame (window)
	private final JFrame frame;
	private Container contentPane;

	// The graphical Calendar
	private final GuiCalendar guiCalendar;

	// Dropdowns above the Calendar
	private JPanel dropdowns;
	private final JComboBox<CarType> carTypeList;
	private final JComboBox<String> monthList;
	private final JComboBox<Integer> yearList;

	// Bottom Panel
	private JPanel actionPanel;
	private final JPanel actionBox;
	private JPanel buttonPanel;

	// Buttons at bottom Panel (Outside Actionbox)
	private JButton editReservationButton;
	private JButton newReservationButton;
	private JButton findButton;

	// Layout Manager for the ActionBox
	private CardLayout actionCards;

	// Panels/States for the ActionBox
	private InspectBox inspectBox;
	private SearchBox searchBox;
	private NewReservationBox newReservationBox;
	private JPanel blankBox;


	public GUI() {
		cars = Database.initCars();

		// Grabbing content pane
		frame = new JFrame("Biludlejning");
		contentPane = frame.getContentPane();
		frame.setResizable(false);
		frame.setLocation(300, 400);
		frame.addWindowListener(new CloseWindowListener());
		
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
		searchBox.getList().addListSelectionListener(new ListSelectListener());
		
		
		inspectBox = new InspectBox();
		inspectBox.fillCars(cars);
		inspectBox.getSaveButton().addActionListener(new SaveReservationListener());
		inspectBox.getDeleteButton().addActionListener(new DeleteReservationListener());

		newReservationBox = new NewReservationBox();
		newReservationBox.fillCars(cars);
		newReservationBox.getAddButton().addActionListener(new AddNewReservationListener());
		newReservationBox.getCancelButton().addActionListener(new CancelNewReservationListener());



		blankBox = new JPanel();

		actionCards.addLayoutComponent(searchBox, "SEARCH");
		actionCards.addLayoutComponent(inspectBox, "INSPECT");
		actionCards.addLayoutComponent(newReservationBox, "NEW");
		actionCards.addLayoutComponent(blankBox, "BLANK");

		actionBox.add(searchBox, "SEARCH");
		actionBox.add(inspectBox, "INSPECT");	
		actionBox.add(newReservationBox, "NEW");
		actionBox.add(blankBox, "BLANK");

		actionCards.show(actionBox, "BLANK");
		
		frame.pack();
		frame.setVisible(true);

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
	
	// Invoked when the user double-clicks a reservation, same as clicking "Edit selected Reservation".
		public void editReservationByDoubleClick()
		{
			if (guiCalendar.getSelectedReservation() != null) {
				selectedReservation = guiCalendar.getSelectedReservation();
				inspectBox.showReservation(selectedReservation);
				actionCards.show(actionBox,"INSPECT");
			}
		}

	// Invoked when the "New Reservation"-button has been pressed
	private class NewReservationButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
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

	// Invoked when the "Add"-button inside the
	// New Reservation Panel has been pressed
	private class AddNewReservationListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			Reservation r = newReservationBox.getNewReservation();
			if (r.getStartingDate().after(r.getEndDate()))
			{
				//Warning message telling the user the ending date has to be after the starting date.
				JOptionPane.showMessageDialog(frame,
						"The ending date has to be after the starting date",
						"Error!",
						JOptionPane.WARNING_MESSAGE); 
			}

			else
			{
				boolean overlapped = Database.newReservervation(r);
				if (overlapped == false){
					//Warning message telling the user the ending date has to be after the starting date.
					JOptionPane.showMessageDialog(frame,
							"Your reservation cannont be created as the car is already occupied on the specified date.",
							"Error!",
							JOptionPane.WARNING_MESSAGE); 
				}
				else{
					CarType t = CarType.SEDAN;
					for (Car c: cars) {
						if (r.getCarId() == c.getId()) {
							t = c.getType();
						}
					}
					carTypeList.setSelectedItem(t);
					monthList.setSelectedIndex(r.getStartingDate().get(Calendar.MONTH));
					yearList.setSelectedItem(r.getStartingDate().get(Calendar.YEAR));
					guiCalendar.setSelectedReservation(r); // This doesn't really work at all =/

					actionCards.show(actionBox, "BLANK");
				}	
			}
		}
	}

	// Invoked when the "Cancel"-button inside the
	// New Reservation Panel has been pressed
	private class CancelNewReservationListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			actionCards.show(actionBox, "BLANK");
		}
	}

	// Invoked when the "Save"-button inside the
	// Edit Reservation Panel has been pressed
	private class SaveReservationListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			Reservation r = inspectBox.getNewReservation();
			boolean success = Database.editReservation(r);
			if (success == false){
				//Warning message telling the user the ending date has to be after the starting date.
				JOptionPane.showMessageDialog(frame,
						"Your reservation cannont be edited as the car is already occupied on the specified date.",
						"Error!",
						JOptionPane.WARNING_MESSAGE); 
			}
			else{
				CarType t = CarType.SEDAN;
				for (Car c: cars) {
					if (r.getCarId() == c.getId()) {
						t = c.getType();
					}
				}

				guiCalendar.setSelectedReservation(r); // This doesn't really work at all =/

				actionCards.show(actionBox, "BLANK");
			}

		}
	}

	// Invoked when the "Delete"-button inside the
	// Edit Reservation Panel has been pressed
	private class DeleteReservationListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			Database.delReserv(selectedReservation.getId());
			selectedReservation = null;
			guiCalendar.setSelectedReservation(null);

			actionCards.show(actionBox, "BLANK");
		}
	}
	
	// Invoked when an entry in the search results list is selected
	private class ListSelectListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				selectedReservation = searchBox.getList().getSelectedValue();
				
				CarType t = CarType.SEDAN;
				for (Car c: cars) {
					if (selectedReservation.getCarId() == c.getId()) {
						t = c.getType();
					}
				}
				carTypeList.setSelectedItem(t);
				monthList.setSelectedIndex(selectedReservation.getStartingDate().get(Calendar.MONTH));
				yearList.setSelectedItem(selectedReservation.getStartingDate().get(Calendar.YEAR));
				
				guiCalendar.setSelectedReservation(selectedReservation);
			}
		}
	}
	
	// Invoked many times when window-related events occur,
	// but only used for when the window is closed.
	private class CloseWindowListener implements WindowListener 
	{

		public void windowActivated(WindowEvent e) {
		}
		public void windowClosed(WindowEvent e) {
		}
		public void windowClosing(WindowEvent e) {
			Database.closeDb();
		}
		public void windowDeactivated(WindowEvent e) {
		}
		public void windowDeiconified(WindowEvent e) {
		}
		public void windowIconified(WindowEvent e) {
		}
		public void windowOpened(WindowEvent e) {
		}
		
	}


}
