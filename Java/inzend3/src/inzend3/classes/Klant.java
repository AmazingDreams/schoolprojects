package inzend3.classes;

public class Klant extends Persoon {

	public Klant() {
		super();
	}

	public Klant(String naam, String adres, String postcode, String woonplaats) {
		super(naam, adres, postcode, woonplaats);
	}

	public void tevreden() {
		System.out.println("Hmmmm! Dat was lekker! Bedankt!");
	}
}
