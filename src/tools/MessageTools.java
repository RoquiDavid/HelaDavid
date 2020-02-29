package tools;

import org.bson.Document;

public class MessageTools{

	public static String getMessage(ObjectId mid, MongoCollection<Document> coll) throws Exception{
		Document doc = new Document();
		doc.append("_id", mid);
		System.out.println(doc.toString());
		MongoCursor<Document> cursor = coll.find(doc).iterator();
		String message = null;
		if(cursor.hasNext()) {
			message = cursor.next().getString("contenu");
		} else {
			throw new Exception();
		}
		return message;
		
	}

	public static String createMessage( String content, MongoCollection<Document> coll) {
		Document doc = new Document();
		
		doc.append("contenu", content);
		coll.insertOne(doc);
		return doc.getObjectId("_id").toHexString();
	}

	public static void deleteMessage(ObjectId message_id, MongoCollection<Document> coll) {
		Document doc = new Document();
		doc.put("message_id", message_id);
		coll.deleteOne(doc);
	}

	public static boolean existMessage(ObjectId message_id,MongoCollection<Document> coll) throws Exception {
		Document doc = new Document("_id", message_id);
		MongoCursor<Document> cursor = coll.find(doc).iterator();
		String message = null;
		if(cursor.hasNext()) {
			message = cursor.next().getString("contenu");
			
		} else {
			throw new Exception();
		}
		if(message != null) {
			return true;
		}
		System.out.println(message);
		return false;
	}

	public static UpdateResult updateMessage(ObjectId message_id, String idUser, String content, MongoCollection<Document> coll) throws Exception {
		UpdateResult updateResult = null;
		
		Document doc = new Document("_id", message_id);
		Document found = coll.find(doc).first();
		
		if(found != null) {
			 System.out.println("user found");
			 Bson updatevalue  = new Document("contenu", content);
			 Bson updateOperation = new Document("$set", updatevalue);
			 coll.updateOne(found,updateOperation);
			 updateResult = coll.updateOne(found, updateOperation); 
			 System.out.println("Message Updated");
		}
		return updateResult;
			
	}
}
