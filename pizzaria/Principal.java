package pizzaria;

import java.util.Scanner;

import estruturas.Lista;
import estruturas.ListaCircular;
import estruturas.PilhaDinamica;

public class Principal {
	public static ListaCircular<Casa> tabuleiro =	new ListaCircular<Casa>();
	public static ListaCircular<Jogador> jogadores;
	public static Lista<Jogador> ganhadores = new Lista<Jogador>();
	public static PilhaDinamica<Carta> cartas = new PilhaDinamica<Carta>();
	public static boolean ultimaRodada = false;
	
	public static void main(String[] args) {
		preencheTabuleiro();
		embaralhar();
		
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
		
		System.out.println("Quantos jogadores? (2-5)");
		jogadores = new ListaCircular<Jogador>();
		int quantJogadores = 0;
		do {
			try {
				Scanner in = new Scanner(System.in);
				quantJogadores = in.nextInt();
			} catch (Exception e) {
				System.out.println("Favor inserir um número de jogadores de 2 a 5");
			}
		} while (quantJogadores < 2 || quantJogadores > 5);
		
		
		
		for(int i = 0; i<quantJogadores; i++) { 
			Jogador j = new Jogador(NomeDeJogador.values()[i]);
			jogadores.inserir(j);
		}
		
		for(int i = 0; i < jogadores.tamanho(); i++) {
			Jogador aMontar = jogadores.get(i);
			int pizzaEscolhida = 0;
			do {
				System.out.println("Escolha uma dessas pizzas para montar, " + aMontar + ". (1-5)");
				System.out.println("1) Portuguesa");
				System.out.println("2) Vegetariana");
				System.out.println("3) Calabresa");
				System.out.println("4) Frango");
				System.out.println("5) Presunto");
				try {
					Scanner in = new Scanner(System.in);
					pizzaEscolhida = in.nextInt();
				} catch (Exception e) {
					System.out.println("Favor inserir o número da pizza escolhida entre 1 a 5");
				}
			} while (pizzaEscolhida < 1 || pizzaEscolhida > 5);
			jogadores.get(i).setReceita(receitas.get(pizzaEscolhida - 1));
		}
		
		//teste para mostrar os ingredientes de cada jogador
		//for(int i = 0; i<jogadores.tamanho(); i++)
		//	System.out.println(jogadores.get(i) + " - " + jogadores.get(i).mostraIngredientes());
		
		int rodada = 0;
		do {
			System.out.println("rodada " + (rodada+1));
			for(int i = 0; i<quantJogadores; i++) {
				jogadores.mostraAtual().jogar();
				jogadores.proximo();
			}
			rodada++;
			System.out.println("");
		} while (!ultimaRodada);
		
		System.out.println("Resultado Final");
		for(int i = 0; i<ganhadores.tamanho(); i++) {
			Jogador j = ganhadores.get(i);
			if(j.isTemAbacaxi())
				System.out.println(j + " completou a sua pizza, mas a pizza com abacaxi ficou uma porcaria!");
			else
				System.out.println(j + " completou a sua pizza, que foi o seu próprio prêmio. Delícia!");
		}
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

	public static void incluirEmGanhadores(Jogador jogador) {
		ultimaRodada = true;
		if(!ganhadores.contem(jogador))
			ganhadores.inserir(jogador);
	}
	
	public static void embaralhar() {
		Lista<Carta> aDistribuir = new Lista<Carta>();
		for(int i = 0; i < 20; i++)
			aDistribuir.inserir(Carta.QUEIMA_TUDO);
		for(int i = 0; i < 20; i++)
			aDistribuir.inserir(Carta.TIRA_DO_VIZINHO);
		for(int i = 0; i < 20; i++)
			aDistribuir.inserir(Carta.ANDA_NOVAMENTE);
		for(int i = 0; i < 20; i++)
			aDistribuir.inserir(Carta.GANHA_DOIS);
		for(int i = 0; i < 20; i++)
			aDistribuir.inserir(Carta.PERDE_UM);
		for(int i = 0; i < 20; i++)
			aDistribuir.inserir(Carta.COME_ABACAXI);
		for(int i = 0; i < 20; i++)
			aDistribuir.inserir(Carta.JOGA_ABACAXI);
		for(int i = 0; i < 20 * Carta.values().length; i++) {
			int codDistribuida = (int)(Math.random()*aDistribuir.tamanho());
			Carta distribuida = aDistribuir.retirar(codDistribuida);
			cartas.inserir(distribuida);
		}
	}

	public static Carta pedirCarta() {
		if(cartas.tamanho() == 0)
			embaralhar();
		return cartas.retirar();
	}
}
