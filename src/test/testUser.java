package test;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import services.User;
import tools.SessionTools;
import tools.UserTools;

public class testUser {
	public static void main(String[] args) throws SQLException, JSONException {
		String login = "helamn ";
		String password = "mdppostCorona";
		String nom = "Menzli";
		String prenom = "Hela";
		String mail="mn.hela14@mail@com";
		
		User.getUsersList();   
		User.createUser(login, nom, prenom, password, mail);
		User.getUsersList();   
		User.deleteUser(login);
		User.getUsersList(); 
		
		/*String key=SessionTools.insertSession(login);
		/*SessionTools.deleteSession(login, key);
		/*System.out.println(UserTools.getIdFromKey(key));
		ArrayList<String> friendsList=UserTools.getFriendsList();
		System.out.println(friendsList);
		System.out.println(UserTools.getFriendsNbr(friendsList));
		System.out.println(User.getUsersList()+"");
		*/
//System.out.println();
		

	}
} 

