package pizzaria;

import estruturas.Lista;

public class Receita {
	private Lista<Casa> ingredientes = new Lista<Casa>();
	public Receita(Casa[] casas) {
		for (Casa casa : casas) {
			ingredientes.inserir(casa);
		}
	}
	
	public Lista<Casa> mostraIngredientes() {
		return ingredientes;
	}
}
