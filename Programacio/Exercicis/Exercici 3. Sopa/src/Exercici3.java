import java.util.Scanner;

public class Exercici3 {
	static Scanner teclat=new Scanner(System.in);
	public static void main(String[] args) {
		String paraula;
		int dim;
		char[][] sopa;
		
		// Introduim informació
		System.out.println("Indica la paraula que amagarem a la sopa:");
		paraula=teclat.nextLine();
		System.out.println("Indica ara la dimensió de la sopa. Recorda que ha de ser superior al número de lletres de la paraula:");
		dim=Integer.parseInt(teclat.nextLine());
		
		// només continuem si hem introduït bé les dades
		
		if (dim>paraula.length()) {
			sopa=new char[dim][dim];
			inicialitzarSopa(sopa);
			ubicarParaulaSopa(paraula, sopa);
			reomplirSopa(sopa);
			mostrarSopa(sopa);
		} else {
			System.out.println("No has complert amb les especificacions, no podem jugar");
		}
		
		teclat.close();
	}
	
	private static void inicialitzarSopa(char[][] sopa) {
		for (int i=0; i<sopa.length; i++) {
			for (int j=0; j<sopa[0].length; j++) {
				sopa[i][j]=' ';
			}
		}
		
	}

	private static void ubicarParaulaSopa(String paraula, char[][] sopa) {
		paraula=paraula.toUpperCase(); // la paraula la passem a majúscules
		//TODO codi a completar....

		
	}
	
	private static void reomplirSopa(char[][] sopa) {
		for (int i=0; i<sopa.length; i++) {
			for (int j=0; j<sopa[0].length; j++) {
				if (sopa[i][j]==' ') {
					sopa[i][j]=caracterAleatori();
				}
			}
		}
		
	}
	
	private static char caracterAleatori() {
		char c;
		int valor=(int) (Math.random()*('Z'-'A'+1));
		c=(char)(valor+'A');
		return c;
	}

	private static void mostrarSopa(char[][] sopa) {
		System.out.println("La sopa de lletres que hem construit és:");
		for (int i=0; i<sopa.length; i++) {
			for (int j=0; j<sopa[0].length; j++) {
				System.out.print(sopa[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("\nEl repte és ara buscar la paraula que has amagat...");
	}

}
