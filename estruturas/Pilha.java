package estruturas;

public class Pilha {
	private int[] elementos;
	private int topo;
	
	public Pilha() {
		this.topo = -1;
		this.elementos = new int[100];
	}
	
	public void inserir(int novo) {
		this.elementos[topo+1] = novo;
		this.topo++;
	}
	
	public int retirar() {
		this.topo--;
		return this.elementos[topo+1];
	}
	
	public boolean ehVazia() {
		return tamanho() == 0;
	}
	
	public int tamanho() {
		return this.topo + 1;
	}
	
	public int mostraTopo() {
		if(tamanho() == 0) {
			return -1;
		}
		return this.elementos[topo];
	}
}
