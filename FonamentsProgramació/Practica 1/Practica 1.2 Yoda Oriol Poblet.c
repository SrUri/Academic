#include<stdio.h>
#include<stdlib.h>
#define MAX_LL 400
#define MAX_F 15
#define MAX_C 15

//Introduïm el dibuix del mestre Yoda que ens han aportat.
void dibuixa_yoda ()
{
    char yoda[4][19] = {{92, 96, 45, 45, 46, 95, 39, 46, 58, 58, 46, 96, 46, 95, 46, 45, 45, 39, 47},
    {32, 92, 32, 32, 32, 96, 32, 95, 95, 58, 58, 95, 95, 32, 39, 32, 32, 32, 47},
    {32, 32, 32, 45, 45, 58, 46, 96, 39, 46, 46, 96, 39, 46, 58, 45, 45, 32, 32},
    {32, 32, 32, 32, 32, 32, 92, 32, 96, 45, 45, 39, 32, 47, 32, 32, 32, 32, 32}};
    
    int i, j;
    printf("\n\n\n");

    for (i = 0; i < 4; i++)
    {
        for (j = 0; j < 19; j++)
        {
            printf ("%c", yoda[i][j]);
        }
        printf("\n");
    }
}

//Per començar, establirem el procediment quan el nivell de yoidificació sigui 0
void yodificacio_0(char fr_origi[MAX_LL], char taula_origi[MAX_F][MAX_C], char taula_yodif[MAX_F][MAX_C], char fr_yodif[MAX_LL])
{
    int z, i, j, x, y, a;
    i=0;j=0;x=0;y=0;z=0;a=0;

    //Establirem les variables i passarem les frases del arxiu a taula per a col·locarles per files i columnes
    while(fr_origi[z]!= '.')
    {
        if(fr_origi[z]!=' ')
        {
            //Mentres no detecti un espai, anirà copiant els caràcters de la frase i els passarà a taula anant sumant una columna.
            taula_origi[i][j]=fr_origi[z];
            j++;
        }
        else
        {
            //Quan detecti un espai, passarà al else i copiarà en una altre fila els següents caràcters de la frase.
            taula_origi[i][j]=fr_origi[z];
            j++;
            taula_origi[i][j]='\0';
            j=0;
            i++;
            //Això ens servirà com a contador de paraules.
        }
        z++;
    }

    //Posem el sentinella final.
    taula_origi[i][j]='\0';
    j=0;
    i++;
    taula_origi[i][j]='.';
    i=0;
    y=0;
    a=0;

    //En aquest pas passarem de taula original a taula yodificada.
    while(taula_origi[i][j]!='.')
    {
        if(taula_origi[i][j]!='\0')
        {
            taula_yodif[y][a]=taula_origi[i][j];
            a++;
            j++;
        }
        else
        {
            taula_yodif[y][a]=taula_origi[i][j];
            a=0;
            j=0;
            i++;
            y++;
        }
    }
    //Al ser nivell 0, copiariem la taula i les dos taules seríen exactament iguals
    taula_yodif[y][a]=taula_origi[i][j];
    x=0;
    y=0;
    a=0;
    
    //En aquest darrer pas, passarem la taula yodificada a frase per a ensenyar-la a l'usuari que està fent servir el programa.
    while(taula_yodif[y][a]!= '.')
    {
        if(taula_yodif[y][a]!='\0')
        {
            fr_yodif[x]=taula_yodif[y][a];
            x++;
            a++;
        }
        else
        {
            a=0;
            y++;
        }
    }
    //Finaliztem posant un sentinella i copiant la frase.
    fr_yodif[x]=taula_yodif[y][a];
    fr_yodif[x]='\0';
}

//En aquest procès declararem com funcionarà el nivell de yodificació 1.
void yodificacio_1(char fr_origi[MAX_LL], char taula_origi[MAX_F][MAX_C], char taula_yodif[MAX_F][MAX_C], char fr_yodif[MAX_LL])
{
    int z, i, j, x, y, a;
    i=0;j=0;x=0;y=0;z=0;a=0;
    //Tornarem a fer els mateixos processos d'abans.
    while(fr_origi[z]!= '.')
    {
        if(fr_origi[z]!=' ')
        {
            taula_origi[i][j]=fr_origi[z];
            j++;
        }
        else
        {
            taula_origi[i][j]=fr_origi[z];
            j++;
            taula_origi[i][j]='\0';
            j=0;
            i++;
        }
        z++;
    }

    taula_origi[i][j]='\0';
    j=0;
    i++;
    taula_origi[i][j]='.';
    i=0;
    y=0;
    a=0;

    while(taula_origi[i][j]!='.')
    {
        if(taula_origi[i][j]!='\0')
        {
            //Aquí, al ser nivell 1, intercambiarem files de la taula original per files la taula yodificada per a fer aquesta barreja.
            if(i < 6)
            {
                taula_yodif[y][a]=taula_origi[i][j];
                taula_yodif[2][a]=taula_origi[1][j];
                taula_yodif[1][a]=taula_origi[2][j];
            }
            else
            {
                taula_yodif[y][a]=taula_origi[i][j];
                taula_yodif[2][a]=taula_origi[1][j];
                taula_yodif[1][a]=taula_origi[2][j];
                taula_yodif[8][a]=taula_origi[7][j];
                taula_yodif[7][a]=taula_origi[8][j];
            }
            a++;
            j++;
        }
        else
        {
            taula_yodif[y][a]=taula_origi[i][j];
            a=0;
            j=0;
            i++;
            y++;
        }
    }
        
    taula_yodif[y][a]=taula_origi[i][j];
    x=0;
    y=0;
    a=0;

    while(taula_yodif[y][a]!= '.')
    {
        if(taula_yodif[y][a]!='\0')
        {
            fr_yodif[x]=taula_yodif[y][a];
            x++;
            a++;
        }
        else
        {
            a=0;
            y++;
        }
    }
    //Posarem el sentinella final i acabarem el nivell 1 de yodificació
    fr_yodif[x]=taula_yodif[y][a];
    fr_yodif[x]='\0';
}

