package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public interface IPelicules {

    // Metode per a afegir una pelicula
    public void inserir(Pelicula p);

    // Metode per a esborrar una pelicula
    public void esborrar(Pelicula p) throws ElementNoTrobat;

    // Metode per a saber si una pelicula esta guardada
    public boolean buscar(Pelicula p);

    // Metode per saber si hi ha alguna pelicula guardada a l'estructura
    public boolean esBuida();

    // Metode per saber quantes pelicules hi ha emmagatzemades
    public int longitud();

    // Funcio que busca quantes pelicules hi ha d'un any en concret
    public int comptarPeliculesAny(int any);

    // Funcio que ens diu l'any en que va sortir una pelicula
    public int buscarAnyPelicula(String titol) throws ElementNoTrobat;

    // Funcio que retorna la pelicula segons el titol
    public Pelicula buscarPeliculaTitol(String titol) throws ElementNoTrobat;

    // Funcio que retorna totes les pelicules de l'estructura
    public Pelicula[] llistaPelicules();

}
