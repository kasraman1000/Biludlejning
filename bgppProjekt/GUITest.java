package bgppProjekt;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUITest {
	
	
	
	public GUITest() {
		makeFrame();
	}
	
	private void makeFrame() {
		
		JFrame testFrame = new JFrame("Testing out GUICalendar...");
		Container contentPane = testFrame.getContentPane();
		
		contentPane.add(new GuiCalendar(null));
		Dimension d = new Dimension(800,600);
		testFrame.setSize(d);
		//testFrame.setBounds(400, 400, 800, 600); //WHY THE FUCK WON'T IT RESIZE WINDOW?!? D:<
		//testFrame.setResizable(false);
		
		testFrame.pack();
		testFrame.setVisible(true);
				
	}

}
