package cadastro_bandas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BandaInstrumento {
	private Instrumento i;
	private Banda b;

	public void cadastrar() {
		String sql = "insert into banda_instrumento (instrumento, banda) "
				+ "values (?,?)";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setInt(1, i.getId());
			ps.setInt(2, b.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			//SQLException acontece no caso de duplicar a inclusão
			e.printStackTrace();
		}
	}
	
	public void excluir() {
		String sql = "delete from banda_instrumento where instrumento=? and banda=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setInt(1, i.getId());
			ps.setInt(2, b.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Instrumento> listar(int idBanda) {
		ArrayList<Instrumento> lista = new ArrayList<Instrumento>();
		String sql = "select * from banda_instrumento where banda=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setInt(1, idBanda);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				lista.add(new Instrumento().buscaPorID(rs.getInt("instrumento")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public Instrumento getI() {
		return i;
	}

	public void setI(Instrumento i) {
		this.i = i;
	}

	public Banda getB() {
		return b;
	}

	public void setB(Banda b) {
		this.b = b;
	}
}
