package arvore;

public class Principal {

	public static void main(String[] args) {
		Node<String> a = new Node("A");
		Node<String> b = new Node("B");
		Node<String> c = new Node("C");
		Node<String> d = new Node("D");
		Node<String> e = new Node("E");
		
		Arvore<String> arv = new Arvore(a);
		System.out.println(a.getValor());
		
	}
}
