#!/bin/bash
mkdir -p backup		# creo backup si no existe ya
date=`date "+%y%m%d_%H-%M-%S_%N"` # esto es otra alternativa para no enumerar ficheros por si son el primero o segundo, sino por tiempo (incluyendo ns por si se hace un backup antes de que pase 1s)
# date=`date "+%y%m%d"`

# para que funcione el otro date, hay que comentar el codigo desde line 9 - 18 y cambiar variable ruta por rutaok
rutaok="backup/$date"

: '	# comentado para mostrar la forma del date en lugar de la enumeracion
if [ ! -e $ruta"_1" ]	# si no hay esta ruta, es que no hay ninguna
then
	NUMFICH=0
else
	NUMFICH=`ls -ld $ruta* | wc -l`
fi

NEWFICH=`expr $NUMFICH + 1`
rutaok=$ruta"_"$NEWFICH		# la ruta para varios backups el mismo dia es esta
'

for i in $*
do

	if [ ! -f "$i" ]        # Chequea si el parámetro de entrada representa un fichero que existe
	then                    # En caso de no existir, da un mensaje comunicándolo
        	echo $i no existe
	else # En caso de existir el fichero ...
		mkdir -p $rutaok	# creo una carpeta con la ruta correspondiente al dia en el que estamos y la version de backup
                cp $i $rutaok           # Copia el fichero original ($i) a la carpeta
	fi
done
