package cat.urv.deim.models;

import java.util.ArrayList;
import java.util.List;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.ElementRepetit;

public class MultiLlista {
    
    private HashMapIndirecte<Usuari, NodeUnio<Pelicula>> taulaA;
    private HashMapIndirecte<Pelicula, NodeUnio<Usuari>> taulaB;
    private int num_relacions=0;

    public MultiLlista(){
        taulaA = new HashMapIndirecte<Usuari, NodeUnio<Pelicula>>(1000000);
        taulaB = new HashMapIndirecte<Pelicula, NodeUnio<Usuari>>(1000000);
    }

    public void inserir(Usuari a, Pelicula b, int qualificacio, String data) throws ElementRepetit {
        
        NodeUnio<Usuari> nodeUsuari = new NodeUnio<>(a, qualificacio, data);
        NodeUnio<Pelicula> nodePelicula = new NodeUnio<>(b, qualificacio, data);

        if(!existeix(a, b)){
            if (taulaA.buscar(a)) {
                try {
                    NodeUnio<Pelicula> node_aux = taulaA.element(a);
                    while (node_aux.seguent != null) {
                        node_aux = node_aux.seguent;
                    }
                    node_aux.seguent = nodePelicula;
                } catch (ElementNoTrobat e) {
                    e.printStackTrace();
                }
            } 
            else {
                taulaA.inserir(a, nodePelicula);
                try {
                    if(taulaA.element(a).getValor().getID()==2530404) {
                        System.out.println("Inserit a la taula A " + taulaA.element(a).getValor().getID());
                    }
                } catch (ElementNoTrobat e) {
                    e.printStackTrace();
                }
                
            }
            
            
            if (taulaB.buscar(b)) {
                try {
                    NodeUnio<Usuari> node_aux = taulaB.element(b);
                    while (node_aux.seguent != null) {
                        node_aux= node_aux.seguent;
                    }
                    node_aux.seguent = nodeUsuari;
                } catch (ElementNoTrobat e) {
                    e.printStackTrace();
                }
            } 
            else {
                taulaB.inserir(b, nodeUsuari);
                try{
                    if(taulaA.element(a).getValor().getID()==2530404) {
                        System.out.println("Inserit a la taula B " + taulaA.element(a).getValor().getID() + taulaB.element(b).getValor().getID());
                    }
                } catch (ElementNoTrobat e) {
                    e.printStackTrace();
                }
            }
            nodePelicula.setQualificacio(qualificacio);
            num_relacions++;
        }
        else {
            throw new ElementRepetit("Element repetit");
        }
    }

    public void esborrar(Usuari a, Pelicula b) throws ElementNoTrobat {
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

    public List<Pelicula> fila(Usuari a) throws ElementNoTrobat {
        List<Pelicula> llista = new ArrayList<Pelicula>();
        if(taulaA.buscar(a)) {
            try {
                NodeUnio<Pelicula> node_aux = taulaA.element(a);
                while (node_aux != null) {
                    llista.add(node_aux.getValor());
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

    public List<Usuari> columna(Pelicula b) throws ElementNoTrobat {
        List<Usuari> llista = new ArrayList<Usuari>();
        if(taulaB.buscar(b)) {
            try {
                NodeUnio<Usuari> node_aux = taulaB.element(b);
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

    public boolean existeix(Usuari a, Pelicula b) {
        if (!taulaA.buscar(a) || !taulaB.buscar(b)) {
            return false;
        }
        return true;
    }

    public int numRelacions() {
        return num_relacions;
    }

    public Pelicula getPeliVista(Usuari usuari) {
        Pelicula peli = new Pelicula(0, null, 0);
        if (taulaA.buscar(usuari)) {
            try {
                NodeUnio<Pelicula> nodeAux = taulaA.element(usuari);
                if (nodeAux != null) {
                    peli = nodeAux.getValor();
                }
            } catch (ElementNoTrobat e) {
                e.printStackTrace();
            }
        }
        return peli;
    }

    public int getNota(Usuari usuari, Pelicula peli) {
        if (taulaA.buscar(usuari)) {
            try {
                NodeUnio<Pelicula> nodeAux = taulaA.element(usuari);
                if (nodeAux != null) {
                    if (nodeAux.getValor() == peli) {
                        return nodeAux.getQualificacio();
                    }
                }
            } catch (ElementNoTrobat e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public Usuari trobarUsuariNou(int nota_peli, Usuari user, HashMapIndirecte<Integer, Usuari> usuaris, HashMapIndirecte<Integer, Pelicula> pelicules, Pelicula peli, MultiLlista multillista) throws ElementNoTrobat {
        Usuari usuari=null;
        MultiLlista multi = multillista;
        NodeUnio<Usuari> nodeAux = multi.taulaB.element(peli);
        NodeUnio<Pelicula> nodeAux2 = multi.taulaA.element(user);
        boolean trobat = false;
        while (nodeAux2 != null && !trobat) {
            if (nodeAux.getValor() != user && nodeAux2.getQualificacio() == nota_peli) {
                int nota = multi.getNota(nodeAux.getValor(), peli);
                if (nota >= 4) {
                    usuari = nodeAux.getValor();
                    trobat = true;
                }
            }
            nodeAux = nodeAux.seguent;
        }
        return usuari;
    }

    public Pelicula[] trobarPelicules(Usuari user, Pelicula vista, MultiLlista multi) throws ElementNoTrobat {
        Pelicula[] pelis = new Pelicula[3];
        MultiLlista multi2 = multi;
        NodeUnio<Pelicula> nodeAux = multi2.taulaA.element(user);
        NodeUnio<Usuari> nodeAux2 = multi2.taulaB.element(vista);
        int i = 0;
        while (nodeAux2 != null && i < 3) {
            if (nodeAux2.getValor() != user && nodeAux.getQualificacio() >= 4) {
                pelis[i] = nodeAux.getValor();
                i++;
            }
            nodeAux2 = nodeAux2.seguent;
        }
        return pelis;
    }
}
