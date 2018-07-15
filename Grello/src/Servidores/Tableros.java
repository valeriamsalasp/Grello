package Servidores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Grello.Queries;

/**
 * Servlet implementation class Tableros
 */
@WebServlet("/Tableros")
public class Tableros extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Tableros() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject data = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		Queries db = new Queries();
		JSONObject userBoard = new JSONObject();
		ArrayList<JSONObject> dataBoard = new ArrayList<JSONObject>();
	
		
		String a = data.getString("tipo").toString();
		System.out.println(data);
		System.out.println("a: "+a);
	
		
		if("crear".equals(a)) {
			System.out.println("Entramos en crear Tablero");
			try {
				System.out.println("comenzamos");
				if(!db.BuscarTablero(data.getString("board_name"))) {
					System.out.println("Nombre del tablero correcto");
					boolean status = db.CrearTablero(data);
					 userBoard= db.InformacionTablero(data);
					if(status) {
						if(userBoard.length() > 0) {
							boolean nuevaTabla= db.InsertarUsuTa(userBoard.getInt("board_id"), data);
							System.out.println("Ya se creo el tablero");
							if (nuevaTabla) {
								mensaje.put("status", 200).put("response", userBoard);
								System.out.println("Ya se le asigno el tablero al usuario");
							}else {
								mensaje.put("status", 500).put("response","No se le puedo asignar el tablero a el usuario");
							}
						}else {
							mensaje.put("status", 500).put("response:","No se pudo crear el tablero");
						}
					}else {
					mensaje.put("status", 400).put("response:","No retorno ningun dato la tabla de tablero");
					}
				}else {
					mensaje.put("status", 400).put("response:","Este nombre de tablero ya existe");
			}
			}catch(SQLException e) {
				e.printStackTrace();
			} finally {
				db.closeResources();
			}
			
		}else if("actualizar".equals(a)) {
			System.out.println("Entramos en actualizar Tablero");
			try {
				boolean status = db.ActualizarTablero(data);
				if (status) {
						mensaje.put("status", 200).put("response", "El tablero fue actualizado");
				}else {
					mensaje.put("status", 500).put("response","El tablero no fue actualizado");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			} finally {
				db.closeResources();
			}
			
		}else if("borrar".equals(a)) {
			System.out.println("Entramos en borrar Tablero");
			try {
				boolean tabla = db.BorrarUsuTa(data);
				System.out.println("Fue borrado el tablero de la tabla user_board");
				if(tabla) {
					boolean status = db.BorrarTablero(data);
					if (status) {
						mensaje.put("status", 200).put("response", "El tablero fue borrado");
						System.out.println("El tablero fue borrado de la tabla board");
					}else {
						mensaje.put("status", 500).put("response","No se puedo bora");
					}
				}
			}catch(SQLException e) {
				e.printStackTrace();
			} finally {
				db.closeResources();
			}
			
		}else if("leer".equals(a)) {
			try {
				System.out.println("Comenzamos con la lectura");
				dataBoard = db.LeerTablero(data);
				if(!dataBoard.isEmpty()) {
					mensaje.put("status", 200).put("response", dataBoard);
					System.out.println("Se ha realizado la lectura del tablero");
					System.out.println(dataBoard);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if("leerTipo".equals(a)) {
			try {
			userBoard = db.LeerTipoTablero(data);
				if(userBoard.length() > 0) {
					mensaje.put("status", 200).put("response", userBoard);
					System.out.println("Se realizo la lectura del tipo de tablero");
				}else {
					mensaje.put("status", 200).put("response", "No se pudo realizar la lectura del tipo de tablero");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		out.println(mensaje.toString());
		
	}

}
