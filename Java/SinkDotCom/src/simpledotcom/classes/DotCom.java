package simpledotcom.classes;

import simpledotcom.misc.World;

public class DotCom {

	private String name;

	private boolean[][] points;

	private int length = 3;

	public DotCom(String name) {
		this.name = name;
		this.points = new boolean[World.WIDTH][World.HEIGHT];
	}

	public int getLength() {
		return this.length;
	}

	public String getName() {
		return this.name;
	}

	public void setPoints(boolean[][] points) {
		this.points = points;
	}

	public void setValueAt(int x, int y, boolean value) {
		if( ! World.isInBounds(x, y)) {
			// Do nothing
		} else {
			this.points[x][y] = value;
		}
	}

	public boolean isAlive() {
		for(int x = 0; x < this.points.length; x++) {
			for(int y = 0; y < this.points[x].length; y++) {
				// If even one point is available, we're alive
				if(this.points[x][y]) {
					return true;
				}
			}
		}

		// We're dead :(
		return false;
	}

	public boolean getPointAt(int x, int y) {
		// If the coordinates are out of bounds, return false
		if( ! World.isInBounds(x, y)) {
			return false;
		}

		return this.points[x][y];
	}
}
