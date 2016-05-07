package estruturas;

public class ListaDupla {
	private NodeDuplo frente = null;
	private NodeDuplo verso = null;
	private int _tamanho = 0;
	
	public ListaDupla() {
		
	}
	
	public boolean estaVazia() {
		return tamanho() == 0;
	}
	
	public void inserir(int v) {
		NodeDuplo novo = new NodeDuplo(v);
		if (tamanho() == 0) {
			frente = novo;
			frente.prev = null;
			frente.next = null;
		} else {
			verso.next = novo;
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
		NodeDuplo atual = frente;
		for(int i = 1; i < indice; i++) {
			atual = atual.next;
		}
		int retorno = atual.next.valor;
		if(atual.next.next == null)
			verso = atual;
		atual.next.next.prev = atual;
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
			NodeDuplo mostra = frente;
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
