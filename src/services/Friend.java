package services;

import java.sql.SQLException;


import org.json.JSONException;
import org.json.JSONObject;

import dataBase.DBStatic;
import tools.ErrorJSON;
import tools.UserTools;
import tools.FriendsTools ;

public class Friend {
	
	public static JSONObject addFriend(String myLogin, String friendLogin) throws SQLException {
		if (myLogin==null || friendLogin==null) {
			return ErrorJSON.serviceRefused("Champs manquant!", DBStatic.emptyCaseError);
		}
		if (!UserTools.existUser(myLogin) ||!UserTools.existUser(friendLogin) ) {
			return ErrorJSON.serviceRefused("User Inexistant!", DBStatic.notInDbError);
		}
		
		int myid= UserTools.getIdFromLogin(myLogin);
		int friendId= UserTools.getIdFromLogin(friendLogin);
		
		FriendsTools.addFriend(myid, friendId);
		
		return ErrorJSON.serviceAccepted();
	}
	
	public static JSONObject deleteFriend(String myLogin, String friendLogin) throws SQLException {
		if (myLogin==null || friendLogin==null) {
			return ErrorJSON.serviceRefused("Champs manquants!", DBStatic.emptyCaseError);
		}
		if (! UserTools.existUser(friendLogin) || ! UserTools.existUser(myLogin) ) {
			return ErrorJSON.serviceRefused("User Inexistant!", DBStatic.notInDbError);
		}
		
		int myId= UserTools.getIdFromLogin(myLogin);
		int friendId= UserTools.getIdFromLogin(friendLogin);		
		
		FriendsTools.deleteFriend(myId, friendId);
		
		return ErrorJSON.serviceAccepted();
	}
	
	
	public static JSONObject getFriendsList(String login) {
		if (login==null) {
			return ErrorJSON.serviceRefused("Champs manquants!", DBStatic.emptyCaseError);
		}
		try {
			FriendsTools.getFriendsList(login); // il en manque le test ou le messgae d affichage de liste vide si elle l'est
			System.out.println("Friends of " + login +": " + FriendsTools.getFriendsList(login));
			return ErrorJSON.serviceAccepted();
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("Database Error", DBStatic.sqlError);
		} catch (JSONException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("JSON Error", DBStatic.jsonError);
		}
	}
	
	
	public static JSONObject getTheBestSuguestion(String login) throws SQLException, JSONException {
		if (login==null) {
			return ErrorJSON.serviceRefused("Champs manquants!" , DBStatic.emptyCaseError);
		}
		if (! UserTools.existUser(login)) {
			return ErrorJSON.serviceRefused("User Inexistant!", DBStatic.notInDbError);
		}
		FriendsTools.getTheBestSuggestion(login);
		return ErrorJSON.serviceAccepted();
	}
}