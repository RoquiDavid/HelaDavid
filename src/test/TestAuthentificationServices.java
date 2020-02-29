package test;

import org.json.JSONException;
import org.json.JSONObject;

import services.AuthentificationServices;
import services.UserServices;

public class TestAuthentificationServices {
	public static void main(String[] args) {
		try {
			String login = "Gabriel";
			String password = "password";
			String nom = "CAO";
			String prenom = login;
			UserServices.createUser(login, password, nom, prenom);
			JSONObject jsonLogin = AuthentificationServices.login(login, password);
			//JSONObject jsonLogout = AuthentificationServices.logout(jsonLogin.getString("key"));
			System.out.println(jsonLogin);
			//System.out.println(jsonLogout);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
