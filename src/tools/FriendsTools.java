package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dataBase.Database;

public class FriendsTools {
	public static void addFriend(int myId, int friendId) throws SQLException {
		Connection co = Database.getMySQLConnection();
		
		String query = "insert into friends values (" + myId + "," + friendId+ ")" ;
		java.sql.Statement st= co.createStatement();
		st.executeUpdate(query);
		st.close();
		
		co.close();
	}
	
	public static void deleteFriend(int myId, int friendId) throws SQLException {
		Connection co = Database.getMySQLConnection();

		String query = "delete from friend where id_f2 ="+ friendId+"  and id_f1 =" + myId;
		java.sql.Statement st= co.createStatement();
		st.executeUpdate(query);
		st.close();
		
		co.close();
	}
	
	public static JSONArray getFriendsList(String login) throws SQLException, JSONException{
		Connection co= Database.getMySQLConnection();
		
		int id= UserTools.getIdFromLogin(login);
		String query= "SELECT  `nom`, `prenom` FROM `user` u ,`friends` f WHERE u.id=id_f2 and id_f1=" +id;
		java.sql.Statement st= co.createStatement();
		ResultSet res= st.executeQuery(query);
		JSONArray firendsList=new JSONArray();
		String nom, prenom;
		while( res.next()) {
			nom= res.getString("nom");
			prenom=res.getString("prenom");
			firendsList.put(new JSONObject().put("nom", nom).put("prenom", prenom));
		}
		st.close();
		res.close();
		
		co.close();
		return firendsList;
		
	}
	
	// Fonction Bonus qui retourne la meilleure suggestion d'amis: l'ami avec qui on a le plus d'amis en commun 
	public static JSONArray getTheBestSuggestion(String login) throws SQLException, JSONException{
		Connection co= Database.getMySQLConnection();
		
		int id=UserTools.getIdFromLogin(login);
		String query=  "SELECT nom, prenom, COUNT(*) maximum"
				+ "FROM `friends`, user u2 "
				+ "WHERE id_f1=id AND NOT EXISTS ( SELECT  * "
											   + " FROM `user` u ,`friends` f "
											   + "WHERE u2.id=id_f2 OR id_f1="+ id+") AND EXISTS (SELECT *"
											   											  + " FROM friends"
											   											  + "WHERE id_f1= "+id+" and id_f2= u2.id)"
				+ "GROUP BY u2.id "
				+ "ORDER BY maximum DESC "
				+ "LIMIT 1;";
		
		java.sql.Statement st= co.createStatement();
		ResultSet res= st.executeQuery(query);
		JSONArray bestSuggest=new JSONArray();
		String nom, prenom;
		nom= res.getString("nom");
		prenom=res.getString("prenom");
		bestSuggest.put(new JSONObject().put("nom", nom).put("prenom", prenom));
		st.close();
		res.close();
		
		co.close();
		
		return bestSuggest;
	}
}
