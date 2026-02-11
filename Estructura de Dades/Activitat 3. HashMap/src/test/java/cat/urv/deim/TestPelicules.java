package cat.urv.deim;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.io.FileLoader;
import cat.urv.deim.models.IPelicules;
import cat.urv.deim.models.HashMapPelicules;
import cat.urv.deim.models.LlistaPelicules;

import static org.junit.jupiter.api.Assertions.*;

public class TestPelicules {

    IPelicules hashMap;
    IPelicules llista;

    @BeforeEach
    void setup() {
        hashMap = new HashMapPelicules();
        llista = new LlistaPelicules();
        FileLoader.carregarFitxerPelicules("movies.txt", hashMap);
        FileLoader.carregarFitxerPelicules("movies.txt", llista);
    }

    @Test
    public void testCarregarPelicules() {
        //No hi ha titols repetits al hash
        assertEquals(17351, hashMap.longitud());
        //Si que hi ha titols repetits a la llista en pel.licules diferents
        assertEquals(17762, llista.longitud());
    }

    @Test
    public void testRendimentPeliculesTitol() throws ElementNoTrobat {
        String[] titols = new String[hashMap.longitud()];
        var index = 0;
        for (var pelicula: hashMap.llistaPelicules()) {
            titols[index] = pelicula.getTitol();
            index += 1;
        }
        final int PERFORMANCE_LOOP_TIMES = 3;

        long hashMapInici = System.currentTimeMillis();
        for (int i = 0; i < PERFORMANCE_LOOP_TIMES; i++) {
            for (var titol: titols) {
                var any = hashMap.buscarAnyPelicula(titol);
            }
        }
        long hashMapFi = System.currentTimeMillis();
        long tempsHashMap = hashMapFi - hashMapInici; 

        long llistaInici = System.currentTimeMillis();
        for (int i = 0; i < PERFORMANCE_LOOP_TIMES; i++) {
            for (var titol: titols) {
                var any = llista.buscarAnyPelicula(titol);
            }
        }
        long llistaFi = System.currentTimeMillis();
        long tempsLlista = llistaFi - llistaInici; 

        System.out.println("laPerformanceDelHashmapHauriaDeSerMillor|Hashmap: " + tempsHashMap);
        System.out.println("laPerformanceDelHashmapHauriaDeSerMillor|Llista: " + tempsLlista);
        assertTrue(tempsHashMap < tempsLlista);
    }

    @Test
    public void testRendimentPeliculesAny() throws ElementNoTrobat {
        final int PERFORMANCE_LOOP_TIMES = 100;

        long hashMapInici = System.currentTimeMillis();
        for (int i = 0; i < PERFORMANCE_LOOP_TIMES; i++) {
            for (var any = 1980; any < 2000; any++) {
                var c = hashMap.comptarPeliculesAny(any);
            }
        }
        long hashMapFi = System.currentTimeMillis();
        long tempsHashMap = hashMapFi - hashMapInici; 

        long llistaInici = System.currentTimeMillis();
        for (int i = 0; i < PERFORMANCE_LOOP_TIMES; i++) {
            for (var any = 1980; any < 2000; any++) {
                var c = llista.comptarPeliculesAny(any);
            }
        }
        long llistaFi = System.currentTimeMillis();
        long tempsLlista = llistaFi - llistaInici; 

        System.out.println("laPerformanceDelHashmapHauriaDeSerPitjor|Hashmap: " + tempsHashMap);
        System.out.println("laPerformanceDelHashmapHauriaDeSerPitjor|Llista: " + tempsLlista);
        assertTrue(tempsHashMap > tempsLlista);
    }
}