package Grello;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import org.json.JSONObject;

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
		this.rs = executeStatement("BuscarUsuario", value);
		return this.rs.next();
	}
	
	//Verificar el email
	public boolean VerificarCorreo(String value) throws SQLException{
		this.rs = executeStatement("BuscarCorreo", value);
		return this.rs.next();
	}
	
	//Verifica si el usuario existe y retorna sus datos
	public JSONObject ObtenerDatos (JSONObject user)throws SQLException{
		String encriptada = Encriptamiento.HashPassword(user.getString("user_password"));
		this.rs = executeStatement("VerificarIngreso", user.getString("user_username"), encriptada);
		return this.getData();
	}
	
	
	//Registrar la cuenta
	public boolean Registrar(JSONObject data) throws SQLException{
		String encriptada = Encriptamiento.HashPassword(data.getString("user_password"));
		int i = executeUpdate("IngresarUsuario", data.getString("user_name"), data.getString("user_last_name"),
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
	
	//Buscar Tablero
	public JSONObject BuscarTablero (JSONObject user)throws SQLException{
		this.rs = executeStatement("BuscarTablero", user.getString("board_id"));
		return this.getData();
	}
	
	
	//Crear Tableros
	public boolean CrearTablero(JSONObject data) throws SQLException{
		int i = executeUpdate("CrearTablero", data.getString("board_name"), data .getString("user_id"));
		return (i == 1)?true:false;
	}
	
	//BorrarTablero
	public boolean BorrarTablero(JSONObject data) throws SQLException{
		int i = executeUpdate("BorrarTablero", data.getString("boar_id"));
		return (i == 1)?true:false;
	}
	
	//Actualizar Tablero
	public boolean ActualizarTablero(JSONObject data) throws SQLException{
		int i = executeUpdate("ActualizarTablero", data.getString("board_name"), data .getString("board_id"));
		return (i == 1)?true:false;
	}
	
	//Insertar en la tabla de user board
	public boolean InsertarUsuTa(String value, JSONObject data) throws SQLException{
		int i = executeUpdate("InsertarUsuTa", value, data .getString("user_id"));
		return (i == 1)?true:false;
	}
	
	//Buscar Columna
	public boolean BuscarColumna(String value) throws SQLException{
		this.rs = executeStatement("BuscarColumna", value);
		return this.rs.next();
	}
	
	//Crear Columna 
	public boolean CrearColumna(JSONObject data) throws SQLException{
		int i = executeUpdate("CrearColumna", data.getString("board_id"), data .getString("column_name"));
		return (i == 1)?true:false;
	}
	
	//Borrar Columna
	public boolean BorrarColumna(JSONObject data) throws SQLException{
		int i = executeUpdate("BorrarColumna", data.getString("column_id"));
		return (i == 1)?true:false;
	}
	
	//Actualizar Columna 
	public boolean ActualizarColumna(JSONObject data) throws SQLException{
		int i = executeUpdate("ActualizarColumna", data.getString("column_name"), data .getString("column_id"));
		return (i == 1)?true:false;
	}
	
	//Buscar Tarjeta
	public boolean BuscarTarjeta(String value) throws SQLException{
		this.rs = executeStatement("BuscarTarjeta", value);
		return this.rs.next();
	}
	
	//Crear Tarjeta
	public boolean CrearTarjeta(JSONObject data) throws SQLException{
		int i = executeUpdate("CrearTarjeta", data.getString("column_id"), data.getString("user_id"), data .getString("card_name")
				, data .getString("card_description"));
		return (i == 1)?true:false;
	}
	
	//Borrar Tarjeta
	public boolean BorrarTarjeta(JSONObject data) throws SQLException{
		int i = executeUpdate("BorrarTarjeta", data.getString("card_id"));
		return (i == 1)?true:false;
	}
	
	//Actualizar el nombre de la Tarjeta
	public boolean ActualizarTarjetaNo(JSONObject data) throws SQLException{
		int i = executeUpdate("ActualizarTarjetaNo", data.getString("card_name"), data .getString("card_id"));
		return (i == 1)?true:false;
	}
	
	//Actualizar el descripcion de la Tarjeta
		public boolean ActualizarTarjetaDe(JSONObject data) throws SQLException{
			int i = executeUpdate("ActualizarTarjetaDe", data.getString("card_description"), data .getString("card_id"));
			return (i == 1)?true:false;
		}
	
	
	
}
