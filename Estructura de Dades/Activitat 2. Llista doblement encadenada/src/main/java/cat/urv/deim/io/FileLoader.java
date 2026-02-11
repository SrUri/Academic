package cat.urv.deim.io;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.models.ILlistaPelicules;
import cat.urv.deim.models.Pelicula;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.PatternSyntaxException;

public class FileLoader {
    public static void carregarFitxer(String path, ILlistaPelicules llista) {
        try {  
            BufferedReader fitxer = new BufferedReader(new FileReader(path));
            String liniaActual="";
            String[] linia;
            liniaActual = fitxer.readLine();
            while (liniaActual != null) {
                try {
                    linia = liniaActual.split("###");
                    llista.inserir(new Pelicula(Integer.parseInt(linia[0]), linia[2], Integer.parseInt(linia[1])));
                    liniaActual = fitxer.readLine();
                } catch (PatternSyntaxException e) {
                    System.out.println("Error de sintaxis en la linia: " + liniaActual);
                    liniaActual = fitxer.readLine();
                } catch (NumberFormatException e) {
                    System.out.println("Error de format en la linia: " + liniaActual);
                    liniaActual = fitxer.readLine();
                }
            }
            fitxer.close();
        } catch (IOException e) {
            System.out.println("Error de lectura del fitxer");
        }
    }
}
