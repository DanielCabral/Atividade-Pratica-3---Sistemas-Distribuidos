package Questao3;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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
					Object rcv;
					Socket s=cliente;
					
					while(true){
						ObjectInputStream is=new ObjectInputStream(s.getInputStream());
						rcv = is.readObject();
						//rcv = s.nextLine();
						System.out.println(rcv);
						System.out.println(rcv.getClass());
						if(rcv instanceof Arquivo) {
							Arquivo arquivo= (Arquivo)  rcv;
							System.out.println(arquivo.getNome());
							System.out.println(arquivo.getConteudo());
						}
						is=null;
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
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
