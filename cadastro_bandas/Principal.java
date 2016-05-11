package cadastro_bandas;

import java.sql.Connection;

import org.eclipse.swt.SWT;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import banco1.Conexao;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

public class Principal extends Shell {
	private CTabItem tabInstrumento;
	private CTabItem tabBandas;
	private CTabItem tabCidade;
	
	private Composite compInstrumento;
	private Composite compBandas;
	private Composite compCidade; 
	
	//Não peguei o modo usual de impedir CTabItems de abrir mais de uma vez, então temos
	//essa solução simples mas que não funcionaria legal com muitas abas.
	private boolean tabI_ligada = false;
	private boolean tabB_ligada = false;
	private boolean tabC_ligada = false;
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
		tabFolder.setBounds(10, 10, 504, 392);
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
				if(!tabI_ligada) { 
					tabInstrumento = new CTabItem(tabFolder, SWT.NONE);
					tabInstrumento.addDisposeListener(new DisposeListener() {
						public void widgetDisposed(DisposeEvent arg0) {
							tabI_ligada = false;
						}
					});
					tabInstrumento.setShowClose(true);
					tabInstrumento.setText("Instrumentos");
				
					compInstrumento = new TelaInstrumento(tabFolder, SWT.NONE);
					tabInstrumento.setControl(compInstrumento);
					tabI_ligada = true;
				}
			}
		});
		mntminstrumento.setText("&Instrumento");
		
		MenuItem mntmbandas = new MenuItem(menu_1, SWT.NONE);
		mntmbandas.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!tabB_ligada) { 
					tabBandas = new CTabItem(tabFolder, SWT.NONE);
					tabBandas.addDisposeListener(new DisposeListener() {
						public void widgetDisposed(DisposeEvent arg0) {
							tabB_ligada = false;
						}
					});
					tabBandas.setShowClose(true);
					tabBandas.setText("Bandas");
				
					compBandas = new TelaBandas(tabFolder, SWT.NONE);
					tabBandas.setControl(compBandas);
					tabB_ligada = true;
				}
			}
		});
		mntmbandas.setText("&Bandas");
		
		MenuItem mntmcidade = new MenuItem(menu_1, SWT.NONE);
		mntmcidade.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!tabC_ligada) { 
					tabCidade = new CTabItem(tabFolder, SWT.NONE);
					tabCidade.addDisposeListener(new DisposeListener() {
						public void widgetDisposed(DisposeEvent arg0) {
							tabC_ligada = false;
						}
					});
					tabCidade.setShowClose(true);
					tabCidade.setText("Cidade");
				
					compCidade = new TelaCidade(tabFolder, SWT.NONE);
					tabCidade.setControl(compCidade);
					tabC_ligada = true;
				}
			}
		});
		mntmcidade.setText("&Cidade");
		
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

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(540, 470);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
