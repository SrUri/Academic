#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "string.h"
#include "cjt_paraules.h"


/**
 * @brief Inicialitza el conjunt per poder-lo utilitzar
 *
 * @param c El conjunt a inicialitzar
 */

void inicialitzar (cjt_paraules_t * c)
{
  c->n_el = 0;
}

/**
 * @brief Afegeix una paraula al conjunt.
 * Si aquest estC  ple, s'indica un error
 *
 * @param c (E/S) Conjunt al qual afegim la paraula
 * @param p (E) La paraula a afegir
 * @return true S'ha pogut afegir
 * @return false No s'ha pogut afegir
 */

bool afegir (cjt_paraules_t * c, char p[])
{
    int i;
    bool cmp=true;

    if(ple(c)==true)
    {
        cmp=false;
    }
    else
    {
        if(pertany(c, p)==true)
        {
            printf("la paraula ja pertany a la taula");
            cmp=false;
        }
        else
        {

        while((i<c->n_el)&&(cmp!=false))
                {
                    if(((int)p[i]<97)&&((int)p[i]>122)&&((int)p[i]<48)&&((int)p[i]>57))
                    {
                        cmp=false;
                    }
                }
        if(cmp==true)
        {

            //strlen(p) sea menor q 8
            if(strlen(p)>=8)
            {
                cmp=false;
            }
            else
            {

                if(cmp==true)
                {
                    //mirar q los caracteres esten dentro de (abcd...789)
                    c->paraules[n_elems(c)] = (char *) malloc (sizeof (char) *strlen(p)+1);
                    strcpy(c->paraules[n_elems(c)], p);
                    c->n_el++;
                }

            }

        }
        }
    }
    return cmp;
}

/**
 * @brief Elimina una paraula del conjunt, si existeix
 * No informa de si s'ha eliminat o no.
 *
 * @param c (E/S) Conjunt del qual eliminem l'enter
 * @param p (E) La paraula a eliminar
 */
 void eliminar (cjt_paraules_t * c, char p[])
{
    int i, cmp;
    bool pert;
    pert=false;

    if(buit(c)==true)
    {
        printf("No hi ha cap paraula a borrar\n");
    }
    else
    {
        if(pertany(c, p)==true)
        {
            for(i=0; pert==false; i++)
            {
                cmp = strcmp(c->paraules[i], p);
                if (cmp == 0)
                {
                    pert=true;
                }
            }


            free(c->paraules[i]);
            p = c->paraules[i+1];
            strcpy(c->paraules[i], c->paraules[n_elems(c)+1]);
            c->n_el-1;
            printf("La teva paraula %s a sigut eliminada\n", p);
        }
        else
        {
            printf("Aquesta paraula no es troba a la memoria.\n");
        }
    }
}

/**
 * @brief Indica si una paraula pertany o no al conjunt
 *
 * @param c (E) El conjunt pel qual ho volem saber
 * @param p (E) La paraula que busquem
 * @return true La paraula hi C)s
 * @return false La paraula no hi C)s
 */

bool pertany (cjt_paraules_t * c, char p[])
{
    int i, cmp;
    bool pert;
    pert=false;
    for(i=0; i<n_elems(c); i++)
    {
        cmp = strcmp(c->paraules[i], p);
        if (cmp == 0)
        {
            pert=true;
        }
    }
    return pert;
}

/**
 * @brief Indica si una conjunt esta ple o no
 *
 * @param c (E) El conjunt pel qual ho volem saber
 * @return true El conjunt estC  ple
 * @return false El conjunt no estC  ple
 */

bool ple (cjt_paraules_t * c)
{
    bool ple;
    if(n_elems(c)==100)
    {
        ple = true;
    }
    else
    {
        ple = false;
    }
    return ple;
}

/**
 * @brief Indica si el conjunt esta buit o no
 *
 * @param c El conjunt
 */

bool buit (cjt_paraules_t * c)
{
    bool buit;
    if(n_elems(c)==0)
    {
        buit = true;
    }
    else
    {
        buit = false;
    }
    return buit;
}
 /**
 * @brief Indica el nombre de paraules del conjunt
 *
 * @param c (E) El conjunt pel qual ho volem saber
 * @return int El nombre de paraules del conjunt
 */

int n_elems (cjt_paraules_t * c)
{
    return(c->n_el);
}

/**
 * @brief Mostra les paraules del conjunt
 *
 * @param c Conjunt
 */

void mostrar (cjt_paraules_t * c)
{
    int i=0;
    while(i<n_elems(c))
    {
        printf("%s\n", c->paraules[i]);
        i++;
    }
}
