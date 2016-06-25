package pizzaria;

public enum Carta {
	QUEIMA_TUDO(1), TIRA_DO_VIZINHO(2),
	ANDA_NOVAMENTE(3),  GANHA_DOIS(4), PERDE_UM(5), JOGA_ABACAXI(6), COME_ABACAXI(7);
	
	private int tipo;
	
	Carta (int tipo) {
		this.tipo = tipo;
	}
	
	public void executar(Carta carta, Jogador jogador) {
		switch (carta) {
		case QUEIMA_TUDO:
			System.out.println("A pizza de " + jogador + " queimou repentinamente!");
			jogador.limpaPizza();
			break;
		case TIRA_DO_VIZINHO:
			
			Jogador alvo = jogador.procurarProximoComIngredientes();
			if(alvo.getObtidos().tamanho() == 0) {
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
		case COME_ABACAXI:
			if(jogador.isTemAbacaxi()) {
				jogador.consomeAbacaxi();
				System.out.println(jogador + " come o abacaxi. É fonte de energia, " + jogador + " pode jogar novamente!");
				jogador.jogar();
			} else {
				System.out.println(jogador + " sente um desejo de comer abacaxi, mas não tem nenhum. Que pena!");
			}
		default:
			break;
		}
		
	}
}
