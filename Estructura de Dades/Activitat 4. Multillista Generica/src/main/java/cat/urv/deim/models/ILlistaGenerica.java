package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public interface ILlistaGenerica<E> {
    // Metode per insertar un element a la llista. No importa la posicio on s'afegeix l'element
    public void inserir(E e);

    // Metode per a esborrar un element de la llista
    public void esborrar(E e) throws ElementNoTrobat;

    // Metode per a comprovar si un element esta a la llista
    public boolean buscar(E e);

    // Metode per a comprovar si la llista te elements
    public boolean esBuida();

    // Metode per a obtenir el nombre d'elements de la llista
    public int longitud();

    //Metode per a poder iterar pels elements de la llista
    public Iterator<E> iterator();
}