package arvoreBinaria;

public class Node<T> {
	private T valor;
	private Node<T> pai;
	private Node<T> esquerda, direita;
	
	public Node(T valor) {
		this.valor = valor;
		pai = null;
		esquerda = direita = null;
	}
	
	public T getValor() {
		return valor;
	}

	public void setValor(T valor) {
		this.valor = valor;
	}

	public Node<T> getPai() {
		return pai;
	}
	
	public void setEsquerda(Node<T> filho) {
		this.esquerda = filho;
	}
	
	public void setDireita(Node<T> filho) {
		this.direita = filho;
	}
	
	public Node<T> getEsquerda() {
		return esquerda;
	}

	public Node<T> getDireita() {
		return direita;
	}
	
	public void setPai(Node<T> pai) {
		this.pai = pai;
	}
	
	public String toString() {
		return valor.toString();
	}
}
