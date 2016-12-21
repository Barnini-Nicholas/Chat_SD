package Main;

import java.util.ArrayList;

public class Chord {

	/** Taille de l'anneaux au d�part */
	public int sizeRing;
	/** Liste des pairs */
	public ArrayList<Pair> listPair;

	/**
	 * Constructeur de Chord avec un nombre de pairs
	 * @param size - Nombre de Pairs � instancier
	 */
	public Chord(int size) {
		this.sizeRing = size;
		this.listPair = new ArrayList<Pair>();
	}

	/**
	 * M�thode initialisabt l'anneau avec des pairs
	 */
	public void initialize() {
		for (int i = 0; i < this.sizeRing; i++) {
			this.listPair.add(new Pair(i, "127.0.0.1", 8000+i));
		}
	}

	public static void main(String[] args) {
		Chord c = new Chord(16);
		c.initialize();
	}
}
