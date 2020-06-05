package tools.copy;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dataBase.Database;

public class UserTools {
	public static ArrayList<String> amis = new ArrayList<String>(Arrays.asList("Maima" , "Chagra" , "Nessrine" , "Maryata" , "Roukia"));
	
	public static boolean existUser(String login) throws SQLException {
		Connection co= Database.getMySQLConnection();

		String query= "SELECT * FROM `user` WHERE login= '"+ login +"';";
		java.sql.Statement pst= co.createStatement();
		//pst.setString(1, login);
		ResultSet res= pst.executeQuery(query);
		
		int id = -1 ;
		while(res.next()) {
			id = res.getInt("id");
		}
		
		res.close();
		co.close();
		return id!=-1;	
	}
	
	public static boolean checkPassword(String login, String pwd) {
		try {
			Connection co= Database.getMySQLConnection();
			String query= "select * from user where `login`="+ login +"  and password =" + pwd ;
			/*PreparedStatement pst= co.prepareStatement(query);
			pst.setString(1, login);
			pst.setString(2, pwd);
			ResultSet res= pst.executeQuery(query);
			pst.close();
			res.close();
			co.close();
			return true; */
			Statement st = co.createStatement();
			st.executeQuery(query);
			st.close();
			co.close();
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean insertUser(String login, String nom, String prenom, String pwd, String mail) {
		try {
			Connection co= Database.getMySQLConnection();
			String rq = "INSERT INTO `user` (`login`, `nom`, `prenom`, `pwd`, `mail`, `id`) VALUES"
					+ "('"+login+"',"
							+ " '"+nom+"',"
							+ " '"+prenom+"', '"
							+ pwd+"', '"
							+ mail+"',"					
							+ "NULL);";
			java.sql.Statement pst = co.createStatement();
			pst.executeUpdate(rq);
			pst.close();
			co.close();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static void deleteUser(String login) {
		try {
			Connection co= Database.getMySQLConnection();
			String query= "DELETE FROM `user` WHERE `id`=" + getIdFromLogin(login) +";";
			Statement st= co.createStatement();
			st.executeUpdate(query);
			st.close();
			co.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	public static int getIdFromLogin(String login) throws SQLException {
		Connection co= Database.getMySQLConnection();
		String query= "SELECT `id` FROM `user` WHERE `login`= '" + login +"';";
		Statement st= co.createStatement();
		ResultSet res= st.executeQuery(query);
		res.next();
		int id= res.getInt("id");
		st.close();
		res.close();
		co.close();
		return id;
	}
	
	public static int getIdFromKey(String key) throws SQLException {
		Connection co= Database.getMySQLConnection();
		String query="SELECT `id` FROM `session` WHERE `key`='" + key +"';" ;
		Statement st= co.createStatement();
		ResultSet res= st.executeQuery(query);
		res.next();
		int id= res.getInt("id");
		st.close();
		res.close();
		co.close();
		return id;
	}
	
	public static JSONArray getAllUsers() throws SQLException, JSONException{
		Connection co= Database.getMySQLConnection();
		String query= "SELECT `nom`, `prenom` FROM `user` ";
		Statement st= co.createStatement();
		ResultSet res= st.executeQuery(query);
		JSONArray usersList=new JSONArray();
		String nom, prenom;
		while( res.next()) {
			nom= res.getString("nom");
			prenom=res.getString("prenom");
			usersList.put(new JSONObject().put("nom", nom).put("prenom", prenom));
		}
		st.close();
		res.close();
		co.close();
		System.out.println("liiiiiiiiiiist "+usersList);
		return usersList;
	}
	
	public static ArrayList<String> getFriendsList(){
		return amis;
	}
	
	public static int getFriendsNbr(ArrayList<String> amis) {
		return amis.size();
	}
	
	
	
}