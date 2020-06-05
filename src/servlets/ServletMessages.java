package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tools.ErrorJSON;

@WebServlet("/ServletMessages") 

public class ServletMessages extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		String pathInfo = request.getPathInfo();
		if (pathInfo == null) {
			writer.println(ErrorJSON.serviceRefused("argument manquant", -1));
		}
		String param = pathInfo.toString();
		writer.println(services.Messages.listing(param));
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter writer = response.getWriter();
		try {
			writer.println(services.Messages.createMessage( request.getParameter("login").toString(), request.getParameter("message").toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
