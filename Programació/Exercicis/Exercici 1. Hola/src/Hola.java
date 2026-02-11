import java.util.*;

public class Hola {

	public static void main(String[] args) {
		System.out.println("Indica un numero");
		try (Scanner scan = new Scanner(System.in)) {
			String numero1=scan.nextLine();
			System.out.println("El numero que has escrito es: "+numero1);
		}
		

	}

}
