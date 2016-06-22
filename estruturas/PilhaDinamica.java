package estruturas;

public class PilhaDinamica<T> {
	private Node<T> topo = null;
	private int _tamanho = 0;
	
	public PilhaDinamica() {
		
	}
	
	public boolean estaVazia() {
		return tamanho() == 0;
	}
	
	public void inserir(T v) {
		Node<T> novo = new Node<T>(v);
		novo.next = topo;
		topo = novo;
		_tamanho++;
	}
	
	public T retirar() {
		if (tamanho() == 0)
			throw new NullPointerException("Pilha já está vazia.");
		T retorno = topo.valor;
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
		Node<T> mostra = topo;
		while (mostra.next != null) {
			resp += mostra.valor + ",";
			mostra = mostra.next;
		}
		resp += mostra.valor;
		return resp + "]";
	}
}
