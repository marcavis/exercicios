package estruturas;

public class Fila {
	private int[] elementos;
	private int verso;
	private int frente;
	
	public Fila() {
		this.verso = -1;
		this.frente = 0;
		this.elementos = new int[100];
	}
	
	public void inserir(int novo) {
		if (tamanho() < 100) {
			this.elementos[verso+1] = novo;
			this.verso++;
		}
	}
	
	public int retirar() {
		if(tamanho() == 0) {
			return -1;
		}
		this.frente++;
		return this.elementos[frente-1];
	}
	
	public boolean ehVazia() {
		return tamanho() == 0;
	}
	
	public int tamanho() {
		return this.verso - this.frente + 1;
	}
	
	public int mostraTopo() {
		if(tamanho() == 0) {
			return -1;
		}
		return this.elementos[frente];
	}
}
