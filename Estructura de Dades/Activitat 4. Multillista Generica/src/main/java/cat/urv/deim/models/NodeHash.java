package cat.urv.deim.models;

public class NodeHash<T1,T2> {
    private T1 clau;
    private T2 valor;
    private NodeHash<T1, T2> seguent;

    public NodeHash(T1 clau, T2 valor) {
        this.clau = clau;
        this.valor = valor;
        this.seguent = null;
    }

    public T1 getClau() {
        return clau;
    }

    public void setClau(T1 clau) {
        this.clau = clau;
    }

    public T2 getElement() {
        return valor;
    }

    public void setElement(T2 valor) {
        this.valor = valor;
    }

    public NodeHash<T1, T2> getSeguent() {
        return seguent;
    }

    public void setSeguent(NodeHash<T1, T2> seguent) {
        this.seguent = seguent;
    }
}
