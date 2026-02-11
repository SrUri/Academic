
public class Votacions {

	// Atributs de classe
	private static String candidatMesVotat;
	private static int maximVots;
	
	// Atributs dels objectes
	private String nomCandidat;
	private int vots;
	
	
	// Constructor
	public Votacions (String nom) {
		nomCandidat = nom;
		vots=0;
	}
	
	// Mètodes de classe
	public static String guanyadorEleccions () {
		return candidatMesVotat;
	}
	
	public static int votsGuanyador () {
		return maximVots;
	}
	
	// Mètodes dels objectes
	public void votar () {
		vots++;
		if (vots>maximVots) {
			maximVots=vots;
			candidatMesVotat=nomCandidat;
		}
	}
	
	public int quantsVots () {
		return vots;
	}
	
	public String nomCandidat () {
		return nomCandidat;
	}

}

