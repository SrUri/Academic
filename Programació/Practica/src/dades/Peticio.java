package dades;

import java.util.Scanner;

public class Peticio {
    private String codi;
    private Usuari usuariOfereix;
    private Usuari usuariRep;
    private Producte desitjat;
    private Producte oferit;
    private int resposta;
    private int valoracioOfereix;
    private int valoracioRep;

    public Peticio(String codi, Usuari usuariOfereix, Usuari usuariRep, Producte desitjat, Producte oferit) {
        this.codi = codi;
        this.usuariOfereix = usuariOfereix;
        this.usuariRep = usuariRep;
        this.desitjat = desitjat;
        this.oferit = oferit;
        resposta = 0;
    }

    public String getcodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public Usuari getUsuariOfereix() {
        return usuariOfereix;
    }

    public void setUsuariOfereix(Usuari usuariOfereix) {
        this.usuariOfereix = usuariOfereix;
    }

    public Usuari getUsuariRep() {
        return usuariRep;
    }

    public void setUsuariRep(Usuari usuariRep) {
        this.usuariRep = usuariRep;
    }

    public Producte getDestijat() {
        return desitjat;
    }

    public void setUsuariDestijat(Producte desitjat) {
        this.desitjat = desitjat;
    }

    public Producte getOferit() {
        return oferit;
    }

    public void setUsuariOferit(Producte oferit) {
        this.oferit = oferit;
    }

    public int getResposta() {
        return resposta;
    }

    public void setResposta(int resposta) {
        resposta = 0;
    }
    /* 
    public tipus() {
        String t = oferit.getTipusProd();
    }
    */

    public void valoracions() {
        if(resposta == 1) {
            Scanner ans = new Scanner(System.in);
            System.out.println("Com valoraries aquest intercanvi com a oferidor de la petició?");
            valoracioOfereix = ans.nextInt();
            oferit.setIntercanviat(true);

            System.out.println("Com valoraries aquest intercanvi com a rebedor de la petició?");
            valoracioRep = ans.nextInt();
            desitjat.setIntercanviat(true);
           
            ans.close();
        }
    }

    public Peticio copia(){
        Peticio aux = new Peticio(codi, usuariOfereix, usuariRep, desitjat, oferit);
        aux.resposta = 0;
        return aux;
    } 

    @Override
    public String toString() {
        return "Peticio [codi=" + codi + ", usuariOfereix=" + usuariOfereix + ", usuariRep=" + usuariRep + ", desitjat="
                + desitjat + ", oferit=" + oferit + ", resposta=" + resposta + "]";
    }
}
