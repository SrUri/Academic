package dades;

public class llistaPeticions {
    
    private int nPeticions;
    private Peticio[] llistaPeticio;


    public llistaPeticions(int mida) {
        llistaPeticio = new Peticio[mida];
        nPeticions = 0;
    }

    public Peticio getIessim(int i) {
        Peticio aux = llistaPeticio[i].copia();
        return aux;
    }

    public void afegeixPeticio(Peticio pe){
        if(nPeticions < llistaPeticio.length){
            llistaPeticio[nPeticions] = pe.copia();
            nPeticions++;
        }
    }

    public llistaPeticions mostraPeticionsPendents(){
        llistaPeticions llistaAux = new llistaPeticions(nPeticions);
        for (int i = 0; i < nPeticions; i++) {
            if (llistaPeticio[i].getResposta() == 0) {
                llistaAux.afegeixPeticio(llistaPeticio[i]);
            }
        }
        return llistaAux;
    }

    public llistaPeticions mostraPeticionsAcceptades() {
        llistaPeticions llistaAux = new llistaPeticions(nPeticions);
        for(int i = 0; i < nPeticions; i++){
            if(llistaPeticio[i].getResposta() == 1){
                llistaAux.afegeixPeticio(llistaPeticio[i]);
            }
        }
        return llistaAux;
    }

    public llistaPeticions mostraPeticionsRefusades() {
        llistaPeticions llistaAux = new llistaPeticions(nPeticions);
        for(int i = 0; i < nPeticions; i++){
            if(llistaPeticio[i].getResposta() == 2){
                llistaAux.afegeixPeticio(llistaPeticio[i]);
            }
        }
        return llistaAux;
    }
    
    public String toString(){
        String text = "";
        for (int i = 0; i < nPeticions; i++){
            text = text + llistaPeticio[i]+"\n";
        }
        return text;
    }
    
}
