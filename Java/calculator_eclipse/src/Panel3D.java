package calc;

import java.awt.*;

/**
 * @file         Panel3D.java
 * @description  Creates a panel with a three dimensional border
 */
class Panel3D extends Panel {

	/**
	 * @var  Border3D  Used for drawing the border
	 */
	private Border3D border;     //used for drawing the border

	/**
	 * Initializes the panel with a full border
	 */
	public Panel3D() {
		this( Border3D.FULL_BORDER );
	}

	/**
	 * Initializes the panel with a custom border type
	 *
	 * @param  int  Type of border
	 */
	public Panel3D( int type ) {
		border = new Border3D( this, type );
	}

	/**
	 * Paint the panel
	 *
	 * @param  Graphics  The graphics adapter
	 */
	public void paint( Graphics g ) {
		border.draw3DBorder( g );
	}

}
