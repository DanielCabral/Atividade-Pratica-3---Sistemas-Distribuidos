package Questao1;

import java.io.IOException;
import java.net.ServerSocket;

public class Application {
	
	public Application() throws IOException {
			
	}
	
	public static void main(String [] args) throws IOException {
		ServerSocket servidor = new ServerSocket(12345);
		
		while(true) {
			servidor.accept();
			
		}
		
	}
}
