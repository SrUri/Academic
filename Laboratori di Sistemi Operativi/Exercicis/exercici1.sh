#!/bin/bash

archivo="$1"
existe=false

while [ "$existe" = false ]; do
    if [ -e "$archivo" ] && [ -s "$archivo" ]; then
        echo "✅ El archivo '$archivo' existe y no está vacío."
        echo "Número de líneas:"
        wc -l < "$archivo"
        existe=true
    elif [ ! -e "$archivo" ]; then
        echo "⚠️  El archivo '$archivo' no existe."
        read -p "Introduce un nuevo nombre de archivo: " nuevo
        archivo="$nuevo"
    fi
done