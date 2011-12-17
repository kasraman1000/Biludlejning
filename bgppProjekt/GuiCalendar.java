package bgppProjekt;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;
import java.util.Date;

/**
 * The calendar graphical module, showing reservations on a monthly basis
 * 
 *
 */
public class GuiCalendar extends JComponent implements MouseListener {
	
	// constants for drawing
	private final int cellWidth = 20;
	private final int cellHeight = 20;
	private final int carNameWidth = 100;

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
		selectedMonth = new GregorianCalendar(2011, 11, 1);

		cars = new ArrayList<Car>();
		reservations = new ArrayList<Reservation>();

	}

	/**
	 * Makes the calendar load everything 
	 */
	public void reload() {

		fillReservations();
		fillGuiArray();	
	
		repaint();
	}
	
	/**
	 * filling up the 2d array
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
	 * Filling out the reservations array
	 */
	private void fillReservations() {
		// Figure out start and end of month
		GregorianCalendar monthStart = new GregorianCalendar(
				selectedMonth.get(Calendar.YEAR), 
				selectedMonth.get(Calendar.MONTH), 
				selectedMonth.getActualMinimum(Calendar.DAY_OF_MONTH));
		monthStart.add(Calendar.DAY_OF_MONTH, -1);

		System.out.println(selectedMonth.get(Calendar.MONTH));

		GregorianCalendar monthEnd = new GregorianCalendar(
				selectedMonth.get(Calendar.YEAR), 
				selectedMonth.get(Calendar.MONTH), 
				selectedMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
		monthStart.add(Calendar.DAY_OF_MONTH, 1);
		
		reservations = Database.grabPeriod(selectedCarType, monthStart, monthEnd);
	}


	public void paint(Graphics g) {
		g.setColor(Color.BLACK);

		// Drawing border
		g.drawRect(0, 0, calcWidth(), calcHeight());

		// Drawing the name of the month and year
		g.drawString(
				Integer.toString(selectedMonth.get(Calendar.MONTH) + 1) + "/" + Integer.toString(selectedMonth.get(Calendar.YEAR)), 
				0, 
				cellHeight);

		// Drawing everything horizontal (names and lines)
		g.drawLine(0, cellHeight, calcWidth(), cellHeight);

		int numCars = 0;
		for (Car c : cars) {
			if (c.getType() == selectedCarType) {
				g.drawString(c.getName(), 0, 2*cellHeight + (numCars * cellHeight));
				g.drawLine(0, 2*cellHeight + (numCars * cellHeight), calcWidth(), 2*cellHeight + (numCars * cellHeight));
				numCars++;
			}
		}

		// Drawing everything vertical (Numbers and lines)
		for (int i = 0; i < selectedMonth.getActualMaximum(selectedMonth.DAY_OF_MONTH);i++) {
			g.drawLine(carNameWidth + (cellWidth*i), 0, carNameWidth + (cellWidth*i), calcHeight());
			g.drawString(Integer.toString(i + 1), carNameWidth + (cellWidth*i), cellHeight);
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
								carNameWidth + (start.get(Calendar.DATE) - 1) * cellWidth, 
								carNumber * cellHeight, 
								(end.get(Calendar.DATE) - start.get(Calendar.DATE) + 1) * cellWidth, 
								cellHeight);
						g.setColor(Color.BLACK);
						g.drawRect(
								carNameWidth + (start.get(Calendar.DATE) - 1) * cellWidth, 
								carNumber * cellHeight, 
								(end.get(Calendar.DATE) - start.get(Calendar.DATE) + 1) * cellWidth, 
								cellHeight);
					}
					carNumber++;
				}
			}
		}
	}
	// function to determine if a click occurred soon enough after the previous one to be a double-click.
	private boolean doubleClick()
	{
		Date newClick = new Date();
		long duration = 1000;
		if (click != null) {
			duration = newClick.getTime() - click.getTime();
		}

		System.out.println("duration: " + duration);
		int reservId2=0;
		if(selectedReservation!=null)
		{
			reservId2 = selectedReservation.getId();
			System.out.println("Reserv2: " + reservId2);
		}

		return (duration<500 && reservId==reservId2);
	}


	public void mouseClicked(MouseEvent e) {

		int a = (e.getX() - carNameWidth) / cellWidth;
		int b = -1 + (e.getY()/cellHeight);

		System.out.println("x-coordinate: " + a);
		System.out.println("y-coordinate: " + b);
		if (a >= 0 && b >= 0) {
			if (guiArray[b][a] != null) {
				selectedReservation = guiArray[b][a];
				System.out.println("ReservationID: " + selectedReservation.getId());
				if (clickCount > 0)
				{
					if (doubleClick())
					{
						System.out.println("It was a doubleclick");
						//CODE/FUNCTIONS TO BE CALLED WHEN DOUBLECLICK
						gui.doubleClicked();
					}
				}
				click = new Date();
				reservId = selectedReservation.getId();	
				System.out.println("reservId : " + reservId);
			}
		}
		System.out.println("CLICKED");

		repaint();
		clickCount=clickCount+1;
	}

	public void mouseEntered(MouseEvent arg0) {
		
	}

	public void mouseExited(MouseEvent arg0) {
		
	}

	public void mousePressed(MouseEvent arg0) {
		
	}

	public void mouseReleased(MouseEvent arg0) {
		
	}

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
		System.out.println("Changing selected month to: " + i);
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
		System.out.println("Changing selected year to: " + i);
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
		System.out.println("Changing selected car type to: " + c);
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
		return carNameWidth + selectedMonth.getActualMaximum(selectedMonth.DAY_OF_MONTH) * cellWidth;
	}

	/**
	 * Calculates the required height to display everything
	 * @return Height of the calendar
	 */
	private int calcHeight() {
		return (calcCars() + 1) * cellHeight;
	}
	
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
