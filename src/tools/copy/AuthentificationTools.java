package tools.copy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataBase.Database;

public class AuthentificationTools {
	
	
	public static void insertSession(String key, String login) {
		
		String query = "insert into session(id_user, key_session, time) values (? , ?, now());";

	}
	

	
	
	
	
	
	
	
	
}