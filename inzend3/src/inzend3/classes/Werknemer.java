package inzend3.classes;

abstract public class Werknemer extends Persoon {

	protected double salaris;

	protected int sofinummer;

	public Werknemer() {
		super();

		this.salaris    = 1000.0d;
		this.sofinummer = 1234567890;
	}

	public Werknemer(String naam, String adres, String postcode, String woonplaats, double salaris, int sofinummer) {
		super(naam, adres, postcode, woonplaats);

		this.salaris    = salaris;
		this.sofinummer = sofinummer;
	}

	public void setSalaris(double salaris) {
		this.salaris = salaris;
	}

	public double getSalaris() {
		return this.salaris;
	}

	public void setSofinummer(int sofinummer) {
		this.sofinummer = sofinummer;
	}

	public int getSofinummer() {
		return this.sofinummer;
	}
}
