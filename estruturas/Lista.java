package estruturas;

public class Lista {
	private Node frente = null;
	private Node verso = null;
	private int _tamanho = 0;
	
	public Lista() {
		
	}
	
	public boolean estaVazia() {
		return tamanho() == 0;
	}
	
	public void inserir(int v) {
		Node novo = new Node(v);
		if (tamanho() == 0) {
			frente = novo;
			frente.next = null;
		} else {
			verso.next = novo;
		}
		verso = novo;
		_tamanho++;
	}
	
	public void inserir(int v, int indice) {
		if (tamanho() == 0 || indice >= tamanho()) {
			inserir(v);
		} else {
			Node novo = new Node(v);
			if(indice == 0){
				novo.next = frente;
				frente = novo;
			} else {
				Node atual = frente;
				for(int i = 1; i < indice; i++)
					atual = atual.next;
				novo.next = atual.next;
				atual.next = novo;
				
			}
			_tamanho++;
		}
	}
	
	public int retirar() {
		if (tamanho() == 0)
			return -1;
		int retorno = frente.valor;
		frente = frente.next;
		_tamanho--;
		if (tamanho() == 0)
			verso = frente;
		return retorno;
	}
	
	public int retirar(int indice) {
		if (indice == 0 )
			return retirar();
		if (indice >= tamanho())
			indice = tamanho()-1; //ou talvez lançar uma exceção?
		Node atual = frente;
		for(int i = 1; i < indice; i++) {
			atual = atual.next;
		}
		int retorno = atual.next.valor;
		if(atual.next.next == null)
			verso = atual;
		atual.next = atual.next.next;
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
			Node mostra = frente;
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
