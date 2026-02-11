package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public interface ILlistaGenerica<E> {
    //Mètode per insertar un element a la llista. No importa la posició on s'afegeix l'element
    public void inserir(E e);

    //Mètode per a esborrar un element de la llista
    public void esborrar(E e) throws ElementNoTrobat;

    //Mètode per a comprovar si un element està a la llista
    public boolean buscar(E e);

    //Mètode per a comprovar si la llista té elements
    public boolean esBuida();

    //Mètode per a obtenir el nombre d'elements de la llista
    public int longitud();

    //Metode per a obtenir un array amb tots els elements
    public Object[] elements();
}
