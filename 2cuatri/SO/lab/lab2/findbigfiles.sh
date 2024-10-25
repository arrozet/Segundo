#!/bin/bash

for file in $(find $1 -mkdtype f -size +$2c) # -type f -> regular file, -size +numero c -> solo archivos con > numero bytes
do
	NUMBYTES=`stat -c %s $file`
	echo $file":" $NUMBYTES "bytes"
done
