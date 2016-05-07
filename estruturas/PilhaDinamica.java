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
		novo.next = topo;
		topo = novo;
		_tamanho++;
	}
	
	public int retirar() {
		if (tamanho() == 0)
			return -1;
		int retorno = topo.valor;
		topo = topo.next;
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
		while (mostra.next != null) {
			resp += mostra.valor + ",";
			mostra = mostra.next;
		}
		resp += mostra.valor;
		return resp + "]";
	}
}
