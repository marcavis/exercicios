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
		filho.setPai(pai);
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
		return contaDescendentes(raiz);
	}
	
	@SuppressWarnings("unchecked")
	public int contaDescendentes(Node<T> node) {
		if(isExterno(node)) {
			return 1;
		} else {
			int res = 1;
			for (Node<T> filho : node.filhos)
				res += contaDescendentes(filho);
			return res;
		}
	}
	
	public ArrayList<Node> elementos() {
		ArrayList<Node> lista = new ArrayList<Node>();
		return _elementos(raiz, lista);
	}
	
	private ArrayList<Node> _elementos(Node<T> node, ArrayList<Node> lista) {
		ArrayList<Node> ret = new  ArrayList<Node>();
		if(isExterno(node)) {
			ret.add(node);
			return ret;
		} else {
			ret.add(node);
			for (Node<T> filho : node.filhos)
				ret.addAll(_elementos(filho, lista));
			return ret;
		}
		
	}
	
	public T atualiza(Node<T> antigo, Node<T> novo) {
		int i = 0;
		for (Node<T> filho : antigo.getPai().filhos) {
			if(filho == antigo) {
				break;
			}
			i++;
		}
		antigo.getPai().filhos.set(i, novo);
		return antigo.getValor();
	}
	
	public int soma(ArrayList<Integer> lista) {
		int res = 0;
		for(int i : lista)
			res += i;
		return res;
	}

	@Override
	public String toString() {
		ArrayList<Node> itens = elementos();
		String res = "[";
		for (int i = 0; i < tamanho() - 1; i++)
			res += itens.get(i) + ", ";
		res += itens.get(tamanho()-1) + "]";
		return res;
	}
	
	
}
