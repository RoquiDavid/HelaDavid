package test;

import org.json.JSONException;
import org.json.JSONObject;

import bd.LoadDataBase;
import services.AuthentificationServices;
import services.MessageServices;
import services.UserServices;

public class TestMessageServices {
	public static void main(String[] args) {

		try {

			LoadDataBase.loadSQLDataBase();
			//return the id of the message created
			String mid = LoadDataBase.loadMongoDataBase();
			
			UserServices.createUser("Gabriel", "1234", "CAO", "Gabriel");
			String key = AuthentificationServices.login("Gabriel", "1234").getString("key");

			JSONObject jsonPost = MessageServices.createMessage(key, "content");
			JSONObject jsonGet = MessageServices.getMessage(key, jsonPost.getString("mid"));
			JSONObject jsonPost2 = MessageServices.update(key,  jsonPost.getString("mid"), "null", "modif");
			
			JSONObject jsonDelete = MessageServices.deleteMessage(key, mid);

			System.out.println("Results for create :" + jsonPost);
			System.out.println("Results for get :" + jsonGet);
			System.out.println("Results for update :" + jsonPost2);
			jsonGet = MessageServices.getMessage(key, mid);
			System.out.println("Results for get :" + jsonGet);
			//System.out.println("Results for delete :" + jsonDelete);
			//JSONObject jsonGet3 = MessageServices.getMessage(key, "5");
			//System.out.println("Results for get :" + jsonGet3);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	
	}
}
