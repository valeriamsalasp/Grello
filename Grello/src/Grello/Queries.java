package Grello;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import org.json.JSONObject;

public class Queries extends BDConexion {
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	
	private JSONObject getData() throws SQLException {
		JSONObject userData = new JSONObject();
		if(this.rs.next()) {		
			this.rsmd = rs.getMetaData();
			for(int i = 1; i <= this.rsmd.getColumnCount(); i++) {
				userData.put(rsmd.getColumnLabel(i), rs.getObject(i));
			}
		}
		return userData;
	}
	
	//Verificar el usuario
	public boolean VerificarUsuario(String value) throws SQLException{
		this.rs = executeStatement(" SELECT * FROM users WHERE user_username = ?", value);
		return this.rs.next();
	}
	
	//Verificar el email
	public boolean VerificarCorreo(String value) throws SQLException{
		this.rs = executeStatement("SELECT * FROM users WHERE user_email = ?", value);
		return this.rs.next();
	}
	
	//Verifica si el usuario existe y retorna sus datos
	public JSONObject ObtenerDatos (JSONObject user)throws SQLException{
		String encriptada = Encriptamiento.HashPassword(user.getString("user_password"));
		this.rs = executeStatement("SELECT user_id, type_des, user_username, user_email, user_name, user_last_name FROM user INNER JOIN type_user ON type_user.type_id = users.type_id WHERE user_username = ? AND user_password = ?", user.getString("user_username"), encriptada);
		return this.getData();
	}
	
	
	//Registrar la cuenta
	public boolean Registrar(JSONObject data) throws SQLException{
		String encriptada = Encriptamiento.HashPassword(data.getString("user_password"));
		int i = executeStatement2("INSERT INTO users(type_id,user_name, user_last_name, user_username, user_password, user_email) VALUES((SELECT type_id FROM type_user WHERE type_des = 'User'), ?, ?, ?, ?, ?);", data.getString("user_name"), data.getString("user_last_name"),
				data.getString("user_username"), encriptada, data.getString("user_email"));
	
		return (i == 1)?true:false;
	}
	
	//Cerrar los recursos	public void closeResources() {
	public void closeResources() {
		try {
			closeMainResource();
			if(this.rs != null)
				this.rs.close();
		} catch (SQLException e) {
			System.out.println("Problema al cerrar los recursos");
			e.printStackTrace();
		}
	}
	
	
}
