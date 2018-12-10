import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadUsuario extends Thread {
	private Socket socket;
	private Servidor servidor;
	private PrintWriter writer;

	public ThreadUsuario(Socket socket, Servidor servidor) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.servidor = servidor;
	}

	public void run() {
		try {
			
			
			
			InputStream entrada = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entrada));
						
			OutputStream saida = socket.getOutputStream();
			writer = new PrintWriter(saida, true);
			
			listaUsuarios();			
			
			String nome = reader.readLine();
			servidor.addNome(nome);
			
			String msgServidor = "Novo usuario Online: " + nome;
			servidor.broadcast(msgServidor, this);
			
			String msgCliente;
			
			do {
				msgCliente = reader.readLine();
				msgServidor = "[" + nome + "]: " + msgCliente;
				servidor.broadcast(msgServidor, this);
			
			}while(!msgCliente.equals("bye"));
			
			
			
			servidor.removeUsuario(nome, this);
			socket.close();
			
			msgServidor = nome + " saiu!";
			servidor.broadcast(msgServidor, this);
			
		} catch (IOException ex) {
			System.out.println("Erro na ThreadUsuario: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//envia lista de usuarios para quem entra na sala
	void listaUsuarios() {
		if (servidor.existeUsuario()) {
			writer.println("Usuarios online: " + servidor.getNomeUsuarios());
		} else {
			writer.println("Nao ha usuarios conectados!");
		}
	}
	
	//envia mensagem ao cliente	
	public void enviarMensagem(String mensagem) {
		// TODO Auto-generated method stub
		writer.println(mensagem);
		
	}


}
