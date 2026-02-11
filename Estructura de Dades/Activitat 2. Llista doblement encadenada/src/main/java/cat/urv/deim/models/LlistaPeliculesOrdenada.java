package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class LlistaPeliculesOrdenada extends LlistaPelicules {
    
    private LlistaDoblementEncadenadaOrdenada<Pelicula> llistaPeli = new LlistaDoblementEncadenadaOrdenada<Pelicula>();

    //Mètode per insertar un element a la llista. No importa la posició on s'afegeix l'element
    public void inserir(Pelicula e) {
        llistaPeli.inserir(e);
    }
    
    //Mètode per a esborrar un element de la llista
    public void esborrar(Pelicula e) throws ElementNoTrobat {
        llistaPeli.esborrar(e);
    }
    
    //Mètode per a comprovar si un element està a la llista
    public boolean buscar(Pelicula pelicula) {
        return llistaPeli.buscar(pelicula);
    }
    
    //Mètode per a comprovar si la llista té elements
    public boolean esBuida() {
        return llistaPeli.esBuida();
    }
    
    //Mètode per a obtenir el nombre d'elements de la llista
    public int longitud() {
        return llistaPeli.longitud();
    }
    
    //Funcio que busca quantes películes hi ha d'un any en concret
    public int comptarPeliculesAny(int any) {
        int compt=0;
        Object[] elements = llistaPeli.elements();
        for (Object elem : elements) {
            Pelicula peli = (Pelicula) elem;
            if (peli.getAny() == any) {
                compt++;
            }
        }
        return compt;
    }
    
    //Funció que ens diu l'any en que va sortir una película
    public int buscarAnyPelicula(String titol) throws ElementNoTrobat {
        Object[] elements = llistaPeli.elements();
        for (Object elem : elements) {
            Pelicula peli = (Pelicula) elem;
            if(peli.getTitol().equals(titol)) {
                return peli.getAny();
            }
        }
        throw new ElementNoTrobat("No s'ha trobat la pel·lícula");
    }
    
    //Funció que retorna la pelicula segons el titol
    public Pelicula buscarPelicula(String titol) throws ElementNoTrobat {
        Object[] elements = llistaPeli.elements();
        for(Object elem : elements) {
            Pelicula peli = (Pelicula) elem;
            if(peli.getTitol().equals(titol)) {
                return peli;
            }
        }
        throw new ElementNoTrobat("No s'ha trobat la pel·lícula");
    }
    
    //Metode per a obtenir un array amb tots els elements
    public Pelicula[] elements() {
        Object[] elements = llistaPeli.elements();
        Pelicula[] pelis = new Pelicula[elements.length];
        for(int i=0; i<elements.length; i++) {
            pelis[i] = (Pelicula) elements[i];
        }
        return pelis;
    }
}
