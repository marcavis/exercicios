package estruturas;

public class FilaDinamica<T> {
	private Node<T> frente = null;
	private Node<T> verso = null;
	private int _tamanho = 0;
	
	public FilaDinamica() {
		
	}
	
	public boolean estaVazia() {
		return tamanho() == 0;
	}
	
	public void inserir(T v) {
		Node<T> novo = new Node<T>(v);
		if (tamanho() == 0) {
			frente = novo;
			frente.next = null;
		} else {
			verso.next = novo;
		}
		verso = novo;
		_tamanho++;
	}
	
	public T retirar() {
		if (tamanho() == 0)
			throw new NullPointerException("Fila já está vazia.");
		T retorno = frente.valor;
		frente = frente.next;
		_tamanho--;
		return retorno;
	}
	
	public int tamanho() {
		return _tamanho;
	}

	@Override
	public String toString() {
		String resp = "[";
		if (tamanho() > 0)
		{
			Node<T> mostra = frente;
			while (mostra.next != null) {
				resp += mostra.valor + ",";
				mostra = mostra.next;
			}
			resp += mostra.valor;
		}
		return resp + "]";
	}
	
	
}
