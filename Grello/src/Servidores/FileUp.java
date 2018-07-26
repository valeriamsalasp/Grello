package Servidores;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONObject;

import Grello.Queries;

/**
 * Servlet implementation class FileUp
 */
@MultipartConfig
@WebServlet("/FileUp")
public class FileUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileUp() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.print("{\"status\":200,\"res\":\"OK/GET\"}");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		
		Queries db = new Queries();
		Part file = request.getPart("file");
		InputStream filecontent = file.getInputStream();
		OutputStream os = null;
		
		System.out.println("Estamos Aqui FileUp");
		Integer user_id = Integer.parseInt(request.getParameter("user_id"));
		Integer card_id = Integer.parseInt(request.getParameter("card_id"));
		String file_name = getFileName(file);
		String file_url = file.getName();
		System.out.println("user_id: "+user_id+" card_id: "+card_id+" file_name: "+file_name+" file_url: "+file_url);
		try {
			String baseDir = "C:/Users/Gressia/Downloads/Prueba";
			//String baseDir = "WebContent";
			//os = new FileOutputStream(baseDir );
			boolean status = db.AgregarArchivo(card_id, user_id, file_url, file_name);
			os = new FileOutputStream(baseDir + "/" + this.getFileName(file));
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
			if( status) {
				mensaje.put("status", 200).put("response","La subida de archivo se realizo con exito");
				System.out.println("La subida de archivo se realizo con exito");
				}
			System.out.println("Todo Bien");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (filecontent != null) {
				filecontent.close();
			}
			if (os != null) {
				os.close();
			}
		}
		out.println(mensaje.toString());
	}
	
	// Esta funcion permite obtener el nombre del archivo
	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
