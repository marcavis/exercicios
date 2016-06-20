package pizzaria;

public enum Casa {
	PERDE_TUDO(1), SORTE_OU_AZAR(2),
	CEBOLA(3),  CALABRESA(4), QUEIJO(5), TOMATE(6), OVOS(7),
	AZEITONA(8), MILHO(9), PRESUNTO(10), BROCOLIS(11),
	FRANGO(12), CATUPIRY(13), ABACAXI(14);
	
	private int tipo;
	
	Casa (int tipo) {
		this.tipo = tipo;
	}
}
