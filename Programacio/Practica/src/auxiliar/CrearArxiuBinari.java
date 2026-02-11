package auxiliar;
import java.io.*;
import java.util.Scanner;

public class CrearArxiuBinari {
    
    public static void main(String[] args) throws IOException {
        Scanner fit = new Scanner(new File("Usuaris.txt"));
        ObjectOutputStream fitxerBinari = new ObjectOutputStream(new FileOutputStream("Usuaris.bin"));
        String frase;

        while (fit.hasNextLine()) {
            frase = fit.nextLine();
            System.out.println("Linia llegida! "+frase);

            fitxerBinari.writeObject(frase); //Escrivim el contingut del fitxer mentre no sigui final
        }
        System.out.println("Fitxer generat!");
        fit.close();
        fitxerBinari.close();
    }
}
