package calc;

import java.awt.*;

/**
 * @file         CalcDisplay.java
 * @description  This class creates a display screan for AwtCalc
 */
class CalcDisplay extends Canvas {

	/**
	 * @var  String  The text on the display
	 */
	private String text;

	/**
	 * @var  Rectangle  The area of the display
	 */
	private Rectangle area;

	/**
	 * @var  Border3D  The border of the display
	 */
	private Border3D border;

	/**
	 * Initialize a new CalcDisplay
	 *
	 * @param  int  Width
	 * @param  int  Height
	 */
	CalcDisplay( int w, int h)
	{
		setSize( w , h  );

		// Determine the center of the component and create Rectangle object
		// which will be used to draw the white portion and the borders of the
		// display
		int x = ( size().width - w ) / 2;
		int y = ( size().height - h ) / 2 + 6;

		border = new Border3D( this );
		area = new Rectangle( x, y, w, h );

		setFont( new Font( "monospace", Font.PLAIN, 14 ) );
		setForeground( Color.black );
		text = "0";
	}


	/**
	 * Get the preferred size of this object
	 *
	 * @return  Dimension  The preferred size
	 */
	public Dimension getPreferredSize()
	{
		return new Dimension( size().width, size().height );
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
		text = s;
		repaint();
	}

	/**
	 * Clear the screen
	 */
	void clear()
	{
		text = "";
		repaint();
	}

	/**
	 * Get the text on the display
	 *
	 * @return  String  The text on the display
	 */
	String getText() {
		return text;
	}

	/**
	 * Paints all contents of the component
	 *
	 * @param  Graphics  The graphics adapter
	 */
	public void paint( Graphics g )
	{
		drawDisplayScreen( g );
		drawOutput( g );
	}

	/**
	 * Draw the display
	 *
	 * @param  Graphics  The graphics adapter
	 */
	protected void drawDisplayScreen( Graphics g )
	{
		g.setColor( Color.white );
		g.fillRect( area.x, area.y, area.width, area.height );
		border.draw3DBorder( g, area.x, area.y, area.width, area.height );
	}

	/**
	 * Draw the text (numbers) on the screen
	 *
	 * @param  Graphics  The graphics adapter
	 */
	protected void drawOutput( Graphics g )
	{
		g.setColor( Color.black );
		g.drawString( text, area.x+3, area.y+15 );
	}

}
