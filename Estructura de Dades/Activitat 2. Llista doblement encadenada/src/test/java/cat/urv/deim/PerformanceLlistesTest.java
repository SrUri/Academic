package cat.urv.deim;

import cat.urv.deim.io.FileLoader;
import cat.urv.deim.models.ILlistaPelicules;
import cat.urv.deim.models.LlistaPelicules;
import cat.urv.deim.models.LlistaPeliculesOrdenada;
import cat.urv.deim.models.Pelicula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.regex.PatternSyntaxException;

public class PerformanceLlistesTest {

    ILlistaPelicules pelicules;
    ILlistaPelicules peliculesOrdenades;

    final int PERFORMANCE_LOOP_TIMES = 10000;

    @BeforeEach
    void setup() throws PatternSyntaxException, NumberFormatException, IOException {
        pelicules = new LlistaPelicules();
        FileLoader.carregarFitxer("movies.txt", pelicules);

        peliculesOrdenades = new LlistaPeliculesOrdenada();
        FileLoader.carregarFitxer("movies.txt", peliculesOrdenades);
    }

    @Test
    public void laPerformanceDeLaCercaOrdenadaHauriaDeSerMillor() {
        Pelicula pelicula = new Pelicula(17770, "Alien Hunter", 2003);
        long inici = System.currentTimeMillis();
        for (int i = 0; i < PERFORMANCE_LOOP_TIMES; i++) {
            pelicules.buscar(pelicula);
        }
        long fi = System.currentTimeMillis();
        long tempsNoOrdenada = fi - inici;

        inici = System.currentTimeMillis();
        for (int i = 0; i < PERFORMANCE_LOOP_TIMES; i++) {
            peliculesOrdenades.buscar(pelicula);
        }
        fi = System.currentTimeMillis();
        long tempsOrdenada = fi - inici;

        System.out.println("laPerformanceDeLaOrdenadaHauriaDeSerMillor|Ordenada: " + tempsOrdenada);
        System.out.println("laPerformanceDeLaOrdenadaHauriaDeSerMillor|No Ordenada: " + tempsNoOrdenada);
        assertTrue(tempsOrdenada < tempsNoOrdenada);
    }

    @Test
    public void laPerformanceDeLaCercaOrdenadaHauriaDeSerMillor2() {
        Pelicula pelicula = new Pelicula(222222222, "Assassins 2", 1997);
        pelicules.inserir(pelicula);
        peliculesOrdenades.inserir(pelicula);

        long inici = System.currentTimeMillis();
        for (int i = 0; i < PERFORMANCE_LOOP_TIMES; i++) {
            pelicules.buscar(pelicula);
        }
        long fi = System.currentTimeMillis();
        long tempsNoOrdenada = fi - inici;

        long iniciOrdenada = System.currentTimeMillis();
        for (int i = 0; i < PERFORMANCE_LOOP_TIMES; i++) {
            peliculesOrdenades.buscar(pelicula);
        }
        long fiOrdenada = System.currentTimeMillis();
        long tempsOrdenada = fiOrdenada - iniciOrdenada;

        System.out.println("laPerformanceDeLaOrdenadaHauriaDeSerMillor2|Ordenada: " + tempsOrdenada);
        System.out.println("laPerformanceDeLaOrdenadaHauriaDeSerMillor2|No Ordenada: " + tempsNoOrdenada);

        assertTrue(tempsOrdenada < tempsNoOrdenada);
    }

    @Test
    public void laPerformanceDeLaInsercioOrdenadaHauriaDeSerPitjor() {
        long inici = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Pelicula pelicula = new Pelicula(30000 + i, "ZZ Top " + i, 1995);
            pelicules.inserir(pelicula);
        }
        long fi = System.currentTimeMillis();
        long tempsNoOrdenada = fi - inici;

        inici = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Pelicula pelicula = new Pelicula(30000 + i, "ZZ Top " + i, 1995);
            peliculesOrdenades.inserir(pelicula);
        }
        fi = System.currentTimeMillis();
        long tempsOrdenada = fi - inici;

        System.out.println("laPerformanceDeLaOrdenadaHauriaDeSerPitjor|Ordenada: " + tempsOrdenada);
        System.out.println("laPerformanceDeLaOrdenadaHauriaDeSerPitjor|No Ordenada: " + tempsNoOrdenada);

        assertTrue(tempsOrdenada > tempsNoOrdenada);
    }
}