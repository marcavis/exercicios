package arvoreBinaria;

import java.util.ArrayList;

public class ArvoreBusca<T extends Comparable> {
	private Node<T> raiz;
	
	public Node<T> getRaiz() {
		return raiz;
	}
	
	public void insere(Node<T> filho, Node<T> atual) throws Exception {
		if(filho.compareTo(atual) < 0) {
			if(atual.getEsquerda()==null) {
				atual.setEsquerda(filho);
				filho.setPai(atual);
			} else {
				insere(filho, atual.getEsquerda());
			}
		} else if(filho.compareTo(atual) > 0) {
			if(atual.getDireita()==null) {
				atual.setDireita(filho);
				filho.setPai(atual);
			} else {
				insere(filho, atual.getDireita());
			}
		} else {
			throw new Exception("Elemento já existe na árvore");
		}
	}
	
	public void insere(Node<T> filho) throws Exception {
		if(raiz == null)
			raiz = filho;
		else
			insere(filho, raiz);
	}
	
	public void remove(Node<T> n) throws Exception {
		if(n.compareTo(raiz) == 0) {
			if(raiz.quantFilhos() == 0) {
				raiz = null;
			} else if (raiz.getEsquerda() != null && raiz.getDireita() == null) {
				raiz = raiz.getEsquerda();
			} else if (raiz.getEsquerda() == null && raiz.getDireita() != null) {
				raiz = raiz.getDireita();
			} else {
				Node<T> novaRaiz = maisProximo(raiz);
				remove(novaRaiz, raiz);
				if(novaRaiz != raiz.getEsquerda())
					novaRaiz.setEsquerda(raiz.getEsquerda());
				if(novaRaiz != raiz.getDireita())
					novaRaiz.setDireita(raiz.getDireita());
				novaRaiz.setPai(null);
				raiz = novaRaiz;
			}
		} else {
			if(n.compareTo(raiz) < 0)
				remove(n, raiz.getEsquerda());
			else
				remove(n, raiz.getDireita());
		}
	}

	public void remove(Node<T> n, Node<T> atual) throws Exception {
		if(atual == null)
			throw new Exception("Elemento não encontrado");
		if(n.compareTo(atual) == 0) {
			if(atual.quantFilhos() == 0) {
				atual.getPai().removeFilho(atual);
				atual = null;
			} else if (atual.getEsquerda() != null && atual.getDireita() == null) {
				//so tem um filho, a esquerda
				atual.getPai().trocaFilho(atual, atual.getEsquerda());
			} else if (atual.getEsquerda() == null && atual.getDireita() != null) {
				//so tem um filho, a direita
				atual.getPai().trocaFilho(atual, atual.getDireita());
			} else {
				Node<T> substituto = maisProximo(atual);
				remove(substituto, atual);
				atual.getPai().trocaFilho(atual, substituto);
			}
		} else {
			if(n.compareTo(atual) < 0)
				remove(n, atual.getEsquerda());
			else
				remove(n, atual.getDireita());
		}
	}
	
	public Node<T> maisProximo(Node<T> n) throws Exception {
		Node<T> atual = n.getEsquerda();
		//alterar depois pra pegar da sub-arvore maior
		while(atual.quantFilhos() > 0) {
			if(atual.getDireita() != null) {
				atual = atual.getDireita();
			}
		}
		return atual;
	}
	
	public ArrayList<Node<T>> elementos() {
		return _elementos(raiz);
	}
	
	private ArrayList<Node<T>> _elementos(Node<T> node) {
		ArrayList<Node<T>> ret = new  ArrayList<Node<T>>();
		if(node.quantFilhos() == 0) {
			ret.add(node);
			return ret;
		} else {
			ret.add(node);
			if(node.getEsquerda() != null)
				ret.addAll(_elementos(node.getEsquerda()));
			if(node.getDireita() != null)
				ret.addAll(_elementos(node.getDireita()));
			return ret;
		}
	}
	
	public int tamanho() {
		return contaDescendentes(raiz);
	}
	
	public int contaDescendentes(Node<T> node) {
		if(node.quantFilhos() == 0) {
			return 1;
		} else {
			int res = 1;
			if(node.getEsquerda() != null)
				res += contaDescendentes(node.getEsquerda());
			if(node.getDireita() != null)
				res += contaDescendentes(node.getDireita());
			return res;
		}
	}
	
	public String toString() {
		ArrayList<Node<T>> itens = elementos();
		String res = "[";
		for (int i = 0; i < tamanho() - 1; i++)
			res += itens.get(i) + ", ";
		res += itens.get(tamanho()-1) + "]";
		return res;
	}
	
	public void print() {
		ArrayList<Node<T>> itens = elementos();
		for (int i = 0; i < tamanho(); i++) {
			String indent = "";
			for (int j = 0; j < itens.get(i).nivel(); j++) {
				indent += "___";
			}
			System.out.println(indent + itens.get(i) + " pai " + itens.get(i).getPai());
		}
	}
}
