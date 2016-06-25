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
	
	public Jogador(NomeDeJogador n, Receita r) {
		nome = n;
		ingredientes = r.mostraIngredientes();
		posicao = 0;
	}
	
	public void limpaPizza() {
		this.obtidos = new Lista<Casa>();
	}

	public void jogar() {
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
					System.out.println(nome + " entregou o seu abacaxi para " + presenteado.nome + "!");
					temAbacaxi = false;
					guardouAbacaxi = false;
					presenteado.temAbacaxi = true;
					presenteado.guardouAbacaxi = false;
				}
			}
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
		Jogador retorno = null;
		return retorno;
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
			Principal.comprarCarta();
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

	public void consomeAbacaxi() {
		temAbacaxi = false;
		guardouAbacaxi = false;
		
	}
}
