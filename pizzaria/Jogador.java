package pizzaria;

import estruturas.Lista;
import estruturas.NodeDuplo;

public class Jogador {
	private Lista<Casa> ingredientes = new Lista<Casa>();
	private Lista<Casa> obtidos = new Lista<Casa>(); 
	private NomeDeJogador nome;
	private boolean temAbacaxi = false;
	private boolean guardouAbacaxi = false;
	private int posicao;
	
	public Jogador(NomeDeJogador n) {
		nome = n;
		posicao = 0;
	}
	
	public void limpaPizza() {
		this.obtidos = new Lista<Casa>();
	}

	public void jogar() {
		//System.out.println(mostraObtidos());
		if(temAbacaxi && !guardouAbacaxi) 
		{
			System.out.println("O abacaxi ficou espetando a mão de " + nome + ", que não consegue se mexer!");
			System.out.println("Depois de algum esforço, " + nome + " finalmente consegue guardar o abacaxi.");
			guardouAbacaxi = true;
		} else {
			int dado = (int)(Math.random()*6+1);
			posicao += dado; 
			posicao = posicao % Principal.tabuleiro.tamanho();
			System.out.println(nome + ": Rolei um " + dado + ", parei em pos. " + posicao + ", " + Principal.tabuleiro.get(posicao));
			tratarCasa(Principal.tabuleiro.get(posicao));
			if(temAbacaxi && guardouAbacaxi) {
				Jogador presenteado = procurarNaMesmaCasa();
				if(presenteado != null) {
					System.out.println(nome + " entregou o seu abacaxi para " + presenteado + "!");
					temAbacaxi = false;
					guardouAbacaxi = false;
					if(presenteado.isTemAbacaxi())
						System.out.println("Mas " + presenteado + " lembra que já tinha um abacaxi, e joga o presente fora.");
					presenteado.temAbacaxi = true;
					presenteado.guardouAbacaxi = false;
				}
			}
		}
		if(obtidos.tamanho() == 5) {
			Principal.incluirEmGanhadores(this);
		}
	}
	
	public Jogador procurarNaMesmaCasa() {
		Jogador retorno = null;
		NodeDuplo<Jogador> procura = Principal.jogadores.getCursor();
		for(int i = 1; i<Principal.jogadores.tamanho(); i++) {
			procura = procura.next;
			if(procura.valor.posicao == this.posicao) {
				retorno = procura.valor;
				break;
			}
		}
		return retorno;
	}
	
	public Lista<Casa> getIngredientes() {
		return ingredientes;
	}

	public void setReceita(Receita r) {
		this.ingredientes = r.mostraIngredientes();
	}

	public Lista<Casa> getObtidos() {
		return obtidos;
	}

	public NomeDeJogador getNome() {
		return nome;
	}

	public boolean isTemAbacaxi() {
		return temAbacaxi;
	}

	public boolean isGuardouAbacaxi() {
		return guardouAbacaxi;
	}

	public int getPosicao() {
		return posicao;
	}

	public Jogador procurarProximo() {
		NodeDuplo<Jogador> retorno = Principal.jogadores.getCursor().next;
		return retorno.valor;
	}
	
	public Jogador procurarProximoComIngredientes() {
		Jogador retorno = null;
		NodeDuplo<Jogador> procura = Principal.jogadores.getCursor();
		for(int i = 1; i<Principal.jogadores.tamanho(); i++) {
			procura = procura.next;
			if(procura.valor.obtidos.tamanho() > 0) {
				retorno = procura.valor;
				break;
			}
		}
		return retorno;
	}

	public void tratarCasa(Casa casa) {
		switch (casa) {
		case PERDE_TUDO:
			System.out.println(nome + " deixou a pizza cair! Perdeu todos os ingredientes!");
			obtidos = new Lista<Casa>(); 
			break;
		case SORTE_OU_AZAR:
			comprarCarta();
			break;
		case ABACAXI:
			System.out.println(nome + " pegou um abacaxi!");
			temAbacaxi = true;
			guardouAbacaxi = false;
			break;
		default:
			if(ingredientes.contem(casa) && !obtidos.contem(casa)) {
				System.out.println(nome + " adicionou " + casa + " à sua pizza.");
				obtidos.inserir(casa);
			} else if(ingredientes.contem(casa) && obtidos.contem(casa)) {
				System.out.println(nome + " encontrou " + casa + ", mas a sua pizza já contém esse ingrediente.");
			} else {
				System.out.println(nome + " encontrou " + casa + ", mas isso não está na sua receita.");
			}
			break;
		}
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nome.toString();
	}
	
	public String mostraIngredientes() {
		String res = "[";
		for(int i = 0; i<ingredientes.tamanho() - 1; i++)
			res += ingredientes.get(i) + ", ";
		return res + ingredientes.get(4) + "]";
	}

