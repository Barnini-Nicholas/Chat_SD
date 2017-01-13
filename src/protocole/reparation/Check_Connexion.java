package protocole.reparation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

/**
 * Classe threader qui permet de tester la connexion � un pair distant
 * 
 * @author Barnini
 *
 */
public class Check_Connexion implements Runnable {

	private Socket socketSuccesseur;

	public Check_Connexion(Socket socket) {
		// TODO Auto-generated constructor stub
		this.setSocketSuccesseur(socket);
	}

	@Override
	public void run() {

		// On cr�� la socket
		Socket sock = new Socket();
		try {
			// On initialise l'adresse � contacter
			SocketAddress sockaddr = new InetSocketAddress(this.getSocketSuccesseur().getInetAddress().getHostAddress(),
					this.getSocketSuccesseur().getPort());

			// On choisit le timeout
			int timeoutMs = 500;

			// On se connecte en d�finissant le timeout
			sock.connect(sockaddr, timeoutMs);

		} catch (SocketTimeoutException e) {
			System.out.println("PAIR TIMEOUT : Le pair n'est plus pr�sent, il faut donc chercher un rempla�ant !");
			// TODO
			// On cherche le rempla�ant
			// Si le 1er successeur dead, on demande au 2eme ces successeurs,
			// idem pour le 2eme on demande au 3eme
			// Mais si le dernier (3eme) est dead on demande au 2eme pour
			// compl�ter.
			// TODO
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// On ferme dans tous les cas
				e.printStackTrace();
			}
		}
	}

	public Socket getSocketSuccesseur() {
		return socketSuccesseur;
	}

	public void setSocketSuccesseur(Socket socketSuccesseur) {
		this.socketSuccesseur = socketSuccesseur;
	}
}
