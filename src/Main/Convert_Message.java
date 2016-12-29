package Main;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Barnini Nicholas
 * 
 * Classe abstraite permettant de convertir un objet Message en JSON, et vice-versa. 
 * 
 */
public abstract class Convert_Message {

	/**
	 * M�thode renvoyant un objet message rempli vi� le JSON 
	 * 
	 * @param String - format� en JSON
	 * @return Message
	 */
	public static Message jsonToMessage(String json){
		return null;
	}
	
	/**
	 * M�thode renvoyant un String au format JSON gr�ce � un objet Message
	 * 
	 * @param Message
	 * @return String - format� en JSON
	 */
	public static String messageToJson(Message message){
		
		JSONObject obj = new JSONObject();
		try {
			obj.put("typeMessage", message.getTypeMessage());
			obj.put("sender", message.getSender());
			obj.put("message", message.getMessage());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.err.println("Probl�me lors de l'�criture du JSON");
			e.printStackTrace();
		}
		
		return obj.toString();
	}
	
	public static void main(String[] args) {
		Message m = new Message(TypeMessage.MessageChat, "127.0.0.1:8001", "HOLA QUE TAL");
		System.out.println(Convert_Message.messageToJson(m));
	}
	
}
