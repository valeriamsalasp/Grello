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
 * Servlet implementation class Columna
 */
@WebServlet("/Columna")
public class Columna extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Columna() {
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
		
		JSONObject columnData = new JSONObject();
		
		String a = data.getString("tipo").toString();
		
		if(a == "crear") {
			try {
				System.out.println("comenzamos");
				if(!db.BuscarColumna(data.getString("column_name"))) {
					System.out.println("Nombre de la columna correcta");
					boolean status = db.CrearColumna(data);
					if (status) {
						System.out.println("La columna fue creada");
						columnData = db.InformacionColumna(data);
						if(columnData.length() > 0) {
							mensaje.put("status", 200).put("response",columnData);
							System.out.println("Todo realizado con exito");
						}else {
							mensaje.put("status", 200).put("response","No se pudo traer los datos de la columna");
						}
					}else {
						mensaje.put("status", 500).put("response","La columna no fue creado");
					}
				}else {
					mensaje.put("status", 400).put("response:","Este nombre de columna ya existe");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			} finally {
				db.closeResources();
			}
			
		}else if(a == "actualizar") {
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
			
		}else if(a == "borrar") {
			try {
				boolean status = db.BorrarColumna(data);
				if (status) {
					mensaje.put("status", 200).put("response", "La columna fue borrada");
				}else {
					mensaje.put("status", 500).put("response","No se puedo bora");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			} finally {
				db.closeResources();
			}
			
		}
		out.println(mensaje.toString());
		
	}

}
