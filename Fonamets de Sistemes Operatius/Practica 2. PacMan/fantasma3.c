/*****************************************************************************/
/*									                                         */
/*				     cocos0.c				                                 */
/*									                                         */
/*     Programa inicial d'exemple per a les practiques 2.1 i 2.2 de FSO.     */
/*     Es tracta del joc del menjacocos: es dibuixa un laberint amb una      */
/*     serie de punts (cocos), els quals han de ser "menjats" pel menja-     */
/*     cocos. Aquest menjacocos es representara amb el caracter '0', i el    */
/*     moura l'usuari amb les tecles 'w' (adalt), 's' (abaix), 'd' (dreta)   */
/*     i 'a' (esquerra). Simultaniament hi haura un conjunt de fantasmes,    */
/*     representats per numeros de l'1 al 9, que intentaran capturar al      */
/*     menjacocos. En la primera versio del programa, nomes hi ha un fan-    */
/*     tasma.								                                 */
/*     Evidentment, es tracta de menjar tots els punts abans que algun fan-  */
/*     tasma atrapi al menjacocos.					                         */
/*									                                         */
/*  Arguments del programa:						                             */
/*     per controlar la posicio de tots els elements del joc, cal indicar    */
/*     el nom d'un fitxer de text que contindra la seguent informacio:	     */
/*		n_fil1 n_col fit_tauler creq				                         */
/*		mc_f mc_c mc_d mc_r						                             */
/*		fant_f fant_c fant_d fant_r						                             */
/*									                                         */
/*     on 'n_fil1', 'n_col' son les dimensions del taulell de joc, mes una   */
/*     fila pels missatges de text a l'ultima linia. "fit_tauler" es el nom  */
/*     d'un fitxer de text que contindra el dibuix del laberint, amb num. de */
/*     files igual a 'n_fil1'-1 i num. de columnes igual a 'n_col'. Dins     */
/*     d'aquest fitxer, hi hauran caracter ASCCII que es representaran en    */
/*     pantalla tal qual, excepte el caracters iguals a 'creq', que es visua-*/
/*     litzaran invertits per representar la paret.			                 */
/*     Els parametres 'mc_f', 'mc_c' indiquen la posicio inicial de fila i   */
/*     columna del menjacocos, aixi com la direccio inicial de moviment      */
/*     (0 -> amunt, 1-> esquerra, 2-> avall, 3-> dreta). Els parametres	     */
/*     'fant_f', 'fant_c' i 'fant_d' corresponen a la mateixa informacio per al    */
/*     fantasma 1. El programa verifica que la primera posicio del menja-    */
/*     cocos o del fantasma no coincideixi amb un bloc de paret del laberint.*/
/*	   'mc_r' 'fant_r' son dos reals que multipliquen el retard del moviment.  */ 
/*     A mes, es podra afegir un segon argument opcional per indicar el      */
/*     retard de moviment del menjacocos i dels fantasmes (en ms);           */
/*     el valor per defecte d'aquest parametre es 100 (1 decima de segon).   */
/*									                                         */
/*  Compilar i executar:					  	                             */
/*     El programa invoca les funcions definides a 'winsuport.h', les        */
/*     quals proporcionen una interficie senzilla per crear una finestra     */
/*     de text on es poden escriure caracters en posicions especifiques de   */
/*     la pantalla (basada en CURSES); per tant, el programa necessita ser   */
/*     compilat amb la llibreria 'curses':				                     */
/*									                                         */
/*	   $ gcc -Wall cocos0.c winsuport.o -o cocos0 -lcurses		             */
/*	   $ ./cocos0 fit_param [retard]				                         */
/*									                                         */
/*  Codis de retorn:						  	                             */
/*     El programa retorna algun dels seguents codis al SO:		             */
/*	0  ==>  funcionament normal					                             */
/*	1  ==>  numero d'arguments incorrecte 				                     */
/*	2  ==>  fitxer de configuracio no accessible			                 */
/*	3  ==>  dimensions del taulell incorrectes			                     */
/*	4  ==>  parametres del menjacocos incorrectes			                 */
/*	5  ==>  parametres d'algun fantasma incorrectes			                 */
/*	6  ==>  no s'ha pogut crear el camp de joc			                     */
/*	7  ==>  no s'ha pogut inicialitzar el joc			                     */
/*****************************************************************************/



#include <stdio.h>		/* incloure definicions de funcions estandard */
#include <stdlib.h>		/* per exit() */
#include <unistd.h>		/* per getpid() */
#include <pthread.h>
#include <time.h>
#include "memoria.h"
#include "winsuport2.h"		/* incloure definicions de funcions propies */

#define _REENTRANT
#define MIN_FIL 7		/* definir limits de variables globals */
#define MAX_FIL 25
#define MIN_COL 10
#define MAX_COL 80
#define MAX_THREADS 10

