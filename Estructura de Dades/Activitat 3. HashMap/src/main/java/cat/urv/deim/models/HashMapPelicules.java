package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class HashMapPelicules implements IPelicules {
    
    private IHashMap<String, Pelicula> mapa;

    public HashMapPelicules() {
        mapa = new HashMapIndirecte<String, Pelicula>(500);
    }

    public void inserir(Pelicula p) {
        mapa.inserir(p.getTitol(), p);
    }

    public void esborrar(Pelicula p) throws ElementNoTrobat {
        if (!mapa.buscar(p.getTitol()))
            throw new ElementNoTrobat("No s'ha trobat la pel·lícula");
        mapa.esborrar(p.getTitol());
    }

    public boolean buscar(Pelicula p) {
        return mapa.buscar(p.getTitol());
    }

    public boolean esBuida() {
        return mapa.esBuida();
    }

    public int longitud() {
        return mapa.longitud();
    }

    public int comptarPeliculesAny(int any) {
        int comptador = 0;
        Iterator<Pelicula> it = mapa.iterator();
        while (it.hasNext()) {
            Pelicula p = it.next();
            if (p.getAny() == any) {
                comptador++;
            }
        }
        return comptador;
    }

    public int buscarAnyPelicula(String titol) throws ElementNoTrobat{
        Pelicula p = mapa.element(titol);
        if (p == null)
            throw new ElementNoTrobat("No s'ha trobat la pel·lícula");
        return p.getAny();
    }

    public Pelicula buscarPelicula(String titol) throws ElementNoTrobat{
        if (!mapa.buscar(titol))
            throw new ElementNoTrobat("No s'ha trobat la pel·lícula");
        return mapa.element(titol);
    }

    public Pelicula buscarPeliculaTitol(String titol) throws ElementNoTrobat{
        if (!mapa.buscar(titol))
            throw new ElementNoTrobat("No s'ha trobat la pel·lícula");
        return mapa.element(titol);

    }

    public Pelicula[] llistaPelicules() {
        Pelicula[] llista = new Pelicula[longitud()];
        Iterator<Pelicula> it = mapa.iterator();
        int i = 0;
        while (it.hasNext()) {
            llista[i] = it.next();
            i++;
        }
        return llista;
    }
}
