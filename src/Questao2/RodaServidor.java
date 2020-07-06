package Questao2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class RodaServidor {
	
	public static void main(String [] args) throws UnknownHostException, IOException {
		
		ServerSocket server = new ServerSocket(12345);
		String listaDeArquivos = "arquivo1.txt";
	     while(true){
	       System.out.println("Aguardando conexão...");
	       Socket con = server.accept();
	       System.out.println("Cliente conectado...");
	       Servidor.cont++;
	       Servidor servidor= new Servidor(con,listaDeArquivos);
	       Thread t1;
	       t1 = new Thread(servidor);
	       t1.start();   
	    }
	}
	
}
