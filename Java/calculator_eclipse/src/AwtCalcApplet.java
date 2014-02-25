package calc;

import java.awt.BorderLayout;
import javax.swing.JApplet;

/**
 * @file         AwtCalcApplet.java
 * @description  Allow's AwtCalc to be run as an applet.
 */
public class AwtCalcApplet extends JApplet {

	public static final long serialVersionUID = 1;

	/**
	 * Initializes the applet
	 * This method is called when the program is run as an applet
	 */
	public void init()
	{
		setLayout(new BorderLayout());
		add(new AwtCalc(), BorderLayout.CENTER);
	}

}
