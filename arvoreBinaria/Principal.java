package arvoreBinaria;

public class Principal {
	public static void main(String[] args) throws Exception {
		ArvoreBusca<Integer> ab = new ArvoreBusca<Integer>();
		ab.insere(new Node<Integer>(30));
		ab.insere(new Node<Integer>(24));
		ab.insere(new Node<Integer>(23));
		ab.insere(new Node<Integer>(50));
		ab.insere(new Node<Integer>(40));
		ab.insere(new Node<Integer>(45));
		ab.insere(new Node<Integer>(32));
		ab.insere(new Node<Integer>(48));
		ab.insere(new Node<Integer>(46));
		ab.insere(new Node<Integer>(44));
		ab.insere(new Node<Integer>(43));
		ab.insere(new Node<Integer>(47));
		ab.insere(new Node<Integer>(26));
		
		System.out.println(ab.getRaiz());
		System.out.println(ab.getRaiz().getEsquerda());
		System.out.println(ab.getRaiz().getDireita());
		
		System.out.println(ab);
		System.out.println(ab.getRaiz().getDireita().getEsquerda().getDireita());
		ab.print();
	}
}
