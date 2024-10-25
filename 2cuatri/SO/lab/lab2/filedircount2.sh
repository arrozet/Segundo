#!/bin/bash
if [ -d "$1" ]
then
	#for i in `ls -d $1/*` # Para cada entrada "i" del directorio donde se ejecuta el script
	for i in $1/*
	do
		#	echo "entro en el for"
        	if [ -d "$i" ] # Chequea si dicha entrada "$i" es un directorio
        	then # En caso de serlo
                	FILES=`ls -l "$i" | wc -l`      # Lista el contenido del directorio "$i" y
                                                	# cuenta el número de líneas
                	FILES2=`expr $FILES - 1`        # Le restamos uno puesto que una de las líneas es el
                                                # número de bloques que ocupa el directorio
        	# si el directorio esta vacío wc da como resultado 0 y al restarle 1 FILES2 valdrá -1
        	# en este caso, reasignamos FILES2 a su valor correcto que es 0
        		if [ $FILES2 -eq -1 ]
        		then
        			FILES2=0
        		fi
       			echo "$i: $FILES2"      # Muestra el nombre del directorio y el número de
                                		# entradas que hay en él
		fi
	done
else
	echo "$1 no es un directorio"
fi
