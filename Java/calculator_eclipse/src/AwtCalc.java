package calc;

import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;

/**
 * @file          AwtCalc.java
 * @version       1.1
 * @description   A simple calculator that uses AWT component's.
 */
public class AwtCalc extends Panel
{
	/**
	 * @var  String[]  Labels for the number panel of the calculator
	 */
	private String[] numPanelText = {
		" 1 ",          " 2 ", " 3 ",
		" 4 ",          " 5 ", " 6 ",
		" 7 ",          " 8 ", " 9 ",
		Operator.CLEAR, " 0 ", Operator.DOT
	};

	/**
	 * @var  String[]  Labels for the operator panel of the calculator
	 */
	private String[] operPanelText = {
		Operator.ADD,      Operator.POW,    Operator.COS,
		Operator.SUBTRACT, Operator.SQRT,   Operator.SIN,
		Operator.MULTIPLY, Operator.LOG,    Operator.TAN,
		Operator.DIVIDE,   Operator.NEGATE, Operator.EQUALS,
	};

	/**
	 * @var  String[]  Labels for the memory panel of the calculator
	 */
	private String[] memButtonPanelText = {
		Memory.CLEAR,
		Memory.READ,
		Memory.STORE,
		Memory.ADD,
		Memory.SUBTRACT,
	};

	/**
	 * @var  Panel  Used to hold the number buttons
	 */
	private Panel numButtonPanel;

	/**
	 * @var  Panel  Used to hold the operator buttons
	 */
	private Panel operButtonPanel;

	/**
	 * @var  Panel  Used to hold the memory buttons
	 */
	private Panel memButtonPanel;

	/**
	 * @var  Panel3D  Used for the calculator's display
	 */
	private Panel3D displayPanel;

	/**
	 * @var  ButtonHandler  Action listener for the buttons
	 */
	private ButtonHandler handler;

	/**
	 * @var  CalcDisplay  Displays the output
	 */
	private CalcDisplay display;

	/**
	 * @var  Font  The font on the buttons
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

		Panel3D mainPanel = new Panel3D(Border3D.EXCLUDE_TOP_BORDER);

		numButtonPanel  = new Panel(new GridLayout(4, 3, 1, 1));
		operButtonPanel = new Panel(new GridLayout(4, 2, 1, 1));
		memButtonPanel  = new Panel(new GridLayout(4, 1, 1 ,1));
		displayPanel    = new Panel3D(Border3D.EXCLUDE_BOTTOM_BORDER);
		display         = new CalcDisplay(192,26);
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
	private Panel createNumberPanel() {
		if (display != null) {
			ButtonComponent btn = null;

			for(int i = 0; i < numPanelText.length; i++) {
				btn = new ButtonComponent(numPanelText[i]);
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
	private Panel createOperPanel() {
		ButtonComponent btn = null;

		for(int i = 0; i < operPanelText.length; i++) {
			btn = new ButtonComponent(operPanelText[i]);
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
	private Panel createMemButtonPanel() {
		ButtonComponent btn = null;

		for(int i = 0; i < memButtonPanelText.length; i++) {
			btn = new ButtonComponent(memButtonPanelText[i]);
			btn.setFont(buttonfont);
			btn.addActionListener(handler);
			memButtonPanel.add(btn);
		}

		return memButtonPanel;
	}

}
