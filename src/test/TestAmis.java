package test;

import java.sql.SQLException;

import org.json.JSONException;

import services.Friend;
import services.User;

public class TestAmis {
	public static void main(String[] args) throws SQLException, JSONException {
		String login = "heyyouu ";
		String password = "1234osfh";
		String nom = "ane";
		String prenom = "anezeda";
		String mail="mail@com";
		
		//User.createUser(login, nom, prenom, password, mail);
		//User.deleteUser(login);
		//User.createUser(login, nom, prenom, password, mail); 
		Friend.getFriendsList(login);
		/*String key=SessionTools.insertSession(login);
		/*SessionTools.deleteSession(login, key);
		/*System.out.println(UserTools.getIdFromKey(key));
		ArrayList<String> friendsList=UserTools.getFriendsList();
		System.out.println(friendsList);
		System.out.println(UserTools.getFriendsNbr(friendsList));
		System.out.println(User.getUsersList()+"");
		*/

	}
}
	

