#!/bin/bash

if [ -z "$1" ]; then                                    # Si està buit, avisa i surt amb exit 2
  echo "Error: el fitxer està buit"
  exit 2
fi

if [ ! -d "$1" ]; then                                  # Si no és un directori, el creem
  mkdir -p "$1"
fi

while read -r line; do                                  # Bucle que llegeix les linies de recents.log
  fit=$(echo "$line" | awk '{print $2}')                # Agafem la ruta de la linia llegida

  if [ -f "$fit" ]; then                                # Si és fitxer 
    cp -p "$fit" "$1/$(basename "$fit")"                # Copiem el fitxer al directori amb el seu nom corresponent
  elif [ -d "$fit" ]; then                              # Si és directori
    mkdir -p "$1/$(basename "$fit")"                    # Creem el directori amb el seu nom corresponent
  fi
    touch -r "$fit" "$1/$(basename "$fit")"             # Establim la data de modificació al directori/fitxer
done < recents.log
