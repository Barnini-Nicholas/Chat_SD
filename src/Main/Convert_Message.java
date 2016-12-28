package Main;

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
	public Message jsonToMessage(String json){
		return null;
	}
	
	/**
	 * M�thode renvoyant un String au format JSON gr�ce � un objet Message
	 * 
	 * @param Message
	 * @return String - format� en JSON
	 */
	public String messageToJson(Message message){
		return null;
	}
	
}
