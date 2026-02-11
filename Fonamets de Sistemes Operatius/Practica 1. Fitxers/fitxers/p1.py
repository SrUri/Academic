#!/usr/bin/env python3

import subprocess
import sys

if len(sys.argv) != 4:                                      # Si el nombre de paràmetres és diferent a 4 (python compta la crida del script), escriu el missatge i surt.
    print("Han d'entrar 3 paràmetres")
    sys.exit(1)

font1 = sys.argv[1]
font2 = sys.argv[2]
dir = sys.argv[3]

font_c1 = subprocess.call(['./tipus.sh', font1])            # Agafem si és fitxer o directori executant el ./tipus.sh
font_c2 = subprocess.call(['./tipus.sh', font2])            # Agafem si és fitxer o directori executant el ./tipus.sh
dir_c3 = subprocess.call(['./tipus.sh', dir])               # Agafem si és fitxer o directori executant el ./tipus.sh

if font_c1 == 1 and font_c2 == 1 and dir_c3 == 2:           # Si la font1 és un fitxer, la font2 és fitxers i dir és un directori...
    subprocess.call(['./compfitxer.sh', font1, font2])      # Executem els scripts corresponents
    subprocess.call(['./creanou.sh', dir])
elif font_c1 == 2 and font_c2 == 2 and dir_c3 == 2:         # Si la font1 és un directori, la font2 és un directori i dir és un directori...
    subprocess.call(['./comptot.py', font1, font2])         # Executem els scripts corresponents
    subprocess.call(['./creanou.sh', dir])
else:                                                       # En la resta dels casos, informem del problema
    print("Has d'introduïr dos fitxers, o bé, dos directoris, no es poden fer barreges! A més a més, tercer paràmetre introduït ha de ser un directori")
