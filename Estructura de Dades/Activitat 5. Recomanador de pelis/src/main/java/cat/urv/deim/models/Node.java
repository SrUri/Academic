package cat.urv.deim.models;

public class Node<E> {
    
    private E valor;
    private Node<E> seguent;
    private Node<E> anterior;

    public Node(E valor) {
        this.valor = valor;
        this.seguent = null;
        this.anterior = null;
    }

    public Node(int usuari, int qualificacio) {
    }

    public E getElement() {
        return valor;
    }

    public void setElement(E valor) {
        this.valor = valor;
    }

    public Node<E> getSeguent() {
        return seguent;
    }

    public void setSeguent(Node<E> seguent) {
        this.seguent = seguent;
    }

    public Node<E> getAnterior() {
        return anterior;
    }

    public void setAnterior(Node<E> anterior) {
        this.anterior = anterior;
    }

    public boolean hasNext() {
        return seguent != null;
    }

    public E next() {
        if (seguent == null) {
            return null;
        }
        E valor = seguent.getElement();
        seguent = seguent.getSeguent();
        return valor;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}