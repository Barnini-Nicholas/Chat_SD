package protocole.message;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Classe abstraite permettant de convertir un objet Message en JSON, et
 * vice-versa.
 * 
 * @author Barnini Nicholas
 * 
 */
public abstract class Convert_Message {

	/**
	 * M�thode renvoyant un objet message rempli vi� le JSON
	 * 
	 * @param String
	 *            - format� en JSON
	 * @return Message
	 */
	public static Message jsonToMessage(String jsonToParse) {

		Message retour = null;

		try {
			// Mise en place du parser
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(jsonToParse);
			
			// On remplit les arguments via le JSON
			TypeMessage tm = TypeMessage.valueOf(json.get("typeMessage").toString());
			String sender = json.get("sender").toString();
			String message = json.get("message").toString();
			
			// On initialise le Message
			retour = new Message(tm, sender, message);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retour;
	}

	/**
	 * M�thode renvoyant un String au format JSON gr�ce � un objet Message
	 * 
	 * @param Message
	 * @return String - format� en JSON
	 */
	@SuppressWarnings("unchecked")
	public static String messageToJson(Message message) {

		// Initialisation du JSON
		JSONObject obj = new JSONObject();
		obj.put("typeMessage", message.getTypeMessage());
		obj.put("sender", message.getSender());
		obj.put("message", message.getMessage());

		return obj.toString();
	}

	public static void main(String[] args) {
		Message m = new Message(TypeMessage.MessageChat, "127.0.0.1:8001", "HOLA QUE TAL");
		String json;

		System.out.println("TEST des m�thodes :\n");
		
		System.out.println("\tTest de Message � JSON :");
		System.out.println("\t\tMessage au d�part : " + m.toString());
		System.out.println("\t\tJSON : " + (json = Convert_Message.messageToJson(m)));

		System.out.println("\n\tTest de JSON � Message :");
		System.out.println("\t\tJSON au d�part : " + json);
		System.out.println("\t\tMessage : " + Convert_Message.jsonToMessage(json));
	}

}
