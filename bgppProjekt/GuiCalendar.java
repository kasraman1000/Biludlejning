package bgppProjekt;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;

/**
 * The calendar graphical module, showing reservations on a monthly basis
 * 
 * It's not finished though, it needs the following:
 * 	A way to change the month and car-type shown
 * 	It needs to somehow hook up with a database and pull those data (month/reservations)
 * 	
 * 
 * @author Kasra Tahmasebi
 *
 */
public class GuiCalendar extends JComponent implements MouseListener {
	// constants for drawing
	private final int calendarWidth = 800;
	private final int calendarHeight = 300;
	private final int cellWidth = 20;
	private final int cellHeight = 20;
	private final int carNameWidth = 100;
	
	// class fields
	private ArrayList<Reservation> reservations;
	private ArrayList<Car> cars;
	private Reservation[][] guiArray;
	
	private CarType selectedCarType;
	private GregorianCalendar selectedMonth;
	private Reservation selectedReservation;
	
	public GuiCalendar() {
		this.addMouseListener(this);
		
		selectedCarType = CarType.SEDAN;
		selectedMonth = new GregorianCalendar(2011, 11, 7);
		
		cars = new ArrayList<Car>();
		reservations = new ArrayList<Reservation>();
		
		fillCars();
		fillReservations();
		
		fillGuiArray();
		
		repaint();
	}
	
	
	/**
	 * filling up the 2d array
	 */
	private void fillGuiArray() {
		guiArray = new Reservation[cars.size()][selectedMonth.getActualMaximum(selectedMonth.DAY_OF_MONTH)];
		
		for (int c = 0; c < cars.size(); c++){
			for (Reservation r : reservations) {
				if (r.getCar() == cars.get(c)) {
					GregorianCalendar date = (GregorianCalendar) r.getStartingDate().clone();
					GregorianCalendar end = (GregorianCalendar) r.getEndDate().clone();
					end.roll(Calendar.DATE, true);
					
					while (date.before(end)) {
						guiArray[c][date.get(Calendar.DATE) - 1] = r;
						date.roll(Calendar.DATE, true);
					}
										
				}
			}
		}
		
		
	}
	
	/**
	 * Filling out the cars array, for testing purposes
	 */
	private void fillCars() {
		cars.add(new Car(CarType.SEDAN, "Sedan1"));
		cars.add(new Car(CarType.SEDAN, "Sedan2"));
		cars.add(new Car(CarType.SEDAN, "Sedan3"));
		cars.add(new Car(CarType.SEDAN, "Sedan4"));
	}
	
	/**
	 * Filling out the reservations array, for testing purposes
	 */
	private void fillReservations() {
		
		reservations.add(new Reservation(2, cars.get(1), new GregorianCalendar(2011,11,05), new GregorianCalendar(2011,11,13),"",""));
		reservations.add(new Reservation(3, cars.get(2), new GregorianCalendar(2011,11,01), new GregorianCalendar(2011,11,14),"",""));
		reservations.add(new Reservation(4, cars.get(0), new GregorianCalendar(2011,11,13), new GregorianCalendar(2011,11,15),"",""));
		reservations.add(new Reservation(1, cars.get(0), new GregorianCalendar(2011,11,07), new GregorianCalendar(2011,11,12),"",""));
		reservations.add(new Reservation(5, cars.get(3), new GregorianCalendar(2011,11,1), new GregorianCalendar(2011,11,3),"",""));
	}
	
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		
		// Drawing border
		g.drawRect(0, 0, calendarWidth, calendarHeight);
		
		// Drawing the name of the month and year
		g.drawString(
				Integer.toString(selectedMonth.get(Calendar.MONTH) + 1) + "/" + Integer.toString(selectedMonth.get(Calendar.YEAR)), 
				0, 
				cellHeight);
		
		// Drawing everything horizontal (names and lines)
		g.drawLine(0, cellHeight, calendarWidth, cellHeight);
		
		int numCars = 0;
		for (Car c : cars) {
			if (c.getType() == selectedCarType) {
				g.drawString(c.getName(), 0, 2*cellHeight + (numCars * cellHeight));
				g.drawLine(0, 2*cellHeight + (numCars * cellHeight), calendarWidth, 2*cellHeight + (numCars * cellHeight));
				numCars++;
			}
		}
		
		// Drawing everything vertical (Numbers and lines)
		for (int i = 0; i < selectedMonth.getActualMaximum(selectedMonth.DAY_OF_MONTH);i++) {
			g.drawLine(carNameWidth + (cellWidth*i), 0, carNameWidth + (cellWidth*i), calendarHeight);
			g.drawString(Integer.toString(i + 1), carNameWidth + (cellWidth*i), cellHeight);
		}
		
		// Filling in reservations
		for (Reservation r : reservations) {
			int carNumber = 1;
			for (Car c : cars) {
				if (c == r.getCar()) {
					if (r == selectedReservation) {
						g.setColor(Color.ORANGE);
					}
					else {
						g.setColor(Color.RED);
					}
					
					g.fillRect(
							carNameWidth + (r.getStartingDate().get(Calendar.DATE) - 1) * cellWidth, 
							carNumber * cellHeight, 
							(1 + r.getEndDate().get(Calendar.DATE) - r.getStartingDate().get(Calendar.DATE)) * cellWidth, 
							cellHeight);
					g.setColor(Color.BLACK);
					g.drawRect(
							carNameWidth + (r.getStartingDate().get(Calendar.DATE) - 1) * cellWidth, 
							carNumber * cellHeight, 
							(1 + r.getEndDate().get(Calendar.DATE) - r.getStartingDate().get(Calendar.DATE)) * cellWidth, 
							cellHeight);
				}
				carNumber++;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("CLICKED");
		
		int a = (e.getX() - carNameWidth) / cellWidth;
		int b = -1 + (e.getY()/cellHeight);
		
		System.out.println(a);
		System.out.println(b);
		
		if (guiArray[b][a] != null) {
			selectedReservation = guiArray[b][a];
			System.out.println(selectedReservation.getId());
		}
		
				
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @return The currently selected Reservation
	 */
	public Reservation getSelectedReservation(){
		return selectedReservation;
	}

}
