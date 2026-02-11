package cat.urv.deim.models;

public class Node<E> {
    private E element;
    private Node<E> seguent;
    private Node<E> anterior;

    public Node(E e) {
        this.element = e;
        this.seguent = null;
        this.anterior = null;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
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
}
