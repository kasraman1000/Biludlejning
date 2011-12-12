package bgppProjekt;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI {
	
	public GUI() {
		makeFrame();
	}
	
	private void makeFrame() {
		System.out.println("Making the primary JFrame");
		
		JFrame frame = new JFrame("Biludlejning");
		Container contentPane = frame.getContentPane();
		
		contentPane.setLayout(new BorderLayout());
		
		// Adding dropdowns above the calendar
		JPanel dropdowns = new JPanel();
		dropdowns.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		contentPane.add(dropdowns, BorderLayout.NORTH);
		
		// Dropdown for Car types
		dropdowns.add(new JLabel("Car type:"));
		
		JComboBox<CarType> carTypeList = new JComboBox<CarType>(CarType.values());
		dropdowns.add(carTypeList);
		
		// Dropdown for months
		dropdowns.add(new JLabel("Month:"));
		
		String[] months = {
				"January", "Febuary", "March", "April", "May",
				"June", "July", "August", "Sebtemper", "October",
				"November", "December"
		};
		JComboBox<String> monthList = new JComboBox<String>(months);
		dropdowns.add(monthList);
		
		// Adding the Calendar
		GuiCalendar guiCalendar = new GuiCalendar();
		contentPane.add(guiCalendar, BorderLayout.CENTER);
		
		frame.pack();
		frame.setVisible(true);
		
		
	}

}
