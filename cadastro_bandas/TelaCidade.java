package cadastro_bandas;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class TelaCidade extends Composite {

	private Table table;
	private Text textNome;
	private Button btnIncluir;
	private Label lblErro;

	private ArrayList<Cidade> lista = new ArrayList<Cidade>();
	private Text textUf;
	private Label lblFiltro;
	private Text textFiltro;

	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TelaCidade(Composite parent, int style) {
		super(parent, style);
		
		Label lblNome = new Label(this, SWT.NONE);
		lblNome.setBounds(10, 13, 55, 15);
		lblNome.setText("Nome");
		
		textNome = new Text(this, SWT.BORDER);
		textNome.setBounds(71, 10, 369, 21);
		
		Label lblUf = new Label(this, SWT.NONE);
		lblUf.setBounds(10, 40, 55, 15);
		lblUf.setText("UF");
		
		btnIncluir = new Button(this, SWT.NONE);
		btnIncluir.setBounds(10, 96, 75, 25);
		btnIncluir.setText("Incluir");
		btnIncluir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String erro = "";
				String nome = textNome.getText();
				if(nome.equals(""))
					erro += "Nome não pode ser vazio. ";
				String uf = textUf.getText();
				lista.add(new Cidade(nome, uf));
				preencheTabela();
				limpaTela();
			}
		});
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 127, 430, 163);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if (table.getSelectionIndex() >= 0) {
					lista.remove(table.getSelectionIndex());
					preencheTabela();
				}
			}
		});
		
		TableColumn tblclmnNome = new TableColumn(table, SWT.NONE);
		tblclmnNome.setWidth(295);
		tblclmnNome.setText("Nome");
		
		TableColumn tblclmnUF = new TableColumn(table, SWT.NONE);
		tblclmnUF.setWidth(100);
		tblclmnUF.setText("UF");
		
		lblErro = new Label(this, SWT.NONE);
		lblErro.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblErro.setBounds(10, 299, 430, 15);
		
		textUf = new Text(this, SWT.BORDER);
		textUf.setBounds(71, 37, 75, 21);
		
		lblFiltro = new Label(this, SWT.NONE);
		lblFiltro.setText("Filtro");
		lblFiltro.setBounds(10, 67, 55, 15);
		
		textFiltro = new Text(this, SWT.BORDER);
		textFiltro.setBounds(71, 64, 75, 21);

	}

	private void preencheTabela() {
		table.setItemCount(0);
		for(Cidade p : lista) {
			TableItem it = new TableItem(table, SWT.NONE);
			it.setText(p.toArray());
		}
	}
	
	private void limpaTela() {
		textNome.setText("");
		textUf.setText("");
		lblErro.setText("");
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
