#!/bin/bash

mkdir -p $2		# creo el directorio output si este no existe

for cancion in `ls $1` 
do
	AUTOR=$(more $1"/"$cancion | grep "autor:" | cut -d ":" -f 2)
	TITULO=`more $1"/"$cancion | grep "titulo:" | cut -d ":" -f 2`
	EXT=$(echo $cancion | cut -d "." -f 2)
	
	dir=$2"/"$AUTOR		# nuevo directorio, si no existe
	mkdir -p $dir		# lo creo
	
	cp $1"/"$cancion $2"/"$AUTOR"/"$TITULO".$EXT" # copio la cancion a ese directorio y renombrala
	
done
