#include <stdio.h>
#include <stdbool.h>
#define MAX_FR 80
#define MAX_FRE 160

void construir_matriu_xifrat(char matriu_x[7][7])
{
    char xifr[] = " XIFRAT";
    char norm[] = " NORMAL";
    char lletra='A';                            /* Establim la variable "lletra" a "A" en ASCII per a fer un bucle per a la taula */
    int j=0;

    for(int i=0; i<=6; i++)                     /* Creem la taula per a guardar les lletres amb les que encriptarem */
    {
        while(i==0 && j<=6)
        {
            matriu_x[0][j]=xifr[j];             /* Utilitzem variable que conté XIFRAT */
            j++;
        }
        matriu_x[i][0]=norm[i];                 /* Utilitzem variable que conté NORMAL */
    }

    for(int i=1; i<=6; i++)                     /* Continuem la taula amb les lletres a encriptar */
    {
        for(int j=1; j<=6; j++)
        {
            if(lletra>=65 && lletra<=90)        /* Si el valor de "lletra" (que al principi és "A" = 65, anem sumant 1 digit fins a arribar a 90, que equival a "Z"  */
            {
                matriu_x[i][j]=lletra;
                lletra=lletra+1;
            }
            else if(lletra==91)                 /* Quan el valor de "lletra" sigui 91, escribim un espai */
            {
                lletra=' ';
                matriu_x[i][j]=lletra;
                lletra=49;                      /* Establim el valor de "lletra" a 49 per a que fagi el bucle a partir de 1 */
            }
            else if(lletra>=49 && lletra<=57)   /* Quan el valor estigui entre 49 que es "1" i 57, que es "9", escriu el nombre*/
            {
                matriu_x[i][j]=lletra;
                lletra=lletra+1;
            }
        }
    }
}

void transformar_majuscules(char frase[MAX_FR])
{
    int i=0;

    while(frase[i]!='\n' && frase[i]!='\r')     /* Mentres "frase[i]" no sigui \n i "frase[i]" no sigui \r */
    {
        if(frase[i]>=97 && frase[i]<=122)       /*  Si "frase[i]" conté un valor ASCII entre 97 (que és "a") i 122 (que és z) */
        {
            frase[i]=frase[i]-32;               /*  Li restem 32 a la lletra per a que sigui majuscula */
        }
        else if((frase[i]>=65 && frase[i]<=90) || (frase[i]>=49 && frase[i]<=57) || (frase[i]==' '))
        {
            frase[i]=frase[i];                  /*  Si el valor de ASCII de "frase[i]" esta entre 65 (que és A) i 90 (que és Z), esta entre 49 (que és 1) i 57 (que és 57) o es un espai */
        }
        else
        {
            frase[i]='?';                       /*  En la resta de casos guardarem un interrogant per a posteriorment comprovar que la frase sigui valida */
        }
        i++;
    }
    printf("\n");
}

bool es_valid(char frase[MAX_FR])
{
    bool valid=false;

    for(int i=0; frase[i]!='\n'; i++)
    {
        if(frase[i]=='?')                       /* Com s'ha dit abans, utilizarem el interrogant per a detectar que s'ha introduit un caracter invalid, si es detecta, canviem a true el boolea i retornarem el valor de la variable valid */
        {
            valid=true;
        }
    }
    return valid;
}

void escriure_frase(char frase[MAX_FR], char brossa[MAX_FR])
{
    fgets(brossa, MAX_FR, stdin);               /* Recollim la brossa que hi hagi al buffer */
    printf("Escriu una frase: \n");
    fgets(frase, MAX_FR, stdin);                /*  Agafem la frase a encriptar o desencriptar */
}

void xifrar_frase(char frase[MAX_FR], char frase_encrip[MAX_FRE], char matriu_x[7][7])
{
    int k=0;
    int x=0;

    while(frase[k]!='\n' && frase[k]!='\r')
    {
        for(int i=1; i<=6; i++)
        {
            for(int j=1; j<=6; j++)
            {
                if(frase[k]==matriu_x[i][j])            /* Quan el contingut de "frase[k]" sigui igual al de la matriu */
                {
                    frase_encrip[x]=matriu_x[i][0];     /* Asignem el contingut de la matriu a "frase_encrip[x]" */
                    printf("%c", frase_encrip[x]);
                    x++;                                /* Incrementem el valor de "x" ja que cada lletra per a encriptar equival a dos lletres */
                    frase_encrip[x]=matriu_x[0][j];     /* Asignem el contingut de la matriu a "frase_encrip[x]" */
                    printf("%c", frase_encrip[x]);
                    x++;
                    k++;                                /* Incrementem el valor de "x" i incrementem el valor de "k" */
                }
            }
        }
    }
}

void desxifrar_frase(char frase[MAX_FR], char frase_desencrip[MAX_FRE], char matriu_x[7][7])
{
    int k=0;
    int x=0;
    int cmptNORM=0;
    int cmptXIFR=0;

    while(frase[k]!='\n' && frase[k]!='\r')
    {
        for(int i=1; i<=6; i++)
        {
            if(frase[k]==matriu_x[i][0])                /* Quan "frase[k]" sigui igual a la lletra de la primera fila (on esta "NORMAL") de la matriu */
            {
                cmptNORM=i;                             /* Guardem el valor de "i" a "cmptNORM" */
            }
        }
        k++;                                            /* Augmentem el valor de "k" per a agafar la seguent lletra */
        for(int j=1; j<=6; j++)
        {
            if(frase[k]==matriu_x[0][j])                /* Quan "frase[k]" sigui igual a la lletra de la primera columna (on esta "XIFRAT") de la matriu */
            {
                cmptXIFR=j;                             /* Guardem el valor de "j" a "cmptXIFR" */
            }
        }
        frase_desencrip[x]=matriu_x[cmptNORM][cmptXIFR];    /* Guardem el contingut de matriu a "frase_desencrip[x]" utilitzant com a vectors les posicions guardades en els comptadors */
        printf("%c", frase_desencrip[x]);
        x++;
        k++;                                            /* Incrementem el valor de "x" i de "k" */
    }
}

