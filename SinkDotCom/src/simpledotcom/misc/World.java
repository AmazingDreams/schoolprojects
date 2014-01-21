package simpledotcom.misc;

import java.util.Random;

import simpledotcom.classes.DotCom;

public class World {

	public static final int WIDTH  = 7;
	public static final int HEIGHT = 7;

	private DotCom[] dotcoms;

	public static boolean isInBounds(int x, int y) {
		// If coordinates are out of bounds, return false
		if((x >= WIDTH || x < 0) ||
		   (y >= HEIGHT || y < 0)) {
			return false;
		}

		return true;
	}

	public static int charToCoordinate(char c) {
		if(c == 'A' || c == 'a') {
			return 0;
		} else if (c == 'B' || c == 'b') {
			return 1;
		} else if (c == 'C' || c == 'c') {
			return 2;
		} else if (c == 'D' || c == 'd') {
			return 3;
		} else if (c == 'E' || c == 'e') {
			return 4;
		} else if (c == 'F' || c == 'f') {
			return 5;
		} else if (c == 'G' || c == 'g') {
			return 6;
		} else {
			// This somewhat equals 'return out of bounds coordinate'
			// in any other case
			return 7;
		}
	}

	public World() {}

	public void setDotComs(DotCom[] dotcoms) {
		this.dotcoms = dotcoms;

		this.distributeDotComs();
	}

	private void distributeDotComs() {
		for(DotCom d : this.dotcoms) {
			this.do_distributeDotCom(d);
		}
	}

	private void do_distributeDotCom(DotCom d) {
		Random r = new Random();

		// Set up initial x,y values
		int x = r.nextInt(WIDTH);
		int y = r.nextInt(HEIGHT);

		// Distribute towards direction:
		// 0 => North, 1 => East, 2 => South, 3 => West
		int direction = r.nextInt(4);

		// Create an empty points array
		boolean[][] points = new boolean[WIDTH][HEIGHT];

		boolean retry = false;

		int l = 0;
		do {
			// Check if x,y is within bounds
			if( ! isInBounds(x,y)) {
				retry = true;
			}

			// Check if x,y is already taken
			if(this.getDotComAt(x, y) != null) {
			   retry = true;
			}

			if( ! retry) {
				points[x][y] = true;
			}

			switch(direction) {
				case 0: // Move to the north
					y--;
					break;
				case 1: // Move to the east
					x++;
					break;
				case 2: // Move to the south
					y++;
					break;
				case 3: // Move to the west
					x--;
					break;
			}

			// We did one 'length', so add that
			l++;
		} while( ! retry && l < d.getLength());

		if(retry) { // If we need to retry, do it again
			this.do_distributeDotCom(d);
		} else { // If we don't, set the points to our dotcom
			d.setPoints(points);
		}
	}

	public boolean hasDotComsLeft() {
		for(DotCom d : this.dotcoms) {
			if(d.isAlive()) {
				return true;
			}
		}

		return false;
	}

	public DotCom hitAt(int x, int y) {
		DotCom d = this.getDotComAt(x, y);

		if(d != null) {
			d.setValueAt(x, y, false);
		}

		return d;
	}

	public DotCom getDotComAt(int x, int y) {
		for(DotCom d : this.dotcoms) {
			if(d.getPointAt(x, y)) {
				return d;
			}
		}

		return null;
	}
}
