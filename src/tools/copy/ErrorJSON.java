package tools.copy;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorJSON {
	
	public static JSONObject serviceRefused(String message, int code){
		JSONObject error=new JSONObject();
		try {
			error.put("message", message);
			error.put("code", code);
		}catch (JSONException e) {
			e.printStackTrace();
		}
		
		return error;
	}
	

	public static JSONObject serviceAccepted() {	
		return new JSONObject();
	}
	
	public static JSONObject serviceAccepted(String key) {
		JSONObject ok=new JSONObject();
		try {
			ok.put("key", key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ok;
	}
	
	
}