package perguntas;

public class Pergunta {
	private String pergunta;
	private String[] alternativas;
	private int correta;
	
	public String getPergunta() {
		return pergunta;
	}
	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}
	public String[] getAlternativas() {
		return alternativas;
	}
	public void setAlternativas(String[] alt) {
		this.alternativas = alt;
	}
	public int getCorreta() {
		return correta;
	}
	public void setCorreta(int correta) {
		this.correta = correta;
	}
	

}
