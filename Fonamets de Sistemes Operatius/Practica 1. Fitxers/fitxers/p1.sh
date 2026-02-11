#!/bin/bash

if [ $# -ne 3 ]; then                           # Si no hi ha tres paràmetres, avisa i surt amb exit 1
    echo "Han d'entrar 3 paràmetres"
    exit 1
fi

font1=$1
font2=$2
dir=$3

./tipus.sh $1; font_c1=$?                       # Agafem si és fitxer o directori executant el ./tipus.sh
./tipus.sh $2; font_c2=$?                       # Agafem si és fitxer o directori executant el ./tipus.sh
./tipus.sh $3; dir_c3=$?                        # Agafem si és fitxer o directori executant el ./tipus.sh

if [ "$font_c1" == "1" ] && [ "$font_c2" == "1" ] && [ "$dir_c3" == "2" ]; then         # Si la font1 és un fitxer, la font2 és fitxers i dir és un directori...
    echo $(./compfitxer.sh "${font1}" "${font2}")                                       # Executem els scripts corresponents
    echo $(./creanou.sh "${dir}")
elif [ "$font_c1" == "2" ] && [ "$font_c2" == "2" ] && [ "$dir_c3" == "2" ]; then       # Si la font1 és un directori, la font2 és un directori i dir és un directori...
    echo $(./comptot.sh "${font1}" "${font2}")                                          # Executem els scripts corresponents
    echo $(./creanou.sh "${dir}")
else                                                                                    # En la resta dels casos, informem del problema
    echo "Has d'introduïr dos fitxers, o bé, dos directoris, no es poden fer barreges! A més a més, tercer paràmetre introduït ha de ser un directori"
fi