/* definir estructures d'informacio */
typedef struct {		/* per un objecte (menjacocos o fantasma) */
	int f;				/* posicio actual: fila */
	int c;				/* posicio actual: columna */
	int d;				/* direccio actual: [0..3] */
  float r;      /* per indicar un retard relati */
	char a;				/* caracter anterior en pos. actual */
} objecte;


/* variables globals */
int n_fil1, n_col;		/* dimensions del camp de joc */
char tauler[70];		/* nom del fitxer amb el laberint de joc */
char c_req;			    /* caracter de pared del laberint */

objecte mc;      		/* informacio del menjacocos */
objecte fant[9];			    /* informacio del fantasma */

int direcf[] = {-1, 0, 1, 0};	/* moviments de les 4 direccions possibles */
int direcc[] = {0, -1, 0, 1};	/* dalt, esquerra, baix, dreta */

int cocos;			/* numero restant de cocos per menjar */
int retard;		    /* valor del retard de moviment, en mil.lisegons */

int ret;            /* RETORN */
int nfant;          /* nombre de fantasmes */
int id_mem;
void *mem;
int i=0;
int *p_ret;

//pthread_t tid[MAX_THREADS];
//pthread_mutex_t mutex=PTHREAD_MUTEX_INITIALIZER;

/* funcio per moure un fantasma una posicio; retorna 1 si el fantasma   */
/* captura al menjacocos, 0 altrament					*/
int main (int n_args, char * ll_args[])
{
  char numfant;
  i = atoi(ll_args[8]);
  id_mem = atoi(ll_args[0]);
  //fprintf(stderr, "idmem_fant: %d\n", id_mem);
  mem = map_mem(id_mem);
  n_fil1 = atoi(ll_args[1]);
  //fprintf(stderr, "n_fil1: %d\n", n_fil1);
  n_col = atoi(ll_args[2]);
  //fprintf(stderr, "n_col: %d\n", n_col);
  fant[i].f = atoi(ll_args[3]);
  //fprintf(stderr, "f: %d\n", fant[i].f);
  fant[i].c = atoi(ll_args[4]);
  //fprintf(stderr, "c: %d\n", fant[i].c);
  fant[i].d = atoi(ll_args[5]);
  //fprintf(stderr, "d: %d\n", fant[i].d);
  fant[i].r = atoi(ll_args[6]);
  //fprintf(stderr, "r: %f\n", fant[i].r);
  fant[i].a = atoi(ll_args[7]);
  //fprintf(stderr, "a: %c\n", fant[i].a);
  retard = atoi(ll_args[9]);
  //fprintf(stderr, "i: %d\n", i);
  win_set(mem, n_fil1, n_col);
  objecte seg;
  numfant=i+49;
  int k, vk, nd, vd[3];
  do {
    nd = 0;
    for (k=-1; k<=1; k++)		/* provar direccio actual i dir. veines */
    {
      vk = (fant[i].d + k) % 4;		/* direccio veina */
      if (vk < 0) vk += 4;		/* corregeix negatius */
      seg.f = fant[i].f + direcf[vk]; /* calcular posicio en la nova dir.*/
      seg.c = fant[i].c + direcc[vk];
      seg.a = win_quincar(seg.f,seg.c);	/* calcular caracter seguent posicio */
      if ((seg.a==' ') || (seg.a=='.') || (seg.a=='0'))
      { vd[nd] = vk;			/* memoritza com a direccio possible */
        nd++;
      }
    }
    if (nd == 0)				/* si no pot continuar, */
      fant[i].d = (fant[i].d + 2) % 4;		/* canvia totalment de sentit */
    else
    { if (nd == 1)			/* si nomes pot en una direccio */
      fant[i].d = vd[0];			/* li assigna aquesta */
      else				/* altrament */
        fant[i].d = vd[rand() % nd];		/* segueix una dir. aleatoria */
      seg.f = fant[i].f + direcf[fant[i].d];  /* calcular seguent posicio final */
      seg.c = fant[i].c + direcc[fant[i].d];
      seg.a = win_quincar(seg.f,seg.c);	/* calcular caracter seguent posicio */
      win_escricar(fant[i].f,fant[i].c,fant[i].a,NO_INV);	/* esborra posicio anterior */
      fant[i].f = seg.f; fant[i].c = seg.c; fant[i].a = seg.a;	/* actualitza posicio */
      win_escricar(fant[i].f,fant[i].c,numfant,NO_INV);		/* redibuixa fantasma */
      if (fant[i].a == '0') ret = 2;		/* ha capturat menjacocos */
    }
    win_retard(retard);
  } while(ret == 0);
  return(*p_ret);
}