package dades;

public class llistaUsuaris {
    private int nUsuaris;
    private Usuari[] llistaUsuaris;

    public llistaUsuaris(int mesura){
        nUsuaris = 0;
        llistaUsuaris = new Usuari[mesura];
    }

    public void afegeixUsuari(Usuari usuari){
        if(nUsuaris == 0){
            llistaUsuaris[nUsuaris] = usuari.copia();
            nUsuaris++;
        } else {
            if(!comprovaIgualtats(usuari)){
                llistaUsuaris[nUsuaris] = usuari.copia();
                nUsuaris++;
            } 
        }
    }

    public boolean comprovaIgualtats(Usuari usuariAux){
        boolean igual = false;
        int i = 0;
        while(!igual && i < nUsuaris){
            if (llistaUsuaris[i].getAlies().equals(usuariAux.getAlies()) && llistaUsuaris[i].getCorreu().equals(usuariAux.getCorreu())) {
                igual = true;
                System.out.println("Nom d'usuari: "+usuariAux.getAlies()+" i correu: "+usuariAux.getCorreu()+" repetits!");
            } else if (llistaUsuaris[i].getCorreu().equals(usuariAux.getCorreu())){
                igual = true;
                System.out.println("Correu de l'usuari: "+usuariAux.getCorreu()+" repetit! ");
            } else if(llistaUsuaris[i].getAlies().equals(usuariAux.getAlies())) {
                igual = true;
                System.out.println("Nom d'usuari: "+usuariAux.getAlies()+" repetit! ");
            }
            i++;
        }
        return igual;
    }

    public llistaUsuaris valoracioUsuaris(int llindar){
        llistaUsuaris llistaAux = new llistaUsuaris(nUsuaris);
        for(int i = 0; i < nUsuaris; i++){
           /* if (valoracio >= llindar){
                llistaAux.afegeixUsuari(llistaUsuaris[i]);
            } */
        }
        return llistaAux;
    }

    public String toString(){
        String text = "";
        for (int i = 0; i < nUsuaris; i++){
            text = text + llistaUsuaris[i]+"\n";
        }
        return text;
    }

    
}
