package multiCleintChatApp;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private static final String[] isimler = { "Lea", "Jonas","Johannes","Migen"};
	private static final String[] meslekler = {"Entwickler","Arzt","Lehrer","Polizist"};
	
	private static final String SERVER_IP = "127.0.0.1";
	private static final int PORT = 5003;
	private static ExecutorService pool = Executors.newFixedThreadPool(3);
	private static ArrayList<ClientHandler> clients = new ArrayList<>();
	
	public static ArrayList<ClientHandler> getClients(){
		return clients;
	}
	//Thread lari yonetmek icin bir havuz olusturalim. en fazla 3 cleint a hizmetvermek istiyoruz

	public static void main(String[] args) throws IOException {

		ServerSocket serverSocket = new ServerSocket(PORT);
		
		while(true) {
			System.out.println("[SERVER] The server is warting to connection a client...");
			Socket client = serverSocket.accept();
			System.out.println("[SERVER] a client connected!");
			//istemci threadi
			ClientHandler clientThread = new ClientHandler(client, clients);
			
			//clients icersine client thredi ekler
			clients.add(clientThread);
			
			//istemciyi pool da aktif edecegiz
			pool.execute(clientThread);
			
		}

	}
	public static String rastgeleKisiSec() {
		int no = (int)(Math.random()*isimler.length);
		return isimler[no]+" "+meslekler[no];
	}

}
