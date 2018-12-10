import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ThreadEscrita extends Thread{
	private PrintWriter writer;
	private Socket socket;
	private Cliente cliente;
	
	public ThreadEscrita(Socket socket, Cliente cliente) {
		this.socket = socket;
		this.cliente = cliente;
		
		try {
			OutputStream saida = socket.getOutputStream();
			writer = new PrintWriter(saida, true);
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			System.out.println("Erro ao pegar a mensagem: " + ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	
	public void run() {
		
		//System.out.println("Escreva seu nome: ");
		//Scanner scanner = new Scanner(System.in);
		
		Console console = System.console();
		
		String nomeUsuario = console.readLine("Escreva seu nome: ");
		//scanner.nextLine();
		
		cliente.setNomeUsuario(nomeUsuario);
		writer.println(nomeUsuario);
		
		DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
		LocalDateTime datalocal = LocalDateTime.now();
		
		String texto;
		
		do {
			texto = console.readLine("[" + cliente.getEndereco() + " - " + cliente.getPorta() + " - " + data.format(datalocal) + " - " + nomeUsuario + "]: ");
			//scanner.nextLine(); 
			writer.println(texto);
		} while(!texto.equals("bye"));
		
		try {
			socket.close();
		}catch (IOException ex) {
			System.out.println("Erro ao enviar a mensagem: " + ex.getMessage());
		}
	}
	
	
	
	
	
}
