package cat.urv.deim;

public class Pelicula {
    private int idP;
    private String titol;
    private int any;

    public Pelicula(int idP, String titol, int any) {
        this.idP = idP;
        this.titol = titol;
        this.any = any;
    }

    public int getID() { return this.idP; }
    
    public String getTitol() { return this.titol; }

    public int getAny() { return this.any; }
}
