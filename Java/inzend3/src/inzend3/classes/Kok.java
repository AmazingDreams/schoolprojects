package inzend3.classes;

import inzend3.DamascusShoarma;

public class Kok extends Werknemer {

	protected boolean kookboek;

	public Kok() {
		super();

		this.kookboek = false;
	}

	public Kok(String naam, String adres, String postcode, String woonplaats, double salaris, int sofinummer, boolean kookboek) {
		super(naam, adres, postcode, woonplaats, salaris, sofinummer);

		this.kookboek = kookboek;
	}

	public void setKookboek(boolean kookboek) {
		this.kookboek = kookboek;
	}

	public boolean getKookboek() {
		return this.kookboek;
	}

	public void neemBestelling(Klant k) {
		Gerecht g = this.koken();
		Bezorger b = DamascusShoarma.getBezorger();
		b.bezorgen(k, g);
	}

	public Gerecht koken() {
		// code om te koken

		return new Broodje();
	}
}
