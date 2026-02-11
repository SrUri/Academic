[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/tvMam4a4)
[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-c66648af7eb3fe8bc4f294546bfd86ef473780cde1dea487d3c4ff354943c9ae.svg)](https://classroom.github.com/online_ide?assignment_repo_id=10517704&assignment_repo_type=AssignmentRepo)
# Activitat 3: Implementació d'una Taula de Hashing (HashMap) i comparació del rendiment entre una Taula de Hashing i una Llista.

*Aquesta activitat és obligatòria i comptarà un 15% de la nota de pràctiques de l'avaluació contínua.*

L'objectiu d'aquesta activitat es desenvolupar una taula de hashing des de zero, implementant la versió encadenada indirecta com la vista als apunts de classe. Les tasques a realitzar en aquesta activitat son les següents:

- la taula de hashing ha d'implementar els mètodes descrits a la interfície IHashMap. També ha de tenir un constructor que permeti fixar la mida inicial de la taula.

- per a la funció de hashing podeu fer servir el mètode hashcode que implementen tots els objectes de java (https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#hashCode()).

- necessitarem una implementació de la interfície IllistaGenerica, podeu fer servir la implementació doblement enllaçada no ordenada de la pràctica anterior.

- també necessitareu que la classe película es pugui comparar, podeu fer servir el mètode implementat a l'activitat anterior.

- introduirem l'ús de la interfície *iterable* per a poder recorrer l'estructura. Tant el hashmap com la llista hauran de proporcionar un mètode que ens doni un objecte iterable per a poder recorrer tots els seus elements.

- s'hauran de fer dues implementacions de la interfície IPelicules, una que guardi la informació en una instancia de la vostra taula de hashing, i una altra que guardi la informació a la vostra llista, i implementar tots els metodes corresponents fent us dels iterables que us proporcionen aquestes classes. 

- com a part opcional (que compta un 20% de la nota de l'activitat) es demana que la taula de hashing es redimensioni si el factor de càrrega es superior al 75%, doblant la seva mida. Existeix un test especific per a comprovar si el redimensionament de la taula funciona, si no ho està implementat el redimensionament no passarà aquest test.

IMPORTANT: No es poden fer servir les classes de java.util.xxxx a no ser que s'indiqui especificament que es pot fer servir dins d'algun mètode o classe.
