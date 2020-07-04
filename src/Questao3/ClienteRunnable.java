package Questao3;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
public class ClienteRunnable implements Runnable{
private Socket cliente;
private boolean conexao = true;

public ClienteRunnable(Socket c){
	this.cliente = c;
}

public void run() {
	try {
		PrintStream saida;
		System.out.println("O cliente conectou ao servidor");
		//Prepara para leitura do teclado
		Scanner teclado = new Scanner(System.in);
		//Cria objeto para enviar a mensagem ao servidor
		saida = new PrintStream(this.cliente.getOutputStream());
		//Envia mensagem ao servidor
		String snd;
		
		//Escutar servidor
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Scanner s = null;
					s = new Scanner(cliente.getInputStream());
					String rcv;
					 //Exibe mensagem no console
					while(s.hasNextLine()){
						rcv = s.nextLine();
						
					    
						if(rcv instanceof String) {
							System.out.println("rcv: "+rcv);
							String conteudo[] = rcv.split("-");
							String tipoDeMensagem= "";
							String content="";
							String nomeArquivo="";
							
							if(conteudo.length > 0) {
								System.out.println(conteudo.length);
								tipoDeMensagem = conteudo[0];							
								content= tipoDeMensagem.equals("file")?conteudo[2]:conteudo[1];
								nomeArquivo = tipoDeMensagem.equals("file")?conteudo[1]:"";
							}
							
							if(tipoDeMensagem.equals("file")) {
									System.out.println("Gravando arquivo "+nomeArquivo+" com conteudo: "+content);
								 	FileOutputStream fos = new FileOutputStream("src/Questao3/"+nomeArquivo);
								    BufferedOutputStream bos = new BufferedOutputStream(fos);
	
								    bos.write(content.getBytes());							    
								    bos.close();
								    fos.close();
							}
							
							System.out.println("Mensagem recebida...");
							String message= (String) rcv;
							if (message.equalsIgnoreCase("fim"))
								break;
							else
								System.out.println(rcv);
						}

					}
					//Finaliza scanner e socket
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		t.start();
		
		while(conexao){
			System.out.println("Digite uma mensagem: ");
			snd = teclado.nextLine();
			if (snd.equalsIgnoreCase("fim"))
			conexao = false;
			else
			System.out.println(snd);
			saida.println(snd);
		}
			saida.close();
			teclado.close();
			cliente.close();
			System.out.println("Cliente finaliza conexão.");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
