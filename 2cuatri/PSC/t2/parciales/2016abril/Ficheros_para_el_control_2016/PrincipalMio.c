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
	FILE* fent;
	fent = fopen(nombreFich, "rt");
	if(fent==NULL){
		perror("No se pudo abrir el fichero\n");
	}
	char nombreSoldado[30];
	while(fscanf(fent, "%s", nombreSoldado) == 1){
		//printf("%s\n", nombreSoldado);
		insertar(lc, nombreSoldado);
	}

	fclose(fent);

}


int main(){

	TListaCircular lc;
	crear(&lc);

	char nombre[30];

	int n;

	cargarFichero ("listaNombres.txt",&lc);
	//printf("%d\n", longitud(lc));
	recorrer(lc);
	printf("Longitud de la lista: %d\n", longitud(lc));
	printf("Introduce un número entre 0 y 60: ");
	fflush(stdout);
	scanf("%d",&n);
	while (longitud(lc)>1){
		mover(&lc,n);
		//printf("\n");
		//recorrer(lc);
		//printf("\n");
		extraer(&lc,nombre);
		//printf("\n");
		//recorrer(lc);
		//printf("\n");
		printf("%s ha salido del círculo \n",nombre);
	}
	//printf("\nYepa\n");
	extraer(&lc,nombre);
	printf("--------------------------------------\n");
	printf("%s ha sido seleccionado !!!!! \n",nombre);
	fflush(stdout);

	return 0;
}
