package cat.urv.deim.models;
import java.util.List;
import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.ElementRepetit;

public interface IMultiLlistaGenerica<A, B> {
	
	/**
	 * Afegeix la relació d'elements
	 * @param A - element a de la relació
	 * @param B - element b de la relació
	 * @throws ElementRepetit - la relació ja existeix
	 * @throws ElementNoTrobat
	 */
	public void inserir(A a, B b) throws ElementRepetit, ElementNoTrobat;

    /**
	 * Esborrar la relació d'elements
	 * @param A - element a de la relació
	 * @param B - element b de la relació
	 * @throws ElementNoTrobat - algun dels elements no s'ha trobat
	 */
	public void esborrar(A a, B b) throws ElementNoTrobat;
	
	/**
	 * Retorna la llista de relacións amb l'element a
	 * @param A - element a de la relació
	 * @return List<B> - llista dels elements de B relacionats amb a
	 * @throws ElementNoTrobat - Element A no trobat
	 */
	public List<B> fila(A a) throws ElementNoTrobat;
	
	/**
	 * Retorna la llista de relacións amb l'element b
	 * @param B - element b de la relació
	 * @return List<A> - llista dels elements de A relacionats amb B
	 * @throws ElementNoTrobat - Element B no trobat
	 */
	public List<A> columna(B b) throws ElementNoTrobat;
	
	/**
	 * Retorna si existeix relació entre l'element a i l'element b
	 * @param a - element a de la relacio
	 * @param b - element b de la relacio
	 * @return boolean - existencia de la relació
	 */
	public boolean existeix(A a, B b);
}