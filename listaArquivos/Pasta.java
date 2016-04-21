package listaArquivos;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class Pasta extends Shell {
	private Text textDiretorio;
	private Table table;
	private ArrayList<File> lista = new ArrayList<File>();

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Pasta shell = new Pasta(display);
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
	public Pasta(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		Label lblDiretorio = new Label(this, SWT.NONE);
		lblDiretorio.setBounds(10, 16, 55, 15);
		lblDiretorio.setText("Diret\u00F3rio");
		
		textDiretorio = new Text(this, SWT.BORDER);
		textDiretorio.setBounds(71, 10, 251, 21);
		
		Button btnOk = new Button(this, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listFiles();
				fillTable();
			}
		});
		btnOk.setBounds(349, 6, 75, 25);
		btnOk.setText("OK");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				File f = lista.get(table.getSelectionIndex());
				f.delete();
				listFiles();
				fillTable();
			}
		});
		table.setBounds(10, 37, 414, 215);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		
		TableColumn tblclmnNome = new TableColumn(table, SWT.NONE);
		tblclmnNome.setWidth(141);
		tblclmnNome.setText("Nome");
		
		TableColumn tblclmnTamanho = new TableColumn(table, SWT.NONE);
		tblclmnTamanho.setWidth(119);
		tblclmnTamanho.setText("Tamanho");
		
		TableColumn tblclmnDataMod = new TableColumn(table, SWT.NONE);
		tblclmnDataMod.setWidth(126);
		tblclmnDataMod.setText("Data mod.");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	private void listFiles() {
		lista.clear();
		File d = new File(textDiretorio.getText());
		if(d.isDirectory()) {
			File[] vetor = d.listFiles();
			for (File f : vetor)
				lista.add(f);
		}
	}
	
	private void fillTable() {
		table.setItemCount(0);
		for (File f : lista) {
			TableItem it= new TableItem(table, SWT.NONE);
			it.setText(0, f.getName());
			it.setText(1, f.length()/1024+"KB");
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			it.setText(2, df.format(new Date(f.lastModified())));
		}
	}
}
