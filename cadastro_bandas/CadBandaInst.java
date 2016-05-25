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

public class CadBandaInst extends Composite {
	private Label lblErro;

	private Banda selecionada;
	private ArrayList<Cidade> cidades = new ArrayList<Cidade>();
	private ArrayList<Banda> lista = new ArrayList<Banda>();
	private Text textFiltro;
	private Combo cbBanda;
	private Table table;
	private Table table_1;

	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CadBandaInst(Composite parent, int style) {
		super(parent, style);
		
		cbBanda = new Combo(this, SWT.NONE);
		cbBanda.setBounds(71, 10, 243, 23);
		
		Label lblBanda = new Label(this, SWT.NONE);
		lblBanda.setBounds(10, 20, 55, 15);
		lblBanda.setText("Banda");
		
		Label lblFiltro = new Label(this, SWT.NONE);
		lblFiltro.setText("Filtro");
		lblFiltro.setBounds(10, 91, 55, 15);
		
		textFiltro = new Text(this, SWT.BORDER);
//		textFiltro.addModifyListener(new ModifyListener() {
//			public void modifyText(ModifyEvent arg0) {
//				preencheTabela(textFiltro.getText());
//			}
//		});
		textFiltro.setBounds(71, 91, 243, 21);
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 151, 304, 213);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNome = new TableColumn(table, SWT.NONE);
		tblclmnNome.setWidth(100);
		tblclmnNome.setText("Nome");

		TableColumn tblclmnTipo = new TableColumn(table, SWT.NONE);
		tblclmnTipo.setWidth(100);
		tblclmnTipo.setText("Tipo");
		
		Button btnAdicionar = new Button(this, SWT.NONE);
		btnAdicionar.setBounds(320, 221, 94, 33);
		btnAdicionar.setText("Adicionar");
		
		Button btnRemover = new Button(this, SWT.NONE);
		btnRemover.setText("Remover");
		btnRemover.setBounds(320, 260, 94, 33);
		
		table_1 = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setBounds(420, 91, 232, 273);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);
		
		Label lblInst = new Label(this, SWT.NONE);
		lblInst.setAlignment(SWT.CENTER);
		lblInst.setBounds(420, 20, 232, 19);
		lblInst.setText("Instrumentos Adicionados");
		
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
