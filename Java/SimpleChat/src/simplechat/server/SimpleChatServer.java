package simplechat.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SimpleChatServer implements Runnable {

	ArrayList<PrintWriter> clientOutputStreams;

	public void run() {
		go();
	}

	public void go() {
		clientOutputStreams = new ArrayList<PrintWriter>();

		try {
			ServerSocket serverSocket = new ServerSocket(5000);

			while(true) {
				Socket clientSocket = serverSocket.accept();
				clientOutputStreams.add(new PrintWriter(clientSocket.getOutputStream()));

				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();

				System.out.println("Server: got a connection");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void tellEveryone(String message) {
		for(PrintWriter pw : clientOutputStreams) {
			try {
				pw.println(message);
				pw.flush();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket socket;

		public ClientHandler(Socket clientSocket) {
			try {
				socket = clientSocket;
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		public void run() {
			String message;

			try {
				while((message = reader.readLine()) != null) {
					System.out.println("Server: read "+ message);
					tellEveryone(message);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}




}
