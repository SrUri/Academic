package cat.urv.deim;

import org.junit.jupiter.api.Test;

import cat.urv.deim.exceptions.ElementNoTrobat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

public class MainTest {

    @Test
    public void testAnys1() throws IOException {
        var main = new Main();
        main.carregarFitxer("movies.txt");
        assertEquals(490, main.comptarPeliculesAny(1995));
    }

    @Test
    public void testAnys2() throws IOException {
        var main = new Main();
        main.carregarFitxer("movies.txt");
        assertEquals(459, main.comptarPeliculesAny(1994));
    }

    @Test
    public void testBuscar1() {
        var main = new Main();
        assertThrows(ElementNoTrobat.class, () -> {
            main.buscarPelicula("LOTR");
        });
    }

    @Test
    public void testBuida() {
        var main = new Main();
        assertTrue(main.esBuida());
    }

    @Test
    public void testNoBuida() throws IOException {
        var main = new Main();
        main.carregarFitxer("movies.txt");
        assertFalse(main.esBuida());
    }

    @Test
    public void testPelicula1() throws ElementNoTrobat, IOException {
        var main = new Main();
        main.carregarFitxer("movies.txt");
        assertEquals(1994, main.buscarAnyPelicula("Speed"));
    }    

    @Test
    public void testPelicula2() throws ElementNoTrobat, IOException {
        var main = new Main();
        main.carregarFitxer("movies.txt");
        assertEquals(1997, main.buscarAnyPelicula("Con Air"));
    }

    @Test
    public void testPelicula3() throws IOException {
        var main = new Main();
        main.carregarFitxer("movies.txt");
        assertThrows(ElementNoTrobat.class, () -> {
            main.buscarAnyPelicula("Avatar 3");
        });
    }    

    @Test
    public void testEsborrar1() throws IOException {
        var main = new Main();
        main.carregarFitxer("movies.txt");
        assertThrows(ElementNoTrobat.class, () -> {
            var avatar = new Pelicula(31415926, "Avatar 3", 2025);
            main.esborraPelicula(avatar);
        });
    }

    @Test
    public void testEsborrar2() throws ElementNoTrobat, IOException {
        var main = new Main();
        main.carregarFitxer("movies.txt");
        var pelicula = main.buscarPelicula("Con Air");
        main.esborraPelicula(pelicula);
        assertThrows(ElementNoTrobat.class, () -> {
            main.buscarAnyPelicula("Con Air");
        });
    }

    @Test
    public void testEsborrar3() throws ElementNoTrobat, IOException {
        var main = new Main();
        main.carregarFitxer("movies.txt");
        var pelicula = main.buscarPelicula("Speed");
        main.esborraPelicula(pelicula);
        assertEquals(458, main.comptarPeliculesAny(1994));
    }
}
