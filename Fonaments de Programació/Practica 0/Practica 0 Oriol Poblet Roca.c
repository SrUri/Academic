#include <stdio.h>
#include <math.h>
#define IVA 0.21                                        //Constant d'IVA general: 21%
#define IVA_R 0.1                                       //Constant d'IVA reduït: 10%
#define IVA_SR 0.04                                     //Constant d'IVA super reduït: 4%

int main()
{
    float fiv, cio, resta, beg_ensu, beg_alco, aigua, prod_sani, despesa, prod_iva, prod_ivar, prod_ivasr, pr_iva, pr_ivar, pr_ivasr;       //Declarem variables

    printf("Indica la despesa en fruites, verdures, hortalisses, llegums, tubercles: \n");                      //Demanem la despesa de "fiv"
    scanf("%f", &fiv);                                                                                          //Llegim la despesa de "fiv"
    
    printf("\nIndica la despesa en cereals, ous, pa, formatge i llet: \n");                                     //Demanem la despesa de "cio"
    scanf("%f", &cio);                                                                                          //Llegim la despesa de "cio"
    
    printf("\nIndica la despesa en la resta d'aliments: \n");                                                   //Demanem la despesa de "resta"
    scanf("%f", &resta);                                                                                        //Llegim la despesa de "resta"
    
    printf("\nIndica la despesa en begudes ensucrades: \n");                                                    //Demanem la despesa de "beg_ensu"
    scanf("%f", &beg_ensu);                                                                                     //Llegim la despesa de "beg_ensu"
    
    printf("\nIndica la despesa en begudes alcoholiques: \n");                                                  //Demanem la despesa de "beg_alco"
    scanf("%f", &beg_alco);                                                                                     //Llegim la despesa de "beg_alco"
    
    printf("\nIndica la despesa en aigua: \n");                                                                 //Demanem la despesa de "aigua"
    scanf("%f", &aigua);                                                                                        //Llegim la despesa de "aigua"
    
    printf("\nIndica la despesa en productes sanitaris: \n");                                                   //Demanem la despesa de "prod_sani"
    scanf("%f", &prod_sani);                                                                                    //Llegim la despesa de "prod_sani"
    
    printf("\n");
    
    if(fiv<0 || cio<0 || resta<0 || beg_ensu<0 || beg_alco<0 || aigua<0 || prod_sani<0)                         //Si alguna de les variables és menor que 0
    {
        printf("Has introduït un valor menor que 0! Revisa les teves despeses i reinicia el programa!");        //Imprimim el missatge
    }
    else if(fiv==0 && cio==0 && resta==0 && beg_ensu==0 && beg_alco==0 && aigua==0 && prod_sani==0)             //Sino, si totes les variables son iguals a 0
    {
        printf("Si no has comprat res, no utilitzis el programa inútilment!");                                  //Imprimim el missatge
    }
    else                                                                                                        //Sino
    {
        prod_iva=(beg_alco+beg_ensu);                                                   //Sumem en "prod_iva" la despesa en beguda alcoholica i beguda ensucrada
        prod_iva=(prod_iva+(prod_iva*0.8)+(prod_iva*0.7)+(prod_iva*0.6));               //Calculem en "prod_iva" la despesa de 4 setmanes sense IVA general
        pr_iva=(prod_iva+(prod_iva*IVA));                                               //Calculem en "pr_iva" la despesa de 4 setmanes amb IVA general
        
        prod_ivar=(prod_sani+aigua+resta);                                              //Sumem en "prod_ivar" la despesa en productes sanitaris, aiga i la resta
        prod_ivar=(prod_ivar+(prod_ivar*0.8)+(prod_ivar*0.7)+(prod_ivar*0.6));          //Calculem en "prod_ivar" la despesa de 4 setmanes sense IVA reduït
        pr_ivar=(prod_ivar+(prod_ivar*IVA_R));                                          //Calculem en "pr_ivar" la despesa de 4 setmanes amb IVA reduït
        
        prod_ivasr=(fiv+cio);                                                           //Sumem en "prod_ivasr" la despesa en fruites, verdures, cereals, ous...
        prod_ivasr=(prod_ivasr+(prod_ivasr*0.8)+(prod_ivasr*0.7)+(prod_ivasr*0.6));     //Calculem en "prod_ivasr" la despesa de 4 setmanes sense IVA  super reduït
        pr_ivasr=(prod_ivasr+(prod_ivasr*IVA_SR));                                      //Calculem en "pr_ivasr" la despesa de 4 setmanes amb IVA super reduït
        
        despesa=(pr_iva+pr_ivar+pr_ivasr);                                              //Calculem en "despesa" la suma de totes les despeses de 4 setmanes
        
        printf("La despesa dels productes de IVA general sense IVA aplicat és: %.2f \n", prod_iva);                 //Imprimim "prod_iva" sense IVA general
        printf("La despesa dels productes de IVA general amb IVA aplicat és: %.2f \n\n", pr_ivar);                  //Imprimim "pr_iva" amb IVA general
        printf("La despesa dels productes de IVA reduït sense IVA aplicat és: %.2f \n", prod_ivar);                 //Imprimim "prod_ivar" sense IVA reduït
        printf("La despesa dels productes de IVA reduït amb IVA aplicat és: %.2f \n\n", pr_ivar);                   //Imprimim "pr_ivar" amb IVA reduït
        printf("La despesa dels productes de IVA super reduït sense IVA aplicat és: %.2f \n", prod_ivasr);          //Imprimim "prod_ivasr" sense IVA super reduït
        printf("La despesa dels productes de IVA super reduït amb IVA aplicat és: %.2f \n\n", pr_ivasr);            //Imprimim "pr_ivasr" amb IVA super reduït
        printf("La despesa total és: %.2f", despesa);                                                               //Imprimim "despesa" total
    }
    
    return 0;
}

