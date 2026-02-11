#!/bin/bash

if [ -f $1 ]; then      # Si és fitxer, exit 1
    exit 1
fi
if [ -d $1 ]; then      # Si és directori, exit 2
    exit 2
fi
exit 3                  # En algun altre cas, exit 3
