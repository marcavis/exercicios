package pizzaria;

import estruturas.Lista;

public class Jogador {
	private Lista<Casa> ingredientes = new Lista<Casa>();
	private Lista<Boolean> obtidos = new Lista<Boolean>(); 
	private NomeDeJogador nome;
	
	public Jogador(NomeDeJogador n, Receita r) {
		nome = n;
		ingredientes = r.mostraIngredientes();
	}
	
	
	public void jogar() {
		int dado = (int)(Math.random()*6+1);
		System.out.println(nome + ": Rolei um " + dado + "!");
		Principal.jogadores.proximo();
	}
}
