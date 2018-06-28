package Grello;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import org.json.JSONObject;
import java.util.Properties;

public class Queries extends BDConexion {
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	
	public Queries() {
		super();
	}
	
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
		Properties leer= new LeerProperties().getFile("C:\\Users\\Gressia\\git\\Grello\\Grello\\WebContent\\query.properties");
		this.rs = executeStatement(leer.getProperty("BuscarUsuario"), value);
		return this.rs.next();
	}
	
	//Verificar el email
	public boolean VerificarCorreo(String value) throws SQLException{
		Properties leer= new LeerProperties().getFile("C:\\Users\\Gressia\\git\\Grello\\Grello\\WebContent\\query.properties");
		this.rs = executeStatement(leer.getProperty("BuscarCorreo"), value);
		return this.rs.next();
	}
	
	//Verifica si el usuario existe y retorna sus datos
	public JSONObject ObtenerDatos (JSONObject user)throws SQLException{
		String encriptada = Encriptamiento.HashPassword(user.getString("user_password"));
		Properties leer= new LeerProperties().getFile("C:\\Users\\Gressia\\git\\Grello\\Grello\\WebContent\\query.properties");
		this.rs = executeStatement(leer.getProperty("VerificarIngreso"), user.getString("user_username"), encriptada);
		return this.getData();
	}
	
	
	//Registrar la cuenta
	public boolean Registrar(JSONObject data) throws SQLException{
		String encriptada = Encriptamiento.HashPassword(data.getString("user_password"));
		Properties leer= new LeerProperties().getFile("C:\\Users\\Gressia\\git\\Grello\\Grello\\WebContent\\query.properties");
		System.out.println(leer);
		int i = executeUpdate(leer.getProperty("IngresarUsuario"), data.getString("user_name"), data.getString("user_last_name"),
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
