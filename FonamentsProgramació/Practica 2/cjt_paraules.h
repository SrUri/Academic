/**
 * Fitxer de capcalera amb les operacions que han d'implementar en la practica TAD 
 */
#include <stdbool.h>
#define MAX_EL 100

typedef struct 
{
    int n_el;   // Nombre d'elements
    char *paraules[MAX_EL]; // Desem els elements en una taula de punters a caràcter
} cjt_paraules_t;


/**
 * @brief Inicialitza el conjunt per poder-lo utilitzar
 * 
 * @param c El conjunt a inicialitzar
 */
void inicialitzar(cjt_paraules_t *c);

/**
 * @brief Afegeix una paraula al conjunt. 
 * Si aquest està ple, s'indica un error
 * 
 * @param c (E/S) Conjunt al qual afegim la paraula
 * @param p (E) La paraula a afegir
 * @return true S'ha pogut afegir
 * @return false No s'ha pogut afegir
 */
bool afegir(cjt_paraules_t *c, char p[]);

/**
 * @brief Elimina una paraula del conjunt, si existeix
 * No informa de si s'ha eliminat o no.
 * 
 * @param c (E/S) Conjunt del qual eliminem l'enter
 * @param p (E) La paraula a eliminar
 */
void eliminar(cjt_paraules_t *c, char p[]);

/**
 * @brief Indica si una paraula pertany o no al conjunt
 * 
 * @param c (E) El conjunt pel qual ho volem saber
 * @param p (E) La paraula que busquem
 * @return true La paraula hi és
 * @return false La paraula no hi és
 */
bool pertany(cjt_paraules_t *c, char p[]);

/**
 * @brief Indica si una conjunt esta ple o no
 * 
 * @param c (E) El conjunt pel qual ho volem saber
 * @return true El conjunt està ple 
 * @return false El conjunt no està ple
 */
bool ple(cjt_paraules_t *c);

/**
 * @brief Indica si el conjunt esta buit o no
 * 
 * @param c El conjunt 
 */
bool buit(cjt_paraules_t *c);

/**
 * @brief Indica el nombre de paraules del conjunt
 * 
 * @param c (E) El conjunt pel qual ho volem saber
 * @return int El nombre de paraules del conjunt
 */
int n_elems(cjt_paraules_t *c);

/**
 * @brief Mostra les paraules del conjunt
 * 
 * @param c Conjunt
 */
void mostrar(cjt_paraules_t *c);