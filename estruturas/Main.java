package estruturas;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
//		PilhaDinamica pilha = new PilhaDinamica();
//		
//		Random gerador = new Random();
//		pilha.inserir(4);
//		pilha.inserir(7);
//		pilha.inserir(12);
//		pilha.inserir(18);
//		pilha.inserir(gerador.nextInt(300));
//		
//		//System.out.println(pilha);
//		
//		FilaDinamica fila = new FilaDinamica();
//		fila.inserir(4);
//		fila.inserir(7);
//		//fila.inserir(12);
//		//fila.inserir(18);
//		//fila.inserir(5);
//		//fila.inserir(3);
		
		ListaCircular l = new ListaCircular();
		l.inserir(2);
		l.inserir(4);
		l.inserir(14);
		l.inserir(42);
		l.inserir(41);
		l.inserir(43);
		l.inserir(44);
		l.inserir(46);
		System.out.println(l.retirar(5));
		
		System.out.println(l.cantos());
		System.out.println(l);
		System.out.println(l.frente.prev.valor);
		System.out.println(l.verso.next.valor);
	}

}
