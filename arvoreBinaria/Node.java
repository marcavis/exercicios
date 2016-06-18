package arvoreBinaria;

public class Node<T extends Comparable> {
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
	
	public int quantFilhos() {
		return (this.esquerda==null?0:1) + (this.direita==null?0:1);
	}
	
	public String toString() {
		return valor.toString();
	}

	public int compareTo(Node<T> outro) {
		return valor.compareTo(outro.valor);
	}

	public void removeFilho(Node<T> atual) {
		if (atual == esquerda)
			this.esquerda = null;
		else
			this.direita = null;
	}
	
	public void trocaFilho(Node<T> atual, Node<T> novo) {
		System.out.println(this + "t " + atual + "a " + novo + "n ");
		if (atual == esquerda) {
			this.esquerda = novo;
			if(novo != atual.getEsquerda() && novo.getEsquerda() == null)
				this.esquerda.setEsquerda(atual.getEsquerda());
			if(novo != atual.getDireita() && novo.getDireita() == null)
				this.esquerda.setDireita(atual.getDireita());
		} else {
			this.direita = novo;
			if(novo != atual.getEsquerda() && novo.getEsquerda() == null)
				this.direita.setEsquerda(atual.getEsquerda());
			if(novo != atual.getDireita() && novo.getDireita() == null)
				this.direita.setDireita(atual.getDireita());
		}
		novo.setPai(atual.getPai());
	}
	
	public int nivel() {
		int n = 0;
		Node<T> atual = this;
		while(atual.getPai() != null) {
			atual = atual.getPai();
			n++;
		}
		return n;
	}

	
}
