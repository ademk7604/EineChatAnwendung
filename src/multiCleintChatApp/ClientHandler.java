package multiCleintChatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
	private Socket client;
	private BufferedReader input;
	private PrintWriter output;
	private static ArrayList<ClientHandler> clients;

	public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException {
		client = clientSocket;
		this.clients = clients;
		input = new BufferedReader(new InputStreamReader(client.getInputStream()));
		output = new PrintWriter(client.getOutputStream(), true);
	}

	@Override
	public void run() {
		try {
			while (true) {
				String request = input.readLine();
				if (request.contains("bir kisi soyle")) {
					output.println(Server.rastgeleKisiSec());
				} else if (request.startsWith("[herkes]")) {
					int ilkBosluk = request.indexOf(" ");
					if (ilkBosluk != -1) {
						// butun kullanicilara mesaji yolla
						butunKisilereMesajGonder(request.substring(ilkBosluk + 1));

					}
				} else if (request.equals("Exit")) {
					output.println("Hoscakal yine bekleriz");
					break;
				} else {
					output.println("Rastgele bir kisi icin \"bir kisi soyle\" yaziniz!");
				}
			}
		} catch (IOException e) {
			System.out.println("Client Handler da hata olustu");
			e.printStackTrace();
		} finally {
			output.close();
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void butunKisilereMesajGonder(String msg) {
		for (ClientHandler tempClient : clients) {
			tempClient.output.println(Thread.currentThread().getName() + ": " + msg);
		}
	}

}
