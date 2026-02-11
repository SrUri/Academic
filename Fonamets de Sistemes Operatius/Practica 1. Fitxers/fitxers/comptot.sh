#!/bin/bash

if [ $# -ne 2 ]; then                                    # Si no hi ha dos paràmetres, avisa i surt amb exit 1
    echo "S'han d'introduïr només dos directoris"
    exit 1
fi

if [ ! -d $1 ] || [ ! -d $2 ]; then                                         # Si no són tot directoris, avisa i surt amb exit 2
    echo "Han de ser directoris"
    exit 2
fi

ruta1=$1
ruta2=$2
num=0
compt=0

function recorrer_directori {
    for dir1 in "${ruta1}"/*; do                                                                        # Anem recorrent directoris a partir de la ruta que ens donen
        for dir2 in "${ruta2}"/*; do
            if [ -d "${dir1}" ] && [ -d "${dir2}" ] && [ -n "${dir1}" ] && [ -n "${dir2}" ]; then       # Si és un directori i tingui un valor
                num=$(./compdir.sh "${dir1}" "${dir2}")                                                 # Guardem el percentatge de l'execució del ./compdir.sh a la variable num
                ruta1=$(echo "${dir1%/}" | tr -s '/')                                                   # Eliminem els "/" que sobre en la ruta donada
                ruta2=$(echo "${dir2%/}" | tr -s '/')                                                   # Eliminem els "/" que sobre en la ruta donada
                echo $(./mesactual.sh "${dir1}" "${dir2}") >> recents.log                               # Comparem els fitxers i treiem el resultat per recents.log
                let num_tot=${num_tot}+${num}                                                           # Nombre total de percentatge
                let compt=${compt}+1                                                                    # Comptador iteracions (arxius recorreguts)
                recorrer_directori "$ruta1" "$ruta2"                                                    # Cridem de nou a la funció per a que ho treballem recursivament
            fi
        done
    done
}

recorrer_directori "$ruta1" "$ruta2"                    # Cridem la funció per a que s'executi

let similitud=${num_tot}/${compt}                       # Dividim el nombre total de percentatge entre el nombre d'iteracions (arxius)
echo $similitud
