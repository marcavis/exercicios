package pizzaria;

import estruturas.Lista;

public class Jogador {
	private Lista<Casa> ingredientes = new Lista<Casa>();
	private Lista<Boolean> obtidos = new Lista<Boolean>(); 
	private NomeDeJogador nome;
	private int posicao;
	
	public Jogador(NomeDeJogador n, Receita r) {
		nome = n;
		ingredientes = r.mostraIngredientes();
		posicao = 0;
	}
	
	public void jogar() {
		int dado = (int)(Math.random()*6+1);
		posicao += dado % Principal.tabuleiro.tamanho();
		System.out.println(nome + ": Rolei um " + dado + ", parei em pos. " + posicao + ", " + Principal.tabuleiro.get(posicao));
		tratarCasa(Principal.tabuleiro.get(posicao));
		Principal.jogadores.proximo();
	}
	
	public void tratarCasa(Casa casa) {
		switch (casa) {
		case PERDE_TUDO:
			System.out.println(nome + " perdeu tudo!");
			obtidos = new Lista<Boolean>(); 
			break;
		case SORTE_OU_AZAR:
			//comprarCarta();
			break;
		default:
			if(ingredientes.contem(casa))
				obtidos.i
			break;
		}
		
	}
}
