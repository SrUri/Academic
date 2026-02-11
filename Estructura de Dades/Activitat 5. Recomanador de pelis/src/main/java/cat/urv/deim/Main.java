package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.ElementRepetit;
import cat.urv.deim.io.FileLoader;
import cat.urv.deim.models.*;


public class Main {
    public static void main(String[] args) throws ElementNoTrobat, ElementRepetit {

        HashMapIndirecte<Integer, Pelicula> pelicules;
        HashMapIndirecte<Integer, Usuari> usuaris;
        NodeUnio<Pelicula> nodePelicula;
        NodeUnio<Usuari> nodeUsuari;
        Usuari usuari;

        pelicules = new HashMapIndirecte<Integer, Pelicula>(10000000);
        usuaris = new HashMapIndirecte<Integer, Usuari>(10000000);
        nodePelicula = new NodeUnio<Pelicula>(null, 0, null);
        nodeUsuari = new NodeUnio<Usuari>(null, 0, null);
        MultiLlista multillista = new MultiLlista();

        FileLoader.carregarPelicules(pelicules);
        FileLoader.carregarUsuaris(usuaris, pelicules, nodeUsuari, nodePelicula, multillista);
        
        System.out.println("Relacions: " + multillista.numRelacions());
        System.out.println("Usuaris: " + usuaris.longitud());
        System.out.println("Pelicules: " + pelicules.longitud());
        usuari = usuaris.element(2530404);

        Pelicula[] recomanacions = recomanar(usuari, usuaris, pelicules, multillista);
        
        int i=0;
        while(i < 3){
            System.out.println("Segons les teves valoracions el recomanem: " + recomanacions[i].getTitol());
            i++;
        }
    }

    private static Pelicula[] recomanar(Usuari usuari, HashMapIndirecte<Integer, Usuari> usuaris, HashMapIndirecte<Integer, Pelicula> pelicules, MultiLlista multillista) throws ElementNoTrobat {
        int nota_peli = 0;
        Usuari nouUsuari;
        Pelicula vista = multillista.getPeliVista(usuari);
        Pelicula[] novaPelicula = new Pelicula[3];

        nota_peli = multillista.getNota(usuari, vista);
        nouUsuari = multillista.trobarUsuariNou(nota_peli, usuari, usuaris, pelicules, vista, multillista);
        novaPelicula = multillista.trobarPelicules(nouUsuari, vista, multillista);

        return novaPelicula;
    }
}
