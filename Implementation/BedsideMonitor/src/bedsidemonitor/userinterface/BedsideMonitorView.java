/*
 * Package: bedsidemonitor.userinterface
 *
 * File: BedsideMonitorView.java
 *
 * Date: Nov 3, 2011
 * 
 */

package bedsidemonitor.userinterface;

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
import javax.swing.BoxLayout;
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
import bedsidemonitor.BedsideMonitor;
import bedsidemonitor.vitalsigncollection.VitalSignController;

/**
 * BedsideMonitorView - Bedside monitor user interface
 * 
 * @author Anthony Barone
 */
@SuppressWarnings("serial")
public class BedsideMonitorView extends JFrame implements AWTEventListener {


	//------------------------------------ Attributes -------------------------------------//

    private BedsideMonitor bedsideMonitor;

	// ***** Window attributes ***** //

	private static final int FRAME_WIDTH = 714;
	private static final int FRAME_HEIGHT = 610;

	// ***** Menu and Toolbar components ***** //

	private JMenuBar menuBar;
	//private JToolBar toolBar;
	private JMenu fileMenu, helpMenu;

	private JMenuItem exitItem, aboutItem;
	private JButton callButton, alarmOffButton;

	// ***** Panel components ***** //

	private JPanel totalPanelSet, infoPanel, subLeftInfo, statusPanel, subCallStatusPanel, 
		monitorPanel;

	private JLabel monitorNameLabel, callStatus, alarmStatus;

	private JTextArea monitorName;
	
	private static final String CALL_LABEL_OFF = "OFF";
	private static final String CALL_LABEL_ON = "ON";
	
	private static final String CALL_NURSE = "Call Nurse";
	private static final String CALL_OFF = "Turn Off";
	private static final String SPACER_TEXT = " ";

	// ***** A panel for displaying all the patients' vital stats and respective alarm status ***** //

	private JPanel vitalStatDisplay;

	//--------------------------------------------------------------------------------------//


