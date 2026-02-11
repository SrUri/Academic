package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class LlistaDoblementEncadenadaOrdenada<E extends Comparable<E>> implements ILlistaGenerica<E> {

    Node<E> primer = null;
    Node<E> ultim = null;
    int tamany = 0;

    //Mètode per insertar un element a la llista. No importa la posició on s'afegeix l'element
    public void inserir(E e) {
        Node<E> n = new Node<E>(e);
        if(primer==null) {
            primer=n;
            ultim=n;
        } else if (primer.getElement().compareTo(e) >= 0) {
            n.setSeguent(primer);
            primer.setAnterior(n);
            primer=n;
        } else if (ultim.getElement().compareTo(e) <= 0) {
            n.setAnterior(ultim);
            ultim.setSeguent(n);
            ultim=n;
        } else {
            Node<E> actual = primer;
            while (actual.getSeguent() != null && actual.getSeguent().getElement().compareTo(e) < 0) {
                actual = actual.getSeguent();
            }
            n.setSeguent(actual.getSeguent());
            n.setAnterior(actual);
            actual.getSeguent().setAnterior(n);
            actual.setSeguent(n);
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
        int primera=0;
        int fi=longitud()-1;
        while(primera<=fi) 
        {
            int mig=(primera+fi)/2;
            Node<E> n = primer;
            for(int i=0;i<mig;i++){
                n=n.getSeguent();
            }
            E valorMig = n.getElement();
            if(valorMig.compareTo(e) < 0){
                primera=mig+1;
            } else if (valorMig.compareTo(e) > 0){
                fi=mig-1;
            } else {
                return true;
            }
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
        Object[] elements = new Object[tamany];
        Node<E> n = primer;
        int i = 0;
        while (n != null) {
            elements[i] = n.getElement();
            n = n.getSeguent();
            i++;
        }
        return elements;
    }
}
