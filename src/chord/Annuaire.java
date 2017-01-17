package chord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import protocole.message.Convert_Message;
import protocole.message.Message;
import protocole.message.TypeMessage;

public class Annuaire {

	/** Liste de Pairs r�cents */
	public ArrayList<SalonInfos> listSalons;

	/** Liste de Pairs r�cents */
	public ArrayList<PairInfos> listPairRecent;

	/** Nombre max de Pairs r�f�renc�s dans l'annuaire */
	public int maxPairSvg;

	/** Port o� se trouve l'annuaire l'annuaire */
	public int port;

	public Annuaire(int max, int port) {
		maxPairSvg = max;
		listPairRecent = new ArrayList<PairInfos>();
		listSalons = new ArrayList<SalonInfos>();
		this.port = port;

		listen();
	}

	/**
	 * Thread pour �couter
	 */
	public void listen() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				acceptNewClient();
			}

		}).start();
	}

	/**
	 * M�thode qui permet � l'annuaire d'accepter des connexions et donc de
	 * nouveaux clients
	 */
	private void acceptNewClient() {
		try {
			// On �coute sur le port donn�
			ServerSocket ecoute = new ServerSocket(port);
			while (true) {
				// On attend une connexion client
				Socket client = ecoute.accept();

				InputStream is = client.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String msgString = br.readLine();

				// On r�cup�re le message du Client
				Message messageClient = Convert_Message.jsonToMessage(msgString);

				// On r�cupere les infos du Client
				PairInfos destInfos = messageClient.getPairInfos();

				// Message qui sera envoy�
				String message = "";

				switch (messageClient.getTypeMessage()) {

				case AjoutPair:

					// On transforme la liste des Pairs r�cents en String
					for (int i = 0; i < listPairRecent.size(); i++) {
						message += listPairRecent.get(i).getIpPort();

						if (i < listPairRecent.size() - 1)
							message += ";";
					}

					break;

				case JoinSalon:
					// Nom du salon
					String nomSalon = messageClient.getMessage();

					// Si le salon existe d�ja
					if (salonExiste(nomSalon)) {
						// On renvoie au client l'ip port de l'host
						message = getIpPortHostSalon(nomSalon);
					} else {
						// On cr�e un objet qui contient les infos du Salon
						SalonInfos salon = new SalonInfos(messageClient.getMessage(), destInfos);

						// On ajoute les infos du Salon dans la liste
						addSalon(salon);

						// On place dans le message l'ip port du client
						message = destInfos.getIpPort();
					}

					break;

				case GetListeSalons:

					// On transforme la liste des Salons en String
					for (int i = 0; i < listSalons.size(); i++) {
						message += listSalons.get(i).getNom() + "=" + listSalons.get(i).getInfosHost().getIpPort();

						if (i < listSalons.size() - 1) {
							message += "&";
						}
					}

					break;

				default:
					break;
				}

				// Cr�ation du message
				Message m = new Message(messageClient.getTypeMessage(), destInfos.getIpPort(), message);
				String json = Convert_Message.messageToJson(m);

				// Buffer de sortie
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);

				// Envoi du message au client
				out.println(json);

				// On ajout le client � la liste
				addClient(destInfos);

				client.close();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * M�thode qui permet d'ajouter de nouveaux clients dans l'annuaire
	 * 
	 * @param pairInfos
	 *            Pair � ajouter
	 */
	private void addClient(PairInfos pairInfos) {
		// Si on a atteint la taille max, on supprime le plus ancien
		if (this.listPairRecent.size() > this.maxPairSvg) {
			this.listPairRecent.remove(0);
		}
		this.listPairRecent.add(pairInfos);
	}

	/**
	 * M�thode qui permet d'ajouter de nouveaux salons dans l'annuaire
	 * 
	 * @param salon
	 */
	private void addSalon(SalonInfos salon) {
		// Si le salon n'est pas d�ja pr�sent dans la liste
		if (listSalons.contains(salon) == false) {
			// On l'ajoute dans la liste
			listSalons.add(salon);
		}
	}

	private boolean salonExiste(String nom) {
		if (listSalons.isEmpty()) {
			return false;
		}

		// Parcours de la liste des salons
		for (SalonInfos salon : listSalons) {
			if (salon.getNom().equals(nom)) {
				return false;
			}
		}
		return false;

	}

	private String getIpPortHostSalon(String nomSalon) {
		// Parcours de la liste des salons
		for (SalonInfos salon : listSalons) {
			if (salon.getNom().equals(nomSalon)) {
				return salon.getInfosHost().getIpPort();
			}
		}
		return null;
	}

}
