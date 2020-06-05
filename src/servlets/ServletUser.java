package servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.User;
@WebServlet("/ServletUser") 

public class ServletUser extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public ServletUser() {
		super();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException { // throws ServletException, IOException {
		String login= request.getParameter("login");
		String nom= request.getParameter("nom");
		String prenom= request.getParameter("prenom");
		String password= request.getParameter("password");
		String mail= request.getParameter("mail");
		try {
			JSONObject jsonResp= services.User.createUser(login, nom, prenom, password, mail);
			print(jsonResp, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			JSONObject json = User.getUsersList();
			String login = request.getPathInfo();
			json = User.getUsersList();
			json.put("url", login);
			print(json, response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException { // throws ServletException, IOException {
		String login= request.getParameter("login");
		JSONObject jsonResp;
		try {
			jsonResp = services.User.deleteUser(login);
			print(jsonResp, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void print(JSONObject json, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(json.toString());
	}
	
}
