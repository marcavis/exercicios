package estruturas;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		PilhaDinamica pilha = new PilhaDinamica();
		
		Random gerador = new Random();
		pilha.inserir(4);
		pilha.inserir(7);
		pilha.inserir(12);
		pilha.inserir(18);
		pilha.inserir(gerador.nextInt(300));
		
		//System.out.println(pilha);
		
		FilaDinamica fila = new FilaDinamica();
		fila.inserir(4);
		fila.inserir(7);
		//fila.inserir(12);
		//fila.inserir(18);
		//fila.inserir(5);
		//fila.inserir(3);
		
		System.out.println(fila.retirar());
		System.out.println(fila.retirar());
		System.out.println(fila.retirar());
		
		System.out.println(fila);
		
		System.out.println(fila.tamanho());
	}

}
