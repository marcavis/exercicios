package estruturas;

public class NodeDuplo<T> {
	public T valor;
	public NodeDuplo prev, next;
	
	public NodeDuplo(T v) {
		this.valor = v;
	}
}
