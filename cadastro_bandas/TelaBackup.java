package cadastro_bandas;
import org.eclipse.swt.widgets.Composite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class TelaBackup extends Composite {
	private Button btnCidade;
	private Button btnBanda;
	private Button btnInstrumento;
	private Button btnBandainstrumento;
	private Button[] botoes;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TelaBackup(Composite parent, int style) {
		super(parent, style);
		
		Group grpSelecionarTabelas = new Group(this, SWT.NONE);
		grpSelecionarTabelas.setText("Selecionar Tabelas");
		grpSelecionarTabelas.setBounds(10, 10, 217, 155);
		
		btnCidade = new Button(grpSelecionarTabelas, SWT.CHECK);
		btnCidade.setBounds(10, 30, 93, 16);
		btnCidade.setText("Cidade");
		
		btnBanda = new Button(grpSelecionarTabelas, SWT.CHECK);
		btnBanda.setBounds(10, 52, 93, 16);
		btnBanda.setText("Banda");
		
		btnInstrumento = new Button(grpSelecionarTabelas, SWT.CHECK);
		btnInstrumento.setBounds(10, 74, 93, 16);
		btnInstrumento.setText("Instrumento");
		
		btnBandainstrumento = new Button(grpSelecionarTabelas, SWT.CHECK);
		btnBandainstrumento.setBounds(10, 96, 135, 16);
		btnBandainstrumento.setText("Banda_instrumento");
		
		Button btnBackup = new Button(this, SWT.NONE);
		btnBackup.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unused")
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (Button b : botoes) {
					//ignorar os checkbox - carregar todas as tabelas
					//if(b.getSelection()) {
					if(true) {
						try {
							FileWriter fw = new FileWriter(new File(b.getText() + ".txt"), false);
							BufferedWriter bw = new BufferedWriter(fw);
							if(b.getText().equals("Cidade")) {
								salvarCidades(bw);
							} else if(b.getText().equals("Banda")) {
								salvarBandas(bw);
							} else if(b.getText().equals("Instrumento")) {
								salvarInstrumentos(bw);
							} else if(b.getText().equals("Banda_instrumento")) {
								salvarBIs(bw);
							}
							fw.close();
						}
						catch(Exception f) {
							f.printStackTrace();
						}
					} else {
						//código nunca usado, mas eis o que faria se o...
						//...botão não foi selecionado para backup, então apagar o backup atual
						File fw = new File(b.getText() + ".txt");
						fw.delete();
					}
				}
			}
		});
		btnBackup.setBounds(10, 171, 75, 25);
		btnBackup.setText("Backup");
		
		Button btnRestaurar = new Button(this, SWT.NONE);
		btnRestaurar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//apagar todas as tabelas
				String sql = "delete from banda_instrumento";
				String sql2 = "delete from banda";
				String sql3 = "delete from instrumento";
				String sql4 = "delete from cidade";
				try {
					Principal.conn.prepareStatement(sql).executeUpdate();
					Principal.conn.prepareStatement(sql2).executeUpdate();
					Principal.conn.prepareStatement(sql3).executeUpdate();
					Principal.conn.prepareStatement(sql4).executeUpdate();
				} catch (Exception exc) {
					exc.printStackTrace();
				}
				for (Button b : botoes) {
					//ignorar os checkbox - carregar todas as tabelas
					//if(b.getSelection()) {
					if(true) {
						boolean arquivoExiste = false;
						try {
							FileReader fr = new FileReader(new File(b.getText() + ".txt"));
							BufferedReader br = new BufferedReader(fr);
							if(br.readLine()!=null) {
								arquivoExiste = true;
							}
							br.close();
							fr.close();
							fr = new FileReader(new File(b.getText() + ".txt"));
							if (arquivoExiste) {
								br = new BufferedReader(fr);
								if(b.getText().equals("Cidade")) {
									carregarCidades(br);
								} else if(b.getText().equals("Banda")) {
									carregarBandas(br);
								} else if(b.getText().equals("Instrumento")) {
									carregarInstrumentos(br);
								} else if(b.getText().equals("Banda_instrumento")) {
									carregarBIs(br);
								}
							}
							br.close();
							fr.close();
							
						} catch(Exception f) {
							f.printStackTrace();
						}
					}
					//Apagar os arquivos depois da restauração
					//File fw = new File(b.getText() + ".txt");
					//fw.delete();
				}
			}
		});
		btnRestaurar.setBounds(152, 171, 75, 25);
		btnRestaurar.setText("Restaurar");

		botoes = new Button[]{btnCidade, btnBanda, btnInstrumento, btnBandainstrumento};
	}

	protected void carregarCidades(BufferedReader br) {
		String sql = "insert into cidade (nome, uf) values (?,?)";
		String linha;
		try {
		while((linha=br.readLine())!=null) {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			String[] campos = linha.split(",");
			ps.setString(1, campos[0]);
			ps.setString(2, campos[1]);
			ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void carregarBandas(BufferedReader br) {
		String sql = "insert into banda (nome, estilo, integrantes, cd, cidade) "
				+ "values (?,?,?,?,?)";
		String linha;
		try {
		while((linha=br.readLine())!=null) {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			String[] campos = linha.split(",");
			ps.setString(1, campos[0]);
			ps.setString(2, campos[1]);
			ps.setInt(3, Integer.parseInt(campos[2]));
			ps.setString(4, campos[3]);
			Cidade buscaPeloNome;
			try {
				buscaPeloNome = Cidade.listar("%"+campos[4]+"%").get(0);
			} catch (Exception e) {
				//não achamos a cidade, então não incluir essa banda
				break;
			}
			//System.out.println(buscaPeloNome.getNome());
			//System.out.println(buscaPeloNome.getId());
			ps.setInt(5, buscaPeloNome.getId());
			ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void carregarInstrumentos(BufferedReader br) {
		String sql = "insert into instrumento (nome, tipo) values (?,?)";
		String linha;
		try {
		while((linha=br.readLine())!=null) {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			String[] campos = linha.split(",");
			ps.setString(1, campos[0]);
			ps.setString(2, campos[1]);
			ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void carregarBIs(BufferedReader br) {
		String sql = "insert into banda_instrumento (instrumento, banda) "
				+ "values (?,?)";
		String linha;
		try {
		while((linha=br.readLine())!=null) {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			String[] campos = linha.split(",");
			Banda bandaPeloNome;
			Instrumento instPeloNome;
			try {
				bandaPeloNome = Banda.listar("%"+campos[0]+"%").get(0);
				instPeloNome = Instrumento.listar("%"+campos[1]+"%").get(0);
			} catch (Exception e) {
				//não achamos banda ou instrumento, não cadastrar esse item
				break;
			}
			//System.out.println(buscaPeloNome.getNome());
			//System.out.println(buscaPeloNome.getId());
			ps.setInt(1, instPeloNome.getId());
			ps.setInt(2, bandaPeloNome.getId());
			ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void salvarCidades(BufferedWriter bw) {
		String sql = "select * from cidade order by nome";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				//c.setId(rs.getInt("id"));
				bw.append(rs.getString("nome") + "," + rs.getString("uf") + "\n");
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void salvarBandas(BufferedWriter bw) {
		String sql = "select * from banda order by nome";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				//c.setId(rs.getInt("id"));
				Cidade c = new Cidade().buscaPorID(rs.getInt("cidade"));
				
				bw.append(rs.getString("nome") + "," + rs.getString("estilo") 
				+ "," + rs.getInt("integrantes") + "," + rs.getString("cd")
				+ "," + c.getNome() + "\n");
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void salvarInstrumentos(BufferedWriter bw) {
		String sql = "select * from instrumento order by nome";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				bw.append(rs.getString("nome") + "," + rs.getString("tipo") + "\n");
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void salvarBIs(BufferedWriter bw) {
		String sql = "select * from banda_instrumento";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Banda b = new Banda().buscaPorID(rs.getInt("banda"));
				Instrumento i = new Instrumento().buscaPorID(rs.getInt("instrumento"));
				bw.append(b.getNome() + "," + i.getNome() + "\n");
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
