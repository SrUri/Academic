package dades;

public class Usuari {
    
    private String alies;
    private String correu;
    private String codiPostal;
    private int numeroIntercanvis;

    
    public Usuari (String alies, String correu, String codiPos){
        this.alies = alies;
        this.correu = correu;
        codiPostal = codiPos;
        numeroIntercanvis = 0;
    }


    public String getAlies() {
        return alies;
    }


    public void setAlies(String alies) {
        this.alies = alies;
    }


    public String getCorreu() {
        return correu;
    }


    public void setCorreu(String correu) {
        this.correu = correu;
    }


    public String getCodiPostal() {
        return codiPostal;
    }


    public void setCodiPostal(String codiPostal) {
        this.codiPostal = codiPostal;
    }


    public int getNumeroIntercanvis() {
        return numeroIntercanvis;
    }


    public void setNumeroIntercanvis(int numeroIntercanvis) {
        this.numeroIntercanvis = numeroIntercanvis;
    }


    public Usuari copia(){
        Usuari aux;
        aux = new Usuari(alies, correu, codiPostal);
        aux.numeroIntercanvis = 0;
        return (aux);
    }

    @Override
    public String toString() {
        return "Usuaris [alies=" + alies + ", correu=" + correu + ", codiPostal=" + codiPostal + ", numeroIntercanvis="
                + numeroIntercanvis + " ]";
    }

}


