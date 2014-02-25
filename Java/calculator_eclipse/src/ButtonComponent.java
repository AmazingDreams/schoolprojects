package calc;

import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @file         ButtonComponent.java
 * @description  A Button component that feature's three a dimensional
 *               similar to swing components.
 */
public class ButtonComponent extends Component {

	/**
	 * @var  String  The buttons label
	 */
    private String label;

	/**
	 * @var  boolean  Used to determine the button presses
	 */
	private boolean pressed = false;

	/**
	 * @var  ActionListener  The action listener
	 */
	private ActionListener action;

	/**
	 * @var  boolean  Used to determine the button mouseover
	 */
	private boolean mouseOver = false;

	/**
	 * @var  Border3D  To draw a 3D border around the button
	 */
	private Border3D border;

	/**
	 * Initialize the buttoncomponent
	 *
	 * @param  String  The label to print on the button
	 */
	public ButtonComponent(String label)
	{
		this.label = label;
		border = new Border3D( this );
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
	}


	/**
	 * Returns the preferred size of the button,
	 * This method is called automatically when the component is painted
	 *
	 * @return  Dimension  The preferred size of the button
	 */
	public Dimension getPreferredSize()
	{
		Font f = getFont();

		if(f != null) {
			FontMetrics fm = getFontMetrics(getFont());

			return new Dimension(fm.stringWidth(label) + 10, fm.getHeight() + 5);
		}

		return new Dimension(25, 25);
	}

	/**
	 * Returns the minimum size of the button.
	 *
	 * @return  Dimension  The minimum size of the button
	 */
	public Dimension getMinimumSize() {
		return new Dimension(20, 20);
	}

	/**
	 * Detects mouse events on the component.
	 */
	public void processMouseEvent(MouseEvent e)
	{
		switch(e.getID()) {

			// Create a new actionevent and pass to the action listener - (the Button Handler)
			case MouseEvent.MOUSE_PRESSED:
				if ( action != null ) {
					 ActionEvent event = new ActionEvent( this, e.getID(), label );
					 action.actionPerformed( event );
				}

				pressed = true;

				//Invoke the repaint method which draws the button to appear pressed
				repaint();
				break;

			//When repaint is invoked, component color is returned to normal
			case MouseEvent.MOUSE_RELEASED:
				if(pressed == true) {
					pressed = false;
					repaint();
				}
				break;

			//repaint method lighten's component's color to give a hover effect
			case MouseEvent.MOUSE_ENTERED:
				mouseOver = true;
				repaint();
				break;

			//cancel hover effect
			case MouseEvent.MOUSE_EXITED:
				mouseOver = false;
				if(pressed == true) {
					pressed = false;
				}

				repaint();
				break;
		}

		super.processMouseEvent(e);
	}

	/**
	 * Adds an action listener
	 * [!] This overwrites the current action listener
	 *
	 * @param  ActionListener  The actionListener to add
	 */
	public void addActionListener(ActionListener a)
	{
		action = a;    //this is the ButtonHandler
	}

	/**
	 * Returns the buttons label
	 *
	 * @return  String  The label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Repaints the background
	 *
	 * @param  Graphics  The graphics adapter
	 */
	public void paint(Graphics g) {
		int width = getSize().width - 1;
		int height = getSize().height - 1;

		// Set background darker to give a pressed effect
		if(pressed) {
			g.setColor(getBackground().darker().darker());
		}
		// Set background lighter to give hover effect
		else if ( mouseOver ) {
			g.setColor( getBackground().brighter() );

		// Set background to normal
		} else {
			g.setColor(getBackground());
		}

		// Fill background
		g.fillRect(0, 0, width, height );

		// Draws 3D border
		border.draw3DBorder(g, 0, 0, width, height );

		// Center and draw the button's label
		Font f = getFont();
		if(f != null) {
			FontMetrics fm = getFontMetrics(getFont());
			g.setColor(getForeground());
			g.drawString(
					label,
					width/2 - fm.stringWidth(label)/2,
					height/2 + fm.getMaxDescent()
				);
		}
	}

}
