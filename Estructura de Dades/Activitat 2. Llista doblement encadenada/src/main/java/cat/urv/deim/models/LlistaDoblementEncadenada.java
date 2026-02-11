package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class LlistaDoblementEncadenada<E extends Comparable<E>> implements ILlistaGenerica<E> {

    Node<E> primer = null;
    Node<E> ultim = null;
    int tamany = 0;

    //Mètode per insertar un element a la llista. No importa la posició on s'afegeix l'element
    public void inserir(E e) {
        Node<E> n = new Node<E>(e);
        if (primer == null) {
            primer = n;
            ultim = n;
        } else {
            ultim.setSeguent(n);
            ultim = n;
        }
        tamany++;
    }

    //Mètode per a esborrar un element de la llista
    public void esborrar(E e) throws ElementNoTrobat {
        Node<E> n = primer;
        Node<E> anterior = null;
        while (n != null && n.getElement().compareTo(e) != 0) {
            anterior=n;
            n = n.getSeguent();
        }
        if (n != null) {
            if (n==primer) {
                primer = n.getSeguent();
            } else if (n==ultim) {
                anterior.setSeguent(null);
            } else {
                anterior.setSeguent(n.getSeguent());
            }
            tamany--;
        }
        else 
        {
            throw new ElementNoTrobat("No s'ha trobat l'element");
        }
    }

    //Mètode per a comprovar si un element està a la llista
    public boolean buscar(E e) {
        Node<E> n = primer.getSeguent();
        while (n != primer) {
            if (n.getElement().compareTo(e) == 0) {
                return true;
            }
            n = n.getSeguent();
        }
        return false;
    }

    //Mètode per a comprovar si la llista té elements
    public boolean esBuida() {
        return tamany==0;
    }

    //Mètode per a obtenir el nombre d'elements de la llista
    public int longitud() {
        return tamany;
    }

    //Metode per a obtenir un array amb tots els elements
    public Object[] elements() {
        Pelicula[] llistaPeli = new Pelicula[tamany];
        Node<E> n = primer;
        for (int i = 0; i < tamany; i++) {
            llistaPeli[i] = (Pelicula) n.getElement();
            n = n.getSeguent();
        }
        return llistaPeli;
    }

    public Pelicula obtenir(int pos) {
        return null;
    }
}
