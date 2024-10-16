/*
 ============================================================================
 Name        : ExamenAbril2021.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include "Bloques.h"

int main(void) {
	ListaBloques lb;			 //Lista de bloques libres de memoria
	ListaBloques b1, b2, b3, b4; //Punteros a los nodos extraidos de la lista

	//Creamos la lista de bloques libres
	printf("CREAMOS LA LISTA DE BLOQUES LIBRES\n----------------------------------\n");
	fflush(stdout);
	crear(&lb,4096);
	printf("Bloques libres: ");
	fflush(stdout);
	imprimir(lb);
	printf("DEBE SALIR: Bloques libres:  ( 0 512 1024 1536 2048 2560 3072 3584 )\n");
	fflush(stdout);

	//Obtenemos varios bloques de la lista
	printf("\nOBTENEMOS CUATRO NODOS DE LA LISTA DE BLOQUES LIBRES\n----------------------------------------------------");
	fflush(stdout);
	obtenerBloque(&lb,&b1);
	if (b1 != NULL){
		printf("\nLa direccion de comienzo del bloque obtenido es: %d\n",b1->dirInicio);
		fflush(stdout);
	}

	obtenerBloque(&lb,&b2);
	if (b2 != NULL){
		printf("La direccion de comienzo del bloque obtenido es: %d\n",b2->dirInicio);
		fflush(stdout);
	}

	obtenerBloque(&lb,&b3);
	if (b3 != NULL){
		printf("La direccion de comienzo del bloque obtenido es: %d\n",b3->dirInicio);
		fflush(stdout);
	}

	obtenerBloque(&lb,&b4);
	if (b4 != NULL){
		printf("La direccion de comienzo del bloque obtenido es: %d\n",b4->dirInicio);
		fflush(stdout);
	}

	printf("Bloques libres: ");
	fflush(stdout);
	imprimir(lb);
	printf("DEBE SALIR: Bloques libres: ( 2048 2560 3072 3584 )\n");
	fflush(stdout);

	//Devolvemos los bloques insertandolos nuevamentes en la lista de bloques libres
	printf("\nDEVOLVEMOS LOS NODOS A LA LISTA DE BLOQUES LIBRES\n-------------------------------------------------");
	printf("\nDevolvemos el bloque con direccion de inicio 0\n");
	fflush(stdout);
	insertarBloque(&lb,b1);
	printf("Bloques libres: ");
	fflush(stdout);
	imprimir(lb);

	printf("Devolvemos el bloque con direccion de inicio 1024\n");
	fflush(stdout);
	insertarBloque(&lb,b3);
	printf("Bloques libres: ");
	fflush(stdout);
	imprimir(lb);

	printf("Devolvemos el bloque con direccion de inicio 1536\n");
	fflush(stdout);
	insertarBloque(&lb,b4);
	printf("Bloques libres: ");
	fflush(stdout);
	imprimir(lb);

	printf("Devolvemos el bloque con direccion de inicio 512\n");
	fflush(stdout);
	insertarBloque(&lb,b2);
	printf("Bloques libres: ");
	fflush(stdout);
	imprimir(lb);
	printf("DEBE SALIR: ( 0 512 1024 1536 2048 2560 3072 3584 )\n");
}
