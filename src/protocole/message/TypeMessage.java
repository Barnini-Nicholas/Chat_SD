package protocole.message;

/**
 * Enum�ration permettant de diff�riencer les diff�rents types de messages.
 * 
 * @author Barnini Nicholas
 * 
 */
public enum TypeMessage {

	// Ajout
	AjoutPair, SuppressionPair,

	// Protocole de maintenance
	Reparation, DemandeClef, CheckConnexion, ModificationSuccesseurs, GetSuccesseurs,

	// Salon
	GetListeSalons, JoinSalon,

	// Message
	MessageChat, MessageSalon, Image;
}
