#!/bin/bash

if [ $# -ne 2 ]; then                                                       # Si no hi ha dos paràmetres, avisa i surt amb exit 1
    echo "S'han d'introduïr només dos directoris"
    exit 1
fi

if [ ! -d $1 ] || [ ! -d $2 ]; then                                         # Si no són tot directoris, avisa i surt amb exit 2
    echo "Han de ser directoris"
    exit 2
fi

dir1=$1
dir2=$2
compt=0

for arxiu1 in "${dir1}"/*; do                                               # Iterem la ruta del primer directori
    for arxiu2 in "${dir2}"/*; do                                           # Iterem la ruta del segon directori
        if [ -f "${arxiu1}" ] && [ -f "${arxiu2}" ]; then                   # Si és un fitxer
            num=$(./compfitxer.sh "${arxiu1}" "${arxiu2}")                  # Agafem el numero que ens proporciona l'execució del compfitxer
            echo $(./mesactual.sh "${arxiu1}" "${arxiu2}") >> recents.log   # Comparem els fitxers i treiem el resultat per recents.log
            let num_tot=${num_tot}+${num}                                   # Nombre total de percentatge
            let compt=${compt}+1                                            # Comptador iteracions (arxius recorreguts)
        fi
    done
done

let similitud=${num_tot}/${compt}                                           # Dividim el nombre total de percentatge entre el nombre d'iteracions (arxius)
echo $similitud
