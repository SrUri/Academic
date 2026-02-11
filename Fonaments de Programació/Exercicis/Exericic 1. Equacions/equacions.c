#include <stdio.h>

int main()
{

    int a, b, c, discriminant;
    float x, y;

    printf ("Et resoldré una equació de segon grau. Per començar, assigna un valor a a:\n");
    scanf ("%d", &a);

    printf ("Ara, escriu el valor de b: \n");
    scanf ("%d", &b);

    printf ("Finalment, dona un valor a c: \n");
    scanf ("%d", &c);

    if(a!=0)
    {
        discriminant = ((b*b)-(4*a*c));
        if (discriminant >= 0)
        {
            x=((-b+discriminant)/2*a);
            y=((-b-discriminant)/2*a);
            printf("%.2f", x);
            printf("%.2f", y);
        }
        else
        {
            printf("No es possible retornar la resposta, el discriminant ha de ser més gran o igual a 0.\n");
        }
    }
    else
    {
        printf("No es una equació de segon grau.\n");
    }
    
    return 0;
}
