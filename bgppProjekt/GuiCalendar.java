package bgppProjekt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JComponent;

/**
 * The calendar graphical module, showing reservations on a monthly basis
 */
public class GuiCalendar extends JComponent implements MouseListener {

	// constants for drawing
	private final int CELL_WIDTH = 20;
	private final int CELL_HEIGHT = 20;
	private final int CARNAME_WIDTH = 100;

	// fields to track double clicks
	int clickCount =0;
	private Date click;
	private int reservId; 

	// class fields
	private ArrayList<Reservation> reservations;
	private ArrayList<Car> cars;
	private Reservation[][] guiArray;

	private CarType selectedCarType;
	private GregorianCalendar selectedMonth;
	private Reservation selectedReservation;

	private GUI gui;

	public GuiCalendar(GUI g) {
		addMouseListener(this);

		gui = g;
		selectedCarType = CarType.SEDAN;
		selectedMonth = new GregorianCalendar();

		cars = new ArrayList<Car>();
		reservations = new ArrayList<Reservation>();
	}

	/**
	 * Makes the calendar reload everything it needs to draw
	 */
	public void reload() {
		// Grab all reservations in the current month
		fillReservations();
		// Calculate the reservations' coordinates for clicking purposes
		fillGuiArray();	

		// Draw the calendar again
		repaint();
	}

	/**
	 * Filling up the 2d array with references to reservations, so that
	 * mouseClicked() knows which Calendar-cell represents what reservation.
	 */
	private void fillGuiArray() {
		int numCars = calcCars();

		ArrayList<Car> selectedCars = new ArrayList<Car>();
		for (Car c : cars) {
			if (c.getType() == selectedCarType) {
				selectedCars.add(c);
			}
		}

		guiArray = new Reservation[numCars][selectedMonth.getActualMaximum(selectedMonth.DAY_OF_MONTH)];
		for (int c = 0; c < numCars; c++){
			for (Reservation r : reservations) {
				if (r.getCarId() == selectedCars.get(c).getId()) {
					GregorianCalendar date = (GregorianCalendar) r.getStartingDate().clone();
					GregorianCalendar end = (GregorianCalendar) r.getEndDate().clone();
					end.add(Calendar.DATE, 1);

					while (date.before(end)) {
						if (date.get(Calendar.MONTH) == selectedMonth.get(Calendar.MONTH)) {
							guiArray[c][date.get(Calendar.DATE) - 1] = r;
						}
						date.add(Calendar.DATE, 1);						
					}
				}
			}
		}
	}

	/**
	 * Filling out the cars array with a list of all available cars
	 */
	public void fillCars(ArrayList<Car> c) {
		cars = c;
		reload();
	}

	/**
	 * Filling out the reservations array with reservation from the
	 * currently selected month
	 */
	private void fillReservations() {
		// Figure out start and end of month
		GregorianCalendar monthStart = new GregorianCalendar(
				selectedMonth.get(Calendar.YEAR), 
				selectedMonth.get(Calendar.MONTH), 
				selectedMonth.getActualMinimum(Calendar.DAY_OF_MONTH));
		monthStart.add(Calendar.DAY_OF_MONTH, -1);
		GregorianCalendar monthEnd = new GregorianCalendar(
				selectedMonth.get(Calendar.YEAR), 
				selectedMonth.get(Calendar.MONTH), 
				selectedMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
		monthStart.add(Calendar.DAY_OF_MONTH, 1);

		reservations = Database.grabPeriod(selectedCarType, monthStart, monthEnd);
	}

	/**
	 * Draws the entire calendar.
	 */
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);

		// Drawing border
		g.drawRect(0, 0, calcWidth(), calcHeight());

		// Drawing the name of the month and year
		g.drawString(
				Integer.toString(selectedMonth.get(Calendar.MONTH) + 1) + "/" + Integer.toString(selectedMonth.get(Calendar.YEAR)), 
				0, 
				CELL_HEIGHT);

		// Drawing everything horizontal (names and lines)
		g.drawLine(0, CELL_HEIGHT, calcWidth(), CELL_HEIGHT);

		int numCars = 0;
		for (Car c : cars) {
			if (c.getType() == selectedCarType) {
				g.drawString(c.getName(), 0, 2*CELL_HEIGHT + (numCars * CELL_HEIGHT));
				g.drawLine(0, 2*CELL_HEIGHT + (numCars * CELL_HEIGHT), calcWidth(), 2*CELL_HEIGHT + (numCars * CELL_HEIGHT));
				numCars++;
			}
		}

		// Drawing everything vertical (Numbers and lines)
		for (int i = 0; i < selectedMonth.getActualMaximum(selectedMonth.DAY_OF_MONTH);i++) {
			g.drawLine(CARNAME_WIDTH + (CELL_WIDTH*i), 0, CARNAME_WIDTH + (CELL_WIDTH*i), calcHeight());
			g.drawString(Integer.toString(i + 1), CARNAME_WIDTH + (CELL_WIDTH*i), CELL_HEIGHT);
		}

