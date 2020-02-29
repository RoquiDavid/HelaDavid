
package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.MessageServices;

/**
 * Servlet implementation class Message
 */
@WebServlet("/Message")
public class Message extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * getMessage
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			String key = request.getParameter("key");
			String mid = request.getParameter("mid");
			
			/* Code to change : access to service */
			JSONObject json = MessageServices.getMessage(key, mid);

			/* print the output in the response */
			print(json, response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * createMessage
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* Access to parameter */
		String key = request.getParameter("key");
		String mid = request.getParameter("mid");
		String content = request.getParameter("content");

		try {

			/* Code to change : access to service */
			JSONObject json = MessageServices.createMessage(key, content);

			/* print the output in the response */
			print(json, response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * deleteMessage
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			//Need the key session of the user and the id of the message to delete the message
			String key = request.getParameter("key");
			String mid = request.getParameter("mid");
			
			/* Code to change : access to service */
			JSONObject json = MessageServices.deleteMessage(key, mid);

			/* print the output in the response */
			print(json, response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void print(JSONObject json, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(json.toString());

	}

}
