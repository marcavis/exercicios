package cadastro_bandas;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
import org.eclipse.swt.widgets.Combo;

public class TelaBandas extends Composite {
	private Text textEstilo;
	private Text textInt;
	private Table table;
	private Text textNome;
	private Button btnIncluir;
	private Group grpCdGravado;
	private Label lblErro;

	private Banda selecionada;
	private ArrayList<Cidade> cidades = new ArrayList<Cidade>();
	private ArrayList<Banda> lista = new ArrayList<Banda>();
	private Text textFiltro;
	private Combo cbCidade;
	private Button btnSim;
	private Button btnNo;

	
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
		
		btnSim = new Button(grpCdGravado, SWT.RADIO);
		btnSim.setBounds(10, 31, 90, 16);
		btnSim.setText("Sim");
		
		btnNo = new Button(grpCdGravado, SWT.RADIO);
		btnNo.setBounds(10, 53, 90, 16);
		btnNo.setSelection(true);
		btnNo.setText("N\u00E3o");
		
		btnIncluir = new Button(this, SWT.NONE);
		btnIncluir.setBounds(10, 121, 75, 25);
		btnIncluir.setText("Incluir");
		btnIncluir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Banda b = getTela();
				b.cadastrar();
				preencheTabela(null);
			}
		});
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 187, 520, 156);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				selecionada = lista.get(table.getSelectionIndex());
				setTela(selecionada);
			}
		});
		
		TableColumn tblclmnNome = new TableColumn(table, SWT.NONE);
		tblclmnNome.setWidth(150);
		tblclmnNome.setText("Nome");
		
		TableColumn tblclmnEstilo = new TableColumn(table, SWT.NONE);
		tblclmnEstilo.setWidth(122);
		tblclmnEstilo.setText("Estilo");
		
		TableColumn tblclmnIntegrantes = new TableColumn(table, SWT.NONE);
		tblclmnIntegrantes.setWidth(58);
		tblclmnIntegrantes.setText("Integrantes");
		
		TableColumn tblclmnCd = new TableColumn(table, SWT.NONE);
		tblclmnCd.setWidth(49);
		tblclmnCd.setText("CD");
		
		TableColumn tblclmnCidade = new TableColumn(table, SWT.NONE);
		tblclmnCidade.setWidth(138);
		tblclmnCidade.setText("Cidade");
		
		lblErro = new Label(this, SWT.NONE);
		lblErro.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblErro.setBounds(10, 299, 430, 15);
		
		cbCidade = new Combo(this, SWT.NONE);
		cbCidade.setBounds(71, 92, 243, 23);
		
		Label lblCidade = new Label(this, SWT.NONE);
		lblCidade.setBounds(10, 96, 55, 15);
		lblCidade.setText("Cidade");
		
		Button btnAlterar = new Button(this, SWT.NONE);
		btnAlterar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Banda nova = getTela();
				nova.setId(selecionada.getId());
				nova.alterar();
				preencheTabela(null);
			}
		});
		btnAlterar.setText("Alterar");
		btnAlterar.setBounds(91, 121, 75, 25);
		
		Button btnExcluir = new Button(this, SWT.NONE);
		btnExcluir.setText("Excluir");
		btnExcluir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selecionada.excluir();
				preencheTabela(null);
			}
		});
		btnExcluir.setBounds(172, 121, 75, 25);
		
		Label lblFiltro = new Label(this, SWT.NONE);
		lblFiltro.setText("Filtro");
		lblFiltro.setBounds(10, 163, 55, 15);
		
		textFiltro = new Text(this, SWT.BORDER);
		textFiltro.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				preencheTabela(textFiltro.getText());
			}
		});
		textFiltro.setBounds(71, 160, 369, 21);

		preencheCidades();
		preencheTabela(null);
		
	}

	private void preencheCidades() {
		cbCidade.setItems(new String[]{});
		cidades = Cidade.listar(null);
		for (Cidade c : cidades) {
			cbCidade.add(c.getNome());
		}
	}
	
	private Banda getTela() {
		Banda b = new Banda();
		b.setNome(textNome.getText());
		b.setEstilo(textEstilo.getText());
		b.setIntegrantes(Integer.parseInt(textInt.getText()));
		b.setCD(btnSim.getSelection()?"X":" ");
		b.setCidade(cidades.get(cbCidade.getSelectionIndex()));
		return b;
	}
	
	private void setTela(Banda banda) {
		textNome.setText(banda.getNome());
		textEstilo.setText(banda.getEstilo());
		textInt.setText(banda.getIntegrantes() + "");
		if(banda.getCD().equals("X")) {
			btnSim.setSelection(true);
			btnNo.setSelection(false);
		} else {
			btnSim.setSelection(false);
			btnNo.setSelection(true);
		}
		cbCidade.select(getIndiceCidade(banda.getCidade().getId()));
	}
	
	private int getIndiceCidade (int id) {
		int pos = 0;
		while(cidades.get(pos).getId()!=id)
			pos++;
		return pos;
	}
	
	private void preencheTabela(String filtro) {
		table.setItemCount(0);
		lista = Banda.listar(filtro);
		for(Banda b : lista) {
			TableItem it = new TableItem(table, SWT.NONE);
			it.setText(b.toArray());
		}
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
