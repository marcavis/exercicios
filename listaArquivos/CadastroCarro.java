package listaArquivos;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class CadastroCarro extends Shell {
	private Text textMarca;
	private Text textModelo;
	private Text textPlaca;
	private Text textAno;
	private Table table;
	private ArrayList<Carro> lista = new ArrayList<Carro>();

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			CadastroCarro shell = new CadastroCarro(display);
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
	public CadastroCarro(Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(null);
		
		textMarca = new Text(this, SWT.BORDER);
		textMarca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.keyCode==44 || e.keyCode==16777262)
					textMarca.setText(textMarca.getText().replace(",",""));
			}
		});
		textMarca.setBounds(71, 10, 125, 21);
		
		textModelo = new Text(this, SWT.BORDER);
		textModelo.setBounds(71, 37, 125, 21);
		
		textPlaca = new Text(this, SWT.BORDER);
		textPlaca.setBounds(71, 64, 125, 21);
		
		textAno = new Text(this, SWT.BORDER);
		textAno.setBounds(71, 91, 125, 21);
		
		Button btnGravar = new Button(this, SWT.NONE);
		btnGravar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				grava();
			}
		});
		btnGravar.setBounds(272, 89, 75, 25);
		btnGravar.setText("Gravar");
		
		Label lblMarca = new Label(this, SWT.NONE);
		lblMarca.setBounds(10, 16, 55, 15);
		lblMarca.setText("Marca");
		
		Label lblModelo = new Label(this, SWT.NONE);
		lblModelo.setBounds(10, 43, 55, 15);
		lblModelo.setText("Modelo");
		
		Label lblPlaca = new Label(this, SWT.NONE);
		lblPlaca.setBounds(10, 70, 55, 15);
		lblPlaca.setText("Placa");
		
		Label lblAno = new Label(this, SWT.NONE);
		lblAno.setBounds(10, 97, 55, 15);
		lblAno.setText("Ano");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 118, 414, 274);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnMarca = new TableColumn(table, SWT.NONE);
		tblclmnMarca.setWidth(100);
		tblclmnMarca.setText("Marca");
		
		TableColumn tblclmnModelo = new TableColumn(table, SWT.NONE);
		tblclmnModelo.setWidth(100);
		tblclmnModelo.setText("Modelo");
		
		TableColumn tblclmnPlaca = new TableColumn(table, SWT.NONE);
		tblclmnPlaca.setWidth(100);
		tblclmnPlaca.setText("Placa");
		
		TableColumn tblclmnAno = new TableColumn(table, SWT.NONE);
		tblclmnAno.setWidth(100);
		tblclmnAno.setText("Ano");
		createContents();
		preencheTabela();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 440);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void grava(){
		Carro c = new Carro();
		c.setMarca(textMarca.getText());
		c.setModelo(textModelo.getText());
		c.setPlaca(textPlaca.getText());
		c.setAno(Integer.parseInt(textAno.getText()));
		c.gravaTxt();
		preencheTabela();
	}
	
	private void preencheTabela() {
		table.setItemCount(0);
		lista = Carro.lerTxt();
		for (Carro c : lista) {
			TableItem it = new TableItem(table, SWT.NONE);
			it.setText(c.toArray());
		}
	}
}
