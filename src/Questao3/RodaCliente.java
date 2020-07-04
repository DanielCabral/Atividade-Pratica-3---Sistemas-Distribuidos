package Questao3;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
public class RodaCliente {
	public static void main(String args[]) throws UnknownHostException, IOException {
		/** para se conectar ao servidor, cria-se objeto Socket.
		 O primeiro parâmetro é o IP ou endereço da máquina que
		 se quer conectar e o segundo é a porta da aplicação.
		 Neste caso, usa-se o IP da máquina local (127.0.0.1)
		 e a porta da aplicação ServidorDeEco (12345). */
		Socket socket = new Socket("127.0.0.1", 12345);
		InetAddress inet = socket.getInetAddress();
		System.out.println("HostAddress = "+inet.getHostAddress());
		System.out.println("HostName = "+inet.getHostName());
		/**Cria um novo objeto Cliente com a conexão socket para que seja executado em um novo
		processo. Permitindo assim a conexão de vário clientes com o servidor.*/
		ClienteRunnable c = new ClienteRunnable(socket);
		Thread t = new Thread(c);
		t.start();
	}
}