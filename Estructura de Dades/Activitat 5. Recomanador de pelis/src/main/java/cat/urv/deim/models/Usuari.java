package cat.urv.deim.models;

public class Usuari {
    private int idUsuari;
    private int peli;
    private String data;
    private int qualificacio;

    public Usuari(int idUsuari, int peli, String data, int qualificacio) {
        this.idUsuari = idUsuari;
        this.peli = peli;
        this.data = data;
        this.qualificacio = qualificacio;
    }

    public int getID() { return this.idUsuari; }

    public int getPelicula() { return this.peli; }

    public String getData() { return this.data; }

    public int getQualificacio() { return this.qualificacio; }
}
