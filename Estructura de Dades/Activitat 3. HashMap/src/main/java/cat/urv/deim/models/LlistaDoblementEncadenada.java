package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class LlistaDoblementEncadenada<E extends Comparable<E>> implements ILlistaGenerica<E> {

    Node<E> primer;
    Node<E> ultim;
    int tamany;

    public LlistaDoblementEncadenada() {
        primer = null;
        ultim = null;
        tamany = 0;
    }

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

    //Mètode per a obtenir l'element d'una posició concreta de la llista
    public Iterator<E> iterator() {
        return new IteradorLlista<E>(primer);
    }
}
