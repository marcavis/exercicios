package pizzaria;

import estruturas.Lista;
import estruturas.ListaCircular;
import estruturas.PilhaDinamica;

public class Principal {
	public static ListaCircular<Casa> tabuleiro =	new ListaCircular<Casa>();
	public static ListaCircular<Jogador> jogadores;
	public static PilhaDinamica<Carta> cartas;
	public static boolean jogoAcabou = false;
	
	public static void main(String[] args) {
		preencheTabuleiro();
		
		Receita portuguesa = new Receita(new Casa[]{Casa.OVOS, Casa.CALABRESA, Casa.QUEIJO, Casa.PRESUNTO, Casa.CEBOLA});
		Receita vegetariana = new Receita(new Casa[]{Casa.BROCOLIS, Casa.AZEITONA, Casa.QUEIJO, Casa.MILHO, Casa.CEBOLA});
		Receita calabresa = new Receita(new Casa[]{Casa.TOMATE, Casa.AZEITONA, Casa.QUEIJO, Casa.CALABRESA, Casa.CEBOLA});
		Receita frango = new Receita(new Casa[]{Casa.TOMATE, Casa.FRANGO, Casa.QUEIJO, Casa.CATUPIRY, Casa.CEBOLA});
		Receita presunto = new Receita(new Casa[]{Casa.TOMATE, Casa.PRESUNTO, Casa.QUEIJO, Casa.CATUPIRY, Casa.CEBOLA});
		Lista<Receita> receitas = new Lista<Receita>();
		receitas.inserir(portuguesa);
		receitas.inserir(vegetariana);
		receitas.inserir(calabresa);
		receitas.inserir(frango);
		receitas.inserir(presunto);
		
		System.out.println(tabuleiro);
		
		jogadores = new ListaCircular<Jogador>();
		for(int i = 0; i<5; i++) { 
			Jogador j = new Jogador(NomeDeJogador.values()[i], receitas.retirar());
			jogadores.inserir(j);
		}
		
		int rodada = 0;
		do {
			System.out.println("rodada " + rodada);
			for(int i = 0; i<5; i++) {
				jogadores.mostraAtual().jogar();
				jogadores.proximo();
			}
			rodada++;
			System.out.println("");
		} while (!jogoAcabou && rodada < 10);
	}
	
	public static void preencheTabuleiro() {
		tabuleiro.inserir(Casa.PERDE_TUDO);
		tabuleiro.inserir(Casa.CEBOLA);
		tabuleiro.inserir(Casa.SORTE_OU_AZAR);
		tabuleiro.inserir(Casa.CALABRESA);
		tabuleiro.inserir(Casa.QUEIJO);
		tabuleiro.inserir(Casa.FRANGO);
		tabuleiro.inserir(Casa.SORTE_OU_AZAR);
		tabuleiro.inserir(Casa.TOMATE);
		tabuleiro.inserir(Casa.OVOS);
		
		tabuleiro.inserir(Casa.AZEITONA);
		tabuleiro.inserir(Casa.ABACAXI);
		tabuleiro.inserir(Casa.SORTE_OU_AZAR);
		tabuleiro.inserir(Casa.MILHO);
		tabuleiro.inserir(Casa.CATUPIRY);
		tabuleiro.inserir(Casa.SORTE_OU_AZAR);
		tabuleiro.inserir(Casa.PRESUNTO);
		tabuleiro.inserir(Casa.TOMATE);
		tabuleiro.inserir(Casa.MILHO);
		
		tabuleiro.inserir(Casa.ABACAXI);
		tabuleiro.inserir(Casa.QUEIJO);
		tabuleiro.inserir(Casa.SORTE_OU_AZAR);
		tabuleiro.inserir(Casa.BROCOLIS);
		tabuleiro.inserir(Casa.ABACAXI);
		tabuleiro.inserir(Casa.OVOS);
		tabuleiro.inserir(Casa.CEBOLA);
		tabuleiro.inserir(Casa.CALABRESA);
		tabuleiro.inserir(Casa.SORTE_OU_AZAR);
		
		tabuleiro.inserir(Casa.ABACAXI);
		tabuleiro.inserir(Casa.PRESUNTO);
		tabuleiro.inserir(Casa.FRANGO);
		tabuleiro.inserir(Casa.QUEIJO);
		tabuleiro.inserir(Casa.CATUPIRY);
		tabuleiro.inserir(Casa.SORTE_OU_AZAR);
		tabuleiro.inserir(Casa.BROCOLIS);
		tabuleiro.inserir(Casa.AZEITONA);
		tabuleiro.inserir(Casa.SORTE_OU_AZAR);
	}

	public static Carta comprarCarta() {
		
		return null;
	}
}
