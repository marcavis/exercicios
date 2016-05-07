package estruturas;

public class FilaDinamica {
	private Node frente = null;
	private Node verso = null;
	private int _tamanho = 0;
	
	public FilaDinamica() {
		
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
	
	public int retirar() {
		if (tamanho() == 0)
			return -1;
		int retorno = frente.valor;
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
			Node mostra = frente;
			while (mostra.next != null) {
				resp += mostra.valor + ",";
				mostra = mostra.next;
			}
			resp += mostra.valor;
		}
		return resp + "]";
	}
	
	
}
