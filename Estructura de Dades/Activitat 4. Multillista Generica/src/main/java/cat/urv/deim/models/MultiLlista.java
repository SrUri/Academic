package cat.urv.deim.models;

import java.util.ArrayList;
import java.util.List;
import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.ElementRepetit;

public class MultiLlista<A extends Comparable<A>, B extends Comparable<B>> implements IMultiLlistaGenerica<A,B> {
    
    private HashMapIndirecte<A, NodeUnio<B>> taulaA;
    private HashMapIndirecte<B, NodeUnio<A>> taulaB;

    public MultiLlista(){
        taulaA = new HashMapIndirecte<A, NodeUnio<B>>(33);
        taulaB = new HashMapIndirecte<B, NodeUnio<A>>(33);
    }

    @Override
    public void inserir(A a, B b) throws ElementRepetit {
        
        NodeUnio<A> nodeA = new NodeUnio<>(a);
        NodeUnio<B> nodeB = new NodeUnio<>(b);

        if(!existeix(a, b)){
            if (taulaA.buscar(a)) {
                try {
                    NodeUnio<B> node_aux = taulaA.element(a);
                    while (node_aux.seguent != null) {
                        node_aux= node_aux.seguent;
                    }
                    node_aux.seguent = nodeB;

                } catch (ElementNoTrobat e) {
                    e.printStackTrace();
                }
            } 
            else {
                taulaA.inserir(a, nodeB);
            }
            
            if (taulaB.buscar(b)) {
                try {
                    NodeUnio<A> node_aux = taulaB.element(b);
                    while (node_aux.seguent != null) {
                        node_aux= node_aux.seguent;
                    }
                    node_aux.seguent = nodeA;

                } catch (ElementNoTrobat e) {
                    e.printStackTrace();
                }
            } 
            else {
                taulaB.inserir(b, nodeA);
            }
        }
        else {
            throw new ElementRepetit("Element repetit");
        }
    }

    @Override
    public void esborrar(A a, B b) throws ElementNoTrobat {
        if(existeix(a, b)) {
            if(taulaA.buscar(a)) {
                taulaA.esborrar(a);
            }
            if(taulaB.buscar(b)) {
                taulaB.esborrar(b);
            }
        }
        else
        {
            throw new ElementNoTrobat("Element no trobat");
        }
    }

    @Override
    public List<B> fila(A a) throws ElementNoTrobat {
        List<B> llista = new ArrayList<B>();
        if(taulaA.buscar(a)) {
            try {
                NodeUnio<B> node_aux = taulaA.element(a);
                while (node_aux != null) {
                    llista.add(node_aux.valor);
                    node_aux = node_aux.seguent;
                }
            } catch (ElementNoTrobat e) {
                e.printStackTrace();
            }
        }
        else {
            throw new ElementNoTrobat("Element no trobat");
        }
        return llista;
    }

    @Override
    public List<A> columna(B b) throws ElementNoTrobat {
        List<A> llista = new ArrayList<A>();
        if(taulaB.buscar(b)) {
            try {
                NodeUnio<A> node_aux = taulaB.element(b);
                while (node_aux != null) {
                    llista.add(node_aux.valor);
                    node_aux = node_aux.seguent;
                }
            } catch (ElementNoTrobat e) {
                e.printStackTrace();
            }
        }
        else {
            throw new ElementNoTrobat("Element no trobat");
        }
        return llista;
    }

    @Override
    public boolean existeix(A a, B b) {
        if (!taulaA.buscar(a) || !taulaB.buscar(b)) {
            return false;
        }
        return true;
    }
}