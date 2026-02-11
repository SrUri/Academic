#!/usr/bin/env python3

import os
import sys

if len(sys.argv) != 3:                                           # Si el nombre de paràmetres és diferent a 3 (python compta la crida del script), escriu el missatge i surt.
    print("És necessari passar 2 fitxers!")
    sys.exit(1)

if not os.path.isdir(sys.argv[1]) and not os.path.isfile(sys.argv[1]):      # Si els parametres no son ni fitxers ni directoris, avisa i surt exit 2
    print("Has de passar directoris o fitxers")
    sys.exit(2)
elif not os.path.isfile(sys.argv[2]) and not os.path.isdir(sys.argv[2]):
    print("Has de passar directoris o fitxers")
    sys.exit(2)

if os.path.getmtime(sys.argv[1]) > os.path.getmtime(sys.argv[2]):                                                                                               # Si el primer arxiu és més nou que el segon, escribim el missatge i la ruta absoluta 
    print("L'arxiu " +sys.argv[1]+ " ha sigut modificat més recentment que " +sys.argv[2]+ " i la ruta absoluta és "+os.path.abspath(sys.argv[1]))
else:                                                                                                                                                           # Sinó, escribim el missatge i la ruta absoluta
    print("L'arxiu " +sys.argv[2]+ " ha sigut modificat més recentment que " +sys.argv[1]+ " i la ruta absoluta és "+os.path.abspath(sys.argv[2]))
