package cadastro_bandas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Cidade {
	private int id;
	private String nome;
	private String uf;
	
	public void cadastra() {
		String sql = "insert into cidade (nome, uf) values (?,?)";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setString(1, getNome());
			ps.setString(2, getUf());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void altera() {
		String sql = "update cidade set nome=?, uf=? where id=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setString(1, getNome());
			ps.setString(2, getUf());
			ps.setInt(3, getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exclui() {
		String sql = "delete from cidade where id=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setInt(1, getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Cidade> listar(String filtro) {
		ArrayList<Cidade> lista = new ArrayList<Cidade>();
		String sql = "select * from cidade order by nome";
		if(filtro != null) {
			sql = "select * from cidade where nome like ? order by nome";
		}
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			
			if(filtro != null) {
				ps.setString(1, filtro);
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Cidade c = new Cidade();
				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				c.setUf(rs.getString("uf"));
				lista.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public Cidade buscaPorID(int id) {
		Cidade c = new Cidade();
		String sql = "select * from cidade where id=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				c.setUf(rs.getString("uf"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getUf() {
		return uf;
	}
	
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public String[] toArray() {
		return new String[]{getId() + "", getNome(), getUf()};
	}

	
}
