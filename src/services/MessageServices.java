package services;

import java.sql.Connection;
import java.sql.SQLException;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import bd.DBStatic;
import bd.Database;
import tools.AuthentificationTools;
import tools.ErrorJSON;
import tools.FriendTools;
import tools.MessageTools;
import tools.UserTools;

public class MessageServices {
	
	public static JSONObject getMessage(String key, String mid) throws JSONException {
		Connection c = null;
		try {
			c = Database.getMySQLConnection();
			MongoDatabase db = Database.getMongoDBConnection();
			MongoCollection<Document> coll = db.getCollection("message");
			if (key == null || mid == null)
				return ErrorJSON.serviceRefused("key or mid field empty", -1);

			if(!AuthentificationTools.existKey(key,c))
				return ErrorJSON.serviceRefused("the key doesn't exist", 12);
			
			if(!UserTools.isValid(key, c)) {
				AuthentificationTools.removeSession(key, c);
				return ErrorJSON.serviceRefused("you have been disconnected, key too old", 6);			
			}

			ObjectId message_id = new ObjectId(mid);
			try {
				if(!MessageTools.existMessage(message_id, coll)){
					return ErrorJSON.serviceRefused("not found", 25);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			AuthentificationTools.updateSession(key);
			
			String message = MessageTools.getMessage(message_id, coll);

			return ErrorJSON.serviceAccepted("message", "message " + mid + " : " + message);
		} catch (JSONException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("json error : \n" + e.getMessage(), 100);
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("sql error : \n" + e.getMessage(), 1000);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("not found \n" + e.getMessage(), 24);
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return ErrorJSON.serviceRefused("sql error : \n" + e.getMessage(), 1000);
			}
		}
	}
	
	public static JSONObject existMessage(String key, String mid) throws JSONException {
		Connection c = null;
		try {
			c = Database.getMySQLConnection();
			MongoDatabase db = Database.getMongoDBConnection();
			MongoCollection<Document> coll = db.getCollection("message");
			if (key == null || mid == null)
				return ErrorJSON.serviceRefused("key or mid field empty", -1);

			if(!AuthentificationTools.existKey(key,c))
				return ErrorJSON.serviceRefused("the key doesn't exist", 12);
			
			if(!UserTools.isValid(key, c)) {
				AuthentificationTools.removeSession(key, c);
				return ErrorJSON.serviceRefused("you have been disconnected, key too old", 6);			
			}
			
			ObjectId message_id = new ObjectId(mid);
			if(!MessageTools.existMessage(message_id, coll))
				return ErrorJSON.serviceRefused("not found", 25);
			
			AuthentificationTools.updateSession(key);
			
			boolean message = MessageTools.existMessage(message_id, coll);

			return ErrorJSON.serviceAccepted("message", "message " + mid + " : " + message);
		} catch (JSONException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("json error : \n" + e.getMessage(), 100);
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("sql error : \n" + e.getMessage(), 1000);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("not found \n" + e.getMessage(), 24);
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return ErrorJSON.serviceRefused("sql error : \n" + e.getMessage(), 1000);
			}
		}
	}
	
	public static JSONObject createMessage(String key, String content) throws JSONException {
		Connection c = null;
		String mid;
		try {
			c = Database.getMySQLConnection();
			MongoDatabase db = Database.getMongoDBConnection();
			MongoCollection<Document> coll = db.getCollection("message");
			if (key == null )
				return ErrorJSON.serviceRefused("key or mid  field empty", -1);

			if(!AuthentificationTools.existKey(key,c))
				return ErrorJSON.serviceRefused("the key doesn't exist", 12);
			
			if(!UserTools.isValid(key, c)) {
				AuthentificationTools.removeSession(key, c);
				return ErrorJSON.serviceRefused("you have been disconnected, key too old", 6);			
			}
			
			AuthentificationTools.updateSession(key);
			
			mid = MessageTools.createMessage( content, coll);

		
		} catch (JSONException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("json error : \n" + e.getMessage(), 100);
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("sql error : \n" + e.getMessage(), 1000);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("not found \n" + e.getMessage(), 24);
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return ErrorJSON.serviceRefused("sql error : \n" + e.getMessage(), 1000);
			}
		}
	
		return ErrorJSON.serviceAccepted("mid", mid);
	}
	
	public static JSONObject update(String key, String mid, String idUser , String content) throws JSONException {
		Connection c = null;
		try {
			c = Database.getMySQLConnection();
			MongoDatabase db = Database.getMongoDBConnection();
			MongoCollection<Document> coll = db.getCollection("message");
			if (key == null || mid == null || idUser == null)
				return ErrorJSON.serviceRefused("key or mid or login field empty", -1);

			if(!AuthentificationTools.existKey(key,c))
				return ErrorJSON.serviceRefused("the key doesn't exist", 12);
			
			if(!UserTools.isValid(key, c)) {
				AuthentificationTools.removeSession(key, c);
				return ErrorJSON.serviceRefused("you have been disconnected, key too old", 6);			
			}
			
			ObjectId message_id = new ObjectId(mid);
			
			//Si le contenu est vide on suppose que le message doit être supprimé
			if(content == null) {
				MessageTools.deleteMessage(message_id, coll);
			}
			AuthentificationTools.updateSession(key);
			MessageTools.updateMessage(message_id,idUser, content, coll);

		
		} catch (JSONException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("json error : \n" + e.getMessage(), 100);
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("sql error : \n" + e.getMessage(), 1000);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("not found \n" + e.getMessage(), 24);
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return ErrorJSON.serviceRefused("sql error : \n" + e.getMessage(), 1000);
			}
		}
	
		return ErrorJSON.serviceAccepted("message", "message " + mid + " : " + content);
	}

	public static JSONObject deleteMessage(String key, String mid) throws JSONException {
		Connection c = null;
		try {
			c = Database.getMySQLConnection();
			MongoDatabase db = Database.getMongoDBConnection();
			MongoCollection<Document> coll = db.getCollection("message");
			if (key == null || mid == null)
				return ErrorJSON.serviceRefused("key or mid or login field empty", -1);

			if(!AuthentificationTools.existKey(key,c))
				return ErrorJSON.serviceRefused("the key doesn't exist", 12);
			
			if(!UserTools.isValid(key, c)) {
				AuthentificationTools.removeSession(key, c);
				return ErrorJSON.serviceRefused("you have been disconnected, key too old", 6);			
			}
			
			
			ObjectId message_id = new ObjectId(mid);
			
			AuthentificationTools.updateSession(key);
			
			MessageTools.deleteMessage(message_id, coll);

		
		} catch (JSONException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("json error : \n" + e.getMessage(), 100);
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("sql error : \n" + e.getMessage(), 1000);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("not found \n" + e.getMessage(), 24);
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return ErrorJSON.serviceRefused("sql error : \n" + e.getMessage(), 1000);
			}
		}
	
		return ErrorJSON.serviceAccepted("message", "message " + mid);
	}
	

}
