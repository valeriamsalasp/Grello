package conexion;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Queries extends BDConexion {
	private ResultSet rs;
	
	public boolean checkusuario(String value) throws SQLException{
		this.rs = executeStatement("SELECT * FROM users WHERE user_name = ?", value);
		return this.rs.next();
	}
	
	public boolean checkemail(String value) throws SQLException{
		this.rs = executeStatement("SELECT * FROM users WHERE user_email = ?", value);
		return this.rs.next();
	}
	
}
