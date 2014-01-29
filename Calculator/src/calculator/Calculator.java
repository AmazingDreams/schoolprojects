package calculator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * This class manages the entire calculator
 *
 * @package   Calculator
 * @author    Dennis Ruhe
 * @copyright (c) 2014 Dennis Ruhe
 */
public class Calculator extends JApplet {

	/**
	 * @var  long  Serial version UID
	 */
	public static final long serialVersionUID = 1;

	/**
	 * @var  int  Default button height
	 */
	public static final int BUTTON_HEIGHT = 45;

	/**
	 * @var  String  The text to put in the textfield at startup
	 */
	public static final String DEFAULT_TEXT = "0.";

	/**
	 * @var  boolean  A flag whether the text in the calcField is the result of a calculation
	 */
	private boolean fieldIsResult;

	/**
	 * @var  Container  The container of our app
	 */
	private Container container;

	/**
	 * @var  JPanel  The 'root' panel
	 */
	private JPanel panel;

	/**
	 * @var  JTextField  The field in which the calculations are created
	 */
	private JTextField calcField;

	/**
	 * @var  double  Memory
	 */
	private double memory = 0;

	/**
	 * This init() is called when the app is an applet
	 */
	public void init() {
		this.container = getContentPane();

		createGUI();
	}

	/**
	 * This go() is called when the app is started from the jar file
	 */
	public void go() {
		// Create a frame and set the container
		JFrame frame = new JFrame();
		this.container = frame.getContentPane();

		// Build the GUI
		createGUI();

		frame.setSize(370, 350);
		frame.setVisible(true);
	}

	/**
	 * Create the GUI
	 */
	private void createGUI() {
		panel = new JPanel(new BorderLayout());

		calcField = new JTextField(DEFAULT_TEXT);
		panel.add(calcField, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel(new BorderLayout(10,0));

		// Build the different panels
		buttonPanel.add(buildNorthPanel(), BorderLayout.NORTH);
		buttonPanel.add(buildWestPanel(),  BorderLayout.WEST);
		buttonPanel.add(buildNumPanel(),   BorderLayout.CENTER);

		panel.add(buttonPanel, BorderLayout.CENTER);
		container.add(panel);
	}

	/**
	 * Build the northPanel
	 *
	 * @return  JPanel  The created panel
	 */
	private JPanel buildNorthPanel() {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		// We have a placeholder on the top right
		JPanel placeHolder = new JPanel();
		placeHolder.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_HEIGHT));
		placeHolder.setBorder(BorderFactory.createLoweredBevelBorder());
		p.add(placeHolder);

		createNorthButton("Backspace", p).addActionListener(new BackspaceActionListener());
		createNorthButton("CE", p).addActionListener(new CEActionListener());
		createNorthButton("C", p).addActionListener(new CActionListener());

