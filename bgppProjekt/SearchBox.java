package bgppProjekt;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * One of the ActionBox Panels, this one handles creating the interface for searching through all reservations.
 */

public class SearchBox extends JPanel
{
	private JList<Reservation> list;
	private DefaultListModel<Reservation> listModel;
	private JPanel searchPanel;
	
	private JTextField searchField;
	private JButton searchButton;
	
	public SearchBox() 
	{
		listModel = new DefaultListModel<Reservation>();
		list = new JList<Reservation>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		searchPanel = new JPanel();
		
		searchField = new JTextField(20);
		searchButton = new JButton("Search");
					
		setLayout(new BorderLayout());
		
		add(list, BorderLayout.CENTER);
		add(searchPanel, BorderLayout.SOUTH);
		
		searchPanel.add(searchField);
		searchPanel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// Searching through all reservations for input
				String s = searchField.getText();
				s.toLowerCase();
				ArrayList<Reservation> reservations = Database.initReservs();
				
				listModel.clear();
				
				for (Reservation r : reservations) {
					if (r.getCustomerName().toLowerCase().contains(s)) {
						listModel.addElement(r);
					}
					else if (r.getCustomerPhone().toLowerCase().contains(s)) {
						listModel.addElement(r);
					}
				}
			}
		});
	}

	/**
	 * Returns the list componenent, so you can pull out the selected entry
	 * or attach listselectionListeners
	 * @return The JList component
	 */
	public JList<Reservation> getList() 
	{
		return list;
	}
}
