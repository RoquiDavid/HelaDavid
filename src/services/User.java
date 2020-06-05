package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import dataBase.DBStatic;
import tools.ErrorJSON;
import tools.UserTools;

public class User {
	public static JSONObject createUser(String login, String nom, String prenom, String pwd, String mail) throws SQLException {

		if (login==null || nom==null || prenom==null || pwd==null || mail==null) {
			return ErrorJSON.serviceRefused("Champs manquant!", DBStatic.emptyCaseError);
		}
		if (UserTools.existUser(login)) {
			System.out.println("User already existing!");
			return ErrorJSON.serviceRefused("Utilisateur déja existant!", DBStatic.alreadyInDbError);
		}
		UserTools.insertUser(login, nom, prenom, pwd, mail);
		System.out.println("User " + login  +" inserted succesufully.");
		return ErrorJSON.serviceAccepted();
	}

	public static JSONObject deleteUser(String login) throws SQLException {

		if (login==null ) {
			return ErrorJSON.serviceRefused("Champs manquant!", DBStatic.emptyCaseError);
		}
		if (!UserTools.existUser(login)) {
			System.out.println("User already unexistant!");
			return ErrorJSON.serviceRefused("Utilisateur n'existe pas par défaut!", DBStatic.notInDbError);
		}
		UserTools.deleteUser(login);
		System.out.println("User " + login  +" deleted succesufully.");
		return ErrorJSON.serviceAccepted();
		
	}
	
	public static JSONObject getUsersList() {
		try {
			UserTools.getAllUsers();
			System.out.println("Users' list: " + UserTools.getAllUsers());
			return ErrorJSON.serviceAccepted();
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("Database Error", -1);
		} catch (JSONException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("JSON Error", -1);
		}
	}

	public static JSONObject getUser(String login) throws JSONException {
		try {
			if (!(UserTools.existUser(login)))
				return ErrorJSON.serviceRefused("the user doesn't exist", DBStatic.notInDbError);
			int id = UserTools.getIdFromLogin(login);
			UserTools.getUser(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("DataBase error", DBStatic.sqlError);
		} 

		return ErrorJSON.serviceAccepted();
	}
	
	
}