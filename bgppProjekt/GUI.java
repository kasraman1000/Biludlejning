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
		
		contentPane.setLayout(new FlowLayout());
		
		// Adding dropdowns above the calendar
		String[] months = {
				"January", "Febuary", "March", "April", "May",
				"June", "July", "August", "Sebtemper", "October",
				"November", "December"
		};
		JComboBox monthList = new JComboBox(months);
		contentPane.add(monthList);
		
		// Adding the Calendar
		//GuiCalendar guiCalendar = new GuiCalendar();
		//contentPane.add(guiCalendar);
		
		frame.pack();
		frame.setVisible(true);
		
		
	}

}
