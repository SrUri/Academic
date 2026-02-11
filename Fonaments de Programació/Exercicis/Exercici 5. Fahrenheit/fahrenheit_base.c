 #include <stdio.h>
#include <stdbool.h>
#define MAX_F 100
#define MAX_C 30

/**
 * Carrega les dades que conte el fitxer fisic passat per parametre.
 * @param nom_fit (Valor (E): cadena de caracters). El nom del fitxer fisic.
 * @param taula (Valor (S): taula de reals). Taula on es desaran els valors.
 * @param n_f (Ref (S): enter). Nombre efectiu de files que s'han carregat.
 * @param n_C (Ref (S): enter). Nombre efectiu de columnes que s'han carregat.
 * @return boolea. Cert si s'ha pogut llegit el fitxer; fals altrament.
*/
bool obtenir_dades(char *nom_fit, float taula[][MAX_C], int *n_f, int *n_c)
{
	FILE *fit;
	int i, j, files, cols;
	bool f_ok;

	fit = fopen(nom_fit, "r");

	if (fit == NULL)
	{
		f_ok = false;
	}
	else
	{
		f_ok = true;

		fscanf(fit, "%d", &files);
		fscanf(fit, "%d", &cols);

		/* Suposem que les dades hi son totes */
		for (i = 0; i < files; i++)
		{
			for (j = 0; j < cols; j++)
			{
				fscanf(fit, "%f", &taula[i][j]);
			}
		}	
		fclose(fit);
	}
	*n_f = files;
	*n_c = cols;

	return f_ok;
}

/**
 * Desa les dades que conte la taula al fitxer fisic passat per parametre.
 * @param nom_fit (Valor (E): cadena de caracters). El nom del fitxer fisic.
 * @param taula (Valor (E): taula de reals). Taula que conte els valors.
 * @param n_f (Ref (E): enter). Nombre efectiu de files que conte la taula.
 * @param n_C (Ref (E): enter). Nombre efectiu de columnes que conte la taula.
 * @return boolea. Cert si s'ha pogut escriure el fitxer; fals altrament.
*/
bool desar_dades(char *nom_fit, float taula[][MAX_C], int n_f, int n_c)
{
	FILE *fit;
	int i, j;
	bool f_ok;

	fit = fopen(nom_fit, "w");

	if (fit == NULL)
	{
		f_ok = false;
	}
	else
	{
		f_ok = true;

		fprintf(fit, "%d\n", n_f);
		fprintf(fit, "%d\n", n_c);

		for (i = 0; i < n_f; i++)
		{
			for (j = 0; j < n_c; j++)
			{
				fprintf(fit, "%f\t", taula[i][j]);
			}
			fprintf(fit, "\n");
		}	
		fclose(fit);
	}
	
	return f_ok;
}

int main ()
{
	float dades[MAX_F][MAX_C];
	int n_fil, n_col, i, j, defec, mitja;
	char fitxer[50];

	printf("Nom del fitxer a processar ");
	scanf("%s", fitxer);

	/* Obrim el fitxer */
	if (!obtenir_dades(fitxer, dades, &n_fil, &n_col))
	{
		printf ("Hi ha hagut un error en la carrega de les dades\n");	
	}
	else
	{
		/* Processem les dades */
		/* Empleneu-ho vosaltres! */
		for (i = 0; i < n_fil; i++)
		{
			for (j = 0; j < n_col; j++)
			{
				if (dades[i][j]==-999.9f)
				{
					defec++;
					printf ("La fila i la columna es %d i %d\n", i, j);
				}

				else
				{
					dades[i][j]=(dades[i][j]-32)*5/9;
					mitja = mitja + dades[i][j];
				}
			}
		}

		mitja = dades[i][j]/(n_fil*n_col-defec);
		printf ("La mitja és %d\n", mitja);

		/* Desem les dades */
		if (!desar_dades("celsius.txt", dades, n_fil, n_col))
		{
			printf("No s'han pogut desar les dades\n");
		}
	}

	return 0;
}
