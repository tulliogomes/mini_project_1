import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
	private String endereco;
	private int porta;
	private String nomeUsuario;
	
	public Cliente(String endereco, int porta) {
		this.endereco = endereco;
		this.porta = porta;
	}
	
	public void execute() {
		try {
			Socket socket = new Socket(endereco, porta);
			
			System.out.println("Conectado!");
			
			new ThreadLeitura(socket, this).start();
			new ThreadEscrita(socket, this).start();
			
		}catch (UnknownHostException ex) {
            System.out.println("Servidor nao encontrado: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
	}
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	String getNomeUsuario() {
		return this.nomeUsuario;
	}
	
	public static void main(String[] args) {
		
		Cliente cliente = new Cliente("localhost", 8000);
		cliente.execute();
		
	}
	
	
	

}
