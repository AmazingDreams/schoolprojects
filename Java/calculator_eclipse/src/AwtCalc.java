package calc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

/**
 * AwtCalc.java
 * A simple calculator that uses AWT component's.
 *
 * @version       1.1
 */
public class AwtCalc extends JPanel
{
	public static final long serialVersionUID = 1;

	/**
	 * Labels for the number panel of the calculator
	 */
	private String[] numPanelText = {
		"1",            "2", "3",
		"4",            "5", "6",
		"7",            "8", "9",
		Operator.CLEAR, "0", Operator.DOT
	};

	/**
	 * Labels for the operator panel of the calculator
	 */
	private String[] operPanelText = {
		Operator.ADD,      Operator.POW,    Operator.COS,
		Operator.SUBTRACT, Operator.SQRT,   Operator.SIN,
		Operator.MULTIPLY, Operator.LOG,    Operator.TAN,
		Operator.DIVIDE,   Operator.NEGATE, Operator.EQUALS,
	};

	/**
	 * Labels for the memory panel of the calculator
	 */
	private String[] memButtonPanelText = {
		Memory.CLEAR,
		Memory.READ,
		Memory.STORE,
		Memory.ADD,
		Memory.SUBTRACT,
	};

	/**
	 * Used to hold the number buttons
	 */
	private JPanel numButtonPanel;

	/**
	 * Used to hold the operator buttons
	 */
	private JPanel operButtonPanel;

	/**
	 * Used to hold the memory buttons
	 */
	private JPanel memButtonPanel;

	/**
	 * Used for the calculator's display
	 */
	private JPanel displayPanel;

	/**
	 * Action listener for the buttons
	 */
	private ButtonHandler handler;

	/**
	 * Displays the output
	 */
	private CalcDisplay display;

	/**
	 * The font on the buttons
	 */
	private Font buttonfont;

	/**
	 * Initializes the AwtCalc class
	 */
	public AwtCalc()
	{
		buttonfont = new Font("Courier", Font.PLAIN, 13);

		setLayout(new BorderLayout());
		setBackground(new Color(212, 208, 200));

		JPanel mainPanel = new JPanel();

		numButtonPanel  = new JPanel(new GridLayout(4, 3, 1, 1));
		operButtonPanel = new JPanel(new GridLayout(4, 2, 1, 1));
		memButtonPanel  = new JPanel(new GridLayout(4, 1, 1 ,1));
		displayPanel    = new JPanel();
		display         = new CalcDisplay(392,26);
		handler         = new ButtonHandler(display);

		displayPanel.add(display);

		mainPanel.add(createMemButtonPanel());
		mainPanel.add(createNumberPanel());
		mainPanel.add(createOperPanel());

		add(displayPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
	}

	/**
	 * Constructs and returns the calculators number panel
	 *
	 * @return  Panel  The number panel
	 */
	private JPanel createNumberPanel() {
		if (display != null) {
			JButton btn = null;

			for(int i = 0; i < numPanelText.length; i++) {
				btn = new JButton(numPanelText[i]);
				btn.addActionListener(handler);
				btn.setFont(buttonfont);
				numButtonPanel.add(btn);
			}
		}

		return numButtonPanel;
	}

	/**
	 * Constructs and returns the calculators operator panel
	 *
	 * @return  Panel  The operator panel
	 */
	private JPanel createOperPanel() {
		JButton btn = null;

		for(int i = 0; i < operPanelText.length; i++) {
			btn = new JButton(operPanelText[i]);
			btn.setFont(buttonfont);
			btn.addActionListener(handler);
			operButtonPanel.add(btn);
		}

		return operButtonPanel;
	}

	/**
	 * Constructs and returns the calculators memory panel
	 *
	 * @return  Panel  The memory panel
	 */
	private JPanel createMemButtonPanel() {
		JButton btn = null;

		for(int i = 0; i < memButtonPanelText.length; i++) {
			btn = new JButton(memButtonPanelText[i]);
			btn.setFont(buttonfont);
			btn.addActionListener(handler);
			memButtonPanel.add(btn);
		}

		return memButtonPanel;
	}

}
