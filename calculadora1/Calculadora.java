package calculadora1;


public class Calculadora {
	private Pilha<Double> numeros = new Pilha<Double>();
	private Pilha<Character> operadores = new Pilha<Character>();;
	
	public void inserir(Double num) {
		numeros.inserir(num);
	}
	
	public void inserir(Character op) {
		operadores.inserir(op);
	}
	  
	@Override
	public String toString() {
		return numeros.toString() + "\n" + operadores.toString();
	}
}
