package Questao3;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
public class Servidor implements Runnable{
public Socket socketCliente;
public static int cont = 0;
Socket cliente2;

public Servidor(Socket cliente){
	this.socketCliente = cliente;
}

public void setCliente2(Socket cliente2) {
	this.cliente2 = cliente2;
}

public Socket getCliente() {
	return socketCliente;
}
/* A classe Thread, que foi instancia no servidor, implementa Runnable.
 Então você terá que implementar sua lógica de troca de mensagens dentro deste método 'run'. */
 public void run(){
	System.out.println("Conexao "+Servidor.cont+" com o cliente " +
	this.socketCliente.getInetAddress().getHostAddress() +"/" +this.socketCliente.getInetAddress().getHostName());
	try {
		Scanner s = null;
		s = new Scanner(this.socketCliente.getInputStream());
		String rcv;
		 //Exibe mensagem no console
		while(s.hasNextLine()){
			rcv = s.nextLine();
			if (rcv.equalsIgnoreCase("fim"))
				break;
			else
				System.out.println(rcv);
			if(cliente2 != null) {
				sendMessage(rcv);
			}
		}
		//Finaliza scanner e socket
		s.close();
		System.out.println("Fim do cliente "+this.socketCliente.getInetAddress());
		this.socketCliente.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
 
 public void sendMessage(String mensagem) throws IOException {
	 PrintStream saida = new PrintStream(this.cliente2.getOutputStream());
	 System.out.println("Enviando mensagem para cliente "+cliente2.getInetAddress().getHostAddress()+" : "+mensagem);
	 saida.println(mensagem);
 }
 
}