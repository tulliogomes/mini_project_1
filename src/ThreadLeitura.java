import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ThreadLeitura extends Thread {
	private BufferedReader reader;
	private Socket socket;
	private Cliente cliente;
	
	public ThreadLeitura(Socket socket, Cliente cliente) {
		this.socket = socket;
		this.cliente = cliente;
		
		try {
			InputStream entrada = socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(entrada));
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			System.out.println("Erro ao pegar mensagem: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			try {
				
				DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
				LocalDateTime datalocal = LocalDateTime.now();
				
				String resposta = reader.readLine();
				System.out.println("\n" + resposta);
				
				if (cliente.getNomeUsuario() != null) {
					System.out.println("[" + cliente.getEndereco() + " - " + cliente.getPorta() + " - " + data.format(datalocal) + " - " + cliente.getNomeUsuario() + "]: ");
				}
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				System.out.println("Erro ao ler do Servidor: " + ex.getMessage());
				ex.printStackTrace();
				break;
			}
		}
	}
	
	

}
