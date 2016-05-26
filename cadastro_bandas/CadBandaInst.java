package cadastro_bandas;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CadBandaInst extends Composite {
	private Text textFiltro;
	private Combo cbBandas;
	private Table table;
	private Table table_1;
	
	private ArrayList<Banda> bandas = new ArrayList<Banda>();
	private ArrayList<Instrumento> instrumentos = new ArrayList<Instrumento>();
	private ArrayList<Instrumento> instrumentosDaBanda = new ArrayList<Instrumento>();
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CadBandaInst(Composite parent, int style) {
		super(parent, style);
		
		cbBandas = new Combo(this, SWT.NONE);
		cbBandas.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				//Banda b = bandas.get(cbBandas.getSelectionIndex());
				if(cbBandas.getSelectionIndex() > -1)
					preencheInstrumentosDaBanda();
			}
		});
		cbBandas.setBounds(71, 10, 243, 23);
		
		Label lblBanda = new Label(this, SWT.NONE);
		lblBanda.setBounds(10, 13, 55, 15);
		lblBanda.setText("Banda");
		
		Label lblFiltro = new Label(this, SWT.NONE);
		lblFiltro.setText("Filtro");
		lblFiltro.setBounds(10, 70, 55, 15);
	
		
		textFiltro = new Text(this, SWT.BORDER);
		textFiltro.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				preencheInstrumentos(textFiltro.getText());
			}
		});
		textFiltro.setBounds(71, 67, 243, 21);
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 91, 304, 199);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNome = new TableColumn(table, SWT.NONE);
		tblclmnNome.setWidth(136);
		tblclmnNome.setText("Nome");

		TableColumn tblclmnTipo = new TableColumn(table, SWT.NONE);
		tblclmnTipo.setWidth(134);
		tblclmnTipo.setText("Tipo");
		
		Button btnAdicionar = new Button(this, SWT.NONE);
		btnAdicionar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Banda b = bandas.get(cbBandas.getSelectionIndex());
				Instrumento i = instrumentos.get(table.getSelectionIndex());
				BandaInstrumento bi = new BandaInstrumento();
				bi.setB(b);
				bi.setI(i);
				bi.cadastrar();
				preencheInstrumentosDaBanda();
			}
		});
		btnAdicionar.setBounds(320, 163, 75, 25);
		btnAdicionar.setText("Adicionar");
		
		Button btnRemover = new Button(this, SWT.NONE);
		btnRemover.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Banda b = bandas.get(cbBandas.getSelectionIndex());
				Instrumento i = instrumentosDaBanda.get(table_1.getSelectionIndex());
				BandaInstrumento bi = new BandaInstrumento();
				bi.setB(b);
				bi.setI(i);
				bi.excluir();
				preencheInstrumentosDaBanda();
			}
		});
		btnRemover.setText("Remover");
		btnRemover.setBounds(320, 194, 75, 25);
		
		table_1 = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setBounds(401, 47, 222, 243);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);
		
		TableColumn tblclmnNome_1 = new TableColumn(table_1, SWT.NONE);
		tblclmnNome_1.setWidth(100);
		tblclmnNome_1.setText("Nome");
		
		TableColumn tblclmnTipo_1 = new TableColumn(table_1, SWT.NONE);
		tblclmnTipo_1.setWidth(100);
		tblclmnTipo_1.setText("Tipo");
		
		Label lblInst = new Label(this, SWT.NONE);
		lblInst.setAlignment(SWT.CENTER);
		lblInst.setBounds(401, 13, 222, 19);
		lblInst.setText("Instrumentos Adicionados");
		
		preencheBandas();
		preencheInstrumentos(null);
	}
	
	private void preencheBandas() {
		cbBandas.setItems(new String[]{});
		bandas = Banda.listar(null);
		for (Banda b : bandas) {
			cbBandas.add(b.getNome()+" ("+b.getCidade().getNome()+")");
		}
	}
	
	private void preencheInstrumentos(String filtro) {
		instrumentos = Instrumento.listar(filtro);
		table.setItemCount(0);
		for (Instrumento i : instrumentos) {
			TableItem it = new TableItem(table, SWT.NONE);
			it.setText(i.toArray());
		}
	}
	
	private void preencheInstrumentosDaBanda() {
		Banda b = bandas.get(cbBandas.getSelectionIndex());
		instrumentosDaBanda = BandaInstrumento.listar(b.getId());
		table_1.setItemCount(0);
		for (Instrumento i : instrumentosDaBanda) {
			TableItem it = new TableItem(table_1, SWT.NONE);
			it.setText(i.toArray());
		}
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}