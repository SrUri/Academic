package cat.urv.deim.io;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.ElementRepetit;
import cat.urv.deim.models.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.PatternSyntaxException;

public class FileLoader {
    public static void carregarPelicules(HashMapIndirecte<Integer, Pelicula> taula) {
        try {
            BufferedReader fitxer = new BufferedReader(new FileReader("movie_titles.csv"));
            String liniaActual="";
            String[] linia;
            liniaActual = fitxer.readLine();
            while (liniaActual != null) {
                try {
                    linia = liniaActual.split(",");
                    Pelicula peli = new Pelicula(Integer.parseInt(linia[0]), linia[2], Integer.parseInt(linia[1]));
                    taula.inserir(Integer.parseInt(linia[0]), peli);
                    liniaActual = fitxer.readLine();
                } catch (PatternSyntaxException e) {
                    System.out.println("Error de sintaxis en la linia: " + liniaActual);
                    liniaActual = fitxer.readLine();
                } catch (NumberFormatException e) {
                    linia = liniaActual.split(",");
                    if(linia[1].equals("NULL")) {
                        Pelicula peli = new Pelicula(Integer.parseInt(linia[0]), linia[2], 0);
                        taula.inserir(Integer.parseInt(linia[0]), peli);
                        System.out.println("Peli amb any 0: " + liniaActual);
                    }
                    else {
                        System.out.println("Error de format en la linia: " + liniaActual);
                    }
                    liniaActual = fitxer.readLine();
                }
            }
            fitxer.close();
        } catch (IOException e) {
            System.out.println("Error de lectura del fitxer");
        }
    }

    public static void carregarUsuaris(HashMapIndirecte<Integer, Usuari> taula, HashMapIndirecte<Integer, Pelicula> pelis, NodeUnio<Usuari> nodeUsuari, NodeUnio<Pelicula> nodePelicula, MultiLlista multi) throws ElementNoTrobat, ElementRepetit {
        int IDpeli=0;
        Pelicula peli=null;
        try{
            BufferedReader fitxer = new BufferedReader(new FileReader("movie_users_10_20.txt"));
            String liniaActual="";
            Usuari usuari;
            int id;
            String[] linia;
            liniaActual = fitxer.readLine();
            while (liniaActual != null) {
                try {
                    linia = liniaActual.split(",");
                    id = Integer.parseInt(linia[0]);
                    usuari = new Usuari(id, IDpeli, linia[2], Integer.parseInt(linia[1]));
                    taula.inserir(id,usuari);
                    multi.inserir(usuari, peli, Integer.parseInt(linia[1]), linia[2]);
                    liniaActual = fitxer.readLine();
                } catch (PatternSyntaxException e) {
                    System.out.println("Error: " + liniaActual);
                    liniaActual = fitxer.readLine();
                } catch (NumberFormatException e) {
                    linia = liniaActual.split(":");
                    IDpeli = Integer.parseInt(linia[0]);
                    peli = pelis.element(IDpeli);
                    liniaActual = fitxer.readLine();
                }
            }
            fitxer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
