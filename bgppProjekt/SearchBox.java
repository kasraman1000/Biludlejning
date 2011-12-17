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

public class SearchBox extends JPanel
{
	JList<Reservation> list;
	DefaultListModel<Reservation> listModel;
	JPanel searchPanel;
	
	JTextField searchField;
	JButton searchButton;
	
	public SearchBox() 
	{
		makeFrame();
	}

	private void makeFrame() 
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
						System.out.println(s + " was found in customer name: " + r.getCustomerName());
						listModel.addElement(r);
					}
					else if (r.getCustomerPhone().toLowerCase().contains(s)) {
						System.out.println(s + " was found in customer phone: " + r.getCustomerPhone());
						listModel.addElement(r);
					}
				}
			}
		});

	}
}
