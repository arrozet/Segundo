#!/bin/bash
for i in "$@"
do

if [ ! -f "$i" ]        # Chequea si el parámetro de entrada representa un fichero que existe
then                    # En caso de no existir, da un mensaje comunicándolo
        echo $i no existe
else # En caso de existir el fichero ...

        A=$(ls $i | wc -w)     # Lista los ficheros que comienzan por el nombre dado como
                                # parámetro y cuenta cuantos hay y lo almacena en A
        if [ $A -ge 9 ]         # Si hay 9 o más, da un mensaje indicando que se ha
                                # alcanzado el máximo número de versiones
        then
                echo “Se ha superado el número máximo de versiones”
        else # Si no se ha alcanzado el maximo ...
                date=`date "+%y%m%d_"`       # Se suma 1 al número de ficheros que comienzan
                                        # con ese nombre (A) para que sea la terminación
                                        # de la nueva copia
                Version=$date$i         # se compone el nombre del nuevo nombre de
                                        # fichero (Version) como el nombre original ($1)
                                        # seguido por "." y el número actual de copia (Num)
                cp $i $Version          # Copia el fichero original ($1) con el nombre nuevo
        fi
fi
done
