package pizzaria;

public enum Carta {
	QUEIMA_TUDO(0), TIRA_DO_VIZINHO(1),
	ANDA_NOVAMENTE(2),  GANHA_DOIS(3), PERDE_UM(4), JOGA_ABACAXI(5), COME_ABACAXI(6);
	
	private int tipo;
	
	Carta (int tipo) {
		this.tipo = tipo;
	}
}
