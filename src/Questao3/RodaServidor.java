package Questao3;

import java.awt.List;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.server.SocketSecurityException;
import java.util.ArrayList;
import java.util.HashMap;

public class RodaServidor {
	
	public static void main(String [] args) throws UnknownHostException, IOException {
		
		ArrayList<Socket> clientes=new ArrayList<Socket>();
		ServerSocket server = new ServerSocket(12345);
	    Servidor cliente1 = null;
	     while(true){
	       System.out.println("Aguardando conexão...");
	       Socket con = server.accept();
	       clientes.add(con);
	       System.out.println("Cliente conectado...");
	       Servidor.cont++;
	       Servidor servidor= new Servidor(con);
	       Thread t1;
	       if(Servidor.cont == 2) {
	    	   cliente1.setCliente2(con);
	    	   servidor.setCliente2(cliente1.getCliente());
	    	   t1 = new Thread(servidor);
	    	   Servidor.cont = 0;
	       }else {
	    	   cliente1=servidor;
	    	   t1 = new Thread(servidor);
	       }
	        t1.start();   
	    }
	}
	
}
