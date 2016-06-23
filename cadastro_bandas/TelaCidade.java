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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class TelaCidade extends Composite {

	private Table table;
	private Text textNome;
	private Button btnIncluir;
	private Label lblErro;

	private ArrayList<Cidade> lista = new ArrayList<Cidade>();
	private Cidade selecionada;
	private Text textUf;
	private Label lblFiltro;
	private Text textFiltro;
	private TableColumn tblclmnId;
	private Button btnAlterar;
	private Button btnExcluir;

	
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
		btnIncluir.setBounds(10, 64, 75, 25);
		btnIncluir.setText("Incluir");
		btnIncluir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getTela().cadastra();
				preencheTabela(null);
			}
		});
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 128, 430, 175);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				selecionada = lista.get(table.getSelectionIndex());
				setTela(selecionada);
			}
		});
		
		tblclmnId = new TableColumn(table, SWT.NONE);
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");
		
		TableColumn tblclmnNome = new TableColumn(table, SWT.NONE);
		tblclmnNome.setWidth(212);
		tblclmnNome.setText("Nome");
		
		TableColumn tblclmnUF = new TableColumn(table, SWT.NONE);
		tblclmnUF.setWidth(100);
		tblclmnUF.setText("UF");
		
		lblErro = new Label(this, SWT.NONE);
		lblErro.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblErro.setBounds(10, 299, 430, 15);
		
		textUf = new Text(this, SWT.BORDER);
		textUf.setBounds(71, 37, 75, 21);
				
		btnAlterar = new Button(this, SWT.NONE);
		btnAlterar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Cidade nova = getTela();
				nova.setId(selecionada.getId());
				nova.altera();
				preencheTabela(null);
			}
		});
		btnAlterar.setText("Alterar");
		btnAlterar.setBounds(91, 64, 75, 25);
		
		btnExcluir = new Button(this, SWT.NONE);
		btnExcluir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selecionada.exclui();
				preencheTabela(null);
			}
		});
		btnExcluir.setText("Excluir");
		btnExcluir.setBounds(173, 64, 75, 25);

		lblFiltro = new Label(this, SWT.NONE);
		lblFiltro.setText("Filtro");
		lblFiltro.setBounds(10, 104, 55, 15);
		
		textFiltro = new Text(this, SWT.BORDER);
		textFiltro.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				preencheTabela(textFiltro.getText());
			}
		});
		textFiltro.setBounds(71, 101, 369, 21);
		
		Button btnRelatorio = new Button(this, SWT.NONE);
		btnRelatorio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JasperPrint impressao;
				try {
					impressao = JasperFillManager.fillReport("report/report1.jasper", null, Principal.conn);
					JasperViewer.viewReport(impressao, false);
				} catch (JRException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRelatorio.setBounds(365, 64, 75, 25);
		btnRelatorio.setText("Relat\u00F3rio");
		
		preencheTabela(null);
	}

	private void preencheTabela(String filtro) {
		table.setItemCount(0);
		lista = Cidade.listar(filtro);
		for(Cidade c : lista) {
			TableItem it = new TableItem(table, SWT.NONE);
			it.setText(c.toArray());
		}
	}
	
	private Cidade getTela() {
		Cidade c = new Cidade();
		c.setNome(textNome.getText());
		c.setUf(textUf.getText());
		return c;
	}
	
	private void setTela(Cidade c) {
		textNome.setText(c.getNome());
		textUf.setText(c.getUf());
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
