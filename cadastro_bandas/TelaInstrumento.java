package cadastro_bandas;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class TelaInstrumento extends Composite {
	private Text textNome;
	private Table table;
	private Group grpTipo;
	private Button btnAdicionar;
	private Label lblErro;
	
	private ArrayList<Instrumento> lista = new ArrayList<Instrumento>();
	private Text textFiltro;
	private Button btnCorda;
	private Button btnSopro;
	private Button btnPercusso;
	private Button btnAlterar;
	private Button btnExcluir;
	private Instrumento selecionado;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TelaInstrumento(Composite parent, int style) {
		super(parent, style);
		
		lblErro = new Label(this, SWT.NONE);
		lblErro.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblErro.setBounds(20, 318, 420, 15);
		
		textNome = new Text(this, SWT.BORDER);
		textNome.setBounds(71, 10, 369, 21);
		
		Label lblNome = new Label(this, SWT.NONE);
		lblNome.setBounds(10, 16, 55, 15);
		lblNome.setText("Nome");
		
		grpTipo = new Group(this, SWT.NONE);
		grpTipo.setText("Tipo");
		grpTipo.setBounds(20, 37, 420, 43);
		
		btnCorda = new Button(grpTipo, SWT.RADIO);
		btnCorda.setBounds(10, 17, 90, 16);
		btnCorda.setSelection(true);
		btnCorda.setText("Corda");
		
		btnSopro = new Button(grpTipo, SWT.RADIO);
		btnSopro.setBounds(106, 17, 90, 16);
		btnSopro.setText("Sopro");
		
		btnPercusso = new Button(grpTipo, SWT.RADIO);
		btnPercusso.setBounds(202, 17, 90, 16);
		btnPercusso.setText("Percuss\u00E3o");
		
		btnAdicionar = new Button(this, SWT.NONE);
		btnAdicionar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getTela().cadastra();
				preencheTabela(null);
			}
		});
		btnAdicionar.setBounds(30, 86, 75, 25);
		btnAdicionar.setText("Adicionar");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(20, 144, 420, 168);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				selecionado = lista.get(table.getSelectionIndex());
				setTela(selecionado);
			}
		});
		
		TableColumn tblclmnNome = new TableColumn(table, SWT.NONE);
		tblclmnNome.setWidth(240);
		tblclmnNome.setText("Nome");
		
		TableColumn tblclmnTipo = new TableColumn(table, SWT.NONE);
		tblclmnTipo.setWidth(150);
		tblclmnTipo.setText("Tipo");
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Filtro");
		label.setBounds(20, 120, 55, 15);
		
		textFiltro = new Text(this, SWT.BORDER);
		textFiltro.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				preencheTabela(textFiltro.getText());
			}
		});
		textFiltro.setBounds(81, 117, 359, 21);
		
		btnAlterar = new Button(this, SWT.NONE);
		btnAlterar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Instrumento novo = getTela();
				novo.setId(selecionado.getId());
				novo.altera();
				preencheTabela(null);
			}
		});
		btnAlterar.setBounds(111, 86, 75, 25);
		btnAlterar.setText("Alterar");
		
		btnExcluir = new Button(this, SWT.NONE);
		btnExcluir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selecionado.exclui();
				preencheTabela(null);
			}
		});
		btnExcluir.setBounds(192, 86, 75, 25);
		btnExcluir.setText("Excluir");
		
		preencheTabela(null);
	}

	private Instrumento getTela() {
		Instrumento i = new Instrumento();
		i.setNome(textNome.getText());
		String tipo = "";
		for (Button b : new Button[]{btnCorda, btnSopro, btnPercusso})
			if (b.getSelection())
				tipo = b.getText();
		i.setTipo(tipo);
		return i;
	}
	
	private void setTela(Instrumento i) {
		textNome.setText(i.getNome());
		if(i.getTipo().equals("Corda")) {	
			btnCorda.setSelection(true);
			btnSopro.setSelection(false);
			btnPercusso.setSelection(false);
		}
		if(i.getTipo().equals("Sopro")) {	
			btnCorda.setSelection(false);
			btnSopro.setSelection(true);
			btnPercusso.setSelection(false);
		}
		if(i.getTipo().equals("Percussão")) {
			btnCorda.setSelection(false);
			btnSopro.setSelection(false);
			btnPercusso.setSelection(true);
		}
	}
	
	private void preencheTabela(String filtro) {
		table.setItemCount(0);
		lista = Instrumento.listar(filtro);
		for(Instrumento i : lista) {
			TableItem it = new TableItem(table, SWT.NONE);
			it.setText(i.toArray());
		}
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
