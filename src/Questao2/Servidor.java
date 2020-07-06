package Questao2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Questao3.Arquivo;

public class Servidor implements Runnable{
public Socket socketCliente;
static String listaDeArquivos;
Arquivo arquivo;
private long tamanhoPermitidoKB = 5120; 
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
	 ObjectOutputStream oss=new ObjectOutputStream(socketCliente.getOutputStream());
	 	
	 oss.writeObject(mensagem);
 }
 
 public void enviar(String mensagem) throws IOException {
	 PrintStream saida = new PrintStream(this.socketCliente.getOutputStream());
	 System.out.println("Enviando mensagem para cliente "+socketCliente.getInetAddress().getHostAddress()+" : "+mensagem);
	 mensagem="mens-"+mensagem;
	 //saida.println(mensagem);
	 ObjectOutputStream oss=new ObjectOutputStream(socketCliente.getOutputStream());
	 	
	 oss.writeObject(mensagem);
 }
 
 public void enviar() throws IOException {
	 
	 File myFile=null;
	 try {
	     // envia o arquivo (transforma em byte array)
	     myFile = new File ("src/Questao3/Teste.txt");
	     byte [] mybytearray  = new byte [(int)myFile.length()];
	     
	     FileInputStream fis = new FileInputStream(myFile);
	     FileReader file=new FileReader(myFile);
	     BufferedReader bufferedReader=new BufferedReader(file);    
	     String entrada="";
	     String linha = bufferedReader.readLine(); 
	     while(linha != null) {
	    	 entrada+=linha;
	    	 linha = bufferedReader.readLine(); 
	     }
	     entrada+=linha;
	     file.close();
	     bufferedReader.close();
	     
	     arquivo=new Arquivo();
	     arquivo.setNome("Arquivo.txt");
	     arquivo.setConteudo(entrada);
	     arquivo.setTamanhoKB(entrada.length());
	     ObjectOutputStream oss=new ObjectOutputStream(socketCliente.getOutputStream());
	 	
	     oss.writeObject(arquivo);
	     
	 }
	 catch(IOException e) {
		 System.out.println(e+" caminho: "+myFile.getAbsolutePath());
	 }
 }
 
 private void enviarArquivoServidor(){
	   if (validaArquivo()){
	    try { 
	        BufferedOutputStream bf = new BufferedOutputStream
	        (socketCliente.getOutputStream());
	 
	        byte[] bytea = serializarArquivo();
	        bf.write(bytea);
	        bf.flush();
	        bf.close();
	    } catch (UnknownHostException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	   }
	}
	 
	private byte[] serializarArquivo(){
	   try {
	      ByteArrayOutputStream bao = new ByteArrayOutputStream();
	      ObjectOutputStream ous;
	      ous = new ObjectOutputStream(bao);
	      ous.writeObject(arquivo);
	      return bao.toByteArray();
	   } catch (IOException e) {
	      e.printStackTrace();
	   }
	 
	   return null;
	}
	 
	private boolean validaArquivo(){
	   if (arquivo.getTamanhoKB() > tamanhoPermitidoKB){
	      JOptionPane.showMessageDialog(null,
	       "Tamanho máximo permitido atingido ("+(tamanhoPermitidoKB/1024)+")");
	      return false;
	   }else{
	      return true;
	   }
	}
 
}