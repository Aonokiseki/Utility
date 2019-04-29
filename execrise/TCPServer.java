package execrise;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public static void main(String[] args){
		
		try {
			ServerSocket ss = new ServerSocket(6666);
			while(true){
				Socket s = ss.accept();
				DataInputStream dis = new DataInputStream(s.getInputStream());
				System.out.println("Server Get:"+dis.readUTF());
				dis.close();
				s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
