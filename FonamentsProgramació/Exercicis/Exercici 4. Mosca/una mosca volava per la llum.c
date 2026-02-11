#include <stdio.h>

int main ()
{
    int j;
    char lletres[32];

    printf ("Escriu una frase i escriu el punt final per acarbar-la.\n", lletres);
    fgets (lletres, 32, stdin);

    j = 0;

    while (lletres[j] != ".")
    {
        if (lletres[j] =="a" || lletres[j] =="e" || lletres[j] =="i" ||l letres[j] =="o" || lletres[j] =="e")
        {
            lletres[j]="a"

        }

        i++;
    }


}