//En aquest procès declararem com funcionarà el nivell de yodificació 2.
void yodificacio_2(char fr_origi[MAX_LL], char taula_origi[MAX_F][MAX_C], char taula_yodif[MAX_F][MAX_C], char fr_yodif[MAX_LL])
{
    //Farem tot igual que abans, de frase a taula, de taula a taula yodificada i de taula yodificada a frase yoidificada.
    int z, i, j, x, y, a;
    i=0;j=0;x=0;y=0;z=0;a=0;

    while(fr_origi[z]!= '.')
    {
        if(fr_origi[z]!=' ')
        {
            taula_origi[i][j]=fr_origi[z];
            j++;
        }
        else
        {
            taula_origi[i][j]=fr_origi[z];
            j++;
            taula_origi[i][j]='\0';
            j=0;
            i++;
        }
        z++;
    }

    taula_origi[i][j]='\0';
    j=0;
    i++;
    taula_origi[i][j]='.';
    i=0;
    y=0;
    a=0;

    while(taula_origi[i][j]!='.')
    {
        if(taula_origi[i][j]!='\0')
        {
            //Per a donar-li més barreja intercambiarem encara més files de la taula original per altres files de la taula yodificada.
            if(i < 7)
            {
                taula_yodif[y][a]=taula_origi[i][j];
                taula_yodif[2][a]=taula_origi[1][j];
                taula_yodif[1][a]=taula_origi[2][j];
                taula_yodif[5][a]=taula_origi[4][j];
                taula_yodif[4][a]=taula_origi[5][j];
            }
            else
            {
                taula_yodif[y][a]=taula_origi[i][j];
                taula_yodif[2][a]=taula_origi[1][j];
                taula_yodif[1][a]=taula_origi[2][j];
                taula_yodif[5][a]=taula_origi[4][j];
                taula_yodif[4][a]=taula_origi[5][j];
                taula_yodif[8][a]=taula_origi[7][j];
                taula_yodif[7][a]=taula_origi[8][j];
                taula_yodif[14][a]=taula_origi[13][j];
                taula_yodif[13][a]=taula_origi[14][j];
            }
            
            a++;
            j++;
        }
        else
        {
            taula_yodif[y][a]=taula_origi[i][j];
            a=0;
            j=0;
            i++;
            y++;
        }
    }
  
    taula_yodif[y][a]=taula_origi[i][j];
    x=0;
    y=0;
    a=0;

    while(taula_yodif[y][a]!= '.')
    {
        if(taula_yodif[y][a]!='\0')
        {
            fr_yodif[x]=taula_yodif[y][a];
            x++;
            a++;
        }
        else
        {
            a=0;
            y++;
        }
    }
    
    fr_yodif[x]=taula_yodif[y][a];
    fr_yodif[x]='\0';
}

//Aquest procediment ens servirà per al nivell de velocitat.
void velocitat(int n_vel, char fr_yodif[MAX_LL],char fr_punts[MAX_LL])
{
    int i,j,cont_espai;
    i=0;j=0;cont_espai=0;
    
    //La frase, una vegada yodificada passarà pel procès de velocitat, en el que se l'hi afegiran punts suspensius segons digui l'usuari.
    while (fr_yodif[i] != '.')
    {
        if (fr_yodif[i] != ' ')
        {
            fr_punts[j] = fr_yodif[i];
        }
        else
        {
            //Veurem de quin nivell es la velocitat que ens demanen i segons ens diguin afegirem punts suspensius entre paraula i paraula o cada dues paraules.
            cont_espai++;
            if ((n_vel==2)||((n_vel==1)&&(cont_espai%2==0)))
            {
                fr_punts[j] = '.';
                fr_punts[j + 1] = '.';
                fr_punts[j + 2] = '.';
                j = j + 2;
            }
            else
            {
                fr_punts [j] = fr_yodif [i];
            }
        }
        i++;
        j++;
    }
    fr_punts [j] = fr_yodif [i];
    fr_punts [j+1] = '\0';
    printf ("%s", fr_punts);
    printf("\n");
}

