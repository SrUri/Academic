#include <stdio.h>

void suma (){
    int a = 2;
    int b = 1;
    int c = a + b;
    printf ("El resultat de la suma es %d\n", c);
}

void resta (){
    int a = 2;
    int b = 1;
    int c = a - b;
    printf ("El resultat de la resta es %d\n", c);
}

void multiplicacio (){
    int a = 2;
    int b = 1;
    int c = a * b;
    printf ("El resultat de la multiplicacio es %d\n", c);
}

void divisio (){
    int a = 2;
    int b = 1;
    int c = a / b;
    printf ("El resultat de la divisio es %d\n", c);
}

void increment_a (){
    int a = 2;
    a++;
    printf ("El resultat del increment de a es %d\n", a);
}

void increment_b (){
    int b = 1;
    b++;
    printf ("El resultat del increment de b es %d\n", b);
}

void decreixement_a (){
    int a = 2;
    a--;
    printf ("El resultat del decreixement de a es %d\n", a);
}

void decreixement_b (){
    int b = 1;
    b--;
    printf ("El resultat del decreixement de b es %d\n", b);
}

int main (){
    suma ();
    resta();
    multiplicacio ();
    divisio ();
    increment_a();
    increment_b();
    decreixement_a();
    decreixement_b();
    return 0;
}