package estruturas;

public class PilhaDinamica {
	private Node topo = null;
	private int _tamanho = 0;
	
	public PilhaDinamica() {
		
	}
	
	public boolean estaVazia() {
		return tamanho() == 0;
	}
	
	public void inserir(int v) {
		Node novo = new Node(v);
		novo.prox = topo;
		topo = novo;
		_tamanho++;
	}
	
	public int retirar() {
		if (tamanho() == 0)
			return -1;
		int retorno = topo.valor;
		topo = topo.prox;
		_tamanho--;
		return retorno;
	}
	
	public int tamanho() {
		return _tamanho;
	}
	
	@Override
	public String toString() {
		String resp = "[";
		Node mostra = topo;
		while (mostra.prox != null) {
			resp += mostra.valor + ",";
			mostra = mostra.prox;
		}
		resp += mostra.valor;
		return resp + "]";
	}
}
