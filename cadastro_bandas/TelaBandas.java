package cadastro_bandas;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class TelaBandas extends Composite {
	private Text textEstilo;
	private Text textInt;
	private Table table;
	private Text textNome;
	private Button btnIncluir;
	private Group grpCdGravado;
	private Label lblErro;

	private ArrayList<Banda> lista = new ArrayList<Banda>();

	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TelaBandas(Composite parent, int style) {
		super(parent, style);
		
		Label lblNome = new Label(this, SWT.NONE);
		lblNome.setBounds(10, 13, 55, 15);
		lblNome.setText("Nome");
		
		textNome = new Text(this, SWT.BORDER);
		textNome.setBounds(71, 10, 369, 21);
		
		Label lblEstilo = new Label(this, SWT.NONE);
		lblEstilo.setBounds(10, 40, 55, 15);
		lblEstilo.setText("Estilo");
		
		textEstilo = new Text(this, SWT.BORDER);
		textEstilo.setBounds(71, 37, 369, 21);
		
		textInt = new Text(this, SWT.BORDER);
		textInt.setBounds(154, 64, 114, 21);
		
		Label lblNmeroDeIntegrantes = new Label(this, SWT.NONE);
		lblNmeroDeIntegrantes.setBounds(10, 70, 138, 15);
		lblNmeroDeIntegrantes.setText("N\u00FAmero de integrantes");
		
		grpCdGravado = new Group(this, SWT.NONE);
		grpCdGravado.setText("CD Gravado");
		grpCdGravado.setBounds(320, 64, 120, 78);
		
		Button btnSim = new Button(grpCdGravado, SWT.RADIO);
		btnSim.setBounds(10, 31, 90, 16);
		btnSim.setText("Sim");
		
		Button btnNo = new Button(grpCdGravado, SWT.RADIO);
		btnNo.setBounds(10, 53, 90, 16);
		btnNo.setSelection(true);
		btnNo.setText("N\u00E3o");
		
		btnIncluir = new Button(this, SWT.NONE);
		btnIncluir.setBounds(10, 91, 75, 25);
		btnIncluir.setText("Incluir");
		btnIncluir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String erro = "";
				String nome = textNome.getText();
				if(nome.equals(""))
					erro += "Nome não pode ser vazio. ";
				String estilo = textEstilo.getText();
				
				int numInt = 1;
				try {
					numInt = Integer.parseInt(textInt.getText());
				} catch (NumberFormatException e1) {
					erro += "Número inválido de integrantes. ";
				}
				if (numInt < 1)
					erro += "Número inválido de integrantes. ";
				if(!erro.equals("")) {
					lblErro.setText(erro);
				} else {
					lista.add(new Banda(nome, estilo, numInt, btnSim.getSelection()));
					preencheTabela();
					limpaTela();
				}
			}
		});
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 148, 430, 142);
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
		tblclmnNome.setWidth(150);
		tblclmnNome.setText("Nome");
		
		TableColumn tblclmnEstilo = new TableColumn(table, SWT.NONE);
		tblclmnEstilo.setWidth(130);
		tblclmnEstilo.setText("Estilo");
		
		TableColumn tblclmnIntegrantes = new TableColumn(table, SWT.NONE);
		tblclmnIntegrantes.setWidth(90);
		tblclmnIntegrantes.setText("Integrantes");
		
		TableColumn tblclmnCd = new TableColumn(table, SWT.NONE);
		tblclmnCd.setWidth(40);
		tblclmnCd.setText("CD");
		
		lblErro = new Label(this, SWT.NONE);
		lblErro.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblErro.setBounds(10, 299, 430, 15);

	}

	private void preencheTabela() {
		table.setItemCount(0);
		for(Banda p : lista) {
			TableItem it = new TableItem(table, SWT.NONE);
			it.setText(p.toArray());
		}
	}
	
	private void limpaTela() {
		textNome.setText("");
		textEstilo.setText("");
		textInt.setText("");
		lblErro.setText("");
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
