[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/1XOPx3ti)
[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-718a45dd9cf7e7f842a935f5ebbe5719a5e09af4491e668f4dbf3b35d5cca122.svg)](https://classroom.github.com/online_ide?assignment_repo_id=11058179&assignment_repo_type=AssignmentRepo)
# Activitat 5: Implementació d'un recomanador de pel·lícules
 
*Aquesta activitat és obligatòria i comptarà un 60% de la nota de pràctiques de l'avaluació contínua*

El 60% d'aquesta nota es divideix en tres parts:
- Un 35% corresponent al projecte presentat
- Un 10% corresponent a l'entrevista de pràctiques que es farà en la darrera setmana de curs i on es valorarà el domini tècnic del projecte.
- Un 15% corresponent al resultat d'una competició que hi haurà entre els recomanadors, on es premiarà els que donin les millors recomanacions en el menor temps possible. En la propera sessió us donarem més detalls sobre aquesta part. Aquesta part és opcional i podeu no optar a participar a la competició.
 
*Descripció de l'activitat*

L'objectiu d'aquesta pràctica és fer servir totes les estructures de dades que heu implementat en les entregues anteriors per a crear un sistema que sigui capaç de recomanar pel·lícules a un usuari. Aquestes recomanacions han d’estar basades en la informació de les pel·lícules vistes per l'usuari (i la seva valoració), així com també la informació del que han vist els altres usuaris del sistema.

*Dataset*

Per a fer aquesta activitat farem servir el dataset de pel·lícules del challenge de Netflix que ja us hem donat en algunes entregues anteriors, i ara passem a afegir uns fitxers nous que ens indiquin quins usuaris han mirat pel·lícules, i quin rating li han donat a cadascuna d'elles. Podeu descarregar el dataset i els detalls/format dels fitxers inclosos en aquesta web: https://www.kaggle.com/datasets/netflix-inc/netflix-prize-data


Fixeu-vos que es tracten de moltes dades (descomprimits són més de 2GB!). Els fitxers més grans (combined_data_X) inclouen les pel·lícules, i per cada pel·lícula hi ha un llistat dels usuaris que li han fet una valoració (amb el valor i la data en que li han posat).

El nostre consell per a treballar és que, fins que no tingueu el sistema de recomanació implementat, no feu servir tot el dataset. Us proposem dues alternatives per a poder treballar durant el desenvolupament i poder fer proves amb un temps d’execució curt, sense la necessitat de carregar totes les dades i testejar el sistema de recomanació al complet:
 
- Podeu fer-vos el vostre propi llistat de recomanacions (per exemple, fent-vos un fitxer amb 4-5 pel·lícules, i fixant vosaltres els usuaris i els valors de les recomanacions que els hi poseu a cadascú manualment).
- Fer-vos un fitxer d'usuaris propi que tan sols incorpori les primeres X pel·lícules del fitxer complet.

*Desenvolupament*

El desenvolupament d'aquesta activitat és lliure, podeu decidir com guardar les dades sempre que es facin servir les estructures implementades en les classes anteriors. En principi si us han passat els testos que havíem dissenyat per aquestes classes us han de servir per a poder-les utilitzar en aquesta darrera pràctica.

Primer us recomanem que desenvolupeu el sistema per a emmagatzemar la informació necessària per a l'algorisme: les pel·lícules, els usuaris i les relacions entre ells (valoracions). Haureu de carregar les dades, guardar-les a l'estructura i validar que es poden accedir correctament i amb el menor cost temporal possible.

A continuació haureu de dissenyar el sistema que faci les recomanacions. Aquest sistema ha de rebre com a paràmetre d'entrada un usuari, i com a sortida ens haurà d'indicar:

- Les 3 pel·lícules que li recomanem que li poden agradar segons el que ha vist i valorat anteriorment. En el cas que el sistema no sigui capaç de recomanar-ne 3, pot fer-ho per dues, una o cap.
- Una descripció del raonament per el qual se li recomanen aquestes pel·lícules. Aquesta descripció és lliure, ho podeu escriure per pantalla en el format que vulgueu, però que sigui llegible i fàcil d’entendre.
 
Per al disseny del sistema de recomanació haureu de fer servir els algorismes vistos per a treballar sobre grafs, adaptant-los per al cas específic que us demanem aquí. A les classes us anirem donant pistes d'alguns algoritmes que us poden servir de base, però vosaltres haureu de pensar com adaptar-ho i/o optimitzar-ho per a que pugui servir amb el volum de dades més gran possible i amb la millor resolució.

A més, és molt possible que necessiteu crear-vos estructures de dades temporals que us permetin accelerar alguns aspectes de l’algorisme o que emmagatzemin dades temporals.

Finalment, volem comentar-vos que en aquesta activitat no us donem un conjunt de testos per a validar el funcionament del vostre algorisme, ja que cadascú ho implementarà a la seva manera i els resultats poden ser diferents (però no incorrectes). Tot i així *us recomanem* que vosaltres mateixos us dissenyeu testos per anar valorant si les parts de la vostra implementació funcionen adequadament.

*Valoració de l'activitat*

Per a la valoració d'aquest treball es tindran en compte els següents aspectes:

- Un codi correcte, ben estructurat i que faci bon ús de les estructures dissenyades en les activitats anteriors.
- Que els resultats presentats estiguin ben justificats i que siguin coherents.
- Que el cost temporal de l'algorisme sigui el menor possible.
- Que funcioni amb el màxim de dades possible (idealment amb tot el dataset). També s'acceptaran aquelles activitats que treballin sobre un subset de les dades, tot i que tindran certa penalització en la nota.
