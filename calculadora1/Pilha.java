package calculadora1;

public class Pilha<T> {
	private Node<T> topo = null;
	private int _tamanho = 0;
	
	public Pilha() {
		
	}
	
	public boolean estaVazia() {
		return tamanho() == 0;
	}
	
	public void inserir(T v) {
		Node<T> novo = new Node<T>(v);
		novo.prox = topo;
		topo = novo;
		_tamanho++;
	}
	
	public T retirar() {
		if (tamanho() == 0)
			return null;
		T retorno = topo.valor;
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
