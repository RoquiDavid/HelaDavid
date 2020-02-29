package services;

import java.sql.Connection;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bd.DBStatic;
import bd.Database;
import tools.AuthentificationTools;
import tools.ErrorJSON;
import tools.UserTools;

public class UserServices {

	/**
	 * Returns a JSONObject that contains a message
	 * 
	 * @param login
	 *            the login of the user
	 * @param password
	 *            the password of the user
	 * @param prenom
	 * @param nom
	 * @return a JSONObject that contains a message
	 * @throws JSONException
	 *             that shouldn't happen
	 */
	public static JSONObject createUser(String login, String password, String nom, String prenom) throws JSONException {
		Connection c = null;
		try {
			c = Database.getMySQLConnection();
			if (login == null || password == null || nom == null || prenom == null)
				return ErrorJSON.serviceRefused("field(s) empty", DBStatic.empty_field_error);

			if (UserTools.existUser(login, c))
				return ErrorJSON.serviceRefused("login already used by another user", DBStatic.already_in_db_error);

			UserTools.insertUser(login, password, nom, prenom, c);

			return ErrorJSON.serviceAccepted("message", "the user has been successfully created");
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("database exception", DBStatic.sql_error);
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return ErrorJSON.serviceRefused("database exception", DBStatic.sql_error);

			}
		}

	}

	/**
	 * Returns a JSONObject signaling that the user with the specified login has
	 * successfully deleted its account
	 * 
	 * @param login
	 *            the login of the user
	 * @return a JSONObject signaling that the user with the specified login has
	 *         successfully deleted its account
	 * @throws JSONException
	 *             that should happen
	 */
	public static JSONObject deleteUser(String key) throws JSONException {
		Connection c = null;
		try {
			c = Database.getMySQLConnection();
			if (key == null) {
				return ErrorJSON.serviceRefused("Null key", DBStatic.empty_field_error);
			}

			if(!AuthentificationTools.existKey(key, c))
				return ErrorJSON.serviceRefused("the key doesn't exist", DBStatic.not_in_db_error);
			int id = UserTools.getUserIdFromKey(key, c);
			UserTools.deleteUser(id, c);

			String message = "the account of the user " + key + " has been successfully deleted";
			return ErrorJSON.serviceAccepted("message", message);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return ErrorJSON.serviceRefused("DataBase error", DBStatic.sql_error);
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return ErrorJSON.serviceRefused("DataBase error", DBStatic.sql_error);

			}
		}
	}

	/**
	 * Returns a JSONObject that contains the data of the user of the specified
	 * login
	 * 
	 * @param login
	 *            the login of the user
	 * @return a JSONObject that get the data of the user of the specified login
	 * @throws JSONException
	 *             that shouldn't happen
	 */
	public static JSONObject getUser(String login) throws JSONException {
		JSONArray userInfo;
		Connection c = null;
		try {
			c = Database.getMySQLConnection();
			if (!(UserTools.existUser(login, c)))
				return ErrorJSON.serviceRefused("the user doesn't exist", DBStatic.not_in_db_error);
			int id = UserTools.getUserId(login, c);
			userInfo = UserTools.getUser(id, c);
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("DataBase error", DBStatic.sql_error);
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return ErrorJSON.serviceRefused("DataBase error", DBStatic.sql_error);
			}
		}

		return ErrorJSON.serviceAccepted("user", userInfo);
	}
	
	
	/**
	 * Returns a JSONObject that contains the data of the user of the specified
	 * login
	 * 
	 * @param login
	 *            the login of the user
	 * @return a JSONObject that get the data of the user of the specified login
	 * @throws JSONException
	 *             that shouldn't happen
	 */
	public static JSONObject blockUser(String loginCurrentUser, String loginBlockUser) throws JSONException {
		JSONArray userInfo;
		Connection c = null;
		try {
			c = Database.getMySQLConnection();
			if (!(UserTools.existUser(login, c)))
				return ErrorJSON.serviceRefused("the user doesn't exist", DBStatic.not_in_db_error);
			
			//Récupère le id des utilisateurs concernés
			int idCurrentUser = UserTools.getUserId(loginCurrentUser, c);
			int loginBlockUser = UserTools.getUserId(loginCurrentUser, c);
			
			userInfo = UserTools.block(idCurrentUser, idBlockUser);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("DataBase error", DBStatic.sql_error);
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return ErrorJSON.serviceRefused("DataBase error", DBStatic.sql_error);
			}
		}

		return ErrorJSON.serviceAccepted("user", userInfo);
	}
	
	

	/**
	 * return a JSONObject that contains the login of all user of the website
	 * 
	 * @return a JSONObject that contains the login of all user of the website
	 * @throws JSONException
	 *             that shouldn't happen
	 */
	public static JSONObject getUserList() throws JSONException {
		JSONArray users;
		Connection c = null;
		try {
			c = Database.getMySQLConnection();
			users = UserTools.getUserList(c);
			return ErrorJSON.serviceAccepted("user list", users);
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("DataBase error", DBStatic.sql_error);
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return ErrorJSON.serviceRefused("DataBase error", DBStatic.sql_error);

			}
		}

	}
}
