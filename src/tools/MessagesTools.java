package tools;

import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.DB;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

import dataBase.Database;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MessagesTools {
	public static  void messagePost(int user_id, String texte) {	
		// Récuperation de l'attribut nom
		Connection co;
		String nom = null;
		try {
			co = Database.getMySQLConnection();
			String query= "SELECT `nom` FROM `user` WHERE `id`= '" + user_id +"';";
			Statement st= co.createStatement();
			ResultSet res= st.executeQuery(query);
			res.next();
			nom= res.getString("nom");
			st.close();
			res.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Connection with database
		MongoClient mongo = MongoClients.create( "mongodb://localhost:27017" );
		MongoDatabase db=mongo.getDatabase("Birdy");
		System.out.println("Connected To DataBase ");
		
		//Create Collection 
		MongoCollection collection= db.getCollection("Messages");
		System.out.println("Connected To Collection ");
		Document document = new Document ();
		document.append("User_id", user_id);
		document.append("Name ", nom);
		document.append("Message", texte);
		document.append("Date",new Date());
		
		collection.insertOne(document);
	
	}
		
	
	public static  void listing(int id) {
		
		//Connection with database
		MongoClient mongo = MongoClients.create( "mongodb://localhost:27017" );
		MongoDatabase db=mongo.getDatabase("Birdy");
		System.out.println("Connected To DataBase ");
		
		//Create Collection 
		MongoCollection collection= db.getCollection("Messages");
		System.out.println("Connected To Collection ");
		
		Document doc = new Document("User_id",id);
		MongoCursor cursor=  (MongoCursor) collection.find(doc).iterator();
		
		while (cursor.hasNext()) {	
			System.out.println(cursor.next());	
		}
		
		System.out.println("Listing of user "+id+"  messages is successful ");
		
	}
	
	public static  void listingLastHour(int id) {
		
		//Connection with database
		MongoClient mongo = MongoClients.create( "mongodb://localhost:27017" );
		MongoDatabase db=mongo.getDatabase("Birdy");
		System.out.println("Connected To DataBase ");
		
		//Create Collection 
		MongoCollection collection= db.getCollection("Messages");
		System.out.println("Connected To Collection ");
		Document doc = new Document("id",id);
		MongoCursor cursor=  (MongoCursor) collection.find(doc).iterator();
		
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		
		System.out.println("Listing of user"+id+"  messages");
	}


	public static void remove(int id) {
		
		//Connection with database
		MongoClient mongo = MongoClients.create( "mongodb://localhost:27017" );
		MongoDatabase db=mongo.getDatabase("Birdy");
		System.out.println("Connected To DataBase ");	
		
		//Dealing with collection
		MongoCollection collection= db.getCollection("Messages");
		System.out.println("Collection Matching ");
			
		BasicDBObject theQuery = new BasicDBObject();
		theQuery.put("User_id", id);
		DeleteResult result = collection.deleteMany(theQuery);
		System.out.println("The Numbers of Deleted Messages : " + result.getDeletedCount());		
	}
		
}
	
	
	


