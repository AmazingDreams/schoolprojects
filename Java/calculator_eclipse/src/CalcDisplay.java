package calc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.*;

/**
 * CalcDisplay.java
 * This class creates a display screan for AwtCalc
 */
class CalcDisplay extends JTextField {

	public static final long serialVersionUID = 1;

	/**
	 * Initialize a new CalcDisplay
	 *
	 * @param  w  Width
	 * @param  h  Height
	 */
	CalcDisplay( int w, int h) {
		setSize( w , h  );

		this.setText("0");
		this.setEditable(false);
		this.setBackground(Color.WHITE);
	}


	/**
	 * Get the preferred size of this object
	 *
	 * @return  Dimension  The preferred size
	 */
	public Dimension getPreferredSize() {
		return new Dimension( getSize().width, getSize().height );
	}

	/**
	 * Set the  text (numbers) on the display
	 *
	 * TODO: 'append' is not a very good name as it insinuates
	 *       this function 'adds' text to the display instead of
	 *       replaces
	 *
	 * @param  String  The text to set on the display
	 */
	void append( String s )
	{
		// Clear screen first before updating text
		clear();
		this.setText(s);
		repaint();
	}

	/**
	 * Clear the screen
	 */
	void clear() {
		this.setText("0");
		repaint();
	}

}
