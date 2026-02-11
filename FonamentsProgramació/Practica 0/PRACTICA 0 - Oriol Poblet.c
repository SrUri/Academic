#include <stdio.h>
#include <math.h>


int main () /*ESTABLIM EL PUNT D'INICI DEL PROGRAMA*/
{
    int mesos, n_persones_beca, n_persones_senseb, persones_totals;
    float material_inv, viatges, llog_serv, material_fung, preu_persones_contr, preu_sense_overhead, preu_amb_overhead;
    const int cost_pers_beca = 1200, cost_pers_senseb = 2500; /*DEFINIM LES VARIABLES I LES CONSTANTS*/
    
    printf ("Introdueix la durada del projecte: \n");
    scanf ("%d", &mesos);
    
    if (mesos < 6 || mesos > 48) /*DEFINIM QUE SI ELS MESOS SON MENYS DE 6 O MÉS DE 48 ACABI EL PROGRAMA*/
    {
        printf ("Aquesta durada no està acceptada. Reinicia el programa.");
    }
    
    else /*EN CAS DE QUE ELS MESOS, ESTIGUIN DINS DEL PERMÈS, CONTINUEM EL PROGRAMA*/
    {
        printf ("Escriu el nombre de persones amb beca: \n"); 
        scanf ("%d", &n_persones_beca); /*LLEGIM I DEMANEM EL NOMBRE DE PERSONES AMB BECA*/
        
        if (n_persones_beca < 0) /*SI EL NOMBRE DE PERSONES AMB BECA ÉS MÉS PETIT QUE 0, ACABA EL PROGRAMA*/
        {
            printf ("Aquest nombre de persones becades no és possible. Reinicia el programa. \n");
        }
        
        else /*SI EL NOMBRE DE PERSONES AMB BECA, ÉS MÉS GRAN O IGUAL A 0, CONTINUA EL PROGRAMA*/
        {
            printf ("Inserta el nombre de persones sense beca: \n");
            scanf ("%d", &n_persones_senseb); /*LLEGIM I DEMANEM EL NOMBRE DE PERSONES SENSE BECA*/
            
            if (n_persones_senseb < 0) /*SI EL NOMBRE DE PERSONES SENSE BECA ÉS MÉS PETIT QUE 0, ACABA EL PROGRAMA*/
            {
                printf ("Aquest nombre de persones sense beca no és possible. Reinicia el programa. \n");
            }
            
            else /*SI EL NOMBRE DE PERSONES SENSE BECA, ÉS MÉS GRAN O IGUAL A 0, CONTINUA EL PROGRAMA*/
            {
                printf ("El nombre de persones totals a contractar és: ");
                persones_totals=(n_persones_beca+n_persones_senseb);
                printf ("%d persona/es \n", persones_totals); /*A MÉS A MÉS, MOSTREM A L'USUARI EL NOMBRE TOTAL DE CONTRACTATS*/
                
                printf ("Posa el preu total del material inventariable: \n");
                scanf ("%f", &material_inv); /*LLEGIM I DEMANEM EL PREU DEL MATERIAL INVENTARIABLE*/
                
                if (material_inv < 0) /*SI EL PREU DEL MATERIAL INVENTARIABLE ÉS MÉS PETIT QUE 0, ACABA EL PROGRAMA*/
                {
                    printf ("Aquest preu no és possible. Reinicia el programa. \n");
                }
                
                else /*SI EL PREU DEL MATERIAL INVENTARIABLE ÉS IGUAL O MÉS GRAN QUE 0, EL PROGRAMA CONTINUA*/
                {
                    printf ("Introdueix el preu total dels viatges fets: \n");
                    scanf ("%f", &viatges); /*LLEGIM I DEMANEM EL PREU TOTAL DELS VIATGES*/
                    
                    if (viatges < 0) /*SI EL PREU DELS VIATGES ÉS MÉS PETIT QUE 0, EL PROGRAMA ACABA*/
                    {
                        printf ("Aquest preu no pot ser calculat. Reinicia el programa. \n");
                    }
                    
                    else /*SI EL PREU DELS VIATGES ÉS IGUAL O MÉS GRAN QUE 0, EL PROGRAMA CONTINUA*/
                    {
                        printf ("Inserta el preu al que s'ha arribat entre els lloguers i serveis portats a terme: \n");
                        scanf ("%f", &llog_serv); /*LLEGIM I DEMANEM EL PREU TOTAL DELS LLOGUERS I SERVEIS*/
                        
                        if (llog_serv < 0) /*SI EL PREU DELS LLOGUERS I SERVEIS ÉS MÉS PETIT QUE 0, EL PROGRAMA ACABA*/
                        {
                            printf ("Aquest preu és incalculable. Reinicia el programa.  \n");
                        }
                        
                        else /*SI EL PREU DELS LLOGUERS I SERVEIS ÉS IGUAL O MÉS GRAN QUE 0, EL PROGRAMA CONTINUA*/
                        {
                            printf ("Posa el preu total del material fungible utilitzat: \n");
                            scanf ("%f", &material_fung); /*LLEGIM I DEMANEM EL PREU TOTAL DEL MATERIAL FUNGIBLE*/
                            
                            if (material_fung < 0) /*SI EL PREU DEL MATERIAL FUNGIBLE ÉS MÉS PETIT QUE 0, EL PROGRAMA ACABA*/
                            {
                                printf ("Aquest preu és incalculable. Reinicia el programa.  \n");
                            }
                            
                            else
                            {
                                preu_persones_contr=(n_persones_senseb*(cost_pers_senseb*mesos))+(n_persones_beca*(cost_pers_beca*mesos));
                                preu_sense_overhead=(preu_persones_contr+material_inv+viatges+llog_serv+material_fung);
                                /*CALCULEM EL PREU DE LES PERSONES CONTRACTADES I EL PREU TOTAL SENSE OVERHEAD*/
                                
                                if (mesos >= 6 && mesos <= 12) /*SI LA DURADA DEL PROJECTE ESTÀ DINS DE 6 A 12 MESOS, EL OVERHEAD ES CALCULA AMB EL 10%*/
                                {
                                    preu_amb_overhead=((preu_sense_overhead*0.1)+preu_sense_overhead);
                                }
                                
                                else /*SI LA DURADA DEL PROJECTE SOBREPASSA ELS 12 MESOS, EL OVERHEAD ES CALCULA AMB EL 20%*/
                                {
                                    preu_amb_overhead=((preu_sense_overhead*0.2)+preu_sense_overhead);
                                }
                                
                                printf ("El preu total de tot el projecte de recerca és: ");
                                printf ("%.2f", preu_amb_overhead); /*FINALMENT, MOSTREM EL PREU AMB OVERHEAD TOTAL DEL PROJECTE DE RECERCA*/
                            }
                        }
                    }
                }
            }
        }
    }  
}
