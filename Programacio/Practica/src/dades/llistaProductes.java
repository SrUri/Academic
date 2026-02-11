package dades;

import java.util.Scanner;

public class llistaProductes {

    private int nProductes;
    private Producte[] llistaProd;

    public llistaProductes(int mida) {
        llistaProd = new Producte[mida];
        nProductes = 0;
    }

    public void afegeixProducte(Producte prod) {
        if (nProductes < llistaProd.length) {
            llistaProd[nProductes] = prod.copia();
            nProductes++;
        }
    }

    public void eliminaProducteFisic(Producte prod) {
        Scanner teclat = new Scanner(System.in);
        System.out.println("Introdueix el nom del producte: ");
        String nom = teclat.nextLine();
        System.out.println("Introdueix la descripció: ");
        String desc = teclat.nextLine();
        System.out.println("Introdueix la data d'oferta: [dd/mm/aaaa]");
        String dataOferta = teclat.nextLine();
        teclat.close();

        String[] dataSplit = dataOferta.split("/");
        Data dataProducte = new Data(Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[2]));
        
        for (int i = 0; i < nProductes; i++) {
            if (llistaProd[i].getTipusProd().equalsIgnoreCase("be") && (llistaProd[i].getIntercanviat() == false)
                && llistaProd[i].getNomProd().equalsIgnoreCase(nom) && llistaProd[i].getDescripcio().equalsIgnoreCase(desc)
                    && llistaProd[i].getDataOferta().equals(dataProducte)) {
                llistaProd[i] = llistaProd[i+1];
                for (int j = i; j < nProductes-1; j++) {
                    llistaProd[j] = llistaProd[j + 1];
                }
                nProductes--;
            }
        }
    }

    public void donarDeBaixaServei(Producte prod) {
        Scanner teclat = new Scanner(System.in);
        System.out.println("Introdueix el nom del producte: ");
        String nom = teclat.nextLine();
        System.out.println("Introdueix la descripció: ");
        String desc = teclat.nextLine();
        System.out.println("Introdueix data de finalització de servei: [dd/mm/aaaa]");
        String data = teclat.nextLine();
        teclat.close();
        String[] dataSplit = data.split("/");
        Data dataFiServei = new Data(Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[2]));
        
        for(int i = 0; i < nProductes; i++){
            if (llistaProd[i].getTipusProd().equals("servei") && llistaProd[i].getDataOferta().equals(dataFiServei) 
                && llistaProd[i].getNomProd().equals(nom) && llistaProd[i].getDescripcio().equals(desc)){
                ((Servei)llistaProd[i]).dataFiOferiment = dataFiServei;
            }
        }
    }

    public String toString() {
        String text = "";
        for (int i = 0; i < nProductes; i++) {
            text = text + llistaProd[i] + "\n";
        }
        return text;
    }

}
