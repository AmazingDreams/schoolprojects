package inzend3.classes;

abstract public class Persoon {

	protected String naam;

	protected String adres;

	protected String postcode;

	protected String woonplaats;

	public Persoon() {
		this.naam       = "Henk Jan Smit";
		this.adres      = "Lelystraat 23";
		this.postcode   = "1234AB";
		this.woonplaats = "Amsterdam";
	}

	public Persoon(String naam, String adres, String postcode, String woonplaats) {
		this.naam       = naam;
		this.adres      = adres;
		this.postcode   = postcode;
		this.woonplaats = woonplaats;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getNaam() {
		return this.naam;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public String getAdres() {
		return this.adres;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setWoonplaats(String woonplaats) {
		this.woonplaats = woonplaats;
	}

	public String getWoonplaats() {
		return this.woonplaats;
	}
}
