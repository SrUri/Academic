package cat.urv.deim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.PatternSyntaxException;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class Main {
    LlistaGenerica<Pelicula> pelicules = new LlistaGenericaPelis();

    //Funció que llegeix el fitxer i ho guarda a la llista de películes
    public void carregarFitxer(String filename) throws IOException, NumberFormatException, PatternSyntaxException {
        BufferedReader fitxer = new BufferedReader(new FileReader(filename));
        String linia = "";
        String liniaSplit[];
        linia = fitxer.readLine();
        
       
        while(linia != null) {
            try {
                liniaSplit = linia.split("###");
                Pelicula pelicula = new Pelicula(Integer.parseInt(liniaSplit[0]), liniaSplit[2], Integer.parseInt(liniaSplit[1]));
                pelicules.inserir(pelicula);
                linia = fitxer.readLine();
            } catch (NumberFormatException e) {
                System.out.println("Error al llegir el fitxer");
                linia = fitxer.readLine();
            } catch (PatternSyntaxException e) {
                System.out.println("Error al llegir el fitxer");
                linia = fitxer.readLine();
            } catch (IOException e) {
                System.out.println("Error al llegir el fitxer");
                linia = fitxer.readLine();
            }
        }
        fitxer.close();
    }

    //Funció que retorna si tenim pelicules o no
    public boolean esBuida() {
        return(pelicules.esBuida());
    }

    //Funcio que busca quantes películes hi ha d'un any en concret
    public int comptarPeliculesAny(int any) {
        int contador = 0;
        for (int i = 0; i < pelicules.longitud(); i++) {
            if (((Pelicula) pelicules.elements()[i]).getAny() == any) {
                contador++;
            }
        }
        return contador;
    }

    //Funció que ens diu l'any en que va sortir una película
    public int buscarAnyPelicula(String titol) throws ElementNoTrobat {
        for (int i = 0; i < pelicules.longitud(); i++) {
            if (((Pelicula) pelicules.elements()[i]).getTitol().equals(titol)) {
                return ((Pelicula) pelicules.elements()[i]).getAny();
            }
        }
        throw new ElementNoTrobat(titol);
    }

    //Funció que retorna la pelicula segons el titol
    public Pelicula buscarPelicula(String titol) throws ElementNoTrobat {
        for (int i = 0; i < pelicules.longitud(); i++) {
            if (((Pelicula) pelicules.elements()[i]).getTitol().equals(titol)) {
                return ((Pelicula) pelicules.elements()[i]);
            }
        }
        throw new ElementNoTrobat(titol);
    }

    //Funcio que esborra una película de l'estructura
    public void esborraPelicula(Pelicula pelicula) throws ElementNoTrobat {
        try {
            buscarPelicula(pelicula.getTitol());
            pelicules.esborrar(pelicula);
        } catch (ElementNoTrobat e) {
            throw new ElementNoTrobat(pelicula.getTitol());
        }
    }

    public static void main(String[] args) {
        System.out.println("No cal que executis això, mira els tests!");
    }
}
