#include <stdio.h>
#include <stdlib.h>

void compte_enrere(int n)
{
    if(n==0)
    {
        printf("FINAL!\n\n");
    }
    
    else
    {
        if(n>0)
        {
            while(n!=1)
            {
                printf("%d\n, n");
                n--;
            }
            printf("FINAL!\n\n");
        }
        else
        {
            if(n<0)
            {
                while(n!=1)
                {
                    printf("%d\n, n");
                    n++;
                }
                printf("FINAL!\n\n");
            }
        }
    }
}

void factorial(int n)
{
    int x;
    x=n;
    
    if(n>0)
    {
        while(n!=1)
        {
            x=x*(n-1);
            n++;
        }
        if(x<0)
        {
            x=x*(-1);
        }
    }
}

int main(int argc, char *argv[])
{
    if (argc == 2)
    {
        compte_enrere(atoi(argv[1]));
    }

    else
    {
        printf("Cal especificar un numero com a argument!\n");
    }

	return 0;
}