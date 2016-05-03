package perguntas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Principal extends Shell {
	private Label lblPergunta;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private ArrayList<Pergunta> perguntas;
	private Label lblNumPergunta;
	private Label lblPontuacao;
	private int numPergunta = 0;
	private int numAcertos;
	boolean provaEncerrada;
	private Button btnResponder;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Principal shell = new Principal(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public Principal(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		lblPergunta = new Label(this, SWT.NONE);
		lblPergunta.setBounds(10, 10, 424, 43);
		
		Group group = new Group(this, SWT.NONE);
		group.setBounds(10, 59, 275, 100);
		
		btn1 = new Button(group, SWT.RADIO);
		btn1.setBounds(10, 10, 249, 24);
		btn1.setText("");
		btn1.setSelection(true);
		
		btn2 = new Button(group, SWT.RADIO);
		btn2.setBounds(10, 40, 249, 24);
		btn2.setText("");
		
		btn3 = new Button(group, SWT.RADIO);
		btn3.setBounds(10, 70, 249, 24);
		btn3.setText("");
		
		lblNumPergunta = new Label(this, SWT.NONE);
		lblNumPergunta.setBounds(291, 59, 143, 19);
		lblNumPergunta.setText("Pergunta");
		
		lblPontuacao = new Label(this, SWT.NONE);
		lblPontuacao.setText("Pontuação: 0/0");
		lblPontuacao.setBounds(291, 84, 143, 19);
		
		btnResponder = new Button(this, SWT.NONE);
		btnResponder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(numPergunta >= perguntas.size()) {
					provaEncerrada = true;
					btnResponder.setText("Encerrada!");
				}
				if(!provaEncerrada) {
					int escolha = 1;
					if(btn1.getSelection())
						escolha = 1;
					if(btn2.getSelection())
						escolha = 2;
					if(btn3.getSelection())
						escolha = 3;
					if(escolha == perguntas.get(numPergunta).getCorreta())
						numAcertos++;
					numPergunta++;
					salvarProgresso();
					carregarPergunta(numPergunta);
				}
				
					
			}
		});
		btnResponder.setBounds(191, 228, 94, 33);
		btnResponder.setText("Responder");
		
		Button btnReiniciar = new Button(this, SWT.NONE);
		btnReiniciar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				numAcertos = 0;
				numPergunta = 0;
				salvarProgresso();
				provaEncerrada = false;
				carregarPergunta(numAcertos);
			}
		});
		btnReiniciar.setText("Reiniciar");
		btnReiniciar.setBounds(340, 228, 94, 33);
		
		numAcertos = 0;
		int[] progresso = lerProgresso();
		numPergunta = progresso[0];
		numAcertos = progresso[1];
		perguntas = lerPerguntas();
		carregarPergunta(numPergunta);
		
		createContents();

	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public ArrayList<Pergunta> lerPerguntas() {
		ArrayList<Pergunta> lista = new ArrayList<Pergunta>();
		try {
			FileReader fr = new FileReader(new File("perguntas.txt"));
			BufferedReader br = new BufferedReader(fr);
			String linha;
			while((linha=br.readLine())!=null) {
				String[] v = linha.split(";");
				Pergunta p = new Pergunta();
				p.setPergunta(v[0]);
				p.setAlternativas(new String[]{v[1],v[2],v[3]});
				p.setCorreta(Integer.parseInt(v[4]));
				lista.add(p);
			}
			br.close();
			fr.close();
			
		} catch(Exception e) {
			//e.printStackTrace();
		}
		return lista;
	}
	
	public int[] lerProgresso() {
		int[] progresso = new int[]{0,0};
		try {
			FileReader fr = new FileReader(new File("progresso.txt"));
			BufferedReader br = new BufferedReader(fr);
			String linha;
			while((linha=br.readLine())!=null) {
				String[] v = linha.split(";");
				progresso[0] = Integer.parseInt(v[0]);
				progresso[1] = Integer.parseInt(v[1]);
			}
			br.close();
			fr.close();
			
		} catch(Exception e) {
			//e.printStackTrace();
		}
		return progresso;
	}
	
	public boolean salvarProgresso() {
		try {
			FileWriter fw = new FileWriter(new File("progresso.txt"));
			//true seta o modo append, em vez de criar novo arquivo
			BufferedWriter bw = new BufferedWriter(fw);
			String linha = numPergunta + ";" + numAcertos;
			//faz a insercao
			bw.append(linha);
			bw.close();
			fw.close();
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public void carregarPergunta(int indice) {
		if(indice >= perguntas.size()) {
			lblPergunta.setText("Prova Encerrada");
			btn1.setText("");
			btn2.setText("");
			btn3.setText("");
			lblNumPergunta.setText("");
			lblPontuacao.setText("Acertos: " + numAcertos + " de " + numPergunta);
			btnResponder.setText("Encerrada!");
		} else {
			lblPergunta.setText(perguntas.get(indice).getPergunta());
			btn1.setText(perguntas.get(indice).getAlternativas()[0]);
			btn2.setText(perguntas.get(indice).getAlternativas()[1]);
			btn3.setText(perguntas.get(indice).getAlternativas()[2]);
			lblNumPergunta.setText("Pergunta " + (indice+1));
			lblPontuacao.setText("Acertos: " + numAcertos + " de " + numPergunta);
			btnResponder.setText("Responder");
		}
	}
}
