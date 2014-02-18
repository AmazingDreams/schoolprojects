package kreta;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Kreta {

	/**
	 * @var  Gerecht  The currently selected Gerecht
	 */
	private Gerecht selectedGerecht;

	/**
	 * @var  String  Path to config file
	 */
	private String configFile = "config/config.ini";

	/**
	 * @var  String  DB Host
	 */
	private String host = "";

	/**
	 * @var  String  DB name
	 */
	private String database = "";

	/**
	 * @var  String  DB username
	 */
	private String user = "";

	/**
	 * @var  String  DB password
	 */
	private String password = "";

	/**
	 * @var  Connection  Connection to DB
	 */
	private Connection connection;

	/**
	 * @var  ArrayList<Gerecht>  Holds the gerechten
	 */
	private ArrayList<Gerecht> gerechten;

	/**
	 * @var  JList<Object>  The list in the GUI
	 */
	private JList<Object> list;

	/**
	 * @var  JTextField  The name field
	 */
	private JTextField naamField;

	/**
	 * @var  JTextField  The prijs field
	 */
	private JTextField prijsField;

	/**
	 * @var  JTextArea  The ingredients area
	 */
	private JTextArea ingredientArea;

	/**
	 * @var  JTextArea  The recept area
	 */
	private JTextArea receptArea;

	/**
	 * Check dependencies, read the config, create the frame, etc.
	 */
	public void go() {
		// All these methods exit when something fails
		checkDependencies();
		readConfig();
		setupDatabase();

		// If all went well we can go set up the GUI
		getGerechten();
		createGUI();
	}

	/**
	 * Check whether the jdbc driver is present
	 */
	private void checkDependencies() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Kan driver niet laden: "+e.getMessage(), "foutmelding", JOptionPane.ERROR_MESSAGE);

			System.exit(1);
		}
	}

	/**
	 * Read the config file
	 */
	private void readConfig() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File(configFile)));

			String line;
			while((line = reader.readLine()) != null) {
				String[] parts = line.split(":");
				String key     = parts[0];
				String value   = parts.length > 1 ? parts[1] : "";

				if(key.equals("host")) {
					this.host = value;
				} else if (key.equals("database")) {
					this.database = value;
				} else if (key.equals("user")) {
					this.user = value;
				} else if (key.equals("password")) {
					this.password = value;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			System.exit(1);
		}
	}

	/**
	 * Set up the connection to the database
	 */
	private void setupDatabase() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://"+this.host+"/"+this.database+"?user="+this.user+"&password="+this.password);
		} catch (Exception e) {
			displaySQLErrors(e);

			System.exit(1);
		}
	}

	/**
	 * Select all the gerechten from the database
	 */
	private void getGerechten() {
		gerechten = new ArrayList<Gerecht>();

		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM `afhaalmenus`");

			while(rs.next()) {
				this.gerechten.add(new Gerecht(
						Integer.parseInt(rs.getString(1)),
						rs.getString(2),
						Double.parseDouble(rs.getString(3)),
						rs.getString(4),
						rs.getString(5)
				));
			}
		} catch(Exception e) {
			displaySQLErrors(e);
		}
	}

	/**
	 * Create the GUI
	 */
	private void createGUI() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new BorderLayout());

		list = new JList<Object>();
		updateJList();
		JScrollPane sPane = new JScrollPane(list);
		panel.add(BorderLayout.CENTER, sPane);

		/* Create the buttons */
		JPanel buttonPanel = new JPanel(new FlowLayout());

		JButton newButton = new JButton("Nieuw");
		newButton.addActionListener(new NewActionListener());
		buttonPanel.add(newButton);

		JButton bewerkButton = new JButton("Bewerken");
		bewerkButton.addActionListener(new BewerkActionListener());
		buttonPanel.add(bewerkButton);

		JButton wisButton = new JButton("Wissen");
		wisButton.addActionListener(new WisActionListener());
		buttonPanel.add(wisButton);

		panel.add(BorderLayout.NORTH, buttonPanel);
		/* END Create the buttons */

		/* Create the fields */
		JPanel fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));

		fieldsPanel.add(new JLabel("Naam"));
		naamField = new JTextField();
		fieldsPanel.add(naamField);

		fieldsPanel.add(new JLabel("Prijs"));
		prijsField = new JTextField();
		fieldsPanel.add(prijsField);

		fieldsPanel.add(new JLabel("Ingredienten"));
		ingredientArea = new JTextArea(10, 20);
		JScrollPane ingredientSPane = new JScrollPane(ingredientArea);
		fieldsPanel.add(ingredientSPane);

		fieldsPanel.add(new JLabel("Recept"));
		receptArea = new JTextArea(10, 20);
		JScrollPane receptSPane = new JScrollPane(receptArea);
		fieldsPanel.add(receptArea);

		JButton opslaanButton = new JButton("Opslaan");
		opslaanButton.addActionListener(new OpslaanActionListener());
		fieldsPanel.add(opslaanButton);
		/* END Create the fields */

		panel.add(BorderLayout.EAST, fieldsPanel);

		frame.getContentPane().add(panel);

		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	/**
	 * Repopulate the JList with the new data
	 */
	private void updateJList() {
		Object[] data = gerechten.toArray();
		list.setListData(data);
	}

	/**
	 * Show a dialog describing an SQL error
	 *
	 * @param  Exception  The SQL exception to show
	 */
	private void displaySQLErrors(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage(), "foutmelding", JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args) {
		Kreta kreta = new Kreta();
		kreta.go();
	}

	/**
	 * Action listener for the New button
	 */
	class NewActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			selectedGerecht = new Gerecht();

			naamField.setText("");
			prijsField.setText("");
			ingredientArea.setText("");
			receptArea.setText("");
		}
	}

	/**
	 * Action Listener for the Bewerk button
	 */
	class BewerkActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			selectedGerecht = (Gerecht) list.getSelectedValue();

			naamField.setText(selectedGerecht.getNaam());
			prijsField.setText(""+selectedGerecht.getPrijs());
			ingredientArea.setText(selectedGerecht.getIngredienten());
			receptArea.setText(selectedGerecht.getRecept());
		}
	}

	/**
	 * Action Listener for the Wis button
	 */
	class WisActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				Gerecht toDelete = (Gerecht) list.getSelectedValue();
				toDelete.delete(connection);

				gerechten.remove(toDelete);

				updateJList();
			} catch (Exception ex) {
				displaySQLErrors(ex);
			}
		}
	}

	/**
	 * Action Listener for the Opslaan button
	 */
	class OpslaanActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			selectedGerecht.setNaam(naamField.getText());
			selectedGerecht.setPrijs(Double.parseDouble(prijsField.getText()));
			selectedGerecht.setIngredienten(ingredientArea.getText());
			selectedGerecht.setRecept(receptArea.getText());

			try {
				boolean isNew = selectedGerecht.save(connection);

				if(isNew) {
					gerechten.add(selectedGerecht);
				}

				updateJList();
			} catch (Exception ex) {
				displaySQLErrors(ex);
			}
		}
	}
}
