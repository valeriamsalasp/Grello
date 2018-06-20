package Grello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BDConexion {
	public PreparedStatement stmt;
	public Connection con;
	public ResultSet rs;
	
	public BDConexion(){
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
			for(int i=0; i < value.length; i++ )
				this.stmt.setObject(i + 1, value[i]);
			
			//this.rs = this.stmt.executeQuery();
			
			return this.stmt.executeQuery();
			
			
	}
	
	public int executeStatement2(String query,Object...value) throws SQLException{
		this.stmt = this.con.prepareStatement(query);
		for(int i=0; i < value.length; i++ )
		this.stmt.setObject(i + 1, value[i]);
		
		return this.stmt.executeUpdate();
		
	}
	
	protected void closeMainResource() throws SQLException {
		if(this.stmt != null) 
			this.stmt.close();
	}
	
	/*public int executeStatement(String query,String value) throws SQLException{
		this.stmt = this.con.prepareStatement(query);
		this.stmt.setObject(1, value);
		
		return this.stmt.executeUpdate();
		
	}
	public int executeStatement1(String query,String val1, String val2) throws SQLException{
		this.stmt = this.con.prepareStatement(query);
		this.stmt.setObject(1, val1);
		this.stmt.setObject(1, val2);
		
		return this.stmt.executeUpdate();
		
	}
	public int executeStatement2(String query,String val1, String val2, String val3, String val4, String val5) throws SQLException{
		this.stmt = this.con.prepareStatement(query);
		this.stmt.setObject(1, val1);
		this.stmt.setObject(1, val2);
		this.stmt.setObject(1, val3);
		this.stmt.setObject(1, val4);
		this.stmt.setObject(1, val5);
		
		return this.stmt.executeUpdate();
		
	}*/
	
	
}

