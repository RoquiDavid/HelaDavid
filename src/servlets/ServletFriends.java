
package servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServletFriends") 

public class ServletFriends extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	public ServletFriends() {
		super();
	}
	
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) { // throws ServletException, IOException {
		try {
			reponse.getWriter().append("Served at: ").append(requete.getContextPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest requete, HttpServletResponse reponse) { // throws ServletException, IOException {
		doGet(requete, reponse);
	}
}




