package arvoreBinaria;

public class Arvore<T> {
	private Node<T> raiz;
	
	public Arvore(Node<T> raiz) {
		this.raiz = raiz;
	}
	
	public Node<T> getRaiz() {
		return raiz;
	}
	
	public void insere(Node<T> pai, Node<T> filho) throws Exception {
		if(raiz == null || pai == null) {
			raiz = filho;
		} else {
			if (pai.getEsquerda() == null) {
				pai.setEsquerda(filho);
			} else if(pai.getDireita() == null) {
				pai.setDireita(filho);
			} else {
				throw new Exception("Nó já tem dois filhos");
			}
		}
	}
	
}
