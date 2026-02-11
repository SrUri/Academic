package cat.urv.deim.models;

public class NodeUnio<T1> {
    public NodeUnio<T1> seguent;
    public T1 valor;
    
    public NodeUnio(T1 valor) {
        this.valor = valor;
        this.seguent = null;
    }

    public NodeUnio<T1> getSeguent() {
        return seguent;
    }

    public void setSeguent(NodeUnio<T1> seguent) {
        this.seguent = seguent;
    }
}
