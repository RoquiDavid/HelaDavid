package tools;

import java.sql.Connection;
import java.sql.*;
import dataBase.Database;

public class AuthentificationTools {
	
	
	public static boolean checkPassword(String login , String password)throws SQLException , Exception 
	{
		Connection connection = Database.getMySQLConnection() ;
		
		Statement statement = connection.createStatement() ;
		
		String query = "Select password from users where login ='"+login +"'" ;
		ResultSet resultat =statement.executeQuery(query) ;
		resultat.next() ;
		String passwordresult = resultat.getString("password");
		
		resultat.close();
		statement.close() ;
		connection.close(); 
		return password==passwordresult ;
		
	}
	
	
	
	public static boolean sessionExist(String key) throws  SQLException ,Exception {
		Connection connection = Database.getMySQLConnection();
		Statement statement = connection.createStatement() ;
		
		String query="SELECT COUNT(*) FROM sessions WHERE key_='"+key+"'";
		
		ResultSet resultat = statement.executeQuery(query);
		resultat.next();
		int resultat2=resultat.getInt(1);
		
		resultat.close();
		statement.close() ;
		connection.close(); 
		
		return resultat2>=1;
	}
	
	
	
	
	public static void deleteSession(String key) throws SQLException ,Exception {
		Connection connection = Database.getMySQLConnection();
		Statement statement = connection.createStatement() ;
		
		String query="DELETE FROM sessions WHERE key_='"+key+"'";
		
		statement.executeUpdate(query);
		statement.close();
		connection.close();
		
	}
	
}
