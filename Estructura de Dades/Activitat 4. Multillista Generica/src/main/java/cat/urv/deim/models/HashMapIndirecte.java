package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class HashMapIndirecte<K,V> implements IHashMap<K,V> {

    private int mida;
    private int numElements;
    private NodeHash<K,V>[] taula;

    public HashMapIndirecte(int mida) {
        this.mida = mida;
        this.numElements = 0;
        this.taula = new NodeHash[mida];
    }

    public void inserir(K clau, V valor) {
        if (factorCarrega() > 0.75) {
            redimensionar();
        }
        int index = funcioHash(clau);
        NodeHash<K,V> node = taula[index];
        while (node != null) {
            if (node.getClau().equals(clau)) {
                node.setElement(valor);
                return;
            }
            node = node.getSeguent();
        }
        node = new NodeHash<>(clau, valor);
        node.setSeguent(taula[index]);
        taula[index] = node;
        numElements++;
    }

    public void esborrar(K clau) throws ElementNoTrobat {
        int index = funcioHash(clau);
        NodeHash<K, V> node = taula[index];
        NodeHash<K, V> antNode = null;
        while (node != null) {
            if (node.getClau().equals(clau)) {
                if (antNode == null) {
                    taula[index] = node.getSeguent();
                } else {
                    antNode.setSeguent(node.getSeguent());
                }
                numElements--;
                return;
            }
            antNode = node;
            node = node.getSeguent();
        }
        throw new ElementNoTrobat("No s'ha trobat l'element a esborrar");
    }

    public boolean buscar(K clau) {
        int index = funcioHash(clau);
        NodeHash<K, V> node = taula[index];

        while (node != null) {
            if (node.getClau().equals(clau)) {
                return true;
            }
            node = node.getSeguent();
        }
        return false;
    }

    public boolean esBuida() {
        return numElements == 0;
    }

    public int longitud() {
        return numElements;
    }

    public Iterator<V> iterator() {
        return new IteradorLlista<V>(null) {
            int i = 0;
            int j = 0;
            NodeHash<K,V> aux = null;

            public boolean hasNext() {
                return i < numElements;
            }

            public V next() {
                if (aux == null) {
                    while (taula[j] == null) {
                        j++;
                    }
                    aux = taula[j];
                }
                V value = aux.getElement();
                aux = aux.getSeguent();
                if (aux == null) {
                    j++;
                }
                i++;
                return value;
            }
        };
    }

    public Object[] obtenirClaus() {
        K[] claus = (K[]) new Object[numElements];
        int index = 0;
        for (int i = 0; i < mida; i++) {
            NodeHash<K, V> node = taula[i];
            while (node != null) {
                claus[index] = node.getClau();
                index++;
                node = node.getSeguent();
            }
        }
        return claus;
    }

    public V element(K clau) throws ElementNoTrobat {
        int index = funcioHash(clau);
        NodeHash<K, V> node = taula[index];
        while (node != null) {
            if (node.getClau().equals(clau)) {
                return node.getElement();
            }
            node = node.getSeguent();
        }
        throw new ElementNoTrobat("No s'ha trobat l'element");
    }

    public float factorCarrega() {
        return (float) numElements / mida;
    }

    public int midaTaula() {
        return mida;
    }

    private int funcioHash(K clau) {
        return Math.abs(clau.hashCode() % mida);
    }
    
    private void redimensionar() {
        int novaMida = mida * 2;
        NodeHash<K, V>[] novaTaula = new NodeHash[novaMida];
        for (int i = 0; i < mida; i++) {
            NodeHash<K, V> node = taula[i];
            while (node != null) {
                int index = Math.abs(node.getClau().hashCode() % novaMida);
                NodeHash<K, V> seguent = node.getSeguent();
                node.setSeguent(novaTaula[index]);
                novaTaula[index] = node;
                node = seguent;
            }
        }
        mida = novaMida;
        taula = novaTaula;
    }
}