package Servidores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
		JSONObject userData = new JSONObject();
		
		String a = data.getString("tipo");
		boolean b = true;
		System.out.println(data);
		System.out.println("a: "+a);
		
		if(b) {
			System.out.println("Entramos en crear Tablero");
			try {
				System.out.println("comenzamos");
				if(db.BuscarTablero(data).length() < 0) {
					System.out.println("Nombre del tablero correcto");
					boolean tablero = db.CrearTablero(data);
					userData = db.BuscarTablero(data);
					if(userData.length() > 0) {
						if(tablero) {
							boolean status = db.InsertarUsuTa(userData.getString("board_id"), data);
							mensaje.put("satus", 200).put("response","Ya se creo el tablero");
							if (status) {
								mensaje.put("status", 200).put("response", "Ya se le asigno el tablero al usuario");
							}else {
								mensaje.put("status", 500).put("response","No se le puedo asignar el tablero a el usuario\"");
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
			out.println(mensaje.toString());
		}else if(a == "actualizar") {
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
			out.println(mensaje.toString());
		}else if(a == "borrar") {
			System.out.println("Entramos en borrar Tablero");
			try {
				boolean status = db.BorrarTablero(data);
				if (status) {
					mensaje.put("status", 200).put("response", "El tablero fue borrado");
				}else {
					mensaje.put("status", 500).put("response","No se puedo bora");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			} finally {
				db.closeResources();
			}
			out.println(mensaje.toString());
		}
		
	}

}
