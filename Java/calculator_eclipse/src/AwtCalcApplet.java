package calc;

import java.applet.Applet;
import java.awt.*;

/**
 * @file         AwtCalcApplet.java
 * @description  Allow's AwtCalc to be run as an applet.
 */
public class AwtCalcApplet extends Applet {

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
