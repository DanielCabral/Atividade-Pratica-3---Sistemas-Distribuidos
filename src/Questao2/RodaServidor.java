package Questao2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;


public class RodaServidor {
	
	public static void main(String [] args) throws UnknownHostException, IOException {
		
		ServerSocket server = new ServerSocket(12345);
		String listaDeArquivos="src/Questao3/Arquivo.txt";
	     while(true){
	       System.out.println("Aguardando conexão...");
	       Socket con = server.accept();
	       clientes.add(con);
	       System.out.println("Cliente conectado...");
	       Servidor servidor = new Servidor();
	       Thread t= new Thread(new Servidor(cliente, listaDeArquivos));
	       t.start();
	       
	    
	    }
	}
	
}
