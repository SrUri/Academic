package cat.urv.deim;

import java.util.*;
import cat.urv.deim.exceptions.ElementNoTrobat;

public class LlistaGenericaPelis implements LlistaGenerica<Pelicula> {
    
    ArrayList<Pelicula> pelicules = new ArrayList<>();
        
    public void inserir(Pelicula e) {
        pelicules.add(e);
    }

    public void esborrar(Pelicula e) throws ElementNoTrobat {
        if (pelicules.contains(e)) {
            pelicules.remove(e);
        } else {
            System.out.println("No s'ha trobat l'element.");
        }
    }

    public boolean buscar(Pelicula e) {
        return pelicules.contains(e);
    }

    public boolean esBuida() {
        return pelicules.isEmpty();
    }

    public int longitud() {
        return pelicules.size();
    }

    public Object[] elements() {
        return pelicules.toArray();
    }
}