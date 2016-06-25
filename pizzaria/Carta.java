package pizzaria;

public enum Carta {
	QUEIMA_TUDO(1), TIRA_DO_VIZINHO(2),
	ANDA_NOVAMENTE(3),  GANHA_DOIS(4), PERDE_UM(5), JOGA_ABACAXI(6), COME_ABACAXI(7);
	
	private int tipo;
	
	Carta (int tipo) {
		this.tipo = tipo;
	}
	
	public void executar(int tipo) {
		
	}
}
