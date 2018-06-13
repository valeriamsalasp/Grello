package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BDConexion {
	public PreparedStatement stmt;
	public Connection con;
	public BDConexion (){
		  try{
			  this.con = null;		
		    Class.forName ("org.postgresql.Driver");
		    this.con = DriverManager.getConnection (   
		    "jdbc:postgresql://localhost:1099/Trello", "postgres", "10021999");   
		  } 
		  catch (Exception e) {
			e.printStackTrace ();
		  }	
	}
	public ResultSet executeStatement(String query,String value) throws SQLException{
			this.stmt = this.con.prepareStatement(query);
			this.stmt.setObject(1, value);
			
			return this.stmt.executeQuery();
	}
}

