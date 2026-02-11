package aplicacio;

import dades.*;
import java.util.Scanner;

public class mainProductes {
    public static void main(String[] args) {
        
        Data fecha;
        String[] fechaSplit;
        String data;
        llistaProductes llistaProd = new llistaProductes(5);
        Scanner teclat = new Scanner(System.in);
        System.out.println("Introdueix el dia, mes i any amb format [dd/mm/aaaa]: ");
        
        data = teclat.nextLine();
        fechaSplit = data.split("/");
        fecha = new Data(Integer.parseInt(fechaSplit[0]), Integer.parseInt(fechaSplit[1]), Integer.parseInt(fechaSplit[2]));
        
        Producte p1 = new Producte("taula", "vagi be", "be", fecha);
        Producte p2 = new Producte("poma", "aliment", "be", fecha);

        llistaProd.afegeixProducte(p1);
        llistaProd.afegeixProducte(p2);
        System.out.println(llistaProd.toString());
        
        llistaProd.eliminaProducteFisic(p1);
        System.out.println(llistaProd.toString());

        teclat.close();
    }
}