		// Filling in reservations
		for (Reservation r : reservations) {
			int carNumber = 1;
			for (Car c : cars) {
				if (c.getType() == selectedCarType) {
					if (c.getId() == r.getCarId()) {

						GregorianCalendar start = (GregorianCalendar) r.getStartingDate().clone();

						while (start.get(Calendar.MONTH) != selectedMonth.get(Calendar.MONTH)) {
							start.add(Calendar.DAY_OF_MONTH, 1);
						}

						GregorianCalendar end = (GregorianCalendar) r.getEndDate().clone();

						while (end.get(Calendar.MONTH) != selectedMonth.get(Calendar.MONTH)) {
							end.add(Calendar.DAY_OF_MONTH, -1);
						}

						if (r == selectedReservation) {
							g.setColor(Color.ORANGE);
						}
						else {
							g.setColor(Color.RED);
						}

						g.fillRect(
								CARNAME_WIDTH + (start.get(Calendar.DATE) - 1) * CELL_WIDTH, 
								carNumber * CELL_HEIGHT, 
								(end.get(Calendar.DATE) - start.get(Calendar.DATE) + 1) * CELL_WIDTH, 
								CELL_HEIGHT);
						g.setColor(Color.BLACK);
						g.drawRect(
								CARNAME_WIDTH + (start.get(Calendar.DATE) - 1) * CELL_WIDTH, 
								carNumber * CELL_HEIGHT, 
								(end.get(Calendar.DATE) - start.get(Calendar.DATE) + 1) * CELL_WIDTH, 
								CELL_HEIGHT);
					}
					carNumber++;
				}
			}
		}
	}

	/**
	 * Function to determine if a click occurred soon enough after the previous one 
	 * to be a double-click, and if it was the same reservation being doubleclicked.
	 * @return Wether the click counted as the second in a doubleclick
	 */
	private boolean doubleClick()
	{
		Date newClick = new Date();
		long duration = 1000;
		if (click != null) {
			duration = newClick.getTime() - click.getTime();
		}
		int reservId2=0;
		if(selectedReservation!=null)
		{
			reservId2 = selectedReservation.getId();
		}

		return (duration<500 && reservId==reservId2);
	}

	// Defining MouseListener-interface methods...

	/**
	 * Whenever the mouse is clicked, it figures out what that click
	 * corresponds to in the calendar 'table' coordinates, and also
	 * selects the reservation clicked, if any.
	 */
	public void mouseClicked(MouseEvent e) {

		int a = (e.getX() - CARNAME_WIDTH) / CELL_WIDTH;
		int b = -1 + (e.getY()/CELL_HEIGHT);
		if (a >= 0 && b >= 0) {
			if (guiArray[b][a] != null) {
				selectedReservation = guiArray[b][a];
				if (clickCount > 0)
				{
					if (doubleClick())
					{
						gui.editReservationByDoubleClick();
					}
				}
				click = new Date();
				reservId = selectedReservation.getId();	
			}
		}

		repaint();
		clickCount=clickCount+1;
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

	/**
	 * @return The currently selected Reservation
	 */
	public Reservation getSelectedReservation(){
		return selectedReservation;
	}

	/**
	 * @return The currently selected Month
	 */
	public int getSelectedMonth() {
		return selectedMonth.get(Calendar.MONTH);
	}

	/**
	 * @return The currently selected Year
	 */
	public int getSelectedYear() {
		return selectedMonth.get(Calendar.YEAR);
	}

	/**
	 * @return The currently selected Car Type
	 */
	public CarType getSelectedCarType() {
		return selectedCarType;
	}

	/**
	 * Sets the selected month to show
	 * @param i The number of the month (0-11)
	 */
	public void setSelectedMonth(int i) {
		selectedMonth = new GregorianCalendar(
				selectedMonth.get(Calendar.YEAR),
				i,
				1);
		reload();
	}

	/**
	 * Sets the selected year to show
	 * @param i The number of the year
	 */
	public void setSelectedYear(int i) {
		selectedMonth = new GregorianCalendar(
				i, 
				selectedMonth.get(Calendar.MONTH), 
				1);
		reload();
	}

	/**
	 * Sets the selected Car Type to list
	 * @param c The Car Type to list
	 */
	public void setSelectedCarType(CarType c) {
		selectedCarType = c;
		reload();
	}

	/**
	 * Sets the currently selected reservation
	 * @param r The reservation to select
	 */
	public void setSelectedReservation(Reservation r) {
		selectedReservation = r;
		reload();
	}

	// Overriding Jcomponent functions

	public Dimension getPreferredSize() {
		return new Dimension(calcWidth(), calcHeight());
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	/**
	 * Calculates the required width to display everything
	 * @return Width of the calendar
	 */
	private int calcWidth() {
		return CARNAME_WIDTH + selectedMonth.getActualMaximum(selectedMonth.DAY_OF_MONTH) * CELL_WIDTH;
	}

	/**
	 * Calculates the required height to display everything
	 * @return Height of the calendar
	 */
	private int calcHeight() {
		return (calcCars() + 1) * CELL_HEIGHT + 1;
	}

	/**
	 * Calclates the amount of cars in the cars-array, that is of the selected carType
	 * @return The amount of Cars of the selected CarType
	 */
	private int calcCars() {
		int numCars = 0;
		for (Car c : cars) {
			if (c.getType() == selectedCarType) {
				numCars++;
			}
		}
		return numCars;
	}



}
