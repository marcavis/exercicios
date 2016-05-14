package arvore;

import java.util.ArrayList;

public class Node<T> {
	private T valor;
	private Node pai;
	public ArrayList<Node> filhos;
	
	public Node(T valor) {
		this.valor = valor;
		pai = null;
		filhos = new ArrayList<Node>();
	}

	public T getValor() {
		return valor;
	}

	public void setValor(T valor) {
		this.valor = valor;
	}

	public Node getPai() {
		return pai;
	}

	public void setPai(Node pai) {
		this.pai = pai;
	}
}