//Establirem un procediment per demanar a l'usuari quin nivell de yodificació vol i li explicarem com funciona.
void printf_yodif()
{
    printf("Del nivell 0 al 2, quin nivell de barreja (yodificacio) vols?\n");
    printf("Tingues en compte que 0 és considerat com que no vols barreja i 2 com que en vols molta\n");
}

//Establirem un procediment per demanar a l'usuari quin nivell de velocitat vol i li explicarem com funciona.
void printf_veloc()
{
    printf("Del nivell 0 al 2, quina velocitat de parla vols?\n");
    printf("Tingues en compte que 0 és considerat com que no vols velocitat i 2 com que en vols molta\n");
}

//Comença el programa
int main()
{
    int n_yodif, n_vel;
    char nom_fit[320], fr_origi[MAX_LL], taula_origi[MAX_F][MAX_C], taula_yodif[MAX_F][MAX_C], fr_yodif[MAX_LL], fr_punts[422];

    //Al començar el programa donarem la benvinguda a l'usuari.
    printf("BENVINGUT AL PROGRAMA DE YODIFICACIÓ\n\n");
    printf("En aquest programa podràs obrir un fitxer amb diferents frases i tornarn-les més lentes conjuntament amb una barreja divertida similant la manera de parla del mestre Yoda.\n\n");

    //Li demanarem el fitxer que vol introduir i l'obrirem demanant-li a l'usuari el nom del fitxer.
    FILE *fit;
    printf("Per començar, escrigui el nom del fitxer amb les seves frases escrites a dins: ");
    scanf("%s", nom_fit);
    fit=fopen(nom_fit, "r");
    if(fit==NULL)
    {
        //Si el fitxer no es pot obrir li comunicarem.
        printf("No s'ha pogut obrir correctament el fitxer, torni a intentar-ho.");
    }
    else
    {
        //En cas de que s'obri, imprimirem els procediments de velocitat i yodificacio.
        printf_yodif();
        scanf("%x", &n_yodif);
        if((n_yodif<0)||(n_yodif>2))
        {
            //En cas de que el valor sigui més petit que 0 o més gran de 2, yodificar no serà possible i tornarem a reiniciar el programa.
            printf("Has introduit un valor que no està acceptat, el nivell de yodificació ha d'estar entre 0 i 2");
            return 0;
        }
        
        printf_veloc();
        scanf("%x", &n_vel);

        if((n_vel<0)||(n_vel>2))
        {
            //En cas de que el valor sigui més petit que 0 o més gran de 2, posar punts suspensius no serà possible i tornarem a reiniciar el programa.
            printf("Has introduit un valor que no està acceptat, el nivell de velocitató ha d'estar entre 0 i 2");
            return 0;
        }
        
        while(!feof(fit))
        {
            //Una vegada obert el fitxer, agafarem les frases i les començarem a utilitzar per yodificar-les i alentir-les. 
            fgets(fr_origi, 400, fit);

            if(n_yodif==0)
            {
                //En cas que el nivell de yodificació sigui 0, dibuixarem a yoda i deixarà les paraules sense barrejar, la velocitat mirarà per la seva part al seu procediment i també afegirà els punts suspensius que se l'hi demanin.
                dibuixa_yoda();
                yodificacio_0(fr_origi, taula_origi, taula_yodif, fr_yodif);
                velocitat(n_vel, fr_yodif, fr_punts);
            }
            
            if(n_yodif==1)
            {
                //En cas de que el nivell de yoidificació sigui 1, barrejarem les paraules, dibuixarem al mestre Yoda i la velocitat mirarà per la seva part al seu procediment i també afegirà els punts suspensius que se l'hi demanin.
                dibuixa_yoda();
                yodificacio_1(fr_origi, taula_origi, taula_yodif, fr_yodif);
                velocitat(n_vel, fr_yodif, fr_punts);
            }
            
            if(n_yodif==2)
            {
                ////En cas de que el nivell de yoidificació sigui 2, barrejarem encara més les paraules, dibuixarem al mestre Yoda i la velocitat mirarà per la seva part al seu procediment i també afegirà els punts suspensius que se l'hi demanin.
                dibuixa_yoda();
                yodificacio_2(fr_origi, taula_origi, taula_yodif, fr_yodif);
                velocitat(n_vel, fr_yodif, fr_punts);
            }
        }
        
        //Finalment, tancarem el fitxer i imprimirem la frase
        fclose(fit);

        fit=fopen(nom_fit, "a");
        if(fit==NULL)
        {
            printf("No s'ha pogut obrir correctament el fitxer, torni a intentar-ho.");
        }
        else
        {
            fputs(fr_punts, fit);
        }
        fclose(fit);

        printf("De processar... ha  acabat, el fit.\n");
        return 0;
    }
}
