package cat.urv.deim.models;

public class NodeUnio<T1> {
    public NodeUnio<T1> seguent;
    public T1 valor;
    private int qualificacio;
    private int numVegadesPuntuat;
    private String data;

    
    public NodeUnio(T1 pelicula, int qualificacio, String data) {
        this.valor = pelicula;
        this.qualificacio = qualificacio;
        numVegadesPuntuat = 1;
        this.data = data;
    }

    public NodeUnio<T1> getSeguent() {
        return seguent;
    }

    public void setSeguent(NodeUnio<T1> seguent) {
        this.seguent = seguent;
    }

    public T1 getValor() {
        return valor;
    }

    public int getQualificacio() {
        return qualificacio/numVegadesPuntuat;
    }

    public void setQualificacio(int qualif) {
        qualificacio=qualificacio+qualif;
        numVegadesPuntuat++;
    }
}