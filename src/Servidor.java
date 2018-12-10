import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Servidor {
	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	private int porta;
	private Set<String> nomes = new HashSet<>();
	private Set<ThreadUsuario> threadUsuarios = new HashSet<>();
	
	public Servidor(int porta) {
		this.porta = porta;
	}
	
	public void execute() {
		try(ServerSocket serverSocket = new ServerSocket(porta)){
			
			System.out.println("Servidor aguardando na porta " + porta);
			
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println("Novo usuario conectado!");
				
				ThreadUsuario novoUsuario = new ThreadUsuario(socket, this);
				threadUsuarios.add(novoUsuario);
				novoUsuario.start();
			}
		} catch (IOException ex) {
			System.out.println("Erro no servidor: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static void main (String[] args) {
		int porta = 8000;
		
		Servidor servidor = new Servidor(porta);
		servidor.execute();
		
	}
	
	
	//Envia mensagem a todos
	void broadcast(String mensagem, ThreadUsuario receptor) {
		for (ThreadUsuario emissor : threadUsuarios) {
			if(emissor != receptor) {
				emissor.enviarMensagem(mensagem);
			}
		}
	}
	
	//Nomeia usuario
	void addNome(String nome) {
		nomes.add(nome);
	}
	
	//Disconecta usuario e a sua thread
	
	void removeUsuario(String nome, ThreadUsuario emissor) {
		boolean removido = nomes.remove(nome);
		if(removido) {
			threadUsuarios.remove(emissor);
			System.out.println("O usuario " + nome + " saiu!");
		}
	}
	
	Set<String> getNomeUsuarios(){
		return this.nomes;
	}
	
	//verifica se existe usuarios conectados.
	boolean existeUsuario() {
		return !this.nomes.isEmpty();
	}
	
	
	
	
}
