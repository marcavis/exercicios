package cadastro_bandas;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
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

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TelaInstrumento(Composite parent, int style) {
		super(parent, style);
		
		lblErro = new Label(this, SWT.NONE);
		lblErro.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblErro.setBounds(20, 296, 420, 15);
		
		textNome = new Text(this, SWT.BORDER);
		textNome.setBounds(71, 10, 369, 21);
		
		Label lblNome = new Label(this, SWT.NONE);
		lblNome.setBounds(10, 16, 55, 15);
		lblNome.setText("Nome");
		
		grpTipo = new Group(this, SWT.NONE);
		grpTipo.setText("Tipo");
		grpTipo.setBounds(20, 37, 420, 43);
		
		Button btnCorda = new Button(grpTipo, SWT.RADIO);
		btnCorda.setBounds(10, 17, 90, 16);
		btnCorda.setSelection(true);
		btnCorda.setText("Corda");
		
		Button btnSopro = new Button(grpTipo, SWT.RADIO);
		btnSopro.setBounds(106, 17, 90, 16);
		btnSopro.setText("Sopro");
		
		Button btnPercusso = new Button(grpTipo, SWT.RADIO);
		btnPercusso.setBounds(202, 17, 90, 16);
		btnPercusso.setText("Percuss\u00E3o");
		
		btnAdicionar = new Button(this, SWT.NONE);
		btnAdicionar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String nome = textNome.getText();
				String tipo = "";
				for (Button b : new Button[]{btnCorda, btnSopro, btnPercusso})
					if (b.getSelection())
						tipo = b.getText();
				if (nome.equals("")) {
					lblErro.setText("Nome não pode ser vazio!");
				} else {
					lista.add(new Instrumento(nome, tipo));
					preencheTabela();
					limpaTela();
				}
			}
		});
		btnAdicionar.setBounds(30, 86, 75, 25);
		btnAdicionar.setText("Adicionar");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(20, 117, 420, 173);
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
		tblclmnNome.setWidth(240);
		tblclmnNome.setText("Nome");
		
		TableColumn tblclmnTipo = new TableColumn(table, SWT.NONE);
		tblclmnTipo.setWidth(150);
		tblclmnTipo.setText("Tipo");
		
		

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	private void preencheTabela() {
		table.setItemCount(0);
		for(Instrumento p : lista) {
			TableItem it = new TableItem(table, SWT.NONE);
			it.setText(p.toArray());
		}
	}
	
	private void limpaTela() {
		textNome.setText("");
		lblErro.setText("");
	}
}
