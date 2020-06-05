package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import dataBase.DBStatic;
import tools.ErrorJSON;
import tools.SessionTools;
import tools.UserTools;

public class Session {
	
	public static JSONObject login(String login, String pwd) throws SQLException, JSONException {
		if (!SessionTools.verifySession(login)) {
			return ErrorJSON.serviceRefused("Vous etes déja connecté!", DBStatic.alreadyInDbError);
		}
		if (login==null || pwd==null) {
			return ErrorJSON.serviceRefused("Champs manquant!", DBStatic.emptyCaseError);
		}
		if (!UserTools.existUser(login)) {
			System.out.println("fast l exxisr user ");
			return ErrorJSON.serviceRefused("Utilisateur inéxistant!", DBStatic.notInDbError);
		}

		if (UserTools.checkPassword(login, pwd)) {
			System.out.println("mlkklmkmlkkmk ********");
			return ErrorJSON.serviceRefused("Mot de passe Erroné!", DBStatic.checkError);
		} 
		String key =SessionTools.insertSession(login);
		System.out.println("Session "+ key+ " insérée! ");
		return ErrorJSON.serviceAccepted(key);
	}
	
	
	public static JSONObject logout(String login, String pwd, String key) throws SQLException, JSONException {
		if (! SessionTools.verifySession(login, key)) {
			return ErrorJSON.serviceRefused("Vous etes déja déconnecté!", DBStatic.checkError);
		} 
		if (login==null || pwd==null || key==null) {
			return ErrorJSON.serviceRefused("Champs manquant!", DBStatic.emptyCaseError);
		}
		if (! UserTools.existUser(login)) {
			return ErrorJSON.serviceRefused("Utilisateur inexistant!", DBStatic.notInDbError);
		} 
 		if (!UserTools.checkPassword(login, pwd)) {
			System.out.println("f check pswd! ");
			return ErrorJSON.serviceRefused("Mot de passe Erroné!", DBStatic.checkError);
		}
		SessionTools.deleteSession(login, key);
		System.out.println("Session "+ key+ " supprimée! ");
		return ErrorJSON.serviceAccepted();
	}
	
	public static String getKey(String login) throws SQLException {
		return SessionTools.getKey(login);
	}
	
	
	
}
