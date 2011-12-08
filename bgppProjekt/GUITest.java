package bgppProjekt;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUITest {
	
	
	
	public GUITest() {
		makeFrame();
	}
	
	private void makeFrame() {
		System.out.println("Making a new JFrame");
		
		JFrame testFrame = new JFrame("Testing out GUICalendar...");
		Container contentPane = testFrame.getContentPane();
		
		contentPane.add(new GuiCalendar());
		
		testFrame.setBounds(400, 400, 800, 600); //WHY THE FUCK WON'T IT RESIZE WINDOW?!? D:<
		//testFrame.setResizable(false);
		
		testFrame.pack();
		testFrame.setVisible(true);
				
	}

}
