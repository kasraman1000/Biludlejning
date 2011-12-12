package bgppProjekt;

import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ActionBox extends JPanel {
	
	// The layoutmanager for this panel
	CardLayout cardLayout;
	// The two states the Panel can be in
	JPanel inspectBox;
	JPanel searchBox;
	
	public ActionBox() {
		makeFrame();
	}
	
	private void makeFrame() {
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		
		inspectBox = new JPanel();
		searchBox = new JPanel();
		cardLayout.addLayoutComponent(inspectBox, "Inspect");
		cardLayout.addLayoutComponent(searchBox, "Search");
		
		inspectBox.add(new JLabel("INSPECT WINDOW GOES HERE!"));
		searchBox.add(new JLabel("SEARCH WINDOW GOES HERE!"));
		
		add(inspectBox, "INSPECT");
		add(inspectBox, "SEARCH");
		
		
		cardLayout.show(this, "SEARCH"); //FUCKKKN DOESN*T WORK!?!?!?!??!?
		
		
	}

}
