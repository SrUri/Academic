import java.io.*;

public class UsaVotacions {
	
	public static void main (String [] args) throws IOException {
	
		Votacions oscar = new Votacions("Oscar del Valle");
		Votacions marga = new Votacions("Margarita Pala");
		Votacions leo = new Votacions("Leonardo da Vinci");
		
		oscar.votar(); oscar.votar();
		marga.votar(); leo.votar(); leo.votar(); 
		System.out.println("Primera ronda, guanya: "+Votacions.guanyadorEleccions());
		leo.votar();
		System.out.println("Segona ronda, guanya: "+Votacions.guanyadorEleccions());
        marga.votar(); oscar.votar(); leo.votar();
        marga.votar(); oscar.votar(); oscar.votar();		
        System.out.println("Ultima ronda.");
        System.out.println("Vots de Oscar:"+oscar.quantsVots());
        System.out.println("Vots de Marga:"+marga.quantsVots());
        System.out.println("Vots de Leo:"+leo.quantsVots());
        System.out.println("Vots de Guanyador:"+Votacions.votsGuanyador());
        System.out.println("Nom del guanyador de les eleccions:"+Votacions.guanyadorEleccions());     

	}	
}