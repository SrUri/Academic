package cat.urv.deim;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.models.HashMapIndirecte;

import static org.junit.jupiter.api.Assertions.*;

public class TestHash {

    @Test
    public void testInsercioAmbElement() throws ElementNoTrobat {
        var hashMap = new HashMapIndirecte<Integer, String>(500);
        hashMap.inserir(1, "TEST");
        assertEquals("TEST", hashMap.element(1));
    }

    @Test
    public void testInsercioAmbLongitud() {
        var hashMap = new HashMapIndirecte<Integer, String>(500);
        for (var i = 0; i < 100; i++) {
            hashMap.inserir(i, Integer.toString(i));
        }
        assertEquals(true, hashMap.longitud() == 100);
    }

    @Test
    public void testInsercioAmbLongitud2() throws ElementNoTrobat {
        var hashMap = new HashMapIndirecte<Integer, String>(500);
        for (var i = 0; i < 100; i++) {
            hashMap.inserir(i, Integer.toString(i));
            hashMap.inserir(i, Integer.toString(i) + "BIS");
            assertEquals(hashMap.element(i), Integer.toString(i) + "BIS");
        }
        assertEquals(true, hashMap.longitud() == 100);
    }

    @Test
    public void testEsBuidaAmbInserir() {
        var hashMap = new HashMapIndirecte<Integer, String>(500);
        assertEquals(true, hashMap.esBuida());
        hashMap.inserir(1, "TEST");
        assertEquals(false, hashMap.esBuida());
    }

    @Test
    public void testEsBuidaAmbEsborrar() throws ElementNoTrobat {
        var hashMap = new HashMapIndirecte<Integer, String>(500);
        hashMap.inserir(1, "TEST");
        assertEquals(false, hashMap.esBuida());
        hashMap.esborrar(1);
        assertEquals(true, hashMap.esBuida());
    }

    @Test
    public void testCerca() throws ElementNoTrobat {
        var hashMap = new HashMapIndirecte<Integer, String>(500);
        for (var i = 0; i < 100; i++) {
            hashMap.inserir(i, Integer.toString(i));
        }
        Random r = new Random();
        assertEquals(true, hashMap.buscar(r.nextInt(99)));
        assertEquals(false, hashMap.buscar(r.nextInt(99)+100));
    }

    @Test
    public void testObtenirClaus() {
        var hashMap = new HashMapIndirecte<Integer, String>(500);
        for (var i = 0; i < 100; i++) {
            hashMap.inserir(i, Integer.toString(i));
        }
        
        var set = new HashSet<Integer>();
        Object[] claus = hashMap.obtenirClaus();
        assertEquals(100, claus.length);
        for (var i = 0; i < 100; i++) {
            var clau = (Integer)claus[i];
            assertEquals(true, clau >= 0 && clau < 100);
            set.add(clau);
        }
        assertEquals(100, set.size());
    }

    @Test
    public void testIterator() {
        var hashMap = new HashMapIndirecte<Integer, String>(500);
        for (var i = 0; i < 100; i++) {
            hashMap.inserir(i, Integer.toString(i));
        }
        
        var set = new HashSet<Integer>();
        var iterator = hashMap.iterator();
        while (iterator.hasNext()) {
            var valor = iterator.next();
            var clau = Integer.parseInt(valor);
            assertEquals(true, clau >= 0 && clau < 100);
            set.add(clau);
        }
        assertEquals(100, set.size());
    }

    @Test
    public void testFactorCarrega() {
        var hashMap = new HashMapIndirecte<Integer, String>(500);
        for (var i = 0; i < 100; i++) {
            hashMap.inserir(i, Integer.toString(i));
        }
        assertEquals(100.0f/500.0f, hashMap.factorCarrega());
    }

    @Test
    public void testFactorCarregaResize() {
        var hashMap = new HashMapIndirecte<Integer, String>(50);
        for (var i = 0; i < 100; i++) {
            hashMap.inserir(i, Integer.toString(i));
        }
        assertEquals(true, hashMap.factorCarrega() < 0.75f);
    }


    @Test
    public void testResizeInsert() throws ElementNoTrobat {
        var hashMap = new HashMapIndirecte<Integer, String>(50);
        for (var i = 0; i < 2000; i++) {
            hashMap.inserir(i, Integer.toString(i));
        }

        hashMap.inserir(10, "TEST");
        hashMap.inserir(1500, "TEST");

        assertEquals("TEST", hashMap.element(10));
        assertEquals("20", hashMap.element(20));
        assertEquals("30", hashMap.element(30));
        assertEquals("TEST", hashMap.element(1500));
    }
    
}