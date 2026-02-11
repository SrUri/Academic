package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public interface ILlistaPelicules extends ILlistaGenerica<Pelicula> {

    //Funcio que busca quantes películes hi ha d'un any en concret
    public int comptarPeliculesAny(int any);

    //Funció que ens diu l'any en que va sortir una película
    public int buscarAnyPelicula(String titol) throws ElementNoTrobat;

    //Funció que retorna la pelicula segons el titol
    public Pelicula buscarPelicula(String titol) throws ElementNoTrobat;

    //Metode per a obtenir un array amb tots els elements
    public Pelicula[] elements();
}
