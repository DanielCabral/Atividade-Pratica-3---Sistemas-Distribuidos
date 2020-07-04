package Questao2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Servidor implements Runnable{
public Socket socketCliente;
static String listaDeArquivos;
public static int cont = 0;

public Servidor(Socket cliente, String listaDeArquivos) throws IOException{
	this.socketCliente = cliente;
	this.listaDeArquivos=listaDeArquivos;
	enviar(listaDeArquivos);
	enviar();
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
	 //PrintStream saida = new PrintStream(this.cliente2.getOutputStream());
	 //System.out.println("Enviando mensagem para cliente "+cliente2.getInetAddress().getHostAddress()+" : "+mensagem);
	 //saida.println(mensagem);
 }
 
 public void enviar(String mensagem) throws IOException {
	 PrintStream saida = new PrintStream(this.socketCliente.getOutputStream());
	 System.out.println("Enviando mensagem para cliente "+socketCliente.getInetAddress().getHostAddress()+" : "+mensagem);
	 mensagem="mens-"+mensagem;
	 saida.println(mensagem);
 }
 
 public void enviar() throws IOException {
	 
	 PrintStream saida = new PrintStream(this.socketCliente.getOutputStream());
	 File myFile=null;
	 try {
	     // envia o arquivo (transforma em byte array)
	     myFile = new File ("src/Questao3/Teste.txt");
	     byte [] mybytearray  = new byte [(int)myFile.length()];
	     
	     FileInputStream fis = new FileInputStream(myFile);
	     BufferedInputStream bis = new BufferedInputStream(fis);
	     bis.read(mybytearray,0,mybytearray.length);
	     OutputStream os = socketCliente.getOutputStream();
	     System.out.println("Enviando... "+mybytearray);
	     
	     byte a[]=new String("file-Arquivo.txt-").getBytes();
	     byte[] c = new byte[mybytearray.length+a.length];
	     System.arraycopy(a, 0, c, 0, a.length);
	     System.arraycopy(mybytearray, 0, c, a.length, mybytearray.length);
	     os.write(c,0,c.length);
	     //saida.println(mybytearray);
	 }
	 catch(IOException e) {
		 System.out.println(e+" caminho: "+myFile.getAbsolutePath());
	 }
 }
 
}