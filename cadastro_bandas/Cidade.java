package cadastro_bandas;

public class Cidade {
	private String nome;
	private String uf;
	
	public String getNome() {
		return nome;
	}
	
	public Cidade(String nome, String uf) {
		this.nome = nome;
		this.uf = uf;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getUf() {
		return uf;
	}
	
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public String[] toArray() {
		return new String[]{getNome(), getUf()};
	}
}