	/**
	 * Default constructor
	 */
	public BedsideMonitorView(BedsideMonitor bedsideMonitor) {
	    
	    this.bedsideMonitor = bedsideMonitor;
	    
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
		monitorPanel = new JPanel();

		monitorNameLabel = new JLabel("Monitor Name: ");
		monitorNameLabel.setForeground(Color.WHITE);
		monitorName = new JTextArea();
		monitorName.setEditable(false);
		monitorName.setBackground(new Color(102,102,102));
		monitorName.setForeground(Color.WHITE);
		monitorName.setFont(new Font(UIManager.getDefaults().getFont("Label.font").getFontName(),
				Font.BOLD, UIManager.getDefaults().getFont("Label.font").getSize()));

		monitorPanel.add(monitorNameLabel);
		monitorPanel.add(monitorName);
		monitorPanel.setBackground(new Color(102,102,102));
		subLeftInfo.add(monitorPanel);
		subLeftInfo.setBackground(new Color(102,102,102));

		infoPanel.add(subLeftInfo, BorderLayout.WEST);


		// ********** Status Bar ********** //

		statusPanel = new JPanel(new BorderLayout());
		
		// Panel dealing with Call
		subCallStatusPanel = new JPanel(new BoxLayout(statusPanel, BoxLayout.LINE_AXIS));
		subCallStatusPanel.setLayout(new BoxLayout(subCallStatusPanel, BoxLayout.LINE_AXIS));
		subCallStatusPanel.add(new JLabel("Call button: "));
		callStatus = new JLabel(CALL_LABEL_OFF + SPACER_TEXT);
		callStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
		callStatus.setForeground(Color.GRAY);
		subCallStatusPanel.add(callStatus);
		
		callButton = new JButton(CALL_NURSE);
		callButton.setEnabled(true);
		callButton.addActionListener(new TurnOffCallListener());
		subCallStatusPanel.add(callButton);
		
		// Add both sub panels
		statusPanel.add(subCallStatusPanel, BorderLayout.WEST);

		infoPanel.setPreferredSize(new Dimension(infoPanel.getPreferredSize().width, 40));
		infoPanel.setBorder(BorderFactory.createEtchedBorder());
		infoPanel.setBackground(new Color(102,102,102));
		statusPanel.setBorder(BorderFactory.createTitledBorder("Status"));
		totalPanelSet.add(infoPanel, BorderLayout.NORTH);
		totalPanelSet.add(statusPanel, BorderLayout.SOUTH);


		// *********************************** //


		// Add all panels to container
		pack();
		container.add(totalPanelSet);

		// Get dimensions for window and set its properties
		Toolkit testkit = Toolkit.getDefaultToolkit();
		Dimension dim = testkit.getScreenSize();
		int loc_height = (dim.height / 2) - (FRAME_HEIGHT / 2) - 25 /* for taskbar */;
		int loc_width = (dim.width / 2) - (FRAME_WIDTH / 2);
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setLocation(loc_width, loc_height);
		setTitle("Bedside Montior View Console v1.0");
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
		aboutItem = menuItemGenerator("About Bedside Monitor View Console", "img/about-icon.png", 
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
			JOptionPane.showMessageDialog(null, "Bedside Monitor View Console\n" +
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
	 * Set the vital stat display panel for this view
	 * 
	 * @param display - the vital stat display to set to this interface
	 */
	public void setVitalStatDisplay(JPanel display) {
		vitalStatDisplay = display;
		totalPanelSet.add(vitalStatDisplay, BorderLayout.CENTER);
		vitalStatDisplay.setPreferredSize(new Dimension(vitalStatDisplay.getPreferredSize().width, 20));
		totalPanelSet.requestFocus();
	}

	/**
	 * Set the name of the Bedside Monitor
	 */
	public void setMonitorName(String name) {
		monitorName.setText(name);
		monitorName.setCaretPosition(0);
		monitorName.setPreferredSize(monitorName.getPreferredSize());
	}

	/**
	 * Alarm triggered, update display
	 */
	public void alarmTriggered() {
		alarmStatus.setText(AlarmStatus.ACTIVE.name() + SPACER_TEXT);
		alarmStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
		alarmStatus.setForeground(Color.RED);
		alarmOffButton.setEnabled(true);
	}

	/**
	 * Alarm acknowledged, update display
	 */
	public void alarmAcknowledged() {
		alarmStatus.setText(AlarmStatus.ACKNOWLEDGED.name() + SPACER_TEXT);
		alarmStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
		alarmStatus.setForeground(Color.BLUE);
		alarmOffButton.setEnabled(true);
	}

	/**
	 * Call button triggered, update display
	 */
	public void callOff() {
		callStatus.setText(CALL_LABEL_ON + SPACER_TEXT);
		callStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
		callStatus.setForeground(Color.RED);
		callButton.setEnabled(true);
	}

	/**
	 * Call button triggered, update display
	 */
	public void callTriggered() {
		callStatus.setText(CALL_LABEL_ON + SPACER_TEXT);
		callStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
		callStatus.setForeground(Color.RED);
	}

	/**
	 * Set the name of the patient
	 * 
	 * @param name - The patient name
	 */
	public void setPatientName(String name) {
		//TODO
	}

	/**
	 * Set the ID# of the patient
	 * 
	 * @param id - The patient ID#
	 */
	public void setPatientID(int id) {
		//TODO
	}

	/**
	 * Inner class for listener on Acknowledge Alarm button
	 */
	private class TurnOffCallListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String action = ((JButton)e.getSource()).getText();
			if (action.equals(CALL_OFF)) {
				// Turn off call button
				callStatus.setText(CALL_LABEL_OFF + SPACER_TEXT);
				callStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
				callStatus.setForeground(Color.GRAY);
				callButton.setText(CALL_NURSE);
				bedsideMonitor.setCallButton(false);
			} else {
				// Calling all nurse stations
				callStatus.setText(CALL_LABEL_ON + SPACER_TEXT);
				callStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
				callStatus.setForeground(Color.RED);
				callButton.setText(CALL_OFF);
				bedsideMonitor.setCallButton(true);
			}
			//TODO logging, updating external sources, etc
		}
	}

	/**
	 * Add a vital sign row to the monitor
	 * @param name Vital sign name
	 */
	public void addVitalSign(String vitalName) {
	    VitalSignController vitalSign = bedsideMonitor.getVitalSign(vitalName);
		
	    if(vitalSign != null) {
	        VitalStatRow newVital = new VitalStatRow(vitalSign);
    	    vitalStatDisplay.add(newVital);
    	    vitalStatDisplay.validate();
    	    vitalStatDisplay.repaint();
	    }
	}
	
	/**
	 * Update a vital sign value
	 * @param name   Vital sign name
	 * @param value  Vital sign value
	 */
	public void updateVitalSign(String vitalName, int value) {
		//vitalStatDisplay.updateVitalSign(vitalName, value);
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
