#include <stdio.h>

int main (){
    int persones;
    int nits;

    printf ("Escriu el nombre de persones: \n");
    scanf ("%d", &persones);
    
    if (persones < 12){
        printf ("Escriu el nombre de nits: \n");
        scanf ("%d", &nits);
    }
    else {
        printf ("No es possible reservar amb més de 12 persones \n");
    }

}
