/*
 * Package: nursestation.userinterface
 *
 * File: StationView.java
 *
 * Date: Nov 2, 2011
 * 
 */

package nursestation.userinterface;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import alarm.AlarmStatus;

/**
 * NurseStationView - Nurse station user interface
 * 
 * @author Anthony Barone
 */
@SuppressWarnings("serial")
public class NurseStationView extends JFrame implements AWTEventListener {


	//------------------------------------ Attributes -------------------------------------//


	// ***** Window attributes ***** //

	private static final int FRAME_WIDTH = 814;
	private static final int FRAME_HEIGHT = 610;

	// ***** Menu and Toolbar components ***** //

	private JMenuBar menuBar;
	//private JToolBar toolBar;
	private JMenu fileMenu, helpMenu;

	private JMenuItem exitItem, aboutItem;

	// ***** Panel components ***** //

	private JPanel totalPanelSet, infoPanel, subLeftInfo, statusPanel, subNumPatientsPanel, stationPanel;

	private JLabel stationNameLabel, numPatientsLabel, numPatients;

	private JTextArea stationName;

	// ***** A panel for displaying all the patients this Nurse Station is subscribed to ***** //

	private JPanel patientDisplay;


	//--------------------------------------------------------------------------------------//


	/**
	 * Default constructor
	 */
	public NurseStationView() {

		// Set windows look'n'feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		// Set the main container
		Container container = getContentPane();

		// Instantiate the menu bar and add to interface
		menuBar = createMenuBar();
		this.setJMenuBar(menuBar);

		// TODO: Determine if this is needed, decide on buttons
		// Instantiate the command toolbar and add to interface
		//toolBar = createToolbar();
		//container.add(toolBar, BorderLayout.PAGE_START);

		// set up the main panel and subpanels
		totalPanelSet = new JPanel(new BorderLayout());


		// ********** Info Panel ********** //

		infoPanel = new JPanel(new BorderLayout());
		subLeftInfo = new JPanel(new FlowLayout());
		stationPanel = new JPanel();

		stationNameLabel = new JLabel("Station Name: ");
		stationNameLabel.setForeground(Color.WHITE);
		stationName = new JTextArea();
		stationName.setEditable(false);		
		stationName.setBackground(new Color(102,102,102));
		stationName.setForeground(Color.WHITE);
		stationName.setFont(new Font(UIManager.getDefaults().getFont("Label.font").getFontName(), 
				Font.BOLD, UIManager.getDefaults().getFont("Label.font").getSize()));

		stationPanel.add(stationNameLabel);
		stationPanel.add(stationName);
		stationPanel.setBackground(new Color(102,102,102));
		subLeftInfo.add(stationPanel);
		subLeftInfo.setBackground(new Color(102,102,102));

		infoPanel.add(subLeftInfo, BorderLayout.WEST);


		// ********** Status Bar ********** //

		statusPanel = new JPanel(new BorderLayout());

		subNumPatientsPanel = new JPanel(new FlowLayout());

		numPatientsLabel = new JLabel("Number of Patients:   ");
		numPatients = new JLabel("");
		numPatients.setFont(new Font("Tahoma", Font.BOLD, 12));

		subNumPatientsPanel.add(numPatientsLabel);
		subNumPatientsPanel.add(numPatients);

		statusPanel.setBorder(BorderFactory.createTitledBorder("Status"));
		statusPanel.add(subNumPatientsPanel, BorderLayout.WEST);

		infoPanel.setPreferredSize(new Dimension(infoPanel.getPreferredSize().width, 40));
		infoPanel.setBorder(BorderFactory.createEtchedBorder());
		infoPanel.setBackground(new Color(102,102,102));
		totalPanelSet.add(infoPanel, BorderLayout.NORTH);
		totalPanelSet.add(statusPanel, BorderLayout.SOUTH);


		// *********************************** //


		// add all panels to container
		pack();
		container.add(totalPanelSet);

		// get dimensions for window and set its properties
		Toolkit testkit = Toolkit.getDefaultToolkit();
		Dimension dim = testkit.getScreenSize();
		int loc_height = (dim.height / 2) - (FRAME_HEIGHT / 2) - 25 /* for taskbar */;
		int loc_width = (dim.width / 2) - (FRAME_WIDTH / 2);
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setLocation(loc_width, loc_height);
		setTitle("Nurse Station View Console v1.0");
		setVisible(true);
		//TODO figure out implications of closing window (dealing with unsubscribing)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	}


	/**
	 * createMenuBar - Builds a menu bar with all needed menu items
	 * 
	 * @return     A menu bar with actions built in
	 */
	public JMenuBar createMenuBar() {

		// create the menu bar
		menuBar = new JMenuBar();


		// ********** File Menu ********** //

		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		// 1. Close command
		exitItem = menuItemGenerator("Exit", "img/close-icon.png", null, 
				"exit", new ExitListener());
		fileMenu.add(exitItem);

		// Add File menu to bar
		menuBar.add(fileMenu);


		// ********** Help Menu ********** //

		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		// 1. About the program
		aboutItem = menuItemGenerator("About Nurse Station View Console", "img/about-icon.png", 
				null, "about", new AboutListener());
		helpMenu.add(aboutItem);

		// Add Help to bar
		menuBar.add(helpMenu);


		// ******************************* //

		// return the resulting menu bar
		return menuBar;
	}

	/**
	 * Inner class for listener on About button in toolbar
	 */
	private class AboutListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "Nurse Station View Console\n" +
					"Version 1.0\n\n" +
					"Anthony Barone, Chris Bentivenga, Ian Hunt, Jason Smith\n\n" +
					"Copyright 2011 | Group 1\n" +
			"All images are entitled to their respective owners. All rights reserved.");
		}
	}

	/**
	 * Inner class for listener on Exit button in toolbar
	 */
	private class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//TODO figure this out
			System.exit(0);
		}
	}


	/**
	 * createToolbar - Builds a toolbar with command buttons
	 * 
	 * @return     A toolbar with command buttons built in
	 */
	public JToolBar createToolbar() {

		// create the toolbar, define properties
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		toolBar.setBorder(loweredetched);
		toolBar.setPreferredSize(new Dimension(450, 37));

		// create command buttons

		//TODO template below, figure out what buttons we need, if any, for toolbar
		//JButton openButton = buttonGenerator("icons/open-icon.png", "Open (Ctrl+O)", "open", new OpenCommand(main, null));

		// ***** //


		// add components to toolbar
		//toolBar.add(openButton);
		//toolBar.addSeparator();

		// return the resulting toolbar
		return toolBar;
	}


	/**
	 *  buttonGenerator - Helper method to build JButtons
	 * 
	 * @param     imgLocation      path of button image
	 * @param     tooltipText      text to display when hovered over
	 * @param     actionCommand    action command string
	 * @param     listener         specified action listener
	 * 
	 * @return     A button with the requested attributes
	 */
	public JButton buttonGenerator(String imgLocation, 
			String tooltipText,
			String actionCommand,
			ActionListener listener) {

		// create the button and set attributes
		JButton newButton = new JButton();
		if (imgLocation != null) {
			newButton.setIcon(new ImageIcon(imgLocation));
		}
		newButton.setPreferredSize(new Dimension(32, 32));
		newButton.setToolTipText(tooltipText);
		newButton.setActionCommand(actionCommand);
		newButton.addActionListener(listener);
		newButton.setFocusable(false);

		// return the resulting button
		return newButton;
	}


	/**
	 * menuItemGenerator - Helper method to build JMenuItems
	 * 
	 * @param     name            name of menu item
	 * @param     imgLocation     path of menu item image
	 * @param     keystroke       accelerator keystroke
	 * @param     actionCommand   action command string
	 * @param     actionListener  specified action listener
	 * 
	 * @return     A menu item with the requested attributes
	 */
	public JMenuItem menuItemGenerator(String name,
			String imgLocation, 
			KeyStroke keystroke,
			String actionCommand,
			ActionListener listener) {

		// create the button and set attributes
		JMenuItem newMenuItem = new JMenuItem(name, new ImageIcon(imgLocation));
		newMenuItem.setAccelerator(keystroke);
		newMenuItem.setActionCommand(actionCommand);
		newMenuItem.addActionListener(listener);

		// return the resulting button
		return newMenuItem;
	}

	/**
	 * Add a patient to the Nurse station
	 * @param name Patient name
	 */
	public void addPatient(String patientName) {
		//TODO Model objects/controllers
		
        PatientPanel newPatient = new PatientPanel(patientName, AlarmStatus.INACTIVE.name());
	    patientDisplay.add(newPatient);
	    patientDisplay.validate();
	}
	
	/**
	 * Set the patient display panel for this view
	 * 
	 * @param display - the patient display to set to this interface
	 */
	public void setPatientDisplay(JPanel display) {
		this.patientDisplay = display;
		totalPanelSet.add(patientDisplay, BorderLayout.CENTER);
		patientDisplay.setPreferredSize(new Dimension(patientDisplay.getPreferredSize().width, 20));
		totalPanelSet.requestFocus();
	}

	/**
	 * Set the name of the Nurse Station
	 */
	public void setStationNameBox(String name) {
		stationName.setText(name);
		stationName.setCaretPosition(0);
		stationName.setPreferredSize(stationName.getPreferredSize());
	}

	/**
	 * Display the number of patients currently viewing
	 * 
	 * @param num - The number of patients currently subscribed to
	 */
	public void setPatientNumber(int num) {
		numPatients.setText(num+"");
	}

	/**
	 *  Invoked when an event is dispatched in the AWT.
	 *
	 *  @param event - the dispatched event
	 */
	public void eventDispatched(AWTEvent event) {
		if ( event instanceof KeyEvent) {
			KeyEvent kEvent = (KeyEvent)event; 

			// relay key event to all registered key listeners	         
			for (KeyListener keylist : this.getKeyListeners()) {
				if ( kEvent.getID() == KeyEvent.KEY_PRESSED) { 
					keylist.keyPressed(kEvent); 
				} 
			}
		}
	}
	
} // NurseStationView
