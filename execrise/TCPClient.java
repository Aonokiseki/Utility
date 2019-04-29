package execrise;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient {
	public static void main(String[] args){
		try {
			Socket s = new Socket("127.0.0.1", 6666);
			OutputStream os = s.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			Scanner scanner = new Scanner(System.in);
			dos.writeUTF(scanner.nextLine());
			dos.flush();
			dos.close();
			s.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