	public String mostraObtidos() {
		String res = "[";
		for(int i = 0; i<obtidos.tamanho(); i++)
			res += obtidos.get(i) + ", ";
		return res + "]";
	}
	
	public void consomeAbacaxi() {
		temAbacaxi = false;
		guardouAbacaxi = false;
		
	}

	public void recebeAbacaxi() {
		temAbacaxi = true;
		guardouAbacaxi = false;
	}
	
	public void comprarCarta() {
		Carta comprada = Principal.pedirCarta();
		executar(comprada, this);
	}
	
	public void executar(Carta tipo, Jogador jogador) {
		Jogador alvo;
		switch (tipo) {
		case QUEIMA_TUDO:
			System.out.println("A pizza de " + jogador + " queimou repentinamente!");
			jogador.limpaPizza();
			break;
		case TIRA_DO_VIZINHO:
			alvo = jogador.procurarProximoComIngredientes();
			if(alvo == null) {
				System.out.println(jogador + " tentou roubar ingredientes de alguém, mas ninguém tinha nada a ser roubado.");
				break;
			}
			Casa ingRoubado = null;
			int indiceIngRoubado = 0;
			for(int i = 0; i<alvo.getObtidos().tamanho(); i++) {
				Casa ingAtual = alvo.getObtidos().get(i);
				if(jogador.getIngredientes().contem(ingAtual) &&
						!jogador.getObtidos().contem(ingAtual)){
					ingRoubado = ingAtual;
					indiceIngRoubado = i;
				}
			}
			if(ingRoubado != null) {
				System.out.println(jogador + " roubou " + ingRoubado + " de " + alvo + "!");
				alvo.getObtidos().retirar(indiceIngRoubado);
				jogador.getObtidos().inserir(ingRoubado);
			} else {
				Casa ingRemovido = alvo.getObtidos().get(0);
				System.out.println(jogador + " tentou roubar algo de " + alvo + ", mas não achou nada para a sua pizza.");
				System.out.println(jogador + " simplesmente joga fora " + ingRemovido + " de " + alvo + "!");
			}
			break;
		case ANDA_NOVAMENTE:
			System.out.println(jogador + " ganhou uma nova rodada!");
			jogador.jogar();
			break;
		case GANHA_DOIS:
			int[] indicesIngGanhos = new int[2];
			indicesIngGanhos[0] = (int) (Math.random()*jogador.getIngredientes().tamanho());
			do {
				indicesIngGanhos[1] = (int) (Math.random()*jogador.getIngredientes().tamanho());
			} while (indicesIngGanhos[0] == indicesIngGanhos[1]);
			for (int i : indicesIngGanhos) {
				Casa ingGanho = jogador.getIngredientes().get(i);
				if(jogador.getObtidos().contem(ingGanho)) {
					System.out.println(ingGanho + " caiu do céu, mas " + jogador + " já tinha isso na sua pizza!");
				} else {
					jogador.getObtidos().inserir(ingGanho);
					System.out.println(ingGanho + " caiu do céu, exatamente na posição correta da pizza de " + jogador + "!");
				}
			}
			break;
		case PERDE_UM:
			if(jogador.getObtidos().tamanho() > 0) {
				int indiceIngPerdido = (int)(Math.random()*jogador.getObtidos().tamanho());
				Casa ingPerdido = jogador.getObtidos().get(indiceIngPerdido);
				jogador.getObtidos().retirar(indiceIngPerdido);
				System.out.println(jogador + " tropeçou, e deixou " + ingPerdido + " cair!");
			} else {
				System.out.println(jogador + " tropeçou, mas não havia nada na pizza que pudesse cair.");
			}
			break;
		case JOGA_ABACAXI:
			alvo = jogador.procurarProximo();
			jogador.consomeAbacaxi();
			if(alvo.isTemAbacaxi()) {
				System.out.println(jogador + " lança o seu abacaxi na direção de " + alvo + "...");
				System.out.println("Mas " + alvo + " usa o abacaxi que já possui como escudo e derruba o projetil ao chão!");
			} else {
				System.out.println(jogador + " lança o seu abacaxi, que cai certeiramente nas mãos de " + alvo + "!");
				alvo.recebeAbacaxi();
			}
			break;
		case COME_ABACAXI:
			if(jogador.isTemAbacaxi()) {
				jogador.consomeAbacaxi();
				System.out.println(jogador + " come o abacaxi. É fonte de energia, " + jogador + " pode jogar novamente!");
				jogador.jogar();
			} else {
				System.out.println(jogador + " sente um desejo de comer abacaxi, mas não tem nenhum. Que pena!");
			}
			break;
		default:
			break;
		}
		
	}
}
