package cadastro_bandas;

public class Banda {
	private String nome;
	private String estilo;
	private int integrantes;
	private boolean temCD;
	
	public Banda(String nome, String estilo, int integrantes, boolean temCD) {
		this.nome = nome;
		this.estilo = estilo;
		this.integrantes = integrantes;
		this.temCD = temCD;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}

	public int getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(int integrantes) {
		this.integrantes = integrantes;
	}

	public boolean isTemCD() {
		return temCD;
	}

	public void setTemCD(boolean temCD) {
		this.temCD = temCD;
	}
	
	public String[] toArray() {
		return new String[]{getNome(), getEstilo(), getIntegrantes()+"", isTemCD()?"X":""};
	}
	
}
