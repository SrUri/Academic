package cat.urv.deim.models;

public class Pelicula implements Comparable<Pelicula> {
    private int idP;
    private String titol;
    private int any;

    public Pelicula(int idP, String titol, int any) {
        this.idP = idP;
        this.titol = titol;
        this.any = any;
    }

    public int getID() {
        return this.idP;
    }

    public String getTitol() {
        return this.titol;
    }

    public int getAny() {
        return this.any;
    }

    // Comparem amb tres criteris, primer el titol, despres l'any i per últim l'id
    @Override
    public int compareTo(Pelicula peliculaAComparar) {
        if(this.titol.equals(peliculaAComparar.getTitol())) {
            if(this.any == peliculaAComparar.getAny()) {
                if(this.idP == peliculaAComparar.getID()) {
                    return 0;
                } else if(this.idP > peliculaAComparar.getID()) {
                    return 1;
                } else {
                    return -1;
                }
            } else if(this.any > peliculaAComparar.getAny()) {
                return 1;
            } else {
                return -1;
            }
        } else if(this.titol.compareTo(peliculaAComparar.getTitol()) > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
