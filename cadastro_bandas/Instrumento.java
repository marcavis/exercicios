package cadastro_bandas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Instrumento {
	public String nome;
	public String tipo;
	public int id;
	
	public void cadastra() {
		String sql = "insert into instrumento (nome, tipo) values (?,?)";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setString(1, getNome());
			ps.setString(2, getTipo());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void altera() {
		String sql = "update instrumento set nome=?, tipo=? where id=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setString(1, getNome());
			ps.setString(2, getTipo());
			ps.setInt(3, getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Instrumento> listar(String filtro) {
		ArrayList<Instrumento> lista = new ArrayList<Instrumento>();
		String sql = "select * from instrumento order by nome";
		if(filtro != null) {
			sql = "select * from instrumento where nome like ? order by nome";
		}
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			
			if(filtro != null) {
				ps.setString(1, filtro);
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Instrumento i = new Instrumento();
				i.setId(rs.getInt("id"));
				i.setNome(rs.getString("nome"));
				i.setTipo(rs.getString("tipo"));
				lista.add(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public void exclui() {
		String sql = "delete from instrumento where id=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setInt(1, getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Instrumento buscaPorID(int id) {
		Instrumento i = new Instrumento();
		String sql = "select * from instrumento where id=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				i.setId(rs.getInt("id"));
				i.setNome(rs.getString("nome"));
				i.setTipo(rs.getString("tipo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String[] toArray() {
		return new String[]{getNome(), getTipo()};
	}
}
