package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.models.LlistaPelicules;
import cat.urv.deim.models.Pelicula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PeliculaTest {

    @Test
    public void compararDuesPeliculesIguals() {
        Pelicula pelicula1 = new Pelicula(1, "Pelicula 1", 1994);
        Pelicula pelicula2 = new Pelicula(1, "Pelicula 1", 1994);
        assertTrue(pelicula1.compareTo(pelicula2) == 0);
    }

    @Test
    public void compararDuesPeliculesDiferents() {
        Pelicula pelicula1 = new Pelicula(1, "Pelicula 1", 1994);
        Pelicula pelicula2 = new Pelicula( 2, "Pelicula 1", 1994);
        assertTrue(pelicula1.compareTo(pelicula2) != 0);
    }

    @Test
    public void compararAmbUnaPeliculaMajor() {
        Pelicula pelicula1 = new Pelicula(1, "Pelicula 1", 1994);
        Pelicula pelicula2 = new Pelicula(2, "Pelicula 2", 1994);
        assertTrue(pelicula1.compareTo(pelicula2) < 0);
    }

    @Test
    public void compararAmbUnaPeliculaMenor() {
        Pelicula pelicula1 = new Pelicula(1, "Pelicula 1", 1994);
        Pelicula pelicula2 = new Pelicula(2, "Pelicula 0", 1994);
        assertTrue(pelicula1.compareTo(pelicula2) > 0);
    }

    @Test
    public void compararAmbUnaPeliculaAmbElMateixTitolPeroAnyMajor() {
        Pelicula pelicula1 = new Pelicula(1, "Pelicula 1", 1994);
        Pelicula pelicula2 = new Pelicula(2, "Pelicula 1", 1995);
        assertTrue(pelicula1.compareTo(pelicula2) < 0);
    }

    @Test
    public void compararAmbUnaPeliculaAmbElMateixTitolPeroAnyMenor() {
        Pelicula pelicula1 = new Pelicula(1, "Pelicula 1", 1995);
        Pelicula pelicula2 = new Pelicula(2, "Pelicula 1", 1994);
        assertTrue(pelicula1.compareTo(pelicula2) > 0);
    }

    @Test
    public void compararAmbUnaPeliculaAmbElMateixTitolIAnyPeroIdMajor() {
        Pelicula pelicula1 = new Pelicula(1, "Pelicula 1", 1994);
        Pelicula pelicula2 = new Pelicula(2, "Pelicula 1", 1994);
        assertTrue(pelicula1.compareTo(pelicula2) < 0);
    }

    @Test
    public void compararAmbUnaPeliculaAmbElMateixTitolIAnyPeroIdMenor() {
        Pelicula pelicula1 = new Pelicula(2, "Pelicula 1", 1994);
        Pelicula pelicula2 = new Pelicula(1, "Pelicula 1", 1994);
        assertTrue(pelicula1.compareTo(pelicula2) > 0);
    }
}
