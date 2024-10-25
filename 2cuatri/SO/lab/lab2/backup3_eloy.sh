#!/bin/bash
if [ ! -d "backup" ]
then
	mkdir backup
fi

Dir=backup/$(date +"%y%m%d")

if [ ! -e $Dir"_1" ]
then
        COUNT=0
else
	COUNT=$(ls -d $Dir* | wc -w)
fi

SUM=`expr $COUNT + 1`
Dir2=$Dir"_"$SUM

mkdir $Dir2

for i in $*
do
	if [ ! -f "$i" ] # Chequea si el parámetro de entrada representa un fichero que existe
	then # En caso de no existir, da un mensaje comunicándolo
		echo $i no existe
	else # En caso de existir el fichero ...
		Dir3="$Dir2/$i"
		cp $i $Dir3 # Copia el fichero original ($1)
	fi
done
