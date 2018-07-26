package Servidores;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

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
 * Servlet implementation class MultFilesUp
 */
@WebServlet("/MultFilesUp")
@MultipartConfig
public class MultFilesUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MultFilesUp() {
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
		JSONObject mensaje = new JSONObject();
		Collection<Part> files = request.getParts();
		InputStream filecontent = null;
		OutputStream os = null;
		Queries db = new Queries();
		System.out.println("Aqui");
		Integer user_id = Integer.parseInt(request.getParameter("user_id"));
		System.out.println("user_id"+user_id);
		Integer card_id = Integer.parseInt(request.getParameter("card_id"));
		System.out.println("card_id"+card_id);
		try {
			String baseDir = "C:/Users/Gressia/Downloads/Prueba";
			for (Part file : files) {
				System.out.println("Dentro del for");
				String file_name = getFileName(file);
				String file_url = file.getName();
				System.out.println("file_name: "+file_name+" file_url: "+file_url+" status: ");
				boolean status = db.VerificarArchivo(card_id, file_name);
				System.out.println("file_name: "+file_name+" file_url: "+file_url+" status: ");
				if(!status) {
					filecontent = file.getInputStream();
					status = db.AgregarArchivo(card_id, user_id, file_url, file_name);
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
					if (filecontent != null) {
						filecontent.close();
					}
					if (os != null) {
						os.close();
					}
				
				}else {
					mensaje.put("status", 409).put("response","Ya existe ese archivo en la tarjeta");
					System.out.println("Ya existe ese archivo en la tarjeta");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
