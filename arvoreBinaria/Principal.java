package arvoreBinaria;

public class Principal {
	public static void main(String[] args) throws Exception {
		ArvoreBusca<Integer> ab = new ArvoreBusca<Integer>();
		ab.insere(new Node<Integer>(30));
		ab.insere(new Node<Integer>(24));
		ab.insere(new Node<Integer>(22));
		ab.insere(new Node<Integer>(23));
		ab.insere(new Node<Integer>(50));
		ab.insere(new Node<Integer>(40));
		Node<Integer> f = new Node<Integer>(45);
		ab.insere(f);
		//ab.insere(new Node<Integer>(32));
		//ab.insere(new Node<Integer>(48));
		//ab.insere(new Node<Integer>(46));
		//ab.insere(new Node<Integer>(44));
		//ab.insere(new Node<Integer>(43));
		Node<Integer> g = new Node<Integer>(47);
		ab.insere(g);
		ab.insere(new Node<Integer>(26));
		
		ab.remove(new Node<Integer>(24));
		
		System.out.println(ab.getRaiz());
		System.out.println(ab.getRaiz().getEsquerda());
		System.out.println(ab.getRaiz().getDireita());
		
		System.out.println(ab);
		ab.print();
	}
}
