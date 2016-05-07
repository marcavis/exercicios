package estruturas;

public class ListaCircular {
	public NodeDuplo frente = null;
	public NodeDuplo verso = null;
	private int _tamanho = 0;
	
	public ListaCircular() {
		
	}
	
	public boolean estaVazia() {
		return tamanho() == 0;
	}
	
	public void inserir(int v) {
		NodeDuplo novo = new NodeDuplo(v);
		if (tamanho() == 0) {
			frente = novo;
			frente.prev = novo;
			frente.next = novo;
		} else {
			verso.next = novo;
			novo.next = frente;
			novo.prev = verso;
		}
		verso = novo;
		_tamanho++;
	}
	
	public void inserir(int v, int indice) {
		if (tamanho() == 0 || indice >= tamanho()) {
			inserir(v);
		} else {
			NodeDuplo novo = new NodeDuplo(v);
			if(indice == 0){
				novo.prev = verso;
				novo.next = frente;
				frente.prev = novo;
				frente = novo;
			} else {
				NodeDuplo atual = frente;
				for(int i = 1; i < indice; i++) {
					atual = atual.next;
				}
				novo.next = atual.next;
				novo.prev = atual;
				atual.next.prev = novo;
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
		frente.prev = verso;
		verso.next = frente;
		_tamanho--;
		if (tamanho() == 0) {
			verso = null;
			frente = null;
		}
		return retorno;
	}
	
	public int retirar(int indice) {
		if (indice == 0 )
			return retirar();
		if (indice >= tamanho())
			indice = tamanho()-1; //ou talvez lançar uma exceção?
		System.out.println("jj");
		NodeDuplo atual = frente;
		int i = 1;
		for(i = 1; i < indice; i++) {
			atual = atual.next;
		}
		int retorno = atual.next.valor;
		atual.next.next.prev = atual;
		atual.next = atual.next.next;
		if(i == tamanho()-1) {
			verso = verso.prev;
		}
		frente.prev = verso;
		verso.next = frente;
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
			NodeDuplo mostra = frente;
			for (int i = 0; i < tamanho()-1; i++) {
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
