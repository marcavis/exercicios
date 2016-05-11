package cadastro_bandas;

public class Instrumento {
	public String nome;
	public String tipo;
	
	public Instrumento(String nome, String tipo) {
		this.nome = nome;
		this.tipo = tipo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String[] toArray() {
		return new String[]{getNome(), getTipo()};
	}
}
