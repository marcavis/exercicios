package pizzaria;

public enum NomeDeJogador {
	AZUL(0), ROSA(1), AMARELO(2), VERMELHO(4), PRETO(5);
	
	private int tipo;
	
	NomeDeJogador (int tipo) {
		this.tipo = tipo;
	}
}
