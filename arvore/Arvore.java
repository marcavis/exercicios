package arvore;

import java.util.ArrayList;

public class Arvore<T> {
	private Node<T> raiz;
	
	public Arvore(Node<T> raiz) {
		this.raiz = raiz;
	}
	
	public Node<T> getRaiz() {
		return raiz;
	}
	
	public void insere(Node<T> pai, Node<T> filho) {
		pai.filhos.add(filho);
	}
	
	@SuppressWarnings("unchecked")
	public Node<T> pai(Node<T> node) {
		if(node.getPai() == null) {
			throw new NullPointerException("Elemento não tem pai");
		}
		return node.getPai();
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList<Node> filhos(Node<T> node) {
		return node.filhos;
	}
	
	public boolean isInterno(Node<T> node) {
		return node.filhos.size() > 0;
	}
	
	public boolean isExterno(Node<T> node) {
		return !isInterno(node);
	}
	
	public boolean isRaiz(Node<T> node) {
		return node == raiz;
	}
	
	public int tamanho() {
		int cont = 0;
		return cont;
	}
	
	@SuppressWarnings("unchecked")
	public int contaDescendentes(Node<T> node) {
		if(isExterno(node)) {
			return 1;
		} else {
			int res = 0;
			for (Node<T> filho : node.filhos)
				res += contaDescendentes(filho);
			return res;
		}
	}
	
	public int soma(ArrayList<Integer> lista) {
		int res = 0;
		for(int i : lista)
			res += i;
		return res;
	}
}
