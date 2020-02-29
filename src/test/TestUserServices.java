package test;

import org.json.JSONException;
import org.json.JSONObject;

import services.AuthentificationServices;
import services.UserServices;

public class TestUserServices {
	public static void main(String[] args) {
		try {
			
			String login = "Gabriel1254";
			String password = "123456";
			String nom = "CAO";
			String prenom = "Gabriel";
			
			JSONObject jsonPost = UserServices.createUser(login, password, nom, prenom);
			UserServices.createUser(login + "ezfizb", password, nom, prenom);
			
			JSONObject jsonGet = UserServices.getUser(login);
			JSONObject jsonGetAll = UserServices.getUserList();
			JSONObject o = AuthentificationServices.login(login, password);
			System.out.println(o.get("key"));
			JSONObject jsonDelete = UserServices.deleteUser(o.getString("key")+"srfez");
			
			System.out.println("Results for jsonPost :" + jsonPost);
			System.out.println("Results for jsonGet :" + jsonGet);
			System.out.println("Results for jsonGetAll :" + jsonGetAll);
			System.out.println("Results for jsonDelete :" + jsonDelete);
			
			
			

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}
}

