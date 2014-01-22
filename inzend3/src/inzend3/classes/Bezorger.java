package inzend3.classes;

public class Bezorger extends Werknemer {

	private boolean eigenBrommer;

	public Bezorger() {
		super();

		this.eigenBrommer = false;
	}

	public Bezorger(String naam, String adres, String postcode, String woonplaats, double salaris, int sofinummer, boolean eigenBrommer) {
		super(naam, adres, postcode, woonplaats, salaris, sofinummer);

		this.eigenBrommer = eigenBrommer;
	}

	public void setEigenBrommer(boolean eigenBrommer) {
		this.eigenBrommer = eigenBrommer;
	}

	public boolean getEigenBrommer() {
		return this.eigenBrommer;
	}

	public void bezorgen(Klant k, Gerecht g) {
		// Bezorg de bestelling

		k.tevreden();
	}
}
