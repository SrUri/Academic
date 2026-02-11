package cat.urv.deim;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.ElementRepetit;
import cat.urv.deim.models.IMultiLlistaGenerica;
import cat.urv.deim.models.MultiLlista;

public class MultiLlistaTest {

    IMultiLlistaGenerica<Integer, Integer> multilist;

    void setupLlistaPlena() throws ElementRepetit, ElementNoTrobat {
        boolean path;
        int a,b;
        multilist = new MultiLlista<>();

        Random random = new Random();

        for(int i = 0; i<1000;i++){
            a = random.nextInt(10000);
            b = random.nextInt(10000);
            path = random.nextBoolean();
            if(path) {
                for(int j = 0; j<10;j++){
                    a = random.nextInt(10000);
                    if(!multilist.existeix(a, b)) multilist.inserir(a, b);
                }
            } else {
                for(int j = 0; j<10;j++){
                    a = random.nextInt(10000);
                    if(!multilist.existeix(a, b)) multilist.inserir(a, b);
                }
            }
        }
    }

    void setupLlistaBuida() {
        multilist = new MultiLlista<>();
    }

    @Test
    public void testAfegirRelacio() throws ElementRepetit, ElementNoTrobat {
        setupLlistaPlena();
        Random random = new Random();
        int a,b;

        do {
            a = random.nextInt(10000);
            b = random.nextInt(10000);
        } while(multilist.existeix(a, b));  
        
        assertFalse(multilist.existeix(a, b)); 
        multilist.inserir(a, b);
        assertTrue(multilist.existeix(a, b));
    }

    @Test
    public void testAfegirRelacioLlistaBuida() throws ElementRepetit, ElementNoTrobat {
        setupLlistaBuida();

        Random random = new Random();
        int a = random.nextInt(10000);
        int b = random.nextInt(10000);
        
        multilist.inserir(a, b);
        assertTrue(multilist.existeix(a, b));
    }

    @Test
    public void testAfegirRelacioRepetida() throws ElementRepetit, ElementNoTrobat {
        setupLlistaPlena();
        assertThrows(ElementRepetit.class, () -> {
            Random random = new Random();
            int a,b;

            do {
                a = random.nextInt(10000);
                b = random.nextInt(10000);
            } while(multilist.existeix(a, b));  
            
            assertFalse(multilist.existeix(a, b)); 
            multilist.inserir(a, b);
            multilist.inserir(a, b);
        });
    }

    @Test
    public void testEsborrarRelacio() throws ElementRepetit, ElementNoTrobat {
        setupLlistaPlena();
        int a,b;
        Random random = new Random();

        do {
            a = random.nextInt(10000);
            b = random.nextInt(10000);
        } while(!multilist.existeix(a, b));  

        assertTrue(multilist.existeix(a, b));
        multilist.esborrar(a, b);
        assertFalse(multilist.existeix(a, b));
    }

    @Test
    public void testEsborrarRelacioLlistaBuida() throws ElementRepetit, ElementNoTrobat {
        setupLlistaBuida();

        Random random = new Random();
        int a = random.nextInt(10000);
        int b = random.nextInt(10000);
        
        multilist.inserir(a, b);
        assertTrue(multilist.existeix(a, b));
        multilist.esborrar(a, b);
        assertFalse(multilist.existeix(a, b));
    }

    @Test
    public void testEsborrarRelacioInexistent() throws ElementNoTrobat, ElementRepetit {
        setupLlistaPlena();
        Random random = new Random();

        assertThrows(ElementNoTrobat.class, () -> {
            int a = random.nextInt(10000);
            int b = random.nextInt(10000);

            while(multilist.existeix(a, b)); {
                a = random.nextInt(10000);
                b = random.nextInt(10000);
            }
            
            multilist.esborrar(a, b);
        });
    }

    @Test
    public void testFila() throws ElementNoTrobat, ElementRepetit{
        setupLlistaBuida();

        List<Integer> expected = new ArrayList<Integer>();
        Random random = new Random();
        int a = random.nextInt(10000);
        int b;

        for(int i = 0; i<50;i++){
            b = random.nextInt(10000);
            if(!multilist.existeix(a, b)){
                multilist.inserir(a, b);
                expected.add(b);
            }
        }

        var alumne = multilist.fila(a);
        Collections.sort(expected);
        Collections.sort(alumne);
        assertTrue(alumne.equals(expected));
    }

    @Test
    public void testColumna() throws ElementNoTrobat, ElementRepetit {
        setupLlistaBuida();
        
        List<Integer> expected = new ArrayList<Integer>();
        Random random = new Random();
        int a;
        int b = random.nextInt(10000);

        for(int i = 0; i<50;i++){
            a = random.nextInt(10000);
            if(!multilist.existeix(a, b)){
                multilist.inserir(a, b);
                expected.add(a);
            }
        }

        var alumne = multilist.columna(b);
        Collections.sort(expected);
        Collections.sort(alumne);
        assertTrue(alumne.equals(expected));
    }
}
