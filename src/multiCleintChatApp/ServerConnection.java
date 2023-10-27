package multiCleintChatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerConnection implements Runnable{
	private Socket socket;
	private BufferedReader input;
	
	public ServerConnection(Socket socket) throws IOException {
		this.socket = socket;
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	
	@Override
	public void run() {
		try {
			while(true) {
				String sunucuCevap = input.readLine();
				if(sunucuCevap == null) {
					break;
				}
				System.out.println(sunucuCevap);
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	

}
