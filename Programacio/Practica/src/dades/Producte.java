package dades;


public class Producte {
    
    private String nomProd;
    private String descripcio;
    private String tipusProd;
    private Data dataOferta;
    private boolean intercanviat;

    public Producte(String nom, String desc, String tipus, Data data){
        nomProd = nom;
        descripcio = desc;
        tipusProd = tipus;
        dataOferta = data;
        intercanviat = false;
    }

    public void modificaDescripcio(String desc){
        if(descripcio.equals(desc)){
            System.out.println("Descripció no vàlida");
        } else {
            descripcio = desc;
        }
    }
    
    public Producte copia(){
        Producte aux = new Producte(nomProd, descripcio, tipusProd, dataOferta);
        return aux;
    } 

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getTipusProd() {
        return tipusProd;
    }

    public void setTipusProd(String tipusProd) {
        this.tipusProd = tipusProd;
    }

    public Data getDataOferta() {
        return dataOferta;
    }

    public void setDataOferta(Data dataOferta) {
        this.dataOferta = dataOferta;
    }

    public boolean getIntercanviat() {
        return intercanviat;
    }

    public void setIntercanviat(boolean boolea) {
        intercanviat = boolea;
    }

    @Override
    public String toString() {
        return "Producte [nomProd = " + nomProd + ", descripcio = " + descripcio + ", tipusProd = " + tipusProd
                + ", dataOferta = " + dataOferta + "]";
    }

}
