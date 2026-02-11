#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX 10

typedef struct
{
    int taula[MAX];

} bloc_creixent_t;


int aleatori(int min, int max)
{
    min=0;
    max=20;

    return (min + (rand()%(max-min+1)));
}

void omple_taula(bloc_creixent_t *b)
{
    int i, min, max;

    for(i=0; i<MAX; i++)
    {
        b->taula[i]=aleatori(min, max);
        printf("Posicio: %d. Nombre: %d.\n", i, b->taula[i]);
    }
}


int main()
{
    int min, max;
    bloc_creixent_t b;

    omple_taula(&b);

    return 0;
}
