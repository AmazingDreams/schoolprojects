package simplechat;

import simplechat.client.SimpleChatClient;
import simplechat.server.SimpleChatServer;

class SimpleChat {

	public static void main(String[] args) {
		SimpleChatServer server = new SimpleChatServer();
		Thread tServer = new Thread(server);
		SimpleChatClient client = new SimpleChatClient();
		Thread tClient = new Thread(client);

		tServer.start();
		tClient.start();
	}
}
