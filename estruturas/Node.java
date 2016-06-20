package estruturas;

public class Node<T> {
	public T valor;
	public Node<T> next;
	
	public Node(T v) {
		this.valor = v;
	}
}
