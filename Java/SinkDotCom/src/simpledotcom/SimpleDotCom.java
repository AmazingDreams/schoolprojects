package simpledotcom;

import java.util.Scanner;
import java.util.Random;

import simpledotcom.classes.DotCom;
import simpledotcom.misc.World;

public class SimpleDotCom {

	public static final int NUM_DOTCOMS = 3;

	private String[] dotcom_names = new String[] {
		"google.com",
		"facebook.com",
		"twitter.com",
		"ebay.com",
		"wikipedia.com",
	};

	World world;

	public SimpleDotCom() {
		System.out.println("Welcome to DotCom!");
		this.world = new World();
		this.world.setDotComs(this.createDotComs());

		Scanner scan = new Scanner(System.in);
		while(this.world.hasDotComsLeft()) {
			System.out.println("Enter coordinates to strike [A-G][1-7]:");
			String target = scan.nextLine();

			if(target.length() == 0) {
				System.out.println("You supplied an invalid coordinate, try again");
				continue;
			}

			int x = World.charToCoordinate(target.charAt(0));
			int y = Character.getNumericValue(target.charAt(1)) - 1; // Subtract 1, because A7 should be 0,6 in our program

			if( ! World.isInBounds(x, y)) {
				System.out.println("You supplied an invalid coordinate, try again");
				continue;
			}

			DotCom d = this.world.hitAt(x, y);
			if(d != null) { // hitAt returns the struck dotCom
				System.out.println("You hit "+ d.getName());

				if( ! d.isAlive()) {
					System.out.println("You utterly destroyed the servers of "+ d.getName() +"!");
				}
			} else {
				System.out.println("Miss! Try again!");
			}
		}

		System.out.println("You won!");
	}

	public static void main(String[] args) {
		// Start the game
		SimpleDotCom game = new SimpleDotCom();
	}

	private DotCom[] createDotComs() {
		DotCom[] dotcoms = new DotCom[NUM_DOTCOMS];

		for(int i = 0; i < NUM_DOTCOMS; i++) {
			dotcoms[i] = new DotCom(this.getRandName());
		}

		return dotcoms;
	}

	private String getRandName() {
		int rand = new Random().nextInt(NUM_DOTCOMS);
		String randName = this.dotcom_names[rand];

		// Check if name was already used
		if(randName == null) {
			return this.getRandName();
		}

		// Set the name to null to ensure it cannot be used again
		this.dotcom_names[rand] = null;

		return randName;
	}

}
