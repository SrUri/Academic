/**
 * Programa principal
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdlib.h>
#include "cjt_paraules.h"

int main()
{
    cjt_paraules_t c;       // Conjunt de paraules
    int i;
    char p[9];

    // Inicialitzem el conjunt de paraules
    inicialitzar(&c);

    // I no haurem de mostrar res
    mostrar(&c);

    // Ha de dir que es buit
    if (buit(&c))
    {
        printf("El conjunt es buit.\n");
    }
    else
    {
        printf("El conjunt no es buit.\n");
    }

    // Afegim una paraula
    afegir(&c, "hola");

    // Afegim una paraula
    afegir(&c, "hola2");

    // Afegim una paraula
    afegir(&c, "adeu");

    mostrar(&c);

    // Ha de dir que pertany al conjunt
    if (pertany(&c, "hola"))
    {
        printf("La paraula hola pertany al conjunt\n");
    }
    else
    {
        printf("La paraula hola no pertany al conjunt\n");
    }

    // No ha de notificar res, si la paraula no hi es
    eliminar(&c, "casa");

    // No ha de notificar res, pero ha d'eliminar la paraula perque hi és
    eliminar(&c, "hola");

    // Ara ha de dir que NO pertany al conjunt
    if (pertany(&c, "hola"))
    {
        printf("La paraula hola pertany al conjunt\n");
    }
    else
    {
        printf("La paraula hola no pertany al conjunt\n");
    }

    // La següent paraula no s'ha d'afegir perque supera longitud
    if (afegir(&c, "anticonstitucionalment"))
    {
        printf("La paraula anticonstitucionalment s'ha afegit\n");
    }
    else
    {
        printf("La paraula anticonstitucionalment no s'ha afegit\n");
    }

    // La següent paraula no s'ha d'afegir perque te caracters no permesos
    if (afegir(&c, "OrdiNador"))
    {
        printf("La paraula OrdiNador s'ha afegit\n");
    }
    else
    {
        printf("La paraula OrdiNador no s'ha afegit\n");
    }

    // La darrera prova consisteix en emplenar el conjunt amb el maxim de paraules
    // Cada paraula sera un conjunt de lletres
    for (i = 1000; i< 1100; i++)
    {
        sprintf(p, "%d", i);    // Fa un printf sobre un la cadena 'p'
        if (!afegir(&c, p))
        {
            printf("La paraula %s no hi cap!\n", p);
        }

    }

    // Hem de veure que hi ha paraules que no ha afegit
    mostrar(&c);
    return 1;
}
