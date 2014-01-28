package kreta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

class Gerecht {

	/**
	 * @var  int  ID
	 */
	private int id;

	/**
	 * @var  String  Naam
	 */
	private String naam;

	/**
	 * @var  Double  Prijs
	 */
	private double prijs;

	/**
	 * @var  String  Ingredienten
	 */
	private String ingredienten;

	/**
	 * @var  String  Recept
	 */
	private String recept;

	/**
	 * Populates the fields with default values
	 */
	public Gerecht() {
		this.id = 0;
		this.naam = "";
		this.prijs = 0.0;
		this.ingredienten = "";
		this.recept = "";
	}

	/**
	 * Populates the fields with given values
	 *
	 * @param  int     id
	 * @param  String  Naam
	 * @param  double  Prijs
	 * @param  String  Ingredienten
	 * @param  String  Recept
	 */
	public Gerecht(int id, String naam, double prijs, String ingredienten, String recept) {
		this.id = id;
		this.naam = naam;
		this.prijs = prijs;
		this.ingredienten = ingredienten;
		this.recept = recept;
	}

	/**
	 * Turn the object into a string
	 *
	 * @return  String  Naam
	 */
	public String toString() {
		return this.naam;
	}

	/**
	 * Get the objects name
	 *
	 * @return  String  Naam
	 */
	public String getNaam() {
		return this.naam;
	}

	/**
	 * Set the objects name
	 *
	 * @param  String  New name
	 */
	public void setNaam(String naam) {
		this.naam = naam;
	}

	/**
	 * Get the objects prijs
	 *
	 * @return  double  Prijs
	 */
	public double getPrijs() {
		return this.prijs;
	}

	/**
	 * Set the objects prijs
	 *
	 * @param  double  New prijs
	 */
	public void setPrijs(double prijs) {
		this.prijs = prijs;
	}

	/**
	 * Get the objects ingredients
	 *
	 * @return  String  Ingredients
	 */
	public String getIngredienten() {
		return this.ingredienten;
	}

	/**
	 * Set the objects ingredients
	 *
	 * @param  String  New ingredients
	 */
	public void setIngredienten(String ingredienten) {
		this.ingredienten = ingredienten;
	}

	/**
	 * Get the objects recept
	 *
	 * @return  String  Recept
	 */
	public String getRecept() {
		return this.recept;
	}

	/**
	 * Set the objects recept
	 *
	 * @param  String  New recept
	 */
	public void setRecept(String recept) {
		this.recept = recept;
	}

	/**
	 * Save the object
	 * This method finds out whether to INSERT or UPDATE by itself
	 *
	 * @throws  SQLException
	 * @param   Connection    DB Connection
	 * @return  boolean       Whether this is a new object or not
	 */
	public boolean save(Connection con) throws SQLException {
		if(this.id == 0) {
			this.insert(con);
			return true;
		} else {
			this.update(con);
			return false;
		}
	}

	/**
	 * Delete the object
	 *
	 * @throws  SQLException
	 * @param   Connection    DB Connection
	 */
	public void delete(Connection con) throws SQLException {
		if(this.id != 0) {
			Statement s = con.createStatement();

			String q = "DELETE FROM `afhaalmenus` WHERE `id`='"+id+"'";

			s.executeUpdate(q);
		}
	}

	/**
	 * Insert the object
	 *
	 * @throws  SQLException
	 * @param   Connection    DB Connection
	 */
	private void insert(Connection con) throws SQLException {
		Statement s = con.createStatement();

		String q = "INSERT INTO `afhaalmenus` (`naam_gerecht`, `prijs`, `ingredienten`, `recept`)"+
			"VALUES('"+naam+"','"+prijs+"','"+ingredienten+"','"+recept+"')";

		s.executeUpdate(q, Statement.RETURN_GENERATED_KEYS);

		// Set the generated id
		ResultSet generatedKeys = s.getGeneratedKeys();
		if(generatedKeys.next()) {
			this.id = generatedKeys.getInt(1);
		}
	}

	/**
	 * Update the object
	 *
	 * @throws  SQLException
	 * @param   Connection    DB Connection
	 */
	private void update(Connection con) throws SQLException {
		Statement s = con.createStatement();

		String q = "UPDATE `afhaalmenus` SET `naam_gerecht`='"+naam+"', `prijs`='"+prijs+"',"+
			"`ingredienten`='"+ingredienten+"', `recept`='"+recept+"' WHERE `id`='"+id+"'";

		s.executeUpdate(q);
	}
}
