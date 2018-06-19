package Grello;

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
		    "jdbc:postgresql://localhost:1099/Grello", "postgres", "10021999");   
		  } 
		  catch (Exception e) {
			e.printStackTrace ();
		  }	
	}
	public ResultSet executeStatement(String query,Object...value) throws SQLException{
			this.stmt = this.con.prepareStatement(query);
			for(int i=1; i < value.length; i++ )
			this.stmt.setObject(i + 1, value);
			
			return this.stmt.executeQuery();
			
	}
	
	public int executeStatement2(String query,Object...value) throws SQLException{
		this.stmt = this.con.prepareStatement(query);
		for(int i=1; i < value.length; i++ )
		this.stmt.setObject(i + 1, value);
		
		return this.stmt.executeUpdate();
		
}
	
	protected void closeMainResource() throws SQLException {
		if(this.stmt != null) 
			this.stmt.close();
	}
}