		return p;
	}

	/**
	 * Build the westPanel
	 *
	 * @return  JPanel  The created panel
	 */
	private JPanel buildWestPanel() {
		JPanel p = new JPanel(new GridLayout(4, 1, 5, 5));
		p.setBorder(new EmptyBorder(0, 3, 3, 0));

		createWestButton("MC", p);
		createWestButton("MR", p);
		createWestButton("MS", p);
		createWestButton("M+", p);

		return p;
	}

	/**
	 * Build the numPanel
	 *
	 * @return  JPanel  The created panel
	 */
	private JPanel buildNumPanel() {
		JPanel p = new JPanel(new GridLayout(4, 5, 5, 5));
		p.setBorder(new EmptyBorder(0, 0, 4, 4));

		createNumButton("7", p);
		createNumButton("8", p);
		createNumButton("9", p);
		createNumButton("/", p);
		createNumButton("sqrt", p);

		createNumButton("4", p);
		createNumButton("5", p);
		createNumButton("6", p);
		createNumButton("*", p);
		createNumButton("%", p);

		createNumButton("1", p);
		createNumButton("2", p);
		createNumButton("3", p);
		createNumButton("-", p);
		createNumButton("1/x", p);

		createNumButton("0", p);
		createNumButton("+/-", p);
		createNumButton(",", p);
		createNumButton("+", p);
		createNumButton("=", p);

		return p;
	}

	/**
	 * Calculate the result using the text currently in the field
	 */
	public double calcResult(String text) {
		// We use the build-in JavaScript engine to calculate the result
		// this saves us a lot of weird conversions and complex calculations
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine engine = sem.getEngineByName("JavaScript");

		// Do some pre processing to allow javascript to do something with it
		text = text.replace("sqrt(", "Math.sqrt(");

		try {
			return (double) engine.eval(text);
		} catch (ScriptException e) {
			JOptionPane.showMessageDialog(null, "Er was een probleem met het evalueren van uw input", "foutmelding", JOptionPane.ERROR_MESSAGE);
		}

		return 0.0;
	}

	/**
	 * This will run when the application is run from the jar file
	 *
	 * @param   String[]  Arguments
	 */
	public static void main(String[] args) {
		Calculator app = new Calculator();
		app.go();
	}

	/**
	 * Create a button
	 *
	 * @param   String   Contents
	 * @param   JPanel   The panel to add
	 * @return  JButton  The created button
	 */
	private JButton createButton(String contents, JPanel p) {
		JButton b = new JButton(contents);
		b.setMargin(new Insets(0,0,0,0));
		p.add(b);

		return b;
	}

	/**
	 * Create a button for the NumPanel
	 *
	 * @param   String   Contents
	 * @param   JPanel   The panel to add
	 * @return  JButton  The created button
	 */
	private JButton createNumButton(String contents, JPanel p) {
		JButton b = createButton(contents, p);
		b.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_HEIGHT));
		b.addActionListener(new NumButtonActionListener());

		return b;
	}

	/**
	 * Create a button for the WestPanel
	 *
	 * @param   String   Contents
	 * @param   JPanel   The panel to add
	 * @return  JButton  The created button
	 */
	private JButton createWestButton(String contents, JPanel p) {
		JButton b = createButton(contents, p);
		b.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_HEIGHT));
		b.addActionListener(new MemoryActionListener());

		return b;
	}

	/**
	 * Create a button for the NorthPanel
	 *
	 * @param   String   Contents
	 * @param   JPanel   The panel to add
	 * @return  JButton  The created button
	 */
	private JButton createNorthButton(String contents, JPanel p) {
		JButton b = createButton(contents, p);
		b.setPreferredSize(new Dimension(100, BUTTON_HEIGHT));

		return b;
	}

	/**
	 * Check if String b is a number
	 *
	 * @param   String   The string to check
	 * @return  boolean  String is a number or not
	 */
	public boolean isNumber(String b) {
		try {
			Double.parseDouble(b);
		} catch(NumberFormatException ex) {
			return false;
		}

		return true;
	}

	/**
	 * This class handles the buttons in the 'numPanel'
	 */
	class NumButtonActionListener implements ActionListener {
		/**
		 * Responds to a click on the button
		 *
		 * @param  ActionEvent  The event object
		 */
		public void actionPerformed(ActionEvent ev) {
			JButton source    = (JButton) ev.getSource();
			String sourceText = source.getText();
			String origText   = calcField.getText();

			String textToAdd = "";

			// If the text equals the default text, clear it
			if(origText.equals(DEFAULT_TEXT)) {
				calcField.setText("");
			}

			// If you just calculated something and wish to use the result
			// we don't want to clear the text for you, the text should only
			// be cleared when you enter a new number
			if (fieldIsResult && isNumber(sourceText)) {
				calcField.setText("");
				fieldIsResult = false;
			} else {
				fieldIsResult = false;
			}

			// Some buttons have special uses
			if(sourceText.equals("=")) { // Calculate the result
				calcField.setText(""+calcResult(calcField.getText()));
				fieldIsResult = true;
			} else if(sourceText.equals("sqrt")) { // Square root needs a '('
				textToAdd = "sqrt(";
			} else if(sourceText.equals("+/-") && origText.length() > 0) { // Toggle negative/non-negative number
				char firstChar = origText.charAt(0);
				if(firstChar == '-') {
					calcField.setText(origText.substring(1, origText.length()));
				} else {
					calcField.setText("-"+origText);
				}
			} else { // Just append the button text
				textToAdd = sourceText;
			}

			if( ! textToAdd.equals("")) { // If there is something that needs to be appended
				calcField.setText(calcField.getText() + textToAdd);
			}
		}
	}

	/**
	 * This class handles the backspace button
	 */
	class BackspaceActionListener implements ActionListener {
		/**
		 * Responds to a click on the button
		 *
		 * @param  ActionEvent  The event object
		 */
		public void actionPerformed(ActionEvent ev) {
			String newText = calcField.getText();

			// If there are characters
			if(newText.length() > 0) {
				// Remove the last one
				newText = newText.substring(0, newText.length() - 1);
			}

			calcField.setText(newText);
		}
	}

	/**
	 * This class handles the C button
	 */
	class CActionListener implements ActionListener {
		/**
		 * Responds to a click on the button
		 *
		 * @param  ActionEvent  The event object
		 */
		public void actionPerformed(ActionEvent ev) {
			// C will simply clear the field
			calcField.setText("");
		}
	}

	/**
	 * This class handles the CE button
	 */
	class CEActionListener implements ActionListener {
		/**
		 * Responds to a click on the button
		 *
		 * @param  ActionEvent  The event object
		 */
		public void actionPerformed(ActionEvent ev) {
			// C will simply clear the field
			String newText = calcField.getText().replaceAll("[0-9]+$", "");
			calcField.setText(newText);
		}
	}

	/**
	 * This class handles all the memory actions
	 */
	class MemoryActionListener implements ActionListener {
		/**
		 * Respons to a click on the button
		 *
		 * @param  ActionEvent  The event object
		 */
		public void actionPerformed(ActionEvent ev) {
			JButton source    = (JButton) ev.getSource();
			String sourceText = source.getText();

			if(sourceText.equals("MC")) {
				memory = 0.0; // Clear memory
			} else if (sourceText.equals("MR")) {
				calcField.setText(""+memory);
				fieldIsResult = true;
			} else if (sourceText.equals("MS") && fieldIsResult) {
				memory = Double.parseDouble(calcField.getText());
			} else if (sourceText.equals("M+") && fieldIsResult) {
				memory = calcResult(memory+"+"+calcField.getText());
			}
		}
	}
}
