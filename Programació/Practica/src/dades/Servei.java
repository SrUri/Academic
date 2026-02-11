package dades;


public class Servei extends Producte {
    
    protected Data dataFiOferiment;

    public Servei(){
        super(null, null, null, null);
        dataFiOferiment = null;
    }

    public Data getDataFiOferiment() {
        return dataFiOferiment;
    }

    public void setDataFiOferiment(Data dataFiOferiment) {
        this.dataFiOferiment = dataFiOferiment;
    }

    
}
