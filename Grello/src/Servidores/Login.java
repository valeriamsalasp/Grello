package Servidores;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import Grello.LeerProperties;
import Grello.Queries;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject data = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		
		Queries db = new Queries();
		JSONObject userData = new JSONObject();
		HttpSession sesion = request.getSession(true);
		/*String username = request.getParameter("user_username");
		String password = request.getParameter("user_password");*/
		Properties dataSource = new LeerProperties().getFile("C:\\Users\\Gressia\\git\\Grello\\Grello\\WebContent\\query.properties");

		if(sesion.isNew()) {
			try {
				out.println("hola");
				userData = db.ObtenerDatos(data);
				System.out.println(userData);
				//JSONObject users = db.ObtenerDatos(username, password);
				if(userData.length() > 0) {
					storeValue(sesion, userData);
					mensaje.put("status", 200).put("userData", userData);
					System.out.println("Todo realizado con exito");
					response.sendRedirect("Grello/loggedin/index.html");
				}else {
					mensaje.put("status", 409).put("response", "Invalid username or password");
					sesion.invalidate();
				}
			}catch( SQLException e) {
				e.printStackTrace();
				sesion.invalidate();
			} finally {
				db.closeResources();
			}
		} 
		out.println(userData.toString());
		
	}
	
	private void storeValue(HttpSession session, JSONObject value) {
		if(value ==null) {
			session.setAttribute("session", "");
		}
		else {
			session.setAttribute("session", value);
		}
	}

}
