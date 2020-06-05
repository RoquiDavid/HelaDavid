package test;

import services.Messages;
import tools.MessagesTools;




public class TestMessages {

	public static void main(String[] args) throws Exception {
		
		// les données de test
			String login="mylogin";  
			String nom="sofien";
			String message="Restez en bonne santé ";
			
		// Testing Creation des messages
			Messages.createMessage(login, message);
		
		// Testing Listing of Messages by ID ( user's Specific messages ) 
			Messages.listing(login);  
		
		// Testing Deleting Message By Id ( user's Specific messages ) 
 			Messages.deleteMessages(login);
	}

}
