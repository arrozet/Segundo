#!/bin/bash
if [ -x $1 -a ! -d $1 ]	# es fichero ejecutable y NO es fichero
then 
	echo $1 es un fichero ejectubale
else
	echo $1 no es un fichero ejecutable
fi
