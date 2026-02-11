package aplicacio;

import java.io.*;
import java.nio.BufferUnderflowException;

import dades.*;

public class mainAplicacio {
    static final int MAX = 0;

    public static void main(String[] args) throws FileNotFoundException, EOFException, IOException {
        boolean finalFitxer = false;
        // BufferedReader fitUsuaris = new BufferedReader(newFileReader("Usuaris.txt"));
        
        llistaUsuaris llistaUsu = new llistaUsuaris(MAX);

        try {
            llegirPeticions();
            llegirProductes();
            // llegirUsuaris();

        } catch (EOFException e) {
            System.out.println("[+] No hi ha més dades a llegir... " + e);
            finalFitxer = true;
        } catch (FileNotFoundException e) {
            System.out.println("Fitxer incorrecte" + e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void llegirProductes() throws IOException {
        BufferedReader fitProductes = new BufferedReader(new FileReader("Usuaris.txt"));
        boolean finalFitxer = false;
        String frase = "";
        String[] fraseSplit;
        llistaProductes llistaProd = new llistaProductes(MAX);

        while (!finalFitxer) {
            frase = fitProductes.readLine();
            fraseSplit = frase.split(";");
            String[] splitData = fraseSplit[3].split("/");
            Data data = new Data(Integer.parseInt(splitData[0]), Integer.parseInt(splitData[1]), Integer.parseInt(splitData[2]));
            Producte prod = new Producte(fraseSplit[0], fraseSplit[1], fraseSplit[2], data);
            llistaProd.afegeixProducte(prod);
        }
        fitProductes.close();
    }

    public static void llegirPeticions() throws IOException {
        BufferedReader fitPeticions = new BufferedReader(new FileReader("Peticions.txt"));
        String frase = "";
        String[] fraseSplit;
        boolean finalFitxer = false;
        llistaPeticions llistaPet = new llistaPeticions(MAX);
        
        while (!finalFitxer) {
            frase = fitPeticions.readLine();
            fraseSplit = frase.split(";");
            Usuari userOf = new Usuari(fraseSplit[1], fraseSplit[2], fraseSplit[3]);
            Usuari userRep = new Usuari(fraseSplit[5], fraseSplit[6], fraseSplit[7]);

    

            //Peticio peticio = new Peticio(fraseSplit[0], userOf, userRep, );
            //peticio.setNumeroIntercanvis(Integer.parseInt(fraseSplit[3]));
        }
        fitPeticions.close();
    }

    /*
    public static void llegirUsuaris() throws IOException {
        BufferedReader fitxerBinari = new BufferedReader(new FileReader("Usuaris.bin"));
        boolean llegit = false;
        String frase = "";
        String[] fraseSplit;
        
        while(!llegit) {
            frase = fitxerBinari.readLine();
            fraseSplit = frase.split(";");
        }
    } */
}