void des_encriptacio_fitxer(char brossa[MAX_FR], char frase[MAX_FR], char frase_encrip[MAX_FR], char matriu_x[7][7], int opcio)
{
    char nom_fit[30];
    bool sortir=false;
    FILE *fit;

    printf("Escrigui el nom del fitxer amb les seves frases escrites a dins: ");
    scanf("%s", nom_fit);

    fit=fopen(nom_fit, "r");                            /* Obrim el fitxer en mode lectura */

    if(fit==NULL)                                       /* En cas de que no es pogues llegir avisem amb un printf */
    {
        printf("UPS! Error en obrir el fitxer, torni a intentar-ho. \n");
    }
    else
    {
        fgets(brossa, MAX_FR, stdin);                   /* Agafem la brossa amb el fgets */
        fgets(frase, MAX_FR, fit);                      /* Agafem la primera frase del arxiu */
        while(!feof(fit) || sortir==true)               /* Mentres no sigui final de fitxer o hi hagi un "\n" a la posicio final */
        {
            if(frase[0]!='\n')                          /* Si la posició 0 no és \n */
            {
                if(opcio==3)                            /* Si la opció és igual a "3" transformem a majuscules la frase i la xifrem */
                {
                    transformar_majuscules(frase);
                    xifrar_frase(frase, frase_encrip, matriu_x);
                    fgets(frase, MAX_FR, fit);          /* Agafem la nova frase i anem repetint el bucle */
                }
                else                                    /* Sino, la opcio sera la "4", per tant, haurem de desxifrar la frase */
                {
                    transformar_majuscules(frase);
                    desxifrar_frase(frase, frase_encrip, matriu_x);
                    fgets(frase, MAX_FR, fit);
                }
            }
            else
            {
                sortir=true;                            /* Si la posicio 0 de frase es \n sortira del bucle */
            }
        }
        fclose(fit);                                    /* Tanquem el fitxer */
    }
}

int main()
{
    char matriu_x[7][7];
    char frase[MAX_FR];
    char frase_encrip[MAX_FRE];
    char frase_desencrip[MAX_FR];
    char brossa[MAX_FR];
    bool fi=false;
    int opcio;

    construir_matriu_xifrat(matriu_x);                  /* Cridem al procediment per construir la matriu d'encriptacio */
    do{
        do
        {
            printf("\nEscolleix una opcio de les 5 seguents: \n");
            printf("Recorda que has d'introduir un nombre entre 1 i 5! \n\n");
            printf("1) Encriptar una frase introduida per teclat.\n");
            printf("2) Desencriptar una frase introduida per teclat.\n");
            printf("3) Encriptar un arxiu amb frases.\n");
            printf("4) Desencriptar un arxiu amb frases.\n");
            printf("5) Sortir del programa.\n\n");
            scanf("%d", &opcio);
            printf("\n");

        }while(opcio<1 || opcio>5);                    /* Repetim el bucle mentres el valor de opcio no sigui menor o igual a zero o sigui més gran que 5 */

        switch(opcio) {
            case 1:                                     /* Al cas 1 del switch, volem escriure una frase i que s'encripti */
                escriure_frase(frase, brossa);
                transformar_majuscules(frase);
                if(es_valid(frase)==false)
                {
                    xifrar_frase(frase, frase_encrip, matriu_x);
                    printf("\n_______________________________________________________\n");
                }
                else
                {
                    printf("Has d'introduir caracters valids per a la encriptacio! Recorda que nomes s'admeten lletres, nombres i espais...");
                }
                break;
            case 2:                                     /* Al cas 2 del switch, volem escriure una frase i que es desencripti */
                escriure_frase(frase, brossa);
                transformar_majuscules(frase);
                if(es_valid(frase)==false)
                {
                    desxifrar_frase(frase, frase_desencrip, matriu_x);
                    printf("\n_______________________________________________________\n");
                }
                else
                {
                    printf("Has d'introduir caracters valids per a la encriptacio! Recorda que nomes s'admeten lletres, nombres i espais...");
                }
                break;
            case 3:                                     /* Al cas 3 del switch, volem llegir un fitxer i encriptar les frases */
                des_encriptacio_fitxer(brossa, frase, frase_encrip, matriu_x, opcio);
                printf("\n_______________________________________________________\n");
                break;
            case 4:                                     /* Al cas 4 del switch, volem llegir un fitxer i desencriptar les frases */
                des_encriptacio_fitxer(brossa, frase, frase_encrip, matriu_x, opcio);
                printf("\n_______________________________________________________\n");
                break;
            case 5:                                    /* Al cas 5 del switch, sortirem del programa */
                fi=true;
                printf("Sortint del programa correctament...");
                break;
        }
    }while(!fi);                                        /* Mentres fi no sigui true, continuarem el bucle */

    return 0;
}
