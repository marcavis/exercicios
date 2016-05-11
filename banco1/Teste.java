package banco1;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Teste {
	public static void main(String[] args) {
		try {
			Connection conn = Conexao.conn();
			String sql = "insert into cidade (nome, uf) values ('Tubarão','SC')";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
