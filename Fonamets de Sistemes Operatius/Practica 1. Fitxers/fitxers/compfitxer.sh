#!/bin/bash

if [ $# -ne 2 ]; then                                           # Si no hi ha dos paràmetres, avisa i surt amb exit 1
    echo "És necessari introduïr 2 fitxers, ni més ni menys!"
    exit 1
fi

if  [ ! -f $1 ] || [ ! -f $2 ]; then                                           # Si no son fitxers, avisem i surt amb exit 2
    echo "S'han d'introduïr només fitxers"
    exit 2
fi

diff -ibw $1 $2 1>&2 > diferencies1.txt                         # Fem el diff del paràmetre 1 i 2 i reconduïnt el resultat al fitxer diferencies1.txt
                                                                # i --> ignora casos diferents en continguts de fitxers
                                                                # b --> ignora els canvis d'espais
                                                                # w --> ignora tots els espais en blanc

linies1=$(cat $1 | awk 'NF' | wc -l)                            # Agafem el contingut del fitxer 1 (excepte espais en blanc) i comptem totes les línies que suerten
linies2=$(cat $2 | awk 'NF' | wc -l)                            # Agafem el contingut del fitxer 2 (excepte espais en blanc) i comptem totes les línies que suerten

let liniestot=$(echo $linies1 $linies2 | awk '{print $1+$2}')   # Sumem les linies

liniesdif1=$(cat diferencies1.txt | grep "<" | wc -l)           # Comptem les linies diferents que ens dona el diff
liniesdif2=$(cat diferencies1.txt | grep ">" | wc -l)           # Comptem les linies diferents que ens dona el diff
let liniesdif=$((${liniesdif1}+${liniesdif2}))                  # Sumem la diferència de linies

let diferencia=$((100-(${liniesdif}*100/${liniestot})))         # Calculem el percentatge

fit1=$1
fit2=$2
fit1=$(echo "${fit1%/}" | tr -s '/')                            # Eliminem els "/" que sobre en la ruta donada
fit2=$(echo "${fit1%/}" | tr -s '/')                            # Eliminem els "/" que sobre en la ruta donada

if [ $diferencia -ge 100 ]; then                                # Si diferencia entre els dos fitxers és igual a 100, els treiem a recents.log
    echo "L'arxiu ${fit1} és igual a ${fit2}" >> recents.log
    echo "L'arxiu ${fit2} és igual a ${fit1}" >> recents.log
fi

echo $diferencia
