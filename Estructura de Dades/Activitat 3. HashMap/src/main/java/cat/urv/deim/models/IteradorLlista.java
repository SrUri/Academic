package cat.urv.deim.models;

public class IteradorLlista<T> implements Iterator<T> {
    private Node<T> actual;

    public IteradorLlista(Node<T> actual) {
        this.actual = actual;
    }

    public boolean hasNext() {
        return actual != null;
    }

    public T next() {
        if (actual == null) {
            return null;
        }
        T valor = actual.getElement();
        actual = actual.getSeguent();
        return valor;
    }
}
