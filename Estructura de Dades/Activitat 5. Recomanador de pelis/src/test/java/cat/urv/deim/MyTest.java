package cat.urv.deim;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.ElementRepetit;
import cat.urv.deim.io.FileLoader;
import cat.urv.deim.models.*;
import cat.urv.deim.Main;

public class MyTest {

    HashMapIndirecte<Integer, Pelicula> pelicules;
    HashMapIndirecte<Integer, Usuari> usuaris;
    NodeUnio<Pelicula> nodePelicula;
    NodeUnio<Usuari> nodeUsuari;
    MultiLlista multillista;

    
    @BeforeEach
    void setup() throws ElementNoTrobat, ElementRepetit {
        pelicules = new HashMapIndirecte<Integer, Pelicula>(5);
        usuaris = new HashMapIndirecte<Integer, Usuari>(5);
        nodeUsuari = new NodeUnio<Usuari>(null, 0, null);
        nodePelicula = new NodeUnio<Pelicula>(null, 0, null);
        multillista = new MultiLlista();
        FileLoader.carregarPelicules(pelicules);
        FileLoader.carregarUsuaris(usuaris, pelicules, nodeUsuari, nodePelicula, multillista);
    }

    @Test
    void test1() throws ElementNoTrobat {
        Usuari usuari = usuaris.element(2530404);
        Pelicula[] recomanacions = recomanar(usuari, usuaris, pelicules, multillista);
        assertTrue(recomanacions[0].getTitol().equals("Baseball"));
        assertTrue(recomanacions[1].getTitol().equals("Vietnam: A Television History"));
        assertTrue(recomanacions[2].getTitol().equals("Butch Cassidy and the Sundance Kid"));
    }

    @Test
    void test2() throws ElementNoTrobat {
        Usuari usuari = usuaris.element(2165002);
        Pelicula[] recomanacions = recomanar(usuari, usuaris, pelicules, multillista);
        assertTrue(recomanacions[0].getTitol().equals("Black Adder IV"));
        assertTrue(recomanacions[1].getTitol().equals("Shrek 2"));
        assertTrue(recomanacions[2].getTitol().equals("Something's Gotta Give"));
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
