package services;

import org.json.JSONObject;

import dataBase.DBStatic;
import tools.ErrorJSON;
import tools.MessagesTools;
import tools.UserTools;

public class Messages {
	
	 public static JSONObject createMessage(String login, String texte) throws Exception {
		 if (login==null || texte==null) {
			 return ErrorJSON.serviceRefused("Champs manquant!", DBStatic.emptyCaseError);
		 }
		 if (UserTools.existUser(login)) {
			System.out.println("User already existing!");
			return ErrorJSON.serviceRefused("Utilisateur d�ja existant!", DBStatic.alreadyInDbError);
		 }
		 int id_user = 0;
		 try {
			id_user = UserTools.getIdFromLogin(login);
		 } catch (Exception e) {	
			e.printStackTrace();
		 }
		 if (id_user<0) {
			 return ErrorJSON.serviceRefused("Account does not exist" , DBStatic.notInDbError);
		 }
		 MessagesTools.messagePost(id_user,texte);
		 System.out.println("Le message a �t� cre� avec succes");
		 
		 return ErrorJSON.serviceAccepted();	 
	  }
	  
	 
	  public static JSONObject listing(String login) {
		  if (login==null) {
				 return ErrorJSON.serviceRefused("Champs manquant!", DBStatic.emptyCaseError);
		  }
		  int id = 0;
		  try {
			  id = UserTools.getIdFromLogin(login);
		  } catch (Exception e) {	
			  e.printStackTrace();
		  }
		  MessagesTools.listing(id);			
		  return ErrorJSON.serviceAccepted();
	  }
	  
	  
	  public static JSONObject deleteMessages(String login) {
		  if (login==null) {
				 return ErrorJSON.serviceRefused("Champs manquant!", DBStatic.emptyCaseError);
		  }
		  int id = 0;
		  try {
			  id = UserTools.getIdFromLogin(login);
		  } catch (Exception e) {	
			  e.printStackTrace();
		  }
		  MessagesTools.remove(id);
		  System.out.println("Tous les messages de l utilisateur"+id+"ont �t� supprim�");
		  return ErrorJSON.serviceAccepted();
	  }
  
	  
	  public static JSONObject ListingLastHour(String login) {
		  if (login==null) {
				 return ErrorJSON.serviceRefused("Champs manquant!", DBStatic.emptyCaseError);
		  }
		  int id = 0;
		  try {
			  id = UserTools.getIdFromLogin(login);
		  } catch (Exception e) {	
			  e.printStackTrace();
		  }	
		  MessagesTools.listingLastHour(id);
		  return ErrorJSON.serviceAccepted();
	  }

}






	
