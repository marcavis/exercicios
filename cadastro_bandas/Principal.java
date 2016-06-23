package cadastro_bandas;

import java.sql.Connection;

import org.eclipse.swt.SWT;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import banco1.Conexao;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

public class Principal extends Shell {
	private CTabItem tabInstrumento;
	private CTabItem tabBandas;
	private CTabItem tabCidade;
	private CTabItem tabCadBI;
	private CTabItem tabBackup;
	
	private Composite compInstrumento;
	private Composite compBandas;
	private Composite compCidade;
	private Composite compCadBI;
	private Composite compBackup;
	
	private CTabFolder tabFolder;
	
	public static Connection conn = Conexao.conn();
	
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
		
		tabFolder = new CTabFolder(this, SWT.BORDER);
		tabFolder.setBounds(10, 10, 704, 392);
		tabFolder.setSimple(false);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		
		MenuItem mntmcadastro = new MenuItem(menu, SWT.CASCADE);
		mntmcadastro.setText("&Cadastro");
		
		Menu menu_1 = new Menu(mntmcadastro);
		mntmcadastro.setMenu(menu_1);
		
		MenuItem mntminstrumento = new MenuItem(menu_1, SWT.NONE);
		mntminstrumento.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!verificaAba("Instrumentos")) { 
					tabInstrumento = new CTabItem(tabFolder, SWT.NONE);
					tabInstrumento.setShowClose(true);
					tabInstrumento.setText("Instrumentos");
				
					compInstrumento = new TelaInstrumento(tabFolder, SWT.NONE);
					tabInstrumento.setControl(compInstrumento);
					tabFolder.setSelection(tabInstrumento);
				}
			}
		});
		mntminstrumento.setText("&Instrumento");
		
		MenuItem mntmbandas = new MenuItem(menu_1, SWT.NONE);
		mntmbandas.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!verificaAba("Bandas")) { 
					tabBandas = new CTabItem(tabFolder, SWT.NONE);
					tabBandas.setShowClose(true);
					tabBandas.setText("Bandas");
				
					compBandas = new TelaBandas(tabFolder, SWT.NONE);
					tabBandas.setControl(compBandas);
					tabFolder.setSelection(tabBandas);
				}
			}
		});
		mntmbandas.setText("&Bandas");
		
		MenuItem mntmcidade = new MenuItem(menu_1, SWT.NONE);
		mntmcidade.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!verificaAba("Cidade")) { 
					tabCidade = new CTabItem(tabFolder, SWT.NONE);
					tabCidade.setShowClose(true);
					tabCidade.setText("Cidade");
				
					compCidade = new TelaCidade(tabFolder, SWT.NONE);
					tabCidade.setControl(compCidade);
					tabFolder.setSelection(tabCidade);
				}
			}
		});
		mntmcidade.setText("&Cidade");
		
		new MenuItem(menu_1, SWT.SEPARATOR);
		
		MenuItem mntmInstrumentosdasBandas = new MenuItem(menu_1, SWT.NONE);
		mntmInstrumentosdasBandas.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!verificaAba("Instrumentos das Bandas")) { 
					tabCadBI = new CTabItem(tabFolder, SWT.NONE);
					tabCadBI.setShowClose(true);
					tabCadBI.setText("Instrumentos das Bandas");
				
					compCadBI = new CadBandaInst(tabFolder, SWT.NONE);
					tabCadBI.setControl(compCadBI);
					tabFolder.setSelection(tabCadBI);
				}
			}
		});
		mntmInstrumentosdasBandas.setText("Instrumentos &das Bandas");
		
		new MenuItem(menu_1, SWT.SEPARATOR);
		
		MenuItem mntmBackup = new MenuItem(menu_1, SWT.NONE);
		mntmBackup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!verificaAba("Backup")) { 
					tabBackup = new CTabItem(tabFolder, SWT.NONE);
					tabBackup.setShowClose(true);
					tabBackup.setText("Backup");
				
					compBackup = new TelaBackup(tabFolder, SWT.NONE);
					tabBackup.setControl(compBackup);
					tabFolder.setSelection(tabBackup);
				}
			}
		});
		mntmBackup.setText("&Efetuar Backup");
		
		new MenuItem(menu_1, SWT.SEPARATOR);
		
		MenuItem mntmsair = new MenuItem(menu_1, SWT.NONE);
		mntmsair.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		mntmsair.setText("&Sair");
		
		
		createContents();
	}

	private boolean verificaAba(String nome){
		CTabItem[] items = tabFolder.getItems();
		int indice = -1;
		for(int i = 0;i<items.length;i++){
			if(items[i].getText().equals(nome)){
				indice = i;
				break;
			}
		}
		if(indice != -1){
			tabFolder.setSelection(indice);
			return true;
		}
		return false;
			
	}
	
	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(740, 470);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
