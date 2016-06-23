package estruturas;

public class Lista<T> {
	private Node<T> frente = null;
	private Node<T> verso = null;
	private int _tamanho = 0;
	
	public Lista() {
		
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
	
	public void inserir(T v, int indice) {
		if (tamanho() == 0 || indice >= tamanho()) {
			inserir(v);
		} else {
			Node novo = new Node(v);
			if(indice == 0){
				novo.next = frente;
				frente = novo;
			} else {
				Node<T> atual = frente;
				for(int i = 1; i < indice; i++)
					atual = atual.next;
				novo.next = atual.next;
				atual.next = novo;
				
			}
			_tamanho++;
		}
	}
	
	public T retirar() {
		if (tamanho() == 0)
			throw new NullPointerException("Lista já está vazia.");
		T retorno = frente.valor;
		frente = frente.next;
		_tamanho--;
		if (tamanho() == 0)
			verso = frente;
		return retorno;
	}
	
	public T retirar(int indice) {
		if (indice == 0 )
			return retirar();
		if (indice >= tamanho())
			indice = tamanho()-1; //ou talvez lançar uma exceção?
		Node<T> atual = frente;
		for(int i = 1; i < indice; i++) {
			Node<T> next = atual.next;
			atual = next;
		}
		Node<T> retirado = atual.next;
		T retorno = retirado.valor;
		if(atual.next.next == null)
			verso = atual;
		atual.next = atual.next.next;
		_tamanho--;
		return retorno;
	}
	
	public T get(int indice) {
		Node <T> atual = frente;
		for(int i = 0; i < indice; i++) {
			atual = atual.next;
		}
		return atual.valor;
	}
	
	public boolean contem(T objeto) {
		boolean ret = false;
		for(int i = 0; i < tamanho(); i++)
			if (objeto.equals(this.get(i)))
				ret = true;
		return ret;
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
				resp += mostra.valor + ", ";
				mostra = mostra.next;
			}
			resp += mostra.valor;
		}
		return resp + "]";
	}
	
	public String cantos() {
		if(tamanho() == 0)
			return "[]";
		return "[" + frente.valor + ", " + verso.valor + "]";
	}


}
