package userAction;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import conexion.Queries;

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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject data = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		HttpSession sesion = request.getSession();
		Queries db = new Queries();
		
		if(sesion.isNew()) {
			try {
				if(!db.checkusuario(data.getString("username"))) {
					if(!db.checkemail(data.getString("user_email"))) {
						mensaje.put("status", 200).put("response", "el usuario fue creado");
					}else {
						mensaje.put("status", 500).put("response", "este correo ya existe");
					}
				}else {
					mensaje.put("status", 500).put("response:","Este username ya existe");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			out.println(mensaje.toString());
		}
		
	}

}
