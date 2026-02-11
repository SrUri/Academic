#!/bin/bash

{print gsub(/[AaEeIiOoUu]/, "")}

line=$0
gsub(/[bcdfghjklmnpqrstvwxyz]/, toupper("&"), line)
gsub(/[BCDFGHJKLMNPQRSTVWXYZ]/, tolower("&"), line)