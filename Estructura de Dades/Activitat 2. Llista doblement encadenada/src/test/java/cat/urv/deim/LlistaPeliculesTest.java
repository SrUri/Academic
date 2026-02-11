package cat.urv.deim;

import cat.urv.deim.io.FileLoader;
import cat.urv.deim.models.ILlistaPelicules;
import cat.urv.deim.models.LlistaPelicules;
import cat.urv.deim.models.Pelicula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cat.urv.deim.exceptions.ElementNoTrobat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.regex.PatternSyntaxException;

public class LlistaPeliculesTest {

    ILlistaPelicules pelicules;

    @BeforeEach
    void setup() {
        pelicules = new LlistaPelicules();
        FileLoader.carregarFitxer("movies.txt", pelicules);
    }

    @Test
    public void insertarPelicula() {
        Pelicula pelicula = new Pelicula(2000000, "ZZZZZZ", 1995);
        int longitudInicial = pelicules.longitud();
        pelicules.inserir(pelicula);
        int longitudNova = pelicules.longitud();
        assertEquals(longitudInicial + 1, longitudNova);

        Pelicula[] peliculesArray = pelicules.elements();
        assertEquals(pelicula, peliculesArray[longitudNova-1]);
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
    public void buscarPeliculaInexistent() {
        assertThrows(ElementNoTrobat.class, () -> {
            pelicules.buscarPelicula("Pel·lícula que no existeix");
        });
    }

    @Test
    public void comprobarLlistaBuida() {
        LlistaPelicules llistaBuida = new LlistaPelicules();
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
        pelicules.esborrar(pelicula);
        assertThrows(ElementNoTrobat.class, () -> {
            pelicules.buscarAnyPelicula("Speed");
        });
        assertEquals(458, pelicules.comptarPeliculesAny(1994));
    }
}