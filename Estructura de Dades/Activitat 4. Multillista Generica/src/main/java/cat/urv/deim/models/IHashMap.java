package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public interface IHashMap<K, V> {
    // Metode per insertar un element a la taula. Si existeix un element amb aquesta clau s'actualitza el valor
    public void inserir(K key, V value);

    // Metode per a esborrar un element de la taula de hash
    public void esborrar(K key) throws ElementNoTrobat;

    // Metode per a comprovar si un element esta a la taula de hash
    public boolean buscar(K key);

    // Metode per a comprovar si la taula te elements
    public boolean esBuida();

    // Metode per a obtenir el nombre d'elements de la taula
    public int longitud();

    //Metode per a poder iterar pels elements de la taula
    public Iterator<V> iterator();

    // Metode per a obtenir les claus de la taula
    public Object[] obtenirClaus();

    // Metode per a obtenir un array amb tots els elements de K
    public V element(K key) throws ElementNoTrobat;

    // Metode per a saber el factor de carrega actual de la taula
    public float factorCarrega();   

    // Metode per a saber la mida actual de la taula
    public int midaTaula();   
}