package arvore;

import java.util.ArrayList;

public class Principal {

	public static void main(String[] args) {
		Node<String> a = new Node("A");
		Node<String> b = new Node("B");
		Node<String> c = new Node("C");
		Node<String> d = new Node("D");
		Node<String> e = new Node("E");
		
		Arvore<String> arv = new Arvore(a);
		arv.insere(a, b);
		arv.insere(b, c);
		arv.insere(a, d);
		arv.insere(d, e);
		arv.insere(b, new Node("F"));
		arv.insere(a, new Node("G"));
		ArrayList<Node> sai;
		sai = arv.elementos();
		System.out.println(arv);
		System.out.println(arv.atualiza(b, e));

		System.out.println(arv);
	}
}
