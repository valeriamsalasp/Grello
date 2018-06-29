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
 * Servlet implementation class Tarjetas
 */
@WebServlet("/Tarjetas")
public class Tarjetas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Tarjetas() {
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
		
		String a = data.getString("tipo");
		
		if(a == "crear") {
			try {
				System.out.println("comenzamos");
				if(!db.BuscarTarjeta(data.getString("card_name"))) {
					System.out.println("Nombre de laa tarjeta correcta");
					boolean status = db.CrearTarjeta(data);
					if (status) {
						mensaje.put("status", 200).put("response", "El tablero fue creado");
					}else {
						mensaje.put("status", 500).put("response","La tarjeta no fue creada");
					}
				}else {
					mensaje.put("status", 400).put("response:","Este nombre de tarjeta ya existe");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			} finally {
				db.closeResources();
			}
			out.println(mensaje.toString());
		}else if(a == "actualizarNo") {
			try {
				boolean status = db.ActualizarTarjetaNo(data);
				if (status) {
						mensaje.put("status", 200).put("response", "La tarjeta fue actualizada");
				}else {
					mensaje.put("status", 500).put("response:","La tarjeta no fue actualizada");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			} finally {
				db.closeResources();
			}
			out.println(mensaje.toString());
		}else if(a == "actualizarDe") {
			try {
				boolean status = db.ActualizarTarjetaDe(data);
				if (status) {
						mensaje.put("status", 200).put("response", "La tarjeta fue actualizada");
				}else {
					mensaje.put("status", 500).put("response:","La tarjeta no fue actualizada");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			} finally {
				db.closeResources();
			}
			out.println(mensaje.toString());}else if(a == "borrar") {
			try {
				boolean status = db.BorrarTarjeta(data);
				if (status) {
					mensaje.put("status", 200).put("response", "La tarjeta fue borrada");
				}else {
					mensaje.put("status", 500).put("response","No se puedo borar la tarjeta");
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
