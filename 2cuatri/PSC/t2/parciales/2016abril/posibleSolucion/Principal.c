/*
 * Principal.c
 *
 *  Created on: 12/4/2016
 *      Author: mmar
 */

#include "ListaCircular.h"
#include <stdio.h>


// Lee el fichero y lo introduce en la lista
void cargarFichero (const char *nombreFich, TListaCircular *lc){
	FILE * fp = fopen(nombreFich,"rt");
	char nombre[30];

	if (fp != NULL){
		
		while (!feof(fp)){
			fscanf(fp,"%s",nombre);
			insertar(lc,nombre);
		}
		fclose(fp);
	}else{
		perror ("Error al abrir el fichero");
	}

}


int main(){

	TListaCircular lc;
	crear(&lc);

	char nombre[30];

	int n;

	cargarFichero ("listaNombres.txt",&lc);
	recorrer(lc);
	printf("Longitud de la lista: %d\n", longitud(lc));
	printf("Introduce un numero entre 0 y 60: ");
	fflush(stdout);
	scanf("%d",&n);
	while (longitud(lc)>1){
		mover(&lc,n);
		extraer(&lc,nombre);
		printf("%s ha salido del circulo \n",nombre);
	}

	extraer(&lc,nombre);
	printf("--------------------------------------\n");
	printf("%s ha sido seleccionado !!!!! \n",nombre);
	fflush(stdout);

	return 0;
}
