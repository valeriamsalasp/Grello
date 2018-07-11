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
 * Servlet implementation class Login
 */

@WebServlet("/Registro")
public class Registro extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Registro() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject data = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		Queries db = new Queries();
		
		try {
			System.out.println("comenzamos");
			if(!db.VerificarUsuario(data.getString("user_username"))) {
				System.out.println("Usuario correcto");
				if(!db.VerificarCorreo(data.getString("user_email"))) {
					System.out.println("email correcto");
					boolean status = db.Registrar(data);
					if (status) {
						mensaje.put("status", 200).put("response", "El usuario fue creado");
					}else {
						System.out.println("El usuario no fue creado");
					}
				}else {
					mensaje.put("status", 500).put("response", "este correo ya existe");
				}
			}else {
				mensaje.put("status", 500).put("response:","Este nombre de usuario ya existe");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeResources();
		}
		out.println(mensaje.toString());
		
	}
}
