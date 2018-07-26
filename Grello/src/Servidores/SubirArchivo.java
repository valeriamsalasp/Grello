package Servidores;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONObject;

import Grello.Queries;


/**
 * Servlet implementation class SubirArchivo
 */


@WebServlet("/SubirArchivo")
@MultipartConfig
public class SubirArchivo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubirArchivo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		Queries db = new Queries();
		ArrayList <JSONObject> arrayFile = new ArrayList<JSONObject>();
		
		System.out.println("Estoy en el metodo get de Subir archivp");
		Integer card_id = Integer.parseInt(request.getParameter("card_id"));
		System.out.println("El card_id es "+ card_id);
		
		try {
			System.out.println("Comenzamos con la lectura de los archivos");
			arrayFile = db.LeerArchivo(card_id);
			if(!arrayFile.isEmpty()) {
				mensaje.put("status", 200).put("response",arrayFile);
				System.out.println("Se ha realizado la lectura de los archivos");
				System.out.println(arrayFile);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.closeResources();
		}
		out.println(mensaje.toString());
	}

	/**
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject data = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		Queries db = new Queries();
		
		HttpSession sesion = request.getSession();
		Collection<Part> files = request.getParts();
		InputStream filecontent = null; 
		OutputStream os = null;
		
		System.out.println("Estamos en el metodo post de Subir Archivo");
		try {
			//int card_id = data.getInt("card_id");
			//int user_id = data.getInt("user_id");
			Integer card_id = Integer.parseInt(request.getParameter("card_id"));
			Integer user_id = Integer.parseInt(request.getParameter("user_id"));
			if(!sesion.isNew()) {
				String baseDir = "C:/Users/Gressia/Downloads/Prueba";
				for(Part file : files) {
					String file_name = getFileName(file);
					boolean status = db.VerificarArchivo(card_id, file_name);
					String file_url = file.getName();
					System.out.println("file_name: "+file_name+" file_url: "+file_url+" status: "+status);
					if(!status) {
						filecontent = file.getInputStream();
						status = db.AgregarArchivo(card_id, user_id, file_url, file_name);
						os = new FileOutputStream( baseDir + "/" + this.getFileName(file));
						int read = 0;
						byte[] bytes = new byte[1024];
						while ((read = filecontent.read(bytes)) != -1) {
							os.write(bytes, 0, read);
						}
						mensaje.put("status", 200).put("response","La subida de archivo se realizo con exito");
						System.out.println("La subida de archivo se realizo con exito");
					}else {
						mensaje.put("status", 409).put("response","Ya existe ese archivo en la tarjeta");
						System.out.println("Ya existe ese archivo en la tarjeta");
					}
					
				}
			}else {
				mensaje.put("status", 403).put("description", "Acceso denegado");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.closeResources();
		}
		out.println(mensaje.toString());
		
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject mensaje = new JSONObject();
		JSONObject data = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		Queries db = new Queries();
		ArrayList <JSONObject> arrayFile = new ArrayList<JSONObject>();
		
		JSONObject FileData = new JSONObject();
		
		System.out.println("la data es: "+data);
		
		try {
			arrayFile = db.LeerPersonaAdmin(data);
			if(arrayFile.size() == 1) {
				System.out.println("Admin");
				boolean status = db.BorrarArchivo(data);
				if (status) {
					mensaje.put("status", 200).put("response", "El archivo fue borrado");
				}else {
					mensaje.put("status", 500).put("response","No se puedo borrar");
				}
			}else {
				System.out.println("Invitado");
				FileData = db.LeerArchivoEspecifico(data);
				System.out.println("El id del creador es: " +FileData.getInt("user_id"));
				System.out.println("El id del usuario actual es: "+ data.getInt("id"));
				if((data.getInt("id")) == (FileData.getInt("user_id"))) {
					boolean status = db.BorrarArchivo(data);
					if (status) {
						mensaje.put("status", 200).put("response", "El archivo fue borrado");
					}else {
						mensaje.put("status", 500).put("response","No se puedo borrar");
					}
				}else {
					mensaje.put("status", 409).put("response","No es el que subio el archivo a borrar");
					System.out.println("No es el creador del archivo a borrar");
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeResources();
		}
		out.println(mensaje.toString());
	}
	
	private String getFileName(Part part) {
		for(String content : part.getHeader("content-disposition").split(";")) {
			if(content.trim().startsWith("filename")) {
				return content.substring(content.indexOf("=") + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

}
