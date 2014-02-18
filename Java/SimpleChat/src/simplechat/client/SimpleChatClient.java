package simplechat.client;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class SimpleChatClient implements Runnable {

	public void run() {
		try {
			// Allow the server to set up
			Thread.sleep(500);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		go();
	}

	private JTextArea incoming;

	private JTextField outgoing;

	private PrintWriter writer;

	private Socket socket;

	private BufferedReader reader;

	public void go() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();

		incoming = new JTextArea(15,50);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane incScroller = new JScrollPane(incoming);
		incScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		incScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(incScroller);

		outgoing = new JTextField(20);
		panel.add(outgoing);

		JButton send = new JButton("Send");
		send.addActionListener(new SendActionListener());
		panel.add(send);

		frame.getContentPane().add(BorderLayout.CENTER, panel);

		setupNetworking();

		Thread incoming = new Thread(new IncomingReader());
		incoming.start();

		frame.setSize(600, 400);
		frame.setVisible(true);
	}

	public void setupNetworking() {
		try {
			socket = new Socket("localhost", 5000);
			writer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			System.out.println("Socket established");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	class SendActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				writer.println(outgoing.getText());
				writer.flush();
			} catch(Exception ex) {
				ex.printStackTrace();
			}

			outgoing.setText("");
			outgoing.requestFocus();
		}
	}

	class IncomingReader implements Runnable {
		public void run() {
			String message;
			try {
				while((message = reader.readLine()) != null) {
					System.out.print("Chat: read "+ message);
					incoming.append(message +"\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
