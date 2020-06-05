package tools.copy;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.mysql.cj.xdevapi.Statement;

import dataBase.Database;
import tools.UserTools;

public class SessionTools {
	private static final String KEY_CARACTERS= "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvxyz" + "0123456789";
	private static final int KEY_LENGTH= 12; 

	public static String generateKey(int taille) {
		StringBuilder key= new StringBuilder(taille);
		for (int i=0; i<taille; i++) {
			key.append(KEY_CARACTERS.charAt((int) (Math.random() * ( KEY_CARACTERS.length()))));	
		}
		return key.toString();
	}
	
	public static String insertSession(String login) throws SQLException {
		
		Connection co= Database.getMySQLConnection();
		
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String time= formatter.format(date);
		String key= generateKey(10);
		int id = UserTools.getIdFromLogin(login);
		String query=  "INSERT INTO `session`(`id`, `key`, `time`) VALUES ("+ id+" , '"+  key+ "','"+ time+"')"; 
		java.sql.Statement st= co.createStatement();
		st.executeUpdate(query);
		st.close();
		co.close();
		return key;
	}
	
	public static void deleteSession(String login, String key) throws SQLException {
		Connection co=Database.getMySQLConnection();
		System.out.println("iddddddddddddd vide");
		int id = UserTools.getIdFromKey(key);
		System.out.println("iddddddddddddd " + id);
		String query=  "DELETE FROM `session` WHERE `id`="+ id; 
		java.sql.Statement st= co.createStatement();
		st.executeUpdate(query);
		st.close();
		co.close();
	}
	
	public static boolean verifySession(String login, String key) {
		return true;
	}
	
	public static boolean verifySession(String login) {
		return true;
	}


	public static String getKey(String login) throws SQLException {
		Connection co=Database.getMySQLConnection();
		int id = UserTools.getIdFromLogin(login);
		
		String query=  "SELECT `key` FROM `session` WHERE `id`="+ id; 
		java.sql.Statement st= co.createStatement();
		st.executeQuery(query);
		ResultSet res= st.executeQuery(query);
		res.next();
		String key= res.getString("key");
		st.close();
		co.close();
		return key;
	}
	
}


