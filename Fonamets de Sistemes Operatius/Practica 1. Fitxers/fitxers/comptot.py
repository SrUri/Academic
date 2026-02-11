#!/usr/bin/env python3

import os
import subprocess
import sys

if len(sys.argv) != 3:                                  # Si el nombre de paràmetres és diferent a 3 (python compta la crida del script), escriu el missatge i surt.
    print("S'han d'introduïr només dos directoris")
    sys.exit(1)

if not os.path.isdir(sys.argv[1]) or not os.path.isdir(sys.argv[2]):      # Si els parametres no son ni fitxers ni directoris, avisa i surt exit 2
    print("Has de passar directoris o fitxers")
    sys.exit(2)

ruta1 = sys.argv[1]
ruta2 = sys.argv[2]
num_tot = 0
compt = 0

def recorrer_directori(ruta1, ruta2):
    global num_tot
    global compt
    for dir1 in os.listdir(ruta1):                      # Fem un bucle buscant directoris dintre de la ruta
        dir1 = os.path.join(ruta1, dir1)                # Agafem la ruta completa d'un arxiu
        if os.path.isdir(dir1):                         # Si és directori
            for dir2 in os.listdir(ruta2):              # Fem un bucle buscant directoris dintre de la ruta
                dir2 = os.path.join(ruta2, dir2)        # Agafem la ruta completa d'un arxiu
                if os.path.isdir(dir2):                 # Si és directori
                    num = int(subprocess.check_output(['./compdir.sh', dir1, dir2]))            # Guardem el nombre que surt de l'execució de l'script ./compdir.sh
                    num_tot = num_tot + num                                                     # Nombre total de percentatge
                    compt = compt + 1                                                           # Comptador iteracions (arxius recorreguts)
                    with open ('recents.log', 'a') as log:                                      # Obrim el fitxer recents.log en mode d'escriptura al final
                        sortida = subprocess.check_output(['./mesactual.py', dir1, dir2])       # Guardem a la variable sortida de l'execució de ./mesactual.py
                        log.write(sortida.decode())                                             # Convertim sortida en una cadena de caràcters i ho escribim al recents.log
                    recorrer_directori(dir1, dir2)                                              # Convertim la fució en recursiva cridant-la a ella mateixa


recorrer_directori(ruta1, ruta2)                        # Cridem la funció per a que s'executi

similitud=num_tot/compt                                 # Dividim el nombre total de percentatge entre el nombre d'iteracions (arxius)
print(similitud)
