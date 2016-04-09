package calculadora1;

public class Principal {
	public static void main(String[] args) {
		Calculadora calc = new Calculadora();
		
		calc.inserir(3.0);
		calc.inserir(7.0);
		calc.inserir('-');
		calc.inserir('+');
		calc.inserir(23.0);
		calc.inserir(0.5);
		
		System.out.println(calc);
	}
}
