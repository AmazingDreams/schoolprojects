package calc;

import java.awt.*;
import java.awt.event.*;

/**
 * @file         AwtCalcApp.java
 * @description  Allows AwtCalc to be run as a stand-alone application.
 */
class AwtCalcApp {

	/**
	 * The static method called when starting up using the jar
	 *
	 * @param  String[]  Arguments - This program does not use them
	 */
	public static void main( String args[] )
	{
		Frame fr = new Frame();
		fr.setTitle("Awt Calculator");
		fr.setSize(310, 175);
		fr.setResizable(false);
		fr.add(new AwtCalc(), BorderLayout.CENTER);
		fr.setVisible(true);

		fr.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
					System.exit( 1 );
				}
			}
		);
	}

}
