package calculadora1;

public class Node<T> {
	public T valor;
	public Node<T> prox;
	
	public Node(T v) {
		this.valor = v;
	}
}
