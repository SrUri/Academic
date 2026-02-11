package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class LlistaPelicules implements IPelicules {
    
    LlistaDoblementEncadenada<Pelicula> llistaPelicules = new LlistaDoblementEncadenada<Pelicula>();

    //Mètode per insertar un element a la llista. No importa la posició on s'afegeix l'element
    public void inserir(Pelicula e) {
        llistaPelicules.inserir(e);
    }

    //Mètode per a esborrar un element de la llista
    public void esborrar(Pelicula e) throws ElementNoTrobat {
        llistaPelicules.esborrar(e);
    }

    //Mètode per a comprovar si un element està a la llista
    public boolean buscar(Pelicula pelicula) {
        return llistaPelicules.buscar(pelicula);
    }

    //Mètode per a comprovar si la llista té elements
    public boolean esBuida() {
        return llistaPelicules.esBuida();
    }

    //Mètode per a obtenir el nombre d'elements de la llista
    public int longitud() {
        return llistaPelicules.longitud();
    }

    //Funcio que busca quantes películes hi ha d'un any en concret
    public int comptarPeliculesAny(int any) {
        int compt=0;
        Iterator<Pelicula> iterador = llistaPelicules.iterator();
        for (iterador.hasNext(); iterador.next() != null;) {
            Pelicula peli = iterador.next();
            if (peli.getAny() == any) {
                compt++;
            }
        }
        return compt;
    }

    //Funció que ens diu l'any en que va sortir una película
    public int buscarAnyPelicula(String titol) throws ElementNoTrobat {
        Iterator<Pelicula> iter = llistaPelicules.iterator();
        while (iter.hasNext()) {
            Pelicula peli = iter.next();
            if (peli.getTitol().equals(titol)) {
                return peli.getAny();
            }
        }
        throw new ElementNoTrobat("No s'ha trobat la pel·lícula");
    }

    public Pelicula buscarPeliculaTitol(String titol) throws ElementNoTrobat {
        Iterator<Pelicula> iter = llistaPelicules.iterator();
        while (iter.hasNext()) {
            Pelicula peli = iter.next();
            if (peli.getTitol().equals(titol)) {
                return peli;
            }
        }
        throw new ElementNoTrobat("No s'ha trobat la pel·lícula");
    }

    public Pelicula[] llistaPelicules() {
        Pelicula[] pelicules = new Pelicula[llistaPelicules.longitud()];
        Iterator<Pelicula> iter = llistaPelicules.iterator();
        int i = 0;
        while (iter.hasNext()) {
            pelicules[i] = iter.next();
            i++;
        }
        return pelicules;
    }
}