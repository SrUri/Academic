#!/bin/bash

if [ $# -ne 2 ]; then                                                                               # Si no hi ha dos paràmetres, avisa i surt amb exit 1
    echo "És necessari introduïr 2 fitxers, ni més ni menys!"
    exit 1
fi

if [ ! -d $1 ] && [ ! -f $1 ]; then                                                                 # Si els parametres no son ni fitxers ni directoris, avisa i surt exit 2
    echo "Has de passar directoris o fitxers"
    exit 2
elif [ ! -f $2 ] && [ ! -d $2 ]; then 
    echo "Has de passar directoris o fitxers"
    exit 2
fi

arx1=$1
arx2=$2
arx1=$(echo "${arx1%/}" | tr -s '/')                                                                # Eliminem els "/" que sobre en la ruta donada
arx2=$(echo "${arx1%/}" | tr -s '/')                                                                # Eliminem els "/" que sobre en la ruta donada

if [ $1 -nt $2 ]; then                                                                              # Si el primer arxiu és més nou que el segon, escribim el missatge i la ruta absoluta
    echo "L'arxiu ${arx1} ha sigut modificat més recentment, la ruta és: $(pwd $arx1)/${arx1}"
else                                                                                                # Sinó, escribim el missatge i la ruta absoluta
    echo "L'arxiu ${arx2} ha sigut modificat més recentment, la ruta és: $(pwd $arx2)/${arx1}"
fi
