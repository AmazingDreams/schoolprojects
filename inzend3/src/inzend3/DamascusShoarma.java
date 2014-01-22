package inzend3;

import java.util.ArrayList;

import inzend3.classes.Bezorger;
import inzend3.classes.Klant;
import inzend3.classes.Kok;

public class DamascusShoarma {

	private static final int aantalKoks = 2;

	private static final int aantalBezorgers = 2;

	public static ArrayList<Kok> koks = new ArrayList<Kok>();

	public static ArrayList<Bezorger> bezorgers = new ArrayList<Bezorger>();

	public static Bezorger getBezorger() {
		// Voor iedere bezorger
		// if isBeschikbaar
		//   return bezorger

		return bezorgers.get(0);
	}

	public static int getAantalKoks() {
		return koks.size();
	}

	public static int getAantalBezorgers() {
		return bezorgers.size();
	}

	public DamascusShoarma() {
		koks.add(new Kok("Henk de Vries", "Koolsingel 31", "1351AB", "Amsterdam", 1000.0d, 1231123, true));
		koks.add(new Kok("Freek van Es",  "Koolstraat 13", "1013BC", "Amstelveen", 999.9d, 1239562, true));
		bezorgers.add(new Bezorger("Japie Leeghwater", "Jansstraat 123", "1581CD", "Amsterdam", 20.0d, 12312351, true));
		bezorgers.add(new Bezorger("Jappie Leeghwater", "Jansstraat 123", "1581CD","Amsterdam", 200.d, 12312352, true));

		System.out.println("DamascusShoarma heeft "+ getAantalKoks() +" koks en "+ getAantalBezorgers() +" bezorgers\n");

		Klant k = new Klant("Henk-Jan van Dam", "Sterrenhof 13", "1289CD", "Amsterdam");

		koks.get(0).neemBestelling(k);
	}

	public static void main(String[] args) {
		new DamascusShoarma();
	}

}
