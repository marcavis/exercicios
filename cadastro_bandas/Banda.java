package cadastro_bandas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Banda {
	private int id;
	private String nome;
	private String estilo;
	private int integrantes;
	private String cd;
	private Cidade cidade = new Cidade();
	
	public void cadastrar() {
		String sql = "insert into banda (nome, estilo, integrantes, cd, cidade) "
				+ "values (?,?,?,?,?)";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setString(1, getNome());
			ps.setString(2, getEstilo());
			ps.setInt(3, getIntegrantes());
			ps.setString(4, getCD());
			ps.setInt(5, getCidade().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterar() {
		String sql = "update banda set nome=?, estilo=?, integrantes=?, "
				+ "cd=?, cidade=? where id=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setString(1, getNome());
			ps.setString(2, getEstilo());
			ps.setInt(3, getIntegrantes());
			ps.setString(4, getCD());
			ps.setInt(5, getCidade().getId());
			ps.setInt(6, getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excluir() {
		String sql = "delete from banda where id=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setInt(1, getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Banda> listar(String filtro) {
		ArrayList<Banda> lista = new ArrayList<Banda>();
		String sql = "select * from banda order by nome";
		if(filtro != null) {
			sql = "select * from banda where nome like ? order by nome";
		}
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			
			if(filtro != null) {
				ps.setString(1, filtro);
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Banda b = new Banda();
				b.setId(rs.getInt("id"));
				b.setNome(rs.getString("nome"));
				b.setEstilo(rs.getString("estilo"));
				b.setIntegrantes(rs.getInt("integrantes"));
				b.setCD(rs.getString("cd"));
				b.setCidade(new Cidade().buscaPorID(rs.getInt("cidade")));
				lista.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}

	public int getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(int integrantes) {
		this.integrantes = integrantes;
	}

	public String getCD() {
		return cd;
	}

	public void setCD(String cd) {
		this.cd = cd;
	}
	
	public Cidade getCidade() {
		return cidade;
	}
	
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public String[] toArray() {
		return new String[]{getNome(), getEstilo(), getIntegrantes()+"", getCD(), getCidade().getNome()};
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
