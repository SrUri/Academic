#include <stdio.h>
#define MAX 100

int main()
{
    FILE *fit_t, *fit_b;
    int n;
    float n_3;
    char paraula[MAX];

    do {
        printf("Escriu un nombre que no sigui multiple de 3 ");
        scanf("%d", &n);
    } while(n % 3 == 0);

    n_3 = (float)n / 3.0;
    printf("El numero %d entre tres es %.2f\n", n, n_3);

    printf("Escriu ara una paraula ");
    scanf("%s", paraula);

    /* Obrim el fitxer de text per escriure */
    fit_t = fopen("text.txt", "w");

    /* Obrim el fitxer binari per escriure */
    fit_b = fopen("binari.txt", "wb");

    /* Nomes treballem si hem obert els fitxers  */
    if ((fit_t != NULL) && (fit_b != NULL))
    {
        /* Escrivim al fitxer de text */
        fputs("Fitxer de text\n==============\n", fit_t);
        fprintf(fit_t, "%d %.2f\n", n, n_3);
        fprintf(fit_t, "%s\n", paraula);

        /* Escrivim al fitxer binari */
        fputs("Fitxer binari\n=============\n", fit_b);
        fwrite(&n, sizeof(n), 1, fit_b);
        fwrite(&n_3, sizeof(n_3), 1, fit_b);
        fprintf(fit_b, "%s\n", paraula);

        /* Tanquem els fitxers */
        fclose(fit_t);
        fclose(fit_b);
    }
	return 0;
}