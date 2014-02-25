package calc;


import java.awt.*;

/**
 * @file         Border3D.java
 * @description  A utility class useful for creating components with
 *               three dimensional borders.
 */
public class Border3D {

	/*****************************************************************
	 * Border Types
	 *
	 * Static variables that allows control of how the the borders is
	 * displayed.
	 *
	 * EXCLUDE_TOP_BORDER:     Use to create components that lack a
	 *                         top border.
	 *
	 * EXCLUDE_BOTTOM_BORDER:  Use to create components that lack a
	 *                         bottom border.
	 *
	 * FULL_BORDER:            Default type.  Creates a component with
	 *                         a full border.
	 ******************************************************************/
	static final int EXCLUDE_TOP_BORDER = 1,
	                 EXCLUDE_BOTTOM_BORDER = 2,
	                 FULL_BORDER = 3;

	/**
	 * @var  int  Type of border
	 */
	private int type;

	/**
	 * @var  Component  The component itself
	 */
	private Component comp;


	/**
	 * Initialize Border3D using only a component
	 * This will make the border default to 'FULL_BORDER'
	 *
	 * @param  Component  The component
	 */
	public Border3D( Component comp ) {
		this(comp, FULL_BORDER);
	}

	/**
	 * Initialize Border3D with a specific border
	 *
	 * @param  Component  The component
	 * @param  int        The border type
	 */
	public Border3D( Component comp, int type )
	{
		this.comp = comp;
		this.type = type;
	}

	/**
	 * Draws a 3D border around components perimiter
	 *
	 * @param  Graphics  The graphics adapter
	 */
	public void draw3DBorder( Graphics g )
	{
		draw3DBorder(g, comp.size());
	}

	/**
	 * Draws a 3D border around components perimiter with a custom size
	 *
	 * @param  Graphics   The graphics adapter
	 * @param  Dimension  The area to draw the border
	 */
	public void draw3DBorder( Graphics g, Dimension area ) {
		draw3DBorder(g, 0, 0, area.width, area.height);
	}

	public void draw3DBorder( Graphics g, int x, int y, int width, int height ) {

		//Draws top part of border
		if (type == EXCLUDE_BOTTOM_BORDER || type == FULL_BORDER ) {
			g.setColor( comp.getBackground().darker() );
			g.drawLine( x, y, width, y );

			g.setColor( Color.white );
			g.drawLine( x+1, y+1, width-1, y+1 );
		}

		//Draws bottom portion of border
		if ( type == EXCLUDE_TOP_BORDER || type == FULL_BORDER )
		{
			g.setColor( comp.getBackground().brighter() );
			g.drawLine( x, height, width, height);
			g.setColor( comp.getBackground().darker() );
			g.drawLine( x+1, height-1, width-1, height -1);
		}

		//draws the sides of the border
		g.setColor( comp.getBackground().brighter() );
		g.drawLine( x+1, y+1, x+1, height - 1 );
		g.drawLine( width, y, width, height );

		g.setColor( comp.getBackground().darker() );
		g.drawLine( x, y, x, height );
		g.drawLine( width-1, y+1, width-1, height - 1);
	}

}
