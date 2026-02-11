package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.io.FileLoader;
import cat.urv.deim.models.ILlistaPelicules;
import cat.urv.deim.models.LlistaPeliculesOrdenada;
import cat.urv.deim.models.Pelicula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.PatternSyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class LlistaPeliculesOrdenadesTest {

    ILlistaPelicules pelicules;

    @BeforeEach
    void setup() {
        pelicules = new LlistaPeliculesOrdenada();
        FileLoader.carregarFitxer("movies.txt", pelicules);
    }

    @Test
    public void comptarPeliculesDe1995() {
        assertEquals(490, pelicules.comptarPeliculesAny(1995));
    }

    @Test
    public void comptarPeliculesDe1994() {
        assertEquals(459, pelicules.comptarPeliculesAny(1994));
    }

    @Test
    public void estaOrdenat() {
        Arrays.stream(pelicules.elements()).reduce((peliculaAnterior, pelicula) -> {
            assertTrue(peliculaAnterior.compareTo(pelicula) <= 0);
            return pelicula;
        });
    }

    @Test
    public void buscarPeliculaInexistent() {
        assertThrows(ElementNoTrobat.class, () -> {
            pelicules.buscarPelicula("Pel·lícula que no existeix");
        });
    }

    @Test
    public void insertarPelicula() {
        Pelicula pelicula = new Pelicula(2000000, "zzzzzzzzz", 1995);
        int longitudInicial = pelicules.longitud();
        pelicules.inserir(pelicula);
        int longitudNova = pelicules.longitud();
        assertEquals(longitudInicial + 1, longitudNova);

        Pelicula[] peliculesArray = pelicules.elements();
        assertEquals(pelicula, peliculesArray[longitudNova-1]);
    }

    @Test
    public void insertarPeliculaEnPrimeraPosicio() {
        Pelicula pelicula = new Pelicula(2000000, "'Aaaaa", 1995);
        int longitudInicial = pelicules.longitud();
        pelicules.inserir(pelicula);
        int longitudNova = pelicules.longitud();
        assertEquals(longitudInicial + 1, longitudNova);

        Pelicula[] peliculesArray = pelicules.elements();
        assertEquals(pelicula, peliculesArray[0]);
    }

    @Test
    public void insertarPeliculaPelMig() {
        Pelicula pelicula = new Pelicula(2000000, "AAAAAAA", 1995);
        int longitud = pelicules.longitud();
        pelicules.inserir(pelicula);
        Pelicula[] peliculesArray = pelicules.elements();
        assertEquals(pelicula, peliculesArray[450]);
        assertEquals(longitud+1, pelicules.longitud());
    }

    @Test
    public void comprobarLlistaBuida() {
        LlistaPeliculesOrdenada llistaBuida = new LlistaPeliculesOrdenada();
        assertTrue(llistaBuida.esBuida());
    }

    @Test
    public void comprobarLlistaNoBuida() {
        assertFalse(pelicules.esBuida());
    }

    @Test
    public void obtenirAnyDeLaPelicula() throws ElementNoTrobat {
        assertEquals(1994, pelicules.buscarAnyPelicula("Speed"));
    }

    @Test
    public void obtenirAnyDeLaPelicula2() throws ElementNoTrobat {
        assertEquals(1997, pelicules.buscarAnyPelicula("Con Air"));
    }

    @Test
    public void esborrarPeliculaInexistent() {
        assertThrows(ElementNoTrobat.class, () -> {
            Pelicula avatar = new Pelicula(31415926, "Avatar 3", 2025);
            pelicules.esborrar(avatar);
        });
    }

    @Test
    public void esborrarPelicula() throws ElementNoTrobat {
        Pelicula pelicula = pelicules.buscarPelicula("Speed");
        int longitud = pelicules.longitud();
        pelicules.esborrar(pelicula);
        assertThrows(ElementNoTrobat.class, () -> {
            pelicules.buscarAnyPelicula("Speed");
        });
        Pelicula[] peliculesArray = pelicules.elements();
        assertEquals(458, pelicules.comptarPeliculesAny(1994));
        assertEquals(longitud-1, pelicules.longitud());
        assertEquals(peliculesArray.length, pelicules.longitud());
    }
}