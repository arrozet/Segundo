
#!/bin/bash
# Recorre todas las entradas (ficheros y directorios) del directorio donde se ejecute el script y los printea, indicando si es fichero o directorio
for i in `ls`
do
	if [ -d $i ]
	then
		printf "%-20s - directorio\n" $i 
	else
		printf "%-20s - fichero\n" $i
	fi
